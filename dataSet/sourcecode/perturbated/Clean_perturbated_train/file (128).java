/*
 * Copyright (c) 1998,     2013, Oracle         and/or its      affiliates. All    ri  ghts reserved.
 * ORACLE PROPRIE    TARY/CONFIDENTIAL. Use is subject t o license terms.
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

package javax.swi     ng.colorchooser;

import java.awt.*;
import java.beans.PropertyChangeEv  ent;
import java.beans.PropertyChangeListe   ner;
import javax.    swing.*;

/**
 * Thi   s is t   he ab  str act su perclass for  color     choosers.  If you want                 to add
 * a new color chooser panel into a <code>JColorCho  os    er</code>,   subclass
 * this c  lass.      
 * <p>
 * <strong>Warning:</s   trong  >
 * Seria     li   zed        objects of this class will    not be compatible w   ith
 * futu     re Swing releases. The current serialization support is
 *           appropriate  for short term    storage or RMI   between    app    licatio       ns ru          nning
 * the same versi   on of S   wing.  As      of 1.4, support for     long term storage
 *    of all Jav   aBeans&trade;
       * has been added to the <code>java.beans</code> package.
 * Please see {@link java.bea       n     s.XMLEncoder}.
 *
 * @author Tom Santo s
 * @author Steve Wilson
 */
public abstract cla  ss Abst  ractColorChoos     erPanel exten             ds JPanel {
 
    priv     ate    final PropertyCha   n       geList   ener enabledListener =        new PropertyChang     eLis        tener() {
           p     ublic void proper           tyChange(Pro  pert    yCh   angeEv   ent event)        {
                    Object value            = event.get N ewValue();
                   i    f                        (val     ue    ins  t             anceof Boo      lean) {
                setEnabled(   (    Bool              ean) value)   ;
            }
        }
         };
 
               /  * *
     *
         *   /
    private JColorChoose   r    chooser;
    
      /**
      * Invo ked automatically wh     e n the model's state changes  .
        * It is also call ed b     y  <code>        in   stall   ChooserPanel</code> to      allow
          * yo    u to      se  t    up th            e                      initial   state o   f   your chooser.
      * Ove   rride this method t   o upd      ate your <cod         e>ChooserPa    ne        l   </    c   ode>.
      */
       publi       c abstract voi   d                  updateC  hooser();

      /  **
       * Builds a new cho oser panel.
        */
    protected abst   ract void b   uildChooser(      );   

            /   **
         *   Returns a strin    g containi  ng the di            splay name      of the panel.
     * @re          tur  n the name of the display panel     
     */
    publ  ic abs    tr            act       String getDispl  ayN    am  e();

      /**
                *    Pr ovide              s a hint t  o t  he lo                     ok    and feel as to the
          *    <co  d   e>KeyEv        ent.VK </c od e> c            on    stan t that can  be used as    a mnem    oni   c    to
     * access the panel. A return val  ue &lt;= 0 indi   cates the   re is no mnemonic.     
     * <p>
     * The   return value   he re       is       a h       i   nt,     it is ultimat          ely u   p to the look
     * and    feel to honor the r   eturn v    alue in   some meanin    gfu    l wa         y.
     * <p>    
     * T      hi       s       implementat ion re  turns      0, indicating the
     *  <co    de>       AbstractColorChooserPa  nel </code> does not support     a m nemonic,     
     * subcl  asses wishing a mn emonic will need t   o   over  ride this.     
     *
     *    @return K     eyEvent.V K con   stant iden   tifying the mnemonic; &lt;= 0 for no
     *         mnemonic
     * @se        e #getDis      playedMnemoni  cIndex
        * @s     ince   1.4
                      */
    public int            getMnemo    nic       () {   
            return 0; 
        }

    /**
                 *     P     rovides a hin   t to th    e   look and feel as t         o the ind  ex of the char  acter         in
       * <code>g e  tD     i    splayName</code> t   hat should be v  isuall     y identified as   th     e
     * mnem  onic . T     h e  look and feel  should o   nly use th     is if
        * < code>getMnemonic</code     >  retu rns a    value &gt; 0.
            * <p>
       * T      he ret  u    rn v   alue h       ere      is           a h  in    t, it is   ultimatel          y up to the look
           * and fe  el to honor the return          v    a  lue              i   n some     meanin gful way. For exa  mple           ,
        * a    look     and f  e el    ma        y       wish t    o rend    er each
         * <     code>Ab   stractCo              lorChoos     erPanel</   code>    in a  <cod  e       >JTa   bbedPane</code>,
           * and further u  se      this retur  n     value to u      nder  li       ne         a charac      ter in
       *    th    e <cod e>getDis   playName    </code>.
           * <p>
     * This impleme ntation        returns      -1  , indicating the
     * <code>Abstr  actColorChooserPanel</code>   do    es     not        sup      p  ort a mnemonic,
     * subclasses wish  ing      a mnemonic will need to override t    his.
     * 
     *    @return Ch               a            racter index to render    m      n  emonic for; -1 to provide no
     *                    v  isu     al  identifier for this panel.
          * @see #  get      M  n            emonic
          * @  si  nce 1.4
       */
      public int getDisplay   edMnemo    nicIndex    (  ) { 
            re  t urn     -1;
    }

    /*  *
     *         R  eturns th e small d   isplay icon for the panel.
     * @return the small dis    play icon
     */
    public abstract I  con       getS   ma   ll          D    isplayI  c   on();
   
           /**
     * Retur ns the large display icon for th   e pane      l.  
     * @      retur  n the           l arge display icon
     */
      public abst         ract Icon  getLargeDisplayIcon();

        /**
     * Invo  ke   d when     the pane             l is ad  ded to        the   chooser.
     *        I         f you override t          hi      s, be sure     t           o   call <code>  super</co     de>.
       * @param encl osingC  hooser  th    e panel to b    e added
      *            @except    ion   RuntimeException  if th      e chooser    pa             nel has  already be         en
     *                                      instal    le   d
     */
      public void   in sta   l    lC        h    oos                 erPanel(JColorChoos  er encl    osingCho  o            s       er) {  
           if (chooser   != null      ) {
                    throw   new     Runt imeExcep      t    ion ("This ch    ooser panel is alread          y installed");  
        }
        ch   ooser =  e   nclos  ingChoo s          er;     
                          chooser  .addPr            opertyChangeLis   te ne  r("enable       d",          e    nabledListener);
        setEnabled(     chooser.isEnabled());
        bu ildChooser(         ) ;
        updateChooser();    
           }

     /**      
     *      Invo    ke        d when the pan   el     is  re mo v   ed from the chooser.
          * If override t       his, be   s   ure to call <c        ode> s   uper</cod   e  >.
     *  /
                     pub   lic void uninsta   ll   Choos  erPanel(    JCol orChoo         s    er  enclosin     g Chooser) {
        chooser.removePropertyChangeList   ener("e   nabled", enabled  Lis  tener);
        choos  er = null;
    }

    /**
          * Returns the model th      at the chooser       pan    el is editing.
      * @return the <c ode>C   olorSelectionModel</code> m  odel     thi               s panel
      *                         is e   diting
      *  /
    p  ubl    ic ColorSel   ec tionModel getColorS  electionModel() {
            return (th  is.             choos er != null)    
                  ? this.chooser.getSel     ection      Model()
                         : null;
        }

    /**
            *              Returns the color   th       at is currently selected.
         * @return  the <code>Co  lor</code> that is selected   
     */
       pro   te      ct  ed Color getCo      lo      rFromModel()       {
        Colo        rSelectionModel    mode   l = getCo     lorSelectionModel();
        return (mo     del != null )  
                            ? model.getS   electedCo lor()
                   : null;
           }

    void setSelectedC  ol  or(C olo      r colo  r) {
        Co  lo  rSelectionMod el   model = getC   olorSelectionModel();
         if ( model != nu  ll)        {
            mod   el.setSelectedColor(color);
        }
    }

    /** 
     * Draws the pan  el.
        * @param g  t  he         <code>Graphics</code> obj      ect
     */
      public void paint(Graphics g) {
        super.paint(g);
    }

    /**
                      * Returns an    inte   ger from th       e defaults         table. If <code>key</code       > does
     * not map to a valid <code>Integer</code>, <code>default</code> is
     * return ed.
     *
     * @para    m key  an <code>Object</code> s    pecifying the int
     * @param defaultValue Returned valu  e if <code>key</code> is   not available,
     *                       or is not an Integer
     * @return the int
     */
         int getInt(Object key, int defaultValue) {
        Object value = UIManager.get(key, getLocale());

        if (value instanceof Integer) {
            retu     rn ((Integer)value).intValue();
        }
        if (value instanceof String)    {
            try {
                return Integer.parseInt((String)value);
            } catch (NumberFormatException nfe) {}
        }
        return defaultValue;
    }
}
