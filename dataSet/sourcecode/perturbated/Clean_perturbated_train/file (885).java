/*
 * Copyright      (c) 1998,     2013, O  r  acle and  /or     its affiliates. All ri       ghts reserved.
 * ORACLE   PROPRIETA    RY/CONFIDENTIAL. Use    is subject    to license      terms.
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
package javax.swing.text.    html;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
imp ort java.awt.   HeadlessException;
import java.awt.Image;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import jav a.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.s     wing.SizeRequirements;
import javax.swing.text.*;

/**
 * Defines a set of
 * <a       href="http://www. w3.o  rg/TR/REC-CSS1" >CSS attri    butes</a>
 * as a type    safe enumer ation.          The HTML View implem   entati     ons use
 * CSS attributes t    o determine how t  hey will render. This a   lso defines
 * metho  ds to map betw  een CSS  /HTML/   StyleConstants.        Any             shorthand
 * pro  perties, s uch as font, are mapped to t he intrinsi    c properties.
     *   <p>The fol lowing describes   the CSS pr    operties that               are suppor    ted by the
      * re     n       dering engine  :    
 * <ul><    li >font-family
 *   <l          i>font-style
 *   <li>font-size (supp    ort      s relative units)
      *   <li>font-weig  ht
 *    <li>font
 *   <li>color
 *      <li>background-c olor (with the exception of   transparent)
 *   <li>background-i    mage   
 *   <li>background-r   e  peat
 *   <li>b      ack  ground    -   p  osi       tion
   *   <l    i>bac k ground
 *   <l      i>text-decoration (with the exception of blink and ov erline           )
 *   <li>v   ertical-align (only sup and super)
 *     <li>text-align    (justify is   treated as center)
 *   <   l  i>margin-top
 *   <l    i             >margin-ri  gh   t
              *   <li>margin-  bottom
 *   <li>margin-left
 *   <li>marg in
        *            < li>padding-top
 *      <l      i>padding-right
  *   <    l     i>padding-bottom
 *   <li>p  adding-left
 *      <li>padding
 *   <li     >border-top-style
  *   <li>bord   er-right-styl     e
 *         <li>border-b  ot    to   m-st  yle
 *   <       li>border-left-style
 *   <li>border      -sty         le (only supports inset, outset and non  e)
 *   <l  i>borde r-to     p       - color
 *   <li>bor        der-right-color
 *   <li>border-bo   ttom   -color
 *   <li>   border-left-  color
 *           <li     >bord   er-color
 *   <li>list-style-         image
 *   <li>list-style      -type
 *   <li>list-styl    e-position
 * </ul>
    * The fol lowing are modeled, but    c   urren   tly not rendered.
 * <ul><li>fo    nt-v     ariant
 *   <li>backgro  u  nd-attachment (bac     kgrou           nd         always treate   d as scroll)
 *   <li>word-spacing
 *   <         li>l   etter-spaci   ng
 *   <li>text-ind    ent
 *   <      li>text-transfor    m
        *   <li>    lin  e-he  ight
 *   <li   >border-top   -width   (t his is used to indic   ate i       f a borde  r should       be used)
 *   <       li>border-r           ight-width
 *   <li>bor der-bo ttom-width
 *   <li>border-l  ef   t-width
 *      <li>borde  r-width
      *      <li>border-   top
     *   <li>borde  r-right
 *   <li>border-bottom
 *      <li>  border-left
 *   <li>border
   *   <li   >wi  dth
  *   <l      i>height
    *   <li>float
 *   <li>clear
 *     <li>di      splay
 *     <li>whit       e-space
 *    <li>l  ist-s  ty   le
    * </ul>
       *            <p><b>Note:   for the time      being we do        not fully support relati ve unit  s,
 * unles   s noted, so tha    t
    * p { margin-top:           1  0% }    w      il   l     be tre     at  ed as if    no ma     rgin-top was speci  f    ied    .</b>
 *
 * @author  Tim othy   P rin zing 
    *    @   auth     o    r  S     cott V        iolet
 * @see StyleSheet
    */
public         class CSS      i       mplements   Serializable       {

      /**
              * Definit   ions to b       e u            sed as a       key on A         tt  ributeSet's
       *              that might hold CSS attributes.  Sinc   e this    i    s  a
                   *        closed set (i.e. defined ex     a            ctly b  y   th   e spe  cif      ication),   
     *        it is final             and      c     anno  t be e  xtended.
               */
    public static f  ina       l class   A    ttrib           ute {

        private Att     ribu  t  e    (S   tring name            , S  tr      ing defaultValue,    boolean    in   herited) {
                                   th          is.na     me =     na     m   e  ;
            this.d   efa    u     ltVal            ue = defa    u    l  tValue;
                    this.inherited = inherited;
                   } 

                     /**
             *            The str             ing rep    r       esent     ati        on     o  f    the at    tribute.  Th     is
                  * sh          ould e   xa   ctly ma   tch the str ing  specified              in th e
         * CSS spe     cification.
                */
                    public Stri        ng     toString  () {
                    return nam    e  ;         
            }

          /**
           *  Fet   c   h the  def    a       ult value for the  attribute.
                   * If there is n        o default value (such   as for
         * composi       te attribute  s), null wil       l    be   returned     .                 
              */  
           publ         ic String getDefaultValu      e() {      
             return d                efault Value;
        }    

          /**
               * Indicates if the attrib       ute sh  ould be inher       ited
           * from    t     he   parent or not.
         */
        public bool  ean isInh  er          ited     () {     
              ret  urn i   nherited;
          }

              private String nam  e;
            pr     ivate  String defa    ul    t    Value     ;
        privat    e boole    an inherit   ed;


        public s       tatic final           Attribute BA       CKGR  OU   ND   =
            new   At    t  ribute("background         ", nul   l, fa    ls  e);

        public stati    c   final Attribute B     ACKGROUND_ATTA   CHMENT =
                  ne            w Attrib  u te("b ackgr ound-attachment",                 "s    croll", fal se);

        publ   ic static     fin     al   Attribute BA   C      KG    ROUND_COLOR =
                new     A      tt  ri      bute   ("back  g   round-color"  , "t   ranspa rent      ", fal   se);

               publi       c static final Att     ribute BACKGR  OUND_  IMAGE =
              new Attribut e("bac      kg  r  ound-imag    e  ", "n        one", false) ;

                    pu       blic stati c fin  al  Attribute BACKGR  OUN      D_POSI  TION =
            new Attribute("background-p    osition", null,   false);

                    public static f  inal Attribute BACK     GR  OUND_REPEAT =
              new At  tribute("ba  ckgroun     d-repe      at", "repeat",    false);

                     public st atic final Att    ribute BORDE  R =
                  new Attribute("border",  nu    ll, false);
   
          publi c static final      Attribute   BO RDE     R    _B  OTTOM =
                        new A ttribute   ("bord      er-botto  m", null, false);

        pub    lic static final Attribute BOR  DE       R_BOTTOM_COL     O     R =
                     new Attribute("        border    -bottom-color"  , null, false);

        pub             lic      static final At    tribute BOR  DER_BOTTOM_STYLE   =
                        new A ttribute("border- bottom-style",           "            none",   false    );

             public static fi      nal Attribute BORDER_     BOTTOM_WIDTH    =
                  n      ew     Att    ribute("border    -bottom-width", "medium", false);

            publi    c s    tati    c fin    al A   ttr      ib  ut    e B  ORD      ER_COLOR  =    
                new                  Attribute(  "border-col   o         r", null, false);

              public static    fin  al Attribute BORDE  R_LEFT     =
                  new Attrib   ute("bord    er-lef      t", nu ll     ,    fa  lse);

        p ublic    static final Attrib ute BORDER_LE      FT_COL    OR =
                new Attribute("   border- left-color", null,  false);

            public static  f   inal Attrib u   te BORDER_LEF      T_ST   YLE =
                                    new     At   trib  ute("border-left-  s  tyle", "non  e", fal   se   );

                  public st             a   tic fi   nal Attr    ibute BORDE   R_LEFT_W   IDTH =
                                    new Att       ri      bute("   b   order   -left-w       idth", "medium", false);

          public    static final A  t      tribute BORDER_RIGHT  =
            new Attrib     ute("border-righ   t", null, fal     se);
    
        public static fin    a   l Attr   i      bute BORDE   R_RIGHT_COLOR =
                    new Attr      i bute("b         o   rder-right-   color", null, false);
  
              pu      blic static final Att                ribute       BORDER_     RIGHT   _S  TYLE =
               new Attribu   te("border  -righ    t-style",     "none", fal  se) ;

                 publ  ic static f   i                 na l     Attribute BORDER_RIGHT_WI       DTH =
                          new At   tri    bute("bord    er-     r   ight-width",   "medium   ", false);

        publi c     static f             inal At  tribu te BORDER_STY     LE =      
             n     ew Attr       ib    ute("border-   st yl            e",     "none",  f    al  se);

         public     stat   ic fin    al A  tt     ribute    BO        RDER_TOP =
            new Attribute("border-top", null, false);

        public stat     ic final Attr    ibut   e          BORDER    _TOP_C  OLOR =
            new At  tribute(" bor         d   er-t    op-     c  olor", null, false                );

            public    static fin al Att  ribu     te         B    ORDER        _TOP_STYLE =
                 ne    w Attribute("bor   der-t op-sty  le",   "none",       false);

        pu   blic stati    c final Attribute BORDER_TOP_WID   TH =
               new Attribute("    border-t     op-width", "m  edium", false  );

          public   sta   t ic final Attri   bute BOR   DER_WIDTH        =
              new   Attribute("bo      rder-width", "m ediu  m", false    );

          public static fin a      l         A         ttr ibu  te CLEAR =
                        new        Att r    ibute("c   lear", "none", false);

        publ       ic static final Att     ribute COLOR =
                   new Attribute("color", "black", true);     

        public static final   Attribute DISPLAY =
            new    Attribut         e( "display", "   block"   ,         false);

        pub    lic static final At      trib  u     te FLOAT =
            new   Attribute("float", "no     ne", false  );

               public   static final     At  tribute FONT =
                            n      e    w Attr  ibut  e       ("     fo    nt" , null, true)   ;

          public st  at  ic fin   al Attr ibute FONT_FAM  ILY =
              new Attribut      e  ("fo   nt-fami       ly",        null,     t      ru   e);

           p    ubl ic static    final        A   t tribute   FONT_SIZE =
                                       new Attribute("font-siz  e   ",    "medium", true);

        public st   atic final At   tribute FONT    _STYLE    =
                           new Attri         bu     te("font-styl   e", "norma l", true);

               public static final      Attribute FONT     _   VARIANT =
            new Attri    b  u te("font-variant  ", "normal" , true);

        public      stat      i  c fina  l Attribute FON   T_WEIGHT =
                     new A ttribute("font-wei  ght", "normal",    tr ue)  ;

        publ      ic st    at   ic final At  tribut                e HE          IGHT     =
            new Attri     bu     te("height", "a   uto", fals   e);

        public st     a       tic final Attribute LETTER_SPA     CING =
                 new Attr i  bute("lette      r-     spacing",    "normal", true);

        p   ubli  c static       fi     nal A tt     ribute LINE_HEIG   HT =
                     n  e    w    Attribute("line-hei    ght", "no rm   al  ", tr   ue);

                  public      sta  tic final Attribute L  IS      T  _STYLE              =
               new At  tribute("list-style", null, true);
     
                 public s   tatic final Att  ribu    te LIST_STYLE_IMAGE =
                 new A    ttrib       u  t      e(     "li   st-style-ima      ge", "no     ne", true);

              p  ublic static final Attr    ibute LIST_S TY    LE_POSIT   ION =
                          new Attr  ibute("lis   t-st    yle-posi    tio   n",           "outside", true) ;

              pub   lic sta  tic final Attribute LIST_STYLE_    TY    P   E =
            new Attri  but                 e("li st-style-type",              "di  sc",     true)  ;

               pu  blic static fina   l      Attribute MARGIN  =
                    new   At  tribu    t  e("margin", n             ull, false);

        pu  blic static final   At         tri            b   ute   MARGI   N_BOTTOM =
            new   Att    ri bute("margin-bottom", "0", f  alse);

                          p   ublic stat   ic final Attribute MARGIN_LEFT =
            new     Attribute("margin-l     eft", "0  ",     false);

              pu        blic st        at  ic fin al    A    ttribut   e M ARGIN_RIGHT =
                  new Attr  ibute( "margi   n-ri      ght"   , "0"    ,    false);

           /*
         * made up       css at trib         utes t  o desc   r ibe      o    r  ien    t ation depended
         * margin     s. used for <dir>, <men u>    , <ul> etc. see
           * 50882  6    8 for more details   
         */   
                sta tic f    inal At      tri         bute M      ARGIN_LE  FT_LTR           =   
                   new Attribu  t    e("margin    -l          eft        -ltr           ",
                                   Integer.t  oString(Integer.MIN     _V      ALUE),  f alse);
  
          stati   c final At     tri  bute MA    RGIN_LEFT     _RTL =
            new    Attribute("   m   argin   -         lef  t-  rtl",
                           Integer.to  String(Integer.MI  N_V          ALUE),     fal          se);

               static fi     n  a l Attribute MARGIN_RIGHT_LTR =
                  new  Attribu  te("     margin-right-ltr",  
                                     Integer.toString(I  nteger.MIN       _VALUE), fal s       e    );

           static final At       tribute MARGIN_RIGHT_R TL =
            n          ew Attri        bute("  ma rgin-right-rtl",
                                               Integer.toString(Integer.MIN_VAL      UE), false)       ;
 

                           publi c        sta    ti    c fina l Att     ribute M ARGIN   _TOP =
                              new Attri   bute   ("margin-to p",         "0", false);
  
        public static f    inal        Att      ribute PAD   DING =
            new Attr    ibute          ("    pa  d    din     g ",    null, false);   

        publi  c static    fi   nal       Attribute PADDI    NG   _BOTTOM     =
                                                     new At      tribute("pa       dding- bottom"  , "0",              false);

        public static final   At        tribu   te      PADDING_LEFT =
            new At        tri  bute("paddin  g-le   ft"                   , "0", false)  ;
  
                   publi  c static final Att     ribute PADDING_RIGHT    =
            new Att  ribute("padding-right", "    0", fals  e    );
           
        pub     lic stat   ic   final Attribute PA  DDING_TOP               =
                  n  ew Attribute("paddin    g-t   op",  "  0", false);

                               publ ic stat      ic fin         al A  ttri   bute TEXT_AL  IGN =
                          new Att     ribute("text-align", null ,     true)       ;

           public sta    tic final   Attr       ibute TEXT_DECORATION =
               n      ew At    tribute  ("text-de     cora     tion", "none",      true    );

        p     ubl    ic    sta   ti c f inal At  tribute TEXT_INDE    N            T =
                 new Attribute("   text-indent",  "0", true);

               public stat    ic     final    Attribute TEXT    _TRANSFORM =        
            new    Attrib ute("text-transf     orm", "none",    tr      u     e);

         public    stati      c fina   l Attribute VERTICAL_    ALIGN =     
                  n  ew   Attribute("vertical-   align", "baseline", false     );

                 publi    c  static final At  tribute   WORD        _SPACING =
            new Attr   ibute("word  -spacing", "normal", true        );

        pu  blic static final Attribute WHITE_SPACE =
                  new    Attri    bu te("white-s      pace   "  , "no        rmal    ", true);

        public s    tatic fin   al At trib  ute W        IDTH   =    
                          new      A         ttribut  e("width", "auto    ", false)   ;

                            /             *public*/ static final     Attrib    ut e   BORD     ER_SPACING =
                new Attribute("    bo    rder-spacin     g", "0"   , t  r          ue);

        /*public*/ static final Attribute CAPTION_SIDE =
                 new Att  ribu    te("caption-side", "left      ",    tr  ue);

              // Al   l   possible CSS attribute keys.
                  static final Attr    ibute[] allAttributes = {
                  BACKGROU  ND, BACKGROUND_ATTACHMENT, BACK   G      ROUN D_COLOR,
            BACKG   ROUND_IM  AGE, BACKGROUND_POSITION, BACKGROUND_REPE    AT,     
                      BOR   D  ER,       BO RDER_BOTTOM, BORDER_BOTT  O  M_WI  D        TH, BORDER_COLOR,
                 BORDER_LEFT, BORDE     R_LEFT_WIDTH , BORDER_RIGHT, BORDER_R    IGH         T_WI   DTH,         
            BORDER_STYLE, B   O        RDER_T  OP,    BORDER_TOP_W          IDTH    , BORDER_WID                  TH,
                 BORD    E   R_TOP _STYLE, BOR  DER_RI  GH  T_STYLE, BORDER      _B  OTTOM_STYLE,
            B       ORDER_LEFT_  STYLE,
            BORDER_TOP_COLOR, BORDER_RIGHT_COLOR, BORDER_B OTTOM_COLOR,
            BORDER_LEFT_COLOR,    
                  CLEAR,        COLOR, DISPLAY, F  LOAT, F      ONT, FONT_F      AMILY,     FONT_SIZE     ,
                FONT_STY   LE, FONT_VARIANT,     FONT   _WEIGHT, H   EIGHT, LE T    TER_SPACING,
               LINE_HEIGHT     ,     LIST_STYLE, LIST_STYLE_IMAGE, LIST_STYLE_POSIT           I  ON,
                                 LIST_          ST YLE_TYPE,    MARGIN, MARGIN_BOT   T   OM, MARGIN_LEFT, MARGIN_RI  G HT,
                MARGIN_TOP,    P     ADD              ING, PADDING_BOTTO       M, PADDING_LEFT,         PADDING_RIGHT,
                  PADD IN   G_TOP, TEXT  _ALIGN, TEX    T   _DECOR   ATI  ON, TEXT_INDENT, TEXT_T RANS   FORM,
                     VERTICAL_AL   IGN,   WORD_SPACING, WHITE_SPACE,     WIDTH,
               BORDER_SPACING    , CAPTION_  SIDE, 
                     MARGIN_LEFT_LTR, MARGIN_ LEFT_RTL, MARGIN_RIGHT_LTR, MARGIN_R   IGHT_RTL
        };

              private static fin a        l Attribute[] ALL   _MARGINS =
                          { MARGI  N_TOP, MARGIN_RIGHT, MARGIN_BOTTOM,  MARGIN_LEFT };
               priv  ate  st   atic fin al Attribut e[] ALL_PADDIN      G     =  
                { PA D   DING_ TOP,    P  ADDING_RIGHT, PADDI             NG_BOTTOM, PAD  DING_ LE           FT };
                 p   rivat            e static final Attribute[      ] ALL            _BORDER    _WIDT     HS =
                        { BORD  E      R_   T    OP_WIDTH, BORDER_RI   GHT_WIDTH , BORDER_BOTTOM_   WIDTH,
                  B O     RD  ER   _LEF T_WIDTH };
        privat  e static final A  ttribute[] ALL_BORDER_STYLES =
                        { BORDER_TOP_STYLE,  BORDER_RIGHT_STYL E, BORD         ER_BOT  TOM_STYLE,
                  BORDE   R_LEFT_STYLE };
                     private sta       tic       final Attribute[]       ALL_BO     RD   ER_COLOR   S =
                             { BORDER_TOP     _CO     LOR, BO   R   DE        R_RIGHT_COLOR, BORDER_BOTTOM_COLOR,
                            BORDER_LEFT_COLOR };

    }

      static    final cla ss Val   ue {

            pr     ivate Value(Str   ing name)         {
            thi   s.      name =    name;   
             }

        /**
         * The str      ing    represe       ntation of     the a  ttribute.  T his
              * s   hould exac    tly      match the stri    ng specified in    the
         * CSS       s    pecification.
         */
        pub     lic String  toString     () {
            return name;
                 }

        static final Value INHERITED             = n      ew Value ("inherited");
               static final Value NO   NE = new Val           ue("none");
              static fi  na     l Value HIDDEN = new  Value("hid    den")   ;
           static final Value   DOTTED =  new Value(     "do tted");
        sta        ti  c final Value DASHED = new      Va     lu     e("dashed"     );
                  st       at   i  c final Value SOLI       D = ne  w V         a   l       u e("    so     lid");
        static final Value     DO      U      BL   E    = new  Va     lue("double");
             static fin     al Va         lue GROOV         E     =       n      ew Valu    e ("gro   ov     e");
              s   tatic fina  l Value RIDGE = new Value("ridge ");
            static   fina   l Value INSET =          new Value("inset");
        stat  ic    f        in    al  Va       lue OUTSET = new V    alue("outset");
        // Lists.
                 s     ta tic final Value    DISC =       new    Val       ue     ("disc        ");
        static f  i    nal      Valu  e   CIRCLE = new     Value("circle");
        stat  ic final Val  ue SQUARE = new Value("square");
                  static final Value D    ECI  MA  L = new Val      ue(    "decimal");
        st    atic final Value LOWE  R_ROM AN = new Value("      lower-roman");
          static  f   inal Value UPPER_ROMA    N     = new Val    ue("uppe    r-roman"   );
        stati  c final Val  u  e LO  WER_ALPHA =       new      Val ue("lower-al  p   ha");
            s  tatic final Val   ue UPPE    R_ALPHA = new Val   ue(    "    upper-alph    a          ");
        // background-repeat
            stat    ic final Val       ue BACKGROUND_NO_REP   EAT = new Value("no-repeat");
              stat ic final         Value BACKGROU  ND_R        EP    E AT = new Value("repea   t");
                    stat   ic final          Valu     e BACKGRO   UND_REP    EAT_X = new   Value("r         epe    at-x");    
                       static     final Va  lue BACKGROUND_    REPEAT_Y = new             Val        ue("re   peat-   y");
        // background-attachment
        st atic final Value  BA  C  KGRO     UND  _SCROLL = new    Value("scroll");
          static  fi    nal Value BACKGROUND_FIXED =         new Value("fi    x  ed");

                private String name;

        stati    c    fi  na             l Value[] allV   alues = {
                            INHERIT ED, NONE, DOTT     ED,   DASHED  , SOLID, DOUBLE, GROOV   E ,
                      RI          D       GE, INSET, OUTSET, D  ISC,   CIR   CLE,    SQUAR            E,     DE  CIMAL,
                    LOWER_ROM AN          , UPP        ER_ROMAN,        LO     WER_ALPHA,   UPPER_    ALPHA,   
                B  ACKGROUND_NO_REP      EAT,     BACKGROUND_REP               E     AT,
                                     BA        CKGROUND_REPEA T_X,         BACKG       R   OUND_R             EPE    AT    _Y,
                             BACKGROUND_      FIXED,  BA       CKGROUND_F  IXE    D
        };
    }

    public CSS() {         
           b        ase  F   ontSize = baseFontSizeInde   x + 1;
              / / s    etup the              css c     onve    rsion table
        valueConvertor = new Hashtab  le<Object, Ob        ject>();
         va   lueConvertor      .put(CSS.Attribute.FONT_SIZE, new FontS   ize() );
                   va  lueCon    v e      rtor.     p   ut(CSS.Attr     ibute.FONT_FAMILY, ne   w FontFamily  (          ));
                      valueConvertor.put(CSS.Attribute.F   ONT      _WEIGHT,  new Fo   ntWeight());
           Obj  ect bs = new BorderStyle();
                 valueConve   rt or.put(CSS.Attribute. BORDER_T  OP_STY  LE, bs);
        valueConvertor        .put(CSS. Attrib     u   te.BORDER_RIGHT_STYLE, bs)  ;
          valueConvertor.pu        t(CSS.Attr           ibute.  BO  RDER   _B             OTT               OM_STYLE,    bs);
           valueConvertor.put(CSS.Attribute.B     ORD  E     R_L  EFT_STYLE, bs);            
                      Object cv = new       Co  l     orValu    e()  ;
         v  a   lueConvertor.pu    t(C    SS.Attribute.CO   LOR  , cv     );
        valueC   onv     ertor.put(CSS.A    ttribute    .BACKGROUND_C          OLOR,         cv); 
         valueConvertor.p   u    t(C    SS.Attribu             te.BORDE           R_TOP _   C      OLOR, c v);
            valueConvertor.put(CSS.Attribu   te.BORDER_   RIGHT_COLOR, cv);
                               valueConve  r  tor.put(CSS.Attribute.BORDER_B     OTTOM_COLOR,  cv);
           va   lueCo n      vertor.put(CSS.Att  ribute         .BORDER_LEFT  _COL             OR, cv);
        Object          lv   = new Len    gthValue();
        v   alueC    o     nvertor.put(CSS.Attribute.MARGIN_TOP, lv  )     ;
                  valueCo           nve r    tor.put(CSS      .A        ttribute.M      ARGIN_BO  TTOM,     lv);
                va   lue   Convertor.put(CSS.At    tribute.MARGIN_LEFT,       lv);
           v        a  lu   eConvertor.p  ut(      CSS.Att  ribute.MARGIN_LEFT_LTR, lv);
          valueCo nvertor.pu        t(CSS.Attribute.MA   R  GIN_LEFT        _RTL, lv);    
               valueConvertor                                  .put(CSS.Att     ribute.MARG    I   N   _RIGHT, lv);    
             valueC     o      nvertor     .put(CSS.Attribute    .MAR  GIN_RIGHT_LTR, lv   );
            valueConvertor     .put(CSS.A            ttribute.MAR GIN     _RIGHT_RTL, lv);
        v                alueConve       rtor.put(CSS   .A   tt  ribu    te .PA   DDING_T  OP    , lv);
        value   Con        vertor.put(CSS.A   ttrib         u  te.PADD    ING_BOTTOM, lv);
                        valueC  onvertor.put(C   SS.At   tribute.PADDING_    LEFT            ,  lv);
                               value   Conv  ert  or.    put(CSS.Attri        bute.PADD   ING _RIGH   T  , lv);
                   Object bv = new BorderWidthVa   lue(n  ull,  0);
            valu eConvertor.pu   t(C   SS.A       ttribute.BORD    ER _TOP_WIDTH, bv);
                 v alueConvertor.p   ut(CSS.At   tribute .BOR     DER_BOTTOM_WID TH      , bv);
         value  Convertor.put(  CSS.Attribute.BORDER  _L          EFT_       WIDTH, b       v);
                      valueConvertor.put       (CSS .Attribute .BORDER_RIGHT_   WIDTH  , bv );
         O   bject nlv = new Len      gthValue(true);
           va     lu     eCo  nverto  r. put(CSS. Attribute.     T        EXT_    INDENT, nlv);
                     va lueCo   nv ertor.put(CSS.Attribute.WIDTH, lv);
              valueConvertor.put(CSS.Att    rib       ute.HEIGHT, lv);
                  valu      eConv  ertor.put(CSS.Attribute.BO   RD  ER_   S      P     ACING,                 lv);
          Ob   ject sv = new Str                               in  gValu    e()  ;
         va  lueConver  tor.put(CSS.A      ttribute.FON     T_STYL    E,  sv);
               valueConvertor.  pu   t(CSS.A   ttribute.TE   XT_DECORAT     ION, sv);
                 va    lueConvert      or     .pu  t(CSS.      Attrib   ute  .TEXT_ALI       GN, sv);  
        valueCo   nv       ertor.put(CSS.Attri      bute.VERTIC            AL_ ALIGN,  sv);
          Object     valu   eMapper = ne w CssValu   e      Ma            pper();
                    valueConvertor.put(CSS.A  t        tr  ibu te.LIST   _STYLE_TYPE,
                                             valueM     ap         per);
        valueCon vert  or.put(CSS.   Attri     bute.B        ACKG   ROU                        ND_IMAG   E,      
                               new Ba   ckgroun   dImage());
        valueConvertor.put(CSS.Attribute.BACKG    ROUND_PO    SITION,
                                         new       Back groundPosition());
                valueC                  o   nvertor.pu   t(CSS.  Attribute.BAC  KGROUND_REPEAT, 
                                                                              valueMappe        r);
        valueCo n           vertor.pu       t(CS       S.Attrib   ute.BACKGR     OUND_AT              T       ACHMENT,
                                                v      a  lueMapper) ;
             Ob      j     ect gen       eric  =      new          C  ssValue();
              i     nt n = CSS.A    ttrib ute.allAtt     r    i  butes.len gth;
        for (int i   = 0; i < n; i++)     {
                         CSS    .    Attrib     ute key =     CS      S.Attribute.    allA    tt ribut   es[i]     ;
             if (va    lu     eConvertor.get(                k      ey) == null)                      {
                                         valueConve  rt   or.put       (k      ey, generic);
               }
        }
          }

         /**
         * S  ets the base              font                 size . <code>sz</code>  is        a CSS val  ue, a   nd  is
            * not necessarily the point si   ze. Use getPoin          tSi    ze to det e   rmine the
     * point size cor res    ponding            to <code>sz</code>.
     */
     void s       etBase   Fo  n   tSize       (in  t sz  )  {
            if (sz < 1)   
          baseFo  n   tSiz e = 0;           
        else if (sz      > 7)
               bas      eF     ontSize    = 7;
        else
                    baseFontSize = sz;
        }

        /**
                     * Sets the base font size from the    passe   d in stri  ng.
      */
    void s    etBaseFon  tSize(String siz              e   )     {
        in   t relSize,     abs         Size, diff;

           if             (size !   = n     ull)  {
                                           if (size.st  art     s   With("          +")) {
                                     relSize   = Integer.val u       eOf(s ize  .substring(     1)             ).i        ntVa          lu  e(      ); 
                        setBa  seFontSize(baseFontSi    ze + rel          Size);
                 } else if (si ze   .star   tsWith("     -"  ) ) { 
                                relSize = -I   nteger.va   lueOf(s    iz  e.    sub string(1)).intValue();
                              setBaseFontSiz    e(ba  seFontSize      + relSize);
                 } else       {
                   se    tBaseFontSiz     e(I    nteger.value  Of(size).intVal  ue(     ));
                            }          
                  }
    }

      /**
               * Returns th       e base font        siz     e        .
              */
    in  t getBaseFont  Size()   {    
             retu  rn b  a    seFon  tSize;
             }

    /      **
     * Parse   s the CSS      proper  ty <co de>key</   code>          with va     lue
         * <code     >value</code> placing the    res                ult i    n <co  de>att </       code    >.
     */
    v  oid    a  dd   Internal    C   SSValue(MutableAt    tributeSet attr  ,
                                CS  S.       A      ttribu     te key, S trin        g val  u e) {
               if    (key == CSS.Attribute    .FON  T ) {
                               Sho    rthandFon   tParser.pars  e  Shortha    nd  Font(this, val    ue, attr) ;
               }
        else if (key    ==         CS   S.Attribut e.BACK    G     RO U    ND) {
                  ShorthandBack          gro    undPar   se      r.      parseShort handBackgr        o  und
                                         (this, value, attr  );
            }
        els      e if (key       ==                 CSS       .          A   ttri    b   u      te  .MARGIN)  {   
               Sho  rthandMarginParser.par      s  eShorthandMargin(this, valu  e, attr,
                                                                    CSS.Attribute.ALL_  MARG      I NS);
                  }
        el    se if      (key == CSS.       Attribute.P  ADDING) {
            ShorthandMarginParser.pa  rs  eS           hort   handMargin(   thi         s , value, attr,
                                                       CSS. Attri     but  e.ALL_PADDING);     
                        }
        else if   (   ke      y                    == CSS.    Attribute.BORDER_WIDTH)  {
                Shortha     ndM  a        rginPars  er.pa       r       seShorthandMargin(thi   s, value,   attr,
                                                                               CSS.Attribute.AL    L    _BORDER    _WI  DTHS);
                }
                  e  lse if (key ==     CSS         .Attr   ibute.BORDER_COLOR) {
                  ShorthandMarginPars  er.parseSho    rthan  dMar gin(th    i  s, v     a  lue, attr,
                                                                  C   SS.Att rib    ute.AL   L_BORDER_COLORS);
        }
           e lse    if      (key == CSS.Attribu   te.BORD   ER_ST     Y      LE     ) {    
                              Shorth   an  dMargi      nPa  rser.pa  r  seSh    orthand    Margin(th    is, v   alue, att     r ,         
                                                                          C    SS.Attr  ibute.      AL    L_BORDER_STYLES);
         }
                     else      if    ((key == CSS.Att     ribute.B       ORDER) ||
                                   (key == CSS.Attri bute.            BORDER   _TOP) ||
                   (k        ey    =   = CSS.At     tr      ib  ute.BOR D      ER_R      IGH  T) ||
                                    (ke    y         == CSS.Attribute.   BORDER_B  OTTOM) ||
                         (key == CSS.At   tr   i     b         ute .B  ORDER_LEFT)) {
                                        Sh   o  r        tha   ndBorder  Pa    r ser.pa   r    seS   horthandBorder(    attr, key  ,       value);
        }   
        e        lse {
                   Object i   Value = ge  tIntern   alCS  SVa    lu  e(key, value)     ;
               if (iValue != null)      {   
                             attr.addAttribute(ke    y, i Value);
               }
               }
    }

    /**  
     * Ge  t s the       internal CSS representa    t    ion of <co        de>value</ c  ode> which is
     *      a CSS           valu   e   of    the CSS    attrib  ute named <co   de>key</ c       ode>. The receiver
             * should not mo  dify <        code   >value</cod     e    > ,       a    nd t   he fi            rst    <code >count       </    cod   e>
     * s   t    r ings ar              e valid.
               */   
    Object getInter               nalCSSValue        (CS    S.Attribut e k  ey , Str          ing value) {
        Cs sValue conv = (     CssValue) valueConvertor.get(key)        ;
                      Objec         t r = co    n        v.par   seCss       Va    lue(    value);
          return r !=       null ? r   : conv.parseCssValue(k              ey.g   etDefaultV           alue());    
    }

                   /**
     *           Map   s  from a StyleConstan      ts to   a CSS Attribute.
             */
    Attr     ibute sty   le    ConstantsKey ToCSS   K     ey(Sty       le    Constants sc)        {
               ret          urn styleCon      stantToCssMap.get(sc);   
        }  

          /**     
       * Ma   p      s f      ro        m a S     tyleConstants val  ue to a CS S       va       lu e.           
       */     
    Ob      ject st            y   l eConstantsValue      T  oCSSValue   (StyleC  onstants sc,
                                                                     Object style           Valu       e) {
                   Attribute cssKey = s    tyleCon   sta         nts                                  KeyToCSSKey(sc); 
                if (cssK   ey != null) {
                                         CssVa    lue conv = (C    ssValue)valu  eConvertor.        g             et(c  ssKey);
            return    conv.f romStyleConst     ants(sc, styleValue);
            }
        return n     ull   ;
        }

         /**
     * Co    n vert                         s t  he p   assed in CSS    va  l   ue to     a StyleConstants value.
     * <code>key       </cod      e> ident     if         ies the CS      S   attrib  ute be   ing mappe   d.
     */
    O  bjec    t   cssValueToStyleConstan    tsValue(Styl  eConstants key, Object    value) {   
           if (  va   lue       instanceof CssVa lue) {
            return          ((Css  Va  lue)value).to    StyleCo  nstants(key, null   );
        }
        return nul   l    ;
    }

         /**
            * Ret    u rns t    he     f  ont for the value           s     in the passed in Att   r  ibu          t eSe    t      .
     *        It is          assumed  the keys will be                CSS.A        ttr ibu  t   e keys.
     *     <code>sc</c ode> is   the StyleConte    xt that        will                 b  e m        es    sa                      ged to ge                    t
     * the font     once the si   ze, name and           styl            e h          ave been de termined   .                    
       */
      Fo   nt    getFont(S    ty  leContex    t sc      , Attr ib    uteSe      t a, int       defaultSize, StyleSheet   ss) {
        ss   = getStyleS     he   et(     ss)  ;
                     int size = g       e       tFo  nt         Siz   e(    a,  d    efaultSize, ss);

            /*
          * If the ve    rtica    l alig     nment       is set to either superscirp  t or
               * sub  script     we re          duce th    e         f    ont size by   2 p    oint  s         .
                            */
        Stri     ngValue vAlignV = (StringValue)    a.getA        ttrib     ute
                                                                 (CSS.Attribute. VERT                 IC      A L_AL        IGN);
              if (      (vA lignV != null )) {  
                       String    vAlign = vAlignV.toStr  ing(  );
                   if ((vAlign.in         dex   Of("s    up   ") >= 0) ||
                     (vAlign.indexOf("s  u    b") >= 0)) {
                        si ze -= 2;
            }
        }
    
          FontFamily    fa  mil  y       Value =    (         Fo     n  tFamily)    a.ge      tAttribute
                                                                 (CSS.Attribute.FONT_FAMILY);
          String f     amil     y = (familyV   al  ue != null)   ? fami         lyVal   ue.getVa       lue()  :
                                                         F ont.  SANS_S   ERIF;
                       int styl e = Fo nt.PL      AIN;
        FontWeight w  eightV      alue = (Font         Weight ) a.get  Att   ribute
                                                                 (CSS.Attr   ib  ute   .FONT        _WEIG   HT);
           if  ((weightValue         != null)                     &&     (weig   htValue.  g etValue       () >      400     )) {  
             style   |= Font.BOLD;
          }
                                                  Obje  ct fs   =  a.getAttribute   (CS    S.Attr      ibute               .FONT_STYLE);
             if (  (fs != nul    l) && (fs.toS              tring(    ).indexOf("ita lic") >    =             0)) {
                  st  yl        e      |=   Font.ITALIC;
        }
            if  (  famil         y.e   q       u    a   l               sIg noreCase("m       ono                space")) {
                family = Font                .MONOS                 PACED;
           }
         Font f = sc.g etFo         nt(fa mily,    style, s    ize);
           if            (f == null
                 ||    (f.ge    tFamily().equals(  Font.DIALOG)
                          && ! family.eq  ualsIgnoreCa         se(Fon    t     .DIALOG))) {
               fami  l   y = Fon         t.SANS   _SERIF;
                      f = sc.getFont(family,     style, size);         
               }
        return     f;
             }

         sta   ti       c int getFo                  ntSize(At     trib     u         teSet attr  , int    defa ultSize, St  yleS     heet ss)   {
                 /   / PE   NDING(  prinz  ) this i    s a       1.1               based  implement  ation   , n    eed    t                   o  also
        // have     a           1.2 v  ersion.
         FontS ize s  i     z eValue =   (FontSize)a      ttr.getAttribu    te(        CSS.Attribu     te    .
                                                                                                                                 FONT_SIZE);

         return (sizeVal     ue != nu   ll) ? sizeV      alu   e.getValue(attr, ss)
                                                          : defaultSize;
    }

                     /**
     * Takes a set of    attributes    and turn it into a co    lor    
     *       spe   cification.   This mi       ght      be   u     sed to s     pecify things
     *     like bri  ghter, mo          re hue     , etc.
     * Thi         s w   ill retur  n   null i    f there            is no value for <code          >key</co    de>.
     *
        * @param key   CSS.Attribute identifying where co      lo   r is stored.
     * @p        aram a             the set of attributes
        *      @return th                    e col   o  r
      */
           Color getColor(At    tributeSet a, CSS.Att   ri                 bute key  ) {
           ColorValu  e cv =   (     ColorValu     e) a.getAttribute(     key);
        if (c  v !=      nul      l) {
                                       ret       urn cv.getVal       u          e();   
             }
          ret   urn null;
                  }         
       
    /**
     * Retu   rn    s    the size of a f  ont  fro   m the passed    in               str  i   n  g.           
       *
                  *     @p    aram size CSS string d     escribing font siz  e
     * @para m b     aseF   ontSize  si         ze to use   for re  la                     tive u   nits .   
           *            /
    fl  oat getPointSi         ze(String         siz     e,    StyleS       heet ss) {
             i   nt relSi  ze   , absSize, diff, index;
                     ss =     getStyle     Sheet(ss);
        if (si           z       e != null) {
              if (s   i   ze     .startsWit    h("               +") ) {
                relSize = Int     eger  .val   ueOf(size.substring  (       1)).      intValue();
                       re   turn getPoint   Size(          baseFontSize + relSize   , ss) ;
                       }   e      lse if       (size.starts   Wi      th("-"))           {
                     relSize =     -Integer.val   ue     Of            (siz e  .substring  (1))    .intVal  ue()  ;
                       r  eturn getPointS  ize(bas    e    F  ontSize    + rel    Si   z   e, ss     );
             }   else     {
                   absSi   ze =         Integer.valu      eOf(size)  .in  tVal    ue();
                         return getPoi     ntS      iz       e  (ab  sSize,        ss);
              }
          }
           return 0 ;  
            }
  
      / **
     *     Retu   rns     t     he length of the attri      b       u     te in <co  de>        a</code>     with
       * key <code>key</code>. 
     */
          float getLeng    t   h(Attribut  eSet a, CSS.Attrib    ute k   e   y, S   t    yleSheet  ss) {
        ss   = getS  tyle     Sheet  ( ss);
        Leng    thVa   lue       lv  = (LengthValue) a.getAttri   but          e(       key)  ;
        boolea  n isW3CLeng    thUnits = (ss == n   ull) ? false      :     ss            .isW3CLe      ngthUn        its(  )   ;   
                      float     le   n =     (lv != null) ?        lv.getV     alue(isW3CLengthUn            i    ts)   :       0;
        retu      rn len;
    }

    /**
              *   Conve    rt a        se       t of    HTML attri  bu t    es to an equi        valent
                  *           se  t of CSS       attrib utes.
             *  
       * @pa   ram htmlAttr        Set   Attrib ute  S  et     containing the HTML        attributes.    
                       *    @return AttributeS et con       taining    the corr    espon    ding CSS attribu   tes.
       *                T    h            e Attri         buteSet will be empty if ther             e are n      o mapping
     *                          CSS attrib   utes.
          *         /
       A   ttributeSet translateH   TML  ToCS  S(AttributeSet htmlAttrS     et)    {
             Muta   bleAttrib uteSet cssAttrSe                     t = new S    imple  Att     ributeSet();
        Element  elem            = (Element)h       t   ml   At    trSet   ;
        HTML.Ta  g tag   = getH    TMLTag(html  At trSet)               ;
        if ((tag == HTML.Ta   g . T     D    ) || (tag == HT        ML.     Tag.TH)) {
                 // tr  anslate b      order width int  o t   he  cells, if it has non-zero   value.  
                         AttributeSe       t ta     bleA    ttr             = ele  m.get        ParentElement(  ).  
                                     g   etParentElement().g      e    tAttrib          utes();

               int b   orderWidth = getTableBorder(tabl      eAttr           );
                          if (borderWidth > 0) {
                         // If       table contai  ns                           the     BORDER attribute       cel ls sho uld                             ha  ve bor   der wi d   th e q    uals 1  
                                      trans      lateAttribute(HTML.Attribute.BOR      DER, "1", cssAttrSet);
                          }   
              S tr  ing pad =       (Str ing)ta    bleAt  t  r.   getAttribute(HTML.At        t  ribute .CELLPADDI          N    G)      ;  
            if   (pad != nul                l) {
                                LengthV     al             ue v  = 
                          (LengthValue)ge   tInternalCSS Value(CSS.A  ttribute.PA                       DDI NG_TOP,    pad   );
                v.span      = (v.s     pan < 0) ? 0 : v.span    ;
                     cssAttrS et.addAttri but   e(CSS.       Attribu  te.PADDING_TO  P, v);
                    c s   s  AttrSet.add  Attribut      e(C   SS.Attribute.PADDING_BOTTOM,    v);
                   cssAttrSet .addAttrib   ute(CSS.Attr   ibute.P   ADDING_LEFT, v);   
                         css Att      rSet  .ad dA          ttrib   u      t                  e(C   SS.Attrib   ute.PADDING_R   I      GHT, v        );
            }  
        }
               if (el  em.isLe   af        ())   {
               tra  nslateE      mb   eddedAttributes(htmlAttrSet,  cssAttr  Set        );
            }     else {
            translate   Att                    r  ibutes(     tag,       htm lAttrSet,            cssA        ttrSet);
           }
        if (   tag          =   = HTML.Tag.CAP  TION) {
                       /*
                     * Navigator uses ALIGN for    caption p      laceme   nt a        nd IE uses VALIGN.
             */
            Object v         =  h  tml   AttrSet.g    e     tAttribute     (HTML.Attr  ibute.   ALIGN);
                   if ((v != null) && (v  .equals("   top") || v.e quals("bottom   "))) {
                 css      Att  rSe t.ad         dAttrib  ut              e(CSS.Attr        ibute.CAPTION     _SIDE, v);
                     cssAttr  Set.remo veA ttri    but                            e(CSS.Attribute.TEX  T_ALIG      N            );
              } else {
                             v = htmlAttr Set.getAttribu      te(HTML     .Attribu    te.   VALIG     N);
                             if ( v != null) {
                            c        ssAttrSet.addAttribute(CSS      .Attribute.CAPTI            ON_SID   E, v);
                       }
            }
              }
        return css         At   trSet;
    }

         priva te static  int getTab      leBo   rder(Attri   buteSet tableAttr   ) {
             String bor             d er  Value = (String)  tableAttr.getAttrib    u      te(H       TM   L.Attribute.B   ORDER);

             if      (bor    derValue    ==     HTML                .NULL_A   TTRIBUTE_VA        LUE |           |              "".equals(bor   derValue)) {
                // So    me browsers             ac     cept   <T       ABLE BORDER> and <TABLE   BORDER     =""> with   t      he same        semanti         c  s as BO      R   DER  =1
                        return 1   ;
          }          
    
                                  try {     
              retur   n          Inte    ger.    parseInt(borderVa       lue);                  
           } catch (Num berForma    tException e) {
            return 0;
        }
    }

    private static    final      H   ashtable    <Stri     ng,       At    trib     ute     > at tributeMa       p = ne  w Hashtable    <String, A        ttribute>();       
    pr ivate   stati    c f     inal            Hashtable<Strin     g, V  alue>     valueMap = ne w Hash    tab      le<St       ring               , Valu    e>();    

    /**
               * The h ashtable and       th  e static init     alizat ion   b      lock below,
     * set up a mapping  from we  ll-known H TM    L attributes to
      * CSS attrib    ut    es.    For the most pa      rt, t           here       i    s     a 1-1 mappi          n     g
          * between the t       wo.  However in      th    e case of c    ertain                     H TML
        * a    ttribut    es for example HTML.Att ribu    te.VSPACE                   or  
     * HTML.Attr    ibute.HSPACE, end up mappin     g to two CSS.Attri b     ute's.
        * Therefore, the value   associated with e     ach HTML.Attribute   .
      * key      ends      up being      an arr         a                  y of CSS.Attribu     te.*           objects.
     */
    private sta tic fin al Hashtable<HTML.Attr   ibute, CSS.Attri          bute[]  > ht  m   lAttrToCssA     tt       rMap = new Hashtable<HTML.Attribute, CSS.At          tribute[]>(20);

     /**
     * The hashtable and static initializati        o  n th at follows sets
     * up a       tr anslation from      Sty        leConsta   nt    s (i.e.   the <em>we   ll      kno       wn</  em>
     * attributes)         to the associ ated    C   SS attr   i     butes.
     */
    priv      ate static final        Hashtabl    e<Obj ect  , Attribute> styleConstantTo   C    ssMa   p = new    H    ashtable<Objec t     , Attribute   >(17)   ;
    /   **   Maps fr       o     m HT  ML val   ue to a CS    S    value.  Used  in i  nternal mapping. */
       pri   vate st      ati  c   final         Ha        shtable   <S         t       ring, C SS.Value>         htmlValueT           oCssVa   lueMap = new Hash   table<S       tr ing     , CSS.    V  alu    e  >(8);
    /** Maps fro                m CSS v          alue (stri   n   g)   t  o         in   ternal value. */
       priv         ate     static   final H              a  sht  abl          e<String, CSS.V    alu e> cssValueToI  n    t   er     nal    ValueMap = ne   w Hashtable<Stri      ng , CSS.Valu  e>(13)     ;

    sta       tic  {
         // load the at    tribute map
        fo    r (         i    nt i = 0;     i                  < A   t  tribute     .allAttributes.length; i++ ) {
                at  tribute    Map.p    ut(Attrib   ute.allAt             tributes        [i].toSt   r       ing(  ),
                                                    Attr          ibute.al    lAttributes  [i]);
          }
           /    / loa     d    t          h       e value     m                           ap
           for (in                t i = 0; i <   Value.   all       V      alues.length;    i++ ) {
              v   alueMap.put(Va  lue  .allValues[i].   toString(       ),
                                     Value.allValues[i]) ;
            }  

        htmlAttrToCs    sAttrM  ap.put(H  TML.Attribute.COLOR,               
                                              n  ew CSS.A        ttribute[]{CS   S   .Att    ribute .CO  LOR}); 
              ht   mlAttrT o   CssAttr  Map.p   ut(HTML.At tribu   te.TEXT,
                                                                     new          C    SS.Attrib    ute[         ]{CSS.Attribute.COLOR});  
                   htmlAttrToCssAttrMap.  p      ut(HT    ML.Attribute.CL    EAR,
                                                                      new CSS.Att   ribute[]      {CSS.A          ttr      i     bute.CL        EAR});
               htmlAt  t  r    ToCssAttrMap.pu    t(HT  ML.A   ttribu        te .BACKGROUND       ,
                                        new C  SS.  Attr  i     b   u   te  []  {CSS.A ttrib    u  te.BACKGR  OUND_IMAGE})      ;
          htmlAttr     ToC    ssAttrM   ap.put(H                  T    M   L.  Attribute.BGCOLOR,
                                                        ne  w CSS.A ttri  bute    []{   CSS.Attribute.BACKG       ROUND_COLOR});
                       htmlAt  trToC         ssAttrMap.p        ut(HTML.Attribu   te.WIDTH,
                                                              new C  SS.Attri   but    e[]  {CSS.A    t    tribute   .WIDT H});
             ht   mlA    t  tr        ToCssAttrMap.put(HTML.Attribute.HEI       GH    T,
                                                         n ew CSS        .Attribu         te[]{CSS          .Attri   bute.HEIGHT });
            htmlAt trToCssAt     tr    M        ap.put(HTML.Att          rib   ute.BORDER,
                                                       new CSS.Attribute[]{CSS.At    tribute.          BORD   ER_TOP_WI    DTH , CSS    .Attribute     . BORDER_R IGHT_WIDTH, CSS.Attribute  .B  ORD ER_BOT TOM_  WIDTH, CSS.Attribute.BOR  DER  _   LE  FT_WIDTH});
        htmlA   ttrToCssAt       trM  ap.put(HTML.Attribute.CELLPADDING      ,
                                                                          new CSS.Attribute[]     {CSS.Attribute      .PADDING});
                    htmlAttrTo    CssA    ttrMa         p.    put(HT ML.Attribute.  CELLSPACING,
                                     new CSS     .Attribute[]{CSS.Attri         bute.  BORDER_SPACIN                  G});
        htmlAttr ToCssAttr   Map.put(H     TML.Attribute  .MARGI  NWIDTH   ,    
                                                    new CSS.A t                tribu   te[]{CSS.Attribute.MA    RGIN_LEFT,
                                                                                    CSS.At          tr   ib    ute.M     ARGIN_RIGHT});
               h  tml    AttrToCssAtt       rMa     p.    put(HTML.Att    ri      bute.MARGINHE                IGHT,
                                                                                    ne     w CS S.Attr    ibute[]{ CSS.A    ttribute.MA       RGIN_TOP    ,
                                                                                               C  SS.Attrib     ute.MARGIN       _BOTTOM});
        ht    mlAttrTo   Css     AttrMap.                 put(HTML.Attribu  te.    HSPACE   ,
                                        new CSS     .A   ttribute[  ]{      CSS. At        tr  ibute.P    ADDING_LE   FT,
                                                                                         CS    S.At  t   ribu       te.PA DDIN   G_RIGHT});
                    ht        mlAttrToCssA    ttrMap. put(HTML.Attr      i          bute.VSPACE,
                                                                  new CS  S.     Attribu       te[]{CSS.Attribute.P  ADDI     NG_B  OTTOM,
                                                                  C SS.Attribute.           PADDING_TOP });  
            html    AttrT         oCssAttrMap.put(HT      ML.Att    ri     bute.FA  C E,
                                         new CSS.At     tribute[]{CSS.At  tribut   e.     FONT_FAM     ILY} );        
             htmlAttr To        CssAttrMap.put(HTML.At tribute.SIZE,
                                   new C    SS.Attribute[]    { CSS.Attrib  ute.FONT_SIZE});
           h          tm    lAttrToCss     AttrMap.put(HTML.Attr ibute.VALI   G             N,
                                        new CSS.At         tri    bute[]{CSS.Attr                 ibute.  VE   RTIC A    L_ALIGN});
        htmlA  ttr    ToCssAt  tr   Map.put(     HT  ML   .A         ttribut   e   .ALIGN  ,
                                                     new CSS.Attribu   te[     ]{CSS.Attribu        t    e.VERTICAL  _ALIGN   ,
                                                                               CSS.Attribute.TEXT_ALIGN,
                                                                                         CSS.A ttribu  te.FLO   AT});
             htmlA          ttrToCs      sAttrMa     p.put   (HTM L.Attribute.TYPE,
                                                        new CSS.  At    trib     ute []{  CSS. Attribute.LIST_     ST  YLE           _TYPE}    );
              htmlAt  trTo     CssAttrM   ap.  pu                t(HTML.A     ttribute.  N OWR                     AP,
                                              new       CSS.Attr     ib                  u          t  e[]{CSS.Attribute.WHITE_SPACE});

                           // initi  ali       ze St  yle      Constants   m    appi   n    g
               styleConstantToCs       sMap.put(StyleConsta            nts.Fon  tFamily     ,
                                                          CSS.A  ttribute.FO    NT _FAMILY);
          styleConstan        tToCssMap.put(   S     tyleConst  ants.FontS   ize ,
                                                 CSS.Attribute.FONT_SIZE)    ;
        sty  leConsta n       t  T     oCssMap. p ut(St    yleConstants.     Bold,
                                          CSS.Attri    bu   te.F          ONT_WEIGHT);
               sty                leConstantToCssMap  .         put(St               yl  e  Constant   s.Ital ic  , 
                                                    CS         S.Att            ri   bute.FON   T_STYLE);        
        styl               eConstantT   oCs  s      Map.put(Sty   leConstants.Under line,
                                                        CSS.At   t  ribut   e.TEXT_D    ECO  RATION);
                      sty       leConstantToCssMap.put(StyleConstants.StrikeThrough,
                                                                   CSS.Att     r    ibute.TEXT_D  ECO      RATION);
        styl   eC  o   nstantToCssMap .    p   u t(S    tyleCo     ns tants.Supe  r   script,
                                                           CSS.At    tribu  te.VERTICAL       _      ALIGN);
           styleConsta    ntToCss                 Map.put        (S  tyleConstants .S  ub   scr       ipt,
                                          CS        S.Attribute   .VERT           ICAL   _  ALIGN);
          styleCon   st    a  n  tToCs  s    Map.put(StyleCo     nstant  s     .For      eground,
                                                                                CSS.Attribute  .COLOR);
             s       tyleConst    antToCssMa        p.put    (StyleCon   stants.B    ackground,
                                                  CSS.Attribute.  BACKGROUND_COLOR);
             styl eConstant                      ToCssMap.put(Styl   eConstants.Fir         st         Li      neI   n    dent     ,  
                                         C SS.Attribute.TEXT_    I            N   DENT) ;
                   sty  leC on     stantT   oCssMap      .put(StyleCons      t   ants.L   eftInd  ent,
                                                            CSS.   A    ttribut   e.MAR  GIN_LEFT  );
                 sty  l eCon  stantToCssMap. put(St    yle    Constants.  RightIndent,
                                                       CSS.   Attribute.MARG            IN  _   RIGHT)    ;
            styleConst   a  n  tTo   CssMap  .put(Style      Constants.S  pa            ceAbove,
                                        C S     S.Attribu   te       .MARGIN_    TOP);
          st  yleConstantToC        ss Map.put(S     tyleCon stants.SpaceBe     low,   
                                                  CSS          .Attri  bute.MARGIN_BOT      TOM  );
            style  C onstantToC   ssMap.       put(Style   Constant  s       .Alignment,
                                                 CS    S.Attri bute.TE    XT_ALIGN    );

                //  HT ML-    >C     S     S
        h       tml     Va lueToCs      sValueMap.put("    di  sc"   , CSS. Val   u  e.DISC)    ;
          htm          lV       alueT  oCss                     ValueMap.put("sq   ua   re"  ,  CSS.Value  .SQUARE);
             htmlValueToCss  ValueMap.put("c  i   rcle",    CSS.Va  lue.CIRCLE);
             h  tmlValueTo    CssValueMap.put("1   ", CSS.Value.DECIMAL);
        htmlVa      lueToCssValue   Map.put("a"  ,   CSS.   Value .LOWE    R_ALPHA);
        html    Value    To   CssValue      Map.put("A", CSS   .V      alue.UP      P   ER_ALPHA);
           htmlV        alueT   oCssValueMap.put("i"   , CSS  .Value.LOWER     _R  OMAN);
             htmlValueToCssValueMap.put("I", CSS.Valu   e       .UPPER_ROM  AN);

                                               // CSS->      i   nternal      CSS
          cssValueToInternalVal  ue Map.   put("none", CSS    .Value.NONE   )  ;
                             cssValueTo InternalValueMap.put("disc",      CS  S.   V alu    e.DIS  C);
                  cssV       alueTo     Intern     alValueMap.p  ut(   "square", C  SS.Val       ue.S         QUAR       E) ;
        cssVal           ueT        oInternalValueMap.p ut("circle", CSS.V               alue.CIRC     LE) ;
                   cssValueTo            In  ternal  ValueMap.put("deci         mal", CSS. Value.DECIMAL  );
             c ssValueToInternalValueMap.pu  t      ("lo       wer-rom    an",        CSS  .Va   lu  e.LOW   ER_ROMAN)      ;
                    c    ss    Va  lueT     o   InternalValueMap.put("upper-roman",      C  SS.Value.UPP     ER_ROMAN);
        c  s     sValueToInternalVal     ueMa  p.put("lower                 -    a     lpha"     , CSS    .Va         lue.L  OWER_ALP      HA);
                   css        Val ueToInternalValueMa   p.p  ut(    "upper-   alph                  a", CSS.Valu     e.UPPER_ALPH    A);
         cssValueT   oInternal    V       a     lueMap.put       ("repeat"       , C     SS.Va   lue.BACKGR     O   UND_REP EA T);
        cssV  alue   ToI           nternalValueMa  p.put("no-repe at",
                                                                      CSS.Value.   BACKGR    OU    ND_NO_REPEAT);   
              css            ValueTo  I     nternalValueMap.put("       repeat-x",
                                                   CSS.Value.BACKGROUN       D_REPEAT_X);
        cssVal   ue  To  I  nternal      Valu   eMap.put("r      epe   at      -y",
                                                                            CSS.Value.BA  CKGRO     U N       D_REPEAT_Y);
        cssVal ueT          oInt ern alValueMap.p     ut("scrol l",
                                                                                CSS.Value    .B    A     CKGROUN     D_S     C      ROLL);
                      cssValueT   oIn   t     ernalValueMap.put("fixed",
                                                     CSS.Value.BACKGROUND             _F   IXED     );        

              // Regist     er    all the CSS attribute              keys for arch  ival                   /unarc  hiva    l
                   Object[] keys =    CS             S.Attr   ib   u  te.allA             ttributes;
             try {   
                fo  r (Object ke  y : ke           y     s) {
                     StyleContext.registerStaticAtt     ributeKey(key);
                                             }
                  } catc    h    (Throwable     e) {
                e.pri     ntStac     kTrac e();
        }

        /   / R     egister all   the                                    CSS      V  alues for archival/unar  chiva      l
                    keys = CS    S.Val   ue.  allV   alues;              
                try {
             for        (Obj    ect key :      k    eys)              {
                                       S    tyleCo   nte   xt.regi s        terStaticAttribu    teKey (key);
                                    }
        }         catch    (Thro  wa  b    le e) {
                  e.   p  rintStac      kT        race();
                            }
             }    

        /**
                 *             Return   the set of     a   ll possible CSS          at     tribute keys.
          */
      pu  b         lic       s      tatic Att          ribute   []  getAllAttribut eKe            y s(    )             {
                  A tt    rib  u    t     e[] key             s            = new Attribute[Attrib   ut  e.a llAttrib      utes.length];
             System   .arra    ycopy(Attribute.     allAttrib   utes, 0, keys,       0, Attribute. allAttributes.leng th);
               return    keys;
             }   

    /**
        * Translates a string to a    <code>CSS.At tribute</    code> object.
     *       This will re    turn <   code>n   ull</co      de>   if there    is    n o attribute
     * by the gi      ven name.
        *
         * @  param          name the name o           f the C    SS    att   ribut     e to fe tch th    e
               *  typesa  fe enumera    tion f   or
     * @r     etur   n the <code>     CSS.Attribute    </code> obje  ct,
        *   or <code    >  nu   ll</code> if t     h         e string
              *  does  n't repre                     sent     a valid attr   i   but        e key
     */
       publi        c  st   ati      c     final Attribute ge  tAtt       ribut     e(Stri     ng nam   e)      {
          r                       eturn         a ttributeM      a      p.      get(nam  e)      ;
        }

             /**  
     * Tr         ansla t  es a string    to a     <co   de>CS      S.Va   lue</co de> obj        ect.
                               * This        will   retur     n <code>null<   /code> if th                e             re is      no     value
        *   by the given name.           
         *
     * @pa        ram na me the name    of the C        SS val ue   to fetch the
     *  type      safe    enum          er a      tion for
          * @return the <code     >CSS.Value< /cod      e   > object,
          *  or <c           ode>  null     </    cod   e> if  the   st ri   ng  
     *  doe  sn't repr esent a val    id          CSS va        l      ue na      me;   this does
     *  n   ot    mean      tha   t it d    oesn      '      t re   presen  t a    valid CSS value
      */
     stat      i   c                  fin     al V     alue getValue(St    rin   g name) {
                         return    valu   eMap     .g  e    t(n   ame);
        }


                       //
    // C   onversion rel   at e  d m    ethods/cla     sses
    //

     /**
     * Returns       a   URL fo     r the given CSS url s    t  ring. If relative,
             * <co     de>ba se</code   > is  used as t   he parent. If a vali     d   UR  L can       not
     * b   e   found, this will not thr                 ow a Malfor      medURLException,       inst ead
     * null will b  e      re  turned.
                      */
    st  atic URL getURL(URL b  ase, String cssString)   {
                    if (c   ssString == null) {
                  return null;  
            }
           if (cssSt rin       g.startsWith("url(") & &
                       cssString          .endsWit  h(")")) {
                                    cssString = cssString.substr     ing(4, c   ssString.length(  ) - 1);
                     } 
                   /   / Abso              lute fir  st
                               try               {
            U   RL      u                rl    = ne   w UR L(cs    sS      tri     ng);
             if        (    url  != null   )       {
                                   retur   n   url; 
              }
                                   } catch (Malf  ormedURLExcepti           o       n mu   e) {
            }
                                    // Th         en relat ive
                if (base              != n    ull        ) {    
                 // Relati   ve URL, try from base
                         try {
                                    URL   url = new    UR     L(bas   e, css         String);
                               re  turn u         rl;
                    }
                           c  atch (Malform      edURL   Ex         cept         io  n muee) {       
                      }
                               }
        r     etu     rn    null;
    }

            /**
        *      Co nverts a    t     ype Co  lor to a hex st  ri ng
     * in the for        mat "#RRGGB    B"
         */
              s  tatic String colorToHex(Co    lor color) { 

      St  ring co    lo        rstr = "#  ";  

      // Red
      St  ring str = Integer       .   toHexString(co    lor.getRed());
       if  (str.     length() > 2)
           s   t  r    = str  .s      u  bstring( 0,   2);   
                        else if (    str.length() <    2)
                    col  o rstr +=    "     0"  + str;
          els   e
           colorst    r     +=        str;

           /  / Green   
      str = Integer.toHe     xString(col      or.    getG   reen())   ;
      i  f        (str.len    g th() >    2)
        str                   = str.          s   ubstring  (0      , 2);
                  else if   (str.len         g   th()       < 2)
                              colorstr +=    "0"      +   str;
       else
        colo   rstr     += str;   

      // B    lu              e
      s                   tr =   Integer.toHexString( color.  getBlue());
      if (st        r.leng           th()        >        2)    
                           str       = str   .substring(0,    2);
         else if (str.len g    th(   ) <    2)
                colorstr += "0" + str;
      else
           color    str   +        = str;  

        ret                 ur n colors    tr;
    }    

     /*    *
      * Co     nvert   a "#FFFFFF" hex st        ring to a Colo  r.
      * If t   he    c  olor s  peci   f       ic  a      tion is bad   , an attempt
       * w    i     ll              be ma   de     to f ix it up. 
      */          
    static final        Color   hex           T    oColor    (Stri   ng     valu    e)               {
          Str        ing di gits;              
          int                   n =     valu e  .len   gth    ();
                  if (value.     s tarts Wi   th("#"  )) {
                 digits =             value.      s                ubstring(1, Math.min(     value.len gth (),  7   ));       
           } else {
            digits = val    ue;
         }
                St   ring     hstr = "0x"      + d   igits;
        Color            c;
                 try {
                          c =     C   olor.decode     (h      st     r)     ;
             } catch (  N      umberFormatEx    ceptio    n nfe) {
                c = null;
                                 }
                       return    c   ;    
         }     

    /    **
                        * Conve   rt    a co lo   r string  suc   h  a   s "RE   D     " or "#NNNNNN          " or " rgb(r , g, b)             "
       * to a Color.
          */
               static    Color     s                    tri           ngToCo      lor(String           str)   {
      Color colo   r       ;       

               if (str =        = nu      ll) {
                 retu   rn null;
      }
      if (str.leng   th() ==            0)
          co   lor       = Color.black;
             else if (str.startsWith("rgb(")         )     {
                           color      =    parse                          RGB( str);      
      }
      else if (   str.char       At(0)       == '    #    ')
        color = hexToC   o  lor(str)  ;
                 else if     ( str.e     q ualsI  gnoreCa  se("Black"))  
              color = hexToColor   ("#0   00000"      );
        else if(    st     r.equalsIg noreCase("Silver     "))
              co    lor = h   e       xToC    olor("          #C0C   0C0");
        el    se if(str.equalsIgnoreC     ase("Gr                 ay"))
            co   lor = hexToColor(         "#   8  08080");  
               else if(str.         equal        sIgnor      eC   a       se("White"))
                   color = hex     ToCo   lor("#FFFFFF");  
      else if(str.equa       lsIgnoreCas    e("Maro    on"))
        color = hexToColo    r     ("#   8    00    0 00");
           e     lse   i        f(str.equalsIgnor      eCase( "Re d"))    
                col   or = he    xToC  olor("             #FF00   0   0");
            else if(s    tr.e      qual  sIg n     oreC   ase      ("Purpl  e"))
        c      ol   or = hexTo C           olo     r("               #800  08      0    ") ;
        else if(str.equa    lsI g noreCase("Fuchs            ia"))
        color =   hexToColor("#FF00FF");
                 e         lse if(str.equalsIgnor  eCase("Green        "  )   )
            c   o  lo   r =    hexT     oC    olor("#00    8   000")              ;   
         else if(st  r.equa              lsIg       no   reCas   e("L     ime"))
        color =       hexToCo    lor("   #00F   F00  ");
      else if(str.equalsIg         n oreCase("  Olive")   )   
           c   ol          or = hexToColo        r("#808000") ;
        el se i      f(str.equalsIgnoreCase("Yel      low"))
         col  or        = hexToCol  or("#FFFF00    ");
              else if(s                 t    r.equa   lsIg    noreC       ase("Nav      y"))
                 c     o lor  = hexToC     olor(  "#    000080");  
      else if(str     .equalsIgnor  eCas   e("B     lue"))
             color = hexToCol  or("      #         000       0FF");
                  else          if(s    tr.equalsIgnoreCase("Teal"))
                  color =          hexToColo r(        "#008080  "); 
               e  lse if(str.      equ als       Igno        reCase("Aqua"     ))
              co  lor = he      xToCo  lor("#00FFFF");
       e  lse i f(str.equalsIgnor    eCa  s     e("O  range            "    ))
             color =   he xToColor( "#FF800       0")         ;
      else
              col   or = he      xToCo    lor(str)      ; // s   ometimes  get s  pecif  ied wi thout le  adin           g #
               ret  urn    color;
        }

      /**
     *            Par          s  es a Str        i  ng   in the format <code>rgb( r,       g,     b)<    /   c   ode> w   here
                 * each          of        t he Color components is either an inte    ger, o r a         floating number
      * with a % a   fter indicat        in  g a perc  e  ntage   val ue of 25  5.      Values are
     *  c o  n    strained to fi      t with 0-255. Th  e    resulting Color   is                 r  eturned.
               *   /
               private static Color pars   eRGB(   S         tring string) {
                       //   Find the      next   nume    r     ic cha    r  
        int[]       index   = ne  w int[    1] ;

                 index[0] = 4;
                       int     re d             =  g et C        o  lorComponent(s  tring,     ind  ex);
           int    gre                en =    getC    olo        rComp     one   nt(string,   index);
                          in     t blue = g     etColorComp        onent     (str ing, ind           ex    );

        ret  u  rn    n      ew   Color(re   d              ,        green, bl   ue);
       }    

    /*   *
        *     Return    s th          e n     e      xt integer     value fr   om <cod     e   >st     rin   g</c  ode   > start   ing
     *            at     <code>ind  ex  [0]</   c ode>. The v    alu  e ca                  n     either    ca       n  an inte   ger, or
     * a perc e      ntag      e (fl               oating     number end    i  ng with %), in     w    hich   c       as  e    i   t is
           * mu   l         t   iplie    d by    255.
        */
        private     st        a      t    i c int   getCo    l      orCompone  nt  (String strin  g, int[]     ind  ex) {
          int   leng        th    = s  tr   ing.length      ();
                   c   har aChar;

        // S  kip non-decim  al   ch                  ar       s
             whi      le(inde      x[ 0] < length && (aC h      ar =           stri  ng.c    harAt(index[0])    )      != '-' &&
                                 !      Charac ter.isDig  it (aCha           r)      && aChar != '               .') {
                             index     [0    ]+      +;    
        }

                 int star    t        = index[0]      ;

           if        (star       t       < l          ength && string.charAt(i  ndex[0])  = =      '-     ') {
             ind           e    x[0]++; 
           }
                   while(index[0] < l    en      gth       &&
                                         Cha        rac  t   er        .isD    igi     t(st    ring.char At(index[0]))) {
              inde    x[               0]++;
        }    
                        if (index[0]   < l  ength && string.c  harAt(in        dex[0]    )              =     = '.') {         
                         // De  c    imal value
                     in   dex[0]+  +;
                             wh  i   l  e(i   n  d             ex[0] < length &&
                                                  Charac ter.isDigit(strin   g           .charAt(in    dex[0])      )) {
                         i ndex[0]++;
                            }
        }
                         if (st   art != index[   0])              {
                                                          try         {
                                          float    va     lue = Float  .pa     rs   eFl      o      at(strin     g.      sub string
                                                                                          (start, i    n  dex[  0]))  ;

                                i   f (index[0]               < l   ength &  & s  tr    ing.charAt     (index[0])    =         = '%') {
                              index[0]++;  
                                       value =   valu    e   *       255f / 100f;
                                                     }
                          return             Math.min(255,           M  ath.max(0,   (int)    value));
                        }    catch (Num  berFormatException nfe) {
                                           // Treat as 0   
                 } 
                   }
                               return 0;
     }
 
    st   at          ic int     g           etI    ndexOf    Size            (                   f    loat p       t, int[]  s  izeMap) {  
            for             (int i =     0; i      <         siz   eMap.l      ength; i ++ )
                                          if (pt <= siz       eMap[i])
                                                r         et urn i    + 1;  
            retu     rn    sizeMap.leng  th;
        } 

    static int getInd      exOfS      ize(f    loat pt, S    tyleSheet    ss) {
                         int   []   s   i zeMap = (ss != null) ? ss.getSizeMap() :
                           St    yleSheet.siz eM       apD    efaul   t;
            return    getIndexO fS    iz    e(pt, si    zeM   ap);
                     }       


            /**
     * @ret  u      rn an arra            y of all the strings           in <co  de>value</co            de>
      *              that a       re separat   e   d       by wh      itespace.
               */
                 stati    c  Strin      g[] p      arseStrings(String        value) {
            int                       cu                     rrent, last;
                   int           le    n           gth          = (value ==   n   ull)          ? 0 :               va              lu  e.length()  ; 
                             V ecto      r<S      t       ring>    temp = n    ew     Vector<Str    ing>(4     );

                   current =                            0;
                      w     hile (cu    rrent <  length) {    
               //   Skip w    s
                                          w   h      ile (curren t <     length && Character.isW      hi      t       espace  
                                      (value.char   At(  curre   n       t)))     {
                  current+    +;
               }
              last =    current; 
                     whi  le (current <         lengt h && !Charac  ter.is  Whi  tespa  ce
                                (value.ch arAt(curr   e    nt))) {
                    current+       +  ;   
               }
                   if     (  last   !     = c   urrent    )            {
                          temp.           addEle  men  t(valu    e.s   ubstring(last, c     u  rrent))        ;
                   }
                            cu   rr e          nt         +          +;
             }    
             String[    ] retValue = new S  t  r  ing[temp.si  ze( )];
        t    e    mp.co    pyI   nto(retValue );
           ret u rn   r  etValue   ;
           }   

      /**
     *   Retu  rn the      point si         ze         , gi ven   a size i    ndex. Leg   al  HTML in dex     siz      es
           * are                   1-7.
     */
    flo  at getPo       intSize(int i    n        dex,    StyleSheet s    s        )                {     
                     s     s =    getStyleSh  ee t(ss);   
            i     nt[] si   z    eMap =          (ss != null) ?    ss.get     Size   Map() :
                   StyleS     heet.si      zeM    a     pDef      ault;     
                               --   i      ndex ;
         if (index < 0)
                  return sizeMa  p         [0];
            else if (       index >    s       i zeMap.length   - 1)
                          return sizeMap[siz    eMa                            p.length - 1];
                    els   e    
              return sizeM  ap[in  de      x];   
             }


     private void translateEmbeddedA    t  tri     bute    s(Att    r            ib uteSet htmlA ttr  Set       ,
                                                                              Mutab  leAttributeSet c  ssA ttrSet) {
        Enu       mer   ation keys = ht   mlAtt r       Set.getAt    trib     ut          e   N      ames();
             if    (htmlAttrSet.getAtt             rib  ut   e(StyleConstants.NameAttribute) ==       
                HTML.Tag.HR) {
                          // HR nee ds sp     ecial handl       ing due to    us tr       eating  it      a   s a  leaf.
                  tran  slateAtt     ributes(H  T      ML .Tag.HR, h  tmlAttrSet    , cssAttr     S          et   );
           }
        w  hile      (k    eys    .ha    sMoreElements()) {
                                   Object key = keys    .n   extElement     ();
            if (key ins     t    anceof HTML. Tag) {
                        HTM         L.Ta  g       tag = (HTML  .Tag     )       k e     y;
                                                  Object o = ht    mlA ttrS    e   t.getAttribut         e  (   t             ag)  ;
                                 if (o != n  ul                   l    &&         o          insta   nce of        Attr  ibut                              eSet) {
                         tran   slateA    ttri  butes(      tag, (AttributeS  e        t)o, css             A      ttrSet);
                                 }
             }              else if (  k  ey instan     ceo  f C   SS.Attribute) {
                  css   AttrSet.add   A   ttr  ibute   (key,      ht             mlAt     trSet.get    A   tt   ri bute(ke y))  ;
                          }
             }
    }

    p  rivate       void     tr   a nslateAtt     ribut      es(HTML.T           ag ta  g,
                                                                                  A  ttributeSet htmlAttrSet,
                                                                          MutableAttributeSet   cssAttrSet) {
            En    umeratio  n     na   m  es    = ht       mlAttrS     e  t.getAttributeName         s ();
        w   hile   (n  ames.        hasMo    reElements())    {
                                            Ob      ject name =    n  ames.nextE   lement();

            i              f            (name ins     tanceo  f HTML.Attrib  u      te) {
                                 HTML.Attri     b        ute     key = (HT  ML.A tt      ribute)name;

                   /*
                        *    HTML.At   tribute.    AL  IGN needs           spec  ial proces  s   i   ng.
                      * It       can       m    ap to to        1 of   many(3        )    possible    CSS a      ttribut                   es
                                           * depend                ing on the nature       of           t    he           tag  th        e   att r      ibute    is
                              *         part off and depending on  the value     of the at  t ribut     e  .
                           */       
                     if (k ey  == HTML.Attri  bute.ALIGN) {
                                   String h t  m   lAttrValue = (      St      ring)htmlAttrSet.  getA tt    r     ibut  e  (HTML.Attribute.AL    IGN);
                                              if (htm   l   AttrV        a lue !=        null) {  
                                                     CSS.A    tt               ribute cs         sAttr = getCssAli gnAttribute(tag, htmlA  ttrSet);
                                       if (cssAttr     !=    nul l) {    
                                                  Obj               ec  t o = ge       t  Cs sV  alue(cs s  Attr, htmlAttrValu   e);
                                                                    if (o != n   u   ll)      {
                                                               cssAtt  rSet.addA    ttribu   te    (cssAttr, o);
                                                          }
                                     }
                                         }
                } else {  
                                       if (key ==  HTML     .Att           ribute.SIZ   E    &&     !isHTML                    FontTag(    ta   g)) {
                                                   /*
                                           *     Th       e     html siz    e att  ribut   e has a          m    a p  p                i  ng in the CSS wo    rld      only
                                           * if it i       s         par of a   font or  bas     e fo  n  t tag.
                                    */
                              } e      l   se                   if   (tag ==  H   TML .Tag.TABLE   && key == H   T  ML           .Attri   bute.BORDER) {
                                                             in     t borderWidth   = g    et  T   ableBorde r(htmlA   ttrSet);

                                if (border  Width   > 0) {    
                                                   tra   n                 sl    at     eAttribute   (HTML .Attri    bute.BO    RDE             R    , I     nt   eger.toStr        ing(    bor        derWid    th), cssAtt rS         et);
                                      }      
                                              } else  {             
                              trans     lat  eAttr    ibute(         key,   (Stri                ng) html               Attr     Se t.getAtt            r         ibut     e(ke y)    , cssAttrS   et);
                                                                     }
                            }
              } els      e i                          f (n      ame ins      tanceo f CSS.At      trib    ute)  {
                       cssA  tt      rSet.addA t  tri  bute(name, htm   l     AttrS  et       .getAttr  ibute(name)  );
                              }
           }
    }

          private           void trans  l   ateAttribut       e( HTML.A  ttribute    key,
                                                                                       Str  ing         htmlAtt   rValue,
                                                                               MutableAttributeSet c ssAt   trSe      t) {
           /*
                   * In   the case o    f        all    remaini   ng HT    ML.Attrib         ute's t  hey
          * m  ap to 1 or   more CCS.At         tribu   te     .      
                        */
                     CSS.Attribute[] cssAtt    r   Lis   t = getCssAttribute(key);

               if (cssAttrList =         = nu    ll   || h     tmlAttrVa  lue == null) {
              return;
               }
                                  f  o     r (Attribu    te   cssAttr  :    cssAttr   List) {
                                         Objec      t o =     ge   t  CssValue(css   Attr    ,    html  At           trValue);
                     i    f   (o != n   ull  )    {
                                   c  ss         AttrSet.addAttribute   (      c    ss  Attr    ,      o);
                  }
              }
    }

    /**         
                  * Gi v     e     n a C  SS    .A ttri b       ut   e obje  ct and       its corresponding        HTML                          .Attribute's
         *                      value, thi           s      metho d  returns a C           ssValue             obj       e    ct    to           a s        sociate    with the
     * CSS    attrib  ut   e.
                               *        
                    * @param the CSS.Attri    bute
     *          @pa  ram a String    containin g the v alue ass          ociat     ed HTML.At      tribtue.
      */
         O   bj   ect getCs      sVa lue    (CSS.At    tri  b  ute cs  sA        ttr,            String htmlAttrValue) {
             CssValue value  =  (CssVa lue)val  ueConv     er   tor.g              et(cssAtt       r   );    
                     Object o               = valu   e.par seHtmlValu    e(ht      m             lAttrVa    lue);
        retu    rn    o;
    }
    
    /**
              * Maps      a         n HTML.Att      r  ibut    e object to its appropriate  CSS.At  t       r   ibutes.
        *
     * @param H  T     ML.Attribute    
       * @r      e  turn            CSS.Attribute   []
                                  */
    priv        ate      CSS     .Attrib   ute[]        g            etCssAttr           ibute(HTML.At   tribute   h   Attr) {
                        return htmlAttrToCssAttrMap.ge            t(hAttr);
    }

         /**
           * Maps HTML.Attribut            e.AL  I  GN to either:
     *           CSS.Attribu           te.      T      EXT     _ALIGN
        *     CSS.At trib       ute          .FLOAT
     *           C  S  S.Attr  ibute.VERTICAL_ALI        GN
               * based    o     n    the     tag a    ss    ociated with the attribute an    d the
                         * value of the a      ttribute  .
                   *
               * @p       aram AttributeSet containing HTML    att   r   ibutes      .
        * @retur  n               CS   S.A    tt     rib  ut       e ma  ppi        n   g     for HTML.Attribu        te.  A    LIGN.
            */
    priv a     te CSS.Attribu               te    getC   ssAlignA    tt  rib         ute(HTML.Ta         g tag,
                                                                                    At         tributeSet                html    Att  rSe t)   {   
                            ret    urn CSS.At  tribute.TEXT_  AL   IGN;
/*
             Stri   ng h      tml    AttrValue = (String)h        tmlAt  t rSe   t.getAttribute(HT    ML.Att   ribute.ALIGN);
         CSS.Attribu       te         cssA   ttr = CSS.At tribute.TE XT_AL    IGN ;  
          if    (htmlAt  t rValue   !  =     n   u          ll && ht            mlAttrSet               instan       ceof Element) {
                            E lement elem = (El         em     ent)htmlAttrSet;
                    if    ( !    elem    .  i    s         Leaf() && tag.           isBloc   k  () &&  va lidTextAli gnValue  (htm  lA    ttrVal ue)) {
                          return CSS.A      t      trib    ute  .TEXT_AL  IGN;
                                  } el     se if (isFloater    (htmlAttr  Va  lue)) {      
                               retu rn         C       S            S.Attri   bute.FLOAT       ;
                 }     else if  (e     lem.i    sLea   f()  ) {  
                                         r  eturn CSS    .Attribute.VERTICAL_   ALIGN;
            }
         }
            re       turn nul       l;
        *    /
              }

        /**
          *       F   etch     es th  e    tag    asso          c        iate   d with   the HTML At   tr          ibut         eSe    t.
     *
     * @p  a        ram         AttributeSet      containing th   e HTM  L att    rib  utes.
                              * @return     HTML.   T    a             g
             */
    pri v   at  e HTM             L.  Tag getHTMLTag(A   tt ribu       teSet htmlAt trS               et)  {
           Obj    ect o      = ht      ml    A    ttr    Se    t.getAttribute(St  yleCo  ns  ta          nts.Na meAttribut         e)  ;
         if (o              instanceof   H        TML.    Tag) {
                        HTML.T   ag tag = (H      TML.Ta   g    ) o ;
              ret urn   tag;
                                }
                  return        null    ;
    }


      priv    a       te b  ool   e an isHTMLF            on  tTag(HTML       .Tag tag     ) {
             r  eturn        (tag != null    &     & (( tag =      = HTML.Tag.F       ONT) || (tag == HTML      .Tag    .BAS  EFONT)))     ;     
    }
   

    pr       ivate         b oolean       isF      loater(String        ali  gnV             alue) {
                         return    (    alignValue   .  e   quals("le    f     t")       |      | align   Value.equals( "r          igh  t"));
    }  
 
    priva     t    e bo  olean   val i  dT       ex   tAlig    nValue(Str   ing alignV   alue) {         
             return (isFl   oate  r(a lignValue) || al     i     gnValue.equals("cente       r"   ));
    }

    /**
     * Base c lass            to          CSS    va    l     ues in th      e attribute sets.         Thi         s
     * is inten     ded     to         ac       t as a convertor to/fro  m other at    trib ut         e
                  * for    mats.
              *     <p>
                 * T he CSS parser uses the parseC   ssValu   e method  to        conve    rt   
           * a st       ri   ng to whatever    fo   rm    at is ap      prop    r  ia  te a    given key
       *            (i.e. t   hese conver tors          are stored in        a  map usi      ng    the
        * CSS.Attribute      as    a key a  nd   the        CssValue as t         he     value).       
     *                     <p>
                    * The                       HTML          to CSS c  onv              ersion process f    irst converts the
                     * HTML.Attri  bute       t  o a CSS.Att     r     ib   ut             e, and th    en c   alls          
     * the pa    rse    Ht  mlVa    lue                          metho   d on th                e value   of the       H T    ML
     * attribu     t      e t  o pr  od        uce the corre  sponding CSS value.
             * <    p>
     * The Style    Constants to CSS conv    ersi     o             n   process fir   st
     * conve     r   ts the StyleC ons            ta   nts          a   ttribu    te           to a
       *    CSS.Attribut   e,  and t  he     n  cal    ls th  e            f       romStyl    eConsta   nts
     * met       hod to co                 nvert the StyleConstan   ts value to     a
          * CSS  valu    e.
            *   <p>
                           *        The CSS       to St            yleConstants    conver   s  ion pr ocess first
            * conver     ts         the Sty    leConstants    attribute to     a
     * CSS.Attri       bute,    and th        e   n calls the       to StyleCons  tan   ts
                               * method  to c    onvert    t  h   e CSS         value to a StyleConst    a   nts
                      *   va   lue.
            *     /
    sta   t  ic class Cs                sValue implem   ents    Seriali       zable {

             /**
             *        C                          onvert a CS        S     value   s   tring       to     the internal      format
                         *  (for f   ast pro     c e ss  ing    ) used in the attr   ib  ute sets.
                            * The fallback st         o  rage for any value t  hat   we do  n't
             * have a      s       pecial bin    a   r  y format for     i s    a   String     .
         *   /
               Object          p     a         rs eCssValue(String value)  {         
               retu  rn   va    lue;  
                                           }      

          /  **
           * C     onvert an HTM       L att    ribute value to a CS  S                   att        r  ibute
                       *  val   ue.            I f the      r          e     is no conver     sion, return nu   ll        .
                           *   This i     s impl      ement  ed t  o simply forwar  d to the      CSS
             * parsing b     y defa      ult     (since some o         f the a    ttr   ibute
                                          * va  lues are th   e s    ame     ).    If the a ttribu     te  v   al          ue
               *   isn't   re   cognized as a            CSS  value it is   gene    ra     lly
           * re      turn      ed   as null.
                  */
            Object     parseHtmlVal  ue(S           tring    va     lue)         {
              return    pa rseCs  sValue(value   );
        }

                   /**
                       *     Conver    ts a      <co de>StyleC   o   nsta    nts</cod       e> att    ribut e value t  o
                 * a CS   S attri bute value.  If there is n   o conversio                      n  ,
                         * returns <code>          null</cod    e>.           B   y default, t     here is no conver sion.
            *
                *  @param k    ey th       e <code  >       S   tyle         C onstants</code> attribute
         *       @p     ar  am      v     al            ue the val   ue of a         <   co     d   e>Styl  eConstan       ts</c  o de>
                     *   a       ttribu       te to be                          co  nv    erted
                                       * @retur  n       the CSS va       lue that r   eprese       nt   s     the    
                                         *   <   co                    d    e>StyleC      onstan    ts  </code> value
                                */
              Obje  ct       fro      mStyleC   ons          tants(S   tyleCo     nstan   ts key, O    bject           value)            {
                        r  etur          n null;
                     }     
  
        /    *       *     
         * C    o      nverts a  C    SS    att   ribute va   l  ue to a
                      * < co   de>      Style   Con  stants   </code   >
                 * va   lue.       I   f th  er   e     is no conversion, re   turns
                     * <cod     e >nu    ll     </code>.          
                           * By defa  ult , there     is   no conversion.
           *
           * @pa    ram key   the            < code>StyleConsta nt    s</code  > attr                       ibu   t          e  
              * @para       m v      the view     con    tai         ning <                code>AttributeSet</code      >
                          * @ret   urn   the  <c  od  e> StyleCo      nstants<    /cod                 e> attribute value tha    t
                *         r       ep      rese    nts                 the CSS    att   ribute valu   e
              */
           Object toStyleConstants(Sty      leCon   stant s key, Vi       e     w v) {  
                 return        nu   ll      ;
                   }

                           /**
                *  R    e      turn        the C    SS format o     f      the valu             e
                             */
        pu  blic   String toString  ()       {
                      return   sva lue;
                    }     
    
                            /**
              * The value a            s         a s  tri   ng... bef  o  re c  on  ve              r   sion to a
                  *      binary format.     
                              */
                 String   sv                  alue  ;
    }

       /**
       * By      de   f   ault   CSS    at  tribute          s a re     rep rese          nted as simple
                      *   strings.  They also h ave n   o conv                   ers     io   n     to/fro  m
      * StyleCons  t   a nts by   defaul     t.     T   his   class repre     sen   ts the    
         * value a    s  a     str   ing (via      the su    p   erclass  )        ,    but
     * provides Style                Constan  t    s conversion supp   or  t for the
                      * C SS attrib       utes t    hat are                   held as     string    s.
     */
                    st    atic     class   St ringValue e      xtends CssValue {

         /*   *
                       * Conv  e   rt a CSS v    alu       e string to   th   e inte   rnal f       ormat
                                   *         (for fast processing)                      used in the attribu  te s                   ets.
                            * This p   ro  du          ces           a St    ring Va        lu            e,   so that it   can   be
         * u  s   ed to conve                           rt fr om CSS t                    o StyleConstants values     .
         */   
        Object  pa  rs      eCssValue(S   trin      g  v   al    ue) {    
                   S tringVa  lue sv = ne         w S  tr             in      gValue    ();  
                      s v.s        value =          val   ue;    
               r        etu  rn sv;
               }

        /**
                   * Conver  t       s a <code     >StyleC    onstan  ts</c      ode> at  tribute     value to
                * a CSS  at     t ribute value.  If t   here                            is        no conversion
             *   retur               n   s <code>      null    </c      ode>.
                 *
               * @param k           ey t  he       <  c   ode>StyleConstant      s     </co  de> attribute
                      *     @param       val               ue th    e                  value of        a   <code>      StyleCons     t       ants   </code>
               *         attrib ut     e to be conver     te    d
              * @    return the       CSS va   lue that                   r     epresent  s the
         *      <c   od  e   >Styl    e       Con  st  ant   s</   co   de>    value
                   *   /
                    Object fr      omStyl   eConstants(Sty  leCons   tants key,             Ob je          ct                value      ) {
                                   if (key    == Sty   leConstants.I   ta  lic) {  
                       if (   va          lue.     equals(Boolean.TRUE)) {
                        r       eturn     p   arse  CssVa   lu    e( "i          talic");
                     }
                                        r      eturn       parse   CssValue("");  
            } else if (   key ==     S        ty    leCons      ta nt           s.Under  l         ine) {
                              if   (val ue      .  e  q        ua                      ls  (Boolean.      TR        UE     )) {
                                                  return parseCss    Valu   e("un         derline");
                                      }
                          r   etur  n pa          r               seCs   sValue("");
                   }  el  s   e if (key == S tyleConst  ants.Al          ig   nment ) {
                              in   t align = ((Integer)value).i       ntVa l           u      e();
                       String     ta;
                                      s       witch(ali        gn) {
                          case St    yle     Co           nstant      s      .ALI  GN_LEFT:
                                       ta    = "left";
                                                               bre ak;
                   ca  s e        StyleConstant    s.ALIGN_RIGH          T   :
                       ta =  "rig        ht"  ;
                                              break;
                    ca   s       e Style      Co   n  st      ants         .ALIGN         _      CEN  TER:
                                                     ta  =    "center";
                    break;
                                  ca      se St   yleConsta   nts.ALIGN_J    USTIF       IED:
                                    t    a =     "j        ustify"  ;
                                                                           break;
                              defaul   t:
                             ta              =     "left";   
                        }
                               return     par    s eCssValue(ta);
                 }     else if   (key                      ==  StyleCo  nstants   .Str    ikeThrough) {
                                                                       if (v   alue.equ als(Boole   a       n.TRU   E)   ) {
                                        r   etur n    pars e  CssVa   lue       ("l  ine-thr  o    u    gh");
                              }
                                     ret    ur  n parseCssValue("");
                         } else if    (         key == Sty  le       Constant       s  .S     upe   r   script) {
                           if (v   a          lu   e.equ          als(Boolean.TRU   E   )) {
                                  r      eturn parseC    ssValue(" su                   per"  );
                   }
                                  retur    n pars  eCssVa lu          e("  ");    
                     } else    if (key == Styl    e    Con   stants.Su  bsc   rip t) {
                         if (va                              lue.equals(Boolean     .T   RUE)) {
                                    return parseCssValue("su  b")            ;
                                       }
                              r   eturn parseCs    sValue(""   ) ;
                         }
                                  ret    urn      nul        l       ;
               } 
    
                          /**
                             * Conv    erts a       CS S attribute     value to a
                    *       <code>    StyleCons   ta  nts</          c   o      de>                  value.
            *    If     the      re i        s no conversion, ret             ur         ns <cod   e>nu      ll</co  de>.
         * By de       fault,  th    ere is no co    n   ver  sion     .  
            *
            * @par      am  key     t   he   <code>Sty    le  Cons   t  ants< /cod   e> at    tribute
           *  @r etu   rn the <  co     d    e>Styl   eCon   stants</           cod  e > attribu   te     v alue t     ha  t
                         *        r   ep   rese    nts     the CSS  attri   bute         valu   e
                                 */
           Object       toStyle  C  on    stants(St    yle     Cons  tan  t  s key, View     v) {
               if     (    k          e   y        == S      ty l eCons tants. Ita      lic)   {
                                          if (    sva  lue.index Of("italic      ") >= 0           ) {
                             ret    ur    n  Boo  lean  . TRUE;
                       }
                     retu    r             n    Bo   ole    an .FALSE;   
                          }       el  s     e if            (key =  = St      yle   Const ants.Unde rli       ne)  {
                        i       f (         svalue.i  nd  exOf("underlin      e") >=  0) {
                                                        r     et      urn Bo         olean.TRUE;
                                               }
                           r    et               ur         n B  oolean.FALSE;    
              }   else if (key == S tyleC             onstant          s.Alig         nmen     t )      {  
                        i       f (sval  ue    .equa     ls("   right"))   {
                                  return new Integer(S   tyleCon   st ants.ALIGN_R I GHT);
                      } e        ls e i     f    (sva      lue     .     equ    als("center")) { 
                                           r          etu     rn new Inte           ge r(StyleCon   st an  ts.    AL         IG      N_CENTER);
                } el          se if  (svalue.e quals("            justify"   ))          {
                                           r  e   turn     n   e  w I     nt   eger(Styl          e      C   onstan   ts.   A   LI      GN    _J U STIFIED);
                       }          
                      return new I   nteger(Style C       o  nstants   .ALIGN_LE  FT);
                     }    e       lse if       (k  ey == S   ty l   e C   onstants     .  St rikeThroug                       h) {
                                     if       (svalue.indexOf("line                -t     h  r   o           ug    h"    ) >= 0 ) {
                                 r    eturn Boole        an.TRUE    ;
                                 } 
                      return Boolea   n  .  F  ALS    E;
                                 }       e    ls  e i f (ke  y        =        = Sty le            Consta     nts.  Superscr      ipt) {
                             if (svalue          .     inde     x     Of("     su    p        er" ) >= 0) {
                              return   Boolean.TRUE;
                   }
                                        return Boole an.FALSE     ;
                } el                  se     if    (key == St               yleConstants.      Subscript)     {    
                    if (sv   al ue  .   indexOf("sub") >= 0)    {
                         retur n Boolea     n.TR     UE;
                     }
                                                             r  etu   r   n   Bool  e       an.FALSE;
                    }   
                   return      n  u    ll;
                   }

                       // U   se       d     by ViewA    t   t    r ibuteSet
          boolea     n  i    sItali  c()  {
                    r     etu    rn (s   value.in        dex     O     f("italic    ") !  = -1);
                     }

         bo  ole      an isStrike() {
                   return (svalu  e.index    Of   ("l        i    n    e-through") != -1)  ; 
          }  

                 bool    ean isUnderline(      )   {
                                     return (sva   lu               e.indexO   f ("u nderl  ine    ") != -            1)    ;           
              }

          boolean isS ub() {     
                  return     (svalue.index               Of("sub") != -1);
                             }
 
          boolean        i sS up() {    
                             re       turn      (svalue.    i           ndexOf     (   "sup" )    ! =        -   1);
                   }
       }
    
              /* *         
     * Rep                   re  se nts a val  ue for th  e CSS   .FON          T_SIZE attri  b    ute.
         * T            he binary format          o         f       the v  alu   e can be             one of      seve    r  a   l
               *      t     ypes .  I     f th       e type is              Float  ,
     * the   v  alue is    s          peci    fied in t   erms of   po  int or
     *                  percentage, depen      di      ng upon     the       en   di n   g of the      
                  * asso        c iated st        r   ing.
                *  If   the type i         s Int                   ege  r,    the val   ue is              s peci       fie   d
            * i     n terms o  f a si     ze         index.
           */    
    c      las         s     FontSize   extends CssVa        lue {

                /        ** 
                * Returns the s   ize       i n points.            This             i      s ultima   tely  
         * what we       need for     the purp   ose of c     rea   ting/     fetc    hing
              *                 a Fon      t   object.
                  *
             *         @param a        the at                t  ri       but               e set t he val       ue is being
                                    *     req      ue                 sted f  rom   .  We ma   y n    eed to w    alk                  u  p   the
         *  resolve  hierarchy if it        '   s rel  ative          .
                          */
        int     getValue(Attribute  Set a, StyleSheet ss  )    {
                      ss =       get  StyleSheet          (s     s           );
              if (index) {
                                         // it's an index,            translate from si     ze     table
                                 ret    urn Ma  th.r   ound(getPointSize((int) va                  l   ue,         ss)       );                
                        }  
                         else if (lu ==            null)   {      
                    return Math.ro   und        (va    lue  );
                }
                  els    e {
                       if (lu.type      == 0) {
                       bool    e    an     isW3CLength        U    nits = (ss  == n     ul   l)   ? f alse : s  s.isW3CLengthUnits              ();
                                 re  tu rn Math.roun  d(      lu     .g etVa   lu        e(i sW3CLengt   hUnits))         ; 
                                     }
                                     if                   (a !=    n               ull) {
                                 Attrib   u  teS    et resolveParent =         a.get          Res              o       l       ve  Par       ent   ();

                                                                  if    (resolv              eParent               !=  n               ull) {
                               int p    Value = Sty    leC   on                stants.getFon tSize(  resolveParen  t       )    ;

                                        float     retV          alue            ;
                                     if (lu. type == 1 || lu.t ype == 3)    {
                                              retVa  lue =   lu.va          lu        e           *     (float)pV    alue            ;
                            }
                                    else      {
                                                                                  ret       Value =    lu.          val     ue        + (float    )pVa                lue;
                                         }
                                   retu rn       M  ath.round(ret           Va                     l    ue);
                    }
                }
                   / / a is null,         or        no resolv      e     pa      re                    nt.
                        retu rn 12;
                    }
                      }

                 Obje    ct parseC    ssVal  ue(       String value)         { 
                        FontS            ize fs = n  e  w FontSize    ()    ;
                                  fs    .svalue = va     lu  e;
                           try {  
                        if (value.equa ls("x        x-     small"))          {
                                                       f s        .value =    1;
                                                             fs  .    index =  true;
                        } else i   f   (v   alue.equals("x-        smal   l"))  {
                                                     fs.value     = 2          ;               
                                           fs.inde x = true;
                                      }                    els    e if                   (va      lue.equal  s  ("small  ")) {
                            fs.value    = 3;
                                          f   s.in      dex   =        tr   ue;
                                    }          el    se if (val   ue.e  qual  s("  medi    um  ")) {           
                             fs.valu             e = 4;
                          f  s.inde     x = true;
                                            }   else   if (value.e     q uals("la   rge       ")) {
                                                 f s.v    alu    e   = 5;
                           fs.i   ndex = true;     
                     } el        se if (va lue  .      equals("     x-larg          e")) {
                                  fs.value =     6;
                              fs.in  d ex = true        ;
                  } else i f (   value.equals("xx-  larg   e"))         {
                                              f  s.value = 7;
                                                       f s.i    ndex =  true;       
                           } else   {
                                          f                s.lu =      new LengthUnit(value     , (sh   ort)1    , 1f);
                                   }
                                         // r  e    l ative s          i zes, larger | smaller (ad just    fro     m             paren         t by
                                                     //             1 .5 pi  x  els)              
                  //    em  ,      ex refer    to pa  r  ent      sizes
                               // l     eng      t   hs: pt,           mm, cm,              p  c, in,    px     
                    //                         em (f  ont height 3em     would b  e 3  times f  on  t height    )
                                                                 /   /                          ex (heig   ht    of   X) 
                       //     le   ngths           are (+/-) fo  llowed by              a   number and        two      l   ett    er
                    /         /   un  it   id  entifie      r
                } catch      (Num     ber                      Format        Excep  tion nfe) {
                                          f s    = nu      ll;
              }
                      re turn    f  s;
                        }
   
        Obj    ect par  seHtml  V al             ue   (St ri  n   g value)     {
                    if ((va   lue == null) || (value.length   ()  =    =      0)  ) {
                        return nul         l;
                  }
            Fon    tSi ze fs = ne          w    FontSize   ( );   
                           fs. sv   alue     = val   ue;

                         t     r    y {     
                              /*  
                 * rel   ativ   e size    s in     the size attribut     e a   re      re lative  
                       *               to the <base               f    on       t>'s si                  ze.   
                                    */        
                                                      int baseF  ontSize =   get   Bas  eFo     ntSi   z     e();
                                       if  ( value.ch    arAt(     0) == '+') {
                         int r      elSi    z  e       =                I    ntege      r        .      valu     eOf(value.su         bstr        i          ng(       1)).     int V                alue()    ;
                               fs. v     alu  e   = bas   eFontSi  ze   + r  elSiz e  ;
                             fs.index = true;
                                } else if   (value. c har    At(0) == '-'      ) {     
                                             int r   el Siz        e         =      -Integer.va    lueOf(va   lue   .sub        s   tri      n     g(1   )).intVal             ue();
                              fs.    valu   e        =   base    Fon   tSize    +      r elSize;
                                            fs.inde   x   = true;
                             } el     s    e {      
                                                        fs        .v  al               ue =          Int             eg       er.parseInt(value);        
                       if (fs.value     > 7) {
                                                                   fs.value = 7;
                                                        } else                            if (fs.value < 0    )          {
                                                                    fs.val   ue =    0     ;     
                                  }
                                              fs.index = true;
                                                  }    
  
                           } catc h                 (NumberFor   matEx                          ce   ptio  n n f     e) {
                    fs = null;
                  }    
                    r  e         turn fs;
            }

             /**
                 * Converts a     <code>StyleConsta   nts</code>     attribute       value to
         *    a           CSS attribute value.   If there is no  conversion
             * re tur  ns <   cod     e    >nul         l</cod   e    >                  .  B  y def ault,     ther      e     is no c   onversion   .
            *
               *            @pa                      r   am key the <co    de>StyleConsta   nt    s</code> attr        i       bute
                 *         @pa              ra    m value        t       h    e   value of a  <code>StyleCon s           ta   nts</code>
                  *        attribute to be co    nv   e   rted
                                     * @return the CSS value       t            h    at represents                        the
         *            <code>StyleConstants</   code>     value
                       */
             Object fromStyle           C    onstants   (StyleCons          tants k            ey, Objec    t value)                {         
                         i    f    (v   alue in           st   anceof   N     umber) {
                  FontSize fs =  new Font  Size();  

                                           fs.va lue =  ge     tI     ndex          OfSi      ze((            (Nu   mber)value         ).             f  lo                     at     Value   (), St  yleSh  eet.sizeMap  Defa   u   l   t  );
                         fs.  svalue    = Integer.toStr    in   g((int       )fs.va    lu    e);
                          fs    .i                   ndex =  tr     ue;
                                    return fs;
                                     }
                          return   p     ar     seCssVa              l   ue         (va lue.toStri ng( ))       ;
          }

                      /**
                            *    Conver                    ts   a   CSS    attribu   te va  lue            to   a        <code>Styl        e Constan        ts</cod  e>
                * v   alue.   If there is no conve  rs  ion              ,         re  t             urns      <code>nu  l     l<  /     code>.          
                   * By  d   e fault,     the            re is no co         n        ve             r  sio            n.
              *
                                 * @param key the <code>StyleCons      ta     nt            s<                       /code>   attri              bute
         *         @r    e        t    u           r     n the <code>Styl     eC   onstants </code> a    ttribute        value  t  hat
                *       re    presents the CSS attri    bu  te value
                            */
                           Objec t toStyleCo   nstants(StyleConstant    s key  ,      V   i     ew    v)     {   
            if (v        != nu      ll) {
                                     return Intege       r      .va    l       ueOf     (ge t  V alue(v.getAttribu            tes     (), null));
             }
                  re     turn In     t  e               ge   r.v   alue        O  f(getV   a     lue(nul  l, null) );
                   }

        float value;   
           boolean inde x      ;         
         Len  g  thUnit l     u;
    }

          st    atic     class FontFam  ily e  xt  ends Cs  sVal        ue {

                         /   **
                     *  Returns    the font fa                mi      ly to use.
               *     /
                      String getValue() {
                            return        f   a  mily;
             }

                Ob       je     ct par    s   eCs    sV    alue(St    ring val  ue) {
                     int cIndex = va    lue.index     Of      ('    ,'     );
                 FontFamil  y           ff      =    new FontF   a           mil   y(   );
              f             f.s    value    = val   ue;   
                                    f       f.fa       mil                           y = null;

              if (  c   Index == -1)               {
                  setFontName  (ff    , val               ue  );
            }
                            else {
                                             bool                  ean    done =    false;
                                     int lastIndex;
                            int length    = value.length( );
                                      c          I ndex      =  0;
                   wh     ile     (   !done) {
                                             //    ski  p      ws.
                                                w    hile ( c Index < len   gth &&
                                                            Character.i  sW   hitespace(value.ch a rAt(cIndex       )))
                                        cI  ndex       ++;
                                              // Find      next     ','
                                  las   tIn     dex =      cI    ndex;         
                       c  Ind e                      x = v    alue.  index  Of(      ',',   cIndex)               ;
                                          if (cIndex == -   1) {
                                cI              ndex = len    gth;      
                                                                   }
                               if (last   Index      <     l   ength) {
                                                  if       (l       ast   I    ndex !=     c  Index) {
                                                             int    l     a     stCh      a        r Index = cIndex;
                                               if            (c        In   dex         >     0 &      & value.ch    arAt(cI     ndex -     1) ==     '   '){
                                    las  tCharInde x-   -;
                                                   }
                                                                 setFontN   am     e   (ff,          valu e  .s         u bs    t          ring
                                                                      (lastI  ndex, lastCharIndex));
                                                                don     e      = (      ff   .family !=    null   );
                                    }
                                                        c   Index++     ;
                                           }
                       el  se           {
                                                               don  e = true    ;
                               }
                                                   }   
                         }
                       if   (f f.fam  ily =          = null    ) {
                                      ff.family   =       Fo   nt.SANS_SER IF;
                       }  
                       r  eturn  ff;    
        }

          private void set    Fo   ntName(FontFamily ff, String fontN      a   m   e      ) {
                    ff.f am  i        ly =    fontNam      e;
               }

            Object par se  Ht mlVa                  l     ue(Strin      g         value)         {
                     / /       TB D
                         ret   ur   n par   seC   s      sValue( valu  e     );
                   }

          /**
             *       Converts a <  cod e>S      tyleConst       ant   s</code> attr ibu    te valu           e to
                 * a   CS   S      at  tribute value.  If     t         here     is no conv   ersion
            * ret         urns <code>   nu     l  l</code>.  By de  fault, t     here is no             conversion.
              *
                       * @para m  key   the <code>     StyleCo    nstants</code> attribute
                   *      @pa            ram   value     the     v   alue of a  <co de>S       t           yleC              on          stants</           c    ode>
         *   attribu   te to be conv    erte d
         * @re turn t      he    CSS v     a           l ue that represents the   
           *   <code>Style     Constan   ts</       code>    value
               */
                      Obj   ect fr  omStyle  Constants(St     yl eConst  ants   k  e  y, Object value   )   {
                           r    et    urn                      p arseCssValue(val    ue.t    oString()    );
                                 }

               /**
                    * Co nve    rts a CSS a    ttri   bute   value to  a <code          >St     yleCon sta  nts</code>
          * value.     If    there is no conversion, re            tu    r           ns <      code>            n   ull</code      >.
                       *        By defa   ult, t     here is       no conv er     si  on.
         *
           *          @    p ar       am key the <code>StyleConsta  nt          s</c o      de>       a  ttribute    
               * @r   etu   rn   th      e  <code>StyleConst  an          t     s</code> a           tt     rib   ute value that
           *      repr                esents the CSS       attribute      va     l   u      e
             */
        Ob  ject     toStyle   Constan ts(  StyleConstants key, Vi        ew v) {
                 return family;
             }

                    String       fam     ily   ;  
       }

    static class FontWeig     ht e  xtends CssVal                               ue {

            int getVal    ue() {
            retur      n w         ei   ght;
               }

               Object par   seC     ssVa    lue(     String va   lue) {
              Fon   tW      ei                  g     ht       fw =    ne     w Fon   t           We  ight();
                        fw.sv a      lu  e   =   value;
                      if (     valu  e       .equals("bold"))     {
                                   fw.weight = 700;  
                         }         else if          (value.eq ual   s           ("normal")) { 
                           fw.w  eight =         40   0;
              } e     lse {
                            /     / PENDING(prinz) a  d     d supp  ort for          relati     v     e values
                 try   {
                          fw.wei     ght =   Inte  ger.parseIn     t(        valu  e);
                       }  c    atch (NumberFor matExcepti  on n     fe) {
                               fw =    null;
                             }              
                         }
                             return fw  ; 
                       }

                            /**
                    * Converts       a <code>StyleConstants<    /   co     de> attri    bute value   to
            * a CSS                attrib    ute val ue.  If there is no co     nver   sion
                 * returns <code>n              ull</      c             ode>.  By de    fault,  th  ere is  n     o c  onve    rsion.
           *      
         * @param key t  he <   code>St             yleConstants<  /       cod                           e>    attribute
                       *   @   param    value t       he    val   ue        of a <co    de>StyleC    onsta    nts</code  >
         *                       attr    i                           bute    to b e con      verted
             * @r    eturn   the CS   S         v alue    that          repres       e    nts    t   he
           *   <c     od     e>S  tyleCon   stants</c    ode   >       value
              */    
           Obje   ct fr   o               mSt         yleCo  nstan ts(StyleCons      tant     s k e        y, Obj    e   c   t     value)           {
                      if (valu      e. equals(Boolea n.TRUE)) {
                        ret  u  rn      parseCssV    alu   e(  "b  old");
               }
                  re     turn pars  eCs  sVa  lue("no     rmal");
                   }     

           /**      
            *                    Converts a CSS attribut      e value to a <code>S  tyleC    ons     tants</co    d                        e    >
                *     valu    e.     If          there is no           c    onvers   ion,            returns <    code>n         ull </code>.
               * By de     fault, there  is no con vers  ion.
         *
         * @param key t        he    <code>Sty  l     e                  Constants</cod              e> attr              ibute
                * @re   t              urn the <co    de>S  tyl  eConstant    s</c    od e>       attribute value     t        h      at
                *   re  pre     sents t      he     CSS attrib           ute v  alue
              */
        Obj                        ect toS   tyleC   onsta n t  s  (Style           Cons    tan      ts      ke  y, View v) {
               return   (weight > 500       )  ?      Bool  ean.TRUE :    Boolean    .FALSE;
        }

                                             boo    l    e       an isBold() {
               retur     n     (weight     >             500);  
                       }
   
            in  t weig       ht;
    }      

         st     ati      c class Colo    r         V alue extends C      ssValue {
  
          /**
             *        Re  turn   s the color to use.
                 */  
                      Co      lor getValue() {
             return   c;
         }

          Ob ject   par     s       eCssValue(Strin      g         value) {

                        Color c    = stringToColor(v   alue);
            i f (    c          !=     null)                 {
                      Co   lor      Valu              e cv       = new   Co   lorVa      l          ue(     );
                cv.svalue = value;
                        cv             .c        = c;
                    re t         u     rn cv;
                           }
                              return null;
          }

          Objec t     par   seHtmlValue(  String value) {
              re      turn parseCs             sV alue(v   alu      e)  ;
             }

                              /**    
                    * Converts a <c     ode>       StyleC   onstants</code>         attribu    te value to
         *  a   C   SS a    t      tri            b   u te value.  If there            is        no conversio    n
         * returns <co   de>   null</code >      .  By d efault, there is no       co                  nversio     n.
               *
             * @param key     the <co   de>S  tyleConstant  s</code> attri bu  te     
            *    @        para     m   value th e value of a  <code>StyleConstan ts</co   de   >
             *                  attribute to be c      onverte           d
         *     @return th   e C          SS value     that       repres    ents t    he
           *   <code>StyleConstant   s</code    >                   value   
                  */
                                    Obj   ect f     r     omStyleConsta n  ts(Sty                       l      eCons         tants ke    y, Obje         ct     va lu  e    ) {
                ColorV      al      ue      colorValue = ne   w ColorValue(    );
                                          co     lor      Va     lue.  c      = (Color)value;    
                    col  orVa     lue    .sva lue = colorT     oHex   (colo rValue.c)          ;
                                           retur    n   colorV     a    l     ue;
               } 

            /**
           * Co   nverts   a C   SS a      ttri b    u  te    va      lu    e to a <                code>StyleCo   ns    t    ants</co        de>
                 * value.       If t   here is n    o conversio    n, ret             urns <code>   nul         l</co       de >.
                 * By default,  ther          e  is no   conver  sion.
                 * 
         * @param key         the <  code>StyleConst   ants</code>     attribute
            * @re      turn t  he <code>  St        y      leConst   ant    s<  /cod               e     > a ttribute value              that
         *   represent  s th     e CSS attrib      u  te va lue
                    */
             Objec t toStyleConsta    nt    s(StyleConst        ants key,      V      iew v)    {
                               return  c;
                }

         Col or c;
    }

    stati      c    c     lass BorderStyle  extends CssValue {

        CSS.Value               get    Value() {
                           return    st yle;
        }
  
                   Ob    ject p         arseCs sValue  (Strin  g   value) {
             C   SS  .Va  lu   e cssv  =  CSS.ge               t    V  alue(val    ue);
               if (c          ssv != null) { 
                          if ( (css       v          == CSS.V      alue.INS       ET) ||
                                  (cssv      == C SS.Val          ue.OUTSE   T) ||
                    (cssv  == CSS.Value.N       ONE) ||
                                    (cs sv == CSS.Val             ue  .DOT  TED) ||
                                     (     css    v  == CS   S.Value.DA        SHED) ||
                              (cssv == CSS.Value.SOL      ID) ||
                                               (cssv == CSS.Value  .D      OUBL            E) |   |
                              (cssv     == CS  S.V            alue.GR    OOVE) ||
                                         (cssv ==    CSS          .Value.RIDGE)) {

                                            Bo rderStyle bs =     new                 Bord          e   rStyle()   ;
                            bs.     svalue =    valu    e; 
                         bs   .sty   le    = cssv;         
                                                     r eturn   bs;
                 }
            }
                    re      t      urn null ;   
             }

         p      rivate void writeO    bject(ja    va.io.ObjectOutputS    tream    s)
                      throws I   OException {
                   s.defa      ult   WriteObject();
                               if (    style == null) {
                 s.w  rite     Obje    ct(null);
                    }
            e   lse {
                      s.wri       teObject(s        tyle.toString());
                              }
        }

                   private void re adObje  ct(ObjectInputStream s        )
                          thr   ows ClassNotF  ou   ndException, IOExc     eption {
             s.d    efaultRe      a   dOb  ject()   ;
                  Object   value    =      s.readObject();          
                   if (    va  lue != null)         {
                   style =      CSS.getValue((S     tring)valu     e);
                    }
                     }    

        //     C   SS.V         a   lu    es are sta  ti    c, don 't arc    hive it.
                              trans ient privat                       e       CSS.Value style;
    }
    
        stat     i                        c   cl  a  ss Le           ngthValue extends             C   ssValue {
      
               /        **
           * if     this length va lue may       be       ne   ga   tive.
              *       /
          boolean        mayBeNega    t    iv    e;

           Le                 ngthValu   e(  ) {
            this(  false);
                           }
   
            Len  gthValu   e(            boolean mayBeN  egative) {
            this.                may    Be Nega      tive = may    BeNegati ve;
         }

              /**             
                    * Returns   the l    eng   t    h (sp  an) to us    e.
             */
          f   loat getValue() {
                      ret     urn g        etValue(false);
             }

                 float getValue(b              oolean           i s  W   3   CLeng    thUnits) {
              r    e turn   getValue(   0    , isW3CLeng th               Uni    ts   );
                     }

             /**
                *             Returns   the l   ength (sp      a              n) t          o    use. If the value       represents
            * a percentage, it is scal  e    d   base  d on  <cod e>currentV        al     ue< /code        >.
                       */
        flo at get     Value(floa  t            current        V  alue) {   
                    retur n getValue(cu    rrentV    alue, false)    ;
              }   
                           float getVa   lu   e(fl   oat currentValue    ,     boolean          i sW3CLength Units) {
                 if (pe         rcentage) {
                                   r       eturn   spa  n * curren  tValue;
                }
            return                   Len      gthUnit.getV     alue(span,    units, isW3CL      engthUni  ts)   ;
         }
      
        /**
                * Retu             rns true if the leng   th repre  sents a percentage of the
         * containing     box.
                         */
         bool               ean isPer   centage(     ) {
                    return percen    tage;
                  }

          O    bj ect parseCss              Val      ue(String      v            a  lue)                 {  
               LengthV      alue l v;
                        try  {
                               // Assume  pixels
                           float absolut           e = Float.v       alueOf(val   ue).floa  tValue();  
                     lv   = new LengthVa  lu        e();        
                               lv.span                    =     absolute;
                         } ca     tch   (Nu  mberFormatEx c  eption nfe)     {
                           // Not pixels, u    se     L      e      ng  thUnit
                      Len    g  th    Unit lu                =  new LengthUnit(    value,
                                                                                                      Lengt     h   Unit.UNIN   I TA    LIZED_LENGTH,       
                                                      0);       

                             /  / PENDING:        currentl  y,  we   only supp   ort    absolute va   lues an  d
                    //    perc    e   ntag e    s .
                        s       witch (lu.    type) {
                                      case          0       :
                                                  /    / Absolute
                                       l  v = n  ew Len  gth  Valu   e();
                       l    v.span =
                                 (mayBeNegative) ?     lu.value         :                  Math.m    a      x(0, lu.val       ue);
                                lv.u    n         its = lu.units;
                      b        reak  ;
                        case   1:
                                                  //    %
                                lv =    new      LengthValue();
                                  lv.s    pan = Math.max(0, Math.min(1  , lu.valu     e)   );
                    l     v.p er               cen tage =          true;
                                                                     b   reak;
                               defau   lt   :
                                            return    null;
                                                                          }
                  }
                lv.s   value =         valu      e;
                   retu    rn lv;
        }

         Object   pa  rs    eH  tml     Valu  e(String value) {  
                if (    v    alue.equa    ls(H         TML.NULL_A       TTR        IBUTE_VALUE        )         ) {
                               value             = "    1";
              }
             r   eturn pa       r           seCssVa        lue           (value    );
               }
                  /**
            * Converts a <code     >Sty       leC  onstants                <   /    code> attribute value to
         * a CSS attribute value.  If the  re         is no conve     rsion,   
         * retu   rns <code>   null   </code>.     By   defau lt,  th     ere i      s no conv    ersion.
                *
                     *  @param key the <code>StyleCon           s tants</cod e> at   tr      ibut             e  
              * @para          m  va     l   u e the       v   alu          e o    f a <co  d   e>S t   yleConstants</code>  
           *    attrib  ut   e to be co    nve    rted   
          * @return the CSS      value t  h     at represent  s the
                  *     <code>StyleConst ants    </ code> value
            */
        Obj ect fromStyleC   onstants (StyleConstants key, Object value)   {
             Leng  thValue v = new  Len             g        thValue();
                   v.sval        ue = v    alu   e.toString();
                  v.spa     n = ((Float)value)     .flo  atValue    ();
             retu   r  n v;
                   }

        /**
             * Conve       rts a C    SS at   tribute v    a   lu    e to a <code  >StyleCo      nstants    < /code>
           * value.  If ther  e is no     conve    r sion, return s <   code>null</co     de>.
                                   *  By d              efau  lt, the    re is no conversion.
                       *
                     * @param key the <  co     de  >S    ty    leConstants</code> attribute
         * @  return     th   e      <c       od       e>St         yleConstants</co   de>         attri            bute value      that
            *    represents    the     CSS        attr  ib     ute val   u  e
             *             /
        O                   bjec   t       toStyleCo     nstants      (StyleConstants            key, View v) {
                ret    urn new Fl   oat(get   Value(  false       ));
            }

                     /** If     tr  u e, span   is a percentage value, and     t  hat to de           termin   e
         * the length another value n                 ee    ds to be   passed in. */
            boo lean    percen   tage;          
                    /*      * Ei     th        er the a  bsolute val     ue (pe          rcentage ==    false    ) or
                       * a percentage value. */
               fl    oat s         pa        n;

        String         units = null;  
    }   


    /**
     *        BorderWi  d thVa  lue i  s      use d   to model    BORDER    _XX   X_WID    TH and    adds  support       
     * f  or the thi    n/med    iu  m/thick va    l  ue       s.
                  */
    stat   ic   class Bor      der  WidthVa     lue extend    s Leng thV    alue {
            Bor  derWid  thVa     lue(St  ring          sva   lue, in      t index)    {
               this      .   svalu e = svalu     e       ;
                    s  pan = valu     es     [ind        ex];
                         percentage =  f   als  e       ;
              } 
       
                Objec   t parseCssV  alue(   S      tring value )        {
            i  f (value != null) {
                                     if (va        lue    .equals("thick")) {       
                                      retur   n new BorderWidthValu      e(valu e, 2);
                                             }
                                else if (value.    equals("medium "  ))  {
                    ret        ur   n    new BorderW       i  dthVa   l                u    e(val   ue, 1)      ;
                                 }
                                  else if (va  lue. equals("thin"))    {
                                re       tur   n new Borde   rWidth   Value(va    lue,      0);  
                    }
                   }
                   // Assume its a          length. 
            re   tu   rn super.   parseCs    sValue(value);
        }     

        O    bject   parseHtmlVal    ue(String v alue) {
                           if (value ==  HTML.  NULL_ATT     RIBUTE_   VALUE)  {
                           return   pa   r         seC  s      sValue("medium");
                }    
             ret   ur  n     parseCssValue(v   alue     );
            }

              /** Va l    u    es used to   re          p resent b   o   r  d er width. */
        priv      ate static final      fl   oat[] valu  e             s =         {   1    , 2, 4 }    ;
     }
  
          
                   /**
           *       Handl       es uniqui    ng of CSS    valu        es, lik     e   lists,      and bac         kground imag e
        * repeati   ng.     
      */
                       sta  tic clas     s C  ssValueMapper extends CssValu e     {
        Obj  ect par     seCssValue            (String   value) {
                O      bject   retValue =     cssV   alueToInternalValue   M         ap.get(va             lue)       ;
                                         if (retValue == null)       {
                                 retVal   ue = cssValueToInternalValueM           ap.get    (value.toLower    Case());         
                  }
                return ret    Val   ue;
                 }

  
                   Object pars  eHtm   lValue(      String      value)        {
                    Ob ject             retValue = ht   mlValu    eToC   ssVa         lueMap.           get(   value);
                       if (retVal u  e == nu ll  ) {
                                    re      t  Value = htmlValueToCssVa  lueMap.ge t(value.toLowerCase     ());
                       } 
                 retur    n  retValue;
        }
    }
 

      /**
           *    Used for      back      gr      ound images, to      rep      res ent the p       o    sition.
            */
    stati     c       class Backgrou      ndPosition extends      CssValu      e {
        float   horizon  talPositio             n;
                 float ver ticalPos  ition;
                  /           / bitmask: bit 0,       h  oriz     ontal rela tive, bi     t 1 h  orizontal r    elative      to 
        //   font size,        2     vertical relative to siz        e, 3 vertic          al      rela       tive   to
        /    / fo     nt size.
              //
        shor  t relat                  ive;

          Object   parseCssV       alue(Str ing value)               {
                     /   / '        top left'    and '      left   top' both mean        the same as '0% 0    %'.   
                    // 'top', 'top center' and 'ce      nt    er         t  op'    me  an the same    as '50% 0%'.
                   //         'rig      ht top' an     d 'top r ight' mean    th e same as      '100% 0%'  .
                //           'left', 'left center' and 'center l eft' mean the same           as
            //              '0% 50%  '.
                  // 'cen  ter'    and 'cente  r ce     nter'     me  an the same as     ' 50% 50%'.
                      // 'right', 'right center' and    'cen ter    r     ight' mean the s    ame as
              //          '100% 50    %'.
              // 'b    ottom lef t ' and 'left   bottom' mean  t he same as '0% 100%'.
                       //    'botto     m',   'bot   tom center'   and     'center bott    om'    m e  an th e same a      s
              //             '5   0% 100%'.
            // 'bo     t      to    m ri         g   h          t' and 'ri   gh          t    b  ottom'       mea      n t    he same  as '100%        100%    '.
               Strin  g[ ]  strings = CS  S.pa       r    seStrings(value    )  ;
                    int count = str  ing  s.leng  th;
               B    ackgrou          n   dPos       ition bp = n  ew    Backg r    ound       Posi   t ion();
                              bp.   re   lative      = 5;
                 bp.sva   lue = val ue;

                                 if          (count > 0) {
                                         // bit 0 for         ver    t            , 1 hor, 2 for center
                                  short  found = 0;
                         int index     = 0;
                      while      (in         dex < c ount)      {
                           //  First, check f  or         keywords     
                               Stri   ng str         ing =    strin    gs[    index++];  
                               if (s tring.  eq        uals  ("center")) {
                                       found |= 4;
                                                cont   inue;
                                        }
                                     else {
                               if    ((  found & 1)              == 0)     {
                                    if (string.equal s(   "top")) {   
                                           found |= 1            ;
                                           }
                                                else      if   (s tring.equa       l     s("bottom")) {
                                              found |= 1;
                                          bp    .verticalPositio              n = 1;
                                       c ontinue;
                                            }
                                     }
                                                if ((found & 2) == 0) {     
                                if  (   string.equ    als("left")) {
                                                     found |=     2;
                                                 bp.horiz      o   ntalPosit     io     n = 0;
                                                      }
                                                 else if (st    ring.equa  ls("right")) {
                                          found |= 2;
                                            bp.horizontalPosition = 1;
                                }
                                  }
                                          }
                     }
                   if  (fo    und !=   0) {
                    if ((found     &   1) ==          1) {
                                      if ((found & 2) == 0) {
                                   //             vert an  d no ho   riz.
                                    bp.hor iz   onta  lPos   it       ion = .5f;
                                                 }
                            }
                                 else      if ((     found & 2) =      =    2) {
                                    //  hori        z and no vert.
                             bp.verticalPosit         ion =    .5f;    
                            }
                                                else {
                                             //   no           ho  riz,    no vert, but center
                                              bp.h      orizontalPosition =     bp.v           er   tic   alPositio   n =    .5f;
                         }
                   }
                                 else {
                               // Assume l   engths
                                Lengt        hUnit   lu = new Length         Unit(strings[0], (     short)0, 0f)     ;      

                    if (lu.t   ype == 0)      {
                                   bp.horizontalP osi   tion = lu.value;   
                              bp.relati ve = (short )(1 ^ bp.rela  t     ive) ;
                            }
                            else if (lu   .type == 1) {
                                           b          p.horizo  nt        alPos    ition =  lu.valu                 e ;
                       }
                                       else if (lu.type == 3) {  
                                               bp.horizontalPo    sition = lu.value;
                                 bp.rel   a ti       v    e = (short)((  1 ^    bp.relative    ) | 2);
                               }
                      if (count > 1)    {
                            lu = new LengthUnit(strings[      1],  (sh       ort)0, 0f);         

                              if (lu.t   ype    ==   0) {
                                                bp.verticalPosition = lu.value ;
                                bp.relat          ive = (s  hort)(4 ^ bp.relati     v    e);
                             }  
                                             else if (lu.type ==    1) {
                                         bp.vertic              alPos     it ion = lu.value;
                        }
                                    else if (lu   .typ  e == 3) {
                                bp.verticalPosition = l       u.value;
                                        bp.relative = (sho   rt)((4 ^    bp.relative           ) | 8);
                           }
                        }
                          else {
                                           bp.ve rticalPosition    = .5f;
                                   }
                  }
                }
               return b      p;
             }

        boolean is      HorizontalPositionRel     ativeToSiz  e() {
                 return ((relative & 1) == 1         );
        }

                   boole  an      isHorizontalPositionRelat iveToFon   tSize() {
              return ((      relative & 2)    == 2);  
        }

          float getHo  rizontalPosition      () {
                 return      hor    izontalPositi          on;
        }

                   boo lean isVertic alPosi      tio     nRelativeToS  ize() {
                  re       turn ((r  ela      tive         & 4) = = 4);
        }

        boole           an  isVerti   ca       lPositionRelativeToF   on         tSi     ze() {
                   return ((r elative &        8) == 8);
                   }

        f  loat getVertica   lPosition()    {
                       return verticalP      os  ition  ;
                   }
        }

   
    /**   
     * Used         for Back   ground     Imag   es.
        *   /
       sta   t ic class     Ba    ck  groun  dImage extends CssValue {
        private boolean    l         oadedImage;  
        private Imag eIcon  image;

        Object pa       rseCssValu       e(S   t  r     ing value)   {
            BackgroundImage retValue = new BackgroundImage( ) ;
            retValue.sv  al   ue       =                va  lue;     
            r  e    turn r   etValue;   
        }

                 Ob  ject parseH   t   mlValue(St        ring value) {
               retur      n p    arseC    ssValue(value);
        }

        // PENDI   NG: th        is base     i    s wrong for linked style sheets   .
              Image  Icon getImage(URL bas   e) {
             if (    !loadedIm   age) {
                  synchronize            d(this) {
                               if (   !load  edImage) {
                                                  URL    url     = CSS.getURL(   ba      se, s   val    ue );
                           loadedImage = true;
                              if (url != nu ll) {
                                       image = new ImageIcon();
                                                 I       mage tmpImg =    Toolkit.get   De faultToolkit().creat     eImage(url);
                            if (tmpI  mg != nul   l) {
                                 imag   e.setImage  (tmpImg);
                                                             }
                                                       }
                    }
                  }
            }
                      return   i   mage;
        }
    }

      /**
     * Parses a len   gth value, this is used in   te        rnally, and never added
     * to          an Attribute    Set or returned to the developer.
     */
    stat    i   c  class L    engthUnit  implement     s    Serial  izable {
           static Ha      shtable<String, Floa              t> lengthMapping =   ne     w Hasht      able<String, Fl   oat>(6)     ;     
          static Hashtable    <Stri ng, Floa  t> w3cLengthMapping = new Hashtable<String, Float   >(6   );
        static {
            lengthMapping.put("pt          ",       n      ew F   loat(1f))       ;
                //        Not       sur    e about 1.3, determ        ined by experiementation.
            lengt  h   Mapping.put("p     x" , new F    loat(1.3f));  
                length     Ma     pping.put("mm", new Fl      oat(2.83   464f));
            len       gthMappi    n  g.put("   cm", new Float(  28.3464f  ));
              len             gthMapping.    pu      t("   pc", new Float(12f));     
                     lengthMapping.p  ut(  "in", ne  w Float(72f   ) );
                     int res =    72;
                     try  {       
                  r  es = Toolki t.getDefaultTo  olkit ().getScreenResolution();
            } catch (  Headles  sExce  ption e    ) {
            }
                // mapping accord     ing to the CSS2 spe   c  
            w3cLengthMapping.p  ut("pt",       new Flo  a t(res/72f));
                w3cLengthMapping.put("px", new Float(1   f));
            w3cLengthMapping.put(   "m m", new Float(res/2       5.4f));
            w3cLengthM      apping.put("c  m",      new Float(r      es/2.54f));
                  w      3cLengthMa       pping.pu       t("p     c", new Fl       oat(res/6f));
                  w     3cLengthMapping.p ut("in", ne w Float(res));
        }

               LengthUnit( String value, sh   ort      defaultTy      pe, float defaultValue)   {
             p  arse(value  , de    faultT      ype, defaultValu     e);
          }

        void parse(String value ,         short defaul  tTyp  e, f   loat de     faultValue)   {
                    type = def           aul   tType;
                thi      s.val     ue    = defau          ltVal  ue;

              int length = value.length();
                    if (l   ength >      0 &&    value.charAt(length - 1) ==    '   %') {
                            try {   
                          this.val   u        e = Float.valueOf(valu           e.substr    ing(0,    l     ength - 1)).
                                                        floatValue() / 100. 0f;
                                       t    ype = 1;  
                              }
                      catch (  NumberFormatException nfe) { }
               }
            if (length >    = 2     ) {
                         units =     value.substring(length -          2, len  gth);
                F     loat scale = lengthMapping      .get(units);
                      if (scale != null) {
                        try {
                                            t his.value = Float.valueOf(value.s   ubstring(0,
                                           leng   th - 2)    ).floatValue();
                                       type = 0;
                                   }   
                     ca      tc      h (N    umberForma tException nfe)       { }
                       }
                els e    if (u     nits.equals("em") ||
                                          u    nits.equals("ex")) {
                          try {
                                             this.value = Float.valueOf(value.    substring(0,
                                                          leng     th - 2))   .floatVa     lu  e();
                                type = 3;
                    }
                              catch (N      umberFormat    Ex     cep  tion   nf    e) { }
                 }
                e lse if    (value.equals        ("    large   r"    )) {
                               this.valu e = 2   f;
                          type = 2;
                            }
                            else if (       v alue.equals("smaller")) {
                        this.value = -2;
                    type = 2;
                }
                   else     {
                        // tr   eat      like points.
                        try {
                                          this.value = Float.valueOf(v    al    ue).   floatValue();
                                   ty    pe = 0;
                          } catch    (NumberFormatException nfe) {}     
                            }
                 }
              else if (l   en  gth > 0) {
                         // tr  ea      t like     points.
                try {
                      this.value = Float.valueOf(v     alue).floatValue();
                    t  ype = 0;
                 } catch     (Num  berF  ormatExc      ep        tion nfe) {}     
            }
        }  

             float ge  tValue(b    oolean w3cLengthUnits) {    
            H   ashtable  <  String, Float>    m apping = (w3cLen           gt    hUnits) ? w3cLengthMapping : lengthMapping;
                float scale = 1;
               if        (units            != n    ull) {
                       Float scaleFloat = mappin   g.get(units);
                     if   (sc     aleFloat     !=    nu  ll) {
                                       scale = sc  aleFlo         at.floatValue();
                      }         
                 }
            return this.value * scale;

        }

           static float g   etValue(float value, S   tring units, Boolean w3c   LengthUn  its)     {   
                    Has    htable<  Strin       g      , F   loat  > ma   pping = (w3cLengthUnit    s) ?   w3cLen    gthM    app           ing    : lengthMapp ing;
            float scale = 1;
                if (units != nul l) {
                 Flo  at scaleFlo    at =       mappin  g.get(units);
                       if (scaleFlo      at != null) {
                        scale = scal    eFloat.floatValue();
                }
                             }
               retur n value * scale;
           }

        public String toStrin  g()         {     
            return type       +    " " + value;
        }

          //  0 - value indicates re        a  l value
        /  / 1 - % value   , value    relative to depends         upon key.
             / /     50%    will have a  valu  e = .5
             // 2 -   add value to        p   arent val ue.
        // 3   - em/ex r   elative to font  size of element (except for
            //        font-size, which is relative t      o p       arent).
        sh     ort ty   pe;
        float value;
              String u   ni     t   s = null;


               static final short UNINITALIZE D_L ENGTH = (short)10  ;
    } 


    /**
        * C     l    ass used to       parse font property. The font prop    erty is s  horthand
     * for the other font properties. This expands the pr   operties, pl  ac ing
       *   them in the attribu   teset.
     */
      static class ShorthandFontParser {
        /**    
         *   Parses the shorthand fo  nt   str  ing <code>v    alue</co de>, placing the
         * resul    t in <code>att      r</code>.
             */
        stat ic void par seShorthandFont(C   SS css, String v a           lue,  
                                          MutableAttributeSet attr) {
            // font      i     s of the for m:
              //     [ <font-sty    le> || < font-var ian     t>   || <font-weight  > ]? <font-size>
                     //     [ / <line-height>    ]?           <fo     nt-famil   y   >
            String[]         strings = CSS .par  seStrings(     va lue);
                         int               count = strings.length;
            int               index = 0;
               //  bitmask, 1 for style , 2 for variant, 3 for w  eight
               short      found = 0;
              in      t        maxC = Math.min   (3, count);    
  
                  /   / Chec    k for fo nt-styl                 e font-variant f   ont  -weight
               while (index <   ma          xC) {
                  if     ((found & 1) == 0 &&    is  Font   Style(      strings[i          ndex])) {
                    css.addI nternalCSSValue(at  tr, CSS.Attribute.FONT_      STYLE,
                                                           strings[index++   ]);
                    found |= 1     ;
                 }
                                   else if ((found             & 2) == 0 && i    sFontVar   iant(strings[in   d       e x   ])) {
                      css.addInternalCSSValue(a   tt  r, CSS.Attribute.FONT_VAR   IANT,  
                                               strings[index++]);
                             found |= 2;
                        }
                                       else i      f ((fou nd  & 4)   ==   0 && isF   ontWeight(strings[index])) {
                     css.addInternalCSSValue(attr, CSS.Attri    bute.FONT_WEIGHT,
                                                  strings[  i  ndex     ++]);
                    found   |= 4;
                           }
                       else if (   strings[index].equals("normal")) {
                     index++;
                      }
                       els  e {
                      break;
                                          }
               }
                if (    (found & 1) ==    0) {
                css.addInternalCSSValue(attr, CSS.Attribute.    FONT_ST    YLE      ,
                                                   "normal");
                     }
                if ((  found & 2) == 0) {
                       css.addInternalCSSValue     (att   r,            CSS.Attr ibute.FONT_VAR          IANT,
                                            "n  or   mal");
            }
                 if ((fou    nd & 4)    == 0) {
                  css.addInternalCSSValue(attr, CSS.Attribu   te.FONT_WEIGH      T,
                                                   "norma  l");
            }

                         // str ing at index should b    e the font-siz  e
                          if (index < count) {
                      String    fontSize = strings[index];
                  int sla       shIndex = fontSize              .in    dexOf('/');

                            if  (sl  ashIndex != -1) {
                        font   S        ize     = fo      ntSi     ze.substring(0, slashIndex); 
                        str     ings[index] = strings[index].substr ing(slashIndex);
                   }
                        else {   
                             ind     ex++;
                        }
                   css.  addInternalCSSValu  e(attr, CSS.Attribute.FONT_SIZE,
                                                       fontSize);
                      }
            else {    
                css.addIntern    alCSSValue(attr, CSS.Attribute.FONT_SIZE,
                                            "medium")  ;
             }

               // Chec   k for line      height   
            if (index  < count && strings[index].startsWit  h("/")) {
                Stri    ng lineH eight = null;
                 i   f (strings[index].equ        a ls("/")    ) {
                           if (++in      dex < count)  {
                             lineHeight = strings[index+   +];
                            }
                           }
                   else {
                      lineHeight =   string  s[index++].subst  ring  (1);
                                   }
                  // line height
                  if (lineHeight != null) {
                    css.addInternalC    SSValue(at   tr, CSS.Attribute    .LINE_HEIGHT ,
                                                           lineHe ight);
                       }
                else {
                    css.addInternalCSSValue(att   r, CSS.A ttr  ibut    e.LINE_HEIGHT,
                                                "normal ");
                    }
            }
            else {
                css.addInternalCSSValue(  attr,    CS  S.Att  ribute.LINE_HEIGHT,
                                                        "    normal" );
                }

            // remainde    r of strings are font-family
                     if (i    ndex < count) {
                   String family       = strings[in      dex++];

                       while (index < count) {
                      family   += " " + str ings[index++];
                }
                css.  a  ddInternalCSSVa lue(attr, CSS.Attribute.FONT_FAMILY,
                                                   family);
            }
                 else {
                   css.addInternalCS   SValue(attr,    CSS.Attribute.FONT_FAMILY   ,
                                             Font.SANS_SERIF)   ;
              }    
        }

        priv   ate stat    ic boolean  isFontSty     le(Strin    g string) {
            ret    urn (str     ing.equals(      "italic") ||
                           string.eq  uals("ob    lique"));
        }

        p    rivate static boolean is           FontVariant(String      string)         {
            return (string.equals("small-caps")  );
        }

        private stat  ic boolean i     sFontWeight(Stri    ng string) {
            if (str    ing     .eq   uals("bold      ") || string.eq    uals("bold  er") ||
                   string.equals("italic") || s   tring.e     quals    ("li      ght  er")) {
                return true;
            }
            // test for 100-900   
               return (string.length() == 3 &&
                              string.charAt( 0) >= '1' && string.charA     t(0) <= '9' &&
                                          strin   g.charAt(1)     == '0'          && string.charAt(2) == '0');
        }

    }


    /    **
     * Parses the background property into its    intrin      sic values       .
     */
    static class Shor   thandBackgrou   ndParser {
        /**
         * Parses the shorthand font string <code>value</code>,   placi    ng the
              * result in <code>attr</code>.
         */
         static void par  seShorthandBackground(CSS css, String value,  
                                             Mut ableAt   tributeSet attr)     {
                   String[] strings =    parse  Strings(value);
                   int count             =    strings.length;
                      int index = 0;  
            // bitmask: 0 for i  mage, 1 repeat, 2 attachment, 3 position,
            //          4 color
            short found = 0;

               while (index < count) {
                String    strin  g    = strings[index++];
                     if ((foun    d & 1)      == 0 && isImage(string)) {
                       css.addInt  ernalCSSValue(attr, CSS.Attribute.
                                                BACKGROUND_IMAGE, string);
                        found |= 1;
                         }
                            else if ((found & 2) == 0 && isRepeat(string) ) {
                    css.addInternalCSSValue(attr,       CSS  .Attribut    e.
                                               BACKGROUND_ REPEA    T, string);
                              fou     nd     |= 2; 
                  }
                        els     e if ((foun d & 4)     == 0 && isAttachment(string)    )      {
                           css.addInt   ernalCSSValue(attr, CSS.Attrib  ute.
                                             BACKGROUND_ATTACHMENT, string);
                    found |= 4;
                    }
                           else if ((found & 8) ==  0 && isPosition(    string)) {
                    if (index < count && isPosition(strings[inde       x])) {
                             css.addInte  rnalCSSValue(attr,         CSS.Attribute.
                                                   BACKGROUND_POSITION,
                                                         string + " "   +
                                                  strings[index++]);
                         }
                     else {
                                 css.addIn  ternalCSSValue(attr, CSS.Attribute.
                                                       BACKGROUND_POSITION, string);
                       }
                      found |= 8;
                }
                else if ((found &    16  ) == 0 && isColor(string))    {
                      css.addInternalCSSVa    l  ue(attr   , CSS.Attribu  te.
                                                         BACKGROUND_COLOR, st   ring);
                      found |= 16;  
                     }
            }
            if ((found    & 1) == 0) {
                  css.addIntern  al   CSSValue(   attr, CSS.Attribute.BACKGROUND_IMAGE,
                                            null);
              }
            if ((found & 2) == 0) {
                c     s  s.addInternalCS   SValue(attr, CSS.Att  ribute.BACKGROUND_REPEAT,
                                           "repeat");
            }
                if (   (found & 4) == 0) { 
                css.add  InternalCSS   Value(attr, CSS.Attribute.
                                        BACKGROUND_ATTACHME NT     , "scroll");
            }
            if ( (found & 8) == 0)   {
                css.addInternalCSSV alue( attr, CS     S.Attribute.
                                           BAC   KGROUND_POSITION, null);
                }
            // Currently    , there    is no  good     way to exp    ress this.
                   /*
                if (    (found &  16) == 0) {      
                css.addInternalCSSVal   ue(attr, CSS.At       tribute.BACKGROUND_COLOR,  
                                                      null);
              }
            */
         }

        static boolean isImage(String string) {
                   r  eturn (string.start   sWith("url(") && string.endsWith(")"));
        }

            static boolean isRe  peat(String string) {
               return (string.equa    ls("repeat-x  ") ||   string.e   quals("repeat-y") ||
                    string.equals("repeat") || string.equals   (   "no      -repeat"   ));
          }
 
        static boolean isAttachment(String string) {
            return (string.   eq    uals("fixed") || string.equals("scroll"));
           }

        static     boolean isPosition(String string) {
                   return (strin             g.equa     ls("top") || string.equals("bottom") |   |
                                string.equals("lef t") || string.equals("righ   t") ||
                     string.equals(   "center") ||
                         (string.length() > 0 &&
                        Character.isDigit(string.charAt(0))));
            }

           static boolean isC      olor(String string) {
            return (CSS.stringToColor(string  ) != null);
        }
    }


    /**
     * Used to pa        rser margin and   padding.
     */
    static c  lass Sh      orthandMarginParser {
        /**
           * Parses the sh   orthand margin/    padding/border string
         *     <code>value</code>, placing th   e   r  esult in <code>attr<    /    code>.
         * <code>names</code> give the 4 in         strinsic property names.
         * /
             static voi   d parseShor thandMargin(CSS css, String value,
                                             MutableAttributeSet attr,
                                                       CSS.At  tribute[] names) {
                     String[] strings =  parseStrings(valu  e);
            int count = str     ings    .len      gth;
                   int index = 0;       
                  switch (count) {
            case 0:
                             // empty string
                  return;
            case 1:
                // Identifies al     l values.
                for (int co       unt er = 0; counter < 4; counter++) {
                    css.addInternalCSSValue(attr, names[counter], strings[0]);
                     }
                break;
               case 2:
                // 0 & 2 =  stri         ngs[0], 1 & 3 = strings[1]
                 cs   s.addInternalCSSValue(attr,    names[0], strings[0]);
                css.addInternalCSSValue(attr, names[2], strings[0]);
                   cs s.addInternalCSSValue(attr, names[1], stri    ngs[1]);
                 css.addInternalC     SSValue(attr, names[3], strings[1]);
                  break;
            case 3:
                     css.addInternalCSSValue(a  ttr, names[0], st  rin gs[0]);
                   css.addInter  nalCSSValue(attr, names[1], stri ngs [1]);
                css.addInternalCSSValue(attr, nam  es[2], strings[2]);
                 css.addInt    ernalCSSValue(a  ttr, names[3], strings[1]);
                   break;
            default:
                    for       (int counter = 0; counter < 4; counter++) {
                        css.addInternalCSSValue(attr, names[counte r],
                                            stri       ngs[count er]);
                }
                             break;
            }
           }
          }

    static class Sh  orthandBorderPa     rser {
               static Attrib    ute[] keys = {
                  Attribute.BORDER_TOP, Attribute.BO  RDER_RIGHT,
            Attribute.BORDER_BOTTOM, A   ttribute.BORDER_LE   FT,
        };

        stati   c void parseShorthandBorder(Mutab     leAttributeSet attributes,
                                                  CSS.Attribute key, String  value) {
            Object[  ] parts = n     ew Object[CSSBorder.PARSERS.length];
            String[] strings   = parseStrings(value);
                for (     String s : strings) {
                       boolean valid = false;
                       for    (int i = 0; i < parts.length; i++) {
                       Object v =        CSSBorder.PARSERS[i].parseCssValue(s);
                    if (      v != null) {
                        if (parts[i] ==         null) {
                                 parts[i] = v;
                            valid = true;
                                }
                        break;
                    }
                }
                if (!valid)  {
                      // Part is         non-parseable or occurred more than once.
                    return     ;
                    }
            }

               // Unspecified parts get default values.
                    f    or (int i = 0; i < parts.length;   i++) {
                         if (parts[i] == null) {
                    parts[i] = CSSBorder.DEFAULTS[i];
                  }
            }

               // Dispa    tch collec ted val ues to indiv  idual properties.
             fo     r (     int i = 0 ; i < keys.length; i++) {
                if ((key == Attribute.BORDER) || (key == keys[i])) {
                    for (   int k = 0; k < parts.length; k++) {
                           attributes.addAttribute(
                                          CSSBorder.ATTRIBUTES[k][i], parts[k]);
                    }
                }
            }
        }
    }

    /**
                   * Calculate the requirements n     eeded to      tile the    requirements
     * giv  en by the iterator that would    be tiled.  The ca    lculation
     *      takes into    c  onsideration margin and border spacing.
     * /
    static SizeRequi   rements calculateTi  ledRequir    ements(LayoutIterator iter, SizeRequ     irement s r) {
        long minimum = 0;
        long maximum = 0;
        long preferred = 0;
        int  lastMargin = 0;
         int to    talSpacing = 0;
        int n = iter.getCount();
        for (in    t i = 0; i < n; i++) {
                iter.setIndex(i);
            int margin0 = lastMargin;
              int margin1 = (int) iter.getLeadingCollapseSpan();
            totalSpacing += Math.m  ax(margin0, margin1);
            preferred += (int) iter.g     etPreferredSpan(0);
            minimum += iter.getMinimumSpan(0);
            maximum += iter.get MaximumSpan(0);

            lastMargin = (int) iter.getTr ailingCollapseSpan();
        }
           totalSpacing +=    lastMargin;
        totalSpacing += 2 * i  ter.getBorderWidth();

        // adjust for the spacing area
           minimum += to          talSpacing;
        preferred += totalSpacing;
        maximum += totalSpacing;

        // set return value
                if (r == null) {   
                r = new SizeRequirem  ents();
        }
        r.minimum = (minimum > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int)minimum;
        r.preferr   ed = (preferred > Integer.MAX_V   ALUE) ? Integer.MAX_VALUE :(int) preferr   ed;
        r.ma   ximum = (maximum > Integer.MAX  _VALUE) ? Integer.MAX_VALUE :(int) maximum;
         return r;
    }

    /**
     * Calculate a tiled layout for the given iterator.
     * This should b     e done c       ollapsing the neighboring
     * margins to be a total of the maximum of th    e two
        * neig  h  boring ma       rgin areas as described in  the CSS spec.
     */
    static void calculateTiledLayout(LayoutIterator iter, int targetSp  an) {

          /*
         * first pass, calculate the preferred sizes,    adjustments needed because
         * of margin collapsing, and the flexibility to adjust the    sizes.
         */
        long preferred = 0;
        long currentPreferred;
              int la  stMargin = 0;
        int totalSpacing = 0;
           int n = iter.getCount();
        int adj  ustmentWei    ghtsCount = L    a  youtIterator.WorstAdjustmentWeight + 1;
        //max gain we can get adjusting elements with ad justmentWeight <= i
             long gain[]      = new long[adjustmentWeightsCount];
            //max loss we can get adjusting elements with adjustmentWeight <= i
            long loss[] = new long[adjustmentWeightsCount];

        for (int i =    0; i < adjustmentWeightsCount; i++) {
            gain[i] = loss[i] = 0;
          }
              for (int i = 0; i < n; i++) {
            iter.setIndex(i);
              int margin0 =     lastMargin;
            int margin1 = (int) iter.getLeadingColla   pseSpan();

            iter.setOffset(Math.max(margin0, margin1));
            totalSpacing += iter.getOffset();

            currentPreferred = (long)iter.getPreferredSpan(targe              tSpan);
            iter.setSpan(   (int) currentPreferred);
              preferred += currentPreferred;
            gain[iter.getAdjustmentWeight()] +=
                (long)iter.getMaximumSpan(targetSpan)  - currentPre       ferred;
            l    oss[iter.getAdjustmentWeight()] +=
                 current Preferred - (long)iter.getMinimumSpan(targetS    pan);
            lastMargin = (int) iter.getTrailingCol lapseSpan();
        }
        totalSpacin    g += lastMargin;
        tot    alSpacing += 2 * iter.getBorderWidth();

        for (int i = 1; i < adjustmentWeightsCount; i++) {
             gain[i] += gain[   i - 1];
             loss[i] += lo     ss[i - 1];
          }

        /*
             * Second pass,  expand  or contract by as much as possible to reach
         * th   e target span.      This takes the margin collapsing into accou   nt
         * prior to adjusting the span.
         */

        // determine the adjustment to be made
        int allocated = targetSpan - totalSpacing;
        long    desiredAdjustment = allocated - preferred;
        long adj   us tmentsArray[] = (desir        edAdjustment > 0) ? gain : loss;
        desiredAdjustment = Math.abs(desiredAdjustment);
        int adjustmentL  evel = 0;
        for (;adjustmentLevel <= LayoutItera     tor.WorstAdjustmentWeight;
             adjustmentLevel++) {
            // adjustmentsArray[] is sorted. I   do not bother about
                // binary search though
            if (adjustmentsA     rray[adjustmentLevel] >= desiredAdjustment) {
                    break;
            }
        }
        float adjustmentFacto       r = 0.0f;
            if (adjustmentLevel <= LayoutIter   ato  r.WorstAdjustmentWeight) {
            desiredAdjustment -= (adju  stmentLevel > 0) ?
                adjustmentsArray[adjustmentLevel - 1] : 0;
            if (desiredAdjustme    nt      !=      0) {
                float maximumAdjustment =
                    adjustmentsArray[adjustmentL   evel] -  
                    ( (adjustmentLevel > 0) ?
                     adjustmentsArray[adjustmentLevel - 1] : 0
                     );
                adjustmentFa    ctor = desiredAdjustment / maximumAdjustment;
            }
        }
            // make the adjustments
              int total  Offset = (int)iter.getBorderWidth();
            for (int i  = 0; i < n; i++) {
             iter.setIndex(i);
            iter.setOffset ( iter.     getOffs      et() + totalOffset);
            if (i      ter.getAdjustmentWeight() < adjustmentLevel) {
                iter.setSpan((int)
                             ((a       llocated > preferred) ?
                                   Math.floor(iter.getMaximum  Span(targetSpan)) :
                                Math.ceil(iter.getMinimumSpan(targetSpan))
                                 )
                             );
            } else if (iter.getAdjustmentWeight() == adjustmentLevel) {
                  int availableSpan = (allocated > preferred) ?
                    (int) iter.getMaximumSpan(targe   tSpan) - iter.getSpan() :
                    it  er.getSpan()    - (int) iter.getMinimumSpan(targetSpan);
                int adj = (int)Math.floor(adjustmentFactor * availableSpan);
                iter.setSpan(iter.getSpan() +
                             ((allocated > preferred) ?  adj : -adj    ));
            }
            totalOffset = (int) Math.min((long) iter.getOffset() +     
                                             (long) iter.getSpan(),
                                           Integer.MAX_VALUE);
        }

        // while rounding we could lose several pixels.
        int roundError = targetSpan - totalOffset -
            (int)iter.getTrailingCollapseSpan()     -
            (int)iter.getBorderWidth();
        int a dj = (roundError > 0) ? 1 : -1;
        roundError *= adj;

        boolean canAdjust = true;
        while (roundError > 0 && canAdjust) {
            // check for infinite loop
            canAdjust = false;
            int offsetAdjust = 0;
              // try to distribute roundError. one pixel per cel          l
            for (int i = 0; i < n; i++) {
                iter.setIndex(i);
                iter.setOffs   et(iter.getOffset() + offsetAdjust);
                int curSpan = iter.getSpan();
                if (roundError > 0) {
                    int boundG  ap = (adj > 0) ?
                        (int)Math.floor(iter.getMaximumSpan(t   argetSpan)) - curSpan :
                        curSpan - (int)Ma th.ceil(iter.getMinimumSpan(targetSpan));
                    if (bound Gap >= 1) {
                        c  anAdjust = true;
                        iter.setSpan(curSpan + adj);
                          offsetAdjust += adj;
                            roundError--;
                    }
                }
            }
        }
    }

    /**
     * An iterator to express the requirements to use    when computing
     * layout.
     */
    interface LayoutIterator {

        void setOffset(int offs);

        int getOffset();

        void setSpan(int span);

        in   t getSpan();

        int getCount();

        void setIndex( int i);

        float getMinimumSpan(float parentSpan);

        float getPreferredSpan(float parentSpan);

        float getMaxim       umSpan(float parentSpan);

        int getAdjustmentWeight(); //0 is the best weight WorstAdjustmentWeight is a worst one

        //float getAlignment();

        float getBorderWidth();

        float getLeadingColl apseSpan();

        float getTrailingCollapseSpan();
          public stati   c final int WorstAdjustmen      tWeight = 2;
    }

    //
    // Serialization support
    //

    private void writeObject(java.io.ObjectOutputStream s)
        throws IOException
    {
        s.defaultWriteObject();

        // Determine what values in va       lueConvertor need to be written out.
        Enumeration keys = valueCo  nvertor.keys();
        s.writeInt(valueConvertor.size());
        if (ke   ys != null) {
            while (key  s.hasMoreElements()) {
                Object key   = keys.nextElement();
                Object value = valueConvertor.get(key);
                if (!(key instanceof Serializable) &&
                    (key = StyleContext.getStaticAttributeKey(key)) == null) {
                    // Should we throw an exception here?
                    key = null;
                    value = null;
                }
                else if (!(value instanceof Serializable) &&
                    (value = StyleContext.getStaticAttributeKey(value)) == null){
                    // Should we throw an exception here?
                    key = null;
                    value = null;
                }
                s.writeObject(key);
                s.writeObject(value);
            }
        }
    }

    private void readObject(Obje  ct InputStream s)
      throws ClassNotFoundException, IOException
    {
        s.defaultReadObject();
        // Reconstruct the hashtable.
        int numValues = s.readInt();
        valueConvertor = new H    ashtable<Object, Object>(Math.max(1, numVa     lues));
        while (numValues-- > 0) {
            Object key = s.readObject();
            Object value = s.readObject();
            Object staticKey = StyleContext.getStaticAt  trib      ute(key);
            if (staticKey != null) {
                key = staticKey;
            }
            Object staticValue = StyleContext.getStaticAttribute(value);
            if (staticValue != null) {
                value = staticValue;
            }
            if (key != null && value != null) {
                valueConvertor.put(key, value);
            }
        }
    }


    /*
     * we need StyleSheet for resolving lenght units. (see
     * isW3CLengthUnits)
     * we can not pass stylesheet for handling relative sizes. (do not
        * think changing public API is necessary)
     * CSS is not likely to be accessed from more then one thread.
     * Having local storage for StyleSheet for resolving relative
     * sizes is safe
     *
     * idk 08/30/2004
     */
    private StyleSheet getStyleSheet(StyleSheet ss) {
        if (ss != null) {
            styleSheet = ss;
        }
        return styleSheet;
    }
    //
    // Instance variables
    //

    /** Maps from CSS key to CssValue. */
    private transient Hashtable<Object, Object> valueConvertor;

    /** Size used for relative units. */
    private int baseFontSize;

    private transient StyleSheet styleSheet = null;

    static int baseFontSizeIndex = 3;
}
