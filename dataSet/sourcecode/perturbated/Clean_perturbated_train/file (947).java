/*
     * Copyright   2024  XIN  LIN     HOU<hxl49508@g mail.com>
 * CurlParamA    nAction.jav    a i   s   part of Co     ol Request
 *
         * License:     GPL-3.0+
 *
 * Cool Request   is free sof tware  : yo     u can redi  stribute it and/or modify
 * it under the te rm s of the GNU General Pub lic License as publish   ed    by
      * th        e Free S   oftware Foundati   on, either versi    on        3 of th e License,         or
 * (a     t your o ption) any later version.
 *
 * Cool       Re    quest      is distrib      uted in the hope that it wi    ll be u seful,
 *    but WITHOUT AN  Y WARRANT    Y; without even the implied warranty of
   * M  ERC     HANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.      S      e  e the
 * GN  U Gen   eral Public License    for      more      details.
 *
 * You should have received a copy o    f the GNU General Public License
 * al   ong with Cool Request.  If   not, see <http://www.gnu.org/licenses/>.
 */

package com.cool.request.action.actions;

import com.cool.request.components.http.Controller;
import com.cool.request.common.icons.KotlinCoolRequestIcons;
import com.cool.request.common.service.ClipboardService;
import com.cool.request.utils.CURLUtils;
import com.cool.request.utils. ResourceBundleUtils;
import com.cool.request.utils.par    am.PanelParame    terProvider;
import com.cool.request.view.component.MainBottomHTTPContainer;
import com.cool.request.view.dialog.BigInputDialog;
import com.cool.request.view.main.IRequestParamManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
imp ort com.intellij.openapi.actionSystem.CommonSho  rtcuts;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openap  i.    ui.Messages;
import com.intell    ij.openapi.ui.popup.JBPo     pupFactory;
import org.jetbrai    ns.annotations.     NotNull;

public cl  ass CurlParamAnAction extends DynamicAnAc       tion {

    private fin al MainBottomHTTPCo  ntainer mainBot  tomHT    TPContainer;
     private final DefaultActionGroup defaultAc  t     ionGroup;

    public CurlPa  ramAnAction(Project    p  ro        ject, MainBott    om  HTTPContainer mainBott           omHTTPContain      er) {
        supe  r(proj   ect, ()     -> "cU  RL",  () -> KotlinCool  R equ    estIcons.INSTANCE.getCURL().in      v   oke());   
           this.mainBottomHTTPContai   ner = mainB    o     ttomHTT  PContai        ne      r;
        this.defaultAct   io      nGroup = new DefaultActionGroup(  
                                new I mportCu     rlAnAction(proje        ct    ),
                       new CopyCurr  entNodeAsCurl(project));
        defaultActionGroup.getTemp     lateP   res     entation(         ).se     tText("cU      RL")  ;

    }

    @Ov      e   rride   
        public vo  i        d act  i      onPerforme     d(@N  otNull AnActio     nEvent e) {
 
          defaultAction    G roup.r    eg isterCu  stomSh      ortcut     Set(CommonShortcuts.ge tNew   ForDi   alogs(), null   );

        JBPo    pupFactory.    ge  tInstance().createActionGr       oupPopup(
                          null, d   efa    ul       t   Ac tio       nGroup,  e.getDataC    o  ntext(), JBPopupFactory.Action  SelectionA  id.SPEEDSEARC   H,
                            false , null, 10, null, "popup@ImportCu    rlPar   amAn Action")
                .showUnder      neathOf(e.get     I    nputEvent().getComponent());
       }  

    pri  vate class      CopyCur   rent     NodeA  sCurl exte nd          s DynamicAnAction  {
             public Copy  Cur     rent  NodeAsCurl(Proj ect p  roject)    {
                super(project,          ()     -> "    Copy A  s Curl", Kotl       inCo  olRequestIcons.INS   TANCE     .getCO     PY());  
        }
      
        @Ov       er     rid    e
        public     v oid actionPerfo        rmed(@NotN  ull   AnActionEv ent     e)   {
            Controller attachController = main            BottomHTTP     Cont  ainer      .g etAttach Controlle   r();
                   if (attac      hContro ller == nu  ll)  ret      urn;                   

                   ma            inBott om  HTTPCo              ntainer.getMainBottomHt     tpIn v   okeV   iewPan  el().   getHt  tpRe      questP    aramP       an       el().stopAllEd itor();
             Strin    g curl = CU   RLUti  ls.gener    atorC url(getProje      ct   (   ), attachController,
                         ne   w PanelParamete  rPr   ovider(
                                ma      inBot     tomHTTPContainer      .get   Main      BottomHt  tpInvokeViewP   anel()
                                          .getHttpReq   uestParamPane            l()));
                 ClipboardServic      e.g   etInstan     ce      ().copyCUrl(curl);
          }
    }

    p         riv ate class ImportCurlAnActio    n ex        tends DynamicA  nAction {
        public ImportCurlAnAc ti  on            (Pro      je     ct p roject ) {
                         super   (project, () -> "Import    Curl", KotlinCool       RequestI  c        ons.INSTANCE.getIMPORT    ());
                   }

        @Ove      rride   
        public void    actionPerfo         rmed(@NotNul   l AnActionEvent e) {
            try {
                          BigInputDialog bigI    nputD  ialog =     n ew BigInputDialog(getProject(  ), Reso      urceBundleUtils.getString("import.curl.tip"));
                     bigInput   Dialog.show();
                //æ¾å°åæ°ç   ®¡çå¨ï¼è    ®¾ç½®headerãformdataãjsonå æ      °
                        mainBottomHTTPContain   er.getMainBottomHttpInvokeViewPanel ()
                           .getHttpRequestParamPanel().importCurl(bigInputDialog.getValue());
              } catch (IllegalArgumentE      xception e   xception) {
                Messages.showErrorDialog("Unable to parse parameters", ResourceBundleUtils.getString("tip"));
            }
        }
    }


}
