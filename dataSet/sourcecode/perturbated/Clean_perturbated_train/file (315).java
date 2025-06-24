/*
 *      Copy  right (c)   1997, 2013, Oracle    and/or its a ffiliates.   All rights rese      rved.
 * ORACLE PROP    RIETARY/CONFIDENTIAL. Use is subj   ect to     license terms.
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

package javax.  swing;

import javax. swing.event.*;
import java.io.Serializable;
imp   ort java.ut  il.Eve    ntListener  ;
       
   /**
 * The abstract definiti    on for the data mod         el t   hat provides
 * a <code>List</code> wit h its contents.
 * <p>
 * <strong> Warning:</strong>
 * Serialized objects                                    of this clas       s will not be com    patible with
 * fut    ure Swing releases. Th  e cur          re nt serializatio  n support is
 *   appropriate for short t erm     storage or R  MI between a pplicati     ons running
 * the    same version of Swing.  As o  f 1.4,    support for long       term storage
 * of al l J     avaBeans&trad   e;  
 * has      bee   n added to the <code>java.beans</code> package.
 * Plea     se see {@lin  k ja   va.beans.XMLEncoder}.
 *
 * @param <E>    the t ype of the               elements of t     his model
 *
 * @author Hans    Muller
 */  
publi     c abstract class A   bstractListModel<E> impl     em        ents ListMode  l<E>, Serializable
{
    prote             cted E      ven tLi    stene      rList l  istenerList = ne     w Eve  ntListener        List();


    /    **
     * Adds a        listener to t     he li  st that's  notified each time a c   hange
      * to the data mode    l occurs.
         *
     *   @param l the <code>ListData       Listen      er</c  ode> to be added
         */
         publ  ic void addListDat       aListener(ListD  ataLi   stener      l) {
        listener L  ist.a   dd(Li  stData       L     istener.class, l);
            }


      /**
     *   Rem   oves a list     ene   r fro                   m the list  that'         s notif    ied               each t ime   a  
     * change to t  h   e data model occurs   .
        *
     *             @    param l the <code>ListDataListen    er</cod    e   > t  o be removed
         *            /
    publ              ic  void removeListDataListener(ListDataListe  n     er l) {
            lis   tenerList   .rem ove(ListDataListener.    class,   l);
     }


         /**
          * Returns an a     rray of all the list data listeners
     * regis  tered on      this <code  >  AbstractListM odel     <  /co  de>.   
     *
          * @return a   ll of t  his model's <code>ListDataL   istene    r</code>s,
       *         or    an empt    y array if no lis  t data liste  ners
     *          are cur   ren    tly registered   
       *
     * @see #addListData List   ener
            * @se    e #  r        emoveListDataListe    ner
     *
          * @since       1.4
             *  /    
    public     ListData  Listener[] getLis tDat     a             Listeners() {
        r                eturn lis  te     nerList.   g   etListeners(ListDataListener.c   lass);
    }


                     /      **
     * <code>Abst  ractListModel</code> s  ubclasses     must ca ll this    m  ethod
     *    <b>a       fter</b >         
     * one or  m     o    re     e  lements     of the lis         t c    han      ge.    T he changed elem  ents  
     * are    specif     ie  d by the       closed       interv          al index0      , index1 -- the e    ndpoints
     * are in    clud    e   d.     N   ote that      
         * i     ndex0      need    not be     less than or equal to   inde  x1 .
          *
           * @param sour ce th  e <c   o    de>ListM     odel</co       de>     that changed, ty    pically "this"
        * @p             aram            index0  one    end of the new int     erval  
     * @param    inde  x1 the ot    her e   nd    of the new i     nterva      l   
             * @see EventListenerList
           * @see   Def   aultList    Model
       */
                pr   ote      cted void fireC   ontentsCha     ng      ed(Objec t       so     urce,         int index0,  int index1    )
    {
                Object[] list   e   ners = listenerList.  getListenerList() ;   
                 ListDa  t  aEven  t e = nu ll;   

                for (int i = l isteners.length - 2; i     >= 0     ;  i -= 2)  {
                  if    (listeners[i] == Lis  tDataListe    ne r.c    lass)   {
                                                       if (e == nul l) {
                                      e = new    ListD        a         taEvent(s      ource,       L  istDa   taEvent.CONTENTS_CHANGE      D   , index0,   i  ndex1);
                                     }
                    ((List          DataListener)  listen   ers[i+1]).conten    t    sChanged(e);       
                }
                   }
    }

     
       /**
                 * <    cod      e>Ab   st      ractList       Model</cod          e>     sub  classes must   call this method
     * <b>af      ter</b>
           * one or mor  e elements a  re added     t  o the model.  The ne  w ele    m e   nts
     * ar    e specifie    d by a close  d i   nte    rval in    d    ex0, index1 --                 the enp  o  int  s       
     * are included.  No    te   that
               * i  n    dex0                  need not    be less than or equal to    index1.
         *
        *      @  par  am source t  he <code   >ListModel</code>    th        at changed, typicall  y "t    his   "
     * @par am           ind   e x0 one en   d of   the     ne   w inte      rval
     * @param index1 the other              end of the new interval
     * @see Eve   n tListenerList
                       * @se    e De  fault  ListMo de  l
               *               /
       protecte d void f   ireInterval    Added        (Object sourc    e    , int ind           ex0  ,    int ind        ex1)   
           {
            Obj         ect[] lis  te ners   = lis    tenerLi      st.   getListenerList()      ;
            ListDat        aEvent e =  null;

                                         for (int i =      li   steners.leng  th - 2;   i >= 0;     i -= 2) {
                if (listeners[i] == ListDataLi      stener.c          lass) {       
                      if (e ==       null) {  
                              e =  new List DataEvent(source    , Lis   tDataEve    nt.INTERV   AL_ADDED, inde   x0, index   1);
                     }  
                       ((Lis tDataLis     tener)li     st    eners[i+1    ])  .intervalAdded(e);   
              }
                    }
       }


    /**
         * <code>AbstractListMode  l</code> subclasse    s must ca ll this method      
     * <b>a           fter</b>  one or more el   ements are    r      emoved  from the   mod   el.
           * <code>ind   ex0</c    ode> and <code>i  n   d          ex1      </   c     ode     > are the       end points
                  * of t    he    i  nterva   l that's         been removed.  Note        that <code>i     ndex0</code      >   
     *         nee      d not be le  ss than        or e   qua   l to <code>index1   </code>.
     *
     * @param source the <cod    e>ListM odel</code>  that ch   anged   ,     typically "this"
     * @param  in        dex   0 one end of th  e removed inter  val,
             *                    including      <code>index0</code>
                   *      @param i      ndex1 the othe      r end of the       removed in     terval,
      *                           inc         l    uding <co      de>inde   x1</c       od      e>
     * @see EventL   ist   enerLis  t
     * @see DefaultLi         stModel
     */
    protected void fireInter   valR          emo    ve        d           (Ob   ject source,   int index0     , int index1)
    {
             Object[] listeners = listenerLi     st.getListenerList();
        ListDataEven   t e = null;

               for   (i    nt i    = listeners.lengt       h         - 2; i >=          0; i -=        2) {
            if (listen  ers[i] ==   ListD  ataListener.class) {
                                if (e == null)     {
                            e =       new L       istDataEvent(source, Lis    tDataEvent.  INTERVAL_REMO     VED, index0, inde x1);
                }
                   ((ListDataListener)listeners[i   + 1]).i    ntervalRemo   ved(e);
                     }
                  }
       }    

    /**
     *    Returns an array of all the objects curren  tly regis     tere  d as
     * <code><em>Foo</em>Listen  er</c          ode>s   
     * upon   thi   s mo     del.
     * <code><em>Foo</em>Listener<    /  code>s
       * are registered using the <code>add<em>Foo  </em>Listener</code> method    .
           * <p>
     *    You         can specif    y the <code>listenerType  </code> argument    
     * with a class l   itera  l, such as <code><em>Foo<  /em>Listener.cla     ss</code>.  
     * For example, you can quer      y a list model
     * <code>m   </code>
     * for its list data listeners
     *    with the following      co   de    :
     *
     * <pre>ListDataList ener[] ldls       = (ListDataListener[     ])(m.getListeners(ListDat aListener.class));</pr     e>
     *
     * If no such listeners exis  t,
     * this method returns an empty array.
     *
         * @param listenerType        the typ   e of listeners requested;
     *          this paramet   er should specify an   interface
     *          that descends from <code>java.u til.EventListener</code>
     * @return an array of all objects registered   as
     *          <code><em>Foo</em>Listener</code>s
     *          on this mode  l,
     *          or    an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code>  doesn't
     *          specify a class or interface that im   plements
     *                    <code>java.util.EventListener</code>
     *
     * @see #getListDataListeners
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }
}
