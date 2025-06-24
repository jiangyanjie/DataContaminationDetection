/*
 * To  chang      e thi        s    template, choose    Tools     | Template  s
    *    an    d ope  n the template in t  he editor.
 */
package readtext.cassandra;


imp    ort co   m.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.co re.Cluster;
import com.datastax.driver.cor      e.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datas   tax.driver.core.Row;
import com.datastax.   driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;
import java.util.Ar    rayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import r    eadtext.MovieData;
import   readtext.ReadTex  tFro  mF   ile;
/**
 *
 * @author Group18
 */
publ     ic class C reateAndFil  lData    b  ase {
   
     private Cluster   cluster  ;
    priva    te Session sessio    n;
   
    private PreparedStatement insertmo   viede  sc;
    pri  vate Prepa     redStatement insertratin  gs;
    privat e PreparedStatement insertactors;
      private Pr   eparedSta   tement insertpop     ularity;
    private P    repared  Statement select    actorfilmedin;
       pr ivate String createmovi e_des  c;
    privat            e    String createratings;
    p    rivate String createactors;
    private String createpopularity;
           private HashMap<Stri     ng,Inte  ger> acto rmap =    new HashMap       <String,       Integer>( );
                   
      publi  c CreateAndFillDatabase(Strin       g node){
           conne  c             t(node  );
        init();
    }
       
            private void ini   t(   ){
                //st a    tments for creating tables
         crea     temo   vi e_d     esc =     "     CREATE TABL   E movie_de         sc (titl e var                  c    har PR  IM ARY KEY,    "  
                          + " descripti  on        v   archar)";
                  createra  tings =   "CREATE TABLE   ratings ( genre varchar    , "
                     +   "rating float,  "
                                          + "title varchar,   "
                  +    "PRI      MARY K   EY        (gen re      , rati ng, title))          ";   
        creat  eacto rs   = "C     REATE TABLE actors  (name v  archar PRIMARY K          EY, filme  d_i            n int)";
           c   reatepop    ular      ity  =    "CREATE TABLE popu   larity (fak e_      field int, filmed_in   int, "
                 + "name varchar, PRIMA  RY KEY (fake_field, filmed_in, name))";
        crea   teT     abl        es();
               
        //prepared Statm  ents for inserts
            insertmovied  esc = session.prepare("INSE    RT INTO movie_desc (titl  e, description) VALUES (?  ,?)");
           in    sertratings = session.prepar  e(     "INSERT INTO ratin    g   s (ge                       nre, ra     ting,   title)   VAL           UES (   ?,?,?)");
        insertact      ors       = session.prepare("INS     ERT INTO act   ors (name, fi  lmed_in  ) VALUES(        ?      ,?)");
                    insertpopular  ity = ses    sion.pr     epare(    "INSERT INTO     populari  ty (fake_field, f     il  med_i   n,     name)        VALUE   S (1,?,   ?)");  
             selectac     torfilmed  in = session.    prepare("SE       LEC    T film ed    _in  FR O                  M actor s WHERE             name    =?");
                        }
     
           public v oid inser   tDa ta(     Arra     y     List da   ta){
                  int done = 0   ;
                BatchStatement  batch   = new BatchS   tatem   ent()  ;
                                  for(int i = 0  ;     i        < data.si   ze(); i+   +){
                           don e++;
                        MovieDat               a md = (    M     ovieDat a)data.get( i);
                  b       atch.add(i nsertmovi    edesc.bind           (md.getTitle(),       md.     g         etDe    sc    rip       tion   ()));
                             St   rin   g []                           genre  = md.getGenre();
                   for(in t    j    =         0;   j     < genre.l       ength;  j++)  {
                     batc       h  . add(i   nsertr    atings.bind(genre[           j], md.getRa             t        ing(), md.getT   itle()))  ;
                        }
                                        Str in   g []        act = md.  ge  tA   ctors();
                                     for(int j = 0; j      <       act          .lengt h; j++ ){
                         addA   ctor     ToMa p(a     ct[j]);
                          }
                                
                                         if(done > 999    ){
                             sessio   n.exe        cu t    e  (b          at   ch);  
                            Sys  tem.o     ut.      pr     intln(i +  " o f b   atch don e      "     );  
                    done =  0;
                          bat   ch = new BatchStatement();
                       }     
        }
             session.ex   ec    ute(       batch);
        fil   l Ac  t  o   rs ();
           System.out.p  rintl      n("In   serting in movie_des       c done.");   
         }
    
     private      vo id fil l    Ac    t ors   (){
                 B    a   tchStatem en t batch = new Batc    hSta   te  ment();
         in          t cnt      = 0;
        int tot = 0;
              for       (Map.Entry                   <St ring    , Integer> entry   : this.actormap.entrySet()) {
                         if(!entry   .getKey  ().equals("unknown")){
                cnt++;
                   tot+       +; 
                                        batc      h.a                                  dd(         inserta    c    tors.bind(entry.ge  t Key(  ), entry.getV al     ue()));
                              bat  ch.a  dd(insertpopul    arity.   bind(entry.getValue(), entry.getKey()        ));
                           if(cnt   >    10000 )      {
                                                  session.exec  ute  ( b   atch)   ;
                         cnt = 0;  
                         b atch = new Ba  tchStateme n   t()     ;
                       }  
                   }
        }
          Sy  st   em.out.print  ln ("i nserted " + tot + " rows   into popularity and a  ctors")        ;  
            ses  sion.e  xecute(b  a tch)          ;
            
    }
       
                 pri     vate void addAc   torToMa   p(String n   ame  ){
         //if(!name.equals("unkno  w   n")){
              
                                            In      te  ger integer = thi    s.actormap.get(name    );
                           i      f(int   eger     ==                   nul  l){   
                      intege      r = 1;
            }
                 else{
                                 int     eger++;
                                           }
                 actormap.pu    t(name, integ   e           r);
               //}
      }
    
    pub     li      c in   t getFil   medIn (Strin  g acto   rName) {
		ResultSet    result          s     = sessio   n
				.exe cute(this.select     actorfilmedin.bind(actorName)  );   

		if (results ! = nul         l) {
			Row des  cr   iption = results.one();
			    return descr   iption.get       Int(0);
                            
		}
		re turn 0;     
	}
       
    private void createTables      (){
                  tr  y{
              se     ssion. e   xecute(createmovie_desc)    ;
            System. out.println("movie    _d     esc created..");      
                       }catc                  h          (A   lreadyExistsException  e){
            Sys  tem. ou   t          .println("movie_des c alread           y exists, sk   ipp       ed..");
            }
        t  ry{
                         sess     ion   .execute(creater  ati ngs); 
                              System .out    .println("    ra tings cr   ea    ted     ..");
        }catch(Alre           adyExists     E    xception e){
                            System.out.println("ratings    al   ready e    xists, skipped..");
                               }
         try{     
                         s ession.e  xecu     te(c      re      a  tea       c           tor        s);
                       System.ou      t.        pri        nt      ln("actors created.   .");
              }catch(AlreadyExistsException e){ 
                   Syst           em.out.println("ac          tors alrea    dy exis  ts, sk        i  p     p  ed        ..");
           }
        try{
                            session.execute(crea          tepopularity  );
                              System.out.pri  ntln(    "popularity  cr eate     d..    ")  ;
                 }catch(Alread             yEx  istsExcept    ion e){
                      System.out.pri ntln("po      pu  lari  ty already e  xists, skipped..    ");
               }
         }
             
       /*public String get M    ov    ieDesc (St    ring   t   itle)   {
          selec               t         mo    viedesc.bind(title);
                 ResultS  et   results   = s        essio  n.execute(selectmovi  edesc.bind(title ));      
               String result     = "";
             for (Row row : results)   {
                   result +=     ro   w.g   et  Stri    n                      g("d  escri pti                   on") + "\n    ";
                    }
               retu       rn result;
       }  */
    
                privat   e   void co  nnect   (S    t    ring     node)    {
        java.util.Ca     lendar cal =       java          .util.Ca lendar.get      Instance();
          lon  g start       = cal.getTimeInMi      llis();
              c   luster = Cl       uster.builde   r()
                        .addC   ontactPoi                nt(  nod   e   )
              .build();   
        
              session = cluster.connect(  );
           sessio    n.execute("USE g      roup   18;");
           cal      = java.util  .Calendar.getInstance();
              long end =     cal.get     TimeInMillis()   ;
           lon   g diff = end - start;

               Sy      stem.o   ut.println       (          "   C  onnected. Time ellapsed: " +  d             iff);                   
    }     

    
      /*public void i    nsertData_   M  ovie_Desc(ArrayList data){
        int do    ne = 0;
        BatchStatement b  atch = new BatchStateme    nt()         ;
          for(int i =   0; i < data.size   (); i++){
                 done+ +;
                 MovieData md = (MovieData)data.get(i);
            batch.add(insertmoviedesc.bind(m     d.  getTit  le(), md.getDesc      ription()));  
                if(done >     999 ){
                s      ession.  execute(ba   tch);
                System.out.println(          i + " of batch done");
                              done = 0;
                batch   = new Batch    Statement();
               }   
        }
        session.ex  ecute(batch);
        S    ystem.out.println("Inserting in mov     ie   _desc   done     .");
    }
    
      p     ublic vo          i  d   insertData_Acto     rs(MovieData md){
        int done    = 0;
             Pr   eparedStatement ps = session.prepare("INSERT INTO a  ctors           (name, filmed_i  n) VALUES (?, ?)      ");
                BatchStatement batch = n      ew BatchS ta tement()  ;         
        for(int i = 0; i < md.getAc   t     or       s().length; i++){
            done++;
               if(done > 999 ){
                       session.execute(ba     t  ch);
                System.out.println(i + " of batch done");
                done = 0;
                          batch = new          B     atchStat    ement();
                }
            batch.add(ps.b  ind(md.getActors()[i     ], md.getTitle()));
        }
            System.out.println("Inserting in actors done.");
      }*/
    
    public void showMoviesData(){
        java.u til.Cal      endar c   al = java.util.Calendar.getInstance();
        long start = cal.ge      tTime   InMill    is();
        ResultSet res = session.execute("SEL   ECT * FROM m ovies");
        cal = java.util.Calendar.getInstance();
        long end = cal.getTimeInMillis ();
        l ong diff = e  nd - start;    
        System.out.println("time ellapsed: " + diff);
        for(Row row : res){
            System.out.println(row.  toString());
        }
    }

    public void close() {
            cluster.close();
        session.close();
        System.out.println("Connection closed. ");
    }

    public static void main(String[] args) {
        CreateAndFillDatabase client = new CreateAndFillDatabase("54.185.23.182");
        //CreateAndFillDatabase clien   t = new CreateAndFillDatabase("localhost");
        ReadTextFromFile read = new ReadTextFromFile();
        
            client.insertData(read.fillArrayListWithRange(2010, 2014));
        client.close();
    }
}
    
