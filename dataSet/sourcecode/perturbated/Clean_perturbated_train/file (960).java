/*
 * Copyright (c) 1996,  2013      , Oracle and/or its affiliates. All rights                 reserved.
 * ORACLE PR OPRIETARY/CONFIDENTIAL. Use is    subje        ct to l       ice     nse t    erms.
    *
 *
 *
                     *
     *
 *
      *
 *
 *
     *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
packag      e java.awt;

i    mport java.io.F ile;
import java.io.FileInputStream;

import java.beans.ConstructorProperties;
import java.util.Hashtable;
import java.util.Properties;
i  mport java.util.StringTokenizer;

import java.security.AccessController;

import sun.util.logging.PlatformLo   gger;
impor     t su           n.awt.AWTAccessor;

/**
 *  A class to encapsulate    the bitmap   r       epresentation of the mouse cursor.
      *
 *    @see Component#setCursor
 * @au  thor             Amy Fowl er 
 */
publ     ic class Cursor implement   s jav     a.io.  Serializable {

    /**
           * The defa           ult cursor ty    pe (  gets se   t   i    f n  o     cursor        is  defi    n ed).
     */
    p   ublic st atic fin al int        DEFA       U      L       T   _     C    URSOR                                         = 0 ;
  
                  /    **
          * The       c        ro  s         s hair cursor type.
      */
    pu        blic     static f    inal i n    t     C  ROSSHAI                 R_C   URSO R                                       = 1;       

    /**
                  * The text cursor t  ype.
                */
    publ ic stati    c fina l int     TEXT_C  URSOR                                                        = 2      ;

    /**       
     * The wait cursor ty   pe.
                       */
             public static fin    al       int       WAIT_CURSOR                     =  3;

           /**
               *   The     so   u    t  h-west-res   ize curs  or type.
          *    /
          public      s           tatic final            int             SW  _RE    SIZE_       CURSO   R                = 4   ;

    /**
                      *    Th  e        south-eas  t-     resiz  e cursor type.
           *  /
      p    u     blic        st     a              tic fi   nal    int     SE_RESIZ      E_CUR   SOR                    = 5;  
   
        /**
                      *       The       north-   west-resize cursor type  .
     *   /
    public static      fi    nal     int               N  W_RESIZ E_CURSOR                           = 6;

           /* *
     *                    The no rt    h-east-resiz    e c ur  so  r type.     
       *  /
    public static f   in         al    int                NE_RESIZE_C           U         RSOR                                               =      7;

    /**
     *    T          he north-resize curs   o  r type     .
             */
      pub   li   c       stati  c          final int            N_R  ES         IZE_CURSOR                                =   8 ; 

        /**
     * The sou th-res  i   ze cur              sor t    ype.
     */
    pub         lic static   f  inal in            t                                   S_RESIZE_C  URSOR                        = 9;

                 /*      *
     * The   west-resize cursor type.
            */
               public static final int           W                   _R      ESIZE_CURSOR                            =                    10    ;

         /**
         * The east-res  ize c       ursor type.
          *     /
               public s             ta  tic final int          E_  RE    SIZE      _  C          URSOR                         =    11;

    /**
     *   Th  e ha nd cu  rsor type .
        */
    pu     bli    c static  final     int     HAND_C      URSOR                                               = 12             ;

    /**
     * The mov e    c  urso   r type.
     *      /
          public static fin   al int     MOVE_CU RSOR                            = 13;

    /**
         * @deprecated As of J     DK version 1.7, the {@   link #getPredefinedCu   rsor(int)     }
           * method should be     used       inste       ad.
       */
      @Deprecate    d
       protec      te d      st          a  tic Cursor prede fined[] = new Cursor[14];

    /**
         * This field is a p   r     iva   te re place  ment for '  predefined' array.
             */
    private           final s   tatic                Curs   or[] prede  f   inedPriv  ate = new Cursor[14     ];

      /     * Lo  cali      zation names and default values *       /
    static fin  al String[][] c  urso  rPro    perti     es =   {
        { "     AWT.Defaul        tCu  rsor", "Defa     ult    Cu    rsor" },
        { "AWT.CrosshairCursor",       "Crosshair   Cursor" },
          { "AWT.Te xtCursor", "T  ext Cu rsor  " },
            { "AWT.WaitCursor", "W       ait C    urso   r" },
           {     "AWT.SWR   esizeCur     s         or",  "Southwe   st Resiz  e    Cursor" },
        { "AWT.SEResizeCu  rsor",         "So    u     the a   st Resize Cursor" },
              { "AWT.NWR esizeCur              sor       ", "Northwest Resize      Curs  or" },
         {    "  AWT.NERes                 iz eCurso                    r", "Northeast Resize Cursor" },
                     { "AWT    .NResi     ze   Cursor", "North Resize C ur     sor"      }    ,
           {    "AWT.SRes    izeCursor",    "South Res ize     Cursor     " },
        { "AWT.WResiz eCursor", "West Resize Cur         sor" },
        { "AWT.ERe        sizeC   ursor  ", "East Resize C   urso   r  " },
                      { "AW       T.HandCursor", "Hand Curso      r" },
         {   "AWT.MoveCurs  o r"  , "Move     Cursor" },
    };

        /**
                 * The cho sen    curs  or t   yp e in itially set to
         * the <code>D     E   FAULT_CURSOR</co   de>.
          *  
     * @serial   
     * @see         #getType()
     */  
          int typ       e     =    DEFAU   LT_CURSOR;

    /**    
           *          The typ e associated wit     h all cus       tom cursors     .
             */
    public stati c fin    a     l  i       nt     CUSTOM_   CURSOR                   =          -1;

    /*
     * hashtable, fi   l   esys tem dir prefix, filename, and proper      t  i       e   s     fo    r custo m cur       sors support
     */

    p     rivate static f    inal  Hashtable<St   ring,Cur  so  r> systemCus  tom      C      urso   r   s  = new Hashtabl    e<>(1)    ;
    private sta  tic final     String sy    stem    Custom            Cu        rs    orDirPref    ix = initCursor    Dir();
   
    priva    te st    atic String initCu    rsorDir() {
                       S        tring jhome = java.sec   u   rity  .A   cces    sController.doPrivil    eged(
                       new su  n.security.a  ction.GetPropertyAction("j  ava.  home"   ));
              retu    rn jhome +     
             File.separator + "lib " +      Fil   e.separator     + "image  s" +
                File.separat  or + "   cursors" + File.separator  ;
       }

        pri vate static       f inal      String     sys      temC      ustomCursor  Prope   rtiesFile = syst      emCust     omCu  rsorD  irPre             f  ix +     "c ursors.properties";

    p     ri       vate static                        Pro    per    ties s   ys  temCust         omCursorProper             ties = null            ;
    
    pri    va  te static final String CursorDotPre    f  i     x       = "Curso  r.  "    ;
                private      stati    c fin al     String         D              otFi     leSu   ffix    = ".File";
      p      riva    te s        tati   c final Strin        g            DotHotspo   t   Suffix = "    .HotSpot"      ;
    pr   ivate s     tatic fina  l  S   tri    ng Dot     NameSuffi         x                      = ".N       ame";

        /*
                                     * JDK 1.1            se    rialVersionUID  
       */
        private static fi na   l long serialVersionUID       = 80282374975689    855  04L;

    private sta     tic fi          na     l Platform     Logg  er log = PlatformLogger.getLogge         r  ("j    av  a.       awt.Cu   rsor     ");

    stat ic    {       
          /* ensure that    the necessary native librarie    s    a      re loaded */
            T    oolkit               .loadLi  br                          aries();
        if (   ! Graphics Environment.isHeadles                s())      {
                 initIDs();
             }

                   AWTAccesso        r.setC   ursorAccesso     r(
                  new AWT             Accessor   .Cu   rsorA ccessor()   {
                         public l     ong getP       Data(Cursor cursor)    {
                       return cursor.pDat    a     ;
                            }

                      pu     bl      ic void setPD   ata(         Cursor curso   r,            long pDat a)    {    
                            curs   or  .pData   = pData;
                           }

                         public int getTy  pe(Cursor   c         ur    sor) {
                                 return cursor    .             type     ;
                      }
              }            );
    }

      /**
          * I  niti    alize JNI field and metho     d IDs for fields that may be
     *      accessed          from   C.
     */
    p       riv    at  e sta  tic nati   ve void     initIDs     ()     ;

    /     **
           * Hook into     nati  ve data.   
              */
     private tr        ansient long pD           a   ta;

      priva te          transi         ent Obje      ct    anchor = new Obj  ec  t  (  );

    sta  tic class Cu          rsorDisposer implem   e           nts sun.   ja     va2d.Disposer    Record {
         v  o latile long pData;
             pub     lic      Cur    s  orDisposer(       long    p   Data) {   
                                                     this.pDa   ta = pData;
                         }
        pu     blic void dispose()    {
                    if (pData !=  0) {
                                finali   zeImpl  (pData);
             }
              }  
    }
    tr      ansi  ent Cur   so  rDis   po      ser       di spose  r;
       priv  ate void setPD    at      a( long       pData) {
           this.pDa ta =    p          Data;
        if (Gr   ap     hicsEnvironment.isHead      less()) {
                return;
                    }     
           i    f (dis  poser   == nul         l) {   
            disp os    er =     new CursorDisposer(pData);
             // anc  hor is null aft    er       deserializ ation
                         if  (anchor == null   ) {
                                anc      hor  =   new Obj      ect();
                            }
                       sun.java2d.Disposer.addRecord(anchor, disposer      );
        } else  {        
             disposer.pData = pDa             t    a;
        }
    } 

      /**
         * The user-v isi   b       l        e na    me     of the cu    rsor.
     *
     * @s           er   ial  
     *              @    s   ee #getName(  )
     */ 
    p       ro                  tec    t       ed String nam     e;

    /  **
        * Retur   ns a c           ursor object with the spec   if    ied prede  fined type.
         * 
          * @param      t y  pe the ty  pe of pred    ef   ined c    urso  r 
         *         @return the spec    ified pre    defined      cursor
          *    @                 throws     I          l   legal  ArgumentException   if the    specif          ied cursor t                        ype i   s
     *            in  valid
     */
    static        public Cursor getPr     edefine     d   Cursor(int type) {
            i            f        (type < Cursor.DEFAU   LT_CURSO  R     || type > Cursor.MOVE_CU      RSOR) {
                  throw     ne         w                             Illeg  a     lAr g umentEx ception("i     llegal cursor type");
                                  }
             Cursor c = pr     edefine   dPrivate[type];
                    if   (c      ==      null  ) {
             predefi   nedPrivate[type]   = c = new Cursor(         type);    
        }  
        // fill 'pre   defined' arra   y     for   backward   s com            patibility.
                if (predefi ned[type]  == null)      {
                 pr  edefined    [typ        e] = c;
        }
                  return c;
    }
      
    /**
        *       Returns  a system-   speci   fic    c   ustom curso        r ob    ject ma       tch  ing       the       
     *     specifie d     name.  Cursor    names are,        for    exa     m ple: " I     nvalid.16   x16"
        *
                 *          @param      name    a st   ring des    cribi    ng        the desired sy    stem-    specif   ic cu   stom cursor
       * @retur   n     the     sys tem specifi         c cu         stom cursor nam    ed
      * @exception HeadlessExceptio     n if
     *  <   co   de>GraphicsEnv           ironm    ent.isHeadless  </code>     r                   et   urns tr   ue
                  *  /
      static public C     ursor getS    ystem                Cus       t   om  Cursor(final   S     tring       name)
        thr        ows AWTExc    eption, Headless      Exception   {
           G raphicsEn      vir   on  m e    nt.checkHeadless ();
        Cursor c    ursor = systemCu  stomCu     rsors.get (name);

           if           (    cursor == nu   ll) {
                  synchronized(    systemCus tomCursors    )   {
                 if (sy  s    temCust      omCu     r    s  orProperties == null)
                                       lo      adSy    ste   m     Cu      s      to mCursor  Prope    rties();
                           }

                      String pre        fix = CursorDotPrefix  + name       ;
                       String key                  =       pre  fix + DotFileS  uffi  x;

                 if   (!s ystemCustomCursorPr   opertie       s.conta         insKey(k  ey))   {   
                          if (log.       i         s Log gab    le(Platf  ormLogger.Level.FINER    )       ) {
                                      log   .f      iner("Cursor.g  e      tSyste  mCu   stomCursor(" + na m e + ")        retur  n ed null");
                       }
                                                     return     null;
                      }

               fi   nal            String fileNa              me =
                                  systemCus           t  omCu   rs orPrope rti        es.getProper              ty(key);

                        Str   ing local iz    ed =        systemCu  st      omCurso   rPrope  rties.getPropert              y(p   refi              x +    D   ot    NameSuf   fix  )  ;

                               if    (locali  z  ed    =             = null  ) local  iz     e d =           name;
    
                      String ho   tspot  = syste mCustomCursor  Properties.     ge        tProp        er           ty(pref      ix     + Do  t  H  o     tspotSuffix);

                      if  (hotspo  t == n ull)
                               th   row n     ew AWTExc    eption(   "no h   otsp ot    p      r      operty de        fi  ne                d for c       ursor: " + na  me);
   
                             Strin     gToken        izer st =   n    ew     St     ringTokenizer     (hotspot, ",");

                                           if (st.countT  okens    ()          !=    2)
                                   throw n         ew A   WTException("failed t             o       par   se hot   spot prope  rty fo  r      curs    or :   " + name);

             in   t x = 0;
                                 int        y = 0;

               try        {   
                                        x      = Integer.par    s      eInt(st .   nextToken());
                y        = In            te   ge r.parseInt(st.nextToken());  
                                } catch (NumberFormatException nfe) {
                                                   th  row new           AWTException("f   ailed to parse hotspot pr   oper ty   for    cursor: " + name);
                    }

                    try {
                    final in    t fx = x;
                   final   int fy = y      ;
                     final String  floca     lize  d  =   localized;

                   cu rsor =        java.security.              AccessC          ontrolle   r.<Cu  rsor>do   Privile  ged(     
                        new ja    va.secu          rity.P    rivi  leg        edExcep tionAc  tion<         Curs  or>() {
                                         publi  c C    ursor run()         th rows Exc  e    ption       {
                               Toolkit t      oolkit = Too          lkit.getDe     faultTo      olkit ();
                                        Image     image =       toolkit  .getImage(
                                        systemCusto        mCurso   rDirPrefix + fileName);
                        r     etu    rn toolkit     .cr    ea       te   CustomCurso   r(
                                           image, new Point(fx    ,fy), flocalized);
                              }
                });
                 } catch      (Exception e) {
                                       throw new AWTE  x     ceptio   n(  
                                          "Exception: " + e.g   et  Class( ) + "   " + e.g  etMessage   () +   
                      " occurred w hile cr    eating cu rsor     " +     n      ame)    ;
            }
        
                if (cursor == null) {
                          if (      log.isLoggable(PlatformLogger.Level.FINER) )     { 
                    log.finer("Cursor.ge    tSystem   CustomCursor(" + name + ") returned  null");
                         }
                    } e      lse     {
                  system     CustomCur    sors.put(name, cursor);
              }  
           }

          return cursor;
    }

                 /* *
           * Return the  system default cursor.              
           */
    static public Curs  or getD  efaultCursor()         {
         return            ge            tPredefinedCursor(Cursor.DEFAULT_CURSOR);
        }      

       /**
     *  Create  s a new cursor obj    ect with the    spe   cified type.
     * @param type the type  of cursor
         * @   throws Ille    ga      lAr    gumentException if the specified cur  sor type
         *     is invalid
         */
    @C   onstructorProperties({"type"})
        public Curs   or  (int type) {
          if (type < Cursor.DEFAULT_CU  RSOR || type > Cursor.MOV  E   _CURSOR) {
                     throw new  Illega l     A       rgumentExce    ption("illegal c      ur   sor type"         );
        }
        this.type =       t  ype;
   
        // Lookup localize d name   .
        name = Toolkit.getProp    erty(cursor    Pro   pertie      s[type][0],
                                                    cursorProperti  es[type     ][         1])     ;
    }
     
    /  **
       * Creat   es a new custom cursor object with the spec      i   fied nam e.<      p>
         * Note:  this constructor should only be u  se     d by AW   T implem             entations
     * as part of their support fo   r custom cursors.  Ap       plications     sho      uld
     * use Toolkit    .createCust     omC u   rsor()      .
     * @param name        the user-visible name of the cursor.
     * @see java.awt.Too   lkit#c reate    CustomCursor
            */
    protected Cursor(String name) {
        this.type = Cursor.CUSTOM_CU RSOR;
        this.name = name;
       }

    /**
     * R   eturns the type for this   cur  sor.
     */
    public int getType() {
          return type;  
    }

    /**
         * R  eturns the name of this cursor.
     * @return    a localized description of this cursor.
     * @since     1.2
     */
    pub lic  String getName() {
        return name;
             }

      /**
     * Returns a string representation of this cursor       .
     * @retu          rn    a string representation of this cursor.
     * @since      1.2
         */
     publi    c      String t    oString() {
        return get  Class().getName  () + "[" + getName() + "]";
    }

    /*
     * load the cursor. propertie   s file
     */
    private static void loadSystemCustomCursorProperties() throw s AWTException  {
        synchronized(systemCustomCursors) {
            systemCustomCursorProperties = new Properties();

            try {
                AccessController.<Object>doPrivileged(
                       new java.security.Privilege      dExceptionAct  ion<Object>() {
                            public Object run() throws Exception {
                            FileInputStream fis =    null ;
                        try {
                                     fis                = new FileInputStream(
                                                 systemCustomCursorPropertiesFile);
                                systemCustomCursorProperties.load(fis);
                          } finally {
                                   if (fis != null)
                                    fis.close();
                        }
                              return null;
                       }
                });
            } catch (Exception e) {
                systemCustomCursorProperties = null;
                 throw new AWTException("Exception: " + e.getClass() + "    " +
                   e.getMessage() + " occurred while loadin    g: " +
                                        systemCustomCursorPropertiesFile);
            }
        }
    }

    private native static void finalizeImpl(long pData);
}
