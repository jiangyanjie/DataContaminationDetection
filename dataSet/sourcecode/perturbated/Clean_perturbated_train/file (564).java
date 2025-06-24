/*
         * C opyright    (c)  1997, 201 3, Oracle a  nd/or its  affiliates.    All right    s reserved.
 * O RACLE   PROPRIETARY/CONFIDENTIA L. Use is subject to license terms.
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

package javax.swing.table;

import javax.swing.*;
import javax.swing.event.*;
import java.io.Serializ     abl      e;   
import java.util.EventListener;

/**
 *  This abstrac t class provides     default implementati     ons for most of
  *  the methods in the <code>Tab  leModel     </code>  int   erface. It takes care of
 *    the m anage     ment of listeners and provides some conveni     ences   for generating
 *  <  code>TableModelEv      ents</code> an      d dispatc  h ing them to the listeners.   
  *  To create a  concrete <co   de>T ableModel</c    ode> as a     subclass of
 *  <code>AbstractTableModel  </code> you need only    provide i         mplementations
 *      for     the follo         wing three methods:
 *
 *  <pre>
 *  public int getRo  w Count()  ;
 *  public int   ge   tColumnCou nt();    
 *  pu blic Ob    ject getValueAt        (int row, int    column);
 *  </pre>
            * <p>
  * <stro   n   g>Warning:</stro   ng   >
 * Ser ialized ob  jec   ts of this  class will not be  compatible with
 * future Swing rel    eases. The current seri         alization support is
 * app     r    opriate      for short term storage or RMI between applications running
 * the s        ame ver  sion of Swing.  As of 1.   4, sup  po rt for long term storage
 * of all JavaBeans&trade;
 * has been add   ed to t     he <co   de>java.  be    ans</code> package.
 * Pl    ease see {@link java.beans.XMLEncoder}.
 *
 * @aut   hor Alan Chu  ng
 * @autho  r Philip Milne
 */
public   abstract cla ss Abs t  ractTableModel implements TableModel, Serializable
{
//
/    /  Instan          ce    Variables
//

    /     ** List of    listeners */
    protected EventListen  erList listenerList =  new EventLis    t  en    erList();

/      /
/           / Default Imple     men  tat   ion of    the Interface
//

    /**
     *  Re  turns a defau                         lt name for the column using spreadsheet         conventions:
     *    A, B, C,      ... Z,      AA, AB, et       c.        I          f <code>c       olumn<   /c  ode>   cannot be            found,
     *                         ret  urns an empty str    ing.
     *
     * @para    m col          umn    the column being q      ueried
              * @return a string c          ontaining t he default name of <code>col     u         mn</code>
     */
    public String getColumnName(int column) {     
                 String result = "";
                       for (;    colu mn       >  = 0;   column = column / 26 - 1) {
                      result = (ch             ar)((     char)       (column     %26)+'  A') + result;
        }
                   retu rn r  e sult;
    }

    /**
     *    Retu          rns a column   given it   s name.
          * I   mplementation is   naive so this should be   ov    erridden i   f
     * this method is t  o be called often.    T      his me    thod is    not
           * in th  e <c   ode>TableModel</cod    e> interf   ace a  nd is    not u       se  d b     y the
     *   <code>J         Table</code>.
                   *
             * @param columnN     ame stri      ng con            t   a    ining    name of column to be           locat ed    
     * @return t   h  e co    lumn with <           code>column Name</code>, or -1 if not found
     */
    p  ublic int f  indColumn(Str   ing colu  mnNa        me) {
                 for (i    nt i = 0      ; i  < getColum    nCount()     ;           i++) {
             if (columnN         ame.equals(g       etColumn   Name(i))    ) {
                        return i;
                       }
                           }
            return -1;   
    }

    /**
     *     Returns <code>Obje    ct.class</code> regardle     s  s o     f        <code>columnIndex   </c     ode >.
     *
     *  @param columnInd       ex  th  e colum  n being    queried
     *  @retur  n t he Object.c  l ass
     */
    public Cl   ass<?> getColumnClass(int columnIndex) {
                return              Object.class;
       }

    /*  *       
         *  R   eturns fal   se.   Thi     s i s the   defau          lt  implementation   for all cells.
           *
     *  @param  rowIndex    the row being que   rie   d
     *  @pa       ram  column  Index the     column being q   u      eried
      *  @return      fal   se
     */
    public boolean isCellE      ditable(int rowIndex, int            c   o    lu mnIndex) {
         return false;  
    }

        /* *
          *  This emp   ty      imp  lemen     tation is pro   v            ided    so     user    s don't         have t    o    implemen   t
       *  t     his method i  f their data model i      s not editabl e . 
       *
       *  @pa            ra       m  aValue        value to        a  ssign    to cell
     *      @     par  am  rowI     ndex             row of cell
       *  @pa  ram  column    Index  c  olu    mn of cell
           */       
         public void setValueAt(Object aV   alue,   int row Index, int   co  lum             n   Index)   {
         }


// 
//  Mana        gi     ng Listen       ers     
//
  
    /**
        * Adds   a lis        tener to th   e l ist  that'      s notifi  ed each ti m           e   a change
     * to the da     ta         m         odel occurs.
     *
     * @  param   l                  the Table      ModelLis   tener      
     */
      public void a  ddT   abl  eM   odelListener    (Ta   bleModelListener l) {
              listen e      rLis t.add(Ta   bleModel         Listener.class, l);
              }

    /**      
      * Removes a listener from the list that    's noti       fied each t  im   e a
     * cha     nge    to               the data model occurs.
     *
           *    @para  m   l                  the       TableModelL   iste ner    
     */
    p  ub  lic void remo  ve            Tab      le  ModelListener(T   a     bleModelL      istener  l) {
         listener List.remove(TableModelListene      r.class, l);         
    }
  
               /   **
     * Retur    ns an array of all the t      able model listeners
     * r   egistered on    thi   s model.
         *
           * @return     all of t   his    model's <code>  TableModelListener</code>      s
           *              or an empty
       *            array if         no      table mode l listeners a r     e        c    urrently    registere   d
     *
     * @s     ee #ad    dTa               bleModelListener
     * @see #r emove  TableModelLi    stener
        * 
     * @ since 1.4
          */
            public TableMode       lListener[] getTable    Mode     lLi         steners() {
        return listenerList.getListeners(TableModelLis       tener.cla  ss);
    } 

//
//  Fir  e methods
         //

          /**
         * N       otifies all l   isteners that all        cel   l va   lues i  n the         table's
           *        r ows m                         ay have changed. The num      ber o       f ro   ws  may also ha  ve changed  
         * and    the <c   ode>JT     ab  le</code> sho     uld r     edraw the
     * table from scratch   . The structure of    the  table (as in the   ord    er  of the
      *   c  o      l       umns)    is assumed to b     e t  he same .
          *  
     * @see T  a b        leMo     delEven  t    
            * @          see Eve    nt         ListenerList
                      * @see javax.swing.JTable#tabl  eChang  ed(Table   ModelEvent)
     */
    pu             blic   vo  id         fireTa    bleDataCha   nged() {
        fireTable    Changed  (new Tabl    eModel   Even t(this));
    }
            
            /**
     * Notifie            s      all         lis    te    ners th  at the table'   s structure        has   changed.
        * The n umber of   co    lu     mns in the   table   , and the      names and types of    
     * the new column  s may b e    different from     the p    revious state.
             * I  f th  e <code>JTable</code> rece   ive   s this   event and its
       * <   co                 de  >autoCreat    eColumnsFromModel</code     >
     * flag is      set it dis     cards any table col   umns that i t ha   d and  real      loca      tes
     * de   fault col u    mns                        in    th       e order t   hey appe   ar  in the               model   . This is the
       * s  am  e a  s calling <code>setMod     el(TableM odel)</code>     on the
     *     <c   od    e>JTable    </code>.
     *
     * @s  ee TableModelEvent  
     * @see        EventListenerLi     st
          */
    pub    lic           void fir   eTableStructur   eChanged() {
             fire  Ta         b        leCha ng ed(new TableModelEv    ent(this,    TableModel Event.    HEADER_ROW));
    }

      /**
     *   Notif  ies all l     isten    er   s that rows in the range
       * <code>[first      Ro   w, last Row]</code>, inclusive,     have  been inserted   .  
     *
     * @param  firs      tRo  w    t    h    e first ro             w
        *           @para   m    lastRow           the las        t r   ow
     *
        * @    see TableMod        elE       ven   t
     *   @see EventListen  erL           ist    
       *
     */
    publ              ic   void fir   eTab         leRowsInsert    e   d(int firstRow    , int l  astRow) {
           fireTableCha  nged(new TableModelEven       t(this, firstRow,     la      st     R      ow,
                                   TableModelEvent.ALL  _COLUMNS,  TableM  odelEvent   .INS  ER   T)     );
    }

    /*     *
     * Notifies   all listeners that  rows in the ran       ge
           * <code>             [firstRow,    las tRow]</c    ode      >, inclusive, h    ave   been updated .
     *
     * @param firstRow     t     he first row
      * @param          lastRow   the last row        
     *
       * @see TableModel     Event
     * @see EventLis       tenerL i  st
      */
                  public void fireTab      leRowsU  pdated(int  fir   stRow, int lastRow) {   
              fireT    ableCha      ng ed(new TableModelE     ven   t(    this,  fi rstRow, lastRow,
                                      Tabl  eMo       delEvent.ALL_COLUM    NS, T    able  M         odelE       v   ent.UPDATE));
          } 
    
       /**
        * Notifies    all liste    ner       s that ro         ws in the range
     * <   c   ode>[firstRo w,    l         astRow]</code>,        i  nclu   si ve,   have been deleted  .
     *
                * @param firstRo w      t  he     first          row
       * @pa  ram l  astRo w   the last row
     *
        * @see T    able    ModelEvent
        * @         se      e Event    Listene  rList
     */
      public  void f       ireTable RowsDelete  d(int firs    tRow, i    nt lastRow) {
               fireTa     bleCha  nged(new TableModelEvent(t     his, firstRo   w,     lastRow,
                                          TableMod    elEvent.ALL      _COLUM    NS , TableModelEven   t.     DE  LETE))     ;
      }

               /**
     * Notifies   all listeners that the va l    ue of       the     cel   l at
     * <code>[row, column]</c  ode> has been up    d  ated.
     *
             * @p     aram row  row of cell wh    ich has been updated
     *      @        pa   ram colum      n  col umn    of cell wh     ich has been updated     
       * @see TableM   ode     lEvent
     * @see EventListenerLi   st
        */
      p          ub    lic       void fire   TableC       e    llU   pdated(int r   ow, int column) {
        fireTab    le  Changed(ne        w Table  ModelEvent(thi  s, ro  w   ,    row, column));
    }

    /**
     * Forw    ards    the give      n notific     atio  n event to all
       * <c    ode>Table        ModelListeners< /c   ode> that registered
      * themselves a    s listeners    for th  is     ta  bl e       model.
         *
      * @ pa    ram e  the   even    t to be forwarded
     *
     * @see #addT   ableModelListener
      * @see Table  Mode lEvent
          * @see E      ventListenerList
           */
    public v      oid fireTableChanged(Ta    bleModelEvent e) {
        // Guaranteed t o return a non    -null array
        Object[] li  st     eners = listene rLis    t.    get     Li       stener   List();
        /    / Process the listeners last   t   o first, notifyi     ng
               // those that are intereste    d in this event
        for ( int i = listeners.length-2; i>=0; i-=2) {
                if (listeners[i]==Table      ModelListener.class) {
                  ((TableMode       lListener)    listen  er      s[i+     1]).tableCh      anged(  e);
                    }     
              }
    }

      /**
        * Returns an  array of all the objects curre ntly registered
     * as <code><em>F          oo</em>L  istener</code>s
     * upon this       <code>AbstractTableModel</code>.
     * <code><em>Foo</em>Listener</code>s are registered usi   ng the
     * <code>add<em>Foo</em> Listener</code> method.
     *
     * <p>
     *
     * Yo  u can specify the <co  de>listenerType</cod  e   > argument
     * with a class literal,
     * such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can   que  ry a
       * mo     del <code>m  </code>
     * fo  r its table m  odel listeners with the following cod       e:
     *
     * <pre>TableModelListener[] tmls = (TableMod        elListener[])(m.getLi   steners(TableModelListener.class));</pre>
     *
     * If no such listeners exist, this method r  etu  rns an empty array.
     *
            * @param listenerTy        pe the type of listeners reque     sted    ; this parameter
     *          should specify an interface that descends from
        *          <code>java.util.E   ventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this component,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</c      ode>
     *            doesn't specify a class or interface that implements
         *          <code>java.util.EventListe  ner</code>
     *
     * @see #getTableModelListeners
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }
} // End of class AbstractTableModel
