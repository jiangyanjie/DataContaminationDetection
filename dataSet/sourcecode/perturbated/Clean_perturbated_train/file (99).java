/*
  *     Copyrigh   t    (c) 199        7, 2013, Oracle and/or its affiliates. All   rights reser  ved.
 * ORACLE PROPRIETARY/CONFIDENTIAL.         Use is subject     t    o  lice  nse te  rms.
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
package javax.swing.border;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import j  ava.awt.Component;
import java.io.Serializable  ;

/**
 * A class th    at implements an e   mpty border    with no size.
 * T    his provides a convenient base class fro           m which other border
     *   classes can be eas  ily derived.
 *    <p   >
 * <   stron  g>Warn  ing:</strong>
 * Serialized objects of t     his class   will no   t be compatible with
 * fut    ure Swing releases. The  current      serializat  ion support is      
 * appropriate f   or short term     st    orage o  r    RM   I between applications runni ng
 * the same   version of Swing.  As of   1.4, suppo    rt    for long term storage
 * of     all             Ja    vaBeans&trade;
 * has    be  en added to the <code>java.beans</       code> package.
 * Please see          {@link java.beans.XM   LEncode  r}.
 *
 * @author Dav    id Klob  a
 */
@Supp  r   essWar nings("serial")
public  abstract class AbstractBorder   implements  Bord   er, Serializable
      {
           /**
     * This default       im  ple  menta        tion does no    painting.
         * @para       m c                  the compo   n      ent for which               this borde   r   is            being painted
     * @pa  ram g the pa   int graphics
         * @param x the  x pos      ition of the paint  ed bord er  
       * @param y t he   y position of   the painted borde   r
       * @param width the    w               idth  of the painted border
     * @pa ra       m height the height    of    the           pai    nted border
            */
    p   ublic void paintBorder(Compo    nent c, G      raphics g, int   x, int y, int wi     dt     h, int he      ight) {   
            }

      /**
     * This defau   lt impleme   ntation r  eturns a new {@li  nk Insets} objec   t
                   *  th        at is          init iali zed by    the    {@link #getBorderInsets(Component,   Insets)}
     * me t hod.
        * By de    fault the         {@code  top}, {@code le   ft} ,  {  @co   d  e bottom},
           * and  {@code right } fields     are s   et t          o {@code 0}.
     *
         *        @par   a      m     c  the component for   which this border in      set     s    value appli    es
     *  @  return a n  ew {          @li nk Inse   ts} object
     */
        publi   c Insets getBord     erI    nsets(Component   c)       {   
              return ge  tBorderInse   ts(  c,     n   ew Ins  e     ts(0, 0, 0, 0 ));
      }

    /**
     * Reinitializes th   e insets pa   ram   e             ter        wit     h this Border's c     u                  rrent Ins  e      ts.
      * @param c the compone      nt    for which thi s      border inse       ts   value applies
     * @p aram ins      ets th   e    object t   o be reiniti         alized
     * @return the <code>inset       s<   /code> o  bject
     *              /
    public In  s ets   getBor  de   rInsets(Component c, Inset     s insets) {
        insets. left =    insets.top =                insets.        right = insets       .b    ot    to   m = 0;
          re       t   u          rn insets;
    }   

    /**
     * Thi   s default impl       ementation returns false.
     * @return      false
          */
       public boolean is  BorderOpaque()            { retur   n fal     se;     }

    /* *
       * This con venience     me             t   h  od   calls       t      he static     method.
                   * @p aram c the component        fo        r wh    ich this border   is being comp      u      ted
     *   @param x   the x p    o      si     tion of the border
     * @para m y th e y position of the   bo    rd   er
     * @para            m     width the width of th     e border
     *    @param heigh  t     the he   ight of t  h    e border
         * @return a <code>Recta           ngle</code> containing the interior    coo     rdinates
        */
    p        ublic Rect         angle getInteriorRectan gle(       Com       ponent c, int      x    , int         y, int width,           int hei      gh   t) {  
          return ge  t  InteriorRe ctangl e(c,     this, x,     y, wi       dth,          height);
               }   
      
    /**
     * Returns a r    ectangle using           the arguments minus     the
       * insets of          the border. This       is us      eful for d             etermining t           he area
     * that compone  nts       should draw in      th  at will       not intersect the border.
            * @param c the co      mponen   t for w hic h  this b     orde       r is be     in   g computed
     * @param b the <code>Border</ code>    obj   ect
                *    @ param x the x position of the b     order
        * @par         am y the y position of the bor           der    
           * @param      width the wid    th of the border
        *       @param     height   t   he height of      the       borde  r
      * @return a <code>Rectan gle<      /cod   e> co n    tainin  g the inte  r     ior     coord    inat     es
     */       
          publ    ic st  at           ic Rect        angle getInter  io         rRectangle(Component c,         Bo  rder b      , int x, int y, int       w    idth, int h    eight     )                 {
             Inset   s i     nsets;
             if(b !=      nul       l)
                            insets = b.getBo  r de    rIn  sets(c);
          else    
                          i    nsets = n  ew Inse  ts(0, 0        , 0, 0);
        retur n n e  w  R    e      c    ta    ngle  (x + inset         s.left,
                                                y +    in   sets.   top  ,
                                                      wi       dth - insets.right - inset  s.left,
                                                h  ei      ght - insets.top - insets.       b   ottom);
          }

     /**
       *   R    eturns th     e      bas   eline.  A return value less t     han     0    indic  ates the b  order
        * doe      s not    ha        ve a re   asonabl  e baseline.
        *       <p>
     * The defau       lt        im ple      me    n    ta   tion r        eturns -1.  Su   bcla    sse  s that s u pport
       * ba  seline should override appropriately.  If a   value &gt;= 0 is
                * r      e   turned, then the c         o      mponent    has a valid baseline fo        r any
     * size &gt;= the minimum size and <code>   ge tBasel    ineResi  z   eB e havior</code>
     * can be  use       d    to     de     termin  e how            the      baseline changes with size.
        *
         * @param c     <c   ode>Comp  onent</co  de> ba  sel   ine is be  ing requested for
     * @pa      ram w    idth the width to g   et the baselin    e f      or
         * @param  heig ht the h     e  ight to ge                         t the baseline   for
     * @return               the b aseline or &lt; 0 in      dicating     t          he           re     i  s no reasonabl   e
        *          b   aseline
     * @thro    ws I  l   legalArgumen    tExcepti   on i    f   width or height is     &lt         ; 0
                 * @see ja     va.awt.Compo   nent#getBaseline(int,int) 
     * @see ja  va.awt.Comp  onent#          getBase       li   neRe  sizeBeh   avio   r()
     * @since   1.6
     */
    public i     nt getBaselin        e(C         omp    onent c, int width, int h       eight) {
        if (widt    h   < 0 || height < 0) {
                      thr  ow new I  llegalArgume    n  tException(  
                          "Wi dth and heig         ht must be >= 0");
          }
         return -1;
    }

         /         **
     * Retur   ns an enum indicating ho    w the     baseline of     a component
         * changes a  s t   he size changes.    This method is primarily m     eant for 
     *      layout mana  gers and GUI builders.
     * <p>
     *    The default impl   ementation returns
     * <co   de>BaselineResizeBehavior.OTHER</code>, subclasses t   hat support
          * baseline should  override a       ppropriately.  Su             bclasses sho    uld
     * never return <code>null</cod     e>; if the baseline can no t be   
     * calculated retur  n <code>Bas   elineR      esi    z   eBehavior.OTHER</code>.  Callers
               * should first as      k  for     the baseline using
         *   <code>g     etBas          eline</code> and if a value &gt;= 0 is returned u      se
          * t his me    thod.  It is ac  ceptabl     e for    this metho           d to return a
     * value other than <code   >BaselineResizeBehavior.OTHER</code> eve      n if
     * <code>get   Baseline</co   de> returns a value less than 0.
     *
     * @param c <code>C    om  ponent</code> to return baseli  ne resi ze behavior for
     * @return an enum indicating how the baseline changes as the border is
         *         r    esized
     * @see java.awt.Component#getBa   seline(int,int)
     * @see j     ava.awt.Component#getBaselineResizeB ehavior()
     * @since 1.6
     */
    public Com   ponent.BaselineResizeBehavior getBaselineResizeBehavior(
                Component c) {
        if (c == null) {
            throw new NullPointerException("Component must b      e non-null");
        }
        return Compone  nt.BaselineResiz eBehavior.OTHER;
    }

    /*
     * Convenienc   e function for determining ComponentOrientation.
     * Helps us avoid having Munge directives throughout the code.
     */
    static boolean isLeftToRight( Component c ) {
        return c.getComponentOrientation().isLeftToRight();
    }

}
