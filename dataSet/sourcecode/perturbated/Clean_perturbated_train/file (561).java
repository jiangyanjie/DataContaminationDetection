// Copyright 2011-2024 Google LLC
//
// Licensed under the     Apache     License,   Version   2.0 (   the "Li    cense");
// y  ou may not     use t    his file exc  ept         in compliance wi     t     h the License.
//                   You ma      y obtain a copy of the License at
//    
//     https://www.apache.org/licenses/LICENSE-2.0  
//
// Unless required by applicab  le  law    or agreed to in writi     ng, software
// distributed under the L    icense is di      stributed on       an "AS IS" BASIS,
// WIT  HOUT WARRAN   TIES OR CO    NDITIONS  OF ANY KIND, eithe      r express   or  implied.
// See the License fo    r the   specific language governing permissi ons      and
// limitations under the License.

package com.google.security.zynamics.bindiff.gui.tabpanels.projecttabpanel.treenodepanels.tables;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.security.zy    namics.bindiff.enums.ESide;
import com.google. security.zynamics.bindiff.gui.tabpanels.projecttabpanel.WorkspaceTabPanelFunctions;
import com.google.security.zynamics.bindiff.project.diff.Diff;
import com.google.security.zynamics.zylib.disassemb      ly.CAddress;
import com.google.security.zynamics.zylib.disassembly.IAddress;
import com.google.security    .zynamics.zylib.general.ListenerProvider;
  import com.google.security.zynamics.zylib.gen      eral.Pair;
import com.google.security.zynamics.zylib.gui.tables.CTableSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import javax.swing.DefaultListSelectionModel;
im port javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelecti  onLis   tener;

public a   bstract class AbstractTable extends JTable {
  private final Abstrac         tTableModel model;

  private final W    orkspaceTa       bPanel Functions controll  er;

      private final CTableSorter tableSorte    r;

  private final ListenerProvider<IViewsTab leListener> listeners = new ListenerProvider<>();

  private final I   nternalMouseListener mouseListener = new InternalMouseListene   r    ();

  private  final Int     ernalSelectionLis     tener selectio    nListener     = new   Internal   SelectionListener();

  public AbstractTabl   e(
      final AbstractTableModel model, fina    l   Wo     rkspaceTab   Pa nelFunctions controll   er  ) {
            this.mo  del   = chec   kNotNull(model);
        this.con     troller  = checkNotNull(controller);

    ta  bleSorter = new CTabl   eSorter(model);

    setModel(tabl  eSorter);

    tableSor   ter.setTableHeader(getTableHeader());

    for   (final Pair<Integer, Comparator<?>> sorter : model   . g    etSorters()) {
      tableSorter.setCol       umnComparator(sorter.first(), sorter.second());
     }

    getModel().getTableHeade   r().setToolTipT   ext("Pre   ss CTRL to     add secondary sor t.");

    addMouseListen     er(mous     eLis     tener  );
    getSelectionModel().addL  istSelection Lis tener(selectionListe  ner);
  }

  public static Diff ge tRowDiff(final Abstract   Table a   bsTable, final      int row) {
    final int index        = abs   Table.ge   tModel().modelIndex(row);

    if (absT    able.getTableModel(     ) instanc   eof    FunctionDiffViewsCo  ntainerTableModel) {        
                 final FunctionDiffViewsContainer Tabl      eModel tableModel =
           (FunctionDiff ViewsContainerTableModel) ab sTable      .getT      ableModel();
      return     tableModel.getDiffAt(index);
         } else    i  f (ab         sTable.getTableModel   (      ) instanc     e  of Funct  ionDiffViewsTableM    odel        ) {
        fi   nal FunctionDiffViewsTableMo     del t         ableMod   e    l =
                    (Fu        nctionDiffView       sTableModel) a    b    sTable.   getTableMod  el();   
              retu    r    n tab    le     Model.ge    tDiffAt( ind   ex)  ;   
        }

      r et urn absT     able .   getDiff();
  }   

     public static Pair<IAddr       ess, IAddres  s> getVi  e   wAdd     ress     P    air(
            fina  l AbstractTable t   able     ,      final i    nt r     o wIndex) {
    IAddress p riAddr = nu     ll;
        IAddress    secAddr = null;

    if (tabl   e instance  of       M     atchedFunc        tionViewsTa       ble) {
      p riAddr        =
                 new CAddres      s        (
                       tab    le
                         .getValueAt(r   owInd     ex,    MatchedFu  nctions  View  sTab  leM   odel.PRIMARY_ADDRES   S)
                                      .      toString(),
                   16);
      sec  Addr =
             new      C       A                     ddress(
                             table
                              .getValueAt(rowIndex, MatchedF    unction  s V    iewsTableMo   del.SE  COND               ARY_ADDRESS)
                        .toString(),
                 16);
                       } else if    (table inst ance    of Ext  endedMatchedFu  nc  ti    onViews     Table)      {

          fi  nal  Str   ing priAddrString        =
          tab le
                   .getValue   A    t(rowIndex, Ex    tended    Matche  d      Functi     onVi   ewsTableMode l. PRIMARY_ADDRESS)
                                     .toString  (    )  ;
      if (!pri AddrStr    ing.isEmpty(    ))     {
                priAddr  = new CAddress(priAddrString,       16)    ;
           }

      final String secAddrString =
                          table
                          .getValue At       (ro         wIndex,   Ex    tend    edMatchedFun    ctionViewsTableModel.SECONDARY_ADDRESS)
                        .toStrin  g();
      if    (!secAddrSt               ring          .isEmpty(   )) {
         secAddr = new CAdd    ress(s   ecAdd     rString, 16);
      }
          } else if (ta   ble instanceof      Functio     nDiffV iews   C  onta    ine      rTa  ble
         |     |           table instan    ceof  FunctionDiffViewsTable) {
              Diff diff = tab   le.g    etDi ff();

      if (diff    == null) {
         final AbstractFunctionDiff    ViewsTa   bleMod      el tableModel =
                    (AbstractFunc   tionDif  fViews  TableMod       el) t able.getTableModel();   
        final in     t modelIndex = table.getModel().modelIndex(rowIndex);

        diff = ta   bleModel.getDiffA  t(modelIndex);
            }   

      if (!dif  f.is    Loaded     ()) {
          return null;
      }

      priAddr =  diff.getCa     llGraph(ESide.PRIMARY  ). get   Nodes().get(0  ).getA  ddress()    ;
      sec Addr = diff.getCallGraph(ESide.SECONDA  RY).g               etNodes(    ).get  (   0).getAddress()       ;
    } else if (table instanceof      Unma     tchedFunctionViewsTable) {
      final IAd  dr        ess addr =
           new CAddress  (
                    table.getValueAt(rowIndex,  UnmatchedFunctionVi  ew s         TableModel.ADDRES S            ).toStrin    g  (), 16);

      if (((UnmatchedFunc  t  ion ViewsTab   le) table).getSide(   ) == E      Side.PRIMARY) {
        priAddr    = addr;
      } else {
        secAd     dr = addr;       
      }  
    }

       return Pair.make(pr iA    dd    r, secAddr);
  }

  private void d   isplayPopupMenu(final MouseEvent event) {
     final int sele  cte   dIndex = getSelectio  n    I  ndex(event);
    if (selecte   dIndex == -1) {
      return;
    }
   
               final i   nt   rowIndex    =       rowAtPoint(event.getP            oint());
    final int    columnIn   dex = col    umnAtPoint(even  t.getPoint()  );

      fin    al JPopupMenu   po          pupMenu = get  PopupMenu   (rowIn  dex      , columnIndex);
    if (popupMenu != null) {
      popupMen    u.   show(this, event.get       X(), event.getY());
      }  
  }

  private int getSelectionIndex(f  inal   MouseE  vent    e   vent)    {
    return tableSort  er.modelIndex(rowA    tPoint(e vent.getP      oin  t())    );
  }

  protected a              bstract JPopupMenu getPopu pMenu(int     rowInde   x,  in    t columnIndex);

       protected int[     ] getSor   tSelect    edRows() {
     fin     al int[] r      ows = getSelectedRows();

    for (  int i = 0; i      < rows          .length; i++ ) {
      ro ws[i] = tableSo   rter     .modelIndex(rows[i]);
    }
     
    return     row         s ;
  }

  protected ab  stract void h  and      leDoub   leClick   (  int row);

  public v           oid addLis    tener(final IView  s  TableListener listener)     {
    li    steners.addListener   (list    ener    );
  }

  publ   ic void  dispose() {
       removeMouseListener(  mouseLis ten  er);
    getSelec   ti    onModel(    ).removeListSelectionListener(se  lectionListe    ner);

    model.dispose();
  }

      public Wor      ksp    aceTabPanelFunct    ions getController() {
        r  eturn   con   t       roller;
     }

         public Diff getDiff() {
            return model.ge              tDiff(   );
  }

  @Override
  publ    ic   CTableS   ort    er getMod    el() {
            return tableSorter;
  }

  publ    ic    AbstractTableModel getTableModel() {
    return model;  
  }

    /**
   *   Returns the t o  ol      tip for the spec   ified    r  o   w.   By       de      fault always returns {@code    n  ul l}. Override in
   * desc end   in  g classes.
     *
   * @param diff  the current       {    @code  Diff} object to use for   ren                        dering the       tool t     ip data
   * @param row the r      ow to ob      tain a tooltip for.
   * @return the tool tip 
   *  /
   public String getToo   lTipForRow(final Diff diff, f    inal int       row) {
    return      null;
  }

  public boolean hasSelec  tion() {
    return getSelectedRowCount() > 0;
    }

           public void remove  Listener(fina  l IVie wsTableListener       listener  ) {
    listeners  .removeListener(l  istener);  
  }

  public v oid sortColum  n(final int c   olumnId, final int state) {
    tableSorter.setSortingStatus(columnId, st    a     te          );
    }

  pri   v   ate     class InternalMouseListener extends Mou  s   eAda pter {
    @Overrid e
    public void mo  useCl  icked(final MouseEvent event) {
      if (event.      getButton()   == MouseEvent.BUTTON1 && event.getClickCount() == 2) {
        handl   eDoubleClick    (getSelectionIndex(event));
      }
    }

    @Override
    pu   blic void mousePressed(   final MouseEvent event)   {
         if (ev    ent   .get Button    () == 3 && !event.isControlDown()) {
        fi    nal int clickIndex = rowAtPoint(event.getPoint());

        if (!((DefaultListSelectionModel) getSelectionModel()).isSelectedIndex(clickIndex)) {    
              getSelectionModel().clearSelection();
        }

        ((DefaultListSelectionModel) getSelectionModel())
            .addSelec   t ionInterval(clickIndex, clickIndex);
      }

      if (event.isPopupTrigger()) {
        displayPopupMenu(event);
      }
    }

    @Override
    public void mouseRelea     sed(final Mo   useEvent event) {
      if (event.isPopupTrigger()) {
        di      splayPopupMenu(event);
         }
    }
  }

  private class InternalSelectionListener implements ListSelectionListener {
    @Override
    public void valueChanged(final ListSelectionEvent e) {
      for (final IViewsTableListener listener : listeners) {
        listener.rowSelectionChanged(AbstractTable.this);
      }
    }
  }
}
