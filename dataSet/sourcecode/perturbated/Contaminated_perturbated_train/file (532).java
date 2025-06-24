/*
 * To change this template, choose   Too  ls | Templates
    *   a  nd open the template in the editor.
 */
package gomoku.scene.hud;

import gomoku.common.Consta   nts;
import gomoku.gameobject.button.BaseButton;
import gomoku.gameobject.button.TextButton;
impo    rt gomoku.manager.GameManager;
import gomoku.manager.NetworkUtilities;
import gomoku.manager.ResourceManage      r;
import gomoku.manager.ScreenManager;
import gomoku.scene.BaseScene;
import gomoku.util.GameUtil;
import ui.entity.primitive.Rectangle;
impo   rt ui.entity.text.Text;

/*    *
 *
    * @author Ng  uyen Anh Tuan
 */
public class ConfirmLogout     HudS  cene extends BaseHu    d  Scene {     

    // ======================= == ==============================   =   ==   =
    // Fields
    //        ===================================    ==   =    ================  =====
    p  riva   te    Rectangl   e mPanel           ;
     p   rivate Text    mText;
    priva       te BaseButton mButtonYes;
        privat  e Bas    eButton mButtonNo;

    // ====     ==============================================           =========
    // Contru   ctors
    // =====  ====================     ===     ============    ======     =============
    publ      ic ConfirmLogoutHudS    cen   e(final BaseScene       pMainScene) {
               super(pMainScene);
    }

    /  / = ========================================== ================
             // M    et   hods for/from   SuperClass/I   nte  rfaces
           //            =====  ==== ======================    =====================   =  ===== =
      @Overrid  e
       public voi  d onLoad(OnLoadCallba     ck p     On    Load    C      al   lback) throws Ex   c     eption {

            /  / Panel
        this.mPan  el =    new R   ectangle(0  ,    0, 650, 15    0);
        t his.mPane  l.setPosition((Constants.SC     REEN_WIDT     H - this.mPanel  .getWidth()) / 2,
                (Const   ants.SCRE    EN_HEIGHT - this.mPanel.getHeight()) / 2);
        this.mPanel.   se   tColor  (Constants.MESSAGE_BO  X_C     OLOR_DEFAULT);
            thi  s.mPanel  .hasStoke(false  );

                   /   / Text
        this.mText = new Text(0, 0, ResourceManager.getInstance().  g    etDefaultFont    32(), Constants.ORANGE _C     OLOR_D      EFAULT, Constants.CONFIRM_LOGOUT_LABEL_T   E   XT);
        this.mText.se  tPosition((t  his.mPan     el. getWid  th() -  th is.mText.   g   etWidth()) /       2, BaseHudScene.PADDING_      DEFAULT);

                        // Button
            this.mButton        Yes = new Tex tButton(0, 0, C   on stants.BUTTON_YES_TEXT, Re    sourc eMan    age   r.g   e      tInstance() .getOr    angeButton   TextureRegion()) {   
                            @Ove     r    r i   de
               publi c void onC lick         () {
                          Con      firmLogoutHu dScene.this.hide();
                    ConfirmLogoutHudSc  en    e.this.m     MainSce  ne.b  ack() ;
                  if (GameManager.get  Ins  tance().ge            tCur     rentGameMo  d    e()               ==    GameUtil.GameM    ode.ONLINE) {
                                      NetworkU  tilit    ies.sendLogoutRequest(Game  Ma nager.getInstance().getF   irstPlayerNam   e());
                }   
                   ScreenManager   .getInstance       ().showLoginSc   ene();
            }
        };
                  thi s.mButto    nYes     .getE ntity().setPositi    on(BaseHudScene.PADDING_D   EF   AULT,
                                            this.mPanel.getHeight() - BaseHudScene.       PADDING_      DEFAULT - this.mButtonYes     .getEntity().getHeight());

          this.mButtonNo = new TextButton(0, 0, Co  nstants.BUTT ON  _  NO_TEXT, Re     sourceManager.getInstance  ().getO   r        a     ngeBu    tt         onTextureRegion())   {
                 @  Over    ride
            publ       ic void onCl          ick() {
                  C  onfirm   Lo     gou   tHud Scene.this.hide();
                     Co    nfirmLog    outHudSc ene.this.mMainScene.ba    c   k();
                    }
               };
           this.mB uttonN    o.getE         ntity ().setPo sition(this.mPanel.getWidth () - B   aseHud  Scene   .PADD     ING    _DEFAULT - this.mButtonNo.getEnti      ty().getWidth(),
                           this   .    mButtonYes.   g   et    Entity().getY(   ));


        this.registryMouseArea (this.mBut            tonYes.getEntity(    ));
             this.registr       yMouse  Area(this.mB    uttonNo.getEntity());

        pOnLoadCallback.      onLoadFinish  ();
    }

       @Override
    public void onShow(OnShow      Callback pOnShowC  allback) t    hrows E   xception   {
        this   .   mPanel.attachC   hild(thi    s.mText);
        this.mPanel.a       ttachChild   (thi       s.mButtonYes.getEntity());
        this.mPanel.attac hChild(this.mButtonNo.getE        ntity());

                      this.attachChild(this.mPanel);

        pOnShowCallbac    k.onShowFinish();
    }

    @Overri de
     p   ublic v   oid onHide(OnHideCallback pOnHideCall   back) throws Exception {

        this.unregistryMouseArea(this.mButtonY   es.getEntity());
        this  .unregistryMo  u     seArea(this.mButtonNo.getEntity());

        pOnHideCa   llback.o    nHid   eFinish(   );
    }

    @Override
       public void onU nload(OnUnloadCallback pOnUnloadCallback) throws Exception {

        t    his.mPanel.detachChildren(     );
        this.mPanel.detach();

        this.detachChildren();
        this.detach();

        pOnUnloadCallback.onUnloadFinish();
    }
}
