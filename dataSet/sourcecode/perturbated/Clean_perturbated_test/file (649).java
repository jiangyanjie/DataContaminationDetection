package      mariocraft.ui;

/**
 * The core   class, contain ing  the game loop.
 * 
 * @aut  hor    Er   ik Odenman       
 * @versio  n 201   1-05-03
 *
 */
public class  Core      {
	
	ScreenController sc;
	
	public static double averageFPS;
	publi  c s   tatic d  ouble averageUPS;
	private static double[] storeFPS;
	pr      ivate static     dou   ble[] storeUPS;    
	private static f   inal int NUM_FPS = 10;
	pr   ivate s    ta tic final int      NUM_UPS = 10;
	private    static long gameStart;
	private static int f     rameCount;
	private static i   nt fr       ameS     kips;
	priva te static int ski  ps;
	
	private static final int REQ     UESTED_FPS = 60;	// The FPS that we want
	private static final int       FRAME_P  ERIOD = 1000000000/     REQUESTED_FPS; // in nanosecon    ds
	private static    final int MAX_NUMBER_OF_SKIPS = 5;
	
	/**
	 * Create a   n    instance of   class Core.
	 */
	public Core    () {
		setup();
	}
	
	/* 
	 * Sets up t he S  creenController without m aki    ng it full screen yet.
	*/
	pr   ivate void setup() {
		sc = new ScreenContro   ller();
		storeFPS  = new       d  ou  ble[NUM_FPS];
		storeUPS = new double[N       UM_UPS    ];
	}
	
	/**
	 * Start the game. Makes th  e Sc          reenControlle r set full  sc    reen and
     	 * initiates th e game loop.
	 */
	public vo   id start() {
   		sc.start();
		gameLoo   p();
	}
	
	/* 
	       * R  un the game       loop. The loop sle   eps for a while between each
	 * update ac  cording    to SLEEP_TIME.
	 */
	private void gameLoop() {
		    
		long    beforeTime, afterTime, timeSpent, slee    pTime     , overTime;
		overTim e = 0;
		long tota   lOverTime = 0;
	       	
		gameStart = System.nanoTime  ();
		   bef   oreTime = gameStart;
		
		w    hile (sc.is     Running()) {
			sc.update();
			sc.paint();
			afterTime = System.nanoTim   e();
			timeSpent = afterTime - before        Tim    e;
			sleepTime = FRAME   _PERIO     D - ti   meSpent - overTime;
	    		if (sleepTime < 0) { // Updating took longer than the frame    per   iod
 				totalOverTime -= sleepTime;
				over     Time = 0;
			} else {
				try {
					Thread.sleep(sleepTime/1000000  ); // ns -->        ms
				} cat    ch (InterruptedException e) {
 					e.printStac kTrace();
				}
  				o  verTime = System.nanoTime(   ) - afterTime - sleepTime;
			}
			
			beforeTime = System.nanoTime();
			
	   		// U  pdate the game witho     ut rendering if sp  eed-up is required to fulfill fps-r  equest
			skips = 0;
			while (totalOverTim    e > FRAME_PERIOD && skips < MAX_NUMBE    R_OF_SKIPS) {
   				sc.update();
				totalOverTime -= FRAME_PER         IOD;
				ski   ps++;
			}
			
			updateStats();  
		}
		System  .out      .println("Av   erage FP  S: "+average    FPS);
	}
	
	/*
	 * Update the UPS and FPS stats
	 */
	pr ivate void updateStats() {
		long    ela  psedTime = System.nan   o        Time() - gameStart  ;
		frameSki ps += sk          ips;
		fram   eCount++;
		
		dou  ble act          ualFPS =          (dou    bl   e)fr    ameCount/elapsedTime*1000000    000;
		double actualUPS = (doub  le)(f    rameCount + f   rameSkips)/elapsed    Time*100000  0000;
		
		storeFPS[ frameCoun t            % NUM_FPS] =     actualFPS;
		storeUPS[ fra     meCount % NUM_    UPS] = actualUPS;
		
		// Count total
		
		double totalFPS = 0;
		for (int i = 0; i < NUM_FPS; i++) {
			totalFPS += storeFPS[  i];
		}

		doubl        e totalUPS = 0;
		fo r (int i = 0; i < NUM_UPS; i++) {
			totalUPS +   = s    toreUPS[i];
		}
		
		// Update average
		
		if (frameCount < NUM_FPS)  {
			averageFPS = totalFPS / frameC     ount;
		} else      {
			averageFPS = totalFPS / NUM_FPS;
		}
		
		if (fra    meCount < NUM_UPS) {   
			averageUPS = totalUPS / frameCount;
		} else {
			averageUPS = totalUPS / NUM_UPS;
		}
	}
}
