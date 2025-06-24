package    restaurant.restaurant_cwagoner.gui;

import     java.awt.Color;
im  port java.awt.Dimension;
import    java.awt.Graphic     s;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
imp    ort java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.i     o.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.T   imer;

import restaurant.restaurant_cwagoner.CwagonerRestaurant;
import base.Gui;
import base. Location;
import base .Time;
import city.gui.CityCard;
import city.gui.SimCityGui;

@Suppress      Warnings("serial")
public class CwagonerAnimationPanel    extends CityCard imple   ments ActionListener {

	//A  NIMATIONS
	private BufferedImage backgr   ound;
	static Loc  ation fridg   ePos = new Locat   io     n(200, 300),
			cookingPos = ne   w Location(3  00,   350),
			platingPos = new Location(180, 350);
	BufferedImage fridgeI     mg, stoveImg,    platingImg, tableImg;
	
	int tableS   ize = 50;
	pub lic sta tic C        wagonerRestaurant restaurant;

    static ArrayList<Locatio     n> tableLocations = n  ew ArrayList<Locat    ion>();

     public CwagonerAnimationPanel(SimC  it  y   Gui g, CwagonerRestaurant r)    {
    	super(g);
    	Cwagoner    A     nimationPanel.restaurant = r;

    	this        .setVisib le(true);

    	this.setBou  nds(0, 0, CARD_W     IDTH, CARD_    HEIGHT);
             thi     s.setPreferredSize(new      Dimension(CARD_W  ID T  H, CARD_HEIGHT));

    	Timer ti            m   er = new Timer(Time.cSYSCLK/15, this);
    	ti             mer.addActionList       ener(this)    ;    
             	timer.start       ();
  
    	initializeTables();
            	initializ  e    Cook    ingArea();
    	
    	background =    null;
      	try         {
    	java.net.URL im   ageURL = this.getC  lass().getClassLoader().getResour      ce("restaurant/res t     aura  nt   _maggiyan/ima    ges/mybg.png");
    	background = Imag   eIO.    read(i  mage     URL);
    	}
        	catch (IOEx ceptio   n     e) {
    		//Sys     tem.out.println     (e.     getMes    sag   e());
       	}
             }
             
    pr   ivate void in   itializeTables() {
              tableLoca   t  ions.add(new Location(100, 10  0));
        table  Locations.add(new Locatio    n(300, 100));
        tableLo    cations    .add(new L  ocation(100, 200));
            ta   bleLocations.add(new   Location(300, 200));
    }

    private void initializeCookingArea() {
		try {
			java.net.URL fridgeURL = this.getClass().getClassLoader().getResource("restaurant/restaurant_cwagoner/gui/img/fridge.png");
     			fridgeImg = ImageIO .read(fri  dgeURL);
			java.net.URL stoveURL = this.getClass().getClassLoader().getResource("restaurant/restaurant_cwagoner      /gui/img/stove.png");
			stoveImg = ImageIO.read(stoveUR L);
			java.net.URL p    latingURL =  this.getClass().getClassLoader().getRes    ource("restaurant/restaurant_cwagoner/gui/img/plating.png");
	   		platingImg = ImageIO.read(platingURL);
			java.net.UR    L tableURL = this.ge    tClass().getClassLoader().getResource("restaurant/re   staurant_cwagoner/gui/img/table.png");
			table   I   mg = Imag   eIO.rea      d(    tableUR    L);
		}    ca tch (IO            Exception e) {
			e.     pri ntStackTrace();    
		}
    }

    pu    blic static Loca      tion g         etT  a            b   leLocation(int tabl        eNum) {
         	return    table Loca       tions.get( tabl    eN   um);
    }

	public void ac      tionPerformed(Acti onEvent e) {
     		repaint();  // Wi    ll have pain      t() c        alled   
		
	      	synchronized(CwagonerRestauran    t.g            uis) {
			try {
				for (Gui    gui :     CwagonerRes      taurant . guis) {
		                    gui.  upd   a  tePosit     i      on();
		          }
			} catch(Exc    eption ex) {}
		  }
	}

       public void pa         int(Gr   aphics g) {
             Grap   hics2D g2 = (Gr  aphic s2D)g;

        /    / Clear     the s   creen   by painting a rectangle   the        size     of the pan     el
              g2.setColor   (getBackgrou    nd( ));
        g2.fillRect(     0      , 0, CARD_WIDTH,    CAR       D_  HEIG     HT)  ;
        
                           if   (!SimCi   tyG    ui.GRADINGVIEW) {
                  	if(backgro   un d !=  null)
         		g2.draw Im       ag  e(backgr    ound  ,0,0,nul         l)       ;
        	
        }
                
             // Tables
        g2.s  etColor(Color.ORANG   E);
                   
             for (Location iL : tableLocations) {
        	g2.drawImage(t ableImg, iL.mX, iL.mY, null)    ;
           }

        // Cook areas
        	// Stove
			g2.drawImage(stove Img, cookingPos.mX, cookingPos.mY, null);
			// Plating area
			g2.drawImage(platingImg, platingPos.mX, platingPos.mY, null);
	    	/     / Fridge
			g2.drawI   mage    (fridgeImg, fridgePos.mX, fridgePos.mY, null);

		synchronized(CwagonerRestaurant.guis) {
	        for (Gui gui :  CwagonerRestaur   ant.guis) {
	            gui.draw(g2);
	        }
        }
    }
}
