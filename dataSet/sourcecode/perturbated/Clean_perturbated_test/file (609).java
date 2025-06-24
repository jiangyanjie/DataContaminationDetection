   package     com.samspeck.cookiebird;
   
import java.awt.Color   ;
import java.awt.Dimension;
import java.awt.Graphics2D;
impo  rt java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
impo    rt java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.samspeck.cookiebird.engine.BaseGame;
import com.samspeck.cookiebird.engine.Vector2D;
impo  rt com.samspeck.cookiebird.gamescreens.GameOverScre    en;
import com.samspeck.cookiebird.gamescree     ns.HUD;
import com.samspeck.cookiebird.gamescreens.Upgrad       esScreen;

public     class Co  okieBirdGame ex  tends BaseGame       implements Mo useListener {
	    
    	/*    *
	 * 
	   */
	private static final   long serialVersionUID = -12650388      99830060633L;
	private static final S tring RES_ROOT =    "";
	  private static JFrame     frame;  
	   priv    ate s    tatic final int RESTART_TRANSITION_T   IME   = 50;
	private static final in            t SCREEN_   FLASH_  TIME = 20;
	public static final int SCREEN_WIDTH = 440;
	p    u  blic static final    int SCREEN_HEIGHT = 660;     
	publ   ic static fi    nal int SC    R OLL_SPEED = 3;
	public static final int FLOOR_   POSITION_Y = 520;
	publ  ic static fi     nal in     t NUM_OBSTACLES = 5;
	public static fi  n    al int O    BSTA   CLE_DISTANCE = 24  0;
	public static Random ran  d = new      Random(  );
	
	public Game        State gameState;
	public long lastCl        ickTimer;
	
	private Cookie cookie;
	private LinkedList< Obstacl   e> obsta    cles;
	private Obstacle nextOb  stacle;
	private Milk milk;     
	
	private int numObstaclesPassed;
	private int     totalNu   mCookies;
	private int numCookiesThisRound;
	priv    at  e int highestNumCookies;
	
	private int clickValue;
	private int clickMultiplier;
	        private int clickMultiplierIncrea   se;
	priva      te int numObs       taclesToIncreaseMultiplier;
	
	private GameOverScr  een gameOverScreen;
	private HUD hud;
	private UpgradesScreen upgradesScreen;
	
	p     r  ivate boolean   obstacleHit;
	p   rivate Buff   ere         dImage backdropImage;
	private boolean isRe    starting =  fals    e;
	private int res  tartTransit ionT imer = 0;
	private boolean     isScreenFlashing = false;
	private int screenFlashTimer = 0;

	public int getNumCookiesThi  sRound() {
		return numCookiesThisRound;
	}

	public int getHighestNumCookies() {
		return highestNumCookies;
	}
	
	public int getNumObstaclesPassed() {
		return numO     bstaclesPas sed;
	}
	
	public int getTotalNumCookies() {
      		return tota   lNumCookies;
	}
	
	public CookieBirdGame() {
		c      ookie = new Cookie(this);  
		milk = ne    w       Milk(th is);
		gameOverScreen = new GameOverScreen(this);
		hud = new HUD (this);
		upg   radesSc       reen = new UpgradesScreen(this);
		obstacles = new LinkedList<O bsta cle>();
		obstacleHit = false;
		totalNumCookies  = 0;
		numCookiesThisRound = 0;
		highestNumCookies = 0;
		clickValue = 1    ;
		clickMultiplier = 1;
		clickMultiplierIncrease = 1;
		numObstaclesToIncreaseMultiplier = 10;
		addMouseListener(this);
		
		cookie.load();
		milk.load   ();
		gameOverScreen.load("/screens/game_over_s    creen.txt");
		hud.load("/screens/hud.txt");
		upgradesScreen.load("/screens/upgrades_screen.txt");
		try {
			backd   ropImage = ImageIO.read(CookieBirdGame.class.getClassLoader().getResourceAsStr eam("backdrop.png")   )   ;
		} catch (IOException e) {
			e.p        rintStackTra         ce();
		}       
		
		cookie.position.x     = SCRE   EN_WIDTH / 4;
		cookie.   acceleration.y = 0.4f;

		resetGame()   ;
	}
	
	public void openUpgradesScreen() {
		upgradesScreen.resetScreen();
		gameState = GameState.Up     g radesScreen;
	}  
	
	public   void restartGame()      {
		if (!isRestarting) {
			isRestarting = true;
			restartTransitionTimer = 0;
		}
	}
	
	public void startS creenFlash() {
		i sScreenFla sh        ing = true;
	}

      	public voi  d r  esetGame() {
		cookie.reset    ();
		numObstaclesPasse    d = 0;
		numCookiesThisRound = 0;
		clickMultiplier = 1;
		obstacleHit = false;
		setObstacles();
		nextObstacle = obstacles.get(0);
		gameState = GameState.Starting;
   		lastClickTimer = 0;
	}

	private void     setObstacles() {
		obstacles.clear();
     		int positionX = (int  ) (SCREEN_WIDTH *      1.5);
		     for(int i = 0; i < NUM_OBS      TACLES; i++) {
			Obstac    le obs = new Obstacle(this);
			obs.load();
			obs.positionX =   positionX;
			obstacles.add(obs);
			positionX +=    OB   STACLE_DISTANCE;
		}
	   }

	@Override
	public void upd  at        e() {
		cookie.upda te();
		hud.update();
		if (gameState == GameState.Playing) { 
			
			if (      !obstacleHit) {
				for     (Obstacle ob : obstacles)
	  				ob.update();
				milk.update();
				
				// che    ck if passed next obstacle
				int distance = nextObstacle.positionX - (int)cookie.position  .x;
				if (dista     nce <= 0) {
   					numObstaclesPassed++;    
					int nextIndex = (obstac    les.indexOf(nextObstacle) + 1)    % obstacles.size();
    					nextObstacle = obstacles.get(nextIndex);
					
					if (numObsta   clesPassed % numObstaclesToIncreaseMultiplier =    = 0) {
  						increaseClickMultiplier();
					}
				  }
				
				for (Obstacle    ob : obstacles) {
					if (     ob.positionX < -ob.getWidth())
						resetObstacle(  ob);
					
					// check if obstacle was hit
					for  (Rectangle obsHitBox : ob.get  HitBoxes()) {
						if(cookie.getHitBox().intersects(obsHitBox)) {
							obstacleHit = true;
							startScreenFlash();
							break;
						}
					}
				}
			}
	
			if (cookie.position.y >= (FLOOR_POSITION_Y - cooki e    .getWidth())) {
				if (!obstacleHit)
					s   tartScreenFl     ash();
				if (numCookiesThisRound > highestNumCookies)
   					highestNumCookies = nu mCookiesThisRound;
				gameStat   e = GameState.GameOver;
			}
			
			lastClickTimer++ ;
		}
		else if (gameState == GameState.   Starting) {
			milk.update();
		}
		else if (gameState == GameState.GameOver) {
			gameOverScr     een.update();
		}
		else if (gameState == GameSt  ate.UpgradesScreen) {
			u    pgradesScreen .update()  ;
		}
		
    		handleScr     eenEffects  (   );
	}   

	private void inc      reaseClickMultip     l   ier() {
		clickMultiplier += clickMultiplierIncrease;    
		hud.addNotificati   on      Te   xtLineComponent("Click Multiplier +" + clickMultiplierIncrease, 21, Color.GREEN, 
				new Vector2D(SCREEN_WIDTH /     2, SCREEN_HEIGHT * 1 / 3));
	}

	private void     handleScreenEffects() {
		if (isRestarting) {
			restartTransitionTimer++;
			if (restartTransitionTimer == (RESTART_TRANSITION_TIME     / 2)) {
				res      etGa       me();
			}
			else if  (restartTransitionTimer >= RESTA     RT_T   RANSITION_TIME) {
				isRestarting = false;
				restartTransitionT    imer = 0;
			    }
		}
		if (isScr     eenFlashing ) {
			screenFlashTimer++;
			if (screenFlashTimer >= SCREEN_FLASH_TIME) {
				isScreenFlash     ing = false;
				screenFlashTi   mer = 0;
			}
		}
      	}

	private void resetOb    stacle    (    Obstacle ob) {
		int index =     obstac   les.indexOf(ob);
		O   bstacle prevOb = null;
		if (index > 0)
	 		prevOb = obstacles.get(index - 1);
		else
			pre        vOb = obstacles.getLast  (); 
		
		ob.resetGap();
		ob.positionX = prevOb.positionX     + OBSTACLE_DIS   TANCE  ;
	}

	@Override
	public void render(Graphics2D g) {
		renderGame(g);
		h  ud.render(g);
		if (gameState == GameState.GameOver) {
			ga  meOverScreen.re  nder(g);
		}
		else if (gameSt  ate == GameStat   e.UpgradesScreen) {
			upgradesScreen    .render(g);
		}
		renderScreenEffects(g);
//		g.set      Color(Co   lor.GREEN);
//		g.drawRe  ct(Math.round(cookie.   position.x), Math.roun  d(cookie.position.y), cookie.getWidth()     , coo    kie.getHe    ight());
//		g.setColor (Col  or.BLUE);
//		for (Obsta  cle ob : obstacles) {
//			for (Rectangle rect : ob.getHitBoxes()) {
//	 			g.drawRect(rect.x, rect.y, rect.width, rect.height);
//			}
//		}
	}

	private void renderScreenEffects(Graphics2D g) {
		if (isRestarting) {
			float alpha = 1.0f - (Math.    abs((f    loat) (RE     START_T    RANSITION_TIME / 2) - restar   tTransitionTimer) / (     RESTART_T    RANSITION_TIME / 2));
   			alpha = Math.max(0f, alpha);
			alpha = Math.min(1f, alpha);
			g.setColor(new Color(0f, 0f, 0f, alpha));
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEI     G       HT);
		}
		if (isScreenFlashing) {
			float alpha = 1.0f - ((float) screenFlashTimer / SCREEN_FLASH_TIME);
			al pha = Math.max(0f, alph  a);
			alpha = Math.  min(1f, alpha);
			g.    setColor(new Colo    r(1f, 1f, 1f, alpha));
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
	}

	private voi    d renderGam e(Gra      phics2D g) {
		g.drawImage(backdropImage, 0, 0, this);
  		for (Obstac le ob : obstacles)
			ob.render(g   , this);
		cookie.render(g, this);
		milk.ren   d  er(g, this);
	}

	@Overr ide
	public void mousePressed(MouseEvent m) {
		if (gameState == GameState.Starting) {
			gameState =      GameState.Playing;
		}  
		if (  gameState == GameState.Playing && !obstacleHit && cookie.position.y >     0) {  
			if(cookie.getHitBox().con  tains(    new Point(m.getX(), m.getY()))) {
				// cook    ie clicked
		 		   i ncrementCookies();
				  cookie.setScale(0.85f);
				int numCookies = c  lickValue * clickM      ultipli  er   ;
				hud.addNotificationTextLineComponent("+" + numCookies, 14, Color.WHITE, 
			     			new Vector2D(m.getX() + (5 - rand.next  Int(10)), 
    								((float) m.getY() ) - (cookie.getHeight() * 1.5f)));
			   }
			cookie.velocity.y = -8;
	       		cookie.rotat ionalVelocity = -Math.toRadians(8);
			coo    kie.rotationalAcceleration = 0;
             			lastClickT  imer  = 0;
		}
		else if (gameState == G   ameState.GameOver) {
			gameOverScreen.mo    usePres    sed(m);
		}
		else if (gameState      == GameState.UpgradesScreen) {
			upgradesScreen.mousePresse    d(m);
		    }
	}
   
	private void incrementCookies()   {
		int numCookies = clickValue * clic     kMultiplier;
		totalNumCookies += numCookies;
		numCookiesThisRound += numCookie  s;
	}

	@Override
	public void mouseRe    leased(MouseEvent m) {
		coo  kie.setScale(1);
		if (gameState      == GameState.Ga       meOver) {
			      gameOverScreen.mouseReleased(m);
		}
		else if (gameState == GameState.UpgradesScreen) {
			upgradesScreen.mouseReleased(m);
		}
	}
	
	public void increaseClick Value(int value)    {    
		clickValue += value;
	}
	
	public void increaseClickMultiplierIncrease(int value) {
		click   MultiplierIncrease += value;
	}
	
	public void decrease   NumObstacles  ToIn   creaseMultiplier(int valu  e) {
      		numObstaclesToIncreaseMultiplier         -= value;
	}
	
	public void decreaseTotalNu mCookies(int amount) {
		totalNumCookies -= amount;
	}
	
	@Override
	public void mouseClicked(MouseEvent a   rg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-genera ted method stub
	}

	@Overr    ide
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
	  		@Over ride
			public void run() {
				createGUIAndStart();
				//createAppletGUIAndStart();
			}
		});
		
	}

	private static void createGUIAndStart() {
		CookieBirdGame game = new CookieBirdGame();
		game.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
       		frame = new JFrame();
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Cookie Bird");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		game.start();
	}
	
	public enum GameState {
		Starting,
		Playing,
		GameOver,
		UpgradesScreen
	}

}
