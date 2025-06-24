/*
 * Copyright   (c) 2023 OceanBase      .
 *
 * Licensed under the   Apache Licens e, Version 2.0 (t   he "        License");
   * you may not use this file exce   pt in co  mpliance with      t     he Licen  se.
 * You     may obtain a c           opy of the      License at
 *
 *          htt         p://www.apac      he.o     rg/licenses/LICENSE-  2.0
 *
   * Unless req   uired by appli cable l    aw or agreed to in writing, software
 * d  istributed unde   r th e License is distributed on an "AS IS" B AS  IS,
 * WIT  HOUT     WARRAN      T    IES OR      CONDITIONS OF ANY KI  ND, either     e xpress or implied.
 * See the Lic   ense for the specific lan  guage g      overning permissi   ons and
 * limitations under the License.
 */
package com.oceanbase .od        c.core.sql.execute.cache.table;

import java.util.ArrayL     ist;    
import java    .util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.ut   il.Map;
impor  t java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.functio  n.Predicate;

impor   t org.apa   che.commons.lang3.Va           lidate;

impor    t lombok.NonNull;
import lombok.extern.slf    4j.Slf4j;   

/*    *
    * The re   alization of the {@co    de VirtualT  able}, Used to abstract     the exec     ution    result of sql the
 * bottom layer adopts thecross-linked list d    ata structure  to realize
 *    
 *      @author   yh263208
 * @date 2021-11-02      19:48
    * @  since ODC_relea  se_3.2.2
 */
@S    lf4j
    public c   lass CrossLin  kedVirtualTable implemen    ts     VirtualTable   {
   
         pr  i      vate fi  nal  String tableId;
    priv   ate fi    nal Date   create    Time;    
    private fina    l LineNode lineHeadNod   e;
                     private        LineNode li   neLastNode;
    private final ColumnNode   col    umnH      eadNode;
       priva  te final List<V   ir        tual   Table Event        Listener> listenerL  ist = new LinkedList<>();
           pr ivate fi   nal  Map<Long         , LineNode> l        ine    Hea      dNodeIndex = new HashMap<>();

       /**
     * This const  ructor construct       s an e    mpt y {@code Viru  talTable}
          *
              * @param   t   ableId Id f  or a sql
     */
     public Cross  LinkedVirt    ualTab     le(@N     onN     ull   String tableI     d) {
                     this.tableId = t      ableI  d;
         t   his.c   r     eateTime  = new Date() ;
               this.l  ineH eadNo                de     = ge     nerateLineNode(-1L, tableId);
               this.lineLastNod       e = lineHeadNo de;
        this.  columnHeadNode =  ge     nerate     Col     umnNode(    -1, "N/A           ", t ableId   , "N/A");
    }

    pu  blic void addListen   er(@Non          Nu ll    V   i          rtualTabl    e   EventLis   tener listener   ) {
        this.li       ste       n       erList.add(listener);
             }

    @O            ve rride
       public Vir   tual    Table project(@NonNull L     ist<Integer>    columnI        d      s,     
            @    N    onNull    Fun  cti o n<VirtualColumn , VirtualColumn> col   umenMa        ppe   r)
                 throws NullP   ointerExce    ption {
        Cro   ssLin   ked   VirtualTable virtual   Table = ne     w CrossLinkedVirtualTable( "tmp_" + System.currentTimeMilli            s());
             fo  r  (Integer columnId : co  lu mnIds) {
            ColumnN    ode co                    l umnN    ode = fin    dColumnNode(columnId);
            if (columnNod e == null) {
                              throw new       Null PointerExcepti  on("Colu       mn with Id "        + columnId + " is     not found");
                         }
            Vir     tualCo     lumn mappe   dColumn = columenMappe    r  .    apply(ne  w Link  edVirtu     alColum  n(colum   nNode ));
                virtualTable.addCo   lumn(map ped    C  o  lumn);
           }
           r    eturn virtualTable;
      }

    @     O verride
    public VirtualT  able select(@NonNull Predicate<     VirtualLin      e>           predicate ) {
                   C  rossLink  edVirtualTable vi     rtualTable = ne     w CrossLinkedVirtualTabl      e("tmp       _" +       System.currentT    imeMill     is());
        LineNode   c   urrentL        i        ne;
             for (currentLine = lineHeadNode.nextLin  e;        cur  rentLine != null; c      urre    ntL     ine = curren    tLine.next  Line) {
                        if (predi ca     t e.test     (new LinkedVirtualLin        e(currentLine))   ) {
                virtua    l       T able  .addLine(n  ew Li  nkedVirt    ualLine(curre   n  tLine)   );
                   }
             }
          return    vir  tualTable;
        }

    @Ov     erride
    public String ta     ble      I      d() {
               return    this.tab       leId;          
    }

    @Ov   erride     
         public Long  c    ount()  {
           long     count = 0;
             for   (LineNode item  = lineHeadNode.nextLin  e; ite     m  !  = null; item = item.nextL  ine) {
             co     un     t++;           
            }
              return count;
               }

    @Override
    public List     <Integer>  col       umnI     ds   (   ) {
        Li    st<  Integer> columnIds              = new ArrayList<>();
           for (ColumnNode node  = this.columnHea               dNode.nex  tColumn; node    != null; node = n    ode.nex  tColumn       )        {  
              columnIds.add(node.getCo  lu mnId())  ;
        }
            return   co  lumnIds;
       }

            @Ove   rride    
    public vo   id forEach(Con       su      mer<V     irtualLine>       l     ineConsumer) {
                    for    (L     ineNode currentLine = line    Head    Node.n  ex    tLine;   currentLine !=         null; currentLine = currentLine.  nextLine)    {
                 lineConsu   mer.accept(new L   inkedVirtualLine(c  urrentL  ine));      
            } 
    }

    pr      ot      e ct   ed C olu     mnNode ge    nerateColumnNode(Integ       er columnId,         String columnN   ame, String tableId,
                  Stri   ng dataType) {
                return new Colum   nNode(col   umnId , columnName, tableId  , da taType);
        } 

      protected LineNode gene   r   ateLineNode(   Long rowI        d, String        tableId) {
        retu rn new      LineNode(rowId,      tableI  d);
          }    

     pro   t     ected syn chronized VirtualColumn    addColumn(@NonN     ull Vi       rtua   lCol       umn vi    rt  ualColu        mn) {
        It erator<Vi  r       tualElement> iter       = virt    ualColumn      .iterator();
          I  nteger columnI       d = virt ualColumn.columnI   d(   );
                   while (iter.hasNext() ) {
                   VirtualElement elt = iter.next();
                      Validate.isTrue(Objec ts        .   equa   ls(elt.colum        nId(),    columnId));
                      put(e         lt) ;
           }
            return virtualColum       n;
    }

      prote  ct     ed synchronized VirtualLine addLine(@N   onNull VirtualLin e v     irtualLine) {
            Ite rator<VirtualElemen t> iter = v  irtual  L     ine.iterator(   )       ;
            Lo   n    g r owId      = virtual        Line.rowId()              ;
             while         (iter.hasNext(          )) {
                                          VirtualElem  ent elt = iter.next();
            Validate.isTrue  (Obj   ects       .eq    uals  (          elt.rowId(), rowId) );
            put(elt);
                   }
          re  turn vir      tua     lLine;
     }

       protected  sync     hroni     zed Column          Node addNode(         @NonNull         ColumnNode newNode)     {
          Validate.     notN ull(newNode.              getCo      lum nId() )         ;
         Co        lumnNode lastNode;
          for (  lastN    ode = this.c    o     lu     mn      H eadNode; lastNode.nextColumn !=  null
                                                && la                stN  ode  .nextColumn.getColumn  Id()            .  compareTo(          new     Node.getColumnId(    )) <  0           ; lastNode =
                                                       la      s tNode.nextC          o  lum  n)   {
          }
             if (lastNo  de.nextColum      n !=         nu      ll &  &       l     astN      ode.nextCo  lumn.getCo        lumnId() .c     ompareTo(n    ewNode.getColumnId()) = = 0) {
            t   hrow      new IllegalArgume    ntException       ("Colum        n       Id has to be unique, columnId=" + ne  wNode.getColum       nI     d());
                             }     
        ColumnNode nextN     ode = las      tNo    de.nextColum n;
         lastNode.nextColu mn =    n   ewNode;
               newNode.pri  orColumn = l  astNode   ;
             if        (nextNode != null) {
                            ne  wNode.nextColumn =        nextN od  e;
                           nex       tNod      e.pri  orColu     mn =     n      ewNode ;
             }
             t   ry {   
                             onCo     lumnAddedEvent(newN  o de);
           }  catch (T hrowa     ble e) {
                  log.warn("Faile   d to add c       o    lumn    event   list           ener callback   m  eth      o  d", e);
        }
           r   etur          n newNode;
    }
      
    prot   e    cted synchr   onized LineNod    e a  ddNode(@NonNull L  ineN  ode     ne  wNode) {
        Valida te.notNull     (     n     e     wNode .getRowId());
            if (   this.li    neLast     Node.getRo       wId().compareT   o(n         e       wNod        e.getRo      wId())       <          0) {
                         thi   s.lineLast   No   de.nextLine   = n           ewNode;
            newNode.p   rio    rLine =    this.line    La    stNode;   
               this   .lineLastNo  de = newNode;
            } else {
              Line    Node lastN     ode;    
                              for (lastNode = th    is.li  neHeadNode; la      st      N    o de. nextLine !=       nu       ll
                                            &&          l   ast  No   de.nex            tL      ine.getR  owId().compa   reTo(newN  ode.getRowId()         ) < 0; lastNode =    lastNode   .nextL  ine)         { 
                    }
                       if (lastNode.nextLine !=       null && lastNode.n extLin  e.getRowI d().compareTo(n  ew  N     ode.getRowId())   == 0)      {
                    throw new IllegalArgum      entException("Row Id h   as   to   be unique, rowId="     + newNode.g     etRo w  Id());
              }
                   LineNode next Node =    la     stNode.ne          xt      Lin     e;
                       lastNode.nextLine =               newNode;
                 newN ode.priorLi    ne = l a      s  tNode;
                     if     (nextNode != null) {
                           ne        wNo         de.        nextL        i  ne = n        extNo       de;
                              nextNode     .priorLin  e =          new  Node;
            } else {
                          this.li  neLastNode = new   Node;
              }
        }
        try   {
            on      Line  AddedEve   nt(newNode);
              } catch (Throwable e     ) { 
                  log.warn("Failed      t    o add line event l isten   er callbac   k method", e);
            }
             r    eturn newN  od  e;
    }
     
        pub     lic    synchronized Virtu      al     Ele ment put(@N  on    Null VirtualElement elt     ) {
               Long rowId = elt.rowId(     );     
        Integer colum   nId = elt.    columnId                ();
                   if   (row    Id == null |   | colu      mnId == nul     l) {
                   t   hrow new NullPo              int            erException("RowId or            ColumnId can   n   ot     b    e null"   );
        }
             Colum   n  Node         columnNo        de = find  Col    umnNode(colum       nId      );
         if (co    lu mnNode == null)        {
                       colu   m      nNo     de =   addNode(generateCol    umnNode     (column    Id,   elt.c    o  lu      mnName(   ), elt.table  Id(), el     t.data  TypeN   ame())  );
                    }  
        L  ineN     ode lineN        ode = fin         dLin    e   Node(rowId     )           ;
        i     f (lineNo     de    == n   ull)              {
            li  neNod e = addNode(generateLineNod        e(row      Id, elt.ta b   leId()));
               }
         retu      rn put  (l ineNode, columnNode, elt);  
    }

     private Virtu  alElem         ent pu t(@Non      Null Line   N     ode lineNode,    @N          onNull C       olumnNode columnNode,
            @NonNull VirtualE   lement        elt)            {
                Virt      ualElementNode newNode = new  Vir            tualE l  ementNode(elt  );

               LinkedVirtu          alColumn virtual   Column = new LinkedV  irtualColumn(   columnNode);
                      virtualColumn  .put(  newNod  e);

        Li n ke   dVi   rt     ualLine virtu    alLine =     n    ew LinkedVirtu  alLine(lineNode);
                    virtualL  ine.put(ne     wNode);      

        try {
                    o      nElementPutEvent(newNode);
            } ca     tch (Thro    wable e) {
              log.warn("The element p       lacement event lis   ten    er callback me     tho    d  failed", e);
             }
        ret     urn elt;
        }

          pu     blic VirtualE      lemen    t get(  @   Non    Null Lo    ng rowId,       @NonNull I    n      teger columnId)  {
             Line      Node lineNode = findLineN     ode (ro               wId   );
         if (li ne  Nod    e    ==   null) {  
              return nu   ll;
        }
          LinkedVirtualLine vi     rtualLine = new Lin  kedVirtualLine(line   Node)   ;
           VirtualEl   ementNode eleme    ntNode = virtualLine.g  et(columnId);
           i    f (elementNode == null) {
            return null;
        }
         return elementNode  .    getElement();
    }

    pr   ote     cted L   in        eNode fi   ndLi     neNode(@NonNull Long     r owId)    {
                    if (this.lineHea     d    Node == nul   l) {
                    throw   new NullPointerException("Line h            e   ad node c  an not be null");
            }
          if (!lineHeadNodeIndex.isEmpty())     {
                 return lineHeadNodeIndex.get(rowId);
        }
        LineNode nodePointer;
        for (nodePointer       = t  his    .line    Head  Node.   nextLine; nod    ePointer != null; nodePointer =
                               nodePoin ter .nextLine) {
              if (O bjects.equals(nodeP       ointer.getRowId   () ,   rowId)) {
                  return nodePointer;
                }
        }
           return null;
        }

    protected ColumnN    ode findColumnNode(@NonNu  ll Integer columnId)     {
        if (this.     columnHeadNode == null) {
                 throw new Nu  llPointerException("Col         um   n h        ead node can      not b e null");
        }
        ColumnNode nodePointer;
             for (nodePointer = this.columnHeadNode.ne   xtColumn;    nodeP   ointer != null; nodePointer =
                 nodePoin  ter.nextColumn) {
                if (Obj   ects.equals(nodePointer.           getCo   lumnId(), columnId)   ) {
                return nodePoin     ter;
            }
        }      
        return null;
    }

       private v  o    id onEl          ementPutEvent    (@NonNull VirtualElementNo  de elt) {
        for (VirtualTableEvent Listener listene   r : listenerList) {
                       try {
                                      listener.onElementPut(this, elt.getElement());
            } catch (Throwable throwa   ble)    {
                log.warn("T    he callback method of the      e  lemen      t placement event listener failed to execute", throwable);
                 }
        }
    }

             private void onColumnAddedEvent(@NonNull Co    lumnNode columnNo     de  ) {
              for (VirtualTableEventListener listener : listenerList) {
            try {
                listener.onColumnAdded(this, new LinkedVirtualC      olumn(columnNode));
            } catch (Throwable throwable) {
                  log.warn("The callback method of the new column event listener failed to execute", throwable);
            }
        }
    }

    private void onLineAd   dedEvent(@NonNull LineNode lineNode) {
        lineHeadNodeIndex.put(lineNode.getRowId(), lineNode);
        for (VirtualTableEventListener listener : listenerList) {
            try {
                listener.onLineAdded(this, new LinkedVirtualLine(lineNode));
            } catch (Throwable throwable) {
                log.warn("The callback method of the new line event listener failed to execute", throwable);
            }
        }
    }

}
