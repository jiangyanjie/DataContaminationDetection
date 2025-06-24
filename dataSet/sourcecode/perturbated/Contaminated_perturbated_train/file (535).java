/*
    * To cha   nge this template, cho    ose Tools |           Templates
 * and open    t   he template in the editor.
 */
package gomoku.scene.hud;

import gomoku.common.Constants;
import gomoku.gameobject .button.BaseButton;
import gomoku.gameobject.button.TextButt  on;
import gomoku.manager.GameManager;
import gomoku.manager.NetworkUtilities;
import gomoku.manager.ResourceMan  ager;
import gomoku.scene.BaseScene;
import gomoku.util.Ga   meU  til;
import java.awt.Color;
import java.awt.event.KeyEvent;
import ui.entity.primitive.Rectangle;
import ui.en    tity.text.Te  xt;

/**
 *
 * @author Ngu    yen Anh Tu an
 */
public class           ConfirmQuitHudScen     e exte       nds BaseH   udScene {
         
    /      / =========================      ====    =============      =======   ======      ====
    // Fields
    // ==    ================    ==    ==================    ====================     =
    
    priva  te Rectangle    mPa     nel;
     pri   vate Text mText;
    pr               ivate        BaseButton mButtonAccept;
    private BaseButton mButtonCancel;
    
    // =============     ========     =======================  ===========  ===      =
    // Con  struct     ors
    // ===========================     =====  ===========================
    pub      lic ConfirmQui    tHudScene(BaseScene pM  a inScene) {
        super(pMainScene);
    }
    
    // ==    =====  ==============  ===   ===================================          
    // Methods fo r/from SuperClass/Int   erfaces  
            // ===  =     =========================    =========================  =====
          
    
    @Override
              pub lic       void onL     oa  d (On   Load    Callb ack    pOnLoad  C   allbac   k) throws      Excepti  on {
               
        // Panel 
               this.m  Panel = new Re  ctangle(0, 0, 600, 150);
                 this     .     mPanel.se      tPos ition((Constant s.SCREEN_WID TH - t         his.mPanel.getWidt     h()) / 2     , 
                      (Constants.SCREEN_HEIGHT - this.mPane l.getHeig   ht()) / 2);  
              this.mPanel. s          etColor(Constants.MESSAGE_  BOX_COLOR_DEFAUL     T);
        thi s.m   Panel.hasStoke(false);
        
              // Text
               this.mText = new Text(0,    0, ResourceManager.g    etInstan     ce().g  etDefaul   t   Font32(), Co      nstants   .ORANGE_COLOR_DEFAULT, Co          n   s tants.CONFIRM_EXIT_LABEL_TEXT);
        this.mText  .setPositio               n((this.mPanel.getWi  dth() - thi              s.mText.ge    tWidth()) /   2, BaseH   udScen   e.PADDIN  G_    DEFAU      L    T);
               
        // Button 
          thi s.mButto         nAccept = new TextButton( 0, 0      ,        Consta           nt  s.BUTTON       _YES_TEXT, Resourc e Manager.getI         nsta nc  e().getOran    geButtonTextur     eRe       gio                     n()) {
                    @Overr  i de
               p  ublic voi   d o   nC     li         ck() {
                  if (GameM   anager.g    etIn            sta   n   ce  ().getCurrentGameMode(    ) == GameUti            l.Ga  meMod        e.ONL   INE          ) {
                                         N  e   tworkU       til  itie     s.s  e   n   dLogoutReq u         es      t(       Ga   meManag      er     .ge   tInsta  nce().getFirstPl   ayer  Name(              ));
                                }
                                 Sy  stem.e  xi      t   (0);
                     }    
                 
                @Override
             publi   c void onKe       yEvent(Key      Even t pK     eyEvent)     {
                    
            }
        };
               this.mButton   Accep   t.getEnt  ity()  .setPosition(B     aseH   udScene   .PA DDIN    G_DEF   AULT, 
                this.mPan         el.     getHei     ght() -   this.m  ButtonAccept.getEntity()   .get     Height() -      BaseHud Scene.PADDING_DEFAULT);
           
            this.mButtonC    a     ncel   = new TextButton (0, 0, C onstants.BUTTON_NO_TEXT,        R        e  sourceManager.getIn     stance().  getOrangeBu   tto  nTex      tureReg       ion()) {
            @Overr ide
                               p   ub   li  c void                onClick()   {
                    C  onf irmQuitHudScene.th          is.hide( )             ;
                     C on firmQuit HudSc    ene.this.mMainS     cene.bac     k();
                 }
                
                     @Override
                   public void    onK         eyEve     nt(KeyEve     nt pKeyEvent) {
                  
                    }
           };
        this.m      ButtonCancel.getEntity().setP  osi           tio n(this.mPane l.getWidth() - this.mB uttonCancel.getEntity() .getWi   dth()   -      Ba    se   Hu  dSce  ne.PADDING_DEFAULT, 
                                     this.mPanel.getHeight() - this.mButtonCancel.getEntity().getHeig     ht() - BaseHudSc   ene.PADDING_DE      FAULT);
                       
        t      his.regi      stryMou  seArea  (this.  mButtonAccept.                    ge  tEntity());
           t   his.registr yMouseAre  a(this.    mButtonCancel.getEn    tity());
        
          pOnLoadCallback.onLoadFinis      h();
         }
        
    @Override
      pu     bl    ic void onShow(OnSh  owCallback        pOn ShowCallback) th   rows          Exception {
        
                this         .mPanel.attachChild(this.mText);
        this.mPanel.attachChild(this.mButton    A   ccept.getEntity());
        this.mPanel   .attachChild(this.mButtonCancel.get    Entity());
        
           this.at     tachChild(this.mPanel);
        
        pOnShowCallba   ck.onShowFinish(      );
    }
    
    @Override
    publi  c void      onHid         e(OnH     ideCall  back pOnHideCall     back) throws Exceptio  n {
        
        this  .unregistryMouseA      rea(this.mButtonAccept.get     Entity());
        this.unregistryMouseArea(this.mButtonCancel.getEntity());
        
        pOnHideCallback.o  nHideFinish();
    }

    @Override  
    public void onUnload(OnUnload Callback pOnUnloadCallback) throws Exce  ption {         
          this.mPanel.detachChi ldren();
        this.mPanel.detach();
        
        this.detachChildren();
        this.detach();
        pOnUnloadCallback.onUnloadFinish();
    }
}
