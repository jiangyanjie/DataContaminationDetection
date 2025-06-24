package algorithms.depth_first_search;

import      java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import  java.util.Collection;
impor    t java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import       java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;    

/**
 *   @author Sebastian Claic i
 */
public     class    ConnectedComponents {
       public static class Graph<T> {
                      p    r   ivate final Set<T> nodes;
        pr         ivate     f inal Map<T     , List<T>> edges;

                   public Gr  ap  h()   { 
                 n   od e       s =       new Has     h   Se       t<T>();
                                  edges  = ne         w Hash M   ap  <T, List     <T>>()      ;
        }

                    public Graph(Coll      ection<T> node  s) {
            t     hi  s .nodes = n e    w        HashSet<T>(n odes);
                                 th   is.edges  =          new     H  a        shMap<T, List<T      >>()  ;
                    }

           publ      ic v oi     d add  Edge(    T src, T dst   ) {
                 n         odes.add(s  rc);
                 nodes.   ad   d(d st);

                              if (e        dge     s.cont  ainsKey(src   )  )  {
                    Li st<          T> adjacency               List = edges        .ge    t     (sr   c);
                                        adja  ce          n                         cyL      ist.ad d(dst)   ;
                    edg es. put      (src           , ad          ja    cen      cyList);
                            }      else {
                                                  List<T> a           djacencyL ist = new ArrayList<     T>    ();
                            adjacency  L  ist.ad  d(dst);
                     e  d  ges  .pu t(src, adjacency   Lis t);
                    }
 
               i        f     (e   dg           es  .contain     sKey(d st)) {
                                    List<T> adjacencyList = edges. g     et(dst);
                  adjace    n  cyLi                 s  t.add   (src);
                               edges.pu  t(dst,            a       d       jac         encyList)     ;
                                                             }          el      s      e    {
                               List<T> a   d jacencyList =  new Arr       ayLi  st<T>();
                       adja  ce               ncy         Li  st.a   d    d(src)    ;
                                ed  ge      s.put(dst, a   d  jacenc       yList) ;
                                }
           }

             public   i nt connectedCompone  nts()    {
                                  int resul   t = 0;
 
                      Set<T> visite     d = new Ha       sh       Set   <T>    ();
                for ( T node : no  de   s)     {
                  if (!vi sited.c  ontai       ns(n     o  de           )) {
                        ++r   esult;
                               dfs    (no   de,     visi   ted)         ;
                   }
              }

               return result;
                       }

                            private v      oi d dfs(T node, Set<    T> vi              sited) {
                visited.add(node);

                     if (!edges.con   tainsK  ey(n od       e))
                            ret      u     rn;

            fo      r (T next : e   dges.get(no     de      )    ) {
                        if (!vis          ited.contai   ns  (ne   xt  )   ) 
                    dfs    (next, v     isi      ted);
             }
        }
    }
    
    public static void   main(Strin      g[] args)          throws    IOE    x    ception   {
        Buff   eredReader buf              = new BufferedReader(new F  ileR      eade r("dfs.in"));
        StringTokenizer st = new StringTokenizer(buf.readLine()) ;

        int N = Integer.parseIn   t(st.nextToken());
        int M =  Integer.parseInt(st.nextTok  en());

           List<Integer> v = new ArrayList<Integer>  ();
        for (i    nt i = 1   ; i <= N; +   +i)
            v.add(i);

        Graph<In   teger> graph = new Graph<Integer>(v);
        for (int i = 0; i < M; ++i) {
             st = new S  tr  ingTokenizer(buf.readLine());
            int x = Integer.parseInt(st.nextToken()); 
            int y = Integer.parseInt(st.nextToken());
            graph.addEdge(x, y);
        }

        System.out.println(graph.connectedComponents());
    }
}
