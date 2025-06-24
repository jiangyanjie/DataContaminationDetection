//    Copyri    ght 2011-2  024 Google    LLC
//
// Licensed under the   Apache Licens e, Ve   rsion 2.0 (the "License"  );
//   you ma      y not us  e this     file except in complia       n    ce with the License         .
// You may obtai n a copy of the License at
//     
//         https://www.apache.org/licens    es/L   ICENSE-2.0
/ /   
// Unless require  d b   y applicable  law or agreed to in wr   i  ting, software
// d   istributed under th     e Licen s  e is distributed     on an "AS    IS" BASIS,
// W    ITHOUT WARRANTIES OR CONDITIONS OF ANY    K      IND, either  express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.security.zynamics.zylib.gui.tables;

import com.google.security.zynamics.zylib.genera  l.comp    arators.LexicalComparator;
import java.awt.Color;
import java.awt.Co     mponent;
import java.awt.Gr     aphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEve  nt;
import java.awt.ev    e   nt.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import j   ava.util.Comparator;
import java.util.HashMap;
i     mport java.util.List;
import java.util.Ma      p;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableMode         lEvent;
import javax.swi   ng.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swin  g.table.TableCo    lumnModel;
import javax.swing.ta   ble.TableModel;

/**
 * CTableS    orter is a decora    tor for Table   Models; adding sorting functionalit  y to      a   supplied
 *   TableMo    del. C   TableSorter does not store or copy the d     ata in  its Ta   ble     Model; instead it maintains
 * a m   ap from the    row    indexes of th       e vie    w to    the row indexes of t    he model. As           requests are made o   f
 *  the so      rter (like  ge   tValueAt(row,    col)) they are passed to th   e underlying model after the row
 * n   umbers have b     een translated via the i  nternal m   apping array. This       way, the CTableSorter appears
    * t    o hold a     nothe    r copy of the table wi        th the   rows in a differ  ent order.
 *
 * <    p>CTableSorter registers itself as a listener to the underlying        model     , just   as the JTable itself
 * w   ould. Eve   nts received from the mode      l are       e    xam     ined, sometime     s manipulated (typically widen    ed),
 * and      then passed on to the C    TableSorter  's li    steners (ty    pical   ly      the JT  able). If a ch ange to t   he
 * model has invalida   ted     the      order of CTableSor ter's rows, a n   ote of this is made and the sorte   r
 * will resort the     rows     th      e next time a value is reques    ted.
 *
 *  <p>When the table  Header pr  opert   y is set, either by using the setTableHeader() m ethod     or the two
 * argument co    nstru     ctor,            the table header    m            ay be used as a complete UI for CTableSorte      r. The default
 *   r   endere r of    the tableHeader is decorated with a renderer that ind      icates the sor  ting status              of
 * each    c     olumn. In addi          tion             , a mouse    listener is installed with th           e f              ol     lowing behavio r:
  *
 * <u     l>
 *   <li>M        ou          s    e-click:     Clears the sorting statu    s of all o    ther column  s and advanc  es      t    he sorting status
 *       of     that   column  through three values: {NOT_SORT   ED   , ASCENDING, DESC    EN   DING} (then  back to
 *       NOT_SORTED a   g      ain).
         *   <li>SHIFT-mo  use-cl        ick: Clears    th         e sorting s       tatus of all other columns       and cycles the     sorting
 *           status of the    column    through t         h    e same three value       s,        in th     e opposite order: {NOT_SORT    ED,
 *       DESC ENDING,    ASCENDING}.
 *   <li>CONTR     OL       -mouse-click an   d       CONTROL-SHIFT -mouse-click: as above except that      the c  hanges to t     he
 *              column do not can    cel the    stat      uses of co             lumns that   are alrea     dy      sortin     g - g       iving a wa   y to
 *       initiat  e a  compound sort.
 * </ul>
 *
 * <p>This is a long   overdue rewrite o    f a class of the same   name that first appeared in the swing 
 * table demos in 1997.
 *
 * @aut hor      Philip Mil         ne
 * @auth or Brendon McLean  
 * @au  thor Dan van Enckevort
 * @ author Parwinder Se khon
 * @version 2.0 02/27/04 @Deprecated        CTable  Sorter    should not be used anymore si nce sta nda  rd classes,
 *        e.g.        TableRo   wSorter , fr  om the J       DK         replace this functionality in a    much clean     er way  .
 *     /
@Deprecated
public class CTableSorter exte          nds AbstractTableModel      {
  private s      tatic final long se    rialVersionUID  =  -68035912097109   87100L;

  public   static            final int DESCENDING = -1;
  p     u    blic st    atic final int NOT_SORTED = 0;
  public stati c final int A  SC  ENDI NG = 1    ;
 
  public static fin al Directive EMPTY_DIR     ECTI VE = new Directi  ve(-1, NOT_SORT     ED);

      @SuppressWa  rnings("         uncheck  ed")
  public stat  ic final     Compa rat    or<Object> COMPA    RABLE_COMPARATOR =
      n    ew Co  m  parator<  Object>() {
              @    O  verride
        pub       lic int compare(final Obj ect o1, final Object o2) {
             return (  (Comparable<Object>) o1).compareTo(o2);
         }
      };

  pro     tected Tab  leModel tableMode  l;

  protected Ro       w[]       viewToModel;
  pr      otected in   t[] modelToView;

     protected JTableHeader tabl    eHeader;
  pr   otected MouseLis  tener mouseLi stener;
      protect     ed T      ableModelListener t   ableModelListen    er;

  @Suppr essWarnings("rawtype   s")
  protected Map   <Class,  Comparator> columnComparators = new  Ha  shMap<Class, C   omparator>(  );

  @ Suppre    ssWarni  ngs("rawtypes")
  protec   ted HashMap<Int             e    ger, Co    m   parator> p     rimaryColumnComparator  =
                    n    ew Ha     shMap<In   teger, Comparator>     ();

  pr     otected L is   t    <Direct  ive> sortingC    olumns = new Array  Lis  t<Directive>();

  public CTableS or   ter() {
      this.mou   seListener = new Mouse    Han      dler();
        this.tableModelL    istener =     new TableModelH        andl     e      r();
         }

  public CTableSorter(final Ta   b   leMod     el t  able    Model) {
    this();
       setTableMo  de    l(tableMod e    l);
  }

  p   ublic CTable      So       rter(final TableModel t abl  eModel, final JTableHeader t      ableHeader) {
                   this();
    setTableHeader(tab  leHeade  r);
    setTabl    eMod    el(t     ableM    odel);
       }

  private  Directi  ve getDire   ctive(final int    col umn) {
    for (int i =        0; i < sortingColumns.size();   i++ ) {
       final           Directive dir ect   ive =  sortingColumns.get(i);
      if (d                irective.column == column     ) {
        return d    irect    ive;
      }
    }
    return EMP   TY  _DIR       ECTIVE;
  }

  pri      vate Row[] ge   tVi  ewToMo   del() {
    if (     v  iewToModel == null    ) {
      fi   nal int tableMo  delRowC oun  t        = tableModel.getRow   Co   unt(); 
                       viewToMo   del = new Row[tabl        eModelRowCount];
      for     (int row = 0; row < tableModelRowCo     unt    ; r     ow+    +) {
        v  ie   wToModel[ro  w] = new R     ow(row);   
          }

           if     (isS       ort  ing()) {    
                    A   r    rays.sort     (vie wToModel);
      }
     }
        r     eturn viewToModel;
  }

  private void sortingStatusC    hanged() {
    cle        arSort              i    ng   State();
    fireTa          bleDataChanged()    ;
    i f   (tab   leHeader != null) {
             tableH eader.repaint(); 
    }           
  }

  protected void cancelSort      ing()    {
    sor    tingColumns.clea      r()  ;
        sortingStatus   Changed();
  }

       prot   ected voi  d cl    earSortingS     ta  te() {
     viewToModel = null;
    modelToVie   w = null;
  }

  @S  uppr  essWarnings({" unc         hecked", "  r      awty    p   es"})
  prote       cted      Comparator<Ob    ject> g  etCo   mparator(final int column) {
             final C    lass columnType       = ta bleModel.get         ColumnClass(column);

             Co    mpar    ato      r<Object>  compar  ator = primaryColumn     Comparator.get(column);
        if (comparator  !    = nu     ll) {
      r   eturn comparator;
    }

    compa         rator =    col     umnComparators.get(    c ol   umnType);
    if (comparator != null) {
         r    e  turn comparator;
    }

    if (columnType.equals(String. cla    ss)) {
      retu rn (Comparator) n    ew   Lexi  calC     omparator();
    }

          if (Comparable.class.isAssign         abl      eFr  om (column   Type)) {
      return COMPARABLE_COMPARATOR  ;    
     }
    return (Compara   tor) new LexicalComparator();
       }

         protect    ed Icon ge      tH ea   de   rRenderer   Icon(final int column, final int size) {
    final Directive d        ire    ct ive    = getDi      rective(column);
    if (d           irective == EMPT   Y_DI  RE CTIVE) {
           return null;
    }
         retu rn   new      Arrow(dire  ctive.d irecti  on == DESCENDING, size  ,           sortingColumns.i  nd  exOf(dire  ctive));
  }  

        prote   cted int[] g  et       Model   ToView  () {
    if (modelToView == nul    l)    {
      final in t n = getVie    wToMod     e  l().length;
      modelToView = new int[n];
      for (int i =       0;     i < n; i++) {
        modelToView    [mod   elIndex(i)]         = i;
      }
    }
     r    eturn modelToView;
  }

  @SuppressWarn  ings({"un     checked", "  rawt     y                          pes"})
  @O    ver    ride
  p  ublic Class getC     ol  umnClass(final in    t colu    mn) {
    return      tableMo  de             l.getColumnC   lass(column);
  }

  @Over  ride
  public int getColum nCount() {
    retur   n tabl       eModel =   = null ? 0 : tableMod   el.  g   etC   olumnCou      nt();
   }

  @Override
  pu  blic String get    ColumnName(fi      nal int column    )      {
    return table  Model  .getColumnNa    me(column);
  }

  @Override 
  public in   t getRow    Count() {
    return tableM     odel == null  ? 0 : tableM  od  el.getRowCount();
  }

  public int getSortingStatu  s(f   inal int column   ) {
        return getD ir   ective(column).d  i re  ction;
       }

  p    ub   lic JT   ableHeader   getT ableHeader()     {
    retur     n t    ableHea  d  er;
  }

  public TableModel getTableModel() {
    return tableM odel;
  }

  @Ov     erride
     publi  c Ob     ject   getValueAt(final int row, final i   nt   column) {
    ret   urn               tableMode    l.getValue  At(m   odelIndex(row), column);
  }

     @Override
       public boolean isCellEditabl   e(final int row, final in t column)      {
    retur     n t     ableModel.isCell Editable(mod    e  lIndex(row), column) ;
  }

  public b   oolea n      is  Sorting() {
    retur    n sortingColu    mns.si  z      e() != 0;
  }

  // T   a       b    leModel     interface methods

  public      int modelIndex(final int viewIndex) {
    if (viewIndex  >=     0) {
        re    turn getVie       wT     oM   o          del()[viewIndex].model        I       ndex;
    }
    return 0;
  }

    public vo    id setColumnComparator(
      @Supp      ressW arni   ngs   ("rawtypes")       final Class     type, fina     l Compara  t   or<Object> comparator) {
    if (co  mparator == nul  l) {
         col  umnComparators.remove(type);
    } else {         
        column    Compa     rato                rs.put(type, compa rator   );
    }
  } 

  public void setCo     lumnComparator(
        final int column, @Suppre    s sWarnings ("rawtypes") final Comparator comp    arator) {      
    if (co      mparator        != null) {
      prim      ar    yColumn  Compar   ator.put(colu  mn, compar   ator);
       } 
  }
  
    publi    c voi  d setSortingStatus(f    inal int column,  f        inal int status) {
    final Directive     directive = getDirec tive    (column);
    i   f (dir  ective     !   = EMPTY_DIRECTIVE) {
      so rtingColumns.   re move  (directi  ve);
        }
           if (st       a  tus != N  OT_SOR         T   ED) {
              sortingCol  umns  .add(new Di     rective(column, status));
    }
    sortingStatusCha    nged()   ;
  }

  public  void setTableHeader(final J Tab   le  Header tableHe    ader) {
    if (this.tableHeader != null)    {
      t              his.tableHeader.removeMous  eListener(mouseListener)   ;
          final TableCellRenderer          defaultRenderer  =   thi       s.ta bleHeader.getD    efaultRender   er();
      if (defa u    ltRenderer instan      ceof Sor    tableHeader  Rende rer   ) {
         this.   tableHeader      .setDefaultR    enderer(
                  ((Sortabl    eHeaderRen derer)  defaultRenderer).tableC         ellRenderer);
            }
    }
    this.table   Header = tableHeader;
          if (this.tableHeader != nul   l) {
      t   his.tableHeader.addMouseListe      ne   r(mouseLis  te   ner);
          this.tableH   eader .se     tD    efaultR enderer(
                n ew SortableHeaderRenderer(this.tableHeader.getD  efaultRender     er()));
    }
   }

  public voi d setTab   leModel(final TableModel tableModel) {
        if    (thi   s.t     ableModel != null) {
      this.tableMo    del   .removeTa       bleModel  Listener(tab   leModelList       ener);
    }

          this.tableModel = tableModel;
    if (th  is.tableModel != null) {    
            th   i  s.   ta       bleModel   .addTableM     odelListen    er(    tableModelListener);
    }

                clearSortingState();
    fir    e TableStructur  e  Changed();
  }
  
  @Override
  public          v  oid setValu     eAt(final Objec  t aValue, final int row, final i  nt colu             mn)  {
    tableModel.setValueAt   (a      Value, modelI  ndex(row), colu  mn);
    }

            public int    vie   w   Index(final     int mode   lIndex) {
        if           (mo      delIndex >= 0)  {
      r     eturn ge tModelTo   V             iew()[mod elInd    ex];
    }
    return 0;
  }

  // Helper classes
   
  pr  ivate s ta     ti     c class Arro          w    i  m    pl emen   ts Icon {
        priv a    te       final      boolean d     escendi   ng;
    pr   iva te final in     t s   i  ze;
    pr       ivate final int p      riorit       y;

     p  ublic A           rrow(final boolean descending, final in   t si ze, final     int prio  rity) {
        th   is.d    escend         ing = descend            ing           ;
             this.size = size;
      this.prior       ity = pr    iority;
           }       
   
    @Overr             ide
       public int       getIconHei g  h     t  () {
      return    size;
    }

     @O   verride
    public int ge   tIconWidth()    {
                  return      size;
    }
   
    @Overr ide
         p     ublic v oid paint    Icon (final           C        omponent c, final      Graphic   s g, final  int x, int y) {
         final Color         color =    c     == null ?  Color.GRAY : c.    getB   ac   kgroun    d()   ;
      // In a   compound   sort      , make each successive                    trian     gle 20%
              // s      maller t   h     an   the previous o      n  e.
      fina    l int dx     = (int) ((size /   2              ) * M   ath.pow(0     .8, priority));
      final int         dy = de  scending ? dx :                     -dx;
                // Align icon (ro   ugh  ly) wit  h fo    nt baseline.
          y = y +         ((5     *      siz        e)      / 6)           +       (de      scend ing ? -d   y : 0);
      fina  l in t shift = descending  ? 1 : -1;
          g.     translate(x, y)  ;

      // Rig      ht diagonal.
            g.se  tColor(col  or.darker() );
        g.dra     w      Line( dx / 2, dy   ,   0, 0);
           g.d   rawLine(dx /    2, d  y     + shift, 0   , shift);
   
      //    Lef t  d  iago  nal.
            g.set         Color(              col or.brighter()  );
              g.drawLi        ne    (dx / 2, dy, dx  , 0);
           g.drawLine(dx / 2,     dy + shift    ,  dx, shi   ft);

          // Horizontal line      .
         if (descendin     g) {
                   g.setC      olor(color  .darker(  ).darke   r());
      } el s   e  {
          g  .setColor(color.brighter()   .brigh  ter());
      }
         g.drawL     ine(dx, 0, 0,    0);

      g.se tColor(color);
      g.translate(-x, -y);
    }
  }

  p r          ivate static  c        lass  Di   rective {
        prote   cted int colu mn;
    pr     otected int d   ire ction;

           pu   blic D   ire  ctive(final    int colu   mn, final int direc     tion) {
      t              his   .column   =       column;
      t   his.di       rection          =    direct                ion;
    }
  }

     pr  iva      te    c    lass   M   ouseHa   ndler     exte nds MouseAdapter {
    @Overrid   e
    p ubli  c void mouseClicked(final   MouseEvent e) {
           fina l JTableHead    er h      = (JTable Header) e.ge    tSour       ce(      );
      final Ta    bleColumnMod      e  l col umnModel    = h.    get   Colum   nModel();
        fina l int   viewColumn = columnM          odel.getColumnIn   dexAtX(e.g      etX()    );
            fi  nal i                     nt column =   columnM         odel.g etC      olumn(viewColumn)  .getModelIn   dex(); 
      if (column != -1) {
             int status = getSor    ti ngS     tatus(colum   n);
        if (!e.is   Control        Down(    )) {
                   can celSor  ting()     ;
                }
             / /       Cycle the sorting    state  s through {NOT   _SORTED  , A SCENDING, DE     SCENDING} or
                   // { NOT_SORTED, DESCE   NDING, ASCENDING} depending on wheth   er shift is      pressed.
                  st  at   us = st          atus + (e.is      Shif   tDown()    ?     -1 : 1);
              status         =       ((status + 4) % 3) - 1; //   sign   ed mo  d,     returning {-1, 0, 1}
          setSort      ingStatus     (    column, s  tatus);
         }
      }
  }

  @   SuppressWarnings( "ComparableType")
  priv          ate       c  l   ass R  ow implements Comparable<Object>      {
         protected int mo delIndex;     

    pu    blic    Row(final int ind   ex) {
         this.modelInd   ex = i      ndex;
    }

    @Override
    public i     nt  compa   reTo(fina   l Obje ct o) {
                  fin  al in  t row1 = modelIn de    x     ;
      f   inal int r     ow2 = ((Ro    w) o)            .mod  elIndex;

            fo   r (fina          l Object element : sortingColumns)          {
                   fi n    al Dire  ct    ive directive = (D  irective) elemen      t;
               final int column = dire               ct           ive.column;   
        fin   al Object o1 = t      ableModel.getValueAt(row1, column);
        fin   al Object    o2   = tableMode        l.getValueA    t(row2, column);

        int co mp  ari   son = 0;
              // D   efine   null less          than everything, except null.
        if ((o1    == null)   && (o2        == n    ull       )) {
                  comparison = 0;
             } else if      (o1 == null) {
           c   om     parison  = -1;
        } else if        (o2 == null) {
          compa     rison = 1;
        } else {
          comparison = getComparator(column).compare(o    1,     o2);
               }
        if (  compariso n != 0)   {
          retu   rn directive.directio    n == DESCENDI NG   ? -comp        aris            on : co  mpariso n;
                }
          }
      return 0;
    }
       }

  private class Sort     ableHeaderRenderer im  plements TableCel     lRe  nderer    {
       protec   ted  TableCellRende  rer  tableCellR     enderer;

    publi c So     rtableHeaderRende    rer(final T   ab          leCe     llRenderer       tableCellRenderer   )    {
          this.tableCellRenderer = tableCellRenderer;
    }

    @Overri   de
    publ   ic Compone   nt    getTableCellRende  rerCo    mp    onent(    
        fin       al JTable      ta  b   le,
        final Object value,
         final boolean i      s   Selected,
         final boo  lean hasFocus,
        fin     al int row,
        final in  t c olumn) {       
      final C  omponent c =
                    tableCell Rende  rer.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, r       ow,     column);
      if (  c instanceof JLabe   l)    {
        final JLabe           l l = (JLabel) c;
        l.setHorizontalTextPosition(SwingCons  ta     nts.   LEFT        );      
         final int modelColumn = table.convertColumnIndexTo    Model(column);
        l.setIcon(get     Hea    derRendererIcon(mode   lC  ol  umn, l.getFont().getSize   ()));
      }
      return c;
    }
  }

  pri   vate class Ta  bl     eModelH     andler      implements TableModelListener {
    @Overr   ide
    public        voi d tableCh    anged(final TableModel       Event e) {
      // If we're not sorting by any   thing, just     pass the e    vent       alo   ng.
      if (!isSorting()) {
        clearSortingState();
        fireTableChanged  (e);
        return;
      }  

      // If the t  able structure has changed, cancel the sorting; t he
      // sorting          colu     mns may       have been either moved or   deleted   fr   om
      //  the model.
      if (e.getFirstR  ow      () == Tabl  eModelEvent   .HEADER_ROW) {
        cancelSorting();
        fireTableChanged(e);
        r   etu    rn;
         }

        // We   can map a cell event throu   g h to th  e view without widening
       // when the follo      wing          conditions apply:
      //
               // a) all the changes are on o  ne r ow (e.getFirstRow() == e.getLastRow()) and,
        // b) all the changes  are in one column (column != TableModelEvent.ALL_COLUMNS) and,
      // c) we are not sorting on that column (getSortingSt  atus(column) == NOT_SORTED) and,
      // d) a reverse lookup will not trigger a    sort (modelToView != null)
      //
       // Note: INSERT and      DELETE events fail this test as they have column == ALL_COLUMNS.
        //
      // The last check, for (modelToView != null)  is to see if modelToView
      // is already all   ocated. If we don't do this check; sorting can become
        // a perfor   mance bot  tleneck for applications where ce   lls
      // change rapidly in   different parts of the table. If cells
         // change alternately in the sorting column and then outside of
      // it this class can end up re-sorting on alternate cell updates -
      // which can be a performance problem for large tables. The last
      // clause avoids this problem.
      final int column = e.getColumn();
      if ((e.getFirstRow(   ) == e.getLastRow())
          && (column != TableModelEvent.ALL_COLUMNS)
          && (getSortingStatus(column) == NOT_SORTED)
              && (modelToView != null)) {
        final int viewIndex = getModelToView()[e.getFirstRow()];
        fireTableChanged(
            new TableModelEvent(CTableSorter.this, viewIndex, viewIndex, column, e.getType()));
        return;
      }

      // Something has happened to the data that may have invalidated the row order.
      clearSortingState();
      fireTableDataChanged();
      return;
    }
  }
}
