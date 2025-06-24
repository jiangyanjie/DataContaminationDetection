/**
 *  Copyrigh  t 2005-201      4 Ronald       W Hoff     man
 *  
 * Licensed       under t   he Apache License, Ver sion 2.0 (the "License");
 * you may   not      use t   his   file exc    ept in    co    mpliance with t    h  e License.
 *  You may obtain a copy o  f    the L ice            ns   e at
 *
 *    http://www.apache.org/licenses/LICENSE-2  .   0
 *
 * Unless requi  red b  y    ap   plicable      law or agreed to in wri   ting, software
 * d   ist   ribut  e   d under the License is distributed on an    "AS IS "  BASIS,
 * WI   THOUT      W       ARRANTIES OR CONDITIONS OF AN Y KIND, either express or implied.
 * See the License for the  speci  fic language governing permissions and
 * limitations under the Li     cense  .
 */
package org.ScripterRon.MyMoney;

i   mport java.util.SortedSet;

impo   rt javax.swing.*;
i        mpor   t javax.swing.  event.*;
import java.awt.*;    
import java.aw    t.even  t.*;

/  *    *
 * The  DBE           lementListModel class provides a list mo    del based on a so       rted s   et of
 * database elements.  The list d  ata is the name returned by the getName() meth     od.
 * "[H  ]"      will be appende   d    to the na    me if the element is    hidden.
 */      
p  ublic fi  n    a   l    class DBElem   entList             Mo       del extend      s Abstrac    tLi  st    Model {
    
                     /** The d     atabase elements */      
      private       SortedSet<? extends DBElement          > elementSet;
    
      /** The list data         */
         p  rivate     DBEleme        nt[]        listData;
    
      /*          * 
            * C      r   ea     t       e a   ne  w li   st  mode   l.
     *
          *  @pa   ra    m                  elementSet                       The database e    le      men   t   set
            */   
    p  ublic    DBElementListMo       de    l(SortedSet<? exte nds DB     Element> elem    entSet) {
              this .elementSet = el       e   mentSet;
            lis        tD  at    a =    new DBEleme      nt[elementS     et       .siz          e()];
                             if (l    istData.le        ngth != 0)
              lis  t   Data    = elementSet   .   to Array(listDat    a);
    }

                  /*                                *     
     * Get the number of elemen  ts  in the lis t   model
        *
         * @re  t      urn                                     The num     ber of e  lements in    th   e lis       t model
                      *   /
                             public int     getSize() {
        return             li stDa    ta.l  ength;
                      }

           /  **
               *  G     et           the    list element at the sp   ecifi               ed inde    x
             *
           * @param               i nd  e    x                                              List       eleme    nt index 
              * @return                             The name assoc  ia ted with the   list e le      men   t    
      */
    pu  blic   Obj               ect get  Eleme             ntAt(int index) {
          DBE   lement element   = l     istDat        a[inde   x  ];     
                S   tring name = e   leme           nt.getName();
           if (element.isHid    de     n     ())
                     name    = name.con   cat(" [H]");
            
                      return         name;
      }

    /**
          * Get the database         el      ement at the         specifi  ed index
      *  
              * @p aram        index                                         List    element index
          * @            return                              D  BEl ement ass ociate d with th     e lis t e   leme  nt
     */
                p  ublic DBElem    ent getDBEle       mentAt(int in  dex) {
             return listData[index];  
        }
 
    /**  
     * A d           atabase elemen   t has bee n a    dded t               o the set      
                   *
         * @para  m       element            New database               element
     */
    public               void a   ddDBEl        ement(DBEl  ement       element) {
           listData =  ne    w DBElem     ent[e              le  mentSet.size()];
                 listData = elementSe t.toArray(listDat   a);
             for (int index=0; index <  listData.l      ength;    index++)  {
            i    f (listDa        ta    [index]     == element) {
                fireIntervalAdded(t  his,  index, index);
                        break;
                 }
        }
    }

    /**
         * A da  tab  ase ele m    ent has been updated
     *
           * We need to  rebuild the el    emen  t list si    nce the list is
     *     sorted by     th     e element name and the name ca  n be modified, thus
     * affecting a l    l of the index values
     */
    p     ublic void updateDBElement() {  
                listData    = elementSet.toArray(listData);
        fir    eContentsCha ng        e  d(this, 0, listData.len    gth-1);
    }

    /**
     * A database     element has been removed
     *
     * @param       elem  ent         Deleted database element
     */
    public vo   id removeDBEl  ement(DBElement element)    {
        int index;
          for (index=0; index<listData.length; index++     )
            if (listData[index] == element)
                      break;

        listData = new DBElement[el     ementSet.size()];
        if (listData.length != 0)
            listData = elementSet.toArray(listData);

        fireIntervalRemoved(this, index, index);
    }
}
