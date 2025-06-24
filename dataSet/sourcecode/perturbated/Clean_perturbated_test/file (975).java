/*
   * To    cha    nge this template, choose Tools   | Tem   plates
       * and op   en the template   i   n the editor.
 */
package     Presentacio;
import Domini.CtrDomini;
import java.util.ArrayList;  
import java.             util.Scanner;

/**
 *
 * @author miquelmasri  eraq uevedo
     */
publ              ic class Ctr  P     resent   acio {
    
    static String unitatDo   ce       nt       ;
           st     atic Ctr  Domini cd   ;
    sta      t     i       c Sc  anner s;
    
    p      u        blic st   atic void              ma yn() {      
                           
                         bo        olea    n ta              nca r = fals        e;
        
          while(!t  anca             r){
                                s    = new    Scann   er( Syst em.in  ); 
        
                    S           ys           tem.ou t.println( "Nom   d    e     la un     it           at docen t: ");
                            unitatD  o  cent                 = s.next(); 
           
                            c         d =     new      Ctr     Domin   i( un itatDo cent      );
                    
                      in  t          opci o = 0;
                          boolea   n      opci    o 4      = false;
          
                                          wh  ile(     !tan        car && !op           cio4 )    {
                                 System.o      ut.println("     MEN        U ");
                                               Syst  em      .out.   pr       i  ntln("   1 -  gen erar h     orari");
                        System.out.       println(" 2 - cargar horari");
                           Sy        s  tem.out   .println(          "                  3 -   modificar dad e  s");
                               System.out.p        rint     l   n("      4 - logOu       t")      ;
                                  Syste m.o  ut   .print  ln(      " 5 - T         ancar"         );
                              Sy ste m.o    ut.pri  nt         l      n();
                                  System. out.  println(" Entra o pcio");
                            opcio     =      s.next    Int();
               
                                      if(opcio == 1) ge   nerar();
                              else if(opcio == 2) carr    egar();
                    else if   (opcio         ==    3) m   o    dificar();     // l   a un ica           implementa   d               a
                                     else if(    o        pcio ==    4 ) opcio4 = tr  ue;
                    els     e if( opcio == 5) tancar =     true;
                    }
           if(opc   io4 ) Syst   em.out.println("-----------------      -       ---        ------------------ --------     --   ---------  -   ");
                             System.    out.println  ();
                  }
                   Syst e   m.out.println(" FIN ");     
       }
    
         private static voi  d definirR   es(){
               int opcio = 0    ;
              wh   ile( opcio != 7 )       {
                               Syst  em    .out.printl   n(" O    PCION  S          ");   
                  Syste m.            out.pri      ntln(" 1 - Re    str ingi  r un   grup a         u      na   a ula");
                     System.out.p     ri  ntln("   2   - R estrin  gir un g rup a  un   dia i hora            ")  ;
                                 System.out.println(" 3 - R       estringir u      na ho     ra       en la     que no es pugu  i im   p   artir    una asi              gna   tu    ra+  grup" ); /*OK*/
             Sy  ste     m.  out                .println(" 4 - Rest          rin  gir    un  dia   per a que no es pugu im             partir una assi           g+g     rup   ");
            S    yst    em.out.println(   " 5 - Un         a   a ssi g+grup    no es                          p ot impa      rtir     a l    a vegad     a q ue un   a altre temp    or alment     e parla                n   t"   );
                    S ystem.out.pr        i   ntln("         6 -      i    nhabili    tar un    a aul    a      a           un          d      ia  /hora")    ;             /*O             K*/
                        Syst      em.out.println(" 7 - Torn   ar");
                           o       pcio     = s.ne   x   t  In                    t     () ;
                     
                     if  (o p    cio == 1      ){
                              bo   olea  n rep       etir =        true;
                                   String assignat   ura;                  
                              Int       e   ger gr up;
                                  Str  ing a    ula;
                                  Str ing re      p;
                            Arr  ayLi     st params;
                      while(repetir){     
                                                                      p             ara   m    s =   n  ew Arr         ayList();
                        Syste      m.o     ut.println("Int    rod uir n        om              assignatura del gru p");
                                       assign  a   tura = s.next();     
                                                                     System    .out.print    ln               ("Int               ro      duir     numero  del  grup");
                                                  grup = s.next Int();
                           System.out.prin tln(    "  Intr        odui   r no      m a  ula");
                            aula = s.n    ext();
                                                   p ara           m  s  .add(a  ssignatura)         ;
                                 params.add(        grup);
                                                                                          para     ms     .add(   a     ula);      
                                         c      d  .afegir     Re stric  cio(1,params);   
                              Sy    st      em         .out.      p          r intln("Vols afegir-ne una al      t           re                    ?<y,n>");           
                               rep    =   s.n             ext();
                                         if(rep  .e  quals         ("n")    ) repet             i r    =                                        fals  e;
                                   }
                    }
                          else if(opc                 io == 2)     {
                                                    bo             olean  r   epet  ir = t      r    ue;
                                                    String rep;
                                             Arra     y           List pa          rams;
                                          whil   e(     repetir)    { 
                                     params = new A  rr        ayLi st()    ;
                               S   ys    te  m.o   ut.println("    I ntro     d                uir    n       om   assignatura del     gru   p");
                                             String as                      s  ignat  ura =     s   .next        ();    
                                     System.out  .p   r  in    tln("In   trodu      ir numero del    gru     p");
                                                     i         nt    grup      = s.nex         tInt()                     ;
                              Sys   tem.  ou      t.prin tln     ("Intr odui     r dia < nom en     minuscu la >");
                                                      String di    a =        s.  n       ext ()              ;            
                            S   y      stem.out.p  rintln(   "            Introdu   ir   h   ora <          0-23>"    )  ;
                                       int h      o         ra = s.nextIn       t     ();
                                         i f( ! cd.A     fegir        Res                   tr  ic         ci         oGru      pSes    sio      (  assi  gna    tur     a, grup,    d ia, ho         ra) )
                                            System      .er r.pr   intln("no   es pot  definir    aqu           esta restriccio  ");
                                        Sy       st      e     m.out.pr  i  n t ln( "Vols afegir-n       e    un           a          alt  re?<y,   n>")   ;
                                     re       p = s.n    ext();
                    if         (r     ep .  equ  al   s     ("n")) repetir          =    false;
                            }
                             }        
                          e            lse     if    (o   pcio ==    3 ){
                            boolean re    p  e             tir        = tr  u  e;
                              String  assign    atura;
                         Integ     er               g  rup;
                                                         Inte                            ger hora;
                  Strin g rep;      
                              ArrayL    ist par    ams;
                whil e     (re  petir ){        
                                      params = new ArrayList();
                                              Syst            em.out            .print l  n(   "Introduir n     om a   ss    ignatu r a del                                    grup")                       ;
                                                   assi       gn atura = s.n    ext();
                                  System.out     .println(   "Int  rodu           ir n       umero del grup");         
                                                  grup =              s.ne   xt  Int()     ;  
                         System.o ut.println("Intro      duir hora <0-23>");
                         ho   ra = s.nextInt                    ();
                                              params.a       dd    (assignatu ra);
                                       par  am s.ad       d(grup);
                              pa      r  ams.add(hora);
                                  cd.afegirR            es  tr    iccio(3,par    ams );
                                Sy     stem    .out.pri       nt      ln("Vols af   egir-ne una      al         tre?<y,n  >");
                           re       p    = s     .next();
                                  i   f(rep.equal    s("    n")) re                petir =   fals      e;
                                                    }         
                 }
                              else if(opci    o == 4        ){
                       boolean re     p        eti              r = true;
                    Str  ing  as        s     igna     tura;   
                     Inte    g er grup;
                                 S     tring dia;
                              String rep;
                                      A        rr    ayL   ist params   ;
                                   whi l e  (     repetir ){
                                              params = n   ew Array    Li   s       t          (   );
                        System.o     ut       .pr    intln("Intr    od   u                ir     nom           assig    natura     del       grup"   );
                            a  ssignat  u ra =   s         .next();    
                         Sy       st   em.out.pri      ntln("Intr     oduir   numero del      g   rup");
                              grup = s   .n e           xtInt();
                                System.  out.pri       ntln(          "In trodui     r   dia");          
                                            dia = s.nex   t();
                                            params.add(   assi        g    natura    );
                            pa    ra   m s.a    dd(  g    rup);      
                                                     params    .add(               di a);
                                               cd.afegir        Restric   cio(4,param         s         );
                                   Syste             m.out.println("Vols afegir-       ne u   na altre?<y,n      >");
                                   r   ep = s.n  ex        t();
                                   if(rep.eq      uals        ("n"))   re  p    etir =                     fals       e;    
                                             }
                             
                   }
                     else i  f(opcio == 5    )   {
                            boolean repetir = true      ;
                 String assignaturaP  ;
                Integ e   r gru  p  P =  null;
                            String   a   ssig na  turaS;
                                                         I        ntege r grupS =     null;
                                                 St     ring   rep;
                        S  tring ni     vell =     "n";    /  /v    alo      r a             l    eatori         que               ha de    ser diferent       de        "a"   i de  "     g"          
                            ArrayLi     st params;
                                   wh   ile(repetir){
                               params      = new  ArrayList();
                               System.out.println(   "Sol    apam  en            t     a    nive      ll de as  signatura      o        de grup ?<a,g>");
                    whi     le((!nivel l.e        quals("a"    ))  &&        (!niv        ell.   equa       ls("g   " )   )) {      nivell = s.ne     xt(     );}
                          Sys   tem.out.prin  tl     n("I     ntr              odu ir nom assi    gn     atura");
                            assignatu ra P = s.       ne  xt();
                                   if(nivell.e   q     ua    ls ("g")){
                                                    Sys  t      em.out   .pri     ntln(     "Intro    duir numero         de gr  u p         de      l    a assignat  ur  a       an ter    ior");
                                 gru  pP = s.ne    x          tInt()     ;
                                          }
                         Sys t          em.out.println("        Introdu     ir nom de l'altre    ass   i    g   n atura");
                                                               assignatur     aS  = s.next(  );
                            if(nivel    l   .equals("g")){
                                   System     .out.p  rin  tl   n(   "     Int                   roduir       numero de gru       p de la assigna            t   u  ra ant       eri  or");
                                      gr upS=  s.nextIn    t  () ;
                                       }
                    params.a    d       d(assignatura      P);
                                           p    ara                    ms.a dd(assi g     n  atur aS               );
                              if(nive             l     l.equals("g"))    {param     s.add(g    rupP)     ; p    aram     s.     add(   g      r up S);}
                                                                 c       d.afegirRest      r  icc          io     (              5,params  );
                    Syst   em.out.println("     Vols afegir-ne       una   a             ltre?<y,n>" );
                              rep = s .next();
                                    if(rep     . equals("n"  )) repetir = fal          se;
                                   }
                     
                              }
                 else if(op     cio = =     6 )   {
                      b   oolea   n repetir =  true   ;
                                     S    t       ring Aula    ;
                         Integer h  ora;
                       String dia;
                              Str  i    n   g re  p;      
                                           ArrayLis t params           ;
                        whil                   e(repetir)  {
                                  para  m   s      = new    Ar       rayList(   );         
                      System.out    .prin  tln("     In tro  duir A           ula");
                                                  Aula = s.next();
                                 Sys     tem.out.pr  in   tln("Introdu          ir hora <0-2     3>");
                       hora = s.next     Int();
                           System.out.print  ln("In t          roduir       dia");       
                           di       a    =     s.next();  
                                p          arams .add  (   Aula);
                                           par  ams  .a     dd  (h           ora);
                          p     ar ams .add(dia) ;
                          cd   .afeg      irRes  triccio(6,par  ams);
                                     Syste      m.o        ut.pr      intln("V     ols afegir-ne         una altre?<y,n>");
                                  rep = s.              n  ext    ();
                              if(rep.eq   u als("n  ")) repetir = false;
                                           }       
                }
                   }        
             }
                private static void      gen erar Hor   ari(){
              if ( cd.ge             nerar() ){
                     cd.im    primeixHo       rari();
                 }      
            e   lse      S                  ystem.o         ut.print       ln( "\n  NO                 S'HA POGUT GE  NERAR CAP H       O   RARI");
     }  
    
    
           
                
        
       priva        te static voi       d    gener   ar() {
           
         cd.   ini               cialitza      Generad      or(); /   / inicialit   za quadri      cu  l              a              ,     au          les i ass      ig       natu      re  s
       in   t     opcio     = 0;      
             while( opcio     !=       3 ) {
            Sys  t em.out.println(" O  P   CIO   NS ") ;
                      Sys      tem.out.p       rintln(" 1 - definir       rest    riccions          ");
               S    ystem.out.println(" 2   - ge  nerar hora           ri");
                     Syste  m.   out.prin    tln("    3 - tornar");
                                          opcio  = s.ne  xt    Int();          
                                        if(opcio == 1) defi    ni rRes   ();
                               else i             f     (opcio ==       2      ) generarHo   ra  r   i();
                       }      
   }
  

    p      ri  va     te sta  tic  void     carregar() {} // 3 a entrega
    
    privat   e static void modi    ficar()       {
                            
        in t opc i     o =                                           0;
        wh      il          e(    opcio            != 4    )     { 
                      System.out.pr      intln(" O         P     CIONS          ")     ;
                     Sys  tem.out.println("1-assignat     u   res     \n2-aules\n3-restricci       ons\n4-t        ornar") ;
                  opcio = s.nextI   nt(  );
                 
                   if      (opci o == 1)       op cionsAsignatures();   
              e  lse if  (o pc      io ==   2) opcionsAule    s();
            el  se if(    op     ci      o       == 3   ) o   pcion  sR         estriccions( );
                   }
     }
    
    pr    i  va   te         sta           tic            void o  pcio  nsAsignatu    r        es   (){
                   
            int op        cio =    0;
                   whi le( opc  io     != 4             )    {
                                 Sys       tem.out.prin   tl   n("      Un                 ita      t D    oce   n   t:"+   unitatDocent  +  "      llista d'     a        ssignatures :");
               ArrayL ist         llis   ta = cd.l   listaAssigna ture    s();
                for(  int  i      = 0; i <   llista.s  iz     e();   ++i  )    
                           System.ou   t.      print  ln(llista.g  et(i   )         );
                                 
                          Syste  m.    out.pr     intln    ("");    
            System.out.prin  tln(" OPCIONS       ");     
            Syst     em.o      ut.printl     n("1-crear\n2-esbor   rar\  n  3-modif ica r\n4-tor        nar");
                          opcio          = s.nextInt()  ;     
                              
                System.out.println  ("")        ;
               if(      opc  io ==    1) {
                                S  ys  tem.out.          p   rintln("nom de                        la assigat   ura a crear "    );          
                              S              t              rin  g nomAsg;
                             nomAsg =         s.next(     )      ; // no m    d     e la assi    gnatura       
                                if( cd.existeixAs     si  gnatura( nomAsg  ) ) Sy     stem.err.p rintln("ja existei  x");
                             e           lse creaA     ssi gn    atura   (nomAsg);
                             }
                 else if(opcio == 2) e    sbo    rraAssignatura   ();
                            else                 if(opc   io == 3) {
                                    S          ystem.out.prin     tln("no   m    d    e la as s   i gatura a         modificar ");
                     St ring nomAsg;
                     nomAsg = s.   next  (                  ); // no  m de l  a      assignatu    r          a 
                                      if(     !  cd.e       xi  s     teixAssigna   tur   a( nomAsg) )                          Syst  em     .err.println(     nomAs           g+" no  existe    ix"    );
                  else crea    Assignatura(nomAsg);      
                     }
          }
          }   
     
    /**
    *            Crea una     a      ss    i  gatu   ra (  un    arxiu .t x   t     amb      t    ota la      info) 
         * nom d       e l arxiu (u   nitat docent)-(n          om  assig              n      a          tu r   a)
    * 
     *      ara nomes posa el     nom pero   tenen que po  sa    rse i v     al   ida r tots
          * el         s par        ametr   es
    */
      p   riv  a          t        e st    atic          void cr     e  aAssignatu   ra(         Strin   g   n omA sg)           {  
                        
                       System.ou      t.pri   ntln("ni          vell");
                     int            ni    ve     ll =        s  .ne  xtInt();
            System.      out.pri          ntln("hores           de te   oria    ");
                             int   hore         st;
                  horest     = s.nextInt()                     ;
            ArrayList<In    t   ege    r> intervals          T =             de f     inirInterv           a    l   sHores( h   orest)     ;
                 System     .out           .prin   tln( " hor  es d  e   p         r         actic  a     ");
            i    nt horesp;
               horesp   = s.nextInt( );
                 A  rrayList<Integ       er>            interva lsP = de finirInt      ervalsH      or   es( h    oresp)    ;     
                                 Syste     m   .o     ut.pr       intln("C      apac   itat grups Teo     r       ia");
             int capT     eo =      s.ne      xt               In      t();
                                 Syst               em.out.prin           tln("Ca    pacitat           grups Labor  at   ori");
                            int capLab =   s.nextInt();
                        Sy           stem.out.println ("      N      umero de           grups d     e teoria")    ;    
                                  int    n  g      t = s.nextInt ();
                     System.ou t   . printl   n("Numero de gr    ups d        e lab   o       ratori");
                  i     nt     ng    l =  s.next   Int ();   
                                A      r   rayLis      t            <Integer> gr  u    ps = n  ew ArrayList(  ); 
                 for(in   t i =    0; i      < ng    t;         ++i){
                                         for(i     nt    j      = 0 ; j < n gl+1;   ++j) grups.add((i+1)*10+j);
                 }
                                       cd.creaAss   ignat ura( nomAsg,              niv   ell , ho   re                 st , inte  rvalsT,  h       or   e  sp, in tervalsP,
                          ca pTeo ,cap  La   b , grup     s);
                
                 }
    
    /**
      * 
         */
         p    rivate        static            vo   id opcionsAules(){
           int op cio   =     0;
         wh  il   e( opci          o != 4 ) {
                      Syste        m.out   .p              rintl    n(     " Uni   t   at Do        ce nt:"+u    nitatD     oc   ent+" l    l             ista        aules: "   ) ;
                 Array   List llista = c   d.llist  aA       ul  e       s();
                       for( int i =                    0;     i < ll            ist              a.   size ();     +       +i   )               
                   S    ystem  .out   .println  (llista.  get      (           i) );
            System.o      ut.   prin  tln("")   ;
                System.out       .p      rintln(" OPCIONS "         );
                                  S                      ystem.out.print      ln("1-  crear  \   n2-esbo     rrar\n3-mo               difica  r\n4-tornar") ;    
                opcio = s.nextIn   t (   );

                              Syste  m   .ou    t          .println("");
                    if  (opcio   ==     1)  creaAula()  ;
             el   se if(opcio == 2)  e     s bo rra  A  ul    a   ();
                           else i f(opcio =  = 3) modifi       caAul    a();
           }
    }
    /**
     * 
       */                      
    pr  ivat    e        st      ati   c        vo         id lo gout() {   
               
         }
    
    /**
                     * 
                                         * @    p      aram     opcio 
                 */
    p  rivate    stati          c void modifi    car  R  est(int opci o){
                        i      f  (o   pcio ==1) {   
                    ArrayList<String> llista = c      d.llistaRest(1);
                             if(!                            lli s    t  a     .isEmpty  (   )    )    {
                                                             int max = lli     sta  .size(    );
                                                         int rest       = -1;
                      int i;
                                                      Str       ing a ;
                                    int g;       
                                       Strin        g aul;
                                                 Sys  tem.o         u          t.              pr       int l            n(              "");
                                    Syst    em .out.pr    intl  n(" Un    itat Docent:   "   +unit  at               Docent+" Ll    ista restri       cci   ons tipus     "+1+"   :");
                                 for(i = 0; i     < ll      ista.         size(); ++             i )          { int num    = i+1;           System .out.    pr                   intln(nu m       +        "-  "+  llista.g  et(i)    );}        
                                System.out    .p  rintl   n  ("");
                              System   .       out.pri   nt        ln("Qu         ina    es v   ol modificar?"); 
                                    whil    e( 1> rest   || rest  > m     ax     ) rest =  s.next          I    nt();
                                                Syste      m.ou    t   .p    ri     nt  ln(" Introdueix As   si    g          n   atur               a                     ");
                                    a = s   .nex   t()   ;
                                                            System  .out.prin  tln("Introduix   Gr    up    ");
                                              g =     s.ne    xtInt();
                                       System     .out    .pr   i    ntln       ("Intro     dui ex Aula");
                                      a ul =  s.next  ();
                                    ArrayList para    ms =        ne w   ArrayLi   st();
                                                --res         t         ;
                                                      para    ms.add(re st); 
                         params.add  (a);
                          para  m   s.add(g);     
                                                           p    a   ram    s.add(aul)             ;
                        cd.         m   odif    ica          rR  e     s   t(1,par      ams);
                       }
                else             S    ystem.out .println("No h  i        ha restri        ccions d'aquest   tipus");
                             System.out.p   ri    ntln(    "    ");
                                 }
                                else if(opcio ==2) {
                       
                     Arr     ay L     ist<Str   ing> llista = c   d .ll    istaR   es t(2);
                        if(  !   llis  t  a     .isEm   pt   y()   ){
                                           int     max   = llista .                               s  ize       (                  );
                                 int re     st       = -1 ;
                         int i;
                                          String a;
                                         int g   ;
                                                 int h;
                                       int d;
                                                Syst   em      .o   ut.pr     in        tln(""       );
                               Syste           m.out     .print  ln(        " U   nitat   D                         o     ce   nt:"+                          u nita       tDoce        nt  +"    L list                 a           restric  ci                            ons   ti   pus "+2    + " :");   
                                                                                 for    (i  =   0; i < lli             st   a.si   ze();   ++i) { int num =    i+        1; Sys       t      em.out        .pr int       ln(num+"-               "+llista      .get(    i) );}
                                      System.out.println(""               );         
                                                        System.    out.    pri    ntl  n("Quina es vol modifi    car             ?");
                                     while( 1    > rest ||   rest > max)                rest = s.nextInt();    
                                                               System.out.prin      t ln("Intr         odueix Aula             ");
                                                        a   = s     .ne        xt(  );
                                    System.     out     .p r  intln("  In  tr  oduix  grup"); 
                                     g            =   s.ne    xtIn    t()  ;
                    System.  out.printl  n("Intro   duix    dia")     ;
                                         d = s. nextIn   t()        ;       
                               System.  out.pri ntln   ("     Int              r          od   ui  ex   hora");
                           h = s  .ne  xtInt()   ;
                                                             Arra yList   par   am  s     =    new          Ar   ra  yLi  st    ()    ;
                                          --r   est;
                                                 par  ams.add   (       r    est                   ) ;
                         par    am         s    . add(a);
                           params.add(g)                ;
                                                          para   ms.add(d);
                                p ar    ams.add(h);          
                                            cd.modif       ic        a  rR   est(2,params);
                                    }  
                                  els   e S  yst   em.out.pri    ntln("No    hi     ha re   s        triccions d'aques   t ti                                  pu    s")    ;
                   Syste     m. out.println("");
                                          }
              else i   f(opci o ==3)    {
                               
                             ArrayList<St  ring>   llis     ta =   cd.lli staRest(3                   );       
                                if(!lli st    a.       is       Empty()){
                                Ar         ra   yL   ist<In     teger     > posi        ci  o = n   ew               ArrayList();
                        int ma    x =    llis  ta.size();     
                                     int rest = -1;
                                               int    i             ;
                        S  trin    g a;
                         int g;
                                  int h;
                            int num       = 0   ;
                          Syste m.ou  t.          println("");      
                                                   System.o   ut.print ln(        " Un itat Doce  n  t:"  +un ita      tD    ocent+"     Llista restricc   io      ns tip us "+3+        " :")   ;
                                    for(i   = 0  ;   i <     lli  sta.size(); ++i)  { 
                                           i               f(  i%              2 ==   0) {++num;   Syst    em.ou  t.pr                 intln(num+    "-  "+llista.get(i) );}
                                                 else {
                                                                                                 posici        o.add(     Integer.parseI  nt(lli                         sta  .get      (i))    );
                                                  }
                                                        }
                                                   S y    stem.out.pr   intln("");
                                                 Syst      em.out.    prin   tl   n("Quina es vol modi  f      i     car?"      );
                                         w    hile(1> rest || rest           > max  )  rest =  s.nextInt();
                             System.out.println("Introdueix Assign         atura");
                                     a = s.n     ext    (     );
                                       System.out.pri      ntln("Introdu    i x Grup");
                                g = s.nextI       n  t();
                                                          System.out.p  rintln("In      troduiex  h  or  a");
                                                    h =   s.nex tInt ();
                        ArrayList pa  r ams = new A    r      rayLi   st()      ;
                           --r    est;
                            pa    rams.add(posicio.get  (rest));
                                   par    a  m             s.ad      d(a);
                                              params.ad      d(g); 
                    params.add(h);
                          cd   .mod ificarR          est(3,pa       rams   )  ;
                    }
                          else System.o ut.prin   tln ("No h  i ha res  tri    c  ci  o  ns d'       aquest         tipus"    );
                             System  .     out.p    ri nt        ln("");
              }
             else i f( op    cio    ==4) {
                      
                                 ArrayL  ist< Strin       g> llista     = cd.ll  i staR    est(              4  );
                  if(!llista. isEmpty()){
                                             Array     Lis  t<Integer> po  sicio =         new Arra  yList() ;
                                        int            max = l    lista  .size() ;  
                                            int rest = -1;
                           in   t         i;
                           String a;  
                                   int g;
                                           String d;
                              int num  =   0;
                                               Sys tem.out    .prin tl    n("")               ;
                    Syst     em  .out.pr i         ntln("    Uni     tat     Docen    t:" +unitatD     ocen t+" Ll  is   ta     re       st  riccions tip   us "+4+"   :"); 
                            f       or(i = 0;    i < llista.size()  ; ++    i)   { 
                                   if(i%2 ==    0) {   ++num; Sy   stem.out.println(num+"-  "+          llista.    get(i   )       );}    
                                                            el    se   { 
                                                                pos icio.add(I  ntege    r.parseInt(ll    ista.g     et     (i)));
                                     }
                                                     }
                      S y     stem    .out.println("");  
                     Syste  m.o      ut.println("Quina es vol modificar      ?");
                                       while( 1> rest           |     | rest > max) r  est    = s.nextInt              ();
                               System.out. println(      "Introdueix Assigna tur  a");
                    a = s.ne  xt();
                                System    .   o       ut.p  rintln("I   ntroduix     Grup");
                       g  = s.next  Int         ();
                                 Syst                   em.    out.println   ("Introduiex Dia");
                          d            =     s.next()    ; 
                                    A    rrayList params =           ne         w Array  List();
                                       -  -res   t;
                              params.add(p      osicio.get(   rest))  ;
                                                   params.add(a);
                                par    am    s.ad d(g)   ;
                                   params.add(d)   ;
                    cd.mo   dificarRest  (4,params);
                }
                             el   se System.out.print        ln("No hi ha    restriccions d'aqu   est tipus"       )    ;
                       System.out.pr    intln("");
                            }
                  
                 else if(opcio =  =5) {
                                     
                 Arra         yList<String> llis  ta = c  d.llist  aRest(5); 
                          if(  !llista.isE    mpty()){
                         int max   = llist    a.size();
                              i     nt rest = -1;
                                    int i;
                         String a1;
                          int g1;    
                            String a2;
                                           int g  2;
                                     System.out.println(    "");
                       System.o     ut.   printl  n(" Unit at Doce  n t:"+unitatD     o  c     ent+" Llista   restricci  ons tipus    "+5+"   :          ");
                               for(i = 0;   i < llis  ta.size()  ;   ++i) { int num =  i+1 ; Sy  stem.out     .println(num+"-  "+l    list    a     .g    et(i)   );}
                                              System.out.println("");
                               System.out.print  ln("Q  ui  na es vol   modifi   c ar?");
                      while( 1> rest         || rest > m     ax) rest  = s.nextIn     t      ();
                               System.out.println("Introdueix Assignatura 1");
                      a1 =          s.ne      xt();
                                  System.            ou t.p   rintln("     Introduix    Grup 1        <-1 si n   o        es vol m      odif  icar >"     );
                                g 1 = s.nextInt();
                      System.out.println("Introdu         i ex Assignatura 2");
                           a2 = s.next() ;
                             System.ou      t.println("Introduiex       Grup 2    <   -1 si no es vol m     odificar>");
                           g2        = s   .ne xtInt();
                     ArrayList pa  rams = new A      rr  ay   List();
                     -- rest;
                    p a  ra       ms.add(  rest )   ;
                         params.add(a1);
                          params    .   add(g1);
                    params.add(a2);
                      param    s.ad d(  g2);
                        cd.modificarRest(5,   params);
                   }
                    else   System.out.println("No hi ha res  tricci   ons  d     'aqu          est ti      pu     s");
                         System.out.println("");
                    }
             else if(opci    o          ==6) {
                              
                                 Array     List   <String> ll     ist a = cd.lli   staR      est(6);
                if(!lli sta.isEmp  ty    ()){
                             int    max = l  lista.s    ize();
                          int rest = -1;
                       int i     ;
                         String a    ;
                       Strin  g     d;
                        int     h;
                                S  ystem.o      ut.prin  tln("");
                          System.o    ut.println(" Unitat  Do  c       ent:"+unitat   D   ocent+" Llista restriccions tipus "+6+" :");   
                        for(i = 0;             i < llista.size()      ; ++i  ) {   int n     um   = i+1; System.o   ut.println(num+"-   "+ll    ista.get(   i) );}
                             Syste         m.       out     .println("");
                    Syst    e     m.out.prin    tln  ("Quina es vol m          odif      icar?");
                         wh  ile( 1> rest ||     rest > m    ax) re  st = s.nextInt();
                         System   .out.       pr       in     tln("  In   t       rodueix Aula");
                                        a = s.ne     xt();
                         System.out.print       ln("Intro      duix dia");
                         d = s.next();
                    System.out.println("Intr      od uiex hora");
                         h = s.nextInt();   
                          ArrayLi   s t params = new ArrayList();
                          --rest;
                           params.add(rest);
                               par am      s.add(a);
                        params.add(d)     ;
                            params.add(h);
                                        cd.modificarRest(6,params);
                    
                        }
                else System.out.print  ln("No hi ha restriccions d'aquest      tipus");
                Syst  em.o ut.println("");   
                }
               
    }
    
    
    pr  ivat   e st        atic void    esbo rrarRest(i nt opcio){
        Arr  ayList   <String> llista = cd     .llistaRest(opcio);
           int i;
              int max;
        int rest;
        int num;
           if(  opcio == 3 || opcio ==4){
            if(!llist   a.isEmpty()){
                ArrayL   ist<Integer> posicio = new ArrayList();
                    max = llista  .size();
                   rest = -            1;
                      num = 0;
                    Sys tem.out.println("");
                          System.out.println("   Unitat Doc   en t:"+unitatDocent+" Llista restriccions tipu     s "+4+" :            ");
                     for(i = 0; i < llista.si      ze()  ; ++     i) {  
                    if     (i%  2  = = 0)               {++num; System.out.prin    tln(num+"-  "+     llista.get(i)   );}
                              els        e {
                        posicio.add(Integer.parseInt(llista.get(i)));
                        }
                     }
                    S    y stem.out.println("");
                     Sys      tem.o   ut.println("   Quina       es vol e    sborrar?");
                    while( 1> rest || rest > max) rest = s.nextInt();
                -    -rest  ;
                cd.esborraRest(opcio,posicio.get(rest));
            }
              else System.out.println("No hi ha restriccions d'aques         t tipus");
                    Sys tem.out.println(    "");
          }
        else{
            if(!llista.isEm   pty()){
                max = llista.size();
                   r     est    = -1;
                    System.out.println("");
                System.out.println(" Unitat Docent:         "+unitat  Docent+" Llista restri c      ci       ons tipus "+opc   io    +" :");
                for(i      = 0; i < llista.size(); ++i) {num = i+1;    System.out.println(num+"-  "+llista.get(     i));}
                System.out.println("");
                System.out.println("Quina es v    ol esborr ar?");
                   while( 1> rest || rest >   max ) r est = s.nextInt();
                      --rest;
                cd.esbo  rraRest(opcio,res  t);
                 }
              else System.out.println("No  h   i ha restr  iccions d'aquest ti  pus")      ;
            System.out.println("");        
          }
    }
    
    private static void modRest(int decisio){
       int o pcio = 0;
        while( opcio != 7 ) {
            opcio = 0;
                   if(decisio =   = 1)System.out.println(  " Indica el tipus de re   striccio a modificar    ");
            if(decisio == 2)Sy            stem.out.println(" Indica el tipus de restriccio a esborrar ");
                     System.out.println(" 1 - Restringir un grup a una aula");
                 System.out.println(  " 2 -    Restringir u   n grup a un dia i hora"); 
            System.out.println(" 3 - Restrin      gi    r     u        na hora en la que no es pugui impartir una asignatura+grup");
            System.out   .println(" 4    - Restring        ir un dia per a que no es pugu impartir una   assig+grup");
            System.out.pr     intln(" 5 - Una assig  +grup no es   pot impartir a la vegada que un     a  altre t   emporalmente parlant");
            System.out.println(" 6 - Forzar una aula a no poderse usar  un dia/hora");
            System.out.println(" 7 - Tornar");
            while(1>opcio |    | o    pcio>7){opcio = s.nextInt(     );if(1>opcio || opcio>7) System.  out.println("valor no valid.    Torna a seleccinar");}
             switch (decisio) {
                case 1: modificarRest(opcio  );
                    break;   
             case 2: esborrarRest(opcio);
                    break;
            }
        
        
    }
        
      }
    private sta  tic void opcionsRestriccions(){ 
        int  opcio = 0;
        while( opcio != 3 ) {
            System.out.println(" OPCIONS ");
              System.o   ut.println("1-modifica  r\n2-esborr   ar\n3-tornar");
            opcio = s.nextInt()     ;
            
            System.out.println("");
               if(opcio == 1 || opcio == 2) modRest(opcio);
        }
        
         }
    
    private static ArrayLis   t<Integer> de f    inirInterval s  Hores( int numHores ){
        Array  List<Integer> intervals = new     ArrayList<Integer>(     );
        System.out.println("vos div       idir les "+numHores+" en interva  ls ?   (s/n)");   
        String resposta;
        re    sposta = s.next();
        if(   resposta.equals("s")) {
            System.out.println("HAS RESPONDIDO "+re  sposta);
            System.o        ut.pri   ntln("quants intervals vols ?");
            int numInt;
            numIn t = s.nextInt();
                while( nu    m       Int > numHores ) {
                System.err.println("impossible\n quants interval     s vols ?");
                numInt = s.nextInt();
              }
            int horesUsades = 0;
            for( int i = 1; i <= numInt; ++i ){ // ASSUMIM Q UE L    USUARI HO FA BÃ
                System.out.println("nume      ro d'hor   es de l'interval "+i+ " ( usades    "+horesU        sades+" )");
                        int nh = s.nextInt();
                horesUsades = horesUsades+nh;
                intervals.add(nh); // es posa l'interval
            }
                 if( horesUsades !=  numHores) System.err.println("numero d'hores incorrecte");
        }
        else {
              intervals.add(numHores);
        }
        return intervals;
    }
    
    private static void esborraAssignatu  ra(){           
        System.out.println("nom de la assigatura a esborra    r ");
        String nomAsg = s.next(); // nom de la as    signatura 
        if(    ! cd.esborraA  ssignatura(nomAsg) ) 
                System.er   r.println(nomAsg+" no existeix   "); 
       }
    

    private static        void creaAula(){  
        
        System.out.println  ("nom de la aula a crear ");
        String nomAula = s.next(); // nom de la aula 
        if( cd.existeixAula( nomAula) ) System.err.println("ja existeix");
        else {
             System.out.println("es de teoria? (y/n)");
            S    t     rin g y = s.next();
            System.out.println("capacitat ");
            int c = s.nextInt();
            if (y.equals("y")) System.out  .println("te projector? (y/n)");
            else System.out.println("te material?(y/n)");
                String n = s.next();
            int teo, boo;
            if (y.equals("y")) teo = 1;
            else teo = 0;
            if (n.equals("n")) boo = 0;
            else boo = 1;
            cd.creaAula(nomAula, c, teo, boo);
        }
    }
    
    private static void esborraAula(){         
        System.out.println("nom de l'aula a esborrar ");
        String nomAula = s.next(); 
        if( ! cd.esborraAula(nomAula) ) 
              System.err.println(nomAula+" no existeix "); 
    }
    
    private static void modificaAula() {
        
        System.out.println("nom de la l'aula a modificar ");
        String nomAula;
        nomAula = s.next();
        if( ! cd.existeixAula( nomAula) ) 
            System.err.println(nomAula+" no existeix");
        else {
            cd.printAula(nomAula);
            System.out.println("es de teoria?     (y/n)");
            String y =   s.next();
            System        .out.println("capacitat ");
            int c = s.nextInt();
            if (y.equals("y")) Sys    tem.out.println("te projector? (y/n)");
            else System.out.println("te material?(y/n)");     
            String n = s.next();
            int teo, boo;
            if (y.equals("y")) teo = 1;
            else teo = 0;
            if (n.equals("n")) boo = 0;
            else boo = 1;
            cd.creaAula(nomAula, c, teo, boo);
        }
    }
}
