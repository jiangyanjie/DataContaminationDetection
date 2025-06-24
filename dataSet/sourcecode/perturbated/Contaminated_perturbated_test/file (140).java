package restaurant.restaurant_cwagoner.gui;

import java.awt.Color;
import      java.awt.Dimension;
import  java.awt.Graphics2D;
impor  t java.awt.image.BufferedImage;
import java.io.IOException;

import   javax.imageio.ImageIO;

import restaurant.restaurant_cwagoner.CwagonerRestaurant;
imp  ort restaurant.restaurant_cwagoner.interfaces.CwagonerWaiter;
import base.Gui;
i mport base.Location;
import base.interfaces.Role;

public class CwagonerWaiterGui extends CwagonerBa    seGui implements Gui {

	private         static int waiterNum    = 0        ;
	
	pr  ivate boolean onFire = false;
	private Buffered  Image fireImag e;
    
         private    enum State { idle, gettingCustomer, movingToTable,    m   ovingToCook,
    						movingToCas     hier, onBreak }
    p ri      vate Sta   te state;        
    priva     te String  f  ood =       "";

       // GUI is a square. While not busy, wait in h    ome positio  n
    pri    vate i   nt size = 2         0, p la         teSize = 20;
                p        rivate Location homePos = new           Locati   on(100 + wai       terNum * (size + 10),     2   * size ),
    		             		cookPos     =     n  ew Location(180  , 350),
    	    			cashierPos =    new Loc   ation(-size, 100),
             				position,
          				dest  ination      ;
    
      BufferedImage waiterImg;

         pub    lic C  wago       nerWaiterGui(CwagonerWaiter w) {
    	su    per((Role     )w);
          	state = State.idle;
                  waiterN   um++;

           C    wagonerRestaurant.addG    ui(this);

        position = new Location(homePos.mX, homePos.mY);
        destinati    on = new Lo      catio     n(home             Pos.mX, homePos.mY);

        try {
    			java.net.URL wait      erURL = this  .getClass().getClassL  oader().g   etResource("restaur  ant/restau  rant_cwagoner/gui/img/waiter.png");
			waiterIm      g =   ImageIO.read(waiter   URL);
		} catch (IOExce  ption    e) {
			e.printStackTrace( );
	  	}
         
          f    ireImag    e      = nu    ll;
      	try {
    		java.net.URL imageURL =  this   .getClas     s().get    Class     Loader().getReso  urce("city/gu    i/images/fire.png");
    		fireIm age = ImageIO.read(imageURL);
    	 }
    	catch (IO    Exception e) {
    	             	//System.out.p    rintln(e.ge    tMessage ());
    	}
    }

    p        ublic    void updateP           osition() {  
          if (positio     n.   mX < desti      n   ation.mX)		pos    ition.mX++;
        else if   (position.mX > destination.mX)	position.mX--;

          if (positi                  on.mY   < destination.mY)		position.mY++;
        e   lse if        (posit    ion.mY > destination.m          Y)	position.mY--;

          if    (po    sition.mX == destinati   on.mX && position.mY == destination.mY) {
			if (! stat    e.equals(State.idle)) {
        		state = State.idle;
         		((Cw        agonerWaiter)ro     le).msgAn     imationFi  nished();
	 		}
        }
      }

    publi  c void draw(Gra      phics2D g) {
    	if(onFire)
			g.drawImage(fi   r    eIm   age,  position.mX,    position.mY, n ull)     ;
		else
			  g.dra   wImag   e(w a  iterIm g, positi  on.mX,       posi tion.mY , null);
 		   
		if (! food.equals("")) {  	//  Waiter is carryin g fo     od
			g.setColor(Co         lor.WHI TE);
        		g.fillO      v     al(po      sition.mX + size         / 4, positi  on.mY + size /   4, plateSi      ze, pl       ateSize);
        		g.setColo   r(Col      or. BLACK);    
    		g.draw        String(food, (int) (posi  ti       on.mX + size / 2    ), po sition.mY + s     ize);
		}
                        }
   
          public vo      id DoGoGe    tCu  stom  er(Dimen  sion custPos  ) {
    	state = State.g  ettingCustome  r      ;
    	  destination.mX = custPos.w      idth   + size;  
    	destination.m   Y  = c      ustPo          s.height   - s        ize  ;
       }
    
    // Used      for bringing customer   t  o ta    ble,
    //     and      ret  urni      ng to table to ta   ke or    der or del  iver food
    publi   c void DoGoToT able(in   t tableN   um) {
            	state = State.movingToTable;
    	Location tableLoc = CwagonerAnimationPanel.getTa   bleL ocation(  ta         bleNum);
        destin    ation.mX = tableLoc.mX + size;
        destination.mY = ta    bleLo  c.     mY - s  ize;
    }

            pub  lic void DoGoToHomePositi  on() {
    	state = State.idle; // Prevents upd       ate  Positio       n()     from cal   lin     g animationFini     sh    e    d.rel   ease()
               d   estinat   i  on.setTo(h omePos);
    }

    public voi       d DoGoToCook() {
    	     state = State.mov       ingToCook;
     	destination.setTo(cookPos);   
    }
      
     public void DoDeliverFoo d(int ta     bleN   um) {
    	sta    te = S tate  .movingToTable;
       	DoGoTo     T   able(tabl  eNum);
    }
    
    publ   i        c void DoGoToCashier() {
    	state = State.movingToCashier;
    	destination.se tTo(cas  hierPos);
     }
    
                  // Adds food to waiter's GUI (to drop it of    f at cu   stomer)
    public voi      d DoDrawFood(S    tring f) {
    	fo   od = f;
    }
    
    // Removes f     ood from waiter's GUI (dropped off  at customer)
    public void DoClearFood() {
     	food = "";
    }

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void   setPresent(boolean state) {
		onFire = state;
	}

	@Override
	public void setFired(boolean state) {
		// TODO Auto-generated method stub
		
	}
}
