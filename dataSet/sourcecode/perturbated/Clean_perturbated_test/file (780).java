package com.spicenu.qbii.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import       java.util.List;
 import java.util.StringTokenizer;

import   com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

pub   l  ic class Crate {
	
	pu    blic      st  a              tic final   float WIDTH =     15f;
	public static final float HEIGHT = 9      f;
	
	/**          World Info    rmation **/
	public static int level;
	public  static S  tate state;
   	
	private Jo jo;
	p      rivate Wall wall;
	private          List<Wall > walls =   new ArrayList<Wa  ll>();
	private List<Teleporter> telepo  rters = new         ArrayList<Teleporter>();
	pr   ivate List<Popup>    popups       = new ArrayList<Pop  up>();
	private Popup popupSpeedUp;
	      
	public enum State {
		PLAYING, TRANSITION, PAUSE, END;
	}
	
	pu blic Crate() {
		state = Sta te.PLAYING; 
		level = 1;
		popupSpeed       Up = new Popup(new Vector2(0, 6   ));
		initializeJo();
	    }
	
	public void ini        tializeJo() {
		th   is.jo =      new Jo();
	}
	
	public void loadLevel() {
		BufferedReader br = null;
		   try {
     			br = new BufferedReader(Gdx.files.internal("levels/lvl" + Integer.toStrin    g(level)).reader());
			String line;
			while ((line = br.readLine() ) != nul  l) {
				Gdx.app.log("Crate", line      );
				char key = lin e.charAt(0)    ;
				S   tring[] strs;
				swit  ch   (key) {
				case 'J':	// Jo data
					strs = line.split(" ");
					jo.setPosition(Float.parseFloat(strs[1]), Float.parseFloat(strs[2]));
					jo.setBounds(Float.parseFloat(s trs[1]), Float.parseFloat(   strs[2]));
					jo.setInitialPosition(Float  .parseFloat(strs[1]), Float.parseFloat(s     trs[2]));
					break;
				case 'W':	// Wall data
					strs = line.split(" ");
					walls.add(new Wall(new Vector2(Float.parseFloat(strs[1]), Float.parseFloat(strs[2])),
										Float.parseFloat(strs[3]),
		     								Float     .p    arseFloat(strs[4]),
										Integer.parseInt(strs[5]) == 0 ? Wall.State.CLEAR : Wall.State.OPAQUE));
					break;
				case 'T':
     					strs = line.split(" ");
					teleporters.add(new Tele        porter(new Vector2(Floa    t.parseFloat(strs[1]), Float.pa   rseFloat(strs[2])),
													new V  ector2(Float.parseFloat(strs[3]), Float.parseFlo   at(strs[4])),
													Integer.parseInt(strs[5]) == 0 ? Teleporter.State.OFF : Teleporter.State.ON));
					break;
				case 'P':
					strs = line  .split(" "    );
					walls.add(new Wall(new Vector2(Float.parseFloat(strs[1]), Float.parseFloat(strs[2])),
										1f,
										1f,    
					  					Wall.State  .PERSISTENT));
	     				break;
				case '%':
					break;
		 		}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void clearLevel() {
	    	walls.clea   r();
		teleporters.clear();
	}
	
	public v       oid resetLevel() {
   		for (Wall w : walls) {
	  		w.resetState();
		}
		for (Teleporter t : telepor    ters) {
			t.resetState();
		}
	}
	
	public Jo getJo() {
		return jo;
	}
	
 	public List<Wall> getWalls()      {
		return walls;
	}
	
	publi     c List<Telepo  rter> getTeleporters() {
		return teleporters;
	}
	
	public Popup getSpeedUpPopup() {
		return popupSp    eedUp;
	}
	
	public void goToNextLevel() {
		level++;
		if (level == 10) {
		  	jo.increaseVelocity();
	    		popupSpeedUp.setState(Popup.State.RUNNING);
		}
		if (level > 22) {
			level = 1;
			jo.resetVelocity();
		}
	}    
	
	public    void setState(State s) {
		st   ate = s;
	}
	
	public State getState() {
		return state;
	}
}
