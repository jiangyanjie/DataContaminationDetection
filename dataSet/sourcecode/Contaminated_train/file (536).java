/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku.scene.layer;

import gomoku.common.Constants;
import gomoku.gameobject.button.BaseButton;
import gomoku.gameobject.button.TextButton;
import gomoku.manager.GameManager;
import gomoku.manager.NetworkUtilities;
import gomoku.manager.ResourceManager;
import gomoku.manager.ScreenManager;
import gomoku.scene.BaseScene;
import gomoku.scene.hud.BaseHudScene;
import gomoku.util.GameUtil;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import ui.entity.primitive.Rectangle;
import ui.entity.text.Text;

/**
 *
 * @author Nguyen Anh Tuan
 */
public class ConfirmSendChallengeLayer extends BaseLayer {

    // ===========================================================
    // Fields
    // ===========================================================
    
    private Text mLabel;
    private Text mUserName;
    private BaseButton mButtonYes;
    private BaseButton mButtonNo;
    private Rectangle mPanel;
    
    // ===========================================================
    // Contructors
    // ===========================================================
    
    public ConfirmSendChallengeLayer(final BaseScene pMainScene) {
        super(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, pMainScene);
    }
    
    // ===========================================================
    // Fields
    // ===========================================================
    
    @Override
    public void slideIn() {
        
    }

    @Override
    public void slideOut() {
        
    }

    @Override
    public void onMouseEvent(MouseEvent pMouseEvent, int pMouseAreaLocalX, int pMouseAreaLocalY) {
        
    }

    @Override
    public void onKeyEvent(KeyEvent pKeyEvent) {
        
    }

    @Override
    public void onLoad(OnLoadCallback pOnLoadCallback) throws Exception {
        
        // Panel
        this.mPanel = new Rectangle(0, 0, 600, 150);
        this.mPanel.setPosition((Constants.SCREEN_WIDTH - this.mPanel.getWidth()) / 2 , 
                (Constants.SCREEN_HEIGHT - this.mPanel.getHeight()) / 2);
        this.mPanel.setColor(Constants.MESSAGE_BOX_COLOR_DEFAULT);
        this.mPanel.hasStoke(false);
        
        // Label
        this.mLabel = new Text(0, 0, ResourceManager.getInstance().getDefaultFont32(), 
                Constants.ORANGE_COLOR_DEFAULT, Constants.CONFIRM_SEND_CHALLENGE_LABEL_TEXT);
        this.mLabel.setPosition((this.mPanel.getWidth() - this.mLabel.getWidth()) / 2, BaseLayer.PADDING_DEFAULT);
        
        // User name
        this.mUserName = new Text(0, 0, ResourceManager.getInstance().getDefaultFont32(), 
                Constants.ORANGE_COLOR_DEFAULT, GameManager.getInstance().getSecondPlayerName());
        this.mUserName.setPosition((this.mPanel.getWidth() - this.mUserName.getWidth()) / 2, 
                this.mLabel.getY() + this.mLabel.getHeight() + BaseLayer.PADDING_DEFAULT);
        
        // Button
        this.mButtonYes = new TextButton(0, 0, Constants.BUTTON_YES_TEXT, ResourceManager.getInstance().getOrangeButtonTextureRegion()) {

            @Override
            public void onClick() {
                GameManager.getInstance().setCurrentPlayer(GameUtil.Player.HUMAN);
                GameManager.getInstance().setMyPiece(GameUtil.Piece.WHITE);
                NetworkUtilities.sendChallengeRequest(GameManager.getInstance().getFirstPlayerName(), GameManager.getInstance().getSecondPlayerName());
                ConfirmSendChallengeLayer.this.hide();
                ScreenManager.getInstance().showLoadingLayer();
            }
        };
        this.mButtonYes.getEntity().setPosition(BaseLayer.PADDING_DEFAULT, 
                (this.mPanel.getHeight() - BaseLayer.PADDING_DEFAULT - this.mButtonYes.getEntity().getHeight()));
        
        this.mButtonNo = new TextButton(0, 0, Constants.BUTTON_NO_TEXT, ResourceManager.getInstance().getOrangeButtonTextureRegion()) {

            @Override
            public void onClick() {
                ScreenManager.getInstance().showUserListLayer();
            }
        };
        
        this.mButtonNo.getEntity().setPosition(this.mPanel.getWidth() - BaseLayer.PADDING_DEFAULT - this.mButtonNo.getEntity().getWidth(), 
                this.mButtonYes.getEntity().getY());
        
        // Register
        this.mMainScene.registryMouseArea(this.mButtonYes.getEntity());
        this.mMainScene.registryMouseArea(this.mButtonNo.getEntity());
        
        // Set position
        this.mEntity.setPosition((Constants.SCREEN_WIDTH - this.mEntity.getWidth()) / 2, (Constants.SCREEN_HEIGHT - this.mEntity.getHeight()) / 2);
        
        pOnLoadCallback.onLoadFinish();
    }

    @Override
    public void onShow(OnShowCallback pOnShowCallback) throws Exception {
        
        this.mPanel.attachChild(this.mLabel);
        this.mPanel.attachChild(this.mUserName);
        this.mPanel.attachChild(this.mButtonYes.getEntity());
        this.mPanel.attachChild(this.mButtonNo.getEntity());
        
        this.mEntity.attachChild(this.mPanel);
        
        this.mMainScene.attachChild(this.mEntity);
        
        super.onShow(pOnShowCallback);
    }
    
    
    @Override
    public void onHide(OnHideCallback pOnHideCallback) throws Exception {
        
        this.mMainScene.unregistryMouseArea(this.mButtonYes.getEntity());
        this.mMainScene.unregistryMouseArea(this.mButtonNo.getEntity());
        
        super.onHide(pOnHideCallback);
    }

}
