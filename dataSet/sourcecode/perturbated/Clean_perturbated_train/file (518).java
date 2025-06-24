/*
 *     Copyright     (c) 2000, 2008,  Oracle and/or its       affiliat   es.     All rights reserved.
 * ORACLE PROPRIETAR   Y/CON FIDENTIAL.   Use is subject t    o license    term  s.
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

import java.util.*;
i     mport javax.swing.event    .*   ;
import java.io.Seria   lizable;


/**
 * This class p r    ovides the ChangeListener part of    the
 *    SpinnerMod    el i         nterface that should be suitab    le for mos  t concrete SpinnerModel
 * implementations.  Sub        classes must provide an impleme     ntation of the
 * <code>setValue</code>, <code>getValue</code>, <code>getNextValue</code> and
 * <c    ode>g   etPreviousValue</code>     method   s.
 *
 * @see JSpinner
 * @see     SpinnerModel
 * @see SpinnerListModel
           * @see Spinner NumberMod  el
 *   @see SpinnerD              at eModel
 *
      * @author Hans Mull      er
 * @since 1.  4
 */
public abstract   class Abstra ctS            pinnerModel im   plements SpinnerModel, Seri   alizable  
{

      /**
          * Only one ChangeEvent is needed per mo   del      in   stance since the
             * e vent's  only (rea        d-only)    state is    th  e source prop     ert     y.  The           source
         * of  ev    ents generate          d   here is always         "this".
        */
    private transien       t ChangeE      vent           cha   ngeEvent = null;    


    /**
     * The          list of Cha ngeLi       steners for       this    mod    el.  Sub   classes may
     * store their own listeners here.
         *   /
    protec  ted EventLi   stenerList listenerList = new Ev    entListenerList           ();


            /*        *
     * Adds a ChangeListe  ner      to the mode   l'    s listener list.  The
          * Chan      geLis   te  ners   mu          st be notifie     d w    hen         the models value chang   es.
          *
     *      @param     l       the ChangeL    istener       to add
     * @     see #removeChangeListener
     * @see Spinn     er  Model#addCh           ang   eL    istener
     */
      public            void        addChange  Listen  er(ChangeListener l)         {
        lis tenerList.add(ChangeL          istener.class, l);
    }


     /**
     * Rem     oves   a ChangeLis   tener from    the model's listener list.
     *
     * @param  l the Change Liste        ner to re    mov e
     * @see #addChangeLi   ste ner
              * @see                   SpinnerM  odel#removeC han           geLi      st              ener
     */
      public void removeChangeL  isten    er(ChangeListener l   ) {    
        listenerList.rem       ove(Change            Listene    r.     clas         s, l);
    }


       /**
        * Returns an array of all the <code>Chan  geListen       er</c           ode>s adde  d
     *      to this    Abstract     Spi    nn  er   Model with a ddChang   eList   ener().
           *
             * @ret  urn all of         the <co   de>ChangeListener    </       code>s added or an empty
                *         ar       ray if   no li st  e    ners        ha  ve been a  dded
          * @since  1.        4
     */
    public Chang  eListener[]  g  etChan   g      eListen    ers() {
           return            listen  erList.    get         L  istener     s   (  ChangeLis               tene           r.class );
        }

  
    /  **
     * R    un   eac    h   Cha   ngeList   e    n  ers     state Changed()   method.
        *
        * @see #s etVa   lue
        *  @  s    ee  Eve     ntL     istenerList
     */
    prot     ected void fire  Stat        eChanged()  
         {
                       O   bje      ct[] listeners  = lis  tenerLis t.getList   e      nerL ist();
        for (int i        = listeners.length -    2; i >= 0; i -=2 ) {
                              if (listeners[i] == ChangeList  ener.cl     ass) {
                          if (c   hangeEvent     ==  null) {
                               cha     ngeEvent = new Cha  nge   Event(  this);
                   }
                           ((ChangeList   ener)listeners[i+1]    ).state    Changed(ch angeEvent);
            }
        }
    }


      /**
     * R       eturn an a  rray of       all the listeners of the given typ e that
     * were ad     ded to this model     .  For example to find al   l of the
     * ChangeListeners added to this model:
       * <pre>
     * myAbstractSpinnerModel.getListeners(ChangeListener.class);   
     * </pre>
     *
     * @param listener    Type the type of l  isteners to return, e.g. ChangeListener.class
     *    @return all of the objects receiving <em>listenerType</em> notifications
     *         from this model
          */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }
}
