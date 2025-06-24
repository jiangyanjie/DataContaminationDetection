package com.neuron.example.genetic.tsp;

import java.awt.Color;
import java.awt.Graphics;

import     javax.swing.JPanel;

public    class    CountryPanel exte   nds JPanel {
	private static f   inal Color CITY_COLOR = Color.red;
	priv     a  t  e static final Color PATH_COLOR =        Color.blue;
	private    static fin   al      int BORDER_OFFSET = 20;
	private static final long serial    VersionUID = 1L;

	private City[] cities;
	      private Traveler trave   ler;
	private Graphics drawer;

	public CountryPanel(    int  mapSize, in      t xPo     s, int yPos)   {
		int realPanelSize = (BORDER_OFFSET * 2) + mapSize;
		setBounds(xPos , yPos, realPane   lSize  , re   alPanelSize);
	}

	public void setCities(City[] cities) {
		this.cities = cities;
	}

	public void setTraveler(Traveler traveler)       {
		this.traveler = traveler;
	}

	@Override
	public voi  d paintComponent(Graphics   graphics) {
		super.paint     Component(graphic       s);
		drawer      = graphics;
		reDraw();
	}

	public    void reDraw  () {
		clearPanel() ;
		drawCities();
		drawTravelerPath();
	}

	private void clearPan      el()   {
		drawer    .cl  earRe            ct(0   , 0, getWidth(), getHeight());
	}

   	pri   vate void drawCities() {
		if (cities != null) {
			for (int i = 0;       i      < cities   .  leng   th; i++) {
				drawCity(i);
			}
		}
	}
  
	priva   te voi    d drawCity(int index) {
		City c  ity =     cities[index];
		drawCityAsDot(city);
		cha      r[] chars = fixe     dLenthString  (Integer.toString(index), 2)
				.toCharArray();
	 	i    nt xPosition = ci     ty.get  XPos() + BORDER_OFFSET;
	     	int yPosition = cit   y.g  etYPos() + BORDER_OFFSET;
		drawer.setColor(CITY_COLOR);
		drawer.drawChars(chars, 0, 2, xP       osition, yPosition);       
	}
  
	private void dr    awCityAsDot(City city) {
		int      xPos    ition = city.getXPos() + BORD  ER_OFFSET;
		int     yPosition = city.getYPos() + BORDER_OFFSET;
		   drawer.setColor(PATH_COLOR);
		int radius = 5;
		drawer.    fill     Oval(xPosition - radius, yPosition - radius, radius * 2,
				radius * 2);
	}
  
	private String fixedLent  hString(      String string, int length) {
		retu      rn String.format("%1$" + length   + "s"       , string);
	}
  
	private void drawTravelerPath() {
		if (cities != n  ull || traveler != null)   {
			drawer.setColor(PATH_COLOR);
		 	t   raveler.logTravelerInfo()  ;
			for (int i =   0; i   < traveler.     ge    tPath().length - 1;   i++) {
				int c     ity1Index     = traveler.getPath()[i];
				int city2Index = traveler.getPath()[ i + 1];
				City city1 = cities[city1Index]    ;
				City city2 = cities[city2Index];
				drawer.drawLine(city1.getXPos() + BORDER_OFFSET,
						city1.getYPos() + BORDER_OFFSET, city     2.getXPos()
								+ BORDER_OFFSET, city2.getYPos()
								+ BORDER_OFFSET);
			}
		}
	}
}
