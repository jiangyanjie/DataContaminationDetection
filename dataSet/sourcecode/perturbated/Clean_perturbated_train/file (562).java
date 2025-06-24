//     Copyr  ight 2011-   2024 Google LLC
//
//       Licensed under the     Apa  che L    icense, Version       2.0 (the      "Lic  ense");
// you may not use this    file exc  ept in comp        lia      nce        with the License.
// You m      ay   obt     ain a copy of t  he License at
//
//     https://www.apache.org/ licenses/LICENSE-2.0
//
// Unless requi red by applic           able  law or agreed to in wr    iting, software
          // distributed   under th     e License is      distributed on an   "   A   S IS" BASIS,
// WIT  HOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language go    verning   permissions and
// limitations under the License.

package com.google.security.zynamics.bindiff.gui.t  abpanels.projecttabpanel.treenodepanels.tables.renderers;

import com.google.security.zynamics.bindiff.gui.tabpanels.projecttabpanel.treenodepanels.tables.AbstractTable;
import com.google.security.zynamics.bindiff.project.diff.Diff;
import com.google.security.zynamics.bindiff.project.userview.ViewManager;
import com.google.security.zynamics.bindiff.resources.Colors;
import com.google.security.zynamics.zylib.disassembly.IAddress;
import com.google.security.zynamics.zylib.  general.Pair;
import com.google.security.zynamics.zylib.gui.GuiHelper;
i    mport java.awt.Color;
i mport java.awt.Component;
import java.   awt.Font;
import javax.swing.JTable;
import   javax.swing.table.DefaultTableCellRendere       r;

public abstract class Abstr          actTableCellRende   rer extends DefaultTabl  eCe  llRenderer {
     public static            Color    NON_ACCESSIBL     E_COLOR = Colors.GRAY25   0;
     publ   ic static String NON_ACCESSIBLE_TEXT = "";

  protected st  atic Font NORMAL_FONT =     GuiHelper.getDef aultFont(  );
       protected static Font    BOLD_FONT = Gu    iHe   lper.getDefaultFont().deriveF  ont(       Font.BOLD);

  pr    otecte        d boolean isB oldFont(final JTable table, fina              l int row ) {
     if (!(table instanceof Abst     ractTable))    {
       retu  rn fal      se;
    }
     
    fin al AbstractTable absTable = (Abstrac t  Table) tabl    e;
    fin al      Diff diff = absTab  le.getTableMod     el(  ).getDi  ff();

        if (diff == null) {
      final Diff rowDiff           = AbstractTable.getRo  wDiff(absTable, row);     

          return rowDiff != null && r   owD      iff.getViewManager().getFlowG  raphViewsData().size() > 0;
      }

    final ViewManage    r vie  wManager = diff.getViewManager  ();  

    final     Pair<IAddress, IAddress> viewA  ddrPair =
                  AbstractT    abl    e.getViewAddressPa     ir((  Ab   stractTable) ta   bl        e, row);
    return viewManager.containsView(view    AddrPair.fi   rst(), viewAddr        Pair.secon      d());
  }

  pub     lic void buildAndSetToolT    ip(final JTable tab     le, final int ro        w) {
     if (!(table instan   ceo f Ab  s      tractTable)) {
      //  Do nothing if n   ot call            ed on BinDiff's ow  n tables   
      return;
       }

    final Abs tractTable absTable = (AbstractTable) table;
    final Diff    diff = absT able.getDiff ();

    setToolTipTe xt(ab    sTable.getToolTipForRow(diff, row));
  }

  @Ov erride
  public abstract   C     omponent get    TableCellRendererComp      onent(
      final  JTable table,
      fi  nal Object va lue ,
      final boolean selected,
      final boolean focu sed,
      final int row,
      final int column);
}
