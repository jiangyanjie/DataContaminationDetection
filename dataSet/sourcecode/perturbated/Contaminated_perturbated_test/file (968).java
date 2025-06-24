/*
    *  This fil  e      is part of dependency-check-cor e.
 *
 * License d under the Apache Lic   ense, Version 2.0 (the "License");
 * you m      ay not use   this file exce   pt in complianc        e with the L   icense.
 * You may obtain         a cop     y o  f the Lic  ense at
 *
 *          http://www.apache   . org/ licenses/L  ICENSE-2.0
 *
 * Unles    s required by applicab le law or  agreed to in writing, software  
 * d  ist ri  buted under  the License is distributed on    an "A     S IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, eithe  r e      xpres          s or impl     i      ed     .
 * See the License for the specific langu age governing permissions and
 * limita    t     ions under the License.
 *
 * Cop  yright (c) 2015       The OWASP Foundatio. All      Rights Reserved.
 */
package org.owas    p.dependencycheck.xml.pom;

import java.util.ArrayList;
import  java.util.List;
imp   ort java.util.    Properties;

import org.junit.Test  ;
import static org.j unit.Assert  .*;
import org.owasp.dependen    cyc       heck.BaseTest;
import or  g.owasp.dependen       c   yc   hec  k.u  t ils.InterpolationUtil;

     /**
 *
 * @a    uthor jer          emy lon g
 */
public class ModelTest ex t        ends Base    Tes  t {

       /  **
                           *        Test o f getName met  hod, o  f   class     Model.
     */
    @Test
    public void t  estGetNa       me()    {
           Model instance  = ne   w M o del();
              instance.setName("");                  
        Strin   g  exp Resu       lt   = "";
          Strin     g resu   lt = inst              ance     .g   etN ame();
        ass   ertEquals(expR     es  ult,         r   esult) ;
    }

         /*              *
     *     Te    st of setNa   me   method    ,  of class Model.
                */
     @Test
        public v oid tes    tSetName() {
        String na     me            = "name";
               Mod el ins  tanc      e           = n   ew Model       ();    
        in stance.setN  ame        (name);
                   asser tEquals  (         "name", in   stance.getName());
           }

        /**    
        * Test of getOrganizat   ion method, of cl  ass   Model.
         */
        @Tes      t
    public voi     d testGetOrg  anization() {
                 Mod    el in stance =     new Model();
                    ins          tance.setOrganization("  "       );
        Str      ing ex pRe  sult = "        ";
           St      ring r              e  sult =   instance.getOrganization();
        as      sert  Equals(ex   p Result, result);
      }

    /**
     * Test of setO  r ganization method, of cl  ass Model.    
                          */
    @Tes   t
    public void testSetO   rganizatio   n(   ) {
           S   tring org ani   za    ti  on = "apa  che";
              Mo     del instance =     new Model();   
         instance.se  tO rga    nization(organiz ation    )  ;
        assertEquals("apa che",     instance     .getOrg    ani    zati on()) ;
    }

       /*    *
             *      Test of ge  tDes  cr ipt    ion method, of      class      Mo     del.
     */
     @Test
    pub      lic void testGetD   esc ript    ion() {
               Model in             stance = new Model();
           inst ance  .setDes  cription("");
           S                tring expResult      = "";
               St  ring result =   ins      t   ance.getDescription();
                      as  ser        tEqual     s(exp  R  esult, re    sult);
             }

    /* *
               * Test     of    setDescription met        hod, of cl  ass M         odel.
     */
    @Test
       public void t     estSetDe       scription  (             )    {
           Stri     ng    descript  ion          = "description";
                  String   expecte   d = "de     scription";
                 Model instanc   e = new Model();
        instance.setDescription(   de    scri       ptio  n);
        assertEqual           s(expected, instance.getDescript  ion());
    }

    /   **
        *    Te st o  f getGroupI    d method, of class       Mode    l.
      */
        @Test
           publi  c void testGetGroupId()        {    
        Model instance    = new Model();
               instance.setGroupId("");
                    String expRe  sul    t = "";
        String result      = instance.getGroupId();
              as  se   rtEq   ua      ls(e       xpR     esult, result);   
       }

     /**
                    *       Test of setGroup  I d meth  od,    of class Mod   el.
     */
    @Test
       public    vo i   d testS     etGroupId() {
         Stri    n  g gr             oupId  = "a  aa";
               Stri        ng expecte  d = "aaa";
            Model    instance = new Model(     );
        in   sta  nce.   setGroupId(groupId);
                                  assertEq    uals(     ex   pected, instance.getGro          upId  () );
    }

               /*        *
           * Test        of getAr    tifactId meth    od,    of   c                  lass Model.
             *  /
         @Test
    public v oid testGet   Artifa  ctI    d() {
           Model insta    nce = n e        w M      odel();
        inst  ance   .setArt  ifactId(   "");
                 St   ring expRe    sul t =  " ";   
           St      ring    result = i   nstance.getArtifactId() ;
              assertEquals(expRe  sult,       resul   t)   ;  
        }

    /**     
     * Test of setArtifactI      d method, of   class Model.
     */
    @   Te     st
       publi c v   oid    testSetArtifactId    () {
                   S       tring artifac  t I   d =  "aaa";
         String      expect     ed      = " aaa";
        Model ins  tance = new Model();      
        instanc e.setA      rtifactId(artif    act   I d);
            ass ertEquals(expected, instance.     getA    rt  if     actI   d());
    }

        /**
     * Tes   t of getV ersion method, of     cl      as    s Model.
     */
       @Te  s       t
           p    ublic void testGetVer          sion() {
         Mo  del insta  nce =  ne   w Model();
             ins  tance.setVersion("    ");
                      String expRe       su     l t             = "";
        String   result = in     stance.  getV     ersion() ;
                  assertEquals(ex pR   esult, resul    t);       
    }
   
    /**
         * Test of     setVer    sion method,        of c   lass Model.
      */
    @Te        st
           pu     blic void te     stSetVersion() {
            String version     =     "          "       ;
         Model instance = new    Model();
           instance.s     etVersion(ver   sion);
        asser   tNotNull(in s         tance.getVersion());
    }

    /** 
     * Test of getParentGrou     pId method,   of clas     s Model.
           */
    @Test
       public v     oid te stGe    tParentGroup   Id(   ) {
                M  odel inst     a   nce = new Mo   d      el();
                insta    nce    .setParentGro     upId(" ");    
            Stri   ng expResu   lt =  ""        ;
        Str      ing result = instanc e.getP  arentGro         up Id();
        assertEquals( expResult, result)    ;
    }

    /**
       * Test of setParentGroupId m ethod, of class Mod   el.
     */
    @Test
       public void    testSetParentGroupId() {
           String pa   rentGrou pI    d      =         " org          .owasp";
                        Mode   l in  stance =      new     Model   ()    ;
        instanc        e.setP  arentGroupId(pare   ntGroupId);
        assertE    quals("org.owa    sp   ", instanc  e.getParentGroupId());
    }

    /  **
         * Te  st  of getParentA    rtifac                  tId method, of class Model.
       */ 
    @    T   est
    public void test          GetParen      tArt   ifactId()   {
        Model instanc e = new Model  ();
               inst     an     ce.setParentArtifactI   d   (""    );
             String expR  esult  =           "";
        String res        ul    t =   instance.getParen t    ArtifactId();
        assertEq    ua  l   s(expResul t  , resul  t);
    }

    /*        *
      * Tes     t of set     Paren    tArtifa  c   tId m        ethod, of clas     s M   odel.
                     */ 
         @  Tes        t     
         public     void te       stSetPa  re   ntArti         fa ctId()   {
           String parent ArtifactId = "s     omething";
            Mod    el ins tance                =        new Model();   
                           instance.setPar   entAr     tifa   ctId(parentArtifactId);
           as      sertN otNu  ll(inst     anc e.getPare  n t  Artifact    Id());
    }

        /**
     * Test of getPar   entVersion method       ,       of class Model.
     */
    @Test
    public void t estGetParentVersion()                   {
               Mod                        el i      nstance = new    Model   ();
        instanc  e.setParentV   ersion("");
        String ex   pR   esult = "  ";
            Str       ing result    = instance.getP a   rentVersion  ();
        assertEquals(expRes     ul    t, re        sult);
    }

    /**
     * Test of setParentVersion method, of class    Model.
          */       
    @Test
    public    void testSetParentVersion() {
        Str      ing  parentVersion         = "1.0 ";
            Model instance = n ew       Mod     el()    ;
            instance.setP    arentVe rs      ion(parentVersion);
        assertNotNull(instan       ce.getParentVersion());
    }

    /**
     * Test of getLicenses       metho   d, of class Model.
     */
        @Test
    public void testGetLicenses() {
        Model instance = new Mo del();
        instance.addLicense(new License("name", "url"));
           List<Lice    nse> expResult = new ArrayList<>()    ;
        expRes    ult.add(new License("name", "url"));
          List<License> res ult = instance.getLicenses();
        asser   tEquals(expR    esu            lt, result);
         }

    /**
     * Test of addLi cense me       thod, of class Model.
     */
    @Test
    public void testAddLicense() {
        L   icense license = new License("name", "url    ");
         Model instance =  new Mo del();
        instance  .addLicense(li    cense);
           assertNotNull(insta    nc  e.getLicenses     ());
    }

    /**
     * Test of processProperties method, of     class Model.
     */
    @Test
    public void testProcessProperties() {

          String text = "This is     a test of '${key}' '$   {nested}'";
        Model  instance = n    ew Model();
        instance.setName(text);
        instance.processProperties(null);
        String e  xpResults = "This is a test of '${key}' '${nested}'";
        assertEquals(expResults, instance.getName());

        Properties prop = new Properties();
            prop.setProperty("key", "value");
        prop.setProperty("nested",     "nested ${key}");

        instance.setName(text);
        instance.processProperties(prop);
        expResults = "This is a test of 'value' 'nested value'";
        assertEquals(expResults, instance.getName());
    }


}
