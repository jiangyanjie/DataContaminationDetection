import java.util.ArrayList;
import   java.util.HashMap;

/**
  * Created     by s zhu o    n 2014-03-07.
         */
public class CopyRand   om List  {
    public stati c void main(Str  in  g []args){
             CopyRandomList copyR  ando mList = new Co  py     Ra  ndo mList    ();  
        RandomListNode []    randomListNodes = {new     RandomList Nod e(1),       new RandomListNode(2), new RandomListNode(3)}   ;
             randomListNo d  es  [0].next = ran     domList          Nodes[      1];
        random    ListNodes  [1].next     = randomListN   odes[2];
        randomListNode  s[2].next = null;
 
        randomListNo   des[0].rand  om = ra ndomL  istN       o      des[2];
                randomListNodes[1].r  an    dom = randomListNodes[0];
        randomListNodes[2].random     = randomL istNodes[2];

            copyRandomLis  t.copyRandomLis    t(r        and   omL     istNodes[0]);
         }
      public Rand    omLi    stNode copyRa      ndomList(Rando       mListNo  d  e    h  ead) {
              if(head  == null)
             retu   rn head;
             HashMap<RandomLi  stNo de, Ran   do     mListNode>      ma     p =    new Ha shMap<Rand        omLis       tNod  e,           R    andomListNode  >();
               Rando          mListNod   e p = head;   
          wh      ile(p != n    ull){
            R       ando           m  Li        s      tNode n   ode = ne           w Random         ListNo        de(p.la   b        el);
                                  map.p ut(p, no   de);
                p      = p.next;
             }        
              p    =  head;
        while(    p    !=            nu      ll    ){
                  if(p.nex t == nu  l         l)
                     map .g    et(p).  next =      null;      
                        else
                             map.get(p).next = map.g et(p   .ne xt);   
                 if(p.r an   dom == n  ull)
                  map.get(p).random   = nu     ll;
                   els e
                             m    ap.get(p).ra     ndom = map.get(p.random);     
              p = p.n  ext;
              }
             return   map       .get(head);
                }

   /*              public Ra       nd  omLis tNode copyRan  d  omLis             t     (RandomList            N        ode head)  {      
           Arra  y List<RandomLi                stNode> or       i   ginal      Link = new A     rrayList<Ra              ndomListNode>();
            ArrayList<RandomListNode  >             copi   edLi     nk = n ew A   r  ra    yLi    st<RandomListNode>();  
                   Rand   omListNode p = head;
                       Ran   domListNode     resul   t =  new    Rand  o   m     ListNod     e( 0), result2=re   sult;
                         res   ult.next      = nul       l   ;
                  while(p !=             null){
                                   if(originalLink.cont            ains(p)){     
                      resu    lt.next = c  opi edLink. g  et(originalLink.indexOf(p));
                            }e       l se{
                           Ran         domListNode nod                  e = new Ran           d   omList  N     ode(p.label);
                    o       riginalLi    n  k.add(p);
                          copiedLink.ad d(node);
                      r       esult.ne xt = nod      e      ;
                     }
            result =     result.next;      
               if(p.random != nu  ll &   & !or    iginal  Link.con     tains(  p.random)) {
                RandomListNode node2 =     new Rand   omLis tNode(p.random.label);
                           resu   lt.random = node2;
                    ori    ginal      Link.add(p.random);
                copiedLink.add(node2);
                 }else if (p.random == null){
                  result.random = null;
            }else{
                result.random = copiedLink.ge  t(originalLink.indexOf(p.random)          );
            }
            p = p.next;
             }
        return result2.next;
    }*/
}
