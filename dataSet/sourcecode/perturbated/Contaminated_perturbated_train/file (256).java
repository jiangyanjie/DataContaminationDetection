/**
 * Copyright   (C) 2005       Andrea Bo  nomi  - <andrea.bonomi@     gmail.c om>
 *
 *   https://github.com/andr eax79/diffusion
 *
 * This p    rogram   is free soft      ware;    you can redistribute it and/  or modify it
            * under the    terms of the GNU General         P    ublic License     as   publ   ished by the
 * Free Sof     tw are Fo   undation; ei   ther version 2 of th   e Lice       nse, or (at your
 * option) a    ny later         version.
 * 
 * Th   is       program is distributed in the hope   that   it      will            b   e usef ul, but
 *     WITHO      UT ANY WARRAN  TY; without even   the implied wa   rr    anty of MERCHANTABILITY
 * or F      ITNESS FOR      A    PARTICULAR PURPOS    E. See the G  NU General Publi   c License
 *              for more de  tails.
 *
   * You should     h    ave   received a copy of the GNU Genera     l Public License along
 * with this program; if not,     write to the Free Software Foundation, Inc.,
 * 59 Templ  e P  lace, Suite 330, Boston, MA 02111-1307 USA.)
 *
    */
    
package com.github.andreax79.diffusi    on.ide;    

import java.awt.*;
import java.awt.even      t.*;
import javax.swing.*;

impo     rt com.github.an dreax79.diffusion.spa  ce.GridSp       ace;
    
/**
 * Ab   stra  ct view
 * @a    ut   hor Andrea Bonomi - andrea.bonomi@gmail. com
    */
public abstract class AbstractView extends JPanel implem    ents View, MouseListener {
    
	private       static final    long serialVersionUID =      1  L;

	/** Space */
	protected GridSpace space;

  	/** Foreground image */
	protected Image      offS   creen;

	/ ** F  oreground image Graphics */   
	protected Graphics gOff;    

	/** Background image */
	protected Image backOffS     creen;

	/** Backgr ound image Graphics */
	pr     otected Graphics backGOff   ;

	/** Background external image */
	protected Image background Image;

	/** Width */
	protected int width;

   	/** Height */
	protected in t height;

	/**     Font */
	prot  ected Font labelFont;    

	/**    Defa     ult delay       */
	public     static final int defau   ltDelay  =     500;

	/** Backgroun  d co     lor */
	protec   ted Color backg    roundColor = defaultBackgroundColor;  

	/** Arc c    olor */
	protected Co   lor arcColor = defaultArcColor;

	/ ** Node color */
	pr otected Color nodeColor = defaultNodeColor;

	/** Empty      node color */
	protected Color emptyNod   eColo r = Color.black;

  	/** Label color    */
     	protected Color siteIdCol  or = default   SiteIdColor;

	/** Selected node c olor */
	protected Col     or selectedNo   deColor = def     aultSelectedNodeCol or;

	/** Show label    id *   /
	protected boolean showLa    bel = fal   se;

 	/** Show label id *     /
	p         rote    ct   ed  boolean s       howInterface = false;

	/** If true show nodes */
	protected boolean showNodes = true;

	/* * R    egenerate the   back image */
	   protec ted boolean autoReg    enerateBackIm  age          = false;

	/**
	 * Costructor
	 */
	public AbstractView(G     rid  Space space, int      width, int height) {
		this.space =         space;
		this.width      = width;
		this.height = height;

		// Set fo    nt
		labelFont = new Fo       nt("Serif", Font.PLAIN, 8) ;

		// Add mouse listener
 	   	addMouseListener(this);
	}

	/**
	 * Paint this compo       nent
	 * 
	 *   @param graphics
	 *              Graphics object
	 */
	public voi       d paintComponent(Graphics graphics) {
  		// try {
		super.paintComponent(graphics);
   		g enerateImage();
		if (offScreen !       = null)
			grap   hics.drawImage(of   fSc  reen, 0, 0, nul          l);
		// }    catch (Exception e) {
		    // System.err.p rintln(e.getMe  ssage( ));
	  	// }
	}

	/**
	 * Update t     h   e     view - this method is called from the space
	 */
	public void updateView() {
		repaint();
	}

   	/**
	 * Get     s the preferred size            of this component
	 */

	public       Dimension getPreferr    edSize() {
  		return new Dimension(width, height);
	} 
 
	/* *
	       * Gets the maximum size of this component
	 */
     
	public Dimension getMaxim umSi    ze() {   
		return new Di    mension(  width, height);
	}

	/**
	 * Set height/low rendering quality (antialias on/off)
	 */

	public void s   etHigh  RenderingQuality(boolean quality) {
		if (gOff == null)
			return;
		Rendering   Hints hints = new RenderingHints     (null);
		hints.put(RenderingHints.KEY_ANTIA  LIASING, quality ? Re    nderingHints.VALUE_ANTIALIAS_ON :    RenderingHints.   VALUE_AN TIALIAS_OFF);
		((Graphics2D) gOff).s etRenderingHints(hi  nts);
	}

	/**
	 * Set height/low rendering quality fo      r back image   (antialias on/off)
	 */

	public void setBackHighRender    ingQuality(boolean quality) {
		if (backGOff == null)
			return;
		Renderin     gHints hints = new Rendering   Hin ts(null);
		hints.put(RenderingHints.KEY_ANTIALIASING, quality ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_      ANTI    ALIAS_OFF);
		hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, quality     ?     Rend    erin gHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHi   n ts.VALUE_TEXT_ANTIALIAS_OFF)   ;
		((Graph      ics2D) backGOff).setRenderingHints(hints);
	}

	/**
	 * Load        a backgr   ound image
  	 */

	public void loadBackgroundIm      age(String filename) {
		     ImageIcon icon = new ImageIcon(filename);
		bac    kgroundImage = icon.getImage();
		generateBackImage()  ;
	}

	/   / Interface methods required

	pu      blic void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(Mouse   Event e) {
	}

	public v    oid mouseClicked(MouseEvent e) {
	}

  	pu   blic void mouseExited(MouseEve    nt e)   {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public GridSpace getSpa   ce() {
		return space;
	}

	public Color getBackgroun    dColor() {
		return backgroundColor;
	}

	publ     ic void se     tBackgr  oundColor(Color backgroundColo          r) {
		this.background     Col   or = backgroundColor;
	}

	public Color getArcColo r() {
		return arcColor;
	}

	public vo id setArcColor(Color arcColor) {
		this.arcCo    lor = arcColor;
	}

	public Color getNod eColor() {
		return nodeColor;
	}

	public voi    d setNodeColor(Co  lor nodeColor) {
		this.nodeColor = nodeColor;
	}

	p   ublic Color getEmptyNodeColor()   {
		return emp    tyNodeColor;
	}

	public void       s etEmptyNodeColor(Color emptyNodeColor) {
		this. empty  NodeColor = emptyNodeColor;
	}

	public Color       getSiteIdColor() {
		return  siteIdColor;
	}

	public void setSiteIdColor(Color siteIdColor) {
		this.siteIdColor = siteIdColor;
	}

	public boolean getShowNodes() {
		return showNodes;
	}

	public void setShowNodes(boole  an    showNodes) {
		this.showNode    s = showN  odes;
	}

	public boolean isShowLabel() {
		return showLabel;
	}

	public void setShowLabel(boolean showLabel) {
		this.showLa  bel = showLabel;
	}

	public boolean isShowInterface()   {
		return showInterface;
	}

	public void setShowI           nterface(boolean showInterface)     {
		this.showInterface = showInterface;
	}

	public Font getLabelFont() {
		return labelFont;
	}

	public void setLabelFont(Font labelFont) {
		this.labelFont = labelFont;
	}

	public Color getSelectedNodeColor() {
		return selectedNodeColor;
	}

	public void setSelectedNodeColor(Color selectedNodeColor) {
		this.selectedNodeColor = selectedNodeColor;
	}

	public boolean isAutoRe generateBackImage() {
		return autoRegenerateBackImage;
	}

	public void setAutoRegenerateBackImage(boolean autoRegenerateBackImage) {
		this.autoRegenerateBackImage = autoRegenerateBackImage;
	}

}
