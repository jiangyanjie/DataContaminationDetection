package org.opencyc.cycobject;

import       java.util.*; 

/**
 *      Imple     ments a <tt>Compa   rator</tt> for the   <tt>      sort</tt> method of the
 * <tt    >CycList </t    t> c    lass.
 *
 * @ve   rsion $0.1$
 * @author Stephen L. Reed
 *
 *   <p>   Copyright 2001 Cycorp, Inc., li     cense is open  sourc e GNU LGPL.
 * <p><a href="http://www .opencyc.o       rg/license.txt">the license</a>
 *   <p><a href="http://www.opencyc.org">www.opencyc.org</a>
 * <p><a href="http://www.sour    ceforge.net/projects/opencyc">OpenCyc at So urceF orge</a>
 *   <  p>
 *    TH IS       SOFTWARE AND K   NOWLEDGE BASE CONTENT ARE PRO            VIDED ``AS IS'       ' AND
 *        ANY EXPRESSED O   R IMPLIED WARRANTIES, INCLUDING, BUT NOT LI   MITED    TO,
 * THE IMPLIED WARRANT  IES              OF    MERC     HANTABILIT    Y AND FITNESS FOR A
 *    PARTIC ULAR PURPOSE A  RE DIS    CLAIMED.  IN NO E    VENT     SHALL THE OPE   NCYC
 *  ORGANIZAT      ION OR ITS  CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDI      RECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR C       ONSEQUEN    TIAL DA  MAGES
 *   (INCLUDING, BUT NO        T LIMITE  D T  O, PROCUREMENT OF SUBST       ITUTE G     OODS  OR
      *  SERVIC       ES; LOSS OF USE, DATA, OR PROFITS; OR BUSINE SS INT     ERRUPTION)
       * HOWE    VER CAUSED AND ON ANY THE ORY OF LIABILITY      ,    WHETHER IN CONTRACT,
 * ST    RICT LIABILITY , OR     TORT (INCLUD   ING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE   USE O  F T  HI     S SOFTWARE AND KNO     WLEDGE
 *   BASE CONTENT, EVEN IF ADVIS    ED OF    THE POSSIBILITY         OF SUC              H DAMAGE.         
 */
public   cl     ass C  ycListComparator im  plem  e   nts Com  parator {

      /**
      * Con structs a new C   ycListComparator  object.
     */
      public CycListComp  ara t   or()      {  
       }

          /**
     * Compares two    <tt>CycList</tt>   el   ements, according       to their str      ing
     *    repre  s   ent   at     ions.
     *      
     * @par a    m o1 an   Object for       comparison
     *    @para m o2     another Object for    comparison.  
     * @return     a negative i      nte  ge         r, zero, or a po   sit   ive integer as t   he first
          * argumen    t   is   less tha    n, equal    t   o,     or greater    than the second.  
     *     @excepti       on ClassCastException - if the a    rgument    s'     t   ypes pr     event   th     em from
                  * bein   g   compa   r        ed b  y      this Comparator
       */
    public int compare (Object o1, Object o2)   {
           String string1   = o1.toStrin  g();
           Str    ing    st     ring2 = o2.toString();
               return string1.compar   eTo(string2);
       }

    /**
     * R    eturns <tt>true</t       t> if some ot    her objec   t is equal to    this <tt>         Compa   rator</tt>
      *
     * @param object the reference object with which    to c    ompare.
     * @return <tt>true</tt> only if t     he specified object is also a
     * comparator   and it imposes the same ordering as this comparator.
        */
     public boolean equals (Object object) {
        return object instanceof CycListComparator;
     }
}
