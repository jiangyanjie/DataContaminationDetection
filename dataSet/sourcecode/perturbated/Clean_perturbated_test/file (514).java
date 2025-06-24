package  lcdEdit;
/*
 *   Component of the      LCD E   dit   or tool
 *              This tool enable s the EIB LCD   Touch display user to configure  the display   pa    ges   
         * a   nd      save th  em   in a binary format, which can      be    downloaded    into the L    CD Touch  Display device.
 * 
 * Copyright (c) 2011-2014 A  rno St   ock <arn     o     .stock@y   ahoo.     de>
    *
 *	Thi s program is free s oftw         are     ; you can redistribute   it and/or modify
  *	it      under the    terms of the GNU General Public License version 2 as
 *	published by the Free Software Foundation.
 *
 */

import java.awt.Color;
import java.awt.Compo    nent;
import java.awt.Cursor;
import java.awt.Dimension;   
import java.awt.Font;
import java.awt.Grap  hics;
import java.a wt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import ja       va.awt.image.BufferedImage;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.TransformerHandler;

import lcdEdit.LcdEditor;

import org.xml .sax.SAXExcepti      on;
import org.xml.sax.helpers.AttributesImp     l;

@SuppressWarn     ings("seria     l")
publ      ic class   ControlEl   ementText extends EIBComp implements 
						M    ouseMotionListener, MouseListener {

    private Point lastPos = new Point (0,0)   ;
	Pro pertiesTableModel inspectorModel = null;
	p rivate boolean selec   ted;
	private	int TextX;
 	private      int Te    xt  Y;
	private Font fontTy  pe;
	private Colo    r font    Color;
	private Color backgroundColor;
	privat   e bool  ean    opac;
	private static final int   DEFAULT_TEXT_SIZE = 14;

   	public Co      ntrolElementText(LcdEdito  r server, 
   			int x, int  y,       int   w, int h, String name ) {

	super (ser      ver, x, y, w, h, nam e);
	state = 1;
	TextX = 3;	TextY = getHeight()-4;
	fontType = new Font ("Arial", Font.PLAIN, DEFAULT_TEXT_S IZE);
	fontColor       = Color.black;
	backgroundColor = Color.white;
	setUpElement();
	addMouseMotionListener(this);
	       s       etCursor(C     ursor.getPredefinedCursor(Cursor.DEFAUL T_CURSOR  ));
}

	public Contr      olEl ementT  ext(LcdEditor server, 
		  		int x, int  y, in  t        w, int h, String nam   e, Font fontT   ype, Color fontColor, Color backgroundColor, boolean opac,
					    	int Te    xtX, int TextY)      {

     	myParent = s  erver;
         	setXPos (x); se    tYPos (y)   ;
     	setWidt        h (w);   setHeight (h);
		state = 1;
		elementName = name;
		this.TextX = TextX;	this.TextY = Text Y;
		this.fontType = n  ew Font (fontType.getFamily(), fontType.getStyle(), fontType.getSize());
		this.fontColor = fontColor;
		this.backgroundColor = backgroundColor;
		this   .opa  c = opac;
		setUpE    lement();
		addMouseMotionListener(this);
		setCursor(Cursor.getPredefine     dCursor(Cursor.DEFAULT_CURSOR));
	}


	public ControlElementText(LcdEditor      server, XMLStreamReader pa            rser)   {
		super(server, parser, nu      ll, null);
		if (fontType == nul   l) f    ontTyp    e = new Font ("Arial", Font.PLAIN, DEFAULT_TEXT_SIZE);
		if (fontColor == null) fontColor = Color.black;
		if (backgroundColor ==       null) backgroundColor = Color.white;
		addMouseMotionListener(this);     
		setCursor(Cursor.getPredefinedCursor    (Cursor.DEFAUL   T_CURS    OR)); 
	}

	@Ove     rride
	   public EditorComponent getClone () {
		return new ControlElementT  ext ((LcdEditor)myPare nt, getX()+5, getY()+5, ge   tWidth(), getHeight(), 
										elementN   ame, fontT   ype, fontColor, backgroundColor, opac, TextX, TextY);
	}

        @Ov   erride
	public void set   UpElement() {
		addMouseListener(this);
		setOpaque(opa   c);
        selected = false;
    }
	
	@Override
	public void writeXML(TransformerHandler hd) throws SAXException {
		
		AttributesImpl atts = ne     w AttributesImpl();
		atts.clear();
		atts.addAttribute("","","Name","CDATA", elementName);
		atts.addAttribute("","","X","CDATA",""+getX());
		atts.addAttribute("","","Y","CDATA",""+getY());
		atts.addAttribute("","","W","CDATA",""+getWidth());
		atts.addAttribute("","","H","CDATA",""+getHeight());
		atts.addAttribute("","","F","CDATA",""+state);
		atts.addAttribute("","","Font","CDATA",fontType.getFamily()+"-"+
				FontStyleCalc.getFontStyle(fontType.getStyle())+"-"+fontType.getSize());
		atts.addAttribute("","","FontColor","CDATA",""+fontColor.getRGB());
		atts.addAttribute("","","FontAlpha","CDATA",""+fontColor.getAlpha());
		if (opac)
			atts.addAttribute("","","Opac","C  DATA","");
		atts.addA  ttribute("","","BackgroundColor","CDATA",""+    backgroundCol    or.getRGB());
		atts.addAttribute("","", "BackgroundAlp      ha","CDATA",""+backgroundColor.getAl   ph   a());
		atts.addAttribute("   ","","TextX","CDATA",""+T        extX);
		at    ts.addAttribute("","","TextY","CDATA",""+TextY);
			
		hd.startElement("","","    TEXT",atts);
		hd.endElement("","","TEXT");
	}
	
    @Override
	protected void handleAttribute (XMLStreamReader parser      , int i){
		/      / handler for derived objects
		if (par  ser.getA ttributeLocalName( i ) == "Font" ) {
			fo  ntType = Fon      t.decode(parser.getAttribut        eValue( i ) );
			return;
		}
		if (parser.getAttributeLocalName( i ) == "FontColor" ) {
			fontColor = setColorKeepAlpha (font       Color,   Color.decode (parser.getAttributeValue( i ))    );
			return;
		}
		if (      parser.g     et     AttributeLo   calName( i ) == "FontAlpha" ) {
			f    ontColor = setAlphaKeepColor (fontColor, Integer.decode(parser.ge  tAttributeValue( i )));
			return;
		}
		if    (parser        .getAttributeLocalName( i ) == "Op   ac" ) {
			opac = true;
			return;
		}
		if (parser  .getA  ttribut  eLocalN  a me( i ) == "BackgroundColor" ) {
			backgro  undColor     = setColorKeepAlpha (backgroun            dColor, Color.dec  ode   (parser.getAttributeValue( i )));
			r   eturn;
		}
	   	if (parser.getAttributeLocalName( i ) == "BackgroundAlpha"     ) {
			backgroundColor = setAlphaKeepColor (back  groundColor, Integer.  decode(parser.getAttributeValue( i )));
  			return;
		}
		if (parser.getAttribut     eLocalName(      i ) == "TextX      " ) {
		    	TextX = Integer.decode    (parser.get      AttributeValue( i ));
			return;
		}
		i f (parser.getAttributeLocalName( i ) = = "TextY" ) {
			TextY = Integer.decode (parser.getAttributeValue( i ));
			retur  n;
		}
		super.handleAttribute (parser     , i);
	}

	
	@Override
	public void mouseDragged(MouseEvent e   )     {  
		if (e.getM  odi        fiers() == 16) {
			int dx = (int)(e.getX() - lastPos.getX());
			int dy = (int)(e.getY()     - l    astPos.getY());
			((LcdEditor)myParent).         moveAllS   elected (dx, dy);
		}
	}

	@Override
	public void mouseMoved(Mou     seEvent arg0) {
	}

	@Override
	pub   lic void        mousePressed(MouseEvent e) {

		if ((e.getModifiers() == 16) ||   (e.getModifiers() == 18)) {
			lastPos = e.getPoint();
			((LcdEdito   r)myParent).selectElement (this,!e.isControlDo   wn());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (e.getModifiers() =     = 16) {
       	  		fillEditor();
			getP    arent(    ).invalidate();
		}
	}

	@Override
	publi c void mouseClicked(MouseEvent e) {
		i             f (e.getModifier      s() != 16) r          eturn;
		fillEd     itor();  
	}	

	@Override 
	public String toString () {      
		retu   rn "Text '"   + elementName + "'";
	}
	
    	@Over   ride  
	public void fillEditor (){
		//      fill up inspector
		((LcdEditor)myParent).f   lushInspector();
		inspectorModel = ((LcdEditor)myParent)  .getInspector ();
		i   nspector M  odel.setEibComp(this);
		// populate the  window
    	Object[][] data = { 
							{"label",      elementName }, 
							{"width",  Math.rou   nd(getWidth())  }, 
					   		{"height",  Math.round(getHeight(   )) }, 
							{"x-pos",  getX() }, 
							    {"y     -  pos",     getY() }, 
				        			{"font",  fontType }, 
							{"color",  fontColor }, 
  							{"background",  back  gro    undColor }, 
							{" Text xpos",  TextX  },  
					   		{"T   ext ypos",  TextY }, 
							{"opac",  opac }
    					  };

    	((LcdEditor)myPa    re      nt).setTable (data, "Text");
	}
	
	
	public    boolean setNewValue (   Object object, Objec  t value)  {

		((LcdEditor)myParent).objectPropertiesChanged ();
		
		if (      object      == "label") {
			elementName      = (String)value;
			repaint();
			return true;
		}

		if (object == "x  -p os") {
			int x = new Integ   er((String)value).intVal ue();
			if (!checkLocation (x, getY())) re turn false;
			setLocation (x, getY() );
			repaint();
			return true;
		}

		if (object ==   "y-pos"     ) {
			int y = new Integer((String)value).i     ntValue(   );
	 		if (!checkLocation (getX(     ), y)) return false;
			setLocation      (getX(), y);
		    	repaint();
			return true;
		}
		
	    	if (object == "Text xpos      ") {
			int x = new Intege      r((Str ing)value).i    ntValue();
			if ((x < 0) || (x >         this.getParent().ge  tWidth())) return false;
		  	TextX = x;
			repaint();
			re    turn      true;
		}

		if (o  bject ==    "Text y   pos") {
			int y = new Integer((String)value).intValue();
		  	if  ((y < 0) || (y > this.getParent().getHeight())) return false;
			TextY = y;
			repaint();
			return     true;
		}

		if (object        == "fon  t") {
			fo  nt    Type = (Font) value;
			repaint();
			return true;     
		}

		if (object == "color"  ) {
			fontColor = (      Color) value;
			repaint();
			return true;
	 	}
		
		if (ob      ject == "background") {
			backgroundColo        r = (Color) value;
			repaint();
			r    eturn true;
		}
		
		if (object == "width") {
			int W = new Integer((String     )value).intVa     lue();
			setWidth (W);
			repaint();
			return true;
	   	  }
		i  f (object == "height") {
			int H = new Integer  ((String)value).intValue();
   			setHeight (H);
			repaint();
			return true;      
 		}
				
		if ((object == "opac") && (value in   stanceof Boolean)) {
			opac = (Boolean)va    lue;
			setOpaque(opa     c);
			repaint();
			return true;
		}

		return f   alse;
	}

	@Over ride
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseE  vent e) {  
	}    

	@Override 
	public void  pa  int     (Graphics g)
	{
		super.  paint(g);
		
		int W = getWidth ();
		int H = getHeight ();
		
		if (opac) {
			g.   setColor(backgroundCol    or);
			g.fil  lRect(0   , 0, W, H);	
		}
		g.setFont(f  o    ntType);
		g.set      Color(fontColor);
		g.drawString(elementName, TextX, TextY);
		if ( selected) {
			g.setColor(Col  or.ORANGE);
			g  .drawRect(0, 0, W-1, H-1);
			g.dr   awRect(1, 1, W-3, H-3); 
   			g.drawRect(2, 2, W-5, H-5);
		}     
	}

	@Override
	public boolean isSe    lected() {
		return selected;      
	}

	@Ov erride
	public void setSelected(boolean selectState) {
		selected = s    ele  ctState;
	}

	// we store text as static pi   cture
	protecte      d static byte SIZE_IMAGE_      PARAMETERS = 6;
	pr      ote   cted static byte SIZE_OF_IMAGE_OBJECT = (byte) (SIZE_IMAGE_PARAMETERS + 2);
	protected static   byte IMAGE_OBJECT  _T       YPE = 0;
	
	@Override
	public void outputToLcdFile(LcdImageContainer imageContainer      ,
			LcdPageContain er pageConta  iner,       Lcd  EibAddresses eibAddresses,    
	    		LcdSoundContainer soundContainer, Component[   ] backgr  oundComp,      Color page  Backgr     oundColor,
			DisplayProperties dor, LcdListenerCont            ainer listener, L cdTimeoutContainer timeout    , int myPage) {

	    BufferedImage backgroundImage = ne    w Bu   fferedImage(((LcdEditor)myParent).g  etMaxX(), ( (LcdEditor)myParent)   .getMax   Y(),     Buf fered   Image.TYPE   _INT_RGB);
	    Graphics gbi      = backgroundI mage.createGraphics();
	    //  fill with page background color
		gbi.setColor(pageBackgroundColor);
		   gbi.fillRect(0, 0, ((L      cd Edito     r)m   yParent).getMaxX(), ((LcdEditor)myParent).getM    axY());
	       // create Image wi th all background component  s
	 	for ( Compon en       t thisComp : backgroundComp ) {
  				  if (EIBC   omp.class.isInstance (thisComp)) {  
    					  EIBComp      c   o = (EIBComp)thisComp;   
					  co  .paint (gbi);
				  }
		}
		Bu       fferedImage buf       feredImage = backgroundImage.getSubimage(getXPos(), getYP     os      (), getWidth(), getHe  i   ght());
		
		// me  rge    text into image
	    G    raphi cs gb   = bufferedImage.createGraphics();
		if (opac) {
			gb.setCo  lor(backgro     undColor);
			g     b. fillRect(0, 0, getWidth(), getHeight()   );	
		}
		gb.setFont(fontType);
		gb.setColor(fo   ntColor     );
		gb.drawString(elementNa   me, TextX, TextY);
	    gb.dispose();		

   		// add    image to image container     
	   	int myImage   Index = 0;
		byte[] parameter = new byte [SIZE_IMAG    E_    PARAMET     ERS];
		// image ind   ex text
		myImageIndex = imag       eContainer.addImage(bufferedImage, getWidth(), getHeight());
		  parameter [1] = (byte) ((myImageIndex >> 8     ) & 0xff);
		parameter [0] = (byte) ((myImageIndex >> 0) & 0xff);
		   // start pos x
		Point origin = dor.getElementOrigin(getXPos(), getYPos(), new Dimension (getWidth(), getHeight()));
   		parameter [3] = (byte) ((origin.x >> 8) & 0xff);
		parameter [2] = (byte) ((origin.x >> 0      ) & 0x     ff);
		// start pos y
		parameter [5] = (byte) ((origin.y >> 8) & 0xff);
		param  eter [4] = (   byte) ((origin.y >> 0) & 0xff);
		// store element to current page
		pageContainer.addElement(SIZE_OF_IMAGE_OBJECT, I   MAGE_OBJECT_   TYPE, parameter);
	}

	@Override
	public     void registerEibAddresses(LcdEibAddresses groupAddr) {
	}

	@Override
	public void changePageName(String oldName, String newName) {
		// TODO Auto-generated method stub
		
	}
	
	@Over    ride
	public boolean isPictureInUse(int id) {
		
		return false;
	}

	@Override
	public boolean isSoundInUse(int id) {

		return false;
	}

	@Override
	public boolean isPageNameUsed(String name) {
		return false;
	}


}
