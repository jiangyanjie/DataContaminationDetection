package    Optimizables;

import graph.GraphWindow;
import graph.IntegerPair;
import graph.UndirectedGraph;

import    java.util.HashMap;


  public class CouplageH extends Optimizable {
  UndirectedGrap  h    g ;
  private i  nt nbV ;       //n   um    ber of vert       ices
  int nbE ;    // number of edges
  private int lastvalue ;
  int[][] struc   ture ;  
     IntegerPair[]   pairing ;
  
  public   CouplageH  (UndirectedGr    aph gra  phe )    {
      g     = graphe     ;   
    se    tNbV(g.n umberOfV  ert     ices())     ;    
    nb  E = g.numberOfEdges() ;
    pairing = new Inte      gerPair[get      NbV    ()] ;  
        for (   int j =0 ; j<getNbV(          ) ; j++)    {
        pai ring[    j] =       new     IntegerPair(   -1,-     1) ;
           }
  }
  
                    pub        lic void init(){    //init pair  ing 
    f  or (int j =0 ; j<    getNb        V() ; j++) {
        pairing[j] =   new Integ     erPair(-    1,-1) ;
      }
    l ast    valu   e =     fitness()   ;
  }
  
  publ     ic Optimi                 zab l e mutation(double lambda){       
      int l=poisson(   lambda)+    1;
    //    System.ou      t.printl       n("L : " + l + " la   m  b da : " +            l   a   mbda    ) ;
    CouplageH c andidate = mu   tateCouplage(l               );
    //x      .println() ;
                   int max=candidate.     fitness();      
    for(   int     i=0;i<  lambda-1;i++){
       C          oupl     ageH t  emp   = mutateCouplage(l);
      //tirages+  +;
            //te mp.println();
      if   (temp   .fi tness     ()>max){
        c    andidate=      temp;
        max=te                   mp.lastvalue;
            }    
    }
    ca    ndida             te.  l   astvalue = m   ax ;
    //candidate    .printl               n();
     re     turn candidate;
  }
     
  /    *     public s    tati  c in   t poisson(d    o ub      le lambda){
                     double t=  Ma th.exp(-lambda)   ;
    int   x=0;
      doub   le prod=Ma      th.ran dom();
    while(prod       >= t){
      x++;
                  prod*=M    ath.random();
          }      
    retur  n x;
  }   */
    
  public Optimiza      ble   crossover(   Optimizable xprim, dou    ble lambda)     {
           CouplageH candida     te=new C ouplageH(g);
        double    c =       1.0/lambda ;
      int max = candida       te.lastvalue     () ;
          for(in     t i=0;i<  lam    bda;i++){
                 Coup   lageH temp= Coupla  geH   .cross Couplag e(c,th   is,(Coupla  geH)           xprim);
        //temp.p          rintln();
      //tirages   +      +;
        int   f =  temp    .fitness() ;   
         if(f>m  ax ){
        cand  idat  e=temp;
                    max=f;
        }
      els  e {candi               da   te = this ; max = this.lastvalue()      ;}
           }
    candidate.lastvalue = max ;
        return   candida        te;
  }
    
p  ublic Coup    lageH    copy() {   
  CouplageH c = new Coup   lag     eH(g) ;         
    c.init();
  for (int j =0 ; j<getNbV() ; j++) {
      c.pair    ing    [j ] = this.pa   iring[j]   ;
    //   #A  _supprimer
      /*for           (in     t k=0; k<nbE        ; k++){
    c.s    tru   cture [j][    k] =   thi       s.str   uct      ure[ j]         [k] ;
    }     */
       }
  c.g = g ;
  c.lastvalue   = lastvalue    ;
      r   eturn c ;
}     

public     In    tegerPa    i  r coupleRand         Edges(int ver     tex){
  int a = -1 ; int b    = -1 ;
        whi  le (a ==    -1 ){   
    int i = (in    t   )   (Math.r   andom()* (doub le)nbE) ;    
       if (structure[vertex][i] !   = -1) { a=i;      }
  }
  w     hile (b == -1     ){
                int i = (int) (Mat     h.random()* (doubl  e)nbE     ) ;
    if (i !    = a   &&  structure[vertex][i] !=       -1) { b=i; }
  }
  IntegerPair p     = ne   w IntegerPai      r(a,b) ;
  return p ;   
}
    
p     u   blic    void setEdge(in   t e ,    int v){
       Inte  gerPair p = p    airing[v] ;
  i   f (p.a == -1    ) {
    pa   iring[v] = new Inte g  e   rPair        (e,p    .b) ;
  }
  els     e if (p.b == -1) {
         pairing[v] = new IntegerPair(p.      a,e) ;
   }
  else     if   (   Math.ran dom()* (d         oubl  e) 2 < 1) {   
    if (e != p.b) {pairi      ng[v] = new Integer        Pair(e,p.b)      ;} //edges have   to be differ     ent for th    e pa         iring
  }
  else {
    if (e != p.a) {pairing[v] = new IntegerPair(p.a,e) ;}  //edges hav    e to be   different for the pairing
  }
}
             
public CouplageH  mutateCoupl    age(int l){
  CouplageH couplage2 = this.copy() ;
  for (in          t i=0 ;    i < l ; i++) {
    int randEdge = (int )    (Math      .random() * (d    ouble) nbE ) ;
    //System.out.println  ("r andE : " + randEdg  e) ;
    if (Math.rand     om()* (d   ouble) 2 >=      1) {
      couplage2.setE      dge(randEdge,    g.getEdge(rand Edg      e).a) ;
         /    /     System.out.print     ln("1 - somme  t :    " + g.getEdge(randEd  ge).a) ;
            }
        else {
           coupla             ge2.setEdge(randEdge,g.ge   tEdge(randEdg      e).b) ;
      //System           .out.println      ("2 - sommet      : " +    g.getEdg   e(randEdge).b     )  ;
    }
        }
  retu rn couplage2 ;
}
 
publ     ic stati    c CouplageH crossCouplage(d  ouble c, C oup    lageH x   , C  ouplageH xprim){
      CouplageH offsprin   g = x.copy() ;
  for (int v= 0 ;    v     < x.   getNbV(  ) ; v++)             {
       if(Math.ran            dom()        < c)  {
      offspring.pairing[ v] = xprim  .pai    ring[v] ;
    }
  }
        return         offspring ;
}

public void p rintln() {
  //GraphWindow graphWindow=new Grap   hWindow(6    00, 600,  g,new HashMap<Intege     r,Intege r>(), ne        w HashMap<     Integer       Pair, In    teger>() , new HashM  ap<I    n  tege rPair ,Float>()  );
  for(int i = 0; i < getNbV(); i++)    {
      Syst e    m.out   .print(pa  iring[i].a + ",    "     + p   airi    n    g[i]. b + " - ");
  }
  System.     out.    println();
}

pu  blic static int     poisson(dou ble lam         bda){
      do   ub    l  e    t=Math   .exp(-l  ambda);
  int x=0;
  double prod=Math.rand  om();
       whi    le   ( p     rod>=t){
                x ++;
        prod*=M  ath.random()   ;
  }      
  r         etur  n     x;
}



public i     nt fitness() {
  return fitness3()  ;
}

  int fitness1() {
       la  st         value = length   Path      s();
  retur  n lastvalue ;
} 

int fitness3() {         
  int l = lengthPaths2(   ) ;  
      lastva  l     ue = l;
  ret   u  rn lastvalu    e ;
}

int fitness2() {
       int f   = 0 ;
  int[] t = ne  w           int[n   bE] ;    
  fo         r (int      i=  0; i < g  et    Nb V()          ;  i         ++) {
    Inte      gerPair p =           pairing[   i] ;
    i f (p.a !=       -1 & & p                         .b != -1     ) {
      if (t[p.a   ] !=  -   1) {t[p.a] = -1 ; f     ++   ; 
          //System   .out.pr      intln("int       : " + i + " p.   a =" + p.a) ; 
      }
              if (t[p.b] != -1) {t[p.b]     = -1 ;  f ++ ; 
                /   /Sys    tem.o ut           .println("int : " + i + " p.b =  " + p.b) ;
           }
    }      
    el     se { f += getNbV()  ;     }
  }
  f   = n  bE - f ;
   if (f == nbE       -g  etNb    V()) { 
        if  (m   es    ureChemin(0,n     ew               bool   ea   n[    getNbV()     ])==(  get    NbV()+1)   )  { f+=   g    etNbV(); }
    else     {
                f = f -1;  
    }
  }
  las  tvalue = f ;
       retur    n f ;
}

@Override
p      ub       lic        int l   ast valu           e()    {
         return lastvalue;     
}


  publ ic   int   lengthPaths  (){  // return the s   um    of the   ( pa    th     s'    lengt    h)ï ¿ ½ (    in   terms of v     ertic es)
  int l   =   0 ;
  in   t k     = 0 ;
      b oo         lean[]       visites = new boolea n[getNbV()         ] ;
                   fo  r  (    in   t v =    0;     v < getNb  V(); v++) {
    if(                 !                     vis    ites[v]){   
       int temp = mesu  reChemin   (v,visites) ;
            //System  .o   ut.println("lengt h         path     : "       + temp) ;
                          i f    (temp == 0) { k+     + ; }
            l += temp*tem   p ;      
      }
  }
   return  (   l - (k*getNbV())) ;     
}


publi c      int mesureC     hemin        (int v, boolean[]       v     isites){  
  int l = 0      ;
  i    n      t e =0;
     //Syst   em.out.println      ("-   --D       ï¿   ½but 1  --   -") ;
  //   System.out.print  ln("  Dï¿½p     art: " + v) ;   
  int suivant                   ;
  Boolean b =   true;
  in  t edge = pai    rin    g[   v].a ;
  int edge2 =     pairing[v].b ;
  if (edge !   = -1 && e dge2 != -      1) {
    IntegerPair     p =     g.getEdge(edge) ;                    
    / /l++ ;
    if (p.a == v) {suiv      ant = p.b ;}
    e lse      { su     i   van    t             = p.a ;   }       
   w  hi              le( !vi   sites[suivant]           && b &&        su   ivant != v  )  {    //o    n explore d'un cï¿       ½tï¿½
        /    /Syste m.out.p  rint(    suiv          ant) ;
      visites[su     ivant]  = true ;
        Int  egerPair q =       pairing[suivant] ;   
      b =     f    alse ;
             if (q.a     == ed     ge) {e = q.b ; b = true ;}
      i                   f (q.b ==     edg    e)  {e = q.a ; b =     true    ;}
           if (q.a == -1     || q. b ==   -1) {b = false ;}
      if (b) {
                 edge = e   ;
            p= g.getEdge(       edge)    ;
              if (p.a == sui   vant) {suivant = p.b ;}
                      els   e { s  ui     vant  = p.a ; }
        l   ++ ; 
      }
      //S    yste    m.out.p rintln(" - " + suivant) ;
    } 
     if (suivant == v && (pa  iring[v]         .a ==   edge || pairing[v].b == edge )) { l+= 2        ; }
        //System.   out.println("   Dï¿½p   art2: ") ;        
      b =     tru   e ;
      edge = edg  e2 ;                    //on expl   o   re l'autre c  ï¿½tï¿½
      p =     g.get    E dge(e   dge) ;
         if (p  .a == v) {suivant   = p     .b ;}
      el se { suivant  =    p.a ; }
         while(suivant !=      v && !visites[suivant] && b){  
            //Sys   tem.out.print(suivant) ;
        visites[suivant   ]  = true ;
               Integ  erPai     r q = pairing[suivant] ;
                  b = false ;
        if (q.a == edge ) {e = q.    b    ; b = true ; }
          if (q.b == edge       ) {e = q.a ; b = t   rue ;}
        if         (q.a =   = -1 || q.b == -1) {b = false ;}
        if    (b) {
          edge = e   ;
          p= g.getEdge(edge) ;
          if (p.a ==  v) {suivant = p. b ;}
                          else { suivan  t = p.a ; }
          l++ ;
        }
           //System.out.print    ln(" - "    + suivant) ;
      }
  }
  visites[v] = true ;
   //System.out.println(l) ;
  return l ;
}




public int len   gthPaths2(){  // return th        e sum of the (paths' length)ï   ¿½ (i    n terms    of vertices)
  int l = 0 ;
  int k = 0 ;
   bo    olea n[] visites = new bool   ean[getNbV()] ;
  for( int v =0; v < g      etNbV(); v++) {
    if (!visites[v]) {
      int temp = mesureChemin(v,visites) ;
      //System.out.println("length path : " + temp) ;
      if (temp ==0) { k++ ;}
        if (l< temp) { l =    temp ; }
    } 
  }
  return (l - (k*getNbV())) ;
}


public void printGraph( ) {
  HashMap<Inte  gerPair, Integer> couleurs=new HashMap<IntegerPair, Integer>();
  GraphWindow graphWindow=new   GraphWindow(600, 600, g,new HashMap<Integer,Integer>(), couleurs, new HashMap<IntegerPair,Float>());
  for (int v = 0 ; v < getNbV() ; v ++) {
    IntegerPair p = pairing[v] ;
    if(p.a != -1){
    couleurs.put(new Inte gerPai     r(g.getEdge(p.a).a,g.getEdge(p.a).b),5);
    couleurs.put(new IntegerPair(g.getEdge(p.b).a,g.getEdge(p    .b).b),5);
    couleurs.put(new IntegerPair(g.getEdg   e(p.a).b,g.getEdge(p.a).a),5);
    couleurs.put(new IntegerPair(g.getEdge(p.b).b,g.getEdge(p.b).a),5);
    }
  }
}

public int getNbV() {
	return nbV;
}

public void setNbV(int nbV) {
	this.nbV = nbV;
}
}