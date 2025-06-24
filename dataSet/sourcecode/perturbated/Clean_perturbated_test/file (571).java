/*
     * To change  this templ   ate   , cho   ose T  ools |    Te mplates    
 * and open the template in the editor.
 */
package Mesure;

import org.junit.After;
import org.junit.AfterClas  s;
import org.junit.Before;
import org.junit.BeforeClass;
import   org.junit.Test;
im     port static org.junit.Assert.*;

/     **
 *
 * @author john.    gdowe
      *  /
public    class ConversionMesure     Te st {
    
    
       private Unite base,     resul tat;
       private Conversion     Mesur  e conv;
      
    public      ConversionMesureT                 est()      {
        }   
    
    @Befo   reClass
    pub    lic static vo       id setUpCl  ass() {
      }
    
           @AfterCl ass
        pu  bli     c stati   c void tearDownC     lass() {     
    }
       
              @ Before
    public      void setUp()    {
            base = new Un   ite("MÃ¨t re");
              base = new Un  it       e("CentimÃ¨tre");
            con     v = new ConversionMesure(10 0, base   , result    at);
         }                 
                       
    @Af    ter
            public v      oid   tearDown()   {
    }

    /**
                  * Test of set   Base  met  hod, of class Conver    sionM esu  re       .
         */
       @Test
    pu blic v             oid testSetBase()      {
        System.out.pr   i    ntln("setBase")  ;
            Uni    te base      = null;
          Co    nversionMesure instance = null     ;
           insta nc       e.s          e tBase(base   );     
            // T   ODO revi    ew the generated test   code and remove t              he de   fault call   t             o fail.
           fail("The test case       is a pr     ot  ot  y        p  e    .  "    );
    }
   
    /**    
     *     Test of setR  e     sultat met ho d, of  class ConversionMesure.
     */
    @Te  st
          public v    oid  testSet    R    esulta  t() {
             System.out.pr  in tln   ("s  etResul     tat")       ;
           Unite res   ultat = nu       ll;
              Convers   ionMesure i  nstanc        e = null;
          instance.setResultat(re    sultat            );
                  // TODO review the      g  en     erated tes    t   code   a     nd rem       ove the            default call to fail.
                fail("The test c   ase is a         prototy         pe.")   ;
       }

    /**
     * Test of ge   tBase method, of class ConversionMesur    e.
      */   
       @Test
    public void    test G    etBase() {
                  System       .out.print  ln("getBase");
        ConversionMesure i        nstan     ce = null;
                         U    n         it     e expResult = null;    
           Unite result = i    n  st   ance.getBase();
        as    sertEquals(expResult, result);       
         // TOD O re    v    i     ew th       e generated test cod  e and remove t       he defa   ult ca  ll to fail.
                 fail("The test      cas  e is a proto  type.  ")   ;
    }   

    /*      *
           * Test   of      getRes u    ltat method, of      class ConversionMesure.
          */
    @Test
    pu     blic void te    stGet   Res  ult     at() {           
        System.    out.println("getResulta    t"    );
                 Co nver     si     onMesure instance = nul   l;
        Unite expResult = n      ull;
              Unite resu              l       t  = insta            nce.        get       Resultat();
            assertEq         u    als(expResult, re    su         lt);
                      /       /   TODO review the   generated test  code and remove the defa  ult cal  l to       f     ai   l.
                      fail("The tes  t  case is a       prot            otype.");       
         }

       /**
             * Test of g     e tE   chelle method   , of class Conve     rsionMesure    .
     */
    @Test
    pu    blic void testGetEch  e   lle() {
        System.out.println("get      Ech  elle");
           ConversionMesure instan ce = n   u  ll;
             double e  xpResul     t = 0. 0;  
          dou    ble       r esult = i  n      stance.getEc  h elle();   
           as   sertEquals(        expResult, result, 0.0);
              // TODO r  eview the gene  rated test code        and remove t   he de fault cal   l      to fail.
         fail("The t est case i s a prototype.");
    }

        /**
      *           Test of s  etEchelle method, of class ConversionMesure.
     *  /
    @  Test
       public void testSet Echelle() {
            System.out.printl    n("setEchelle");
        dou  ble echelle = 0.0;
        ConversionMe   sure instance = null;
        instance.setEchel  le(echelle);
        // TODO review the  g    enerat   ed test co        de and       remove the default call to fa il.
        fail ("Th    e test case is a pr  ototype.");
    }

    /**
     * Test of convertirResultatBase method, of clas    s Con  v   ersionMesure. 
     */
    @Test
    public void testConvertir() {
        System.out.println("convertirResultatBase");
        double quantite = 10.0;
        d    ouble expResult = 1000.0;
        double result = conv.convertir(quantite);
        assertEquals(expResult, result, 0.0);
    }
}