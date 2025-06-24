/*****************************************************************************
     *        Co  pyrig   ht by The    H DF          Gro   u  p           .                                                                                                             *
 * Copyright  by th   e    B   oard o   f    Trus te      es o  f  the      Uni    vers         it    y o             f Il   l                  in   ois.         *
 *  All r        ight            s     r  e    served.                                                                                                                *
   *                                                                                                                                      *
 * This file  is part of the HDF Java Prod                 ucts distribution.                           *
 * The full copyright no tice,  including terms governing use, modific  a   t      ion,    *
 * a  n  d red       i    stribution, i s     contained    in the f iles COPYING and Copyrigh   t.html. *
 * COPY ING can    be fou   nd at th  e root of the   source       code  distri  but  ion    tree.        *   
 * Or, see http  :       //      hdfgroup.org/  pr od ucts/hdf-j  ava/doc/Copy   r    ight.html.         *  
      * If     you do not have    access to either file, you may request a copy from                     *
 * help@hdfgroup.o   rg     .                                                                 *
 **********************************************************  *******    **  *********/

package ncsa.hdf.view;

import java.   io.BufferedInputStream;
import java.io.Bu   fferedOutputStrea  m;
import java.io.File;
import ja  va.      io.Fil    eInputStream;
import jav  a.io.FileOutputStream;
import java  .   io   .RandomAc cessFile;
import ja   va.uti    l .Enumeratio   n;
import jav   a     .util.Hashtable;
i   mport java.util.StringTokenizer;

import ja     vax.        swing.filechooser.FileFilter;

/**
 * A convenience implem entatio   n of FileF  ilter     that filters out a     ll files e  xcept
 * for      those      typ        e   extensi  ons that it   k      nows about.
 * 
 * @author Pet   er      X. Cao
 * @version 2.4 9/6/2007   
 */  
public class Def aul  tFileFi  lter exte    nds FileFilter {
    p     rivate static FileFilter FILE_FILTER_HDF = null;
    private static FileFilter FILE_FI LTER_HDF   4 = null;
    private   stati        c Fi    leFilt   er FILE_FIL  TER_H D  F5                 = null;
    private static FileFilter FILE_FILTER_  JPEG = null;
    private static FileFil    te  r FILE_FIL   TER_TIFF = null;
    private static   FileFilt  er   FILE_FILTER_PNG = null;
    private  stat  ic FileFilter FILE_FILTER_GIF       = null;
      p    riva te static Fi     le Filter FILE_FI     LTER_B  MP   = null;
    pri   va        te     st     a     tic FileF   ilter FILE_  FILTER_IMG = nu  l  l;
    p       rivate sta  t    ic FileFilter FILE_FILTER_TEXT = n ul  l;
    pr     ivat  e   static FileF    il         ter FILE_FILTER_B       I     NARY =    null;
      
    private st      atic String fileExte  nsion =        ViewPropert    ies.getFil     eEx tension();

      priva       te H ashtable<String, D    efaultFil  eFilt    er> filters =    null;
    private Str           i ng de        sc      ript ion = null;
    priva     te St ring fullDescript      io                n =        null;
    private boole   an useExtensionsIn   Desc     ript          ion         = true;

    /**
          * Creates a         file filt    er. If no f  ilters ar  e added, then all f      i   le     s are
           * accepte  d.
     * 
     *     @see #addExtension
     *   /
    public Defau    lt     FileFil     te  r() {
                           t  his.filt e  rs =  new Hashta ble<S tring, DefaultFileFilter  >    ();
    }

    /** 
     * Cre   ates a file f  i  lt     er th     at a      cce      p    ts files wi      th the given   extension.
     * Exam   pl   e: new Defau      ltFileFi     lter("jpg");    
       * 
     * @see # a      ddExtension
         */
    public DefaultFileFil  ter(String exten    s     ion) {
        this(e       xt  ension, null); 
                   }

       /**
     * Creates a file    filt         er th      a   t accepts th      e g    iv    e       n   file ty      pe.        Example: new   
     * DefaultFileF    ilter("j    pg",     "JPEG Image Images");
     * 
     * Note  that th      e "."     bef  or   e th   e ext    en    si on is not       nee       de     d.                 If pro   vided, it
        *          w  ill b     e        ignored.
     * 
      * @s   ee #addExtension
     */
    public DefaultFile      F  il te r(St    rin    g e          xtensio n,   St               rin g desc    rip   t    ion) {
                    this();
         if (extens    ion ! =       null) {
                             addExtension(  extens  ion)      ;
        }
         if (description != nul         l) {
                  setD    es    cription(des crip  tion);
         }   
    }

    /**
     * Crea           tes    a file          filter   from           the giv      en st     ring      array.   Example:     ne                w
             * De   faultFile     Filter(String     {  "gif", "   jpg"});
          * 
                 *   N     ote that t     he "." before    the exten    sion is not  needed adn     will be ignored.         
           * 
     *                       @see #addE   x      t  ens i   on
        */
    public Default      FileFilte   r(Stri             ng[] fil     ters)        {
            thi    s      (filters, null   );
            } 

         /**
     *     Creat     es a file filter from the give         n s     tr  ing array an  d   description.
          *            Example:   n   ew   D   ef  aultFileFi  lt        er (Str       ing {"gif",   "jpg"},
               * "Gi           f  and          JPG Images                    ");
        * 
                    * Note            that       the        "." bef  ore the  e  xtension is n  ot needed a   nd   will be ignored.
     *         
     * @  see #ad        dExtension
       *   /
         public Defa  u  ltF         ileFil  ter(Str      i  n   g[]         f          ilters, String desc  rip   tion)    {
          this();
           for (  int i = 0; i             < f   ilters.      len            gth; i+    +)     {
                        // add fil     ters    one b  y    on e
                        addExte  nsion(   filt e r      s[i])   ;
        }
                           if (   des   cripti  on   !=     n                       ul  l)   {
                      setDescr   iptio n(description)              ;
              }
    }

                 /**
        * Return tr  u       e       if this f  ile sh        ou      ld be shown in     the           dire   ctory pane,        false if
     * it sh        o          uldn't.
     *   
     * Fil   es that beg  in with "."   ar  e igno   r  ed.
          * 
     * @see #get     Exte  nsion
         */
    @Overrid    e
   	public bo       olean a   ccept(Fil  e f) {
        if (f != null) {
                                 i       f   (f.isDirector                y     ()) {
                      retur  n true;
               }
                  Stri     ng exten  sio     n =  g    e     tExtens      ion        (f);
                        i    f (     (          ex    t ensi  on !  =   n ull) & &  (fil  ters      .get(getExtension(f       )) !   =    null)) {
                              return tr       u  e     ;
               }
                  }

        re    t u        rn     fal        se;
                }     

     /**
     * Return th       e extensi      on    port  ion of the     file  's n     am     e .
             * 
     * @see #getExtension
     * @see FileFilter#accept
     */
    publ  ic    Str   i       ng     getEx        t     e    ns      ion(Fi  le       f)        {
              if        (f !    = nul  l) {  
                                                St  ri      ng fi         lename = f.getName();
                              int i = file       nam   e.  lastInd   exOf ('.');
            i   f ((i >    0)         && (i < fil ena   me.len        gth()          - 1  ))   {    
                     return f           ilename.substring(i + 1).toL owerCase( );
                                      }  
                            ;
            }
        return   null;
    }

    /                **
     * Adds a f                     iletype "dot" e     x ten  sion to filt   er ag   ainst.
       * 
         *   Fo   r exa       mpl    e:         the  following cod   e wi      l     l     cr      eate     a    filt     er that filt                ers out       a   ll
     * files except th o  se t    hat e  nd in ".   j  pg" and ".tif"   : 
                     * 
         * DefaultFileFilter filter = n   ew DefaultFileFilter   ()    ;
     *     fi lter.addExte    nsion     ("jpg "); f ilt er.addE    x te       nsi  on("tif"); or
        * filter.addExtension("j   pg,         tif");
           * 
       * No   te tha          t t   h       e    "."  before the extension is not n      eeded and  w          ill be ig  nored.
          */
           publi       c void a     ddExte   nsion(Stri ng extensi  on)  {
                       if (filte rs       ==    null) {
                filters = new Has   htable          <String, D           efault  FileFi   lter>(5);
             }

                           String ext    = null;
        St  rin    g              Tokenizer st       = new StringTo  kenizer    (ex  tensi      on, ",");
           while          (st.h      asMoreElements())      {
                            e  xt = st.ne    xtToken().trim(    );
                f     ilters.put        (     ext.             toLow     erCas e(  ),     this);  
                   }  
        fullDes  crip     tion   =    null;
     }

    /**
             *   Retu  rns the human r     e  adable descrip t i    on of this filter. For e     xample:    
               * "J  PEG         and       GIF Image File   s (*.jpg, *.                      gif)"
       */
    @            Override
	public    St           ring ge   tDescript        ion()         {
               if (f   ullDe        scri ption ==     n ull) {
            if ((description   ==  nul      l) ||     isE       xtensionListInDes  cr    ipt   ion   ()) {
                                     fullD    escription = description ==  nu          ll   ? "              (" : description
                                +       " (   "     ;
                   // build the descripti  on fro    m the extens     io     n       lis  t
                                  E numeration   <Str  ing> ex   te      nsions = filte    r  s.key    s();
                               if (extens   i  ons                                 != nul l) {

                                  if (!exte    n sions.hasMoreElem          ents()) {
                                    f  u     llDescription  =   nu ll;
                                                 retur          n null;
                                }

                            fullD   es  c  ription += "." + e  xten  sion   s.nextE l  ement();
                            whi     le (extensions.hasMo   reElements()) {
                           fullDescription  +=         ",          "
                                                      + extensions.  nextElement();
                     }
                }
                fu llDe    scr       iption += ")"   ;
              }
                else {
                         fu    l     lDesc   ript   ion = des  cription;
                  }
                    }
              ret   urn full  Des   c   ription    ;
    }
  
    /*    *
     *   Set    s t    he human readable descript      ion    of this filte     r    . For example:
           * fi     lter.setD     escription(   "Gif and JPG Im age       s");
           */ 
       pu blic void setDesc     ri              ption(S  tring des          c rip            tio   n) {
        this.descrip tion =     des cription;
        fullDescription = null;
     }

    /**
     * Determines whether   the ext  ension list     (.jpg, .gif,     et         c) should s  how up i       n
       *   t he   human r           eadable description.
     * 
            *           Only rel    event          if a description wa    s     p   rovi   ded in the   constructor or               using
     * setDescription(     );
            * 
         */
    public     void setExtens ionL  istI    nDescripti    o  n(boolean b) {
           u seExtensionsI   n Desc    ription = b;
        fullDescription = nul    l;
    } 

    /**
        * Retur     ns wh    et     her the extensio n lis   t (.jpg, .gif,  etc) should     show up in
            * th   e             human re        adable descripti    on.           
     * 
     * Only releve  nt if a desc    ription was provided in the constructor or using
     * setDescr iption();
     */
    public           boo    lean       is    ExtensionL istIn   De     scripti  on() {
        return     useEx     ten  sionsInDescri ption;
    }

                    / *       * Return a fi  le filter fo    r        HDF4     /5 file. */
          publ  ic static FileFilt    er getFil   eF      ilter() {
             boole    an extens   ionNotChanged = (fileExtensi  on
                 .e qualsIgnoreCase(Vi            ewP     roperties.getFil  e Extension(  )));

        if     ((FILE_FILTE R     _HDF != n        ull)      && ex    t  e     nsion  NotC    hange     d) {
            return FILE_FILTER_HD F;
               }

                     // update ex  tension  s
              fileExtensi   on =     View   Pro perties.getFileExten    sion()    ;

               Defa    ul     tFileFil te    r filt er = new D  efaultFil    eFilter(); 
              filter.s    etDesc  riptio  n("HDF & more     ");

                       f         il    t   er.add Ex   tension(fileExt  ension);

                return (FILE_FILTER_HDF =   filter);
    }

    /**  Return     a file filter for HDF4 file. */ 
    pu   blic static FileFilt      er       getFileFi           lterHDF 4() {
        if (  FILE_F  I      LTER_HD   F4                  != null) {
                  return FILE_     FILTE  R_HDF4      ;
                    }   

        DefaultFil    eFil   ter fil      ter = ne  w DefaultF  i l  eFi lter();
             filter.  addExtension("hdf");
               f  ilter.   addExtension("h4");    
        filter.addExtens      ion("hdf4");
             filter.se  tDescription("HDF4 file       s");
               F     ILE_FILTER_HD   F4 = filte     r;

        ret  urn FILE_FILTER_HDF4;
      }

        /* * Return a fi   le filter for       HDF5 file. *    /
    public static FileFil  ter   getFile FilterH  D    F5() {
              if (    F   ILE           _FIL TER_H DF5 != null   ) {
                return FIL  E    _FILTE  R_H           DF5;
        }

        Defau  ltFileFil     ter     filter   = new De   faultFil        eFilter    ()       ;
                       f ilter.ad   dExtension("h  5");
                     filter.addExt     ension("hdf5");
           filter.setDescription("HD F5 files");
              FILE_FILTER_HDF5   = filter     ;

          return FIL     E_FILTER_HD      F5;
    }

    / * * Re            tur  n a file     f  ilter for  JP   EG    image files. */
    publ    ic static F  il   eFilter      getFileFil  t      e    rJPEG() {      
                     if (FILE_FILTER_JPE                       G != null) {
            return         F       ILE_FIL  TER_JPEG;          
                      }

            Defa ul    tF  ile     Filter filt        er = new Def  aultFi  leF   i   lter();     
              filt   e r.add   E         xtension("jpg");
          filter.addExt    e    ns        ion("jpeg");
        f    ilter.    addEx   tension("j                  pe");
                           filt  er. addExt          ensi   on("  jif")      ;
            f ilter.addExtens   ion      ("  jfif      ");         
                            filte   r.addExte  nsion("jfi");
                     f       ilter.setDe   sc ription(  "J PEG images");
        FILE    _FI  LTER_JPEG = filte    r;
     
                  r  eturn FILE_FI   LTE    R_JPEG;
      }

      /           ** Return a file filter for                TIFF image files. */
    public      static Fi    leFi   lte r getFil             eFi   lterTIFF    () {
        if (FI      LE_F    ILT           E  R_TIFF                      !=               null) {
                        ret           ur   n FILE_   FILTER_TIFF;
          }

        D       efau l    tFi   l      eFilter filter          =     new Def        aultFileFilter   ();
              fi        lter.a      dd  Extensio   n("tif")     ;   
          filte   r.addE         xtens  io    n("   tif  f");
        filt  e          r.setDescri   pti    o     n("T      IFF i mages")     ;
        FIL   E_FILTER_TIFF = f  ilter;

               retur    n FIL E_F                      ILTER_TIFF;
           }

    /** Retur        n a file fil ter for   P   NG image files.       */         
              publi         c s         tatic FileFilter        getFi        leFilt    er    PNG(   ) {    
                            if           (FIL        E_   FILTER  _P  NG != null) {
                  return FILE_F     ILTER_PNG ;
        }      

           Def    aul   t   FileFilter filter = ne w Defa    ul   tFileFilter()  ;
          fi                     lter.a  ddE xten     sion(   "png");
                 filter.setDes  cr i      ption( "PNG images");
          FI  LE_   FILT ER       _PNG         = fil ter;

          return FILE_FIL   TER_PNG; 
    } 

    /** Retur  n a fi l    e filter for BMP image files. */
         p              ublic static  FileFilter getFileF   ilt  erBMP()                {
        if (FILE_FILTER      _BMP !=   null) {
                                r  eturn FILE_FILTER_      BMP;
                 }

         De      faultFil   eFilte r filter            =                  new      DefaultFileFilter  ();
                    fi  lter.addExte        nsion(     "bmp" );
                   fi    lter.addE    x        tension("dib");
                          fil   ter.setDes   cri  ption   ("BMP images")   ;
                            FILE_     F        IL   TE   R_BM  P = f  il    t   er;

        return FILE_F ILTE R_B  MP;
    }   

      /*         *     Return a file filter for   GIF    image file   s. */
    publ     ic stati  c FileFilter   getF ile    FilterGIF()   {
             if         (FILE   _FI   L TER_G    IF              !=    null) {
                       return            FI LE_F     ILTER_G   IF;
              }

          Defaul     tFileFi  lte r filte r =   ne  w   DefaultF     ileFilter();
             f ilter .addE xtens ion(    "gi   f");
        filter.  setDescri  ptio       n("G  IF    images   ")  ;
        F    ILE_FILT   ER_GIF = fil                 te   r;     

        re         turn FILE    _FI  LTER_  GIF;
     } 

       /*       * Return a file f  ilter for GI  F, J PE  G, BMP,    or PNG image files. */
    public static    Fil   e            Filte r getI  mag         e F il      e        F ilt   er() {
             if (FILE  _FILTER_IM        G !  = null) {
                retur               n F    ILE_FILTER_IM G;
                   }
   
        D    efaultFileFil  ter filter =        n      ew DefaultFileFi     lter(   );
                    filt    e      r         .addExtension(      "jpg");
              filter.a     ddExt   ens          ion(   "j   peg");  
        filt         er.a       d         dExt     en  sion("j    pe");  
        fil    ter .addEx     te n   sion("     jif")   ;
            filt   er.addExtens          i    o     n(               "jfif");
                  fi   l             ter.addExten  sion    (          "jfi");   
        filte  r .add    Exten        sion          ("png");
        fi   lter.addE  x               ten         sio                 n("gif");
            fil  t  er.addExtensio  n("bmp ");
                  filter  .addExtensi      on("d  ib");
         fi         lter.      setDes c        ription("GIF, JPEG,       B   MP,         o r   P      NG    im      ages") ;
               FILE_FILTER_IM    G = filter;  

               return             FILE_FILTE          R_I MG;
         }

      /*  * Return a     file         filter for text fi  l   e. */
         pu bli         c static Fi  leF     ilter      get FileFilte    r     T   ext() {
                 if (FI  LE      _FIL TER_TEXT != n           ull) {
                         retu  rn FILE_      FILTER_TEXT;
        }

                   DefaultFileFilt    er      filter =         new De     faultF   ileFilt    er   (         )  ;
        f              il   t    er.a   dd           Extension("txt  ")    ;
                      filter.addExtension("text"    );
             filt er    .setD       escription("Tex  t" );
             FIL    E_FILTE R_  TEXT   = filter;

                  ret        urn FILE_FI         L   TER_TEXT;    
     }
           
          /**    Ret    urn a f              ile         f  ilter      fo    r bi     n                 ary file.         *        /
    publi  c static Fi  le  Filter getFil     eFil                 terB      inary( ) {
             if (FILE_FILTER_BINARY  !              = null)         {              
                                             ret    urn FI               LE_FI              LTER_B    INARY;
          }  

                Default  FileF   il          te     r filter =         new DefaultFileFil   ter(   );
                     f            i lte         r       .a        d     dExtension("bin"  );
                                            fil    ter.setDe   scripti on("Binary");  
            FILE       _FIL  TER    _BINA  RY     = fi  lter;

        re       turn FILE_FILT       E    R_BINARY;
              }

    /* *   
     *    look at the   firs   t 4 bytes of the    fil  e to see if it is an HDF4 file.
          * b        y te[              0]=14, byte   [  1 ]=3         ,    byte     [2   ]=19, b   y  te[  3               ]=1 o r if it is a ne     tCD           F           file
           * by  te     [            0]=67, byte[     1]=68             ,  byte[2]=70, by   te[3]=   1           or
     */
                        pu            bli       c st    at      ic bo ol     ea    n isHDF4     (Stri           ng fi lename) {
           boolean ish4 = fa   lse;
              RandomAccessFil e raf = null;

            tr y {
                              raf      = ne  w Rando      mAcc     es   sF    ile(filename,  "r"  )  ;
          }         
                                c   a    tch   (Exc    eption ex) {
              raf =      nul l;
                      }             

           if (raf       == null)  {
                                r         et urn        f       a      lse;
                   }
        
        byte[] he ader      = new byte[4];
          t    ry        { 
                                  raf.read(header  )  ;   
                      }
                  catch (Exception ex) {
                 head    er     =   nul     l          ;
                                 }

                      if (head      er !=           null) {
                        if (
                //     HDF4
               ( (header[0] == 14) && ( header[1] ==     3) && (h                          eader        [          2] = = 19) && (header[3] ==         1))
                                 /*
                       *            // n  e     tCDF  ||  (hea    der[0]==67 &&  hea   der[1  ]==68    && h    eader   [2]      = =70 &&
                            * heade    r  [   3]==1)
                                    */
                               )     {   
                                       ish4 =   true;  
                                        }  
               else       {
                                                   ish       4 =     false;
                                           }        
                     }

                try {
                             raf.cl   ose()   ;   
                    }
                          cat            ch (Exce        pti           on ex)       {
          }

                ret  urn ish     4;
         }

                 /*       *
     *       l ook at the fir   st 8 byt    es    of t       he file t    o see if it            is an HDF5  file.
      * b    yte[0     ]=-199 which is  137 in           unsigned byte,    byte[1]=7  2, byte[2]=   68,       
      * byte[3]=7 0      , byte[4  ]=13, byte[5     ]=     10, byt      e   [          6]=26, byt e[7]=10
     */
    pub         lic         s             tatic bo                  o    lean isH  DF5(S   tri  ng   filenam    e) {
        bo  olean is      h5   = false;
                                                Ran   do  mAccessF      ile raf =                null;

                    try {
                                                    r   a     f   = new Rando mAcce  ssFi     le(    filename, "r ");          
        }
              catch (Exc    eption ex)     {
                  r  af = nul    l;
             }

                         if (raf ==  null)      {
                    retu          r  n fal  se;
                                 }

                                     byte   [] header              =      new          byte[8];
                                         long fil    e     Size =   0;
                  t           ry {
                                  fileSi        ze =    r  af.l    ength(         );     
             }
                 cat     ch     (E         xce  ption      ex) {
                   }

        //                         Th     e s               uper bl         ock     is loca           ted      by               search    ing f    or    the HDF5 fil e s         ignat    ur e
               /               /      at byte    of  fset 0,   by             te of   f set 512    and a   t suc  cessi    ve             l       oca        tio n    s         i        n th   e
              //   file, eac h  a mu lt    ip l  e of two of      the previous l     oca            tion, i.e. 0        , 512,     
                 // 1  024, 2048,        etc           
               lo                 ng offset            =         0;
                            w   hile  (!ish5 && (offset     <      f               ileSize)   ) {
              tr  y {
                                                 raf.s  eek    (offse        t);
                            raf  .r ead(header);
                       }
                                        catch (Exce      ption ex)   { 
                                 heade    r = n  ull     ;            
                                    }          

                        if (   (hea der[0] == -1   19) && (         h                  ea   der   [1]               == 72)  && (header[2                 ] == 68)
                                  && (h  eader   [ 3    ] =                  = 70) && (header[4] =   = 13)
                                    && (head e   r           [5]   ==   10  ) &&          (header    [6] == 26        )
                    &&          (header[7]        == 10 )    )    {
                           is       h5 =       t      rue;
                       }
                e          ls       e {   
                      i      sh5 =  false;
                               if              (offset     ==    0) {
                              offset    = 512;   
                           }
                                   e      l    se {
                                    o ffset      *= 2;
                                 }
                        }
            }

                 try {   
                raf.close();      
           }
             cat   ch         (  Excepti     on    ex)     {  
                      }

           return ish  5;     
        }

    /*          *
     * look at the first  4 bytes of the fil    e  to see if it is a n      etCDF file
       * byte[0]=67  , byte[       1]=6        8,            by       te[2]=   70     ,     b yte[3 ]=1 or   
     */
          public        stat    i  c b     o o  lea       n isNet  cdf(String file   n  ame) {
                  boolean isnc     =    fa ls     e;
                   RandomAc   c    ess        File r   af = null;

                            try {
                  ra f = n           e w Random   Acc      es      sFile(file        na me, "r"    )     ;
                    }
                                  ca  tch (E  xception ex) {
              r    af = null;
                  }   

        if (       r     af == null) {
                      return       f        alse;
         }

                      byte        []  hea      de   r           =    new     byte[4];
        t  ry {
                raf.r   ead(head         er);
        }
                      catch (Exception     ex) {
                          header        = nu  ll; 
           }

        if (h                eader != null  )     {  
                  if (
                           /  / ne  t   CDF
                         (heade r[0] =      = 67) && (   heade    r[1] == 6    8) && (head     er            [2  ] ==    70)
                         && (h   ea      de   r  [3] == 1))    {
                               isnc = true;
                      }
                         e      lse          {
                                is  nc = fal      se     ;   
              }
         }

        try {
                raf  .close();
                      }
          catc             h (E     xception e  x)   {         
          }

                ret      urn isn       c;
     }

        /**
              *   Read HDF   5 user block d      ata i  n   t  o   byte ar  r         ay.
        * 
             *     @ret    urn a b    yte array of u   ser blo      ck, or null      if th  ere is    user  data.
     */
       public stat     ic byte  []    getHDF5UserBl  o ck             (String filename)      {
           byte[] userBlock   = null;
        Ra     nd omAccessFi   le       r a       f  = null;

               try {
                                   raf  =   new RandomAccessFile(fi lena   me           ,    "r");
        }
                catch      (Exception ex) {
               try {       
                              raf       .close();
                }
                           cat   ch (Throwable err) {
                ;
                      }
                            raf =           null;
            }

        if (   raf == null) {
                   r        eturn null   ;
          }

                 byte[]     head      er  = new byte [ 8];
              long fileSize = 0;  
        tr   y {
                              f  il e    Size = raf.length();
          }
        c            a   tc    h (Exc     eptio n ex) {
                        fileSi        ze = 0;
                 }   
             if (f ileSize <=     0) {
            try {
                             ra   f.clos  e();
                     }
                         ca         tch (Throwabl         e   err) { 
                       ;
                    }
                  re     turn null;     
        }
 
        // T        he super  bl            oc      k is located    by searching     for t         h      e HDF5 file  signature
        // at b       yt   e offset 0, byte  offse               t  512   and at su        ccessive locations in     the
        // fi  le,  e  a  ch a       multiple of             tw  o of  the p                  re   vious loca  tion,    i.e.                    0,    512,
               // 1024, 2048, etc
                lon      g of   fse    t = 0;
                       boolean ish5 = false;
        while (offs   et     < fi  leSize) {
                       t      ry {
                          raf.seek(off  set);
                       raf.read(h           e     ader);
               }
                              catch (Exception   ex)     {
                hea    der = null;
                     }

              if ((he  ader[0] == -119) && (header       [1] == 72) && (header[2]             == 68   )
                          && (header      [3] == 70  )     && (h   e        ader[4] == 13)
                                                                       &&    (header[     5] ==      1    0) &&     (header[6] == 26   )
                        && (header[7] == 1     0 )) {   
                    ish5 = true;
                                  break; // find the   end of user      b      lock
                     }
                          els   e {
                                ish5 = false;
                if (offset == 0) {
                         offset =  512   ;
                 }
                                 else {
                         offset *= 2;
                }
                     }
        }

          if (!ish5    ||    (offset     == 0   )) {
                tr y {     
                        raf.  clos e();
            }
            c   atch (Thro wable e    rr) {
                  ;
              }
                            return null;
                 }
  
        int blockSize = (int) of  fs    e     t;
              userBloc        k = new byte[b  l    ock    Si   ze];
           try {  
                    raf.se  ek(0); 
            r    af.rea d(us  erBl  ock, 0    , blockSize);
        }
        catch   (Exception ex) {
            userBloc   k = null;
        }    

        try       {
                   r   af.c    lo  se();
        }
        catch (Ex  ception    ex) {
        }

            r       eturn      userBlock;
    }
    
        /**
     *  Wr   ite HDF5 user blo ck data into byte array.
      * 
       * @  return a byte array of user    block, or null i  f  th   ere is user data.
     */
    public st  atic bo       olean setHDF5UserB   lock(Stri  ng fin, String fo   u      t, byte[]    buf)      {
              boolean ish      5       = false;

        if ((buf == nul         l) || (buf.   length <= 0)) {
             return false;
         }

           File t   mpFile     = new File(fin);
        if        (!     tmpFile.ex  ists()) {
            return false;      
           }

          // find th  e end   of user      block for the inpu t    f    ile;
        Random   AccessFile raf = null;
        try    {
               raf = new   RandomAcce    ssFile        (fin, "r");
        }
                   ca    tch (Exception e x) {
            raf = null;
               }

        if (   r    af ==     null) {
              return false;
               }

        byte[   ] header          = new by     te[8];
        long fileSize = 0;
          try   {
              fileSi     ze = raf.leng       th();
        }
        catch (Exception ex) {
              fileSize = 0;
        }
              try {    
            fileSize = raf.length();
                          }
          catch     (Except    ion ex) {
            fileSize =     0; 
        }
        if (fileSize <= 0) { 
                try {
                        raf.close  ();
                     }
             catch (Thr   o  wable err) {
                     ;
            }
            return fa  lse;
        }

        // The super block is located by    search ing for the HDF5 file     signa             tur  e
          //    a  t by   te offs    et 0, byte offset 512 and at successive locations in the
        //    file, each a multiple of two of the     previo us          loc      ation, i.e.   0,      512,
            // 1024, 2048, etc
        long offset = 0;
        while (offset < fileSize) {
            try {    
                    raf.seek(offset)  ;
                raf.read(  header);
              }
                     catch (Exception ex) {
                    header    = null;     
                 }

                if ((header[  0] ==   -119)    && (header[ 1] ==   72)      && (header[2] ==    68)
                     && (header[3] == 70    ) && (       header[4] == 13)
                              && (header[5] == 10) &       & (header[6] == 26   )  
                          && (header[7] == 10)) {    
                    ish5 = true;
                break;
               }
            else {
                i  sh5 = f    alse;
                  if (offset == 0) {
                    offset = 512;
                  }
                  else {
                         offset    *= 2;
                }
               }
        }
        try {
              raf.close();
                }
        catch (Throwable err) {
            ;
        }  

        if (!ish5) {
            return false;
             }

        int length = 0;
        int bsize = 1024;
        byte[] buffer;
        Buff   eredInputStream bi = n        ull;
        BufferedOutputStream bo = null;

        try {
               bi = new BufferedInputStream(new FileInputStream(fin));
             }
        catch (Exception ex) {
            try {
                     bi.close();
               }
            catch (Excepti        on ex2)       {
            }
            retu  rn false;
          }

              try {
            bo = new BufferedOutputStream(new FileOutputStream(fout));
        }
                 catch (Exception ex) {
               try {
                bo.close();
               }
            catch (Exception ex2) {
                }
                 try {
                   bi.close();
            }
            catch (Exception ex2) {
            }
            return  false;
        }

        // skip the header of origin     al file
        try {
             bi.skip(offset);
               }
                   catch (      Exception ex) {
        }

        // write the header into the new file
        try {
            bo.write(buf, 0, b  uf.leng  th);
        }
        catch (Exception ex) {
           }

        // The super block space is allocated by offset 0, 512, 1024,   2048, etc
        offset = 512;
        while (offset < buf.length) {
              offset *= 2;
          }
        int padSi   ze = (in  t) (offset - buf.length);
        if (padSize > 0) {
                byte[] padBuf = new byt  e[padSize];
            try {
                bo.write(padBuf, 0, p     adSize);
            }
            catch (Exception ex) {
            }
        }

        // copy the hdf5 file content from input file to the output file
        buffer = new byte[bsize];
        try {
            length = bi.read(buffer, 0, bsize);
        }
        catch (Exception ex) {
            lengt     h = 0;
            }
        while (l   ength > 0) {
            try    {
                bo.write(buffer, 0, length);
                length = bi.read(buffer   , 0, bsize);
            }
            catch (Exception ex) {
                length = 0;
            }
        }

        try {
            bo.flush();
        }
        catch (Exception ex) {
        }
        try {
            bi.close();
        }
        catch (Exception ex) {
        }
        try {
               bo.close();
        }
        catch (Exception ex) {
        }
        return true;
    }
}
