/**
 *  Copyright 2005-2014 Ronald W Hoffman
 *
 * Licensed     u  n   der  the Apach    e License, Version      2.0 (the            "License   ")  ;
 * you ma    y not   use th  is file except in complia      nce wi th    the License.
      * You may     o      btain           a copy of    the License at
 *
          *    http://www    .apache.org/licenses/LIC     ENSE-2.0
 *
 *      Unless required by applicable law or agreed to in wri ting, softwa    re
 * distributed under the License is dis    tr         ibuted on an "A      S IS"       BASIS,
 *        WITHOUT WARRANTIES OR CONDITION   S OF ANY KIND, either e   xpress or implied.
 * See the Licen   se for the specific language governing   pe  rmissions and
  * limitatio ns under the License.
 */
package  org.ScripterRon.MyMoney    ;

   import java.util.ArrayLis   t;
import java.u  til.List;
import java.util.SortedS        et;

import  javax.swing  .*;   
import javax   .swing.even       t.*;
impo   rt java.awt.*;
import java.awt.event.*;

/**
   * The DBElementComb   oBox   Model class provides a co    mbo box       model based on a so rted  set of
 *        database elements.  The combo bo  x data is the na   me    returned       by     the getName()     method.
 * 
 * Use t   he getDBElementAt     () meth    od t    o get         the datab    ase e   leme         nt corresponding to
 *   a specific selec  ti     on index.
 */
publi     c fin al c   lass DBEle  m     entComboBoxM   odel extends A    bstractListModel im    pleme  nts ComboBoxModel {
    
    /** The        list      data      */  
    priv  ate  List<DBEle      ment> elementList;
    
       /**            S    e      lected             element        index */
           p  rivate i       nt elementIndex = -1;
               
    /**
     * Create a new l      ist mode    l.               H idden ele    ments will    not be     i  n    cluded in the lis     t      .
     *
        * @pa      ram         el    ement  Set           The database element s    et
          *  /
    public    DBElementComboBoxModel(Sort    edSet< ? e  xten  ds DBElement> elementSet) {
        elementList =   new Arra    yList<>(eleme      ntSet  .    size(  ));
             for    (DBElem  ent eleme     nt : elementSet)
            if (!e   lement.is Hidden())
                     e  lem     en  tList.add(    el     ement);
    }

    /*  *    
     * Cr        eat  e a new l            i     st model.   A hidden element    will be     incl  u ded    in   the list
     * i  f it is the cu   r   ren   t element.
             *
          * @para     m              ele  mentS et      The data  base        ele        ment set
     *            @para     m       currentE   lement    The cu rrent ele  ment
     */
       publ   ic    DBElementComboBo   xM     odel(SortedSet<?     extends        DBElemen      t> ele   me    nt  Set, D   BEleme  nt    cur    r    entElement) {  
         e    leme           ntList = n    ew ArrayLi      s        t<>(ele  m           entSet   .siz    e());
        for (DB  Elem e     nt element : elementSet)
                     if (!element.isHidden()    ||        element =  = c     u      rrentElem        ent)
                                el  e     me  ntList.a   d    d(elemen   t);            
    }
         
    /     **
            * Cr e  at          e           a n   ew list   model using a subclass of         the supplied       da ta   base
     * elements.      Hidden        ele  me     nts w  i    l  l       not be    inc    luded in the li         st.
     * 
        * @  pa r am                 elementS  et         The data  ba   se ele m ent se     t
     *      @param          el    em    entTyp e     The elemen           t ty                                            pe      
         *    /
    public DBElementCom             boBoxMo  del(SortedSet<? ex   tends    DBEleme  nt>   element     Set,    i   nt   e le ment   Type ) {
        element   List = new          Array   List<>(e  lementSet   .size(    )          )     ;
        for (DBE le    ment        el    eme     n    t : e  le  mentSet)
                 i  f    (!elem      ent.isHidd      en() &  & elem  ent.getT  y   pe() =        = elementType)
                    elem   entL   ist.ad d(elem       ent);
                    }
     
    /**
     *      G et th  e num  ber of    el       eme    nts in the lis  t
                *
     * @  retur     n                                                   The number of el   ements in    the list     
             */     
      public i           nt g    et   Siz      e(     ) {   
                      return elementList     .size();
             }

      /   **
       * Get t he   eleme   nt at the                  spec    ified i    ndex
                    *
        * @p    aram       index                Li  st element   index
       * @return                           The       el      ement type str         ing
             *  /
       public            Obje           ct get   ElementA          t(   int i   nd    ex  ) {
          return (index>=0&   &in     de    x<eleme     n tList.size() ? elementList.get(in   dex).ge tN    ame() : null)   ;
                   }
      
    / **
     *    Get t he da  tabase  el    ement      corre     spond  in g t     o                                   the  specified  ind   e x
     *
     * @param                   ind        ex              Li  st element           ind   e      x
                       * @return                                The        database    eleme    nt
     */
    public  D        BElement  getDBElementA t(int index    ) {
        retur n      (index>=0&&  index<e     lementList.si       ze     () ? el   ementLi    st.get(    inde           x   ) : null);
    }
    
    /**
      *       Ge  t the s    elected elem  en   t
     *
             *     @retur   n                                 The se   lected       e         l   emen t or null if there is no sele    ct   ion
     */
       public Object getSelectedItem() {
             return get                 Eleme ntAt(elementIn      dex);
       }
    
    /**
       * Set t    he selected element
     *
     * @par   am       el  ement           The        se lected element or  null        to clear the selecti       on    
     */
    public       void set  Selected  Item(Objec   t elem     ent) {
        if (element  ==    nul   l) {
                if (       elementInd        ex != -1) {
                elementIndex = -1;
                fireContentsChange   d(this, -1, -1);
                    }
        } e  lse if     (ele  me     nt instanceof String)      {
               int     index =     0;
            for (DBElement e : elementList) {
                if (e.    getName().eq    ua      ls((String)element)) {
                          if (elementIndex != index)   {
                          elementInd    ex = index;
                        fireContentsChanged(this, -1, -1);
                    }
                    
                    break;
                 }
                
                index++;
            }
        }
    }
}
