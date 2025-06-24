package com.spicenu.qbii.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.spicenu.qbii.Qbii;
import com.spicenu.qbii.controller.JoController;
import com.spicenu.qbii.controller.TeleporterController;
import com.spicenu.qbii.controller.WallController;
import com.spicenu.qbii.model.Crate;
import com.spicenu.qbii.model.Jo;
import com.spicenu.qbii.view.CrateRenderer;

public class CrateScreen implements Screen, InputProcessor {
	
	private final static boolean DEBUG = true;
	
	private Qbii qbii;
	private Crate crate;
	private Jo jo;
	private CrateRenderer crateRenderer;
	private JoController joController;
	private WallController wallController;
	private TeleporterController teleporterController;
	
	/** Assets **/
	private AssetManager manager;
	private TextureAtlas atlas;
	
	/** HUD **/
	private Stage inGameHUDStage;
	private Skin skin;
	
	private int width, height;
	
	public CrateScreen(Qbii g) {
		qbii = g;
		crate = new Crate();
		crate.loadLevel();
		jo = crate.getJo();
		joController = new JoController(crate);
		wallController = new WallController(crate);
		teleporterController = new TeleporterController(crate);
		crateRenderer = new CrateRenderer(crate, qbii);
		
		Resolution[] resolutions = {new Resolution(480, 320, "480x320"), new Resolution(960, 640, "960x640")};
		ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);
		manager = new AssetManager();
		manager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
		manager.load("atlas/HUD-textures.atlas", TextureAtlas.class);
		manager.finishLoading();
		
		atlas = manager.get("atlas/HUD-textures.atlas", TextureAtlas.class);
		inGameHUDStage = new Stage();
		createHUD();
		
		InputMultiplexer mux = new InputMultiplexer();
		mux.addProcessor(inGameHUDStage);
		mux.addProcessor(this);
		Gdx.input.setInputProcessor(mux);
	}
	
	private void createHUD() {
		skin = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		
		TextureRegion imgResume = new TextureRegion(atlas.findRegion("resume"));
		TextureRegion imgQuit = new TextureRegion(atlas.findRegion("quit"));
		skin.add("resume", imgResume);
		skin.add("quit", imgQuit);
		
		skin.add("default", new BitmapFont());
		
		ButtonStyle btnResumeStyle = new ButtonStyle();
		btnResumeStyle.up = skin.newDrawable("resume");
		btnResumeStyle.down = skin.newDrawable("resume", Color.LIGHT_GRAY);
		skin.add("resumeStyle", btnResumeStyle);
		
		ButtonStyle btnQuitStyle = new ButtonStyle();
		btnQuitStyle.up = skin.newDrawable("quit");
		btnQuitStyle.down = skin.newDrawable("quit", Color.LIGHT_GRAY);
		skin.add("quitStyle", btnQuitStyle);
		
		final Button btnResume = new Button(skin, "resumeStyle");
		final Button btnQuit = new Button(skin, "quitStyle");
		btnResume.setVisible(false);
		btnQuit.setVisible(false);
		
		btnResume.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + btnResume.isChecked());
				btnResume.setVisible(false);
				btnQuit.setVisible(false);
				crate.setState(Crate.State.PLAYING);
			}
		});
		
		btnQuit.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + btnQuit.isChecked());
//				btnResume.setVisible(false);
//				btnQuit.setVisible(false);
//				crate.setState(Crate.State.PLAYING);
				qbii.setScreen(new MenuScreen(qbii));
				dispose();
			}
		});
		
		TextureRegion trBtnPause = new TextureRegion(atlas.findRegion("pause"));
		skin.add("pause", trBtnPause);
		
		ButtonStyle btnPauseStyle = new ButtonStyle();
		btnPauseStyle.up = skin.newDrawable("pause");
		btnPauseStyle.down = skin.newDrawable("pause", Color.LIGHT_GRAY);
		skin.add("pauseStyle", btnPauseStyle);
		Button btnPause = new Button(skin, "pauseStyle");
		
		btnPause.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("QBII", "Touch down PAUSE button");
				return true;
			}
			
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("QBII", "Touch up PAUSE button");
				crate.setState(Crate.State.PAUSE);
				btnResume.setVisible(true);
				btnQuit.setVisible(true);
			}
		});
		
		Table table = new Table();
		if (DEBUG)
			table.debug();
		table.setFillParent(true);
		
		table.add().expandX();
		table.add(btnPause).width(btnPause.getWidth());
		table.row();
		table.add(btnResume).expand();
		table.row();
		table.add(btnQuit).expand();
		inGameHUDStage.addActor(table);
	}
	
	@Override
	public void render(float delta) {
		switch (crate.getState()) {
		case PLAYING:
//			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			if (jo.getState() == Jo.State.PASS) {
				crate.goToNextLevel();
				crate.clearLevel();
				crate.loadLevel();
	//			jo.resetPosition();
				jo.setState(Jo.State.FALLING);
			} else if (jo.getState() == Jo.State.DEAD) {
				crate.resetLevel();
				jo.resetPosition();
				jo.setState(Jo.State.FALLING);
			}
			
			crateRenderer.render();
			joController.update(delta);
			wallController.update(delta);
			teleporterController.update(delta);
			break;
		case PAUSE:
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			crateRenderer.render();
			break;
		}
		
		inGameHUDStage.act(delta);
		inGameHUDStage.draw();
		if (DEBUG)
			Table.drawDebug(inGameHUDStage);
	}

	@Override
	public void resize(int width, int height) {
		crateRenderer.setSize(width, height);
		inGameHUDStage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
//		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
//		Gdx.input.setInputProcessor(null);
		inGameHUDStage.dispose();
		skin.dispose();
		manager.dispose();
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (crate.getState() == Crate.State.PLAYING) {
			wallController.flipPressed();
			teleporterController.switchPressed();
			return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
