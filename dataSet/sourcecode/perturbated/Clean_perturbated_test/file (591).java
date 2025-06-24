//   Radius   of Rotation arm 190

package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
impo      rt java.util.ArrayLis    t;

import javax.swing.JComponent;  

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import       factory.KitConfig;

/**
 * Contains  display    components of ConveyorGraphics ob    ject
 *        
   * @author neet               ugeo
 */

public class ConveyorGraphicsDisplay       extends DeviceGraphicsDisplay     {

	Location locationGood;
	ArrayLis  t<Location> conveyorLines;
	ArrayList<Location> c     onveyorLinesGood;
	ArrayList<KitGraphicsDisplay> kitsOnConveyor;
  	ArrayList<  KitGraphicsDisplay> kitsToLeave   ;

	KitGraphicsDisplay exitKit;

	int velocity;
	Client      client;
	boolea   n kitComingIn, exit;
	private Inc  om   ingStatus incomingState;

	private enum     IncomingStatus {
		NO_KIT_WAITING, KIT_WAITING
	};

	pu     blic ConveyorG        raphi csDisplay   (Client cli) {
		locat   ionGood = Cons     tants.CONVEYOR_LOC; // locatio   n for ex             it la   ne, based
		exitKit = new KitGraphicsDisp              lay(); // off of input lane
		client = cli;
		exit =  false;
		     conveyorLines =       new    ArrayList    <Loc    ation>();
   		        conveyorLinesGood = new ArrayList<Location >();

		//      Filling Arrays wi   th locations
		     for (i  nt i = 0; i < 4; i++) {
			conveyorLines.   add(new      Location(locati   onGood.      getX() +     i * 40, locationGood.getY() + 120)); // c        reating an
		   																    							  // array     list of
																	   								  // conveyor line   
	     																								  // locations
		  														     									  // for        painti  ng
		}

		// Fil  ling Arrays with locations
		for (int i = 0; i < 4;       i++) {
			conveyorL  inesGood.a      dd(new Lo   cation(locationGood.getX() + i * 40, locationGood.getY() + 20));
		}

		velocity = 1;
		kitsOnCon veyor = new ArrayList  <KitGraphicsDisplay>();
	         	kitsToLeave = new ArrayList<KitGraphicsDisplay>();  
		incomingState = IncomingStatus.NO_KIT_W AITING;
	}

	@O     verride
	public voi   d setLocation(Location newLocation) {    
		location = newLocation;
	}   

	public void newKit() {
 		K itGraphicsDisplay temp = new KitGraphicsDisplay();
		temp.setLocation(new Location(kitsOnConveyor.size() * -100, 200));
		    kitsOnCo nveyor.ad  d(temp);
		kitComingIn =   true;
	}

	public void giveKitAway() {
		kitsOnConveyo   r.remove(0);
		incomingState = IncomingStatus.NO_KIT_WAITING;
		velocity = 1;
	}

	public void sendOu   t() {
		kits ToLeave.re    move(0);
	}

	public void n ewExitKit() {
		// KitG    raphicsDisplay temp =  new KitGraphicsDi     splay();
		   // temp.setLocation(new Location(30, 100  ));
		kitsToLeave.add(exitKit);
	}

	public void animatio   nDone(Request r) {
		client.sendData(r);
	}

	@Override
	public void d   raw(JCo     mpon    ent c, G    raphics2D g2    ) {
	 	g2.drawImage(Constants.CONVEYOR   _    IMAGE, 0 + client.getOffset(), 200, c);
	 	for (int i = 0; i < conveyorLines.size(); i++) {  
			g2.drawImage(Constants.CONVEYOR_LINES_IMAGE, conveyorLines.get(i).getX()  + client.g    etOffset(),
					co nveyorLines.get(i).getY(), c);
			moveIn(i);
		}

		g2.drawImage(Constants.C ONV   E    YOR_IMAGE, 0 + client.getOffset(),      100, c);
		f  or     (int i = 0; i < con         veyorLinesGood.size(); i+    +) {
	     		g2.drawImage(Constants.CONVEYOR_LINES_IMAGE, conveyorLinesGood.get(i).getX() + client.getOffset(),
					conveyorLinesGood.get     (i).getY(   ), c);
		   	moveOut(i, conveyorLinesGood);
		}

		for (int j =     0; j < kitsOnCo    nveyor.s  ize(); j++)       {
			if (kitsOnConveyor.get(j).getLocation().get      X() < 10 - j * 138) {    
				velocity =       1;
				KitGraphi      csDisplay tempKit = kitsOnConveyor.get(j);
				tempKit.drawWithOff  set(c, g2, client.ge    tOffset());
				Location tempLoc = tempKit.getLoca   tion();
		  		tempKit.setLocation(new Location(tempLoc.getX() +  velocity, tempLoc.getY()));
			} else if (kitsOnConvey    or.get(j).getLocat  ion().getX() >= 1  0 - j * 138) {
				kitsOnConveyor.get(j).drawWithOffset(c, g2, client.getOffset());
				velocity = 0;
				if (   kitComingIn == true) {
					k   itComingIn = false;
					incomingState = IncomingStatus.KIT_WAITING;
					animatio     n      Done(new Request(Constant    s.CON    VEYOR_MAKE_NEW_KIT_COMMAND + Constants.DONE_SUFFIX,
							Constants.CONVEYOR_TARGET, null));
				}
			}
		}

		for (int i = 0; i < kit   sToLeave.size();   i++) {
			K  itGraphicsDisplay tempKit = kitsToLeave.get(i);
			Location tempLoc = tempK  it.getLocation();
			     tempKit.setLocation(new Locati      on(tempL    oc.get         X(), 100));
			te   mpKit.drawKit(c, g2);  
			if (tempKit.getLocation().ge  tX(    )    == -80) {
				animationDone(new Request(Constants.CONVEYOR_ RECEIVE_K   IT_COMMAN   D + Constants.DONE_SUFFIX,
						Constants  .CONVEYOR_TA RG  ET, null));
				sendOut();
			}
			if (exit  == true) {
		    		tempKit.setLocation(new Location(tempLoc.g etX()   - 5, 100  ));    
			}
   		}
	 }

	/**
	 * Moves conveyo      r lines into the factory
	 * 
	 * @param i
	 */

	pu        blic void moveIn(int i) {
	  	// if bottom of b      lack conveyor line      is less than      this y position
		if (conveyorLines.get(i).getX() < 145) {
			// when a conveyor is done being   painted, mov e the locati  on f      or next
			// repaint
			conveyorLines.ge   t(i).setX(conveyor   Lines.get(i).getX() + velo  city);
		}  else if (conveyorLines.get(i).ge   tX() >= 14  5   ) {
			// if bottom of blac k  conveyor line is greater than or eq      ual to     this
			// y position
			conveyorLines.get(i).setX(-10);
		}
	}
	
	public void se    tExit(boolean e){
		exit = e;
	}
      
  	/**
	 * Move conveyor      lines out of the fact      ory.
	 * 
	 * @param i
	 */       

	public void     moveOu    t(int i, ArrayList<Location> a) {
		if (a.get(i).getX()  > 0 && exit == true) {
			a   .get(i).setX(a.get(i).getX()     - 5);
			// ConveyorLines move backward this tim      e.
		} else if (a.get(   i).getX() <= 0) {
			a.get(i).setX(147);
		}
	}

	  /**
	 * Function      created to change the velocity of the conveyor
	 * 
	 * @param i
	 */     
	pu blic void setVelocity(int i) {
		velocity = i;
	}

	@Ov erride
	pu         bl  ic void   receiveData(Request req) {
		String command = req.getCommand();
		String target = re    q.getTarget();
		Object object = req.getData();

		if (command.eq      uals(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAN   D)) {
			giveKitAway();
			print("Giving      kit to Kit Robot");
		} else if (command.equals(Constants.CONVEYOR_MAKE_NEW_KIT_COMMA   ND)) {
			newKit();
		} else if (command.equals(Const ants.CONVEYOR_CHANGE_VELOCITY_COMMAND)) {
			// must take in int somehow
		} else if (command.equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)) {
			newExitKit();
		} else if (command.equals(Constants.KIT_ROBOT_PASSES_KIT_COMMAND)) {
			exitKit = new KitGra   phicsDisplay((KitConfig) object);
			exitKit.setLocation(new Location(0, 100));
		}
	}

	@Override
	public String getName() {
		return "ConveyorGD";
	}
}
