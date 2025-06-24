/*
 *    Copyri    ght 2024   XIN LIN HOU<hxl49508@gmai     l.com>
 * CreateNewFolder     Acti  on.java  is part of Cool R   equest
 *     
  *             License: GPL-3.0+
 *
 * Cool Request is fre  e softw          are: you can redistribut  e    it and/o   r modify
 * it under the terms o   f the GNU G   e neral Public Li     c     ense as published by  
 * the   Free Soft  ware Foundation,              either version 3 of the License, o r
 * (at your opti  on)     any later version.
 *
    *        Cool Request is distr  ibuted in the       hope t h    at it will be     usef  ul,
 * but WITHOUT AN     Y WARRANTY; without even the implied warranty of
 * MERC  HANTABILITY or FITNESS         FOR A P  ARTICUL AR PURPOSE.  See th   e
 * GNU Ge  neral Public License for      more details.   
 *
   * You should    have   received a co    py o   f the GNU General Public License
 * along with Cool Request  .   If not, see <http://www.gnu.org/licenses/>.
 */

package com.cool.request.pl  ugin.apifox;

import com.cool.request.utils.Gson    Utils;
impo  rt com.cool.request.utils.MessagesWrapper  Utils;
import com.cool.request.utils       .ResourceBundleUtils;
import     com.cool.request.utils.StringUtils;
import com .intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
im      port com.intellij.openapi.ui.Messages;
import com.intellij.ui.treeStructure.SimpleTree;
impo    rt com.intellij.util.ui.tree.Tr      eeUtil;
import org.jetbrains.a       nnotations.NotNull;

impo  rt javax.s    w i   ng.tree.    TreeP  ath;
imp  ort java.util.Map;

public class Cr      eateNewFolderActi        on  extends AnAction {
    pri  vate final ApifoxAPI a   pifo  xAPI;
    private final SimpleTree simpleTree;
    priv  ate fi   nal RefreshCallback    ca            l    lback;

       @FunctionalI   nterface
    public interface   RefreshCallb   ack {
        void a      ddNewFolder(Apif     oxProjec          tFolderSelectDialog.F older                      TreeNode f  o   lderTreeNode, Apifox   Folder.Fol      de    r fold   e   r);
      }
  
       public CreateNewFolde       rAction(ApifoxAPI a  pifoxA      PI, SimpleT   ree     si   mpleTree, RefreshCallback ca   llback)              {
                       super(   "New       Folder");
        this.apifo  xA PI = apifoxAPI; 
        this.si     mpleTree = simpleTree;
        this   .call   back = callback        ;
    }

    @Override
       public void actionPer formed   (@NotNull AnActio   nEvent e   ) {
        String result = Messages.showInp    utDialog("Input     name", ResourceBundleUtils.getString("t    ip"), AllIcons.Act  ions      .Edit);  
        if (! StringUtils.isEmpty       (result)) {
                  T re      ePath sele     ct   edPathIfOn  e    = TreeUtil.getSele       ctedPathIfOne (this.simpleTree);
                   if (selec    tedPathIfO     ne !  = null && selectedPathIfO   ne.getLastPathC     ompon  e   nt() instanceof    ApifoxProje   ctFolderSelectDialog.Fo   lderTreeNode       ) {       
                     int id = ((ApifoxProjectFolde rSelectDia      lo g.Fo        lderTreeNode) sel   e   ctedPa  thIfO  ne.getLastPathComponent()).g   et Folder ().getId  ();
                  new T   hre      ad(       () -> {
                        int pr           ojectId =   ((ApifoxProjectFold     erSe      lectDi              alog.Fo   l   derTreeNode) sele     ct     e    dPathIfOne. getLastPathC   omponent   ()).getFolder  ().getProjectI             d   ();
                                   Map<String, Obj   ect> createR      esult = api   fo   xAPI.createNew Fold erA  nd Get(id, result, projectId  );
                           if (createResult.getOrDefa ult(  "succes                    s", false).equals(Boolean.T          RUE)  ) {
                                            S      t    r   ing data    =       GsonUt ils.toJsonString(createResult.get("da      ta"));
                           ApifoxFolder.Folder folde   r = G    sonUtils.readValue(data, ApifoxFolde   r.   Folder.class);
                                        callba ck.addNewFolder(((ApifoxProjectFolde   rSel ectDialog.FolderTr    eeNode) se               le        cted    P    a     thIfOne. getL    a        s       tPathComponent()), folder)    ;
                              } else {
                               MessagesWrapp  erUtils.sh  owErrorDialog("Create Fail:" + createResult.   getOrDefaul  t("errorMessage",    ""),  ResourceBundleUtils.get String("tip"));
                       }
                  }).start();
            } else {
                   M     essages.showErrorDialog("Creation failed, unable to create direc tory on this node", "æç¤º");
            }
         }
    }
}
