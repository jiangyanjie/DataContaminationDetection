/*
    *   To        change this     template, choo    se Tools | Templ       ates
    *        and open the template in the editor.        
 */
package gomoku.scene.layer;

import gomoku.common.Constants;
import gomoku.gameobject.button.BaseButton;
import gomoku.gameobject.button.TextButton;
import gomoku.mana  ger.GameManager;
import gomoku.manager.Netw    orkUtilities;
import gomoku.manager     .ResourceManager;
import gomoku.manager.ScreenManager;
import gomoku.scene.BaseScene;
import gomoku.scene.hud.BaseHudScen e;
import gomoku.util.GameUtil;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import ui.entity.primitive.Rectangle;
import   ui.ent   i   ty.text.Text;

/** 
 *
      *   @au thor Nguye  n Anh Tuan
 */
public cl     ass Conf irmSendChallengeLayer  ex tends Base   Layer {

    // ============         ====================================  =======      ====
    // F  ields
      // ===================      =========   ==========   ====         =============    ==== 
     
    private Text mL abel;
    private     Text mUserName;
    priv at      e BaseButton mButtonYes;
    p rivate Ba   seButton mButt    onNo     ;
       private Rect       angle mPan     el;
    
    // =======          ==========  =========================================    =
    // Con        tru ctors
    // =    ===============   ================  == =========================
    
    publ    ic ConfirmSendC    hallengeLaye r(final    BaseScene pMainScene) {
                      s    uper (0,  0, C   onstants.SCREEN _WIDTH, Con stants.SCREEN_HEIG  HT, pMainScene);
       }
    
       /  / ===   ==========   ===  ========   =====    ==========    ===     ================= 
    // Fiel          ds
    //          =     ===========   ==       =    =========   ============    ===========     =======              =====
              
          @Override
       p  ublic voi            d   slideIn(        ) {
            
     }

    @Ov   erride
      public           vo      id sl ideOut() {
                   
    }

    @Override
            public vo id o   nMouse     Eve   nt(MouseE   ve nt p MouseEvent, int pMouseAr   eaLocal     X, int pMo         useAre  aLocalY) {
          
          }
  
           @Over      ride
    public void onKe    yEvent(Ke  yEve       nt p    KeyEvent) {
                   
    }
  
    @Over  rid e
    public void on       Loa  d(O nLoadCal  lback pO    nLoadCal   lback) throws Except  ion    {
            
             // Panel
          th is.mP    anel = new Rectangle(0, 0,   600, 150);
                  this.mPanel.se  tPositio   n((C      onstants.SC      REEN_WIDTH - t        his.   mPanel.getW idth()) / 2 , 
                                          (Con       stan   ts.SCREE N_HEIGHT - this.mPanel.getHeight())                     / 2);             
        this.mPa nel.setColor(Constants.ME          SSAGE_BOX _CO LOR     _DEFAULT);
        this.mPanel.hasSt       oke(f   alse     );
           
        // L      abel
        this.mLabel = new Text(0, 0, Res       ource      Man  ag             er.    get Instance(       ).getDefaul  tFont   32(), 
                      Co      nstants.O      RANGE_COLOR_DEFA   ULT, Constants.CONFIRM_SEN D_   CHALLENGE_L            ABEL_TEXT);
           this.mLabel.setPosition((this.mPanel.ge   tW    idth() - th   is.mLab  el .getWidth()) / 2,  BaseLayer.PADD    ING_DEFAULT);
        
        // User  na me
               this.mUs erName =        new Text(0, 0, ResourceManage    r.g   etInstance().getDefault      F     ont32(), 
                Constants.ORAN  GE_COLOR_DE    FAULT, GameManag    er   .getInstance(    )     .getS        econdPlayerName(    ));
          th    is.mUs    erName.setPosition((this    .mPanel.get   Width() -                     thi s.mUserName.getWidt  h()) / 2     ,     
                            this. mLabel.get   Y() + th  is.mLab   el.getHeight() + Base    Layer.PADDI       NG  _DEFAUL T);
        
        // Button
        t   hi    s.mButton   Ye    s = new TextButt  on(   0, 0   , Constants.BUTT   ON_YE           S_TEXT, ResourceM ana         ger.getInstance().   getOr   angeButtonT   extureRegion ())        {

            @O verride
                    public     void onCl   ick() {
                Ga  meManager.g  etI     nstance().set Cur  rentPlayer(Game  Ut  il.Player.HUMAN);
                     GameManag  er   .g   etI        nstance().setM  yPiece(  GameUtil.Piece.WHITE   );
                        Networ kUtiliti  es.sendC                ha   llengeRequest(Ga   meManag    er.getInstanc  e().getFirstP      layerName(), Gam   eManag  er.get             In       stance().   getSe    condPlayerName(     ));
                  ConfirmSendChallengeLayer.t  his.hide();
                     Scre   enManager.getInstance()     .   showLoad  ingLayer();
            }    
        };
         thi     s.mBu     ttonYe s.getEntity().setPositio    n(BaseLayer  .PADDING_DEFAULT, 
                                     (this    .mPanel.getHeight() - BaseLayer.PADDING_DEFA  ULT - thi   s.mBu   ttonY      es.   getEntit      y().ge   tHeight()));
         
        this.mB u  tton  No = new TextButton(0, 0, Cons      tants.BUTTON_NO_TE   XT, ResourceManager   .   getInstance().getOrangeButt    onTextureRegion()) {

                  @Override
              publ ic void         onC   li   ck() {
                                       S   cr  eenManager.getInst       ance().showUs  erListLayer();
               }
                   }       ;
            
        this.mButton    No.ge tEntity().set   P         osition(this.mPa       ne        l.getWidth()     - BaseL     a      y    er.PADDING_D    EFAULT -     this.mButtonNo.getEntity().getWidth(),  
                this.mButtonY       e  s.getEntity().getY      (     ))   ;
        
          /  / Register
        this.mMai  nScene.re gistryMou seArea(this.mBu    ttonYes.getEntity());
           th  is.mMainScene.r      egist  ryMouse  Ar   ea(this.  mButtonNo.getEntity());
         
        // Set p os   i t    io  n
           this.mEntity.setPosition((Const  ants.SCREEN_WIDTH - this.mEntity.ge   tWidth()) /   2, (Constants.SCREEN_HEIGH  T - this.     mEntity.getHeig    ht()) / 2);
        
                 pOnLoadCallback.onLoadFinish();
    }

    @Override
                public   void onShow(OnShowCal      lback pOnShowCallback) throws Exception {
        
           this.mPanel.attachChild(       this.mL abel);
        this.mPanel.attachChil   d(this.mUserName);
         this.mPanel.attachChild(this.mButtonYes.getEntity());
        th   is.mPanel.attachChild(th     is.mButtonNo.getEnt   ity());
             
        this.mEntity.attachChild(this.mPanel);
           
        this.mMainScene.attachChild(this.mEntity);
        
        supe    r.onShow(pOnShowCallback);
       }
    
    
    @Override
    public void onHide(OnHideCallback    pOnHideCallback) throws Exception {
        
        this.mMainScene.unregistryMouseArea(this.mButtonYes.getEntity());
        this.mMainScene.unregistryMouseArea(this.mButtonNo.getEntity());
        
        super.onHide(pOnHideCallback);
    }

}
