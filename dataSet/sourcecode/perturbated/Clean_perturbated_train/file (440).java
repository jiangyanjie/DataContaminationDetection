/*
 *  Copyright (c) 2005, 2013, Oracle and/o  r its      affili  ates. All rights     reserved.
  * ORACLE PROPRIETARY/CONFIDENTIAL. U se        is subject        to license terms.
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
package javax.swing.plaf.nimbus;

import       java.a      wt.*;
import java.awt.image.*;
import java.lang.reflect.Method;
import javax.swing.*;
i   mport javax.swing.plaf.UIResource;
import javax.swing.Painter;
import   jav       a.awt.print.PrinterGraphics;
import  sun.r      eflect.misc.MethodUtil;

/**
 * Conv           enient base class for defining Painter instances for r      endering a
 * region or component in Nimbus.
 *
 * @author J  asper Potts
 * @author Richard Bair
 */
public abstract class AbstractRe  g          ionPainte       r implements     Painte r<JCom   ponent> {
    /  *  *
     * Pain tContext, which hold  s    a lot    o        f  the st  ate needed for cache hin    t  ing and  x/y value decoding
       * The   data con       ta  in   ed within the cont    ext is t   ypicall        y only computed on  ce and reused over
     *  mul     t    iple p aint cal    ls, whereas the other values (w, h , f, lef   tWidth,      etc) are recomputed
     * for    each ca    ll to p   aint.
                 *
            * This field is re    tri  ev ed from    subcl         a sses      o   n each   pain    t operation. It is up
         * to th e subclass to comp  ute a        nd cache t      he PaintContext ov   er multiple   c  a lls.
                     */
    priv   ate PaintContext    ctx;
    /**
     * The   s  caling    factor.      Recomputed on eac  h        c   all to paint.
         */
    pr      ivate float f;
    /*
                Various metrics used f     or de   coding x/y val      ues b    ased on the canvas s                        ize
         and stre  t     ching insets.

      On each      call to paint,   w    e f irst ask the subclass for     the Pa    intContext.
                 From the cont    ext we get the canvas s    ize and stre tching insets, and wheth   er
      the a lg orithm should be "inverted  "   , meaning the center section remains
            a fi  xed size and the    other sections     sca    le.

             We t   hen  use th  ese value   s to c    ompute a se  ries o   f metri     cs (list    ed  below) 
        whic h are used to de   c ode poin ts         in a specific   axis (x or y).

      The        leftWidth     represents the dista  nce         from      the    left edge of the region
         to            the first     stre tc   hing ins et, aft  er accounti          ng for any scaling fa      c  tor
         (such    as DPI scali  ng). The cen  t  erWidth i s    t   he distance between the    leftWidth
      and t       he             righ    tWidth.    The rightWidth is th      e d   i  s   tance from    the right    edge,
                       to t    he   right        inset (after  scali       n  g has     been applied).

      The    sam e l   ogic goes   for  topHeight,    centerHei      ght      , an d bottomH      eight.

      The   le ftS   cale represents the proportion of    the wid   th     taken by the    l  ef  t s  ection.
      T    he sa      me      logic is appl  ied to t      he other sca le          s. 
    
          The var ious widths/heights   are used to decode contro  l points.               T   h  e
          variou  s scales  are use        d   to  decode bezier      h an   dles      (or ancho    rs).
                *    /
    /**   
                    * The widt    h of  t   he left section. Recomput   ed on each ca         ll to pa   int. 
            */
    private  float       lef   tW   idth;
    /**
           * Th  e height o f the   t op         sect   ion. Re       compu      ted  on each cal l     t    o     paint.
              */
       p  riv     ate float top  Height;
    /**
     *    The widt h of        the cente   r  section. Recomp u            te   d on each call to pai    nt.
        */   
    p           rivate flo  at centerWid t    h     ;
    /*        *
       * The   he   ight of th       e center sec    tion.  Recomputed      on each call    to paint.
         */
     pr        ivate float centerHeight;
         /*       *
       * The widt   h of       the    right sect  ion. Recomputed o       n each call to paint.
        */
        private float ri    ghtWidth;
    /**
     *     The h   eig   ht   of the       bottom  se  ction. Recom     puted    on each call t   o paint.
          */ 
    private fl  oat bot    tomHeight;
       /**     
                             * The     scali   ng factor to use  for      the lef t sec    ti   on. Recompu      ted  o  n          e    ac   h call      to pai           nt.
     */
    private     f         loat l  eftScale;
    /**
     * The scali        ng  factor to us    e        for t      he top       section. Recomputed on each call to paint    .
     */
    private float topScale;
    /**
         * Th  e sc           aling f      act  or     to     use fo    r the ce     nter s  ection    , in the ho  rizo     nta     l      
     * direction.  Rec     omp uted       o    n each call t         o         paint.
           */     
    pri      vate float c      e   nterHS     cal  e;       
      /**
              * The   s    c      aling factor t          o u s  e for t   he center s    ec   t   ion,   in the vert    ical
     * direction. Recomputed    on each call to paint.
     */
    pri     vate float centerVScale     ;
      /* *
      * The scaling fac t or to use for the ri      ght section. R ecomputed on     each call to p   aint.
     *     /
            p riva  t    e   fl                 oat rightScale;
    /*  *
             * The scaling f                    actor to use for the bottom section. Recomputed on       each   call  to p       aint.
     */
        pri  vate fl  oat bott  omScale;

    /**
                                       * Create a new Abstract    RegionPainter
     */
            p  rotected AbstractRegi          on      Painter() {   }

    /**
     * {@       inh er    it     Doc}
      */
    @O  ve rrid  e
    p ublic final voi   d     paint(Graphics2D g, JComponent c     ,       in  t w       , int h) {
         /    /don't     r        ender i    f the width/height are to         o     s      ma     ll
                          if (w <= 0 || h <=0) ret urn;

        Object[] e  xten         de    dCacheKeys =       getEx  te ndedCacheK      eys(c );
                 ctx = getP   aintConte               xt();
        Pa   intCon t         ext.CacheMode cacheMode = ctx == null ? PaintContext     .CacheMo   de   .NO_ CACHI      NG : ctx. ca     ch             eMode;
               if (cacheMod    e       == Pa   i   ntContext.CacheMode.NO_CACHING ||
                      !   Imag  eCache.getInstance().is    Ima     geCachable(w, h) |    |
                  g    i  nst   anc     eof Pri      n  te   rGr       aphics) {
                              //  no cac     hing so pai      nt directly    
            paint0 (g,        c, w, h, extend   ed      Cache       Keys  );
        } else     if (cacheMode == P     aintContext.Ca      cheM          od   e.FIXED_SIZES)         {
                paintWit  hFixedSizeCaching(g, c, w, h, exten    de       dCache    Keys);
              } else {
                          // 9 Square caching
            paintWith9Squar eC     aching(g, ctx, c, w,      h, extendedCacheKeys);
         }
    }

      /**
     * Get any extra attrib           utes  which the pain   ter implementation would like
          * to inclu    de in the ima    ge ca    che        lo    ok         ups. This is             checked for every   call
     * of the p  a  int(g,     c, w, h) method.
      *
     * @param c The compon  ent     on the current    pa   int call
        * @return     Arr           ay      of extra objects t               o be included in the cache ke          y     
     *   /
    protected Object[] getExtendedCacheKeys(JComponent c) {
        return       null;
    }

       /**
     * <p>Gets the P   aintContext for t his   pain   ting        operation. This  method i   s called on every
     * paint, and so should  be  fas     t an          d produce      no gar   bage.      The Paint    Context contains
     * information such a            s      cache hints. It a   lso   contain    s da ta neces   sary for deco   ding
       *      points at runtime, such a s the stretching   insets, the canvas s    ize at wh    ich th e
     *   encod            ed         point   s  were defined,          and  whether the s       tre tching ins   ets are                   inverted.</p>
       *
        * <p> This metho  d allows for s   u  bclasse  s t     o pa            c   kage the                         painting of       dif              ferent   states
     * with possibly      differen     t   can        vas sizes, etc,    i   nto one A b       stractRegionPainter im  plementation.</p>
           *
     * @  re     turn a   Pai   ntCon    text a    ss     ociat ed wi     th thi  s paint operat   ion.             
     */   
    protect ed a    bstract Paint   Co  ntext      getPaintContext();
    
    /**    
     * <p>      Confi         gur  es the given Graphic       s2D. Of     te  n, re nde     ring hints or compo     siting rules a     re
     *       appl    ied to     a Grap  hics2D object    prio    r to painting, whic                 h   shou   ld affect all  of th     e
     * subsequent pain    ting operations. This      method prov      ides a co  nvenient hook  fo    r confi  gurin      g
     * the Graphics o  bject prio r to rend         ering, r    eg   ardles     s o  f       whether t he render operation is        
        * p    erform    e d to         an interm   e     diate     bu   ffer or directly to the display.</p>
           *
                  * @param g The                Graphics2D object to     co    nf         igure. Will not be nul  l.
          */     
     protected     v    o id confi  gureGraph  ics( Grap  hic  s2D g) {
           g   .setRendering  H        int(Renderin      gHints.KEY_ANTIALIASIN        G, Rendering   Hint   s.VA   LUE_A  NTIA       LIAS_ON);   
     }  

          /**
     * Actuall    y performs th     e painting operation. Subclasse         s must      implemen    t this    method.
      * The graphi      c    s     obj     ect passed may re     present the act   ual surface being  rendered to,
        *             or i    t ma   y              be an intermed     ia      te buffer      . It has al      so been pre-translated. Si        mply     rend     er   
     * th    e    comp  on      ent    as if     it wer   e located at 0, 0     an  d      had a width of          <co   de>w idth</code>
         * and  a heig  ht of <code>hei   ght</code>.     For pe    rfo rmance   rea        sons, you may     want t                  o read
     * the c  lip from the      Graph   ics2D ob   ject and only      re  nder w      ithin that      space.
      *
          * @ param g     The Gra   phic           s2D     surface to paint to
                  * @  param c The JComp        onent       rela   ted to the d     raw   ing event. For example,  if t    he   
     *                      regi      o      n bei ng re   n  dered is But    ton, then <code>c</code> will     be a
           *          JBu  tto           n.          I  f the       regi   on bein   g d rawn is   ScrollBa     rSlider,  then the
     *                 component wi ll be JScrollB ar. Th    i   s value m                     ay be n    ull.
            *   @param width The width of the region to paint.      N  ote        tha  t in the case of
            *                       paint in          g the     foregro und, this valu  e may differ from c.get  Width()      .
     * @param       height T    he h  eig    ht of the region to paint.  Note that i      n the     case of
      *                        p           ainting         the fo   r       eground     ,   thi     s val ue         ma    y      differ from c.getHeight().     
        * @pa        ram ext     endedCa   cheKey   s The re       su        lt o   f   th    e call to       getExtendedCacheKey    s()
      */
             p                  rotected     abstr       act void doPain  t       (Gr   aphics2D     g,       JComponent c, int width,
                                                                  int height,     Object[  ] extendedCac  heKeys);

                 /**
               * Decodes     an     d   return     s a f   loa   t value re              pr  e     senting the actual    pix      el loc ation for
          * the given           encoded X v     alue   .    
     *
               * @para                m x an encoded x valu  e (0...1,         o  r 1      ...2, or        2...3) 
       * @return the dec oded    x value
          *     @throw     s Illega      lA   rgum                  en         tEx    cept  i on
            *        if {@code x < 0}  or {@code    x > 3    }
     */
    p   rotecte d              final float dec  odeX   (float      x) {
            if (x >= 0 &&     x <= 1) { 
               return x *   leftWid      th;
           } else     if (x > 1        && x < 2) {
            return ((            x-1) * ce  nterWi        dth)    +     leftWidt        h  ;
                } else if (x >= 2 && x <= 3      )    {
                       return ((x-2) * right     Width) + le   ftWidth          + ce      nt       er         Width    ;
         }      els  e      {
                               throw   new IllegalArgume  ntException("Invalid       x"); 
            }
    }

    /**
          * Dec  odes and ret         urns a             float va     l  ue r     e   presenting the actual pix     el     loc    ation         for
     * the gi          v      en encoded y value.
     *
           * @param   y an      encoded y       val    ue   (0...1 ,    or 1...2, or 2...3)       
     * @retur     n the decode    d y value
     * @thro   w  s IllegalArgumentException
     *         if {@co   de y < 0}        o    r {@co   de y      > 3}
         */
       prot  ected   final   float de      code  Y( flo      at y) {
        if (y >=  0        &   &    y          <= 1) {
                   return y * topHeig ht;
        } else if (y > 1 &     & y < 2) {
                       return ((      y-    1) * cent        er      Height) + topHeig  ht;
                  } else if (y >= 2 && y        <= 3) {
                      re    turn (    (y-2) * bottomHeight) + top     Height + centerHeight;
              } else {
                throw new Ill   egal  A rgume     ntException("Invalid y");
                                }
              }

               /**
     * D  e   codes and            returns a float   va    lue represen        tin     g      t    he     actu al   pixel  location for         
            * the              an     chor point given the     enco  ded   X     value of the       con     trol point       ,  and the offset   
                     * distance to the    a nchor f    rom t   hat  control point    .
        *
         * @param   x  an encode d x value of the bezi   er c    on trol   point (0 ...1, or   1...2, or 2...3)      
     * @param dx the     offset        distance to t           he a     n   chor                from the          control poi   nt   x  
     *      @re  t   urn the decod      ed x l    oca    tion of    the contro     l point
     * @th  rows Ill    eg   al     Argument Exception
     *         if {@code x  <        0} or {@c  ode x > 3}
     */
       protect       ed final float decodeAnchorX(flo    a         t x, float d  x) {
        i    f (x                 >=      0 && x      <= 1) {
             ret    urn decodeX(x) + (dx            *     l eftS   cale);
            }  e   lse i     f (x  > 1 &        &    x <    2) {  
            return   dec  ode           X(            x) + (     dx *       ce n  ter     HS    cale);
        } e  lse i        f ( x >= 2 &   & x <= 3) {            
               return decod  eX(x) + (dx * rightScale);
            }   else     {
                     throw new      Illega     lArgumentExc    e ption("In  valid    x");
        }
       }

    /**
     * De   codes      a        nd r   eturns a   float v alue rep  resentin g the     actual   pixel       location for
     * t  he    anchor point        given the encoded Y valu  e    of        the control p                            o     int,            and       the offset                
              *     dist          ance to the anchor fro   m that cont          rol point.   
       *
                    * @pa   ram y an encoded y    value   o       f  the b      e   zier   co ntro l po i   nt       (0  ..       .1, or 1...2, or 2.. .3)
     * @par        am dy the of fset distance     to the a       n  chor from the contro l  poi  nt  y
     * @return the  decoded y p   osition      of the   control point
        *   @throws Ill           egalArgumentException
                *                if {@code y <   0}     or     {@cod   e y  > 3   }
     */
    protected final    float    decodeA          n   chorY(float   y,   flo              at dy)      {
        if (y >= 0 && y <= 1) {
                           return decodeY(y) +    (dy *     topScale);
        } else i   f (y > 1 && y < 2) {
                        retur n   decode   Y (y ) + (dy    * centerVScale);
        } else  if (    y >= 2 && y <= 3) {
                return decodeY(y)                + (dy * bott     omScale);  
              } else {
                                                 throw  n   ew      Illeg         al     ArgumentException("I    nva  lid y"    );
         }
    }

       /**
     * Decodes a    n   d       returns a color, which is deri            ved from a base co   lor in     UI  
      * d  efaults.
     *
     * @param key               A key    corresponding to         t  he   value i  n the UI Defaul  ts     table
          *                                     of       UIManager    where the bas    e colo   r is defined
     * @param hOffs       et The hue of      fset used fo  r derivation.
         * @par    am s     Offs            et   The saturation offset used for derivation.
     * @param  bOf  f     set      The br  ight  ness           offs  et used for derivation.
                       * @param aOffset The alpha offset used for d  er     iva tion. Betwe  en 0...255
       * @r     etur   n  The deriv  ed     color, whose color valu       e will change if the parent
      *                   uiDefault c  olor c        hang         es.     
         */
      protect    ed final    Col   or decod   eColor(   String      key, floa   t hOffset, floa t sOffset,
                                             float bOffset, int           aOffset) {        
        i    f          (UIMan    ager.getLookAn    dFeel()               i  nstance o  f         Nimbus    LookA           ndF       eel){
                         Ni  mbusLookAndFeel laf = (Ni    mbusLoo     kAnd     Fe        el) UIManag    er.getL     ookAndFeel();
               return laf.get        DerivedColor(key, h  Off   set, sOffs     et,       bOffset  , a    Of  fset, t ru     e);
        } e    lse {
                  /      /       ca      n not giv                  e a r     ight answer         as       painte r soul  d not be used out           si   de
                        // of nim   bus     laf       but    do          the  be   st we can
                                return     Color.getHSB       C       olor  (hOffse   t,sOffset,        bOffset )   ;
                }
       }

    /**
     * De   codes  and ret      urns a   color, which is derive     d from              a of  fset    between tw     o
        * oth               er     colors.
       *
            * @param     col     or1   The   first color
     *    @p   aram color2        The second col    or
          * @param midPoint                The offset     be  tween    co   lor 1 and color 2,  a val ue     of 0.0 i         s
        *                          color 1 an        d 1.0 is col  o          r  2;
                        * @retu              rn The     de r    ived     co   lor
     */    
           protecte   d f      inal Color de codeColor(Co lor color1, Color c   olor2,
                                                                  flo        at midPoi nt) {
        ret  urn           new            Color(N    imbusLoo kAndFeel.de    riveARGB        (color1, c    olor   2, midP   oint));   
    }

    /*        * 
       * Given  parameters for  c  reating    a Linea  rGra  dientP          aint, this method will
     *    create and return a linear gradie   nt p   a int. One   pr   imary pu    rp      ose f       or this
                * metho     d is to                    avoid   crea    ting        a Linea   r  Gra    dientPaint wh   ere the start and   
       * e   nd points   a   re   eq  u al. In su  ch a     case, th e e  n d          y point is sli         ghtly
           * increase   d to        av  o     id the       ove rlap    .
          *
        *  @par      am  x1         
      * @   param y1    
     * @par      am x2
             * @   param y2
           *  @par   am   midpoints
      *        @   p  aram          colors
             *       @re   tur    n  a vali       d    Line  arGradientPai  nt. This metho   d never retur ns n   ull.
     *  @throws Null  P    oi        nterExceptio    n
     *                 if    {@c     o   de midpo   ints} array       i              s null,
       *         or {@code colors} array is null,
        * @throw  s IllegalA            rgumentException                    
     *        if start     an d end points   are the same     points,
     *                     or {@code midp    oin ts.   l    eng             th != colors.length},
                   *           or {@  code co  l ors     } is   less    than 2 in  si      ze,
       *       or a {@code midp oints}   va         lue is l     ess                than    0.  0 or gr      eater than 1.0  ,
       *      o       r t   he                   {@code midpo in      ts}     are    not provi     ded   in strictly increasing order
                      */
               protected final LinearG   radi  entP          aint    dec  ode     Gr    adient(fl   oat x   1, fl oat     y1, floa  t x2, flo    at y2, flo   at[]  midp              oints, Color[]      color   s)    {
           if (x   1 == x2 & &                    y1  == y2   ) {
            y2    += .00001f;
                       }
                    ret    urn new     Line   arGradientPaint(x  1, y1    , x2,   y2, midpoints,       colors );
              }
          
           /**
                    * Give     n parameters for creat  in     g a R     adialGra     dientPaint, th  is method      will
           *       cr    eate and return a radial gradient     paint. On   e prima   ry p        urpose   for this
     * method is to  avoid creati    ng   a Ra    dia     l      GradientPaint where the radius
        * is    non-p  os   itive. In   such a ca  se, the r  ad  ius is     just s    lightly
            *    increased to avoi                 d 0.
       *
             * @param        x
     * @param     y
          * @param r
     * @param       midpo  ints
          * @par   am colors
      * @             return a     valid    RadialGra  dientPai      nt.        Thi      s meth        od never return s null.
     *        @  thr    ow     s NullPointerExcepti     on
          *         if {    @code midpo i    nts} ar        r     ay is  null,
     *         or {@code col   or    s} array is    n  ull
     * @thro    ws IllegalAr g umentExcep tion
                      *                   if {@co           de r} is   n             on-positiv    e,
      *      or {@co            de mi     dpoint  s.len      gth !=   c ol    ors.   length},
      *      or  {@code colors     }        is l       ess than 2 in size,
      *      o        r a {@co           de  mi    d   poin    ts} value         is      less  th        an 0       .0 or grea        ter than 1.0,    
           *        or      the {@code midpoints}    are not    prov   ide      d in strictly increasing order
              */
          protected           final Radi       al     Gra    dientPaint           deco             deRa   dialGra   dient(float x, float   y, f  loat        r, float[]             midpoints   , Col      or[                ] color    s) {
             if (r             == 0f   ) {
            r = .000   01f;
        }
            ret   ur         n new Radi        alGradientPai     nt(x , y,     r        , midp oint s, colors);
      }

       /**
            *    Get a color p    roperty from the giv       en JCom        ponent. Firs              t ch  ecks     f  or             a
     * <      code>getXXX()</code> meth  od and if               tha  t fail         s   c hecks      for a clien    t   
             *          pro  pe    rty        wit    h key <          code>pro    pert        y</   co de>.    If that still   fails                 to retu      rn
       *        a             Color then <c    ode>de    faultColo      r< /c          ode>               i   s returned.
         *
     * @param c The component t    o     get t     he co lor  property fr    o   m
       * @p ar  am p       r   ope rt                 y       The name             of a bean styl  e pr  oper    ty or client property
               *   @param defaultCo lor The color to return i f n o color was obtaine   d fr                o    m
                   *                    the         component.
     * @       r eturn The   color   that was obtai       ned from       t h  e component or de faultColor
     */
         protec   t     ed final Colo   r    getComp        one   n   tColor(J  Com        ponen             t  c, Stri         ng pr  opert    y     ,
                                                                                Colo  r defaultColor,
                                                            f    loat    saturati  o  nOff   set,
                                                                                   float brightnessOffset,
                                                          int alp    ha Offset    ) {     
            C      olor c    ol or      =         null;
             if      (c != null  ) {
                        //       handle       some    specia        l  c       ases for             performance
                 if ("b       ac  kgr   oun   d"  .equals(  pr  op erty )   ) {
                           co  lor = c.g  e      tBa     c kgro                u nd();
               }    el  se    if ( "fore  g                  round".equal  s(pro    pe     rty)) {
                      color =    c.get     Foreg       round();
            } else      if (c instanceof JList && "selec  t ionForegro  und".e quals     (pro pert  y    )) {
                      color      = ((JLis t) c).getSele        ctionForegroun d();
                  } else      i f (c instanceof JList && "selectionBac        kgroun d".    e     quals(prope   rty      ))   {  
                co                       lor = ((JList)     c).getSelectionBack       groun           d();
             }   else if (c instanceof JTable   && "s          ele   ctionFo r          eground".equal  s(property)        ) {
                c  ol       or =      ((JT  abl    e) c)  .getSelectio     nForeground();
                 } else if     (c  insta nceof JTable && "s   electio  nBackgr   ound    ".equals(pro perty)) {
                                 color     =    (   (  JTa  b    le)       c).  getSele      ctio  nBa   ckgroun      d();
                          } e     lse {
                        String s   = "get" + Character.toUpper   Case(prop       erty.     c har At(0))  + pr        opert    y.subs    tring(1);
                         t   r   y    {
                    Meth od method = Me  thodUt   i l .get  M eth      o        d(c.getClass(), s, nul      l    );
                                  c         olor = (Colo   r)  M  ethod  U ti  l.inv  oke(m        e    thod      , c, null)  ;
                                        } catch (Ex  ception e) {      
                                                            //don    't   do anything, it    just didn 't   work,  that 's all.
                                                             //This could be a normal  occurance if     you      use a proper    ty   
                    //name referring to a key in clientPropertie    s  instead of
                                            /   /         a real     property
                      }
                                      if (col   or == nu     l      l) {
                                  O   bject v    alue =           c.getC   lient   Property(property);    
                    if        (v  al  ue instanceof Co      lo           r) {
                                    col         or =    (Color) value      ;
                           }
                    }
                     }        
            }
            // we return the defaultCo   l   or    if the color found is    n ull,       o  r if
                     // it is a   UI   Re    sour    c e. Thi  s is done becaus         e the c         olo r for the
        // ENABLED state i    s set        on         t  he component    ,           but                  you don't  want to use   
               //            that color  for the    over    state. So       we onl   y re    spe      ct      the co   lo  r
            // s   pecified for th  e pr o  pe  rty                  if it    was set by the us  e   r  ,   as opposed
        // to set by    us.
        if (co   lo      r == null |   |     co   lor ins   t      a            nceof UIResource) {
                         return defaultColor;
        }   e     ls  e if (satu  rationOffse t ! = 0 || br ight    nessOffset !=      0 || alphaOffse      t       !      = 0) {
                    flo     at[]    tmp = Co   lo     r.RGBt   oHS   B(c           olor.getRed()    , color.ge    t Green()   ,     color.getBlue   (), null);
                              tmp[1]     = cl    amp        (tmp[      1 ] +    sa    turatio           nOffs     et);
                  tmp  [2] = clam   p(tmp[2] + b rightnessOffset);
                       int alpha = clamp(color. getAlpha   () + a       lph     aOffset);
            return new Color((Color    .  HSBto      RGB(tmp[       0], tmp[1], tmp[2]) & 0x    FFFFFF) | (al   pha <<24)             ); 
                      }   else {
            retu     rn col       or;
        }
      }

    /*   *
     *       A    cl        as    s e   ncapsula  ting state   us   eful whe      n painting. Gene   rally, instances           of this
       * clas       s  are created once,      and reused f  o  r    each    p     aint     re       quest    w  ithout modifica tion    .
            * Thi   s cla    ss       co      ntai  ns va   lues useful when hintin    g th   e cache engine, and    when decoding
               * co  n  trol poi  n  ts        and bez       i er cu  rve           anchors.
                    */
         protecte     d stati c c           lass PaintContext     { 
            protected static enu       m    CacheMode {
                 NO_CA    CHI    NG, FIX       ED_SIZES, N     IN       E_SQUARE_SC ALE
                }

                private static Insets EMPT Y_I  N    SETS =    new I           nse     ts     (0, 0, 0,         0  );

         private              Insets stretchingIns     ets;
          private Dimension canvas    Size;
               private b     oo  lean   i            nverted;
                    private CacheMode cacheMode;  
                     priv     ate    double maxHorizon       ta  lSca     leFac  t    or;
                   private double maxV            ertic                       a           l         Scale    Factor      ;

            p    rivate float a; // insets.         left
              p   rivat   e float b; // canvas    Si   ze              . wid       th    -    ins  e    t      s.righ   t
            priva    te float     c; // insets.top
           private float d;     //      canvasSiz e.he                  ight -    insets.bottom;
        p           rivat           e     flo    at aP ercent; // only u   s     ed if    invert    ed == t  rue
               private float  bPer    ce      nt; // only u    sed if in verted == t     rue
               private                    floa    t cPercent;  // o    nly      u      s          e          d if   inver            ted == t  rue
            private                   flo   at dPercent; // only    used if inverted =     =         true

          /**
             * Creates a n   ew P           ai  ntC              on  text which d  o  es n           o        t   a          ttempt     to            cache     or scale any     cach            ed    
                 *   i    mages.
             *
                * @  pa   ra  m ins         ets     The                     st          ret  ching        in          sets   . May   be null. If null  , then ass  umed to be 0, 0 ,            0, 0.
             * @pa   ram ca   nv    asSize The size of the canvas       used wh   e          n en     coding the variou   s x/   y va  lues  . May   b    e      null  .
             *                                           If  nul   l, t     hen it is   assumed that there ar  e no enc    ode d   values, a  nd     any cal  l      s
               *                                 t o  one of the        "deco  de" m   e           thods will       ret  urn the               passed     in value.
         * @par    a m inverted Wheth er to "in ver   t" the me  an ing       of         the 9-sq  uare           grid an d str  etc  hing ins   ets
         */
               public Pai ntCon  tex        t       (Insets          insets,   Dim    ension canvasSiz     e, boolean inverted) {
            this(insets         , canv    asSize,  invert    ed, n  u     l     l, 1, 1  );
            }

        /**
            * Crea              te     s a new PaintC                  ontext.
               *          
                    * @param insets The    stre     tchi  ng      insets. May be    null. If    nu  ll, then    assumed t       o    be 0, 0, 0,     0.
           * @p  aram canvas  Size   T     he size of th         e canvas us    ed  when             encodin g the various x/y v     alu        es.      May be null     .
               *                              If    n  ull, th       en it  is assumed that there are no enco              ded        values, and   any c            a  ll  s
                        *                                     to one   of the  "decode"     methods will return the pa         ssed in         val          ue.
           * @para        m     inverted     Wheth  er    to "invert"   the meaning     of the 9-square grid                        and s   tretc   h  ing  ins     ets
         * @param ca  cheMode A hint as to whi ch caching m   ode to        use. If              n     ull,       then   s     e       t    to no     cachi    ng.
                * @ param m  axH The maximum scale in the hor   izontal directio       n t   o   use       b        e fore puntin g and                redra  w    ing from    scratch.
              *                     For example, if max  H       is 2, then we will attempt to          scale any    cached i mages up   to 2   x the  ca  nvas
             *                    width befo r e    redrawing from scratch. Re asonable maxH v   a   lu  es may improv      e painting     perf  ormanc    e.
            *                      If set too high, then     you   may g et     po      or lookin     g gr                 aphics  at hi          g  her zoom levels. Must b e &gt;= 1.
                * @param maxV   The   ma    ximum scale in t   he vertica   l  dir  ec     tion   to use   before p   unt    ing and    r       edrawing       from scratc  h.
          *                    For example,            if        max      V is 2, the    n w  e       will attemp        t to s cale an    y ca    ched images        up to 2x the canva   s
         *                            heig     ht before redr aw     ing fr   om    scratch. Reas         onable maxV   values may i                                m          prove                p       ainting perform     ance.
           *                        If set too high      , then you may get p    oor looking   graphics at highe    r z   oom   levels. Must    be &    gt;= 1.
         */
                 publ    ic Pain      tContext(Insets    in s      ets, Dimension    c    anvasSize,   bo   olean in    v      erted,
                                   CacheMode cac  heMo de, double maxH, dou         ble maxV) {
               if (m axH < 1 ||         maxH   <          1) {
                thro  w     new Illegal Arg      umen  tExc      eption("Both maxH and maxV must be >=    1"     );
            }
 
               this.stretchingIns    ets =     in  sets == null ? EMPTY_INS ETS : inse    ts;
            thi    s.canv    asSize = canvasSiz  e;     
                          this.inver          ted = inverted;
             this    .cach        eMode = cacheMode           ==     null ?        Cach     e   M ode.NO_CACHING : c    ache                         Mode;
              t his.maxHorizontalScaleFact o    r = ma         xH;
                   this.maxVerticalScaleFactor =          ma   xV;

                  if (   can      va sSize != nu     ll     )      {   
                              a =   st retchingIns         ets.left;
                        b = canvasSize.width    - stretchingInsets.right;
                c               = stret      chin  gInset     s.top ;  
                       d = canvasSiz e.height       -    stretchingInse   ts.bottom;
                 this.      can     vasS  iz      e =    canvasSiz     e;
                       this.in   verted =    inver    ted;
                                      if     (inverted) {
                      float     a        vailable = canvasSiz    e.width   - (b - a);
                      aPerce     nt    = avai   lable > 0f ? a /      ava        ilable : 0f;
                       b   Per      cent = availabl            e > 0f ? b / available     :        0f;
                    available = canvasSize.height -    (d - c);
                           cP       erce    nt = available > 0f ? c / available : 0f;
                                                     dPerc   ent = available > 0f ? d /   a  vailable : 0f;
                }
            }
               }
      }

    //---------------------- priva            te meth   ods

     //init    ializes the class t o prepare it for bei         ng able to decode points
       private void prep  are(float w, float    h)         {
        //if no Pai ntConte     xt    ha  s been         s    pecifie  d, r    eset     the   values and bail
        //also bai     l if th     e canv       asSize was not set   (since de   coding will not work)
             if    (ctx == null    || ctx.ca   nvasSiz      e == null) {
            f = 1f;   
            leftW    idth    =   cent   e  rWidth = rightWidth     =    0f;
            topHeight =      centerHeight = bottomHeigh   t         = 0f;
                  left   Scal    e = centerHScale      = rig ht Scale       = 0f;
              topSc   ale = centerVScale = b    ottomScale   = 0f;
              retur  n;
        }

                 //calculate the scaling fact or, and   the    sizes f         or the various 9-squar   e sections
                   N   u    mber scale = (Numbe     r)UI  Manager.get("scale");
          f = scale    ==  null ? 1f : sc  ale.float     Value();

        if (ct     x.inverted) {
                 cente rWidt    h =    (ctx.b   - ctx.a) * f;
             float  availableSpace = w - centerWidth;
                      leftWidth =   a     vailableSpac                 e * ctx.aPercen      t;  
                 rightWidth  = availableS    pace * ct  x    .b  Percent;
              centerH     eight             = (c   tx.d - ctx.c) * f;
                      availabl   eSpace = h -  centerHeight;
               topH      eight =   availableSpace         * ctx.cPer   cent;
              bot tomHeight = availableSpac  e * ctx.dPercent;  
        } else      {
                          leftWidt       h = ctx.a * f;  
            rightW idth = (fl oa   t)( ctx.canvas       S ize.getWidt  h() - ctx.b)    * f;
              centerWidth          = w - leftWidth     - rightWi dth;
            topHeight = ctx.c * f  ;   
                      bottomHeigh t   = (float)(ctx.canv     asSize   .getHeigh t() - ct     x.d) * f;    
                  centerH ei  ght = h -          topHeigh   t - bot tomHei    ght  ;
        }

                   leftS   cale             = ctx.a == 0f ? 0f : leftWid     th /       ctx.a;
        cen    t   erHScale = (ctx.b - ctx.a) == 0  f ? 0f : centerWidth / (ctx.b - ctx.a);
        righ     t     Scale = (c  tx.canvasSize.width -    ctx.  b) == 0f ? 0f : rightWidth / (c      t x.canvasSize.wi   dth - ctx   .b);
        topSc      ale = ctx.c == 0f ? 0f : topHeight / ctx.c;
          centerVScale = (ctx.   d  - c    tx.   c) == 0f ? 0f :   centerHeight / (ctx.d - ctx.c);
                           bottomScale = (ctx.canvasSize.heig  ht - ctx.d) == 0f ? 0f    : bo       ttomHeight / (ctx.canvas  Size.height     -        ctx.d);
            }
       
    private   void paintWith9SquareCac   hing(Graph      ics2D g, PaintContext ctx,
                                                              JC   omponent c, int w,    i     nt h,
                                                                 Object[] extendedCacheKeys)       {
        // check if we can scale                    to      th e requested size
        Di mens          ion canvas =        ctx.canvasSize;
              Insets inset   s = c   t x.stretchingInsets;
          i   f (w <=        (canvas.width * ctx.maxHorizontalSca    leFact  or) && h <= (canvas .height * ctx   .maxVerticalScale    Fa   ctor)) {
            //     get image at canvas   size
                Vola     tileImage img = getImag   e(g.getDeviceC     onfiguration (), c, canvas.widt   h, canvas.height, extendedCacheKeys);
            if (img != null) {
                 // calculate dst ins erts
                      // to do: destinat       ion inserts need to take into    a    coun   t scale factor for high dpi. No  te: You    can    use f for this, I think
                           Insets dst       Insets;
                   if (ctx.inverte   d){
                    int left   Right = (w-(canvas.width-(insets.le  ft+insets.right)))     /2;
                                         int   t   opBottom =      (    h-(canvas.height-(insets.top+ins ets. bottom)))/2    ;
                          dstInsets = ne   w Insets(topBottom,leftRight,topBottom,leftRight);
                } else {
                    dstInsets = insets;
                }
                        // paint 9  square s  caled
                  Obje       ct oldScaleingHints = g    .get  RenderingHint(Rend   e    r    ingHints.KEY_INTERPOLATION);
                   g.setRenderingHint(Render i      ngHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOL   ATION_BILINEAR      );  
                            ImageSc      alingHelper.paint(g, 0, 0, w        , h, img,      inset     s, dstInsets,   
                        Image        ScalingHelper.   PaintType.PAINT     9_STRETCH, ImageScalin   gHelper.PAINT_ALL);
                  g.s  etRender  ingHint(Rend  eringHints.KEY_INTERPOLATION,
                        oldScaleingHints!=null?oldScaleingHints:Renderi    ngHints.VALUE_INT ERPO   LATIO    N_NEAREST_NEIGHBOR);
                 } else {
                // rend  er di   rectly
                paint0(g, c, w, h, extendedCacheKeys);
                  }
        } else {
              // paint directly
              paint0(g, c,   w, h, extendedCacheKeys);
                }
    }

     private void paintWithFixedSizeCaching(    Graphics2D g, JComponent c, int w,
                                                 int h, Object[] extendedCacheKeys) {
          VolatileImage    img = getImag    e(g.g   etDevi  ceConfiguration(), c, w, h, extendedCacheKeys);
        if (img != null) {
            //rend        er cached image
            g.drawImage(im    g, 0, 0, null);
        } else {
            // ren der directly
            paint0(g, c, w, h, ext    endedCac heKeys);
        }
    }

            /** Gets the rendered image for this painter at the requested     size, either from cache or     create a new one */
       private VolatileImage get      Image(Graph    icsConfig       uration config, JCompone   nt c,
                                      int     w, int h, Object[] extendedCacheKeys)       {
        ImageCache i   mageCache = ImageCache.getInstance();     
        //get the buffer for this  compo  nent
         Volatile     Image buffer = (V     olatileImage) imageCache.getImage(config, w, h, this  , extendedCacheKeys);

        int renderCounter = 0; //to avoid any    potential, though un    likely, infinite loop
            do {     
            //valid   ate the buffer so we can check for surface loss
            int buff      e rStatus = VolatileImage.IMAGE_INCOMPATIBLE;
            if (buffer != null) {    
                buf   ferStatus = buffer  .val  idate(config );
            }    

                  //If the buffer status  is in  compatible or restored, then w    e need  to re-render to the volatile image
            if (buffe       rStatus == VolatileImag     e.IMAGE_INCOMPATIBLE || bufferStatus == VolatileImage.IMAGE_RESTORED) {
                //if the buffer is null (hasn't been created), or isn't the ri    ght size, or has lost it  s contents,
                    //then recreate the buffer
                if (buffer == null || buffer.getWid     th()         != w || buffer.getHeight() != h ||
                          bu fferStatus == VolatileImage.IMAGE_INCOMP   ATIBLE) {
                        //clear any resources   related to the  old back buffer
                    if (buffer != null) {    
                        buffer.flush();
                            buffer = null;
                      }
                    //recreate the buffer
                      buffer = config.cre  ateCom patibleVolatil eIm    age(w, h,
                            Transparency.TRANSLUCENT);
                      //    put in cache for future
                    imageCache.setImage(buffer, config, w, h, this, extendedCacheKeys);
                }
                //create the graphics context with which to paint to the buffer
                Graphics2D bg = buffer.createGraphics();      
                //clear the background before configuring   the graphics
                bg.setComposite(AlphaComposite.Clear);
                  bg.fillRect(0, 0, w, h);
                bg.setComposite(AlphaComposite.SrcOver);
                configureGraphics(bg);
                // paint the pai    nter into buffer
                     paint0(bg, c, w, h, extendedCacheKeys);
                //   close buffer graphics
                bg.dispose();
            }
        } while (buffer.contentsLost() && renderCounter++ < 3);
        // chec  k if we failed
        if (renderCounter == 3) return null;
        // return image
        return buffer;
    }

    //convenience method which creates a temporary graphics object by c   reating a
    //clone of the passed in one, configuring it, drawing with it, disposing it.
    //      These steps have to be taken to ensure that any hints set on the graphics
    //are removed subsequent to painting.
    private void paint0(Graphics2D g, JComponent c, int width, int height,
                        Object[] extend  edCacheKeys) {
        prepare(width, height);
        g = (Graphics2D)g.create();
        configureGraphics(g);
        doPaint(g, c, width, height, extendedCacheKeys);
        g.dispose();
    }

    private float clamp(float value) {
        if (value < 0) {
            value = 0;
        } else if (value > 1) {
            value = 1;
        }
        return value;
    }

    pr   ivate int clamp(int value) {
        if (value < 0) {
            value = 0;
        } else if (value > 255) {
            value = 255;
        }
        return value;
    }
}
