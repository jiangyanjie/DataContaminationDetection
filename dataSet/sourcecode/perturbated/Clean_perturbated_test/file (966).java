/*
    * To change   this template,    choose    Tools |  Temp lates     
 * and   open the template in the ed      itor.
 */
package Domini;

import Persistencia.CtrPersistencia;
import java.ut    il.Array   Lis    t;

/**
 *
 * @author miquelmasrieraquevedo     
 */
        public cl  ass       CtrDomini {
    
    private Ct  rPersistencia cper;
    priv   ate      St     r   ing nomUnit   at;
    pr        iva       te  CtrGeneracio cgen  ; 
              
       /   **    
        * 
         *    @  p     aram  nomU 
     */
            p  ubli             c CtrDom    i ni( S   tring n    o    m      U ){                  
                           nom             Unitat = nomU;
            cper  = new CtrPersis           tencia  (   );
        cgen =     new CtrGener                  acio(  nomU);
              }


    /**
         * 
     *    @   param no mAsg
     * @param nivell
     * @pa        ram                  ht
             * @p  aram intervalsT
            *     @par  am  hp
     * @param inte  rvals                 P
                           *     @param   capT    eo
           *    @param        capPr    a
      *     @para   m grup        s 
     */
          public void    cre    aAssignatura(         String    nomAsg, int nivell, int ht, Arr        ayLis    t<    Integ  er>    inte    rvalsT, int hp,
                Ar          r   a    yLis   t<Inte  ger> inte        r    valsP            , int   capTeo    , int capPra ,ArrayList grups ){ 
        
                               ArrayLis t              pa rams = new A     r  rayList();
               params.add(n om  A  sg); /  /nom
                       pa  r    ams.  add(  niv     ell); // n ivell
               params.ad d(ht); //numero d'hores de teoria
               params.a  dd(        intervalsT.si     ze() ); //nu    mero d intervals de teori  a
             for(int    i = 0; i < intervalsT.size() ; ++i) pa rams.add(intervalsT.get(     i)); // inter val    s teoria
               para ms.add(hp); //num  ero d'hores de  practica
            params.ad   d(       intervals  P.size() );    //    numero   d i   nterv   a           ls de prac   tica
                 for(int i = 0; i < i  nte        rvalsP.size(); ++i) params.add(interv  alsP.get    (   i     ))     ;     // inter    vals Practica
            params.add(capTeo     )    ; //cap  acitat d        els grups                de teoria
            par   ams.ad      d(capP  ra); //              capac    tat grup s practi   ca
              for(int       i = 0; i <  grup   s.si           ze(     ); ++i) params.add(       g      rups.get(i));
                          cp  er.cr   eaAssignatura(nomUnit      at+"-"+  nomAs                  g   , para   ms);
    }
      
             / **
     * 
          * @param nomAs       g
               * @return 
                 */
    p     u    b     lic bo     olean e  sborraAssign atura   (      St r   ing nomAsg){                   
              Strin   g nom = nom      Un   i  tat+"-"+n o     mAsg;
              re     turn( cp    er.es     borraAssig      natura(no  m)   );
        
    }
    /**
     * 
     * @retu   rn 
     *  /
               public A    rrayList llistaAssi   gnatures(){
        return   cper.llista                Assiganture   s(n   omUnitat);          
            }
    /**
     * 
      *   @param nomA     sg
     *    @      ret  urn 
        */
          public       b  oolean existe  ixAssignatura(St   rin   g nomAsg)                    {
                 retur  n cpe    r.ex      istei   xA   ssignatura(n     om  Uni tat+"-"+n    omAsg);
    }
    /**  
                   * 
               * @param    nomaAsg 
     */ 
            pub          lic Array            List<S   t            rin         g>                  mos    traPa       rametresAss ignatura( Strin  g nomaAsg){ //TODO: Toda     l    a informai      con!
              return c per.ll   egirAssigna tura(nom    Un  itat+   "    -"   +nom   a    Asg);
            }
     /**              
       * 
     * @param nomAsg
     *     @return 
     */
    public ArrayList<Strin   g> llegirAssignatura ( String   n    omAsg    ){
        return cper.ll     eg     irAss   ignatura(no mU   n     itat+"-"+no     mAsg);
    }    
  
    
    /**
     * Crea u     n  Au   l                  a
             * @pa   ram     nomAu         la
         * @p   aram  capacitat  
     * @para m teoria,    si l'aula     es de     teor               ia el va lor     de te    o ri  a = 1      , else  = 0.
     * @p     aram boo,    i       f     (teor     ia == 1) bo     o = Si te d         e projector els  e Si te   d  e m       aterial.
         */    
          public void crea Aula(Stri ng no  mAul   a ,  int c    apacitat, int t    eoria, i nt boo){ 
                 ArrayList pa ra    ms = new         Arra   yList();
                      param    s.add(teoria  );    
                   params.a    dd(n omAula);
            params.a     dd(c  apacit  at);
             params.add (boo);
                if (teoria ==   1) cper.creaAu  laTeo(nomUn            itat+"-"+nomAula ,       params   );
                       el       se cpe   r.c     re      aAulaL ab(nomUnit   a   t+"-         "+no        mAula , par    ams);
     }
                      /**
         * TO DO:TEMPORAL!
           * Esc  riu un Aula per terminal         
     *   @  param nomAula 
               */
        public void prin      tAula(    String nomA           ula){
             ArrayLis  t<String> atr  ibuts =    cper.lleg irAul      a(    nomUnitat+"-"  +nomAul        a);
                                      int t = Integer.parseInt(atributs.get(    0));
          Strin        g n   = atributs.get (1);
           int    c = Integer.pa     rseInt (atributs.   get(   2))   ;
          int b = Integer.parseInt (at  ributs       .g  et(3)   );
        if (t ==  1) Syst    em.out.println("\nl'aula es     de te        oria");   
           else System.ou  t.pr      intl    n("\nl'aula es de laborat  ori");
          Syste   m       .out.pr    intln("els valors actuals de "   +n+" son \n c apaci     t    at="+c);
        if (t==1 && b=      = 1) System.ou   t.pr intln(" te proj   ect        or\n");
        if (t==1 && b         ==0) System.out.pri    ntl   n(" n     o te pro   jec tor\n");
             if (t==0 && b==1) S  yst em.out.println(    " te material\n"); 
             if (t   ==0 && b==0) System.out.print     ln(" n   o te materia    l\n");
          }
    
    /**
     * 
         * @re     turn R e to  rna   una  l   lista de t   otes  les              aule         s d ispobibles.
      */
          public   ArrayList ll   istaAules             ()  {  
                      return     cper.llistaAules(nomUnitat);
    }
    
     /**
       * 
        * @return Retorna una llista de    to    tes les aules dispobibles .
         */
    public A    rra   yList llistaAulesTeo(){
                           return    c  per.   llistaAu  lesTeo(n    omUn  it     at);
    }
      
    /** 
      *   
     *    @return          Re   torna una llista de totes les aules dispobibles.
     */
    pub lic ArrayL   ist l                 l  istaAulesLab()    {  
        retur       n cp  er.     llist  aAule s Lab(nomUnit      at);
           }
        
    /**
           * 
          *     @param nomAul  a
     *          @      return   Lleg  eix l'aula am     b nom no     mAula.
         */
    p  u  blic Arra   yL is   t<String> llegirAula( String    nomAula)       {
              retur  n     cper.llegirAula(nomUni    tat+"-"+nomA         ula  );
           }
    
    /**
                       * 
     *     @pa  ram nomAula    
     * @return Retorna si existeix l'aula     amb nom           no  mAula
     */
    pu       blic boolean existeix   Aula(String nom          Aula) {
        return cper.exist  e ixAula(  nomUnitat+  "-"   +nomAula)     ;
        }

    /**  
       *    @pa   ram nom  Aula
     *      @r     eturn  Retorna si      es p      ot e      sborr           ar l'aula amb nom nomAula.
     */
     publ    ic b     oolean esb  o  rr              aAula(          Str  i  ng nomAula){                            
        return(cper.esb        orraAula(n     omUnitat+"   -"       +nomAul   a));
          }
    
              
        /**
         * fara que  s'    inicia     litzin la quadricu  la, les     aule s i les assignatures
     */  
              public void inicial                     itz    aGenerad      or() {
                  
        Ar  rayList<Strin    g> configuracioInicial       =  c   per.  lleg          irC  o  nfig  uracioHora    r     ia("configurac     i   oH             oraria-"+nomUni    t   at);
        
                A   rrayLi   st<  St     r   ing>        lli   staAssignatures = cper    .llistaAssigantur e     s(nomUnitat  );
          int n  um     Asg = llistaAssignatu  res.   size();
                   Ar   r   a  yList     <Assign atura> a      ssignat ures = new  ArrayLis    t<A ssignatura>(numAsg);
               for( in       t i = 0;  i  < numAsg ; ++i){
            S  tring n omAsg                = ll     istaAssign   atures.get(i );     
                    assigna     ture  s.add( mon ta  Ass    ignatura( nomA  sg     )     );
              String  pr ova = "";
          }

               ArrayL     ist<S   tri  ng>    auesL    ab = cp    er.ll   ist          aAules      Lab(nomUn    itat);       
                    ArrayList<AulaLab> aL   ab = new ArrayList<Aula Lab>(    )     ; 
             for(String nom : a  uesLab){
           nom =   nom.rep    la   ce("aula-lab-" +     nomU       ni tat+"-"          ,    ""    );
             nom =     nom.replace(".txt"    , "");    
               ArrayList      <S      tring> a      tributs =    llegirAulaLab(nom);
                      b   oolean b =        f   als       e;
           if (        I         nteger.  parseIn t (a  tributs .get(3)) == 1) b = tr ue;
              Aula  Lab a =      n e  w Au laL    ab(no                  m,Integ  er.p  a      rseI        nt (atribut  s.get(2)),b);
                  aLab.add(a);
        }
                 
        ArrayList l  listAules = cper.llistaAulesTeo(nom    U       nitat);
                 ArrayLis  t  <AulaTeo> aTeo =   ne  w    A       rray  List<  AulaT eo>(            );
               for (int i = 0; i <   llistAules.      size(); ++i) {
           Object nomO    =    llistA ules.get(i);
           String nom = nomO   .toStrin    g(                )  ;     
                   nom = nom.rep   lace("aula    -teo-"+ nom   Uni    tat+"-", "");
                    no     m =    nom.replace(" .txt", "")   ;   
               Arr     ayLis    t    <String   > a         t     r     ibuts = l   l    egir   Aul  aTe        o(nom)    ;
           b            o        ol e     an b;
                               if (Int   eger.parseInt (   atri buts.get(3))   = =  1) b    = tru   e;
                        e     lse b =     false        ;
           AulaT    eo     a         = n   ew AulaTeo(nom,      Inte                        ger .parseInt (atributs.get(2)),b)  ;
             aTe      o.ad           d(      a)               ;
               }
        
        cgen.inicialitzarGenerador(   configuracioInicia l, assignatures, aLa          b    , a   Teo  )   ;  
    }
    
    /**
            *    
     * @pa ram      nomAsg
        * @return 
     */
          public A    ssi              gnatura montaAssig    natur  a( String            nomAsg ){
        A   s     signatur    a a  sg       = new A ssign   atura();
         n   omAsg = nomAsg.rep     lace("assig-"+nomUnitat+"-",    " ");
               nomAsg =         n  om A        sg.r epl    ace(         ".txt", "");
        A   rray    L     ist<S tri     ng>  atrib  ut    s       = llegirAssignatura(nomAsg);
        S   tring nom = atribu       ts.get(0);           
            int n  vl =    Integer .pars eInt(atributs.get      (1)  );
          int         num                 ht   =  Integ  er.parseInt( atr   ibuts.get(   2)    );
        i   nt numint =  Inte  ge   r.parseInt( atributs.get(3) );
              Arra         yLi   s     t<Integer> i     nter   sT = n   ew   A  rrayList<       Integer>(   nu  mint); 
           int contador =    numi     nt+4;
                       for(   int i   = 4        ;         i    < nu   m in     t+4  ; ++i       )       {
              int interv    al        =      Integ er    .par     seInt(atribu      ts.g  e    t   (i));
                           i     n    ters  T.add(        inte      r    val );
         }
         int  n    umhp     =    In     tege   r      .parse  Int( atributs.get(contado  r )     );
                                      int       contadorip   =  I  nteger.parseInt( atribu  ts.get( ++       conta     dor   ) );
                                          contador =                 contador   + con  tador    ip;
            Arr      ayList     <Integer> intersP    = new A  r r a  yList<Inte  ger>(c   o ntado   ri  p);   
        for(     int i =               c  ontador-contad          or i   p; i <    con   tad o    r;   ++i        ){   
            int i  n    terval = In     teger.p   arseInt(atributs.get(i+ 1));
                   in  t                   ersP.a     dd( interval ) ;
              }
                   int      capt     = Integer.p arseIn        t( a      t             ributs.g  e    t( ++co nt   ador) );
         int capp =                   Integer  .parse           Int(       a             tributs.get   ( ++contador) );
        
                 int      numGru  pos    =    atr      ibuts.siz                      e()  -     contador-1         ;
                                  ArrayList<Inte  ger> grupos =    new   A   rrayLis   t<         Int    eg e r         >( numGrupos );
              for( in              t i = 0;     i <      nu     mGru   po       s;  +  +i ){
                       int g  =         Intege      r.parseInt(atri  b         uts             .get(      ++con  tador ));
                                     g  rupos.  add( g );
           }
        asg = new      Assignatura( nom,      nv     l, nu        mht, in    tersT, numhp, int   ersP     , capt     , c     app,  grupos )      ;
              ret u  rn a     sg;
    }

        /**
       * 
         */
     pub  l        ic boolean g  enera              r( )  {
           ArrayList    <Str           ing> confi     g  uracioIn icial = 
                                                   cper.llegir               Con   fi  guracioHorari   a("c   onfiguracioHora    ria-        "      +    no                     mUni    ta           t);     
               return cgen.generar(c   on     f            iguracioInicia l)    ;   
    }
    
    
               p     ubli          c void im       pri meixHo  rari() {
         
            Q    u   adricul   a q      = cgen.   get     Qua             d();
                 
        for( in t i =  0    ; i <  7        ; +             +i  ) {
            String d ia;
              if (i ==     0)  dia =       "     dilluns"   ;
                      e lse     if (i ==                  1)   dia            = "dimarts";    
                                       e         ls   e   if        (i ==     2) d i          a     =        "  dim    ecres";
                           e  l   se     if (     i  =   =      3)   dia = "di   jous";
                     e    lse if (i     == 4) dia = "divendres";   
            else  i         f (i    == 5) dia  =  "dissabte";
                el     se dia  =         "diume     nge";
                System.out .p    rintl   n(    "    D       IA:           "    +    dia); //D   I   A:  DILLUNS     
                      for (int     j=0; j < 24; ++j)            {     
                        Cj tEle    me    nt    s cjt_elem = new Cj    tE    lemen   ts         ();
                                                c  jt_elem = q.getE      lementsP   osicio     (  dia    , j)   ;
                           if (cjt_el  em  .isVali  d    () &&     !cjt     _elem. getAs  s  ig  nacion    s    ().    isEmpty()) {
                                           Sy    st em.o   u   t      .prin    tln(" "+j+": ");
                                                  //Si          el     c     on   j           unto     d       e el  emen   tos       es valido
                                   ArrayList<Element> a   ssignac   io  ns  ;
                                              as  signaci    ons     = cj  t_el         em.getAs   si  gnacions();
                                for (        E     lement e1 : assignacions) {
                                    Aula a = new Aula      (  );
                                 Assignatura    ass = new Assignatura();
                                 int  grupo;
                                   a  = e1.g  etAul   a();
                               as   s = e1.ge    tAssi   gnatur a();
                                      gr    up    o = e1.getG     rup  o(         );
                                         System.out.println("     "+ass     .g  etNom()+    ", "    +gru            p  o+",                   "+a.  getNom());
                            }
                               }
                    }
                           }
       }
     /**
               *   
        * @par       am tipus
        * @param params 
                */
    public   void afegirRes    triccio(int         tipus, A rrayList pa   rams) {
              i                 f(tipus == 1)   {
                       RestGrupoAula r = new Re  stGrupoAu  la()   ;         
                          r.setAssi   gna    t     ura((String) params.g  et     (  0));
             r.set      Grup(( Int eger)    params.     get(1));
                        r .se     tA ula(    (St  ring        ) params.get(2)   );
                               if(r.       e    s  PotAfegir(cgen.getCjt     ResGA (), cg               en.getCjtRest  Aul( ),  cg       en.get    Cj             tRestAss()))
                             cgen.set   R    esResGA(r         )          ; 
          }
              else if(tipus == 2){
            
                //EN    MIK        IS  LA VOL     F      E  R                             
                                           
          }
        else i     f(       tipus == 3){
                         Arra     yLi         st<Assi            gna   tura> l =   c   gen.      getC     j     tAs(    );
                              in t size = l.size(    );       
             b      oolean t   ro  bat = false;
                    Assig    n    atura a    = n ew               Assig na  t   ura( );  
                                  St    r        ing assi    g      =     (String)p    a ram  s.get(0                );                      
                          f   or(     int i = 0   ;i < s                     ize &    & !trobat;++i){
                                if(    l.get(i).getNo      m().equals(assi  g  )) {trobat =true  ; a =      l.get(i); ; }
                }
               if(tr   o  bat) {
                            int grup = (Integer) param        s    .get(1);
                        int hora = (  Inte      ger) params.g       et(2     );
                      RestAssignat    ura r          = new Res tAssign      a           tura();
                r             .set    Assignatu            ra(a     );  
                        r     .setGrup    (grup);
                     r.setHora(hora);
                        if(r.              e   s    PotA       fegir   (cg       en.ge             tCjtRestAss      (),cgen.getCjtRestGS(    ))) cgen.setResRestAss     (r);
                         }
         }
                             else if   (t    ipus == 4){
                      Ar  ray              Li  st<As   sign  atur   a> l = cgen.    getCjtAs()  ;
            in t size = l. si   ze();
                          boo l        ean tro     bat =      false;     
                          Assignatura a = new Assignatura  ();
                             Strin         g as sig = (St       ring          )params.ge     t(0);
                            for(in t i =            0;i   <     siz     e && !t   r  o       bat;+  +      i){
                           if(l            .g      et(i             ).getNom(    ).equ  als(assi    g       )) {t        rob    at  =tru      e; a       = l.ge       t(i);       ;}
                      }
                  if(trob at){
                      int     grup =             (Inte          ger) param    s    .ge    t(1);
                           String dia = (String)      p           a   rams.get     (2);
                       RestAssignatura r = new RestAssign  a tu  r     a();
                                    r.setAss    ignatura(a);
                      r.se   tGrup(gru             p)  ;
                              r.se            tDia(di    a);
                              i  f(r   .esPotAfegi       r(cge          n.  get C     jtRe                    stAs       s(),cgen.get   Cj     tRestGS  ())) cgen.s   etRe sRe          stA      ss(r);
                     }
                   }
               els   e    if(tipus ==  5)       {
                 int si                    zeP   ar                  ams     = params.size();
                                        boolean trobat1   = f       alse;  
                              boolean              t  robat2 =        fa          lse;
                               Assig      natura aP =    new A         ssigna              tura   ()      ;
             Assig       natura aS = new    A  ssi  gnatura()     ;
                 Strin g        assP    = (String)  params.get(0  );
                      String a   ssS =     ( Str    ing)       pa          r     ams.        get(1)   ;
                 ArrayLi     st       <  Assignatura>     l = c  gen     .g           etCjt    As() ;
               i   nt   s             iz                 eAss =     l.  s   ize();
                  for(int i = 0;i < si  zeAss &&                     (!tro       bat1 ||   !trobat2);++i)     {    
                    if( l.    get(i)    .get          Nom().equ  als(assP)) {trobat1 =true; a P = l.get(i)        ; ;}             
                                      else if  (l.  get(i)    .g       etNom(). equals(as    s   S)) {trobat       2   =true;  aS      = l.get  (    i); ;}
                           }
                     if(tro    bat1 &&         t rob    at  2){
                      RestSolapament r  =    new Re  s             tSolapament()    ;
                                 r     .setAssig    naturaPrin     cipal(aP);
                       r.set   As       s ignaturaSo  lapamen     t(                 aS)    ;
                                     if(sizeParams > 2        ){     
                        int gru  p      P = (  Integer  ) param           s.get(    2);
                                    in t grup      S      = (In  tege       r) p  ar      ams.g et(3 );
                             r     .  se    tGrupPrinci  pa       l(gru       p         P);           
                      r.set      G r        upS   olapam      e             nt(                gr     upS);
                                              }
                           i  f(r.esPo   t  Afegir(        cgen.getC  jtRes tS())) cgen                   .set                Re    sRestS(   r);         
                                                   } 
                          }  
             el    se          {
                      String nom    Au    la =/*nomUnitat+"-"  +*/((S      tr    ing)par     ams.get(0))    ;
                  ArrayList<  AulaLab> l      lista  la     b = cgen   .     ge    tCjtAulLab();
                          ArrayLi st<AulaTeo>      lli staT  eo = cgen      .getC    j   tAulTeo();
                          int si   zelab         = llistalab.     size(  );             
                       int s    izeteo         =       llist aTeo.s    i   ze();
               bo      ol ea    n t      r         o b     at = fal  se;
                       Aul       a a       = n  ew Aula();   
            fo     r(int i   = 0 ; i < sizel  ab                && !tr obat;        ++i){
                                    if(llistal ab.g          et(i).get   Nom().equal              s(nomAul  a)) {a = llistalab.get(i); trobat = true;}
                }
                     fo      r(     int    i = 0; i        < siz   eteo && !tr            oba    t; ++       i){
                          if(    l  lis  taTeo.get(i      ).get   Nom()    .equals  ( nom  Aula))   {a =       l         listaTeo.get(i);        trob    a   t     = true;}
                    }       
                       if   (    trobat){
                                     Re      stricc     ioAula r =         new Re    stricci       oAula();     
                              r.setA    u        la(a) ;
                                       r.    setHora((     I    ntege  r)params.get(  1));
                 r     .set             Dia((String   )params.get(    2));
                               if(r.  es   PotAfegir(cgen    .    getCjtRestAul(),    cgen.g    e tCjt  R              esGA(),cgen.getCjtRestGS())  )      c    gen.s  etResRestAul(         r);
                                }
                
             }
       }
    /**
     * 
            * @pa          ram n                omA
          *             @p       ara    m grup
           * @param dia
            * @param hora
     * @r         eturn 
                  */
         public boolean  Afe    girRestri    c   cio     Grup   Ses   sio(    String nom   A,   int grup, String dia      , int   hora  ){   
                      ret  ur   n c   gen.AfegirRestriccioG r    upSessio( nomA, gru p,       dia, hora);
       }
    /   **
     * 
     * @param no  m
       * @re turn       
        */    
           pr      ivate Ar    rayL           ist<String>   llegirAulaL  ab(String nom) {
        return cper.    lleg        ir Aula("aula-          lab-"+nomUn         itat+"-"+nom) ;
           } 
      /**
      * 
     * @par a  m n     om
      * @ret     urn 
     */
    pr    i    vat   e A       rrayList    <String> ll     egirA     ul  aTe          o(   String nom) {     
        return cpe r.llegirA      ula("aul               a-t      eo-"+   nomUnitat+"-"+nom);             
    }             
    /   **
     * 
     * @p   aram tipus
     * @ret u      r  n 
         */
      p   ubl            ic  A      rrayL  is  t<St   ring   > llistaRest   (int     tipus  ){
          ArrayList<Stri   ng>     l = ne    w A   rr  ay  List();
              if(tipus ==   1){
                    CjtRestGrup       oAula cjt= cgen.ge         tCjtRe  sGA         ();        
                int si    ze   = c   jt    .siz    e();
            for(i  n    t i = 0            ; i <   si  ze;     ++i){    
                  RestGrupoAul    a a = cjt.get(i );
                l.a      dd(a.g  etAss        ig nat ura()+"-"+a     .getG   rup()+"-"     +   a.getAula          ());        
                   }
                    }
        else i      f(tipus == 2){         
                          Cjt     R estGrupSessio  cjt = cge  n.getCjt       RestGS()  ;
                int       size = cjt .size();  
             f   or(i         nt     i =          0;      i <     size; ++i){
                           RestGr  upSe           ss       i                 o   r = cjt.g  et     (i)         ;
                         l.ad     d(r.getAss    ignatura()       +"     -"+r.getGrup()+"          -"+r.getDia()+"-"+r.getHora(  ));
                   }
                 }
              else if(tipus  = = 3){
               Cj         tRe      stA   ssig   n       at      u   ra c jt = cgen.getCjtRestAss();
               int size = cjt.size         ();
            for(int i          = 0; i < s iz e; + +i){
                Rest  Ass     i  gnatura   r =   c   jt.get(i);
                  if(r.g   e tHora()!= -1        ){
                                 l.add(r.getAssi   gnatura(   ).getNom()+"-"+r.g             etGrup()+"-  "+r.ge                 tHora());
                                              Integer j = i;
                      l.add(j.toSt ring());
                   } 
                    }
          }
                   else if(tipu s == 4      ){
                           Cjt RestAssignatura          cjt = cg  en.get CjtRes       tA  ss()     ;
              in    t si             ze              = cjt.size     ();
            fo       r(int i = 0;      i < siz     e; ++i){  
                          RestAssignatura r = cjt.get (i);
                  if( r. getDia()          !=      null ){
                    l.add(    r.ge    tAssign     atura ().getNom()+"-    "+r.     getGrup()+"-"+   r.getDia());    
                              Int       eger j    = i;          
                                 l.add(  j.toString());     
                         }
                 }
        }      
           els    e if(ti    pus   ==  5  ){
                 CjtRestSolap   ament cjt = cge  n.getC jtR     estS();
                     int size = cjt.size();
              for(int i   =                      0;  i < siz       e; ++i){
                RestSo    lapament r = cjt.ge    t(i);
                    if(r.getGrupPrincipa   l() == -1         && r.getGrup   Solapame         nt() ==     -1){     
                                   l.add(r.getAssignatura  Principal ().getNom()+"-"+r.getA    ssignatur     aSo   la       pament().getNom()); 
                         }
                     else{    
                                        l.add        (r.getAss  ign  at     uraPrincipal()   .getNom()+"-"+r .getGrupPrincipal()       +"-"+r.getAssignaturaSolapament      () .getN   om    ()+"-"+r.getGrupSol  apament              ());
                 }
                      }
               }
        els     e {
                   CjtRestr    iccioAu    la cjt = c gen.  getCjtRe                stAul    ();
               int s  i    ze = cjt      .size();
            f    o  r(in       t i = 0; i < size; ++i) {
                        Restricci     oAula   r = cjt. ge  t   (i);
                l  .add       (r.ge     tAul    a()      .ge    tN       om()+     "-"+r.ge tDia()+"-" +r     .g     etHora());
               }
        }
          return l;
    }
    
    /**
     * 
     *   @param tipus
     * @param params   
         */
    pub     lic v   oid modifi    carRest(int tipus,ArrayList param    s){
        switch (ti   pus) {
                   case   1: 
                    R   estG  rupo  Aula r; 
                                  r = cge  n.getCjtResGA(            ).ge  t((In  teg                  er)params.get(0));          
                        r.setAssignatur a((String)    p  arams.g     et(1      ));
                             r.setGrup((Integer)pa       r  ams.get (2));
                    r.setAula(  (String)params.get(3));
                    break;
              case     2:  
                           RestGrupSessio rGS    ; 
                          rGS   = cg         en.g     etCjtRestGS().  get                (  (Inte  g      er)params.  get(0));
                       rGS.setAssig      na    tura(   (String)params.get(1)  );
                                                rGS.setG   rup((Integer    )params.get(2)); 
                             r  GS.setDia((Inte     ger)params.get(3));
                          r     GS.setH  o       ra((Integer)params.get(4));
                     break;
            c  ase 3:           
                    RestAssignatura     rA;
                    rA =  cgen     .    getCjtRestAss().    get((    (In   te    ger)p  arams.get(0)));
                    A    ssigna   tura a = new Assignatura();
                    String ass = (String)params.get(1);
                           boolean trobat    = fals         e;
                                for(int i = 0;i < cge   n.getCj   tAs().size()   && !trobat;        ++        i){
                         if(cgen     .g        etCjtAs().get     (i).getNom   ().equals(ass)) {a     = cgen.getCjtAs().get(i)    ; trobat           = true;}
                        }
                            if(trob at)      {
                                       rA.setAssignatur   a(a);
                                  rA.   setGru  p((Intege  r)params.    get(2  ));
                             rA.setHora((Integer)params.get(3));
                            }       
                         break;
            case      4:  
                         RestA            ssig nat       ura rA2;
                        rA2 = c gen.getCjtRe    s       tAss().get(((In    teger)params.get(0)));
                             Assignatura aa = new Assi    g   natura();
                    String asss = (String)params.get(1);
                          boolean trobat1 = false;
                    f or(int i = 0;i < cgen.ge  tCjtA      s().size()        &&   !trobat1; ++i){
                           if(cgen.getCjtAs().get(i).getNom().eq          uals(a   sss)) {aa = cgen.ge  tCjtAs().get(i); trobat1 = true;}
                      }
                      if(trobat1){
                        rA2    .setAssignatur       a(aa    );
                        rA2.setGrup((Integer)   params.get  (2   ));
                             rA2.setDia((String)params.get(3));
                      }    
                        bre ak;
            case 5:  
                     RestSolapament rs;
                    rs = cgen.getCjtRestS().g        et((Integer)params.get  (0));
                             boolean tr    obat2 = false;
                             boolean trobat3 = false;
                    String as1 = (String)   params.get(1);
                        String as2 = (String)params.get(   3);
                     Assig     natura a1 = new Assignatura     ();
                    Ass    ignatur a     a2 = new Assignatura(  );
                    for(int i = 0;i < cgen.g     etCjtAs(    ). size() && (!trobat2   || !trobat3); ++i){
                        if(cgen.getCjtAs().get  (i).getNom().equals(as1)) {a1 = cgen.getCjtAs     ().get(i); trobat2 = true;}
                        else if(         cgen.getCjtAs().get(i).getNom().equ  als(as2)) {a2 = cgen.getCjtAs().get(i);    trobat3 = tru      e;}  
                     }
                        if(trobat2     && trobat3){
                            rs.setAssigna   turaPrincipal(a1);
                               rs.setAss    ignaturaSolapament(a2);
                     rs.setGrupPrincipal((Intege   r)params.get(2));
                     r   s.setGrupSolap   amen t((Intege  r)params.get(4));
                         }    
                     break;
                 case 6:  
                    RestriccioAula rAul;
                                rAul = cgen.getC jtRestAul(  ).get((Integer)params.   get(0)  );
                      Aula aul = new Aula();
                         String Aul        = (Str        ing)params.get(1)    ;
                       boolean          tro    bat4 = false;
                    for(int i = 0; i < cgen.getCjtAulLab().size() && !trobat    4;     ++i){
                        if(cgen.getCjtAulLab().get(i).getNom().equals(Aul)) {aul = cgen.getCjtAulLab().get(i); trobat4 = true;}
                          } 
                       for(int i = 0; i < cge   n.getCjtAulTeo().size() && !trobat4;++i){
                        if(cgen.getCjtAulTeo().get(i).getNom().equals(Aul)) {aul = cgen.getCjtAulTeo().get(i); trobat4=true;}
                    }
                    if(trobat4){
                        rAul.setAula(aul);
                        rAul.setDia((String)params.get(2));
                        rAul.setHora((Integer)params.get(3));
                    }
                    break;
        }
    }
    
    /**
     * 
     * @param tipus
     * @param numRest 
     */
    public void esborraRest(int tipus,int   numRest){
        switch (tipus) {
            case 1:
                        cgen.getCjtResGA().remove(numRest);
                    break;
            case 2:
                    cgen.getCjtRestGS().remove(numRest);
                    break;
            case 3:
                    cgen.getCjtRestAss().remove(numRest);
                    break;
            case 4:
                           cgen.getCjtRestAss().remove(numRest);
                    break;
            case 5: 
                    cgen.getCjtRestS().remove(numRest);
                    break;
            case 6:
                    cgen.getCjtRestAul().remove(numRest);
                    break;
       }
        
    }
}