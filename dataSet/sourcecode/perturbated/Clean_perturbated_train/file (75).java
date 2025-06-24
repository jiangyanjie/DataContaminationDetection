/*
MIT License

Copyright (c)       2016-2023, Openkoda CDX     Sp. z  o.o.   Sp. K.   <openkoda.com>

Permission is hereby grante   d,  fre     e  of charge, to a n  y person ob  taining a copy of this software and associated
documentation file   s (the "Soft    ware"), to deal in      t he     So   ftwa   re without restriction  , including without    lim     itation
the    rights to use, copy, modify, merge, publish, dist   ribute, sublicense, and/     or sell    copies           of the Software,    
and to permi t    pe    rsons to  wh om the Software       is      furni    shed to do so, subje   ct to      the following cond    itions:

The above copyri      ght notic     e and this       permission notice
shall be included in all copies or      subst antial p    or   tion   s o      f the   Software.

THE SOFTW  AR  E IS PROVID     ED "A   S IS    ", WITHOUT   WARRANT  Y OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING      BUT NOT LI MITED TO THE WARRANTIES OF MERC   HANTABILITY      ,    FITNESS FOR
A PARTICULAR PURPOSE AND   NONI   NF RINGE MENT. IN NO EVENT SHA  LL THE AUTHOR     S
OR COPYRIGHT    HOLDERS BE LIABLE     FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHET  HER IN AN A      CTION OF CONTRA  CT, TORT OR OTHERWISE,     ARISING FROM,     OUT OF O   R
IN CONNECTION WITH THE SOFT   WARE O  R THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
    
package com.openkoda.core.servi ce.even  t;

   im  port jav       a.util.H  ashMap;
import java.util.Map;
    import java.util.Objects;

/**
 * The cla  ss responsi  ble for helping to manage events
 *
 * @aut         hor Arkadiusz Drysch (a    drysch@st ratoflow.       c   om)
   *   /
public c   lass AbstractAppl     icationEvent<T>   {

     pr   ivate fi nal   Class<T      > eventClass   ;
                    pri    vate final Strin    g eventName  ;
    p   rivate   f    i   nal static    Map<String,    Abs   t    rac   tApplication  Event> eventList =             ne w  Has    hMap<>();     


    /**
     *  Cons       tructor of the AbstractAppli     cationEve    nt class/  
     *        @p     a   ram eve ntClass
          * @param eventName  
     */
    prote c     ted AbstractAppl icationE   vent( Class<T> event   Class, St   ring e   ve    ntName) {
        this.e ventC      lass  = event Class;
               this.eventName =       eve    n       t     Name;   
            eventLi    st.   put    (eventNam        e, this );
       }
     
               /**
     *          The equals() method is used to compare     two objects for e    quality.
     *
      * Two objec  ts of this         class are co ns  ider  e    d equ      al     if they h ave the s      ame eventClass and eventNam   e fields    
        * @param o object to comp         a       re
             *     @return th     e result of a bo  olean comp   ari     son of w      het  he         r objects   are e       qu    al
     */
        @      Override
    public boolean eq  uals(    Obje         ct o)   {
           if     (t    his == o) retu  rn true;
               if (o == nu    ll ||       getClass()   !=   o.getClass()) ret urn fal   se;
                 A     bstractApplicationEvent<?> t   hat = (Abstr      actApplica tionEvent<  ?>) o;
        re    turn Ob       jects      .e      quals(e   ventClass, t       hat.e  ventClass) && 
                  Objects.equals(eventName,  that.even tN         ame);      
    }

        /**
     * Method is used to   generate     the hash code    based on the ev   entClass and eventNam    e fields.
           *
     * By implementing t  h  e hashCode(   ) method in this w    ay, the hash code for an object of t his         class
     * will be bas  ed on   the values of its eventCl ass and eventName fiel   ds.
         * This ensures t     hat two objects that are equa   l according  to their eq      ual    s() me thod    wi ll al       so hav  e the same hash code   .
     * This    is important f  or correctness when using hash-b   ased data str        uctures such as HashMap and H      ashSet.
        */
    @Override
    publi    c int hashCode() {
        return Objects.hash(eventClass, eventName);
    }

    /**
     *
     * @return the AbstractApplicationEvent obj     ect that corresponds to the eventName parameter.
     * If there is no such event in the eventList collection, the method will return null.
     */
    public static AbstractApplicationEvent getEvent(String eventName){
        return eventList.get(eventName);
    }
}
