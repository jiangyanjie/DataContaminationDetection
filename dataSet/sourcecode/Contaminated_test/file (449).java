package restaurant.restaurant_davidmca.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import restaurant.restaurant_davidmca.DavidRestaurant;
import restaurant.restaurant_davidmca.Table;
import base.Gui;
import base.Time;
import city.gui.CityCard;
import city.gui.SimCityGui;

@SuppressWarnings({"serial","static-access"})
public class DavidAnimationPanel extends CityCard implements ActionListener {
	
	public static DavidRestaurant restaurant;

	private final int tableSize = 50;
	private final int WINDOWX = 500;
	private final int WINDOWY = 500;
	
	//ANIMATION
	private BufferedImage background;
	private BufferedImage table;
	
	/*
	 * Constructor
	 */

	public DavidAnimationPanel(SimCityGui city, DavidRestaurant rest) {
		super(city);
		this.restaurant = rest;
		setSize(WINDOWX, WINDOWY);
		setVisible(true);
		Timer timer = new Timer(Time.cSYSCLK / 40, this);
		timer.start();
		
		background = null;
    	try {
    	java.net.URL imageURL = this.getClass().getClassLoader().getResource("restaurant/restaurant_maggiyan/images/mybg.png");
    	background = ImageIO.read(imageURL);
    	java.net.URL imageURL2 = this.getClass().getClassLoader().getResource("restaurant/restaurant_maggiyan/images/mytable.png");
    	table = ImageIO.read(imageURL2);
    	}
    	catch (IOException e) {
    		//System.out.println(e.getMessage());
    	}
		
	}

	public void actionPerformed(ActionEvent e) {
		repaint(); // Will have paintComponent called
		synchronized (restaurant.guis) {
			for (Gui gui : restaurant.guis) {
				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, WINDOWX, WINDOWY);
		
		if(SimCityGui.GRADINGVIEW) {
			for (Table table : restaurant.tables) {
				g.setColor(Color.ORANGE);
				g.fillRect(table.getX(), table.getY(), tableSize, tableSize);
			}
        }
		else {
			if(background != null)
        		g2.drawImage(background,0,0,null);
			for (Table t : restaurant.tables) {
				g2.drawImage(table, t.getX(),t.getY(), null);
			}
		}
		
		for (Gui gui : restaurant.guis) {
			if (gui.isPresent()) {
				gui.draw(g2);
			}
		}
	}

}
