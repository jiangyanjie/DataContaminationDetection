/*
      *      Copyright  20      24 XIN LIN HOU<hxl49508@gmail.com>
 * cURLList    ener.jav a is part of Cool Re    quest
 *
 *       License:   G     PL-3.0+
 *
 * Cool Request  is free software: you can redis tribute    it and/or    modif   y
 * it un    der         the terms    o   f the  GNU General Public License a  s published    by
 * the        Free Soft     ware Foundati  on, eith     er ve  rsi on 3 of the License, or
 *          (         at your option) a     n  y later version.
 *
 * Coo  l Request is d    istribu ted in the hope that it will be us    eful     ,
 * but WI  THO             U     T ANY WAR     RAN      T         Y; without even the implied   warranty of
 * MERCHAN     TABILITY       or FITNESS FOR A PAR TICULAR PURPOS   E.  Se   e the
 * GNU G eneral Public License for more d   eta   ils.
 *
 * You should have re     ceived a   copy  of the GNU Gen     eral Public Lice    nse
 * along with Cool Request.  If n   ot, see <http://www.gnu.org/licenses/>.
 */

package com.cool.request.common.listener    .component;

import com.cool.request.common.icons.CoolRequestIcons;
import com.cool.request.common.service.ClipboardService;
impo          rt com.cool.request.common.state.SettingPersistentState;
import com.cool.request.compo nents.CoolRequestContext;
import com.cool.request.utils.ClipboardUtils;
import com.cool.request.utils.Messag   esWrapperUtils;
import com.cool.request.utils.ResourceBundleUtils;
import com.cool.request.utils.StringUtils;
import com.cool.request.view.    main.IRequestParamManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.FocusWatcher  ;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import j ava                  .awt.event.WindowEvent;

import static com.coo    l.request.common.  constant.Co  o   lRequestCo n    fi  gCon stant.PL       UGIN_ID;

/*        *
 * curlçå   ¬å¨
 */
  public    cl       ass    cURLList      e      ner extends FocusWatcher {
    private Project p   ro           ject;
    private String lastContent;
    pri    vate     C    om ponen         t co   mponent;   

    public cUR   LListener(P   ro   ject project, C     omponent componen t)     {
        this.project = project;
        this.component = compo  nent;
    }     

    @Override
    protected void    fo  cusedComponent     Changed(@Null        able Component focusedCompo      nent, @Nullabl      e AWTEvent cau      se) {
        super.foc  usedCompone   ntChanged(component, cause  ); 
            i   f (focusedComponen    t != nu     ll &&    SwingUtilities.isDescending     From(f  ocus   edComponent, comp     onent    )) {
                             if (!Sett       ingPersistentState.getIns  ta  nce(   ).getState().listene         rCUR L) return;
               S  tring newCon    tent =   ClipboardUtils.  getClipboard    T  e      xt();
            if (      newContent != null && (!newCont ent.equa  ls(lastContent    ))) {
                            if (StringUti ls.i s    EqualsI   gnoreCas  e(Clipboar    d     Se     rvice.getInstan        c e().   getCurlData(),                  newConte  nt      )         ) retur   n        ;
                   if (S    tringUtils.is     StartWithIgnoreSpace(ne  wC    ontent,   "c      url")) {
                       IRequestParamMan   ager ma inRe  questParam      Manager = CoolRequestContext.getInsta  nce( project).   g    etMainRequest     ParamManager();
                    Mes   sage    sWrapper     Utils.showOkCancelDialog(Res  ource        Bund leUtils.getString(    "import.c   url. tip.auto"),
                            ResourceBundleU       tils.getSt   ring(      "tip"), C           oolRequestI     cons.MAIN, int  ege r -> {
                                         if (0 ==  integer) mainRequestParamManag   er.             importCurl(newConte nt);
                                });
                }
               }
            lastContent = ClipboardUtils.g etClipboardText();
        }
    }

}
