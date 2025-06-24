package com.spicenu.qbii.view;

import      com.badlogic.gdx.Gdx;
import     com.badlogic.gdx.assets.AssetManager;
im   port com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import    com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Res   olution;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
imp   ort com.badlogic.gdx.graphics.g2d.Anim   ation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.gluti    ls.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.spicenu.qbii.Qb ii;
import com.spicenu.qbii.model.Crate;
   import com.spicenu.q    bii.model.Jo;
import com.spicenu.qbii.model.Popup;
import com.spicenu.    qbii.model.Teleporter;
import com.spicenu.qbii.model.Wall;

publi   c class CrateRenderer      {

	priv  ate static  final int   WIDTH = Gdx.graphic   s.getWidt       h()   ;  
	private static fi   n     al int HEIGHT = Gdx.g   raphics.getHeig   ht();
	private static final float CAMERA_WIDTH =     15f;
	private st atic fina    l float CAMERA_HEIGHT = 9f;
	pri   vate static final float SPEEDU  P_FRAME_DURATION = 0.06f;
	
	      private  Qbii qbii;
	private Crate cr at       e;
	private Orthog   raphicCamera cam;
	
	/** for debug rendering **/
	private ShapeRenderer debugRenderer =   new      ShapeRenderer();
	public static boolean debug = true;
	
	/** Textures **/
	private AssetManager manager;
	private TextureAtlas atlas  ;
	private TextureRegion trJo;
	private TextureRegion trWallOpaqu    e, trWallClear, trWallPers, trWallF    rame;
	private TextureRegion trTeleporterEntran     ceOn, trTeleporterEntranc    eOff, trTeleporterEntr    anceFrame;
	privat e TextureRegion trTeleporterExitOn, trTeleporterExitOff, trTeleporterExitFrame;
	private TextureRegio  n trPopupFrame;
	private TextureRegion trLevelA;
	private BitmapFont   font;
	
	/** Animat   ions **/
	private Animation    ani     mSpee       dUp;
	    
	private floa  t stateTime;
	private SpriteBatch spriteBatch;
	private boolean text  uresInitialized = false;
	   
	private in      t wid     t h;   
	private int height;
      	priva    te float ppuX;	// pixels    p        er unit    on the X   axis
	private float     ppuY;	//       pix els per unit on the  Y a    x   is
	
	public void setSize   (int  w, int h) {
		this.width = w; 
		this.height = h;   
		ppuX = width / CAME RA_WIDTH;
		ppuY = hei     ght /    CAMERA_HEIGHT;
	  }
	
	pu     blic Crat   eRen  derer(Crate c, Qbii g) {
		q bii = g;
		crate       = c;
		cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);			// Set camera h eight  
		cam.position.set(CAMERA_WI  DTH / 2f, CAME  RA     _HEIGHT / 2f, 0);	// Set camera position in  the    middle
		cam.updat            e(   );
		
		font = new     Bit  mapFon   t();
		spriteBatch = qbii.spriteBatch;
		stateTime = 0f;
		loadTexture    s();
	}
	
	public          void r  ender() {
		if (manage  r.update()) {
			// All assets are loaded, we can use them now
			spriteBatch.begin();
			if (!texturesInitialized) { 
				atlas = manager.get("a tlas/textures.atlas");
				trJo = atlas.findR   egion("jo-right");   
				trWallOpaque = atlas.findRegion("wall-op");
				trWa  llClear = atlas.findRegion("wall-cl");    
				trWallPers = a  tlas.findRegion("wall-per");
				trTeleporterEntra  nceOn = a tlas.findRegion("teleporter-in-on");
				trTeleporterEntranceOff = atlas.findRegion("teleporter-in-off");
				trTeleporterExitOn = atlas.f     indRegion("teleporter     -out-on");   
				trTeleporterExitOff = atlas.findRegi    on("teleporter-out-off");
	   	      		trLe     velA = atlas.findRegion("level-a");
				
				TextureR   egion[] speedUpFrames = n  ew TextureRegion   [15];
				for (int i = 0; i < 15; i++) {
   					speedUpFrames[i] = atlas.findReg      ion("sp  eedup-" + (i + 1));
				}
				animSpeedUp    = new Animation(SPEEDUP_FRA    ME_DURATION, speedUpFrames);
				
				texturesInitialize   d = true;
			}
			
		    	if (te    xturesInitialized) {
				drawLevel();    
				drawJo();
				drawWalls ();
				drawTele porters();
				drawPopups();
			}
			
			font.d     raw(spriteBatch, "fps: " + Gdx.graphics.ge     tFrame   sPerSecond(), 26, 40);
			spriteBatc  h.end();
			if (debug)
				    drawDebug();
		}
	}
	
	private void loadTextures() {
		manager = new AssetManager();
	     	Resolution[] resolutions = {new Resolution(480, 320, "480      x320"), new Resolution(960, 640, "960x640")};
		ResolutionFileResolver resolver = new ResolutionFile          Resolver(new InternalFileHandleResolver(), res olutions);
		manager.setLoader(TextureAtlas.cla     ss, n  ew TextureAtlasLoader(resolver));
		manager.load("atlas/textures.atlas", TextureAtlas.class);
	}
	
	p   riv  a         te void drawLevel() {
		spriteBatch.draw(trLev   elA, 0, 0, WIDTH,   HEIGHT);
	}
  	
	private void  d rawJo() {
		Jo jo =   crate.getJo();
		spriteBatch.draw(trJo, jo.    getPositi  on().x * ppuX, jo.getPosition().y * ppuY, Jo.SIZE * ppuX, Jo.SIZE * ppuY) ;
		font.draw(spri    teBatch, "Jo x: " + jo.getPosition().x, 26, 55);
		font.draw(spriteBatch   , "Jo y: " + jo.getPosition().y, 26, 70);
	}
	
	private void            drawWalls() {
		for (Wall w   : crate.getWal    ls()) {
			switch (w.getState()) {
			case OPAQUE:
				trWallFrame = trWa     llOpaque;
				break;
			case CL      EAR:
				trWallFrame = trWallClear;
				break;
			case PERSISTENT:
	    			trWallFrame = trWallPers;
				break;
			}
			spriteBatch.draw(trWallFrame, w.getP   osition().x * ppuX, w.getPosition().y * ppuY, w.getWidth() * ppuX, w.getHeight() * ppuY);
		}
	}
	
	private   void drawTeleporters() { 
		for (Teleporter t : crate.getTeleporters()) {
	  		if (t.getState() == Teleporter.State.ON)     {
				trTelep   orterEntranceFrame = trTeleporterEntr    anceOn;
        				trTeleporterExitFrame = trTelep   orterExitOn;
			} else {
				trTeleporterEntranceFrame = trTeleporterEntranceOff;
				trTeleporterExitFrame = trTeleporterExitOff;
			}
	      		  spriteBa    tch.draw (trTeleporterEntranceFrame,
								t.ge  tEntranceRenderPo sition().x * ppuX,
								t.    getEn   tranceRenderPosition   ().y * ppuY,   
								Teleporter. RE     NDER_WIDTH * ppuX,
								Teleporter.RENDER_HEIGHT * ppuY);
			spriteBatch.draw(trTeleporterExitFrame,
								t.getExitRenderPosition().x * ppuX,
								t .getExitRenderPos     ition().y * ppuY,
								Teleporter.RENDER_WIDTH * ppuX,
								Teleporter.RENDER  _HEIGHT * ppuY   );
		}
	}
	
	private void draw    Popups() {
		Popup p = crate.getSpeedUpPopup();
		if (p.getState() == Popup.Sta     te.RUNNI    NG) {
			     stateTi   me += Gdx.graphics.getDeltaTime();
			trPopupFrame     = animSpeedUp.getKeyFrame(stateTime, true);
			spriteBatch.draw(trPopupFrame, p.ge   tPosition().x * ppuX, p.getPosition().y * ppuY);
			if (stateTime > 1) {
				p.setState(Popup.Stat e.IDLE);
				stateTime = 0f;
	       		}
		}
	}
	   
	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined)    ;
		debugRenderer.begin(ShapeType.Line);
		
		// render Jo
		Jo jo = crate.getJo();
		Rectangle rect = jo.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0,   1));
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		
		// render Walls
		for (Wall w : crate.getWalls()) {
			rect = w.getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(    rect.x, rect.y, rect.width, rect.height);
		}
		
		debugRenderer.end();
	}
}
