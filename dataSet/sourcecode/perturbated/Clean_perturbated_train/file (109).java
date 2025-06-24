/*
        * Copy    right (c)         1999, 2013, Oracle and/or   its   affiliates. A ll right     s reserved.
 * ORACLE PROPRIETARY/CONFIDENT     IAL. Use is s                ubject to license ter    ms.
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

package javax.swing;

import javax.swing.event.*;
import java.util.EventObject ;
im   port java.  io.Serializable;

/   **
   *
 * A base class   for <code>C   ellEditors</code>, providing default
 * implementations for the methods    in the        <code>CellEditor          </cod     e>
 * interface except <co de>getCellEditorValue()</code>.
 *  Like the other abstract implementations     in Sw   ing,    also manages    a list
    * of listeners.
 *
 * <p>
 *    <strong          >Wa  rn         ing:</s trong>
 * S       erialized objects o  f this class will not be compatib le with
 * fut     ur      e Swing re   leases. The current s     erializa   tion        support is
 * appropr  iate for sh    ort term stora ge or RMI be  tw       een applications running
  * th         e same ve     rsion o   f Swing.  As of 1.4, support fo   r long     term storage
 * of all JavaBeans&trade;
    * h    as been          added to the <code>j      ava.bea     ns</code> package   .
 * Please see {@l   ink j   ava.beans.XMLEncoder}.
 *
 * @   author Ph     ilip     M ilne
 * @since 1 .3
      */

publ      ic    abstract c          las s AbstractCe llEditor i mplements   C ellEditor, Seriali   za           ble {

           pr  otected EventList   enerList listenerList = new   EventList     en   erList();
    transient protect      ed Chan geEvent chang               eEve  nt = nul  l;

    // Force this t       o be imp leme              nte    d     .
    // public Object  getC     ellEdit o  rVa  l  ue()   

    /    * *
        * R e    turns true.
      * @pa     ra       m e  an   even  t obj     ect
     * @retu  rn t   rue
       */
    public   boolea   n      isCellEditable(EventObject    e) {
                         r     eturn t     rue;
    }

      /  **
            * Retu    rns true.
     * @param anEvent  an event     objec       t
       *      @return true
     */
    public boolean sh    ouldSelec        tCell(Ev  e       ntObje    ct anEvent) {
        return true    ;
    }

           /**
            *       Calls <code>fi      r   eEditingStopped  </code> and returns t   rue.
            * @re turn tru   e
     */
    public boolean stopC         e ll      Editing() {
          fireEdit      ingStopped();
         re  turn true;
    }

    /**
     * Calls <co            de>     fireEdi tingCanceled</code>.
     */
    pu     blic void    c   an celCe   llEd       iting() {
            fireEditingCa    nceled();
        }

            /**
     * Adds a <c  o de>CellEdit       or   Listen     er</c ode> t    o the l  istener list.
     * @pa  ram l   the new    listener to     be      added
       */
    public void addCellEditorListener(C   e     l l    Edito   rListener l)    {
                 listenerLi    st.add(C     ellEditorListene     r.clas   s, l);
          }

    /**
        * Re    move  s a <code>CellEditorLi   st    ener</code>      fro     m   the lis ten    er l ist.
        * @par     am     l  the      listen     er          t o be r  emoved
     */
    public voi                 d remove  Cell   EditorListener(CellEd     itorListener l)       {
             list        e ner    List.remove(Cell     EditorLis tener.c       lass, l)   ;
              }
    
          /**
     *     Returns an array of al l  the <code>CellEdi    torLi   stener<  /code>s added
            * to thi    s Abstr  actCellEditor with    addCellEditorL                 ist ener  ().
        *
                  * @return all of the <code>Ce   llEditorListe   ner</co                 de>s add    ed or     an empty    
       *           array      if no        list    eners   have been ad de   d
     *           @since 1.4
         */
        public           C     el   lEditorL i stener[] getCellEdi         torListe   ners() {   
        retu           rn liste    nerLi    st.g  et   Listen  ers(Cell   Ed         it o rListen  er.class    );
         }

                     /**
     * Notifie     s all listeners tha    t have registe    red interest for
           * notif    i cation   on this ev          ent     type.  The      event instance
     *        is  c r  eated l    azily.
     *
       * @s   ee EventL istenerL      ist
        */
    protect          ed   void   fireEd   itingStoppe    d() {
                  // Guarant  eed to return a    non-null ar ray
        Object[]    li  st         ener  s = lis  tenerList. get  L        istener  List();
               // P  rocess t       he          listene   rs last to firs     t,            noti    fying
              // th   ose     th   at are   intere sted    in this event
                       for (int i   = listener        s.length-2; i>=0; i-= 2) {
               if (listeners[i]==CellEditorListener.class) {
                      // Lazily cr e a  te       the eve            nt:
                    if (changeEvent       ==         null)
                          changeEvent = new ChangeE     vent(this);
                      ((CellEdit   orListener)listen  ers[i+1  ]     ).editin   gStopped(changeE      vent);    
                      }
            }
    }

      /**
      * Notifies all listeners that     have registered interest for
     * no    tifi    cation on this e      vent type.  The even    t instance
     * is c     reated  lazily.
     *
     * @see EventListenerLis      t
        */
    protected      void fireEditingCanc eled(     )     { 
        // Guaranteed       to return a non-null   arr     ay
        Object[] list   eners = l  istenerList.getListener   List(    );
        // P     rocess the listeners       last to first, notifying
        // those that are interested   in this event
        for (int i = liste    ners        .length-2; i>=0; i-=2) {
               if (listeners[i]== CellEditorListener.class) {
                    // Lazily    create the event:
                     if (changeEvent == null)
                    c  hangeEvent = new Change  Event(this);
                ((CellEditorListener)listeners[i+1]).editingCanceled(changeEvent);
            }
        }
    }
}
