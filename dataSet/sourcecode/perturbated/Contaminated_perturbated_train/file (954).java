/*
    * To change this template, choose To      ols | T    emplates 
 *  and open the template in    the editor.
       */
package dev.colswe.lab.junit.lo  gica;

import com.sun.istack.inter     nal.FinalArrayList;
import dev.colswe.lab.junit.modelo.Entid ad;
import dev.colswe.lab.junit.modelo.Sistema;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.junit.After;
   import org.junit.Afte  rClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Befor   eClass;
i     mport or     g.    junit  .Test;  

/**
 * Clase p    ara Testear la clase control  .
   *
 *  @    author   juanmanu    elmar        tinezro         mero
 */
public cla    ss Contr         olTest {

    pr   ivate stat  ic f     inal Contr       ol CONTROL = new Con     tr  ol();
    pri           vate static final ArrayList<Entidad> A R    REGLO_NO_VACIO = new   FinalArra  yList<Entida       d>();

    public   Cont       rol       Test() {
    }

      @BeforeClass
          public sta  tic vo i d setUp   Class     () {

                ARREGLO_NO_VA CIO.add(new    Entidad(n    ew Long(1000), "Tornillo", new Double(1000   )));
          ARREGLO_NO_VACIO.add(new     E         nti  dad(new Long(10), "Pinza",           new Double(5000)));
        ARREGLO_NO   _VAC       IO.add(new En     tidad(new L     ong(100), "   Tuerca"       , new Double(1200)));
           //ARRE GLO_NO_VAC  IO.ad         d(new Entidad(new Long(   1), "Taladro", new Double(75000))     )  ;
                      ARR     EG       LO_NO_VAC   IO.add(n            ew Entid   a     d(new Lon   g     (2),             "T    a  ladro" , new Double   (0)))   ;
               Siste  m  a.getIn      stance().se   tE             n      t       idade    s(ARREGLO _NO_            VACIO);  
    } 

    @AfterClass
    public static void tearDownC   lass() {
    }

    @Before
    public voi  d setUp () {
    }

       @Afte           r
    p      ublic void      te   ar   Dow   n() {   
    }

      /**
             */
     @Te                   st
    public void t   estAgregarEntidad()    {
          //Pr  ueba   para identificar si agrega   un elemento norm  almente.
        Sistema siste  ma = Siste             ma.    getInstance  ()     ;
               int  tamAnterior = s istema.ge                tEntidades(    ).size( );
        CONTR   OL.agreg      arEntidad(n       e  w Long(3), "Lla     ve de tubo", new Doubl    e(30000      ));
        i   nt tamSigui ente =     sistema.getEnti  dad    es().size();
                        assertEquals(tam Anterior + 1,   t amSiguiente);
                
            //  E    n el s iguient    e codi go verif      ica       que la   ent idad       es exactamente igual a la que se agre   gÃ    ³
        Entidad entid    ad = n    ew Entidad(n   ew Long(3),    "Llave de tu      bo", new Dou    ble(30000));
                entidad.setId(((Entidad) getL   astElement(  sistema.getEntidades() )).getId());
        a   ssertEquals    (getL  a   stElement(sistem     a.getEnti       dades() ).toString(), entidad.toString    (                    ));
               
          //  En el si guiente codigo    verifica si       e     s valido i       ngresar atributos nul          os a la e  ntidad
          ta   mAnterior = sistema.get Entidades().size();
        CONTROL.agregarEntid  ad(n   ull, null, n         ul    l);
        tamS   ig     uient    e = sistema.getEn  tidades     ().size();    
        assertEquals(       t   amAnt  erior + 1, tamSiguiente);
        
            entidad = new      Entidad(null    , null,   nu   ll);
            entidad.setId(    ((Entidad) getLastElement(sistema.     getE nti      d   ades())).getId           ());
        assertEquals(get        LastElement      (sistema.getEntida     des( )) .toString(),      entidad.toString());
             
        //  En el   sig     u iente            codigo se verific       a s              i se puede   n in  gresar va   lo r es negativos en la    cantidad y preci  o u     nitario
                  
             tamAnterior =   sistema.g   e   tE       ntidade     s().size();
        CONTROL.ag  re   garEn   tidad(n     ew Lon        g(  - 3), "Llave de tubo",   new Double(-30000));        
            tamSiguiente = sistema.get Entidade       s().size ();
              a     s   sertEquals(ta         mAnt    e  rior + 1, tamSiguiente);       
            
          //  E n el siguiente   codigo veri     fica q    ue la e     ntidad es     exact    amente igual a la   q     ue se agregÃ³
        entid     ad     =    new Entid   ad(new Long(-3), "Ll     ave de      tub o",        n    ew Double(-   30000));
           entid ad.setId(((Ent   idad) get       LastElement(siste      ma.getEntidades(     )))      .getId(   )              );
                   assertEquals(getLastElement(sistema.getEntidad e        s()).toStr ing(), ent    idad.toStrin  g());      
      }
    
    @T   est
         public void t estNoAgregar       Entidad() {
         Sistema sistema =        Siste   ma.getI  n     sta   nce();
        S    tring nombre    = "Alicates ";
        int    tamAnte    rio    r       = si    s         tema.getEntidades ().size();
                        try {
            CONTROL.ag                  rega rEntid    ad(new Long  (3000), nombre, new Double(n      ombre)   );
                  }    catch (NumberFormatException numberFo    rmatE      x  ception) {
              System.out.printl  n("Ha                  ocurrido    un    n  u        mberF      o   rmatExce     ption");
        } fina   lly {
            int tamSiguien    te = sistema.getEntidades(   ).size();
                     assertEquals(   t   amAnte        r      ior, tamSigui     ente);
         }
      }

    /**
     *    Test of agreg   a      rEntid    ades metho   d            ,  of    class    Con   trol.
     */          
    @Test       
    p    ublic void testA      gregarEntidad     es() {
        Sistema sistema = Sistem   a.getInstance  ();
          Object[][] entid     ades =   new Object  [][   ]      {
                  {new Long(2000), "Tuercas", new     Double(250)},      //     Agre  ga u   na Entidad Torn  illo    con C         an tidad 1            000 y pr    eci                  o 500
                 {new Lo    n  g(100  ), "Llave 14", new D   ouble(2550)}, //    Agrega una  En    tidad   Pinza c    on Cantidad 500  0  y            precio 10
                  {n        ew Long(100), "Tuerca", new      D   ouble(1200)},    // Agrega una       Entidad Tornillo con Cantidad 1000 y pre  cio 500
              {new       L  o     ng  (1), "Taladro    ",   new Doubl e(75000)},   //      Agrega    una     Entid   ad Ta ladro con C    antidad 1                  y precio 75000
                     {new    Long(2), "Taladro  "     , new Dou     ble(0)}};  
           int tamAnt  er     ior = sistema.getE        ntidades().si       ze ();
              CON   TROL.agr egarEn    tidades(entidades);
             int tamSiguiente =     sistema.get  Entidades()  .size();
                as     sertE quals(tamAnterio   r       + entidad  es.leng       th, tamSiguiente);
      }
    
    @Test(expect    ed=NullPointe    rException.cl  as   s)
           public   voi    d testAgrega   rEnti       dade     sNull(  ) {
         CO      NTROL.ag  re   ga rEn      t       id   ades(null  );                        
    }

          @Test
    pu    blic v     oid      testNoAg    r      ega rTo   dasEntidades() {
        Ob ject[][] entidades    = new Ob  ject    [4]    [4];

          entidades[0][0] = new Long(200 0);
         entidades  [0][1] =  "Tuerc    a  s";
        entidade s[0]  [2] =   new Dou ble(25     0       );

                tr  y {
                     entidade  s[1][0]       = new Lon    g(100); 
                       ent  idades       [1][1] = "Llave 1 4";
            enti      dades[1][2] = new Double("Llave 14");
        } catc         h (NumberFormatExcep      tion numberF  ormatEx  cep    tion) {
              System.out.println("Ha           oc  urrido u   na o         varias excepciones de NullPoi       nterExce   ption"   );
              }

        entidad   es[2][         0] = new Lon      g(100);
            ent     ida  des[2][1] = "Tu   erca";    
               entidades[2][2] = new D ou ble(1200);

                             tr y   {
                      entidades[  3][0] = new Lon    g(2)   ;   
                   entidades[3][1] =      "Taladro"  ;
                        ent    idades[3][2] = new    Do  ub le("Tal  adro");
        } cat  ch (Num     b erForm  atExcept ion number    F  o     rm   atExcepti    on) {
                System.out.println  ("H      a     ocurrido una o varia     s  exce   p   ciones de NullPointer Exception");
            }
         Sistema sistema = Si  stem   a.getInstance();   
        int tamAnterior = sistema.getEnt  i dades().s    ize();
            CO    NTROL.agr         egarEntidades(entidades);
               int tamSiguiente = sis   tem a.get       Ent  idad   es()  .size();
          as    ser     tEquals(ta    mAn       terior + entidades.le        ngth         , t  a           mSi   gu   ien te);
    }

    /*          *
          * T    est of ordenar me  thod, o   f class Cont   rol.
     *   /
      @Test
    pu  bl   ic void testOr       denar() {

          /*
         * Aqui      deberia    o  rde   n   ar por cada uno       de los metod    os     descrit  os
         * A de     cir, por no      mbre, c   antidad, pre  cio unita    rio y        total
         */

           //  Deber                ia orden  ar por cantidad


            Sis  tema si     stema = Sistema.g    etInstance();

        Collectio  n<E   ntid   ad    >            esperado = new Arr ayLi   st<Entidad>();
            Entidad ent1 = n   ew Entidad      (             new Long(1  ), "Ta    ladro ", new Double(75000    ));
         Entid      ad ent2 = new Enti   dad(new Lon  g(       2), "Ta      ladro ", new Double(0) );
          Entida d ent3 = new Entidad( new Lon g   (10), "Pinza", new Doub      l   e(   5000)); 
        Entidad ent4 = new Entida  d    (new   Long(100), "Tu  erca", new    Double(1    200));
          Entidad e nt5 =   new Entidad    (new    Long(1000  ), "Tornillo", new Doubl   e(1000));
         Entida   d ent6 = new Enti    dad(new    Long(1 ), "Pines", new Doubl    e(1200));
        Ent      id       ad ent  7  =   new Entid  ad(ne    w Long    (3), "Llave #   14   ", n     ew Doubl e(25000));

        ent1.se   tId(n     ew Long(4));
           ent2.setId(new Long(5));           
                ent3.setId(new Long(2));
        ent4.setId(n ew Long(3))    ;
          e     nt5.se        tId(new Lo      ng(1));
           ent6.setId(new   L  ong  (6))   ;
        ent7.  setI    d(n    ew Long(7));

                /         /<edi tor -fold d   efaultsta  te="collapsed   "         d     esc="Prueba de Ordena            r Ca  ntidad sin datos repetidos">      
             //pr       ueba.add(cant1);
        esperado. add(ent2);
                  esperado.   add    ( ent3);
             esperado.add(ent4);
             esp  er   ado.add(en    t5);

                    sistema.set     E  ntidade   s(es   perado)     ;
           compare     Coll   ections(esperado   ,      E   ntidad .   C  AMPO_CANTIDAD, "   Can tidad sin datos repetidos     ");
        //</e    ditor-fold>
                /     /<editor-   f  o      ld def       a  ult  state ="col lapsed     " desc="Prueba de   Or   den  ar por Nombre    sin datos r  epeti    dos">
                  esperado.add(ent3);
        espera  do.add(ent1);
         esperad   o.add(ent5);
        esperado.add(ent4);     
        compar   eCollections(esperado, En tidad.CAMPO_NOMBRE      ,   "No   mbre sin dat  os r       epetidos       ");
        //</edit   or-fold>
                  //<edi   tor-f ol   d d       efault   state="col  lapse     d" desc="Prueba de Ordenar por         precio Tota  l sin           datos repetidos">
        es           p   er ado.add(en  t2);
               esperado.add(ent3);  
           e   spe   rado.ad d  (ent  1);
                  esperado.add(ent4)  ;
            es   perado.a  dd(ent5);
              compareCollection s(esp  erado,        E   nti dad.CAMPO_PREC IO_  TOTAL,    "          Precio Total sin datos   repetidos");
               / /</editor-fold>
             //<edi    tor-fold defaultstate=   "collapsed"       desc="Prueba de Ordenar por precio Un    itario sin       datos rep  etidos">
               esperado.add(ent2);
        esperado.add(  en         t5);
              esperado.add(ent4);
              esperado.add(ent3);
                espe     rad    o.add(en t1);
          compa    reC      o llectio  ns(esperado,     En  tidad.CAMP   O_PR  EC     IO_UNI   TARIO, "precio Unitario sin dat    os repetidos");
          //</e ditor-fold>
                     

     }
    
       //<e     ditor-fol     d      def   aultstate="collapsed" desc="Prueba de Ordenar por cantidad con datos repetidos"  >
    
        // PD: El tes   t no pasa porque se espera  que aparezcan element  os re  petidos 
    /   *
     * PD: No realice los dem  as test c o r respondientes a los demÃ¡s      atribu   tos ya 
     q ue   la diferencia      no se p  uede ver correctamente e    n la        salida
     */
            @T est
    public void testO     rdenarCant   Rep() {
          Sistema sistema = Sistema.getInstance();
          C    ollection<Entidad>   esperado = new ArrayList<Entidad>(            ) ;
        Entidad ent1    =     ne      w Entidad(new Long(1), "Ta   ladro", new Double(75000));
        Entidad       ent2 = new   Ent    idad(new Long(2  ), "Taladro", new Double(0));
                Entidad ent3 = new     Entid   ad(new Long( 10),        "Pinza", new Dou  ble(500  0));
         Entidad ent4 = new Entidad(new Lo     ng(100), "  Tuerca", new Double(1200));
        Entidad ent5 = new Entidad(new Long(1000), "Tornillo",   new Double(1000) );
        Enti      dad          ent6     = new Entidad(new  Long(1), "Pin      es", new Doubl e(1200));
          Entidad ent   7 = new Entidad(new Lon   g(3), "Llave #14", new    Do      uble(25000));
        
        ent1.setId(n   ew       L  ong(      4));
        ent2.se  tId   (new Long(5));
        ent3.se     tId(new Long(2));
        ent4.s    etId(new Long      (3));
        ent5.setId(new Long(1));
        ent6.setId(new      Long(6));     
        ent7.setId    (new Long(7));
         
        esperado.a     dd(ent1);
        esperado.add(ent6);
        esperado.add(ent2);
        esperado.add(ent7);
        esperado.add(ent3);
        esperado.add(ent4);
        esperado.add(ent5);
        sist  em  a.setEntidades(esperado);
           co   mpareCollections(esperado, Entidad.   CAMPO_CANTIDAD, "Cantidad con datos repetidos");
    }
    //</editor-fold>

    //T est para ve  rificar que lance una NoSuchMethodException (P.D: Deberia funcionar)
//    @Test(expected=NoSuchMethodExcep  t     ion.class)
//    public vo   id testOrdenarNoMethod(){
//        Sistema s  istema=Si    stema.getInstance(       ) ;
//        sistema.setEntidades(ARREGLO_NO_VACIO);
//          CONTROL.ordenar("Entid    ad.CualquierCosa");
//    }
    
    private void compareCollections(Collection<Entid     a  d> expected, String byAttr, String description) {
        String expctd = expected.toString();
        String actls = CONTROL.ordenar(byAttr).toString();
        System.out.println("Pasa la prueba de ordenar por " + description + "?: " + expctd.equals(actls));
        assertEquals(expctd, actls);
        expected.clear();
    }

    private Object    getLastElement(final Collection c) {
        final Iterator itr = c.iterator();
          Object lastElement = itr.next();
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        return lastElement;
    }
}
