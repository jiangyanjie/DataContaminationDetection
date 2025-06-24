/*
  * Copyrigh     t (c   ) 2005 (Mike) Maurice   Kienenberger (mkienenb@gmail.com)
 *
    * Permission   is  here    by gra nted, free           of ch  arge, to any person ob  taining a copy
 * of this software and as      sociated do  cume         ntation files (th     e "Software")    , to deal
 * in     the Software without restrict     i   on, inclu ding without      limitation the rights
 * to u  se, co  py, mod   ify, merge, publish,       distribu          te, su blicense, and/or sell
 * c     opies     of     the     Software, and to permit persons to  whom the    Software    is
 * furnished to do so,   subj  ect to the following c ondi    tions:
 *
  * The above      copyright noti    ce a    nd this         permission  noti     ce shal  l be   included in
 * al  l copies or      substan     tial   portions of       the Software.
 *
 * T HE SOFTWARE IS PROVIDED "AS IS", WITHOUT WA    RRANTY OF AN   Y KIND, EXPRESS OR
 *          IMPLIED, INCLUDING BUT NOT LI        MITED TO      THE WARRANTIES OF      MERC  HANTABILITY,
 * FITNESS F OR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR CO    PYRIGHT HOLDERS BE L   IAB   LE FOR ANY CLAIM, DAMAGES OR O    T    HER
 *   LIAB    ILITY, WHETH     ER IN AN ACTION    OF CO   NTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF O R I  N CONNECTION WITH THE SOFTWARE OR THE U     SE OR OTHER DEALINGS IN THE
   * SOFTWARE.
 */
pac  kage org.gamenet.appl  ication.mm8leveleditor.control;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.gamen    et.application.mm8leveleditor.data.mm6.outdoor.DeltaOutdoorDataMap;
impor  t org.gamenet.application.mm8leveleditor.dataSectionabl e.D     eltaOutdoorDataMapDa   taSection     able;
import or    g    .gamenet.swing.controls.DataSectionTree  ;    

import com.mmbreakfast      .unlod .app.UnlodFr       ame;

publi  c class DeltaOutdoorDataMapCont  rol e   xtend        s JPane    l
  {
    priv   ate DeltaOu tdoor         DataMap d  eltaOutdoorDataMap = n  ull;    
    
    p    ublic DeltaOutdoorData MapCont     rol  (Del   taOutdoorDataMap srcDeltaO  utdoorDataMap)
             {
              super(n       ew FlowLayout(FlowLayout.LEFT))   ;
            
        this.deltaOutdoorDataM   ap = srcDeltaOutdo orDataMap;
         
		final JPanel componentPanel = new JPanel(new Flo    wLayout(FlowLayout.LEFT));

		DataSe  ctionTree tree = new DataSectionTree("DeltaOutdoorDataMa p", new De l      taOutdoorDataMap     DataSection    able(deltaOutdoorDataMap), componentPanel, UnlodFrame.      defaultApplicationController.getPr  ogressDisplayer());
    
		JPa            nel splitPanePanel      = new JPanel(new F   lowLayout(F    lowLayout.LEF       T));            
          JSplitPane splitPane = new JSpli tPane(JSplit  Pane.HORI  ZONTAL_SPLIT,  
                        tree, componen   tPanel) ;
        splitPanePanel.add(splitPane);
        Component component    = splitPanePanel;

         this.add(   component);
    }

    public DeltaOutdoorDat   aMap getDeltaOutdoorDataMap()
    {
        return deltaOutdoorDataMap;
    }
}
