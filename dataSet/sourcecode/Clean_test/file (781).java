package com.spicenu.qbii.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.spicenu.qbii.Qbii;
import com.spicenu.qbii.model.Crate;
import com.spicenu.qbii.model.Jo;
import com.spicenu.qbii.model.Popup;
import com.spicenu.qbii.model.Teleporter;
import com.spicenu.qbii.model.Wall;

public class CrateRenderer {

	private static final int WIDTH = Gdx.graphics.getWidth();
	private static final int HEIGHT = Gdx.graphics.getHeight();
	private static final float CAMERA_WIDTH = 15f;
	private static final float CAMERA_HEIGHT = 9f;
	private static final float SPEEDUP_FRAME_DURATION = 0.06f;
	
	private Qbii qbii;
	private Crate crate;
	private OrthographicCamera cam;
	
	/** for debug rendering **/
	private ShapeRenderer debugRenderer = new ShapeRenderer();
	public static boolean debug = true;
	
	/** Textures **/
	private AssetManager manager;
	private TextureAtlas atlas;
	private TextureRegion trJo;
	private TextureRegion trWallOpaque, trWallClear, trWallPers, trWallFrame;
	private TextureRegion trTeleporterEntranceOn, trTeleporterEntranceOff, trTeleporterEntranceFrame;
	private TextureRegion trTeleporterExitOn, trTeleporterExitOff, trTeleporterExitFrame;
	private TextureRegion trPopupFrame;
	private TextureRegion trLevelA;
	private BitmapFont font;
	
	/** Animations **/
	private Animation animSpeedUp;
	
	private float stateTime;
	private SpriteBatch spriteBatch;
	private boolean texturesInitialized = false;
	
	private int width;
	private int height;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = width / CAMERA_WIDTH;
		ppuY = height / CAMERA_HEIGHT;
	}
	
	public CrateRenderer(Crate c, Qbii g) {
		qbii = g;
		crate = c;
		cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);			// Set camera height
		cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);	// Set camera position in the middle
		cam.update();
		
		font = new BitmapFont();
		spriteBatch = qbii.spriteBatch;
		stateTime = 0f;
		loadTextures();
	}
	
	public void render() {
		if (manager.update()) {
			// All assets are loaded, we can use them now
			spriteBatch.begin();
			if (!texturesInitialized) { 
				atlas = manager.get("atlas/textures.atlas");
				trJo = atlas.findRegion("jo-right");
				trWallOpaque = atlas.findRegion("wall-op");
				trWallClear = atlas.findRegion("wall-cl");
				trWallPers = atlas.findRegion("wall-per");
				trTeleporterEntranceOn = atlas.findRegion("teleporter-in-on");
				trTeleporterEntranceOff = atlas.findRegion("teleporter-in-off");
				trTeleporterExitOn = atlas.findRegion("teleporter-out-on");
				trTeleporterExitOff = atlas.findRegion("teleporter-out-off");
				trLevelA = atlas.findRegion("level-a");
				
				TextureRegion[] speedUpFrames = new TextureRegion[15];
				for (int i = 0; i < 15; i++) {
					speedUpFrames[i] = atlas.findRegion("speedup-" + (i + 1));
				}
				animSpeedUp = new Animation(SPEEDUP_FRAME_DURATION, speedUpFrames);
				
				texturesInitialized = true;
			}
			
			if (texturesInitialized) {
				drawLevel();
				drawJo();
				drawWalls();
				drawTeleporters();
				drawPopups();
			}
			
			font.draw(spriteBatch, "fps: " + Gdx.graphics.getFramesPerSecond(), 26, 40);
			spriteBatch.end();
			if (debug)
				drawDebug();
		}
	}
	
	private void loadTextures() {
		manager = new AssetManager();
		Resolution[] resolutions = {new Resolution(480, 320, "480x320"), new Resolution(960, 640, "960x640")};
		ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);
		manager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
		manager.load("atlas/textures.atlas", TextureAtlas.class);
	}
	
	private void drawLevel() {
		spriteBatch.draw(trLevelA, 0, 0, WIDTH, HEIGHT);
	}
	
	private void drawJo() {
		Jo jo = crate.getJo();
		spriteBatch.draw(trJo, jo.getPosition().x * ppuX, jo.getPosition().y * ppuY, Jo.SIZE * ppuX, Jo.SIZE * ppuY);
		font.draw(spriteBatch, "Jo x: " + jo.getPosition().x, 26, 55);
		font.draw(spriteBatch, "Jo y: " + jo.getPosition().y, 26, 70);
	}
	
	private void drawWalls() {
		for (Wall w : crate.getWalls()) {
			switch (w.getState()) {
			case OPAQUE:
				trWallFrame = trWallOpaque;
				break;
			case CLEAR:
				trWallFrame = trWallClear;
				break;
			case PERSISTENT:
				trWallFrame = trWallPers;
				break;
			}
			spriteBatch.draw(trWallFrame, w.getPosition().x * ppuX, w.getPosition().y * ppuY, w.getWidth() * ppuX, w.getHeight() * ppuY);
		}
	}
	
	private void drawTeleporters() {
		for (Teleporter t : crate.getTeleporters()) {
			if (t.getState() == Teleporter.State.ON) {
				trTeleporterEntranceFrame = trTeleporterEntranceOn;
				trTeleporterExitFrame = trTeleporterExitOn;
			} else {
				trTeleporterEntranceFrame = trTeleporterEntranceOff;
				trTeleporterExitFrame = trTeleporterExitOff;
			}
			spriteBatch.draw(trTeleporterEntranceFrame,
								t.getEntranceRenderPosition().x * ppuX,
								t.getEntranceRenderPosition().y * ppuY,
								Teleporter.RENDER_WIDTH * ppuX,
								Teleporter.RENDER_HEIGHT * ppuY);
			spriteBatch.draw(trTeleporterExitFrame,
								t.getExitRenderPosition().x * ppuX,
								t.getExitRenderPosition().y * ppuY,
								Teleporter.RENDER_WIDTH * ppuX,
								Teleporter.RENDER_HEIGHT * ppuY);
		}
	}
	
	private void drawPopups() {
		Popup p = crate.getSpeedUpPopup();
		if (p.getState() == Popup.State.RUNNING) {
			stateTime += Gdx.graphics.getDeltaTime();
			trPopupFrame = animSpeedUp.getKeyFrame(stateTime, true);
			spriteBatch.draw(trPopupFrame, p.getPosition().x * ppuX, p.getPosition().y * ppuY);
			if (stateTime > 1) {
				p.setState(Popup.State.IDLE);
				stateTime = 0f;
			}
		}
	}
	
	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		
		// render Jo
		Jo jo = crate.getJo();
		Rectangle rect = jo.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		
		// render Walls
		for (Wall w : crate.getWalls()) {
			rect = w.getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		debugRenderer.end();
	}
}
