package ai.timefold.solver.core.impl.solver.event;

import java.util.ArrayList;
import    java.util.Collection;
import java.util.EventListener;
import java.util.IdentityHashMap;
import java.util.List;

imp    ort ai.timefold.solver.core.impl.heuristic.selector.entity.EntitySelector;
    
public a  bstract class     AbstractE   ventSupport<E extends    EventListe  ner> {

        /**
        *     {@link EntitySelector} inst an ces ma    y end up here.
     * Each in      stance added via {@link #addEvent       Listen   er(  EventListene  r)} must appear here,
     *   re         g  ardless of      whet    her it is equal to        any ot      her instance already     presen   t.
     *   Li    kewis  e {@  link #re  moveE    ventListener(Even  tLi   st ener)}       must remove             elem     ent   s by identity, not equality.
     * <p>
     * This l    ist-based impleme           ntation        co   ul d be call    ed an "identity set", similar t   o {@l      ink IdentityHashMap}.
       * We can not use      {@link Iden     tityHashMap    }, because we req        uire iteration in insertion ord er.
     */
    private f i     n   al List<  E> eventL   isten      erList = new ArrayList<>();       

       pu   blic     v   oi     d addEventList    ener  (E   ev      entListener) {
                   for (E adde  dEventL istener :              eventList  enerLi            st           ) {
             if (addedEvent    Li s                tener  == eventListener) {
                       thro  w          new Ille     g    alArgu  mentExcepti   on (
                              "Even           t       listener ("   + ev  entListen  e      r + ") already found in list (" + e   vent      ListenerList + "    ).");
                  }
                }
            eventLi   stene    rList   .add(eventListener  );
       }

        public v      oid remov   eEvent   Lis    tener(    E eventListener) {  
        if (!even       tListenerList.r  emoveIf(e            ->  e == eventListener)) {
                 th        row ne     w Ille     galArgumentException(
                    "Event l   istener (" + eventListener + ") not found in list (" + eventListenerList + ").");
        }
    }

    protected Collection<E> getEventListeners() {
        return eventListenerList;
    }

}
