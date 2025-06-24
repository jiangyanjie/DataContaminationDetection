/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku.scene.hud;

import gomoku.common.Constants;
import gomoku.gameobject.button.BaseButton;
import gomoku.gameobject.button.TextButton;
import gomoku.manager.GameManager;
import gomoku.manager.NetworkUtilities;
import gomoku.manager.ResourceManager;
import gomoku.scene.BaseScene;
import gomoku.util.GameUtil;
import java.awt.Color;
import java.awt.event.KeyEvent;
import ui.entity.primitive.Rectangle;
import ui.entity.text.Text;

/**
 *
 * @author Nguyen Anh Tuan
 */
public class ConfirmQuitHudScene extends BaseHudScene {
    
    // ===========================================================
    // Fields
    // ===========================================================
    
    private Rectangle mPanel;
    private Text mText;
    private BaseButton mButtonAccept;
    private BaseButton mButtonCancel;
    
    // ===========================================================
    // Constructors
    // ===========================================================
    public ConfirmQuitHudScene(BaseScene pMainScene) {
        super(pMainScene);
    }
    
    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    
    
    @Override
    public void onLoad(OnLoadCallback pOnLoadCallback) throws Exception {
        
        // Panel
        this.mPanel = new Rectangle(0, 0, 600, 150);
        this.mPanel.setPosition((Constants.SCREEN_WIDTH - this.mPanel.getWidth()) / 2 , 
                (Constants.SCREEN_HEIGHT - this.mPanel.getHeight()) / 2);
        this.mPanel.setColor(Constants.MESSAGE_BOX_COLOR_DEFAULT);
        this.mPanel.hasStoke(false);
        
        // Text
        this.mText = new Text(0, 0, ResourceManager.getInstance().getDefaultFont32(), Constants.ORANGE_COLOR_DEFAULT, Constants.CONFIRM_EXIT_LABEL_TEXT);
        this.mText.setPosition((this.mPanel.getWidth() - this.mText.getWidth()) / 2, BaseHudScene.PADDING_DEFAULT);
        
        // Button
        this.mButtonAccept = new TextButton(0, 0, Constants.BUTTON_YES_TEXT, ResourceManager.getInstance().getOrangeButtonTextureRegion()) {
            @Override
            public void onClick() {
                if (GameManager.getInstance().getCurrentGameMode() == GameUtil.GameMode.ONLINE) {
                    NetworkUtilities.sendLogoutRequest(GameManager.getInstance().getFirstPlayerName());
                }
                System.exit(0);
            }
            
            @Override
            public void onKeyEvent(KeyEvent pKeyEvent) {
                
            }
        };
        this.mButtonAccept.getEntity().setPosition(BaseHudScene.PADDING_DEFAULT, 
                this.mPanel.getHeight() - this.mButtonAccept.getEntity().getHeight() - BaseHudScene.PADDING_DEFAULT);
        
        this.mButtonCancel = new TextButton(0, 0, Constants.BUTTON_NO_TEXT, ResourceManager.getInstance().getOrangeButtonTextureRegion()) {
            @Override
            public void onClick() {
                ConfirmQuitHudScene.this.hide();
                ConfirmQuitHudScene.this.mMainScene.back();
            }
            
            @Override
            public void onKeyEvent(KeyEvent pKeyEvent) {
                
            }
        };
        this.mButtonCancel.getEntity().setPosition(this.mPanel.getWidth() - this.mButtonCancel.getEntity().getWidth() - BaseHudScene.PADDING_DEFAULT, 
                this.mPanel.getHeight() - this.mButtonCancel.getEntity().getHeight() - BaseHudScene.PADDING_DEFAULT);
                
        this.registryMouseArea(this.mButtonAccept.getEntity());
        this.registryMouseArea(this.mButtonCancel.getEntity());
        
        pOnLoadCallback.onLoadFinish();
    }

    @Override
    public void onShow(OnShowCallback pOnShowCallback) throws Exception {
        
        this.mPanel.attachChild(this.mText);
        this.mPanel.attachChild(this.mButtonAccept.getEntity());
        this.mPanel.attachChild(this.mButtonCancel.getEntity());
        
        this.attachChild(this.mPanel);
        
        pOnShowCallback.onShowFinish();
    }
    
    @Override
    public void onHide(OnHideCallback pOnHideCallback) throws Exception {
        
        this.unregistryMouseArea(this.mButtonAccept.getEntity());
        this.unregistryMouseArea(this.mButtonCancel.getEntity());
        
        pOnHideCallback.onHideFinish();
    }

    @Override
    public void onUnload(OnUnloadCallback pOnUnloadCallback) throws Exception {
        this.mPanel.detachChildren();
        this.mPanel.detach();
        
        this.detachChildren();
        this.detach();
        pOnUnloadCallback.onUnloadFinish();
    }
}
