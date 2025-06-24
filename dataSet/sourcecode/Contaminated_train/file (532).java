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
import gomoku.manager.ScreenManager;
import gomoku.scene.BaseScene;
import gomoku.util.GameUtil;
import ui.entity.primitive.Rectangle;
import ui.entity.text.Text;

/**
 *
 * @author Nguyen Anh Tuan
 */
public class ConfirmLogoutHudScene extends BaseHudScene {

    // ===========================================================
    // Fields
    // ===========================================================
    private Rectangle mPanel;
    private Text mText;
    private BaseButton mButtonYes;
    private BaseButton mButtonNo;

    // ===========================================================
    // Contructors
    // ===========================================================
    public ConfirmLogoutHudScene(final BaseScene pMainScene) {
        super(pMainScene);
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public void onLoad(OnLoadCallback pOnLoadCallback) throws Exception {

        // Panel
        this.mPanel = new Rectangle(0, 0, 650, 150);
        this.mPanel.setPosition((Constants.SCREEN_WIDTH - this.mPanel.getWidth()) / 2,
                (Constants.SCREEN_HEIGHT - this.mPanel.getHeight()) / 2);
        this.mPanel.setColor(Constants.MESSAGE_BOX_COLOR_DEFAULT);
        this.mPanel.hasStoke(false);

        // Text
        this.mText = new Text(0, 0, ResourceManager.getInstance().getDefaultFont32(), Constants.ORANGE_COLOR_DEFAULT, Constants.CONFIRM_LOGOUT_LABEL_TEXT);
        this.mText.setPosition((this.mPanel.getWidth() - this.mText.getWidth()) / 2, BaseHudScene.PADDING_DEFAULT);

        // Button
        this.mButtonYes = new TextButton(0, 0, Constants.BUTTON_YES_TEXT, ResourceManager.getInstance().getOrangeButtonTextureRegion()) {
            @Override
            public void onClick() {
                ConfirmLogoutHudScene.this.hide();
                ConfirmLogoutHudScene.this.mMainScene.back();
                if (GameManager.getInstance().getCurrentGameMode() == GameUtil.GameMode.ONLINE) {
                    NetworkUtilities.sendLogoutRequest(GameManager.getInstance().getFirstPlayerName());
                }
                ScreenManager.getInstance().showLoginScene();
            }
        };
        this.mButtonYes.getEntity().setPosition(BaseHudScene.PADDING_DEFAULT,
                this.mPanel.getHeight() - BaseHudScene.PADDING_DEFAULT - this.mButtonYes.getEntity().getHeight());

        this.mButtonNo = new TextButton(0, 0, Constants.BUTTON_NO_TEXT, ResourceManager.getInstance().getOrangeButtonTextureRegion()) {
            @Override
            public void onClick() {
                ConfirmLogoutHudScene.this.hide();
                ConfirmLogoutHudScene.this.mMainScene.back();
            }
        };
        this.mButtonNo.getEntity().setPosition(this.mPanel.getWidth() - BaseHudScene.PADDING_DEFAULT - this.mButtonNo.getEntity().getWidth(),
                this.mButtonYes.getEntity().getY());


        this.registryMouseArea(this.mButtonYes.getEntity());
        this.registryMouseArea(this.mButtonNo.getEntity());

        pOnLoadCallback.onLoadFinish();
    }

    @Override
    public void onShow(OnShowCallback pOnShowCallback) throws Exception {
        this.mPanel.attachChild(this.mText);
        this.mPanel.attachChild(this.mButtonYes.getEntity());
        this.mPanel.attachChild(this.mButtonNo.getEntity());

        this.attachChild(this.mPanel);

        pOnShowCallback.onShowFinish();
    }

    @Override
    public void onHide(OnHideCallback pOnHideCallback) throws Exception {

        this.unregistryMouseArea(this.mButtonYes.getEntity());
        this.unregistryMouseArea(this.mButtonNo.getEntity());

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
