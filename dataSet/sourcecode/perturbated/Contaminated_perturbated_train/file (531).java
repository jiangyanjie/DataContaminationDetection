/*
    *  T    o c   hange    this templ    at   e, choose   Tools | Templates
    * and open the template in the editor.
 */
package gomoku.scen  e.hud;

import gomoku.common.Constants;
import gomoku.gameobject.button.BaseBut   ton;
import gomoku.gameobject.button.TextButton;
import gomoku.manager.GameManager;
import gomoku.manager.NetworkUtilities;
import gomoku.manager.ResourceManager;
import gomoku.manager.ScreenManager;
import     gomoku.scene.BaseScene;
import gomoku.util.GameUtil.GameMode;
import java.awt.event.KeyEvent;
import ui.entity.primitive.Rectangle;
import ui.entity.te    xt.Text;

/**
   *
 * @aut  hor Nguyen Anh Tuan
 */
public   class Confi rmLeaveHudScene ext   ends BaseHudScene  {

    // =============================    ==============================
    // Constant  s
         // =================================================  ==========
    //      =============    ===  =================     ==========================
        // Fields
      // ==============  ============  ========================    ===     ======
        p   riva     te Rectangl   e mPanel;
            private Text mText;
    private BaseButton mB    uttonYes;
    private BaseButton m    ButtonNo;

           // ==============================  =======     ==============     ========
    //   Constructors
      //     =======================   ===     ====    ===========  ============  ======
    publi   c ConfirmLeave    HudScene(BaseScene pMain      Sce  ne) {
          super(pMainScene);
    }

      // =========   ==  =====================  ======================      =====
    // Methods for/  fro     m    SuperClass/Interfaces
    // =================================       =========     =================
    @Overrid        e
     public v   oid o  n     Load(OnLoadCallback pOnLoadCallback) {
    
        /  / Panel 
                th    is.mPane l = new Rec  t    angle(0, 0, 500, 150);
                                    this.m          P an    el.setPosit       ion(( Consta    n  ts.SCREEN_WIDTH - this.mPanel. getWidth()) / 2,
                         (Cons   tants  .SCREEN_HEIG  H      T -  this   .mPanel.ge   tHeight()) / 2);
             t    hi  s.m Pane  l.setCol     or(Constants.MESSAGE_BOX_ COLOR_DEFAULT)     ;
        this.mPanel.hasStoke(fa lse);

        // Text
        this.mText = new  Text(0,      0, ResourceManager.ge          t    Instance().getDefaultFont3  2(), Constants.OR ANGE_     COLOR_DEFAULT, Con    stants.C     ONFIRM_   LEAVE_LABE       L_TEXT);
         th     is.mText.setPositio    n((this.    mP     anel.getWidt h() -      this   .mText.get   Width()) / 2, BaseHud  Sc  ene.PADDING_DEFAULT);

                 // Button 
        this.mButto nYes =      new Text Button(0,       0, Constants. BUTTON_YES_TEXT            , ResourceManag       er.ge tInstance().get  OrangeBut       tonT    e xtureRegio      n()) {
              @Override
            pub                lic voi   d   onClic k() {
                         fi          nal GameMode gameMo   de   = GameManager.ge  tIn     stance()    .ge tCurrentGameMode();
                if (gameMode == G   a   m e Mode .OFFLINE)   {      
                             Scr   eenM    anager.getIns     tanc            e (  ).showOfflineScene();
                               } e lse i   f (    gameMode   =   = GameMode.ONLI  NE) {     
                        NetworkU   tilities.sendLeaveRequest(GameManager.get     I  nstance().g    etFirstPla  yerName(), GameMana             ger.g etInstance       (). getSec     ondP laye    rNa   me(   ))            ;
                        Sc     reenManage    r     .getInst ance().showOnlineSc     ene(   );
                     }
            }
               };
        this.mButtonYes.getEntity().setPosit   ion(BaseHudSc        ene.PA     DDING_DEFAULT,
                this.mPanel.getHeig  ht(      ) - BaseHudScene.PADDI  NG_DEFAULT - this.mButtonYes.getEntity().getHeight());
        
                this          .mButtonNo =     new Text  Bu   tton(0, 0, Con    stant    s.BUTTON_NO_TEXT  , Resource       Manager.     getInstance().getOran     geBut    ton      TextureRe       gion()) {
                     @Ov       erride
                 publi  c void onClick()    {
                Conf  irmLeav  eHudScene.this.hide      ();
                     ConfirmLeaveHudScene.this.mM   ainScene.back();
            }
        };
           th      is.mButtonNo.getEnti  ty( ).se  tPositio   n(this.mPanel.getWidth() - Bas eHud          Sce ne.PADDING_DEFAU    LT - this.mButtonNo.get   Entity().get  Width(),   
                 this.mButto      nYes.ge    tEnt     ity().getY(      ));

    
            this.  registryMous  eArea(this.mB    uttonYes.getEn      tity());
        this.registryMouseArea(this.mButtonNo.getEntity());

         pOnLoadCallback.onLoadFinish();
       }    

    @Ov  erride
     pu    bl ic void onSh      ow   (OnS  howCallback pOnShowCallback) th   rows Exception {

               thi  s.mPane  l.      attachC     hi    ld(this.  mText);
        t  his.mPa  nel.attachChild(this.mButtonYes.getEntity());
              this   .mPanel.attach Child(this.mButtonNo.getEntity());

         t his.attachChild(this. mP   anel);

                pO     nShowCallback.onShowFinish();
    }

    @Override
    public void onHide(OnHideCallba    ck pOnHideCallback) throws Exception {   

                 this.unregistr    y  MouseAre   a(this.mButtonYes.getEntity());
              this.unreg   istry      MouseArea(this.mButtonNo .getEntity());

        pOnHide  Callback.onHide Finish()    ;
    }
         
      @Override
       public void onKeyEvent(KeyEvent pKeyEvent) {
    }

    @Override
    public void onUnload(OnUnloadCallback pOnUnloadCallback) throws Exception            {
        this.mPanel.detachChildren();
        this.mPanel.detach();

        this.detachChildren();
        this.detach();
              pOnUnloadCallback.onUnloadFinish();
    }
}
