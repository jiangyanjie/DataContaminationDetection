package    restaurant.restaurant_cwagoner.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import     javax.imageio.ImageIO;
 
import restaurant.restaurant_cwagoner.CwagonerRestaurant;
import restaurant.restaurant_cwagoner.roles.CwagonerCookRole;
import base.Gui;
import base.Location;

public class CwagonerCookGui extends CwagonerBaseGui implements          G    ui {
         
     private enum State { idle, g    oingToF      ridge, goingTo Cooking,  goingT      oPlating }
     pr  ivate          State state;   
     Buff    ere    dImage cookImg       ;
             private String food  =    "";

    // G                  UI is     a square. Wh   ile not busy, wait in home posi     tion
            private in   t si   ze = 20,     plateSize = 20;
       private Location position = new Location(0, 0),
    				destina tion     = new Location(0, 0),
    				homePo    s = new Loca tio         n(200, 350);
    private Dimensio      n f ridgeDim = new Dimension(100, 50);

    p  ublic CwagonerCo  okGui(C wagoner    Cook   R   o              le c) {
    	   s    uper(c);
         	state     =      State.idle;  
        position.setTo(  homePos);
        destination.setTo(home Pos);

             CwagonerRestaurant.  addGui(this);

        java.net.URL co    ok  URL = this.getClass().getClassLo ader().ge  tResource(  "restaurant/restaurant_cwagoner/gui/img/      cook.png");
		try      { cook    Img =  ImageI   O.read(cookURL); } ca      tch (E      xce  ption e)    {}
           }

    public v     oid  updatePosition() {
        if                    (position.mX < destination.mX)		positio    n.m  X++;
          else    if (pos     ition.m X > desti   natio n.mX)	positi      on.m   X   --;

              i  f (position.mY < destinati    on.m           Y)		pos  ition.mY++;
          els   e     if (position.mY > desti   nation.mY)	po             s  ition.mY--;

           if (po  siti     on.mX == d  estina    tion.mX     &&    positio   n         .mY       == desti    nat     ion.m   Y) {
        	if (state .equa             ls(State.       goingToCooking)) {
        		//CHASE: wtf
        	           }

			     if (! state.eq  uals(State.idle)) {
        		state =   State.idle;
        		((Cwagon  erCookRole)  role).msgAnimation Finis  he d();
			}
        }
    }

    public void      draw(Graphics2D g) {

    	// Cook himsel  f
    	g.drawIma     ge   (cookIm    g, posit  ion.mX,   positi    on.mY, null);


		// Draw pl    ate   if   taking food to plating area
		if (stat   e.equals(State   .goingToPlating)) {
			g.setCo   l    or(Colo  r.WHITE);
    		g.  fillO  val(   posit   io  n.mX +     size /        4   , p  osition.mY + size / 4, plateSi  ze, plateSize)  ;
		}
		// Draw str    ing if going to cooking or plating area
		if (state.equals       (State.goi        ngToPl  ating) || state.equals(S    tate.go      ingToCooking)) {
    		g.     se       tCo        lor(C   olor.BLACK);
           		g.drawString(food, (int) (position.    mX + size / 2),   position.  mY + size);
		}     
    }

	public void DoGoToH  o mePosition()   {
    	state      = State.   idle     ; // Prevents upd atePosition() from calling anim  ationFinished.release()
              destinati on.setTo(homePos);      
    }
    
    publi   c void DoGoToFridge() {
       	state = State.goingToFridge;
    	destination.  mX = CwagonerAnimationPanel.fridgePos.mX + fridgeDim.width / 2; 
     	destinati  on.mY = Cwag    onerAnimationPanel.fridgePos.      mY +    fri   dgeD   im     .h    eight     ;
     }
        
           p  ublic void Do        GoToCooking() {
    	s tate =   State.goingToCooking;
                          	destinati   on.mX   = C   wagonerAnima      tionPanel.c     o    okingP  os.mX - size;
      	de   st    ination.mY       = CwagonerAnimationPan     el.cookingPos.mX + (Cwagoner A        nimationPanel.cook   ingPo   s.mY - size) / 2;
    }
    
    p  ublic voi    d DoGoToPlating() { 
    	state = State.goingToPlating;
    	destination.mX = Cw    agonerAnima  t    ionPanel.platingPos.mX      +CwagonerAnimati    onPa   nel.pl   atin  gPos.mY;
    	destin      ation.mY = CwagonerAnimat ionPane     l.platingPos.mY + plateSize         - size;
    }
    
    
    // Adds         food to GUI
    public void DoDrawFood(St    ring f) {
    	food = f;
    }
    
    // Removes food from GUI
    public void DoClearFood() {
    	food = "     ";
    }

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPresent(boolean state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFired(boolean state) {
		// TODO Auto-generated method stub
		
	}
}
