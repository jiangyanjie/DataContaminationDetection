package com.spicenu.qbii.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Crate {
	
	public static final float WIDTH = 15f;
	public static final float HEIGHT = 9f;
	
	/** World Information **/
	public static int level;
	public static State state;
	
	private Jo jo;
	private Wall wall;
	private List<Wall> walls = new ArrayList<Wall>();
	private List<Teleporter> teleporters = new ArrayList<Teleporter>();
	private List<Popup> popups = new ArrayList<Popup>();
	private Popup popupSpeedUp;
	
	public enum State {
		PLAYING, TRANSITION, PAUSE, END;
	}
	
	public Crate() {
		state = State.PLAYING; 
		level = 1;
		popupSpeedUp = new Popup(new Vector2(0, 6));
		initializeJo();
	}
	
	public void initializeJo() {
		this.jo = new Jo();
	}
	
	public void loadLevel() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(Gdx.files.internal("levels/lvl" + Integer.toString(level)).reader());
			String line;
			while ((line = br.readLine()) != null) {
				Gdx.app.log("Crate", line);
				char key = line.charAt(0);
				String[] strs;
				switch (key) {
				case 'J':	// Jo data
					strs = line.split(" ");
					jo.setPosition(Float.parseFloat(strs[1]), Float.parseFloat(strs[2]));
					jo.setBounds(Float.parseFloat(strs[1]), Float.parseFloat(strs[2]));
					jo.setInitialPosition(Float.parseFloat(strs[1]), Float.parseFloat(strs[2]));
					break;
				case 'W':	// Wall data
					strs = line.split(" ");
					walls.add(new Wall(new Vector2(Float.parseFloat(strs[1]), Float.parseFloat(strs[2])),
										Float.parseFloat(strs[3]),
										Float.parseFloat(strs[4]),
										Integer.parseInt(strs[5]) == 0 ? Wall.State.CLEAR : Wall.State.OPAQUE));
					break;
				case 'T':
					strs = line.split(" ");
					teleporters.add(new Teleporter(new Vector2(Float.parseFloat(strs[1]), Float.parseFloat(strs[2])),
													new Vector2(Float.parseFloat(strs[3]), Float.parseFloat(strs[4])),
													Integer.parseInt(strs[5]) == 0 ? Teleporter.State.OFF : Teleporter.State.ON));
					break;
				case 'P':
					strs = line.split(" ");
					walls.add(new Wall(new Vector2(Float.parseFloat(strs[1]), Float.parseFloat(strs[2])),
										1f,
										1f,
										Wall.State.PERSISTENT));
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
		walls.clear();
		teleporters.clear();
	}
	
	public void resetLevel() {
		for (Wall w : walls) {
			w.resetState();
		}
		for (Teleporter t : teleporters) {
			t.resetState();
		}
	}
	
	public Jo getJo() {
		return jo;
	}
	
	public List<Wall> getWalls() {
		return walls;
	}
	
	public List<Teleporter> getTeleporters() {
		return teleporters;
	}
	
	public Popup getSpeedUpPopup() {
		return popupSpeedUp;
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
	
	public void setState(State s) {
		state = s;
	}
	
	public State getState() {
		return state;
	}
}
