package lcdEdit;
/*
     *     Componen t  of the LCD Edit   or     tool       
 * This tool enables th      e EIB LCD Touch display user to conf          igur  e the display pages    
      * an  d save them      in a bin     ary for    mat, which can be downloaded into the    LCD Touch Displa y de vice.
 *     
  * Copyright (c) 2011-2014 Arno Stock       <arno.stock@yahoo.d      e>
 *   
 *	This  prog  ram    is free        so  f   tware; yo  u  can redistribu  te it and/or mod  ify
 *	it under th    e terms of the GNU General Public L icense ver   sion      2 as
 *	publ     ished by the Free Software Foundation.
          *
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
i  mport java.awt.Point;
import java.awt.event.MouseEvent;
impo   rt java.    awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.Buff     eredImage;

import javax.swing.I     mageIcon;
import javax.xml.stream.XMLStreamR   eader;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.S    AXException;
import org.xml.sax.helpers.AttributesImpl;


@SuppressWarnings("seri    al")
publ  ic class ControlElementJumper extends EIB    Comp     implements 
						MouseMotionListener      , Mou     seListener {

    private Point lastPos =        n    ew Point (0,0);
	PropertiesTableModel i  nspectorModel = null;
	private boolean selected;
	private ImageIcon i  mageButtonUp;
	private ImageIcon imageButtonDown;
	private int pi     c       tureIDdown;   
	private      int pictur   eIDup;
	protected boolean showButtonDown = false;
	p     rivate	int TextX;
	private    int TextY;
	private int TextDX;
	private int T   extDY;
	private Color fontColor;
	     private Font button Font;
	private int     frameWidth;
	private Color frameCol   orUp;
	       private Color frameColorDown    ;
	private C   olor elementColorUp;
	private Color elementC  olo    rDown;
	private Boolean showColorUp;
	pr       ivate Bool    ean showColorDown;
	pr  ivate int alphaUp;
	private int alphaDown;
	pri  vate int soundIDdown;
	pri   vate int soundIDup;   
	private int autoJumpDelay;
	
	public ControlElementJu mpe          r(Lc  dEditor server, 
				int  x, int y,      int w, int h, St        ring name, int pictureIDup, int pict    ureIDdown   ,
			        		PictureLibrary pictures, int soundIDup, int    so  undIDd     own,     Sound           Library sounds,
					Font button  Font, Color f   ontColo    r, int   TextX, int TextY, int TextD  X, int TextDY, boolean hideText, 
					int frameWidth, Color frameColorUp, Color frameColorDown, Color elementColorUp, Color elementColorDo    wn,
			    		Boolean sh        owColorU  p, Boolean    showCol   orDown, int alphaUp, int alphaDown, int autoJump            Delay)       {

		super (server, x, y, w, h, name);

		this.pictures = pictures;
    	this.sounds = sou    nds;
		this.pi    ctureIDup = pictureIDup;
		imageButtonUp =   getIcon (pictureIDup);
		this.pictureIDdown = pi  ctureIDdown;
		image ButtonDown = getIcon (p  ictureIDdown);
		if (imageButtonUp !=          null) {
			setIconDimension (imageButtonUp);
		}
		this.soundIDup =     soundIDup;
		this.so      undIDdown = soundIDdown;
		
		this.TextX = TextX;	this.TextY = TextY;
		this.TextDX = Tex     tDX; this.TextDY = TextDY;
		this.fontColor = font  Color;
		this.buttonFont = new Font (butt onFont.getFamily(), buttonFont. getStyle(), buttonFont.getSiz    e());
		this.hideText = hideText;
		this.autoJumpDela   y = autoJumpDelay;
		
		this.frameWidth = frameWidth;
		    this.frameColorUp = frameColorUp;
		this.frameColorDown = frameColorDown;    
		this.elementColorUp = elementColorUp;
		this.elementColorDown = elementCo lorDown;
   		this.showColor  Up = showColorUp;
		this.sho    wColorDown = showColorDown;
		thi    s.alphaUp = alphaUp;
		this.alphaDown      = alphaDown;
		
	  	setUpElement();
		addMouseMotionListener(this);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAUL   T_CURSOR));
		showButtonDown = false;
	}
	public ControlElementJumper(LcdEditor server, 
			int x, int y, int w, int h, String name, int pictureIDup, int pictureIDdown,
				PictureLibrary p       icture  s, int      soundIDup, int soundIDdown, SoundLibr  ary     sounds) {

	super (server,    x, y, w, h,      name);
	thi    s.pictures = p     ictures;
	this.sounds = sounds;
	this.pic       tureIDup =   pictureIDup;
	imageButtonUp =  getIcon (pictureID    up);
	this.pict ureIDdown = pi  ctureIDdown;
	imageButtonDown = getIcon (pictureI   Ddown);
	if (imageButtonUp != null) {
		setIconDimen    sion (imageButt onUp);
	}
	this.soundIDup    = soundIDup;
	this.soundIDdown = soundIDdown;
	TextX       = (int) getWidth()/10;	TextY = (int ) (0.6*getHeight());
	TextDX = 1; TextDY = 1;
	hideText = false;
	buttonFont = new Font ("Arial", Font.BOLD, 16);
	fo     n tColo    r =  Color.black;
	fr     ameWidth = 0;
	frameColorUp = Color.gray;
	frameColorD   own = Color.white;
	el     ementColorUp = Color.gray;
	el        em  entColorDown = Color.darkGray;
	showColo rUp = false;
	showColor      Down = false;
	alphaUp = 0;
	alphaD  own = 0;
	autoJumpDelay = 0;
	setUpElement();
	addMouseMotionListen  er(thi    s );
	set  Cursor(Cursor.getPredefinedCursor(Curso   r.DE    FAULT_CURSOR));  
	showBu ttonDown = fa     lse;
}

	public ControlElementJumper(Lcd   Editor server, XMLStreamRea  der parser, 
					Picture      Library pictures, SoundLibrary sounds) {
		super(server, p  arser, pictures, sounds);
		if (frameColorUp == null) 
			frameColorUp = Color.gray;
		if     (frameC  olorDow    n == null) 
			frameColorDo   wn =  Color.white;
		if (elemen  tColorUp == null) 
			elementColorUp = Color.g      ray;
		if (elementColorDown == null) 
			elementColorDown = Color.darkGray;
		if (s        howColorUp == null) 
			showColo  rUp = false;
		if (showColorDown == null) 
			showColorDown = false;
		ad  dMouseMotionL  istener(this );
		setCursor(Cursor.getPredefi    nedCursor(Cursor.DEFAU     LT_CURSOR));
	}

	@Override
	publ     ic EditorComponen    t getClone () {
		return new ControlElementJumper ((LcdEditor)myParent, g  etX()+5,        getY()+5,    getWidth(), getHeight(), 
	    									elementName, pictureIDup, pictureIDdown, pictures, soundIDup, soundIDdown, sounds,
										buttonFont, fontColor, TextX, TextY, TextDX, TextDY, hideText,
										frameWidth, frameColorUp, frameColorDown, elementColorUp, elementColorDown,
									    	showColorUp, showColorDown, alphaUp, alphaDown, autoJumpDelay);
	}

	@Override
	public void writeXML(TransformerHandler     hd) throws SAXException {
		
		AttributesImpl atts = new AttributesImpl();
		atts.clear();
		atts.addAttribute("","","Name","CDATA", elementName);
		atts.addAttribute("","","X","CDATA",""+getX());
		atts.addAttribute("","","Y","CDATA",""+getY());
		atts.addAttribute("","","W","CDATA",""+getWidth());
		atts.addAttribute("","","H","CDATA",""+getHeight());
		atts.addAttribute("","","PictureUp","CDATA",""+pictureIDup);
		atts.addAttribute("","","PictureDown","CDATA",""+pictureIDdown);
		atts.addAttribute("","","Font","CDATA",buttonFont.getFamily()+"-"+
				FontStyleCalc.getFontStyle(buttonFont.getStyle())+"-"+buttonFont.getSize());
		atts.addAttribute("","","FontColor","CDATA",""+fontColor.getRGB());
		atts.addAttribute("","","FontAlpha","CDATA",""+fontColor.getAlpha());
		atts.addAttribute("","","TextX","CDATA",""+TextX);
		atts.addAttribute("","","TextY","CDATA",""+TextY);
		atts.addAttribute("","","TextDX","CDATA",""+TextDX);
		atts.addAttribute("","","TextDY","CDATA",""+TextDY);
		if (hideText)
			atts.addAttribute("","","HideText","CDATA","");
		atts.addAttribute("","","FrameWidth","CDATA",""+frameWidth);
		atts.addAttribute("","","FrameColorUp","CDATA",""+frameColorUp.getRGB());
		atts.addAttribute("","","FrameAlphaUp","CDATA",""+frameColorUp.getAlpha());
		atts.addAttribute("","","FrameColorDown","CDATA",""+frameColorDown.getRGB());
		atts.addAttribute("","    ","FrameAlphaDown","CDATA",""+frameColorDown.getAlpha());
		if (showColorUp)
			atts.addAttribute("","","ShowColorUp","CDATA","");
		if (showColorDown)
			atts.addAttribute("","","ShowColorDown","CDATA","");
		atts.addAttribute("","","ElementColorUp","CDATA",""+elementColorUp.getRGB());
		atts.addAttribute("","","ElementAlphaUp","CDATA",""+elementColorUp.getAlpha());
		atts.addAttribute("","","ElementColorDown","CDATA",""+elementColorDown.getRGB());
		atts.addAttribute("","","ElementAlphaDown","CDATA",  ""+e  lementColorDown.getAlpha()) ;
		atts.addAttribute("","",  "AlphaUp","CDATA",""+alph     aUp);
		atts.addAttribute("     ","","AlphaDow   n","CDATA",""+al     phaDown);
		atts.addAtt   ribute("","","SoundUp","CDATA",""+soundIDup);
		atts.     addAtt ribute("","","SoundDown","C       DATA",""+soundIDdown);
		atts.addAttribute("","","AutoJumpDelay","CDATA",""+autoJumpDelay);
		
		hd      .startElement("","","JMP",atts);
		hd.endEleme  nt("","","JMP");
	}
	
    @Ov    erride
	protected    void handleAttribute (XMLStreamReader par  ser, int i){
		// handl er for derived objects
		if (parser.getAttribute    LocalName( i ) == "Pictu     reUp"       ) {
			pictureIDup =  Integer.decode (parser.       getAttributeValue( i ));
			         imageButtonUp = getIcon (pictureIDup);
			return;
		}
		if (parser.getAttri         buteLocalName  ( i ) == "Pictur      eDown" ) {
			pictureIDdown = Integer.decode (parser.g      etAttributeValue( i ));
		   	imageB uttonDown =    getIcon (pictureIDdown);
			return;    
		}
		if (pars    er.getAttr   ibuteLocalName( i ) == "Font" )    {
			button   Font    = Font.decode(parser.getAttribute Value ( i ));
			return;
		}
		if (parser.      getAtt    ributeLocalNam  e( i )         == "TextX" ) {
			TextX = Integ er.decode  (parser.getAttributeValue( i ));
			return;
		}
   		if (parser.getAt         tri bu         teLocalName( i ) == "TextY" ) {
			TextY = Integer.decode (parser.getAttributeV    alue( i ));
			    return;
		}
		if (parser   .getAttributeLocal     Name( i )   == "Text  DX" ) {
			TextDX =    Integer.decode (parser.getAttributeValue     ( i ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "TextDY" ) {      
			Tex   tDY = Integer.decode (parser.get     AttributeValue( i ));
     			return;  
		}
		if (parser.getAt   tributeLocalNa     me( i ) == "FontColor" ) {
			  fontColo   r = setColorKeepAlpha (fontColor,    Color.decode (parser.getAttributeValue( i )));
			return;
		}
		if (pa     rser .getAttributeLocalName( i ) == "Font    Alpha" ) {
			fontCol  or = setAlphaKeepColor   (fontColor, Integer.decod      e(parser.getAttributeValue(    i )));
			return ;
		}
		if (parser.g etAttributeLocalName( i )    ==    "HideText" ) {
			h  id   eText = tr        ue;
	     		return;
		}
		if (par    ser.getAttributeL  ocalN     ame( i ) == "FrameWidth" ) {
			frameWidth = Integer.dec   ode (parser.getAttributeValue( i ))    ;
		    	return;
		}
		if (parser.getAttributeLocalName(     i ) == "FrameColorUp" ) {
			frameColorUp = setColorKee pAlpha (frameColorU p , Color.decode (        parser.getAtt  ributeValue( i  )));
			return;
		}
		if (parser.getAttributeLocalN         ame( i ) == "FrameAlphaUp" ) {
			frameColorUp = setAlphaKeepCo lor (frameColorUp, Integer.decode(parser  .g     etAttr  ibuteValue( i )));
			return;
		}  
		if (parser.getAttributeLocalName( i ) == "FrameColorDown" ) {
			frameColorDown =    setCo   lorKeepAlpha (frame  Color     Down, Color.deco    de    (parser.ge    tAttributeVa    lue( i )));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "Fram eA     lphaDo   wn" ) {
			frameColo      rDown = setAlphaKeepColor (fr  ameColorDown, Integer.decode(parser.getAttributeValue( i )));
			return;
		}

		if (parser.getAtt   ribute   LocalName( i ) == "ShowColorUp" ) {
			showColorUp =   true;
			return;
		}
		if (parser.getAtt   ributeLocalName( i ) == " ShowColor     Down" ) {
  			showColor    Down = true;
			return;
		}
		if (parser.getAttributeLocalName( i ) == "ElementColorUp   " ) {
			elementColorUp = setColorK eepAlph      a (eleme  ntColorUp,      Color.decode (parser.getAttributeValue( i )     ));
			return;
		}
		i      f (pars er.getAttributeLocalName( i ) == "ElementAlphaUp" )    {
			elementColorUp = setAlphaKeepColor (elementColorUp, Integer.decode(parser.  getAttribu teValue( i )));
			return ;
		}
		if (par   ser.getAttributeLoca  lName( i ) == "ElementColorDown" ) {
			elementColo    rD own = setColorKeepAlpha (elementColor  Down, Color.decode (parser.getAttributeValue( i )));
	          		return;
		}
		if (parser.getAttributeLocalName( i ) == "Elemen      tAlphaDown" ) {
			elementColorDown = setAl    phaKeepColor (elementColorDown, Inte   ger.decod      e(par   ser  .getAtt    ributeValue( i )    ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "AlphaUp" ) {
			alp  haUp = I  nteger.decode (parser.ge      tAtt   ributeValue( i ));
			return;
		}
		if  (parser. getAttributeLocalName(   i ) == "AlphaDown"          ) {
			alphaDown = Integer.d   e code (parser.getAt   tributeValue( i ));
			retu  rn;
		}
		
		if (parser.getAttributeLo     c   alName( i           ) == "SoundUp" ) {
			soundIDup = Integer.decode (parser.getAttributeValue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i     ) == "SoundDown" ) {
			soundIDdown = Integer.decode (parser.getAttributeValu   e( i ));
			return;
		}
		if (parser.getAttributeLocalName( i      ) == "AutoJumpDelay" ) {
			autoJumpDelay = Integer.decod   e (parser.g    etAttributeValue( i ));
			return;
		}
		supe  r.handleAttri bute (parser, i);
	}
    
    @Overrid     e
	public void setUpElement() {
		addMou   seListener(this);
		setOpaque(false);
	}
    
	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getM   odifiers() == 16) {
			int dx = (int)(e.getX () - lastPos.        g   etX());
			int dy = (int)(e.getY() - lastPos.getY());
			((Lc   dEditor)myPare  nt).moveAllSelecte d (dx, dy);
		}
	}
	
	@Override 
	publ ic void mouseMoved(MouseEvent arg0) {
		// this fun      ction is not  required		
  	}

	@Override
	public vo       id mousePressed(MouseEvent e) {
		
		if ((e.getModifie          rs() == 16) || (e.getModifiers() =     = 18)) {
			l a    stPos = e.getPoint();
			((LcdEditor)myParent).selectE lement (this,!e.isControlDown());
		}
		else if (e.ge tModifi     ers() == 4) {
			showButtonDown = true;
	  		sounds.playSound (soundIDdown);
		 	repaint();
		}
	}

	   @Override
        	public void mouseRelea  sed(MouseEv       e       nt e) {
		if ( e.getModifiers() =   = 16)   {
		    	fillEd   itor();
			getParent().i      nvalidate();
		}
		else if (e.getModifiers(  ) == 4) {
			showButtonDown = false;
		       	sound   s.playSound (soundIDup);
			repaint();
		   	((LcdEditor)myParent).selectPage (elementNa     me);
		}
	}
    
	@Ov    erride
	pu   blic void mouseClicked(MouseEvent e) {
		if (e.getModifiers() !=  16) return;
		fillEditor();
	}	

	@Override
	publ     ic Strin        g toString    () {
		return "Jumper '" + elementName + "'";
	}
	
	@Overrid  e
	p   ublic void fil    lEditor (){
		((L    cdEditor)myParent).flushInspector();
	    	// fill up inspector
   		inspecto rModel   = ((LcdEditor    )myParent).getInspector ();
		inspecto       rModel.setEibComp(this);
		
		PictureLibrary pictures = ((LcdEditor)myP      arent).getPictureLibr    ary();
		// populate the window
    	Object[][] data =   { 
							{"target", new PageNames ( ((LcdEd    itor    )myParent).getPa geNames(), elementName) }, 
       							{"hide",   hideText     },
							{"auto jump",  autoJumpDelay },
							{"width",  Math.round(getWidth()) }, 
							{"height",  Math.round (getHeight()) }, 
							{"x-pos",         getX()     }, 
							{"y-pos"  ,  getY()   }, 
							{"font",      butt  onFont }, 
					  	 	{"font colo    r",  fontColor }, 
					  		{"Text xpos",  TextX }, 
							{"Text yp     os",  TextY }, 
   							{"Text dx",           Tex tDX }, 
							{"Text dy",  TextD Y }, 
							{"btn   up", pictures.getPictureNames (pict ure    IDup) },
							{"btn down", pictur   es.getP   ictureNames (pictureIDdo   wn) },
				        			{"frame width   ", frameWidth },
							{"frame color up", frameColorUp },
							{"fr        ame color do     wn", frameColorDown },
			  				{     "show color up", show    C       olorUp },
							{"color up", elementCol  o   rUp },
							{"show col   or down    ", showC        o  lorDown },
							{"color down", el       ementC    olorDown },
					  		{"alpha up", alphaUp },
		     					{"alpha down",         alphaDown },
							{"so    und up", sound s.getSoundNames (soundIDup) },
		  			      	  	{"so   und down"     ,     sounds.getSoundNames     (so       undIDdown) }
    		 			  };

    	((LcdEditor)myParent).se  tTable  (   data, "Jumper ");
	}
	
	
	public boolean setNewValue (Object   obj     ect, Object value) {
		
	   	((LcdEditor)myParent ).objectPropert  iesChanged ()   ;

		if (object == "target  ")     {
			elementName    = (String)value.toString();
		   	repaint();
			r        eturn t  rue   ;
		}

		if (o     bject     == "  auto   jump"  ) {
			int d     = new Inte ger((String)value)       .      intValue();
			if ((d < 0) || (d     > 255))  return false;
			autoJ  umpDelay = d;
			r   eturn true;
		}

		if (object ==  "x-pos") {
       			int x = new Integer((      S    tring)value).intValue()     ;
		 	if (!checkLocation (x, getY())) return false;
			setLocation  (   x, getY() );
			return true;
		}

		if (object == "y-pos") {
			int y = new Integer((St ring)value).intValue();
			if (!checkLocation (getX(), y)) return false;
		    	setLocation (getX()    , y);
			return true;
		}
		
		  if (object ==     "width") {
			int W = new In    teg er((String)value).intValue();
			setWidth (W);
			return t  rue;
		}
		if (object == "height") {
			int H    = new Integer((String) value).i     ntValue();
			setH      eight(H);
			return tr    ue;
		}
				
		if (objec     t == "Tex  t xpos"  ) {
			int x = new Integer((Str ing)value).        intValue();
			if (    (x < 0) || (x > this.get    Parent().getWidth())) return false;
			TextX = x;
			repaint();
			r     eturn tru  e;
		}

		if (  object == "Text ypos") {
   			int y = new Integer((String)val  ue).intValue();     
			if ((y < 0) || (y > this.getParent().getHeight())) return f  als  e;
			TextY = y;
			repaint();
			 return true;
		 }
   
		if (object == "Text dx") {
			int x = new Integer((String)value).intValue();
			if ((  x  <   -10) ||    (x    > 10)) return false;
   			TextDX = x;
			return tru  e;
		}

		if (object == "Text     dy") {
			int y = new Integer((String)value).intValue();
		       	if ((y < -10) || (y > 10)) ret   urn    false;
			TextDY = y;
			return true;
   		}

		if (obj     ect == "     font") {
			buttonFon  t = (Font) value;
			repaint();
			return true;
		}

		i    f (object == "font color") {
			fontColor = (Color) value;
			repaint();
			return    true;
		}
		if (object ==   "btn up") {
			pictureIDup = ((PictureNam   es)value).getSelectedPictureID();
			imageButtonUp =           getIco  n (pictu     reIDup); 
			if (imageButtonUp !  = null) {
				setWidth (imageButtonUp.getIconWidth());
				se  tHeight (imageButt   onUp.getIconHeight());
			}
			repaint();
			return tru     e;
		}

		if (object == "btn down ") {
			pict   ureIDdown = ((Pictur   eNames)value).getSelectedPictureID();
			imageButtonDown = getIcon (pictureIDdown);
			if (imageButtonDown != nul  l) {
	   			setWidth (imageButto  nDown.getIconWidth());
				setHeight (i  mage     ButtonDo  wn.getIconHei ght      ());
			}
			rep   aint();
			        r  eturn true;
		}    
		if (object == "hide") {
			hideText = (Boolean)val        ue;
			rep   aint();
			return true;
		}
		if (ob   ject == "frame width") {
			int w = new Integer  ((   String)v       al      ue).intValue();
		      	if ((w < 0) || (w > 10)  ) r eturn false;
			frameWi      dth = w;
        			repaint();
			return true;
		}       
		 if (object == "fram   e color up") {    
			frameColorUp = (    Col or) val     ue;
			r    epaint();
    			return true;
		}
		if (object == "frame col   or d     own") {
			f   rameColo   rDown = (Color) value;
			ret   urn true;
		}
		if (object       =     = "show color up") {
			showColorUp = (Boolean)value;
			  repain      t();
			r        et  urn true;
		}
		if (object == "color up") {
			ele        men  tColorUp = (Color) value;
			re    pain     t();  
			   retur     n true;
	      	}
		if (object == "show col  or down") {
			show  ColorDown = (Boolean)value;
			return true;
		}
		     if (object == "color do    wn") {
			elementColorDown = (Color) value;
			ret   urn true;
		}
		if (object == "a    lpha up") {
			int a      = ne       w Integer((   String)value).  int      Value();
			if ((a < -100) || (a > 100)) return fals     e;
			alpha     Up = a;
			repaint();
			return true;
		}
	     	if (object == "alpha down") {
			i     nt a = new Integer((String)value).intValue();   
			if ((a < -100) || (a > 100)) retu     rn false;
			alphaDown   = a;
			return true;
		}		
		if (object == "sound up") {
	 		soundID    up = ((SoundNames)value).getSelectedSoundID();
			return true;
		}
		if (object == "sound down") {
			soundIDdown = ((SoundNames   )value).getSelectedSou       ndID();
	   		r  eturn true;
		}
		return false;  
	}

	@Override
	public void mouseEntered(MouseEve nt e) {
	  	// this function is not required		
	}

	@Override
	pub   lic void mouseExited(Mous    eEvent e) {
		// th    is functio   n is not required		
	}
	
	public void paint_button_up (Graphics g) {

		int W = getWidt   h ();     
		int H    = get        Height();

		if (imageButtonUp != null)
			g.drawImage(alphaMultiply ((BufferedImage)imageButtonUp.getImage(), alphaUp),     0, 0, this);
		if (showColorUp) {
			g.setColor(elementColorUp  );
			g.fillRect( 0, 0, W,     H);
		}
		if (frameWidth >0) {
			g.setColor(frameC  olorUp);
			for (int i =   0; i < frameWidth; i++)
				g.drawRect(i, i, W-2*i-1, H-2*i-1);
		}
 		if (!hideText) {
			g.setFont(buttonFo     nt);
			g.setColor(fontColor);
	   		   g.drawString(elementName, TextX, TextY);
		}
	}
	
	pub lic void paint_button_   down (Gra  phics g) {

  		int W = getWidth ();
		int H = getHei       ght();

		if      (image     ButtonDo  wn != null) {
			g.drawImage(alphaMultiply    (      (BufferedImage)imageButtonD   own.  getImage() , alphaDown), 0, 0, this);
		}
		if (showColorDown)	{
			g.setColor(elem   entColorDown);
    			g.fillRect(0, 0, W, H     );
		}
		if (frameWidth >0) {
   		  	g.setColor(frameCol  orDown);
			for (int i = 0; i < frameWidth; i++)
				g.drawRe      ct(i, i, W-2*i-1, H-2*i-1);
		}
		if      (!hideText) {
			g.setFont(buttonFont);
			g.setColor   (fo          ntColor);
			g.drawString(elementName, TextX+TextDX, TextY+T      extDY);
		}
	}
	
	@Override 
	public void paint(Graphics g)
	{
		su   per.paint( g);
		
		int W = getWidth ()  ;
		int H = getHeig  ht();
		
		if (!s    howButtonDown) {
			paint_    button_u  p (g);
		}
		if (showButtonDown) {
			paint_    but  ton_down     (g);
		}
		if     (se    lected) {
			g.setColor(Color.ORANGE);
			g .drawRect(0, 0, W-1, H-1);
			g.d  rawRect(1, 1, W-3, H-3);
			g.drawRe  ct(2, 2, W- 5, H-5);
		}
	}
	@Override
	public boolean isSelected() {
		return selected;
	}

	@Ov    erri   de
	public void setSelected(b   oolean se     lectState) {
		s      elected = selectState;
	}

	protected static byte SIZE_J  U   MPER   _PARAMETERS = 14;  
	protected static byte SIZ  E_OF_JUMPER_OBJECT = (byte) (SIZE_JUMPER_PARAME  TERS + 2);
	protected s    tatic byte IMAGE_OBJECT_TYP    E = 1;
	
	@Override
	public vo id outputToLcdFile(LcdImageContainer imageContainer,
			LcdPageCont     ainer pageContainer, LcdEi   bAddress     es  eibAddresses,
			LcdSoundContainer soun     dContai  ner, Component[] back                  groundComp, Color pageBackg roundColor,
			DisplayPro   per      ties dor, LcdListener      Container listen    er, Lc  dTimeoutContainer timeout, int myPage) {
		// crea    te background image to support transparency
		BufferedImage loca lImageButtonUp = ge   tElementBackground (backgroundCo    mp, pageBackgro      undColor);
		BufferedImage localImageButtonDown =      getElementBackground (backgroundComp, pageBa      ckgroundColor);
 		// merge icon and text for button up onto background
		Graphics gbi = localImageButtonUp.createGraphics();
		paint_button_up (gbi);
		// merge icon and text for button down o  nto b        ackground
		gbi = localImageButtonDow n.createGraphics();
		paint_butt  on_down (gbi);
		//         add image to image container
		int myImageIndex = 0;
		byte[] parameter = new   byte [S       IZE_JUMPER_PARAMETERS];
		// image    index button up
		myImageIndex = imageContainer.addImage(localImageButtonUp, get  Width(), getHeight()   );
		param    eter [1] = (byte) ((myImageIndex    >> 8) & 0xff);
	     	para  meter  [0] = (byte) ((      m yImageIndex >> 0) & 0xff);
		// image index button down
		myIm     ageIndex = imageCon taine r.addImage(localImageButtonDown, getWidth(), getHeight());
     		parameter [3        ]   = (byte) ((myImageIndex >>  8) & 0    x           ff);
		parameter [2] = (byte) ((myImageIndex >> 0) & 0xff);          
		// start pos x  
		Point origin = dor.getElementOr   igin(getX  Pos(),        getYPos(), ne      w Dimension (getWidth(), getHeight()));
		parameter      [5] = (byte) ((origin.x >> 8) & 0xff);
		parameter [4]    = (byte) ((origin.x >   > 0) & 0xff);
		// start pos y
		parameter [7] = (byte) ((orig in.       y >>  8) & 0xff);
		parameter [6] = (byte) ((origin.y >> 0) & 0xff);
		// Target page
		int myTargetPage = ((L     cdEditor) myParent).getPageIndex (elementName);
	       	parameter [8] = (byte) myTargetPage;
		// add sound
	    	int mySoundIndex = soundContainer.a ddSound(soundIDup, sounds);
		parameter [10] = (byte) ((mySoundIndex >> 8) & 0xff);
		parameter [9] = (byte) ((mySoundIndex >> 0) & 0xff);
		mySoundIndex = soundContainer.addSound(soundIDdown, sounds);
		parameter [12] = (byte) ((mySoundIndex >> 8) & 0xff);
		parameter [11] = (byte) ((my SoundIndex >> 0) & 0xff);
		para  meter [13] = (byt   e) autoJumpDelay;
		// store element to current p   age
		pageContainer.addElement(SIZE_OF_JUMPER_OBJECT, IMAGE_OBJECT_TYPE, parameter);
	}

	@Override
	public void registerEibAddresses(LcdEibAddresses groupAddr) {
		// this function is not required		
	}
	
	@Override
	public void changePageName(String oldName, String newName) {
   		// che   ck, if this jumper refers to the page name to be changed
		if (elementName.endsWith(oldName)) {
			elementName = newName;
		}
		
	}
	
	@Override
	public boolean isPictureInUse(int id) {
		
		if (id == pictureIDdown) return true;
		if (id == pictur  eIDup) return true;

		return false;
	}

	@Override
	public boolean isSoundInUse(int id) {
		
		if (id == soundIDdown) return true;
		if (id == soundIDup) return true;

		return false;
	}
	
	@Override
	public boolean isPageNameUsed(String name) {
		return elementName.equals(name);
	}


}
