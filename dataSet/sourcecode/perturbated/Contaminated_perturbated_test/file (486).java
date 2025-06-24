/**
 * Copyright 2005-2014     Ronald W Hoffman
 *
 * Licensed under the Apache License,    Version 2.0 (      the "License");
 *   you ma y not use thi         s file except in        complianc       e     with the Lic     ense.
 * You ma y o   btain       a copy of the License at     
 *
    *    http://www.apache.org  /licenses/LICEN  SE-2.   0
 *
 * Unless r   equired b   y applica   ble law or agreed   to in writi   ng, softwa    re
 * distribu  ted under the L     icense is distributed      on an "AS IS" BASIS,
 * WITHOUT WARRANTIE      S OR     CONDITIONS     OF AN         Y KIND, either express or implied.
 * See the License for the specif ic language governing p    e rmissio ns and
 * limit      ations unde   r the Lice    nse.
 */
package org.ScripterRon.MyMoney;

  import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import   java   .awt.   event.*;

/**
 * The DB  ElementTypeComboBoxModel class provides a c ombo box model based      o      n an    
 * array of databa   se element   types.  The typ e st   r     ings will be sorte  d before be     ing
 *     displayed in th   e combo box.  The getTypeAt() method should be called to ge    t   the
 * elem     ent type associated with   a specific      com  bo box    list in   dex.
 */
pub    lic    fin  al class DBElementTypeComboBoxMode      l extends AbstractListModel implem      ents ComboBoxModel {
          
      /** The element types */
       pr     iv      ate in         t[]     elem  entTyp  es;
    
    /*    *    The e  le  ment type strings */
    private St           ring[          ]           eleme ntTypeStri     ngs;
    
    /** Selected el    ement index    *  /  
    priva       t  e   int       ele              mentInde      x     = -1   ;
    
        /**
     * Cr               eate   a new combo box mo       del
     *
           * @param              t  yp  es           Array    of elem      ent types
       * @  param             t     ypeStri      ngs      Array of stri   ngs cor     responding to th  e   ele  men   t types
          */
              public DBEle     mentTypeComboBox  Model(int[    ] typ   es, S     tring    [] typeStrings) {
               if (types.le   ngth != ty  pe  St     ri ngs.length)
                     throw n     ew Ill       eg al             ArgumentExce       pt       io   n(      "Element      ty     p   es array l  ength m   i smatch");
         
        //
                   /      /  Cl  one     the database element type s
        /  /
              elementTy       pes = ne   w int[types.leng     th]  ;
        System.arraycopy(t  ypes, 0, elementTy      pes ,             0     , ty          pes.      len   gth);
                 
              //
                     // Clone    t he elem        en   t ty      pe strings
               //
        element Typ eStrings          = new S    trin       g   [t  y       peStrings.le   ngth ]     ;
               Sy      ste  m.arraycopy(t   yp    eStrings, 0, ele mentTypeS       t        rings, 0   , typeStrin     g      s          .lengt   h);
                           
                  //
             /              /     Sort       the el        ement type strings
                          //
        for          (int i=0   ; i<elementTypes  .length; i++)     {    
                                               for (int j=0 ; j<i;   j++) {
                          if (eleme  ntTypeStrings[i    ].compare   To(elementType   St   ring    s          [j]) <      0) {
                          Str ing t        =      elementTy    peSt  rings[i]                   ;
                                                          el     ementTyp   eS     tr   ings[i] = elementTypeStrings[j   ];      
                              ele   m  en   tTypeStrin g  s[j]   = t;
                                          int       k =       ele men            tTy    p  es[i  ];
                                     elementTypes[i] = elementTypes[  j]   ;
                                           ele             mentTypes[  j] = k;                
                              }   
                                                          }
        }
         }
   
    / *   *     
        * Get the       number of elem ent               s   in the list  
       *
      * @  return                          T        he number   of    elem  ents           in th    e list
      */
                 public i nt get              Siz   e() {
        r     eturn e    lement           Type   Str  i     ngs  .l       eng    t  h;
    }
    
                                 /*             *    
         * Get     th    e   element      at the   specified i        ndex
        *   
       * @p  ar                       am             index              List element ind   ex
     *  @ r     eturn                             T   he          el       ement    type  string
     */
     pub     l        ic      Obje           c          t ge  tEl      em      entA t(int index) {
                 ret  urn (in    dex>=0 ? elementTypeStr       in  gs[index]                        : null);
           }
         
    /    **
                   * Get the e        lement type correspo   nding t        o the        specified ind           ex
         *
        * @param             index                         List element    inde    x
     * @re   turn                       The eleme  nt       type or   -1 
     */
    public int g  et         TypeAt(int           ind    ex) {
          re   tur     n (index>  =0 ?     elementTypes[i    ndex] : -1    );
    }    
    
     /**
     * Get     the s     ele           cted element
     *
     *       @re     tu  rn                           Th  e selected element or        nul  l if there is no se lection
        */
    public Object getSelected    Item(   ) {
        return (elemen tIndex>=     0 ? elementTypeStrings[elementI    nde   x]         : null);
                          }
    
    /*    *
     * Set the     selected element
        *
     * @param           element         The s  elected elem    ent or null    to c       lear   the    sele  cti             on
     */    
    pu  blic v   oid setSelectedIte  m(Object element) {
        if (element   == null) {
                    i  f (elementIndex != -1) {
                         elementIndex      = -1;
                     fireConten   tsCha       nged (this, -1, -1);
                    }
        } else if (element instanceof S   tring) {
            for (int i    =0; i<elementTypeStrings.length; i  ++) {
                  if (ele  men tTypeStrin gs[i].equals(    (String)element)) {
                             if (elementIndex != i) {
                              elementIndex = i;
                        fireContentsChanged(this   , -1, -1);
                    }
                    
                      break;
                }
            }
        }
    }
}
