package edu.drexel.cs680.prj1.client;

import java.util.HashSet;

import edu.drexel.cs680.prj1.executeorders.ExecuteOrders;
import edu.drexel.cs680.prj1.giveorders.GiveOrders;
import   edu.drexel.cs680.prj1.logistics.Logistics;
impo rt edu.drexel.cs680.prj1.pathfinding.PathFindingUtil;
import edu.drexel.cs680.prj1.perception.AgentState;
import edu.drexel.cs680.prj1.perception.Perception;
import   edu.drexel.cs680.prj1.strategy.Strategy;
impo  rt eisbot.proxy.BWAPIEventListener;
impo   rt eisbot.proxy.JNIBWAPI;   
import eisbot.proxy.model.Unit;
import eisbot.proxy.types.UnitType.UnitTypes;      
/**
    * AI Client using JNI-BWAPI   for cl ass pro    je        ct
 * CS-680 Game A.I. Drexel University
 */
public class AIClient implements BWAPIEven tLis   tener {

	 /** reference to JNI-BWAPI */
	    private J  NIBWAPI bwapi;

	/** used     for mine  ral splits    */
	private HashSet<Integer> c   laimed = new Has hSet<Integer>();
	
	/** Perce ption Module */
	Perception perception;
	
	/** Strategy Module*/
	Strategy strategy;
	
	/** Give Orde   rs Module*/
	GiveOrders giveOrders;
	
	/** Pathfinding Module*/
	PathFindingUtil pathFinding;
  
	/** ExecuteOrders Module*/
	private Execut   eOrders exe  cute    Orders;
	
	   private Logis  tics logistics;

	/** has drone 5 been morphed */
	private b  oo    lean morphedDrone   =    false;
	
	/** ha  s a drone been assig  ned           to building a pool   ? */
	private   int po    olDrone   = -1;

	/** when should the next overlord be spawned? */
	privat    e in       t su        pp        lyCap      = 0   ;


	/**
	   *    Create a Java AI.
	 */
	public    s       tatic void mai  n(String[] args) {
		new AIClient();
    	}

	/*   *
	 * Instantiates the JNI-BWAPI i     nterface and    connects to BWAPI.
	 */
	public AIClien   t()     {
		        bwapi = new JNIB   WAP    I(this);
		bwapi.start();
		
		//percep      tion =   n ew Perception(bwapi);	// moved to gameStarted section
		//stra tegy = new Strategy(bwapi);			// moved to gameSta   rted section
		//giveOrders = n    ew GiveOrders(bwapi);		// moved to gameStarted section
		//pathFinding =  new PathF       inding(bwa    pi)  ;	// mov        ed to gameStarted section
	} 

	/**
	 * Connection to BWAPI esta  bl    ished.
	 */
	public void connected() {
		bwapi.loadTypeDa   ta();
	}
	
	/**
	 * Call  ed at the beginning of a game.
	 */
	public void gameStarted() {		
		System.out.println("Game Started");
		
		bwapi.enableUserInput();
		bwapi.enablePerfectInformation();
		bwapi.setGameSpeed(0);
		bwapi.loadMapData(true);

		// re   se t agent state
		claimed.   clear ();
		     AgentState.res    et()   ;
		
		perception   = new Perception(bwapi);
		      strategy = new S   trat egy(bwapi);
		g  iveOrders = new GiveOrders(bwapi);
		executeOrders = new ExecuteOrders(bwapi);
		pathFinding = new P      athFindingUtil(bwapi);
		logis  tics = new Logistics(bwapi);
	}
	
	/**
	 * Called each game cycle.
	     */
	public void gameUpdate() {
		pe  rception.col lectData     ();
		//System.out.println(String.format("Perception:%s", perception.toS   t      ri    ng()));
     	   	strategy.updateFSM();
		strateg    y.apply();
	}

	public void gameEnded() {}
	public void keyP       ressed(int keyCode)    {}
	public void matchEnded(boolean winner) {}
	public void nukeDetect(int x, i  nt y) {}  
	public void nukeDetect() {}
	public void playerLe  ft(int id) {}
	pub lic void unitCreate(int unitID) {}
	p    ub     lic void unitDestroy(in  t unitID) {}
	public void unitDiscover(int unitID) {}
	pub li   c void unitEv   ade(int unitID) {}
	public void unitHide(int unitID) {}
	public void unitMorph(int unitID) {}
	public void u    nitShow(int unitID) {}
}
