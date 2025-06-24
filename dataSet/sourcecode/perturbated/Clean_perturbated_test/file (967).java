/*
         * To change this template, choo         se Tools | T empla  tes
 * and open   the              template in the editor.     
 */
package Domini;

im  po       rt java.util.Arra  yList;

/**
 *
 *        @author miquel
 */
public class        CtrGeneraci  o {
      
       private RestriccioTemps    re    sT;
    private    ArrayList<AulaLa b> cjtAulLab;
    private ArrayList<AulaTeo> cjtAulTeo;
    priv   ate Array   List<    Ass   ignatur    a>   cjtAs;
    private CjtRestriccions cjtRes;
    private CjtRestGrupoAula     cjtResGA;
       privat e CjtRestAssignatura cjt  RestAss;
    p   rivate     CjtRe  st    Gru   pSessio c    jt  RestGS      ;
    private    CjtRestSolapament cjtRestS;
       private CjtRestricci   oAula cjtRestAul;
      p  riv       ate Str      in     g       n omUnit a      t;
    priva  te   Quadric        ula q   u       ad;
    p  riva        t     e Gen     erador gen;
    
     
          /   **
        *
                  * @param      nomU
       */    
                  public  CtrGeneracio( String nomU ) {
        nomUnitat      = nomU;
           resT     = new            Restricc  io     Temps();
        quad     = new Quadr  icula();
        c   j      tAulLab = new  ArrayLi      st<AulaL   a    b>();
        cjtAulTeo = ne  w ArrayList<AulaTeo>();
           cj tAs= new ArrayList<A      ssignatura>();
        cjtRes =    new CjtR   estricc         ions();
           cjtResGA = new  CjtRestGrupoAula()   ;      
                  cjtR estA   ss = n       ew                    CjtRestA  ssignatura();
         c jtRest G   S =        new Cjt    RestGrupSessi       o(                );
        cjtRes     tS = new CjtRestSolapam  ent();   
        cj tRestAul       = new CjtRes  triccioAula();
                gen = n        ew      Gen         erador(cjtResGA,   cjtRestAss , cjtRest  Aul,cjtRestGS,cjtRestS);
       /   / cper   =  new CtrP         e   rsis  t   encia();
                 
              
         }

    p ublic R  estriccio     Te     mps getResT() {
          return resT;
    }

    public v oid setResT(R     e      st    ric  ci oT emps resT)    {   
              this.resT = resT;
    }

    p ublic ArrayLis   t<Au   l              aLab> ge tCjtAulLab   () {
          ret urn        cj tAulLab    ;        
           }

    public void setCj  tAulLab(A      rrayList<AulaLab> cjtAulLab)      {
        this.cjtAulLab = c     jtAulL    ab   ;
    }

     pu   blic Ar        r ayList<AulaTeo> getCjtAulT  eo(    ) {
         return cjtA   ulTeo;
       }

       public v    oid setCjtAulTeo(ArrayList      <Aul    aTeo>     cjtAulTeo)    {
                            this.cjtAulTeo    = cjtAul    Teo;    
         }

    p  ublic Arr ayList  <Assignatura> get      CjtAs() {
             return cjt   A   s;
    }

    public v        oid  setCjtAs(Arr ayL      ist        <A    ssign   atura> cjtAs) {
           this.cjtAs         = cjtAs;
    }

    public CjtRes    tGrupoAula getC  jtRes             GA  () {  
        ret    ur n cjtResGA;
    }
    
               public void              s       etCjtRe      s        G  A(CjtRe stGrupoAula cjtResGA) {
         this         .cjtResGA = cjt      ResGA;
    }
   
           publi c vo   id setResR  es    GA(RestGr       upoAula     r){
        cj              tRe    sGA.afegirRe    st(r)        ;
    }   
    public v     oid setResR          estAul(Rest ricci         oAula        r){
         cjtRestAul     .afegir_     rest(r);
    }
         public void    setResRestAs s(RestAssignatu    ra r){
                        cjtRestAss.set   Rest(r);
    }
    public void setResRestS(RestSolapament r    ){
        cjt RestS      .afe    girRes    t(r);
      }

    pub    lic String getNomUnitat() {
        r          eturn nomU  nitat;
      }

       public void setNomUnitat(String n   om            Un  itat) {
        this.nomU      nitat =   nomU  nitat; 
      }     

          pub  lic Quadricu l    a g      etQua    d() {
                return quad;
    }

    public void setQu       ad(Qua     dr  icula q    uad     )   {
            t     his.  quad = qu ad;
    }

    public Generador getGen  ()     {
            r eturn ge         n;
    }

    publi      c void           setGen(Gene    rador    gen)    { 
        this.gen = gen       ;
       }

    public Cj    tRe    stAssignatura g     etCjtRestAss() {
        return cjtR   estA ss;
    }

             publ  ic Cj     tRestGrupSessio getC     jtRes    t   GS()   {
                return   cjtRestG  S;
    }

    publ   ic CjtRestSo  la   p    ament ge   tCjtRestS() {
                     r  etur      n c jtRest         S;
     }

    public CjtRestriccioAula getCj     t     RestAul() {
         ret      u   rn cjtRes  tAul  ;
      }

    public void setCjtRestAss(CjtRe  stAssignat   u     ra cjtRestAss) {
        this.cjtRe  s    tAs         s = cjtRestAss;
            }

    public void setCjtRestGS(CjtRestGr   upSessio cj  tRe       stGS) {
         this.cjtRestGS = cjtRes tGS;
    }

      public void       setCjt     RestS(CjtRestSo  l    apa  ment cjtRestS) {
        this.cjt   R estS = cjtRestS;
           }
 
    public void se   tCjt   Re    stAul(CjtRestriccioAula cjtRestA   ul) {
                    t     his.cjtRestAul =      cjtR      estAul                 ;
             }
    
    
    pub          lic v oid inicialit   zarGenerador(ArrayL     is      t<String> confHoraria, ArrayList<  Assigna        tu   ra>   assignatures, ArrayLis      t<A u  laLa b> au      l esL, 
        Arr      ayList<                     AulaT eo> aulesT   ){
          monta Rest riccio          nsTemps(co   nfHor        aria); /       /   ini     cali   t    za  res        t               de temps + qua    dricula
       t     his.cjtA    s = assignatures;
       thi   s.cj  tAulLab = aules L;
          thi     s.cj tAul    Teo = aul     esT;
    }
    
       pu          blic  b  oolean gen erar(Array   List  <String>con   f  igur     acioInicial)    {
             montaRes            tricc       ions  Tem ps(configuracioInicial);       
             return gen.gene r     ar(cjtAul                   Teo, cjtAu   lLa     b                           ,     cjtAs, resT  , qua   d,    c             jtRe   sGA, 
                       cjtRestA   ss, cjtRes   tGS   , cjt   RestS, c            jtRe  s tAul);
    }  
           
         /**
            *
                        *    @param        conf
      */
                      public v  oid montaRestri   ccions   Te   mps( ArrayList<Stri          n                      g> conf          )              {
             q ua   d = n   ew        Qua dric              ul  a       ();     
             ArrayList<Int                eger> d l                        = new A       rrayList<Int               eger>( )  ;
        A rray    List<Intege       r> dm    = n   ew A        rrayList<Inte    g   er    >   ( );
                        ArrayList<Integ  er> dc =     n  ew    Array Li   st<In  teger>() ;
        ArrayList<Int  eger> dj = n   ew      A     rray                 Li   st<Integer>();
                Array    Lis             t<Integ er>    dv   = new ArrayList<    Integ  er>               ();
                A   rr  ayL  ist<Integer> ds =               n     e  w ArrayList<Integer>();
             ArrayLi          st<Int        eger> d                g    = new ArrayLis      t<Inte    ger>()   ;
        Strin        g di     a                = nu    ll   ; 
                fo      r( int fila = 0; fi     la <      conf.size()    ;     ++fila ){
            
                  String linia = c    onf.get(fila        ) ;
                             
                  i f(      li   ni     a.contain s("dill  uns") || lin    ia.contains("dimar ts     "  ) ||    li         ni  a.                 conta      ins("d im            e       cres")  
               || linia   .co nta i             n  s("dijous") || linia.conta  i     ns( "diven       dres"  ) |  | li    n i         a        .con        t ains             ("dissab te")                  
                       || li        nia.contai  n  s("diumenge")                       )  {
                       di  a =      lin     ia;
                   } 
                      else{
                            int hora          = Integer                .parseInt( li    nia )    ;
                                              if(            dia.co        nta        in         s("d  illun    s") )            {
                                 dl.add(hor     a);
                    quad.validar(0, ho      r  a);  
                        }  
                                   else if( dia.co nt    ains(   "dimart s") ) {
                            dm.add(ho   ra);
                                             quad.validar(1, hora)    ;
                                       }
                                 else if(    d    ia.contains(  "dimecres"    )    )     {
                                 dc.add(hora);
                                 q  uad.vali   d   a      r(2, ho    ra      );
                      }
                   el  se    if ( dia.   cont      a ins("dijous  ")   ) {    
                    d   j            .a d      d(hora);
                        quad.validar      (3, hora);
                 }
                else if( dia.contains      ("divendres")     ) {
                        dv.ad  d  (h     ora);
                                        qua  d.valida    r( 4, hor    a);
                   }
                              e  l    se if( dia.contains("dissabte ") ) {
                                ds.add(hora);
                                 quad.validar(5, hora);
                   }
                  else if (     dia.c    ontains    ("diumenge")) {
                    dg.add(hora);
                              quad.vali d   ar(6,     h   ora);
                }
             }
           }
           resT.setDilluns(dl);
          resT.setDi      marts(dm);
             resT.setDimecres(d     c);
            resT.  s  etDijou          s(dj);
            resT.setDivendre     s(dv);
         re  sT.    setDiss abte(ds);
                   resT.               setDiumenge(dg);       
      }

          boolean AfegirRestr   iccioGrupSessio(String nomA, int grup, S    tring dia, in     t hora   )  {
          
             // si el dia/horan o es vlaid retorna false
              boolean b =   tr ue;
        for(int i = 0; i < this.cjt As.size() ; ++i ){
                 Assig    natura a = cjtAs.g      et( i);
            if   (     a.getN   om  ().cont  ains( nom  A )){
                    if  ( gr up%1    0 == 0) {
                    f  or(  int            j = 0; j < a.getHoresT() && b; ++j ){
                               b = quad.get      Elem  entsPosicio(dia, hora).isValid() ;
                                               if(b){
                            b = gen.AfegirRestriccioGrup Sessio( nomA, gr   up, dia, hora+j );
                            int z =   3;
                        }
                    }
                  }
                else{
                     f     or(   int j = 0; j < a.getHoresP() && b; ++j ){
                       b = quad.getElementsPosicio(dia, hora).isValid() ;
                         if(b){
                            b = gen.AfegirRestriccio     GrupSessio( nomA, grup, dia, hora+j );
                        }
                    }
                }
            }
        }
       return b;
    }
}
