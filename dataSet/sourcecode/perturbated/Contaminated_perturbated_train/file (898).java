package    lcdEdit;
     /*
 * Compone   nt of the LCD E    ditor t    o   ol
 * This t      ool en  ables    the EIB LCD Touc  h d        isplay    user to config         ure    the display pag e   s   
 * and save them in a bi  nary format, whic h can be down  loaded     into the LCD Touch Display d    evice.
 * 
 * Copyri   ght (c) 2011-2014 Arno Stock <arno.st  ock@yahoo.de>
 *
 *	This   program i     s    free            software; you can redis tribu     t     e it and/or modify
 *	it un der t  h   e terms of the GNU General Public License version 2    as
 *	published b    y the Free So   ftware F oundation.
 *
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dim    ensi   on;
impo rt java.awt.Font;
import      java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.Buff  eredImage;

import javax.swing.Ima geIcon;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


@SuppressWarnings("     serial")
public class ControlElementJumper exte  nds EIBComp imple  ments 
						MouseMotionListen      er,  MouseListener {

    private Point lastPos = new Point (0,0);
	PropertiesTableMod   el inspectorModel = null;
	priva       te bool ean selected;
	private   ImageIcon imageButt     onUp;
  	private ImageIcon image ButtonDown;
	privat  e int pi   ctur eIDdown;
	privat     e int pi            ctureIDup;
	protected boolean showBut tonDown = false;     
	private	int   TextX;
	p  rivate int TextY;
	private int TextDX;
	private  int TextDY;
	private C   olor fontColor;
	privat   e Font buttonFont;
     	private int frameWidth;
	private Color frameColorUp;
	private   Color fra  meColorDown;
	private Color elementCo  lorUp;
	private Color elementColorDo  wn;
	private Boolean  showColorUp;
	private    Boolean showColorDown;   
	private int alphaUp;   
	priv    ate int a   lphaDown;
	private int sound      IDd    own;
	private int soundID   up;
	p  rivate in t autoJumpDelay;
	
	public Contro lElementJumper(LcdEditor s erver, 
				          int x  , int y, int w, int h, String name, int pictureIDup, int pictureIDdown    ,
					PictureLibrary picture      s, int soundIDup, int soundIDdown, SoundLibrary sou  nds,
					Font butt  onFont, Color fontColor, int TextX, int TextY, int TextDX, int Te    xtDY,    boolean h    ideText,
				    	int frameWidth, Co lor frameColorUp, Color fram     eColorDown, Color         e  lementColorUp, Color elementColorDown,
					Boo  lean sho           wCo    lorUp, Boolean showColorDown, int alp         haUp, int alphaDown,       int autoJumpDelay) {

		super (  server, x, y, w, h, name);

		this.pictures = pictures;
    	t his.sounds = sounds;
		t   his.pi   ctureIDup =   pictureIDup;
		imageButtonUp = getIcon (pictureI   Dup);
		this.pictureIDdown   = pictureIDdown;
		imageButtonDown = getIcon (pictureIDdown);
		if (imageButtonUp != null) {
			setIconDimension (image   ButtonUp);
	  	}
		this.soundIDup =  so  undIDup;
		this.soundIDdown = soundIDdown;
		
		thi   s.TextX   = TextX;	this.TextY = TextY;
		this.TextDX =     TextDX; this.TextDY = TextDY;
		this.fontColor = fontColor;
		this.buttonFont = new Font (buttonFont.getFamily(), buttonFont.     getStyle(), buttonFont.getSize());
		this.hideText = hideText;
		this.autoJumpDelay = autoJumpDela    y;
		
		this.frameWidth = frameWidth;
		this.frameColorUp = frameColorUp;
		this.frameColor       Down = fram    eColorDown;
		this.elementColorUp = eleme   ntColorUp;
		this .   elementColorDown = elementC    olorDown;
		this.showColorUp = showColorUp;
		this.showColorDown = showColorDown;
		this.alphaU   p = alp  haUp;
		this.alphaDown  = alphaDown;
		
		setUpElement();
	  	addMouseMotionListener(this);
		setCursor(Cursor.  getPredefinedCursor(Curso r.DEFAULT_CURSOR));
		showButtonDown = false;   
	}
	publi  c C ontrolElemen     tJumper(Lc      dEdi  tor server, 
			int x,    i    nt      y, int w, int h, String name, int pi         ctu    re    ID  up, int p  ictureIDdown,
				PictureLibrary pictu     res, int soundIDup, int soundIDdown, SoundLibrary sounds) {         

	super (server, x, y, w, h, name);
	   this.pictures = pictures;
	this.sou   nds = sounds;
	this.pictureIDup = pictureID    up;
	imageBut tonUp = getIcon (pictu reI     Dup);
	this.pictureIDdown = pictureIDdown;
	imageButtonDown = getIcon (pict    ureIDdown);
	i   f (imageButtonUp != null) {
		se tIconDimension (imageBu      ttonUp);
	}    
	this.soundIDup = soundIDup;
	this.soundIDdown =  soundIDdown;
	TextX = (int) getWidt   h()/10;	TextY = (int) (0.6*getHeight());
	Text  DX = 1; TextDY = 1;
	hideText = false;
	b     uttonFon  t = new Font ("Ar   i al", Font.BOLD, 16);
	fontColor = Color.black;
	   frameWidth = 0    ;
	frameColorUp = Color.gray;
	frameColorD   own        = C     olor.white;
	elementCol    orUp = Color.gray;
	elementColorDown = Color.darkGray;
	showColorUp = false;
	show     Colo  rDown =      false;
	alphaUp   = 0;
	alphaDown = 0;
	autoJumpDelay = 0;
	setUpElement();
	addMou  seMotion       Listener(this);
	setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	showButtonDown = false;
}

	pub      lic ControlElement   Jump  er(LcdEditor server, XMLStreamRea   der parser, 
					Picture Li      brary  pictures  , Sou         ndLibrary sounds) {
		super(server, parser, pictures, sounds);
		if (frameColorUp == null) 
			frameColorUp  = Color.g  ray;
		if (frameColorDown == null) 
			frameColorDown = Color.white;
		if (elementColorUp == null) 
			elem    entColorUp = Colo  r.gray;
		if    (elementCo   lorDown == null) 
			elementColorDown = Color.darkGray;
		if (s            howColorUp == null) 
			showColorUp = false;
		if (showColorDown == null)       
			showColorDown = false;
		addMouseMotionListener(this);
		se   t  Cursor(Cursor.      getPredefinedCursor(Curs    or.DEFAULT_CURSOR));
	 }

	@Overr  ide
	public EditorComponent getClone () {
	    	return new ControlEle    mentJumper ((LcdEditor)myParent   , getX()+5, getY()+5, getWidth(), getHeight(), 
										elementName, pictureIDup, pictureIDdown, pictures, soundIDup, soundIDdown, sounds,
										buttonFont, fontColor, TextX, TextY, TextDX, TextDY, hideText,
										frameWidth, frameColorUp, frameColorDown, elementColo   rUp, elementColorDown,
										showColorUp, showCo     lorDown, alphaUp, alphaDown, autoJumpDelay);
	}

	@Override
	public void writeXML(TransformerHandler       hd) throws SAXException {
		
		AttributesImpl atts = new AttributesImpl();
		atts     .clear();
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
		if (h ideText)
			atts.addAttribute("","","HideText","CDATA","");
		atts.addAttribute("","","FrameWidth","CDATA",""+frameWidth);
		atts.addAttribute("","","FrameColorUp","CDATA",""+frameColorUp.getRGB());
		atts.addAttribute("","","FrameAlphaUp","CDATA",""+frameColorUp.getAlpha());
		atts.addAttribute("","","FrameColorDown","CDATA",""+frameColorDown.getRGB());
		atts.addAttribute("","","FrameAlphaDown","CDATA",""+frameColorDown.getAlpha());
		if (showColorUp)
			atts.addAttribute("","","ShowColorUp","CDATA","");
		if (showColorDown)
			atts.addAttribute("","","ShowColorDown","CDATA","");
		atts.addAttribute("","","ElementColorUp","CDATA",""+elementColorUp.getRGB());
		atts.addAttribute("","","Elemen   tAlphaUp","CDATA",""+elementColorUp.getAlpha());
		atts.addAttribute("","","ElementColorDown","CDATA",""+  elementColorDown.getRGB());
		atts.addAttribute("","","ElementAlphaDown","CDATA",""+elementColor      Down .getAlpha  ());   
		atts.addAttribute("","","AlphaUp","CDATA",""+alphaUp);
		atts.addAttribu te(     "","","AlphaDown","CDATA",""+alphaDown);
		atts.addAttribute("","","SoundUp","CDATA",""+soundIDup      );
		atts.addAttribute("","","SoundDown","CDAT  A",""+soundIDd    own);
		atts.addAttribute("","","AutoJumpDelay","CDATA",""+autoJumpDelay);
		
		hd.startElement("","","JMP",att s);
		hd.en      dElement(  "","","JMP");
	}
	
    @Override
	prot   ected void h  andleAttrib     ute (XMLStreamReader parser, int i){
		// han    dler for derived objects
  		if (parser.getAttribute     LocalName( i )      == "PictureUp" ) {
		   	 p    ictureIDup = Integer  .decode (parser.getAttributeValue(       i ));
			imageBu    ttonUp = getI   con (pictureIDup);
			return;
		}
		if (parser.getAttrib uteLocalNam   e( i ) == "PictureDown" ) {
			pictureIDdown = Integer.decode (parser.getAttribu   teValue( i ));
     			imageB uttonDow    n   = getIcon (pic     tureIDdown);
			return;
		}
		if (parser.getAttributeLocal Name( i ) == "Font" ) {
			button Font = Font.de   co   de(p  arser.getA ttributeValue( i ));
			return ;
		}
		if (parser.getAttributeL     ocalName( i ) == "TextX" ) { 
			TextX = In   teger.decode (parser.ge tAttributeValue( i ));
			return;
		}
		if     (parser.getAt  tribut eLo ca lName( i ) == "TextY" ) {
			TextY = Integer.decode (   parser.getAttributeValue( i    ));
			retur     n;
  		}
		if (parse r.getAttributeLocalName( i )     == "Tex   tDX"  ) {
			TextDX = Integer.decode (parser.getAttributeValue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "Tex  tDY" ) {
			TextDY = I  nteger.decode (parser.getAttributeValue( i ));
			return;
    		}
		if (parser.getAttributeLocalName( i ) == "FontColor" ) {
			fontColor = setColorKeepAlpha (fontColor, Col  or.decode (parser.ge   tA  ttributeValue( i )));
      			re  turn;
		}
		if (parser.getAttributeLocalNa  me( i ) == "FontAl  p ha"       )   {
			fontColor = setAlphaKeepColor   (fontColor, Integer.decode(parser.getAttributeValue( i )));    
			retu    rn;
		}
		if (parser.getAttributeLo   calName( i ) == "H      ideText" ) {
			hideText = true;
			return;
		}
		if (parser.getAt   tributeLocalName( i )      == "FrameWi   dth" ) {
			frameWi      dt  h = Integer.decode (parser.getAt tr   ibuteValue( i ));
			return;
	      	}
		if (pa     rser.getAttributeLocalName( i ) == "FrameColorUp" ) {
			frameColorUp =    setColor  KeepAlpha (fram  eColorUp, Co lor.decode (parser.getAttributeValue( i )));
			return;
		}
		if (parser.getAttributeLocalNam   e ( i ) == "FrameAlphaUp"  ) {
			frameColorUp = set   AlphaKeepColor (frameColorUp, Integer     .decode(parser.getAttributeValue( i )));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "FrameColorDown" )       {
			frameColorDown = setColorKe    epAlpha (frameColorDown, Color.decode (parser.getAttributeValue( i )));
			return;
		}
		if (parser.getAtt  ributeLocalName( i ) == "FrameAlphaDow       n" ) {
			frameColorDown = setAlphaKeepColor (frameColorDown, Int eger.decode(parser.getAttr   ibut eValue( i )));
			return;
		}     

		if (parser.getAttributeLocalName( i ) == "ShowCol  orUp" ) {  
			showColorUp = true;
			  return;
		}
		if (parser   .getAtt ributeLocalName(      i ) == "ShowCol     orDown" ) {
			showColorDown = true;
			return;
		}
		if (parser.getAttributeLocalName( i ) ==      "ElementColorUp" ) {
			e   lementColor    Up = setColorKeepAlpha (elementColorUp,     Color.decode (parser.getAttributeValue( i )));
			return;
		}
		if    (parser.getAt   t     ributeLocalName(     i ) == "ElementAlphaUp" ) {
			elementColorUp    =      setAlphaKeepC       olor  (    elementColorU       p, Integer.decode(parser.getAttributeValue( i )));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "ElementColorDown" ) {  
			elementColorDown = setColorKee  p      Alpha (elem e   ntColorDown,   Color.decode (parser      .getAttrib uteVa    lue( i ))  );
			r   eturn;
		}
		if       (parser.getAttributeLocalName( i ) == "Ele   mentAlphaDown" )  {
			elementColorDown = setAlphaKeepColor (elementCol      orDown, Integer.decode(parser.getAttributeValue( i )));
	   		retu    rn;
		}
		if (parser.getAttributeLo  calName( i ) == "AlphaUp" ) {  
			a      lphaUp = Integer.decode (parser.ge tAttr    ibuteValue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "AlphaDown" ) {
			alphaDown = Integer.d  ecode (parser.        getAttributeVal      ue( i ));
			return;
		}
		
		if (parser.getAttributeLocalNam   e( i ) == "SoundUp" ) {   
			soundIDup = Integer.decode (parser.getAttributeValue( i )) ;
     	 		       return;
	    	}
		if (parser.getAttributeLocalName( i ) == "SoundDown" ) {
			so  undIDdown = Integer.decode (parser.getAttributeValue( i ));
			return;
		}
        		if (parser.ge   tA  ttributeLocalName( i ) == "AutoJumpDelay" ) {
			autoJumpDelay = Integer.decode (parse r.getAtt    ributeValue( i ));  
			return;
		}
		super.handleAttribute (parser, i);
	}
    
    @Override
	public void setUpElement() {
		           addMo      useListe    ner(this);
	     	setOpaque(false);  
	    }       
    
	      @Override
	public  void mouseDragged(MouseEvent e) {
		if (e.getModifiers() == 16) {
			int dx = (int)(e.getX() - lastPos.g  etX());
			int   dy = (int)(e.getY() - lastPos.getY());
			((LcdEditor)myP   arent).moveAllSelected (dx, dy);
		}
	      }
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
	      	// this function is not require d		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if ((e.getM      odifiers() == 16) || (e.   getModifiers() == 18)) {
			last Pos = e.getP   oint();
			((LcdEditor)myParent).sel    ectElement (thi  s,!e.isControlDown());     
		}
		else if (e.getModifiers() == 4) {
			showB      ut  t    onDown  = true;
			sounds.playSound (so      undIDdown);
	       		repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getMo       difiers() == 16) {
			fillEditor();
			getParent().inva   lidate();
		}
		else if (e.getModifiers() == 4) {
			showButt  onDown = false;
			so  un   ds.p  laySo      und (soundIDup);
			repai  nt();
			((Lcd Editor)myPa     rent).selectP  age (elementName);
		}
	}

	@Over     ride
	public void mouseClick    ed(MouseEvent e) {  
		if (e.g etModifiers() != 16) return;
		fillEd     itor();
	}	

	@Ov  erride
	public String toString () {
		return "Jumper '" + elementName +  "'";
	}
	
	@Ov erride
	publ    ic void fillEditor (){
		((LcdEditor)myPar  ent).flu   shInspector     ();
		// fill up inspector
		ins    pe   ctorModel = ((L   cdEditor)myPare   n t).ge     tInspec         tor ();
		ins pectorModel.se tEibComp(this);
		
		PictureLibrary pictures = ((LcdEdit  or)myParent).getPictureLibrary();
		// popul     ate the w indow
    	Object  [][  ] data = { 
						     	{"target", new PageNames (      ((LcdEditor)myParent).getPageName  s(),        elemen tName) }, 
							{"hide",  hideTe  xt },
 							{"auto jump",                autoJumpDelay },
							{"width",     Math.round(getWidth()) }, 
							{"height",  Math.round(      getHeight()) }, 
							{"x-pos" ,  getX() }, 
							{"y-pos",  getY() }, 
							{"font",   buttonFont }, 
							 {"font color",  fontColor }, 
							{"Text xpos",     TextX }, 
							{"Text ypos",  TextY }, 
					  		{"Text dx",  TextDX }, 
							      {"Text dy",  TextDY }, 
							{"btn up", pictures.getPictureNames (pictureIDup) },
							{"btn d own", pictures.getPictureNames (pictureIDdown) },
							{"frame width", frameWidth },
		      					{"frame color up", frameColorUp },
							{"frame color down", frameColorDown },
			   				{"show   color u    p", showColor     U          p },
			  				{"color up", elementColorUp },
							{"sh   o    w color do  wn", showColorDown },
							{"co  lor down", ele    mentColorD     own },
							{"alpha up", alph       aU p },
			     				{"alpha down", alphaDown },
     							{"sound up", sounds    .getSoundNames (soundI Dup) },
							{"sound down", sou nds.getSoundNames (soundIDd  own) }
         					  };

              	((LcdEdi  tor)myParent).setTable (data, "Jumper");
	}
	
	
	public boo  lea    n setNewValue (Object    obje     ct, Object value) {
		
		((LcdEditor)myPa  rent).objectPropertiesChanged ();

		if (object == "tar    get")     {
			elementName = (Str  ing)value.toString();
			repaint();
			re     tur  n true    ;
		}

		if (objec   t ==   "   auto jump") {
			int d = new Integer((Stri  ng)value).intV     alue();
			if ((d < 0) ||   (d > 255))  return false;
			autoJumpDe         lay     = d;
			return true;
		}

 		if (object == "x-p     os") {
			int x = new Integer((String)value).intValue();
			if (!che    ckLocation (x, get  Y())) return false;
			setLocation (x, getY() )  ;
			return    true;
		}

    	   	if (object == "y  -pos") {
			int y = new Integer((Str   ing)value).intValue()    ;
			if (!checkLocation (getX(), y)) return false;
			setLocati      on (getX(), y);
			return true;
		}
 	 	
		if (object == "width") {
			int       W = new Integer((Str  ing)value).intValue();
			setWid  th (W);
			return true;
  		}
		if (object == "height")    {
			int H = new Integer(   (String)va  lue). intValue();
			setHeight(H   );
			return true;
		}
				
		          if (object == "Text xpos") {
			int x = new  In  teger((String)value).intValue();
			if ((x <        0) || (x > this.getParent().getWidth())) return false;
			TextX = x;
			r        epaint();
			return true;
		}

    		 i  f (object == "Text ypos         ") {
			i   nt y = new     Integer((S     tring)value).intValue();
			if ((y < 0) ||     (y    > this.getParent().getHeight())) return false;
   			TextY = y;
			repaint();
			 return true;
		}

		if (o    bject  ==    "Te   xt dx") {
			i  nt x = new Integer((String)val   ue).intValue();
			if ((x < -10) || (x > 10)) return false;
			TextDX = x;
			return true;
		} 

		if (object == "Text dy") {
			int y = new Integer((String)val    ue).intValue();
			if    ((y < -10) || (y > 10)) return false;
			TextDY = y;
 			return true;
 		}

		             if    (object == "font"        ) {
			  buttonFont    = (Font) valu   e;
			repaint();
			return true;
		}

		if (  object ==    "font color") {
			fontColor = (Color) value;
			repaint();
     			re      turn true;
		}
		if (object == "btn up")    {
			pictureIDup = ((Picture Name      s)value).getSelectedPictureID();
			imageButtonUp = getI     con (pictureIDup);
			if (i  mageButtonUp != null) {
			    	setWidth (imageButtonUp.ge          tIconWidth());
			   	setHeight (imageButtonUp.getIconHeight());
			}
			rep    aint();
			return true;
		}

  		if (obje   ct == "btn down") {
			pictureIDdown = ((Pictu   reNames)      value).getSelectedPictureID    ();
			imageButtonDown   = ge tIcon (pictureIDdown);
			if (imageButtonDown != null) {
				setWidth (imageButtonD      own.getIco   nWi    dth());  
				s   etHeight (im       ageButtonDown.getIco    nHe    ight());
			}
			repaint();
	   		return true;
		}
		  if (     ob   ject == "hide") {
			hideText = (Boolean)value;
			repai       nt();
			return  true;
		}
		if (object == "frame width") {    
			int w = new      Integer((String)value        ).intVa    lue(   ) ;
			if ((   w < 0) || (w >     10)) return fa  lse     ;
			frameWidth =   w;
			repaint();
			return true;
		}
		if      (object == "frame color up") {
			frameColor     Up = (Color) value;
			repaint();
			return true;
		     }
		if (object == "frame color   down")      {
			frameColorD     own =     (C   olor) value;
			return true;
		   }
		if (object == "show color up") {
			sh  owCol   orUp = (Boolea     n)value;
			repaint();
			re   turn true;
		}
 		if (object ==       "color up"     ) {
			elementColorUp = (Color) value;
			repaint();
			retu   rn true;
		}
		if (object == "show c   olor down") {
			showColorDo   wn     = (Boolean)value;
			re   tu   rn true;
		}
		if (object  ==    "color down") {
			el    ementColorDown = (Color) value;
			return true;
		}
   		if (object == " alpha up") {
	  		int a = ne   w Intege r((String)value).intValu     e();
	    		if ((a < -100) || (a > 100)) return false     ;
			alphaUp =      a;
			repaint();
			return true;
		}
		if (object == "alpha down") {
			int a = new Integer((String)value).intValue()      ;
			if ((a < -100) || (a > 100)) return       false;
			alphaDown = a;     
			r eturn true;   
		}		
		if (object == "sound up") {
			soundIDup = ((SoundNames)valu      e).getSelectedSoundID()  ;
			retu   rn true;
		}
		if (object == "sound down") {
		   	soundIDdown = ((SoundNames)value) .ge  tSelectedSo  u       ndID();
			return tru   e;
		}
	       	return false;
	}

	@O   verride
	public void mouseEntered(MouseEvent e)    {
		// this function is not required		
	}

	@Override
	publ   ic void      mo us      eExited(Mouse    Event e) {
		// this function is not required		
	}
	
	public void paint_button_up (Graphics g) {

		int W = get    Width ();
		int H = getHeight();

		if (imageButtonUp != null)
			g.draw    Image(al   phaMultiply ((BufferedImage)imageButtonUp.getImage(), alphaUp), 0     ,  0, this);
		if (showColorUp) {
			g.setColor(elementColorUp);
			g.fillR  ect(    0, 0, W, H);
		}
		if (frameWid              th >0) {
   			g.setColor(frameColorUp);
			for (int i =  0; i < frameWidth; i++)
				g. drawRect(i, i, W-2*i-1, H-2*i-1);
		}
		if (!hideText) {
			g.setFont(buttonFont);
			g.setColor(fontColor)   ;
			g.drawString(element  Name, Tex      tX , T   extY);
		}
	   }
	
	public void          paint_button_down (Graphics g) {

		int W = getWidth ();
		int     H = g      etHeight();

		if (imageBu ttonDown != null) {
			g.drawImage(alphaMultiply ((BufferedImage  )imageButton  Down.getImage(), alphaDown), 0, 0,  this);
		} 
	 	if (showColorDown)	{
			g.setColor(elementColorDown);
			g.fillR ect(0, 0, W, H);
		}
		  if (frameWidth >0) {
			g.se   t     Color(frameColorDown);
			for (int  i =       0; i < fr ameWidth; i++)
			  	g.drawRect(i, i, W-2*i-1, H-2*i-1);   
		}
		if (!hideText)  {
			g.setFon  t(buttonFont);
			g.setColor(fontColor);
			g.drawString(elementN am     e, TextX+TextDX, TextY+TextDY);
		}
	}
	    
	  @Override 
	publi  c void paint(Graphics g)
	{
		super.paint(g);
		
	    	int W = getWidth ();
		int H = getHeight();   
		
	   	if (!showButtonDow    n) {
			pa    int_button_up (g);
		      }
		if (showButtonDown) {
			paint   _button_down (g);
		}  
    		if (sel    ec   ted) {
			g.setColor(Color.ORANGE);
			g.drawRect(0, 0, W-1, H-1);
			g.drawRect(1, 1, W-3, H-3);
			   g.drawRect(2, 2, W-5,  H-5);
		}
	}
	@Ove    rride
	public boolean     isSelected()      {
		return selected;
    	}

	@Override
	public   v   oid setSelected(boolean selectState) {
		selected = selectState;
	}

	protected static byte SIZE_JUMPER_PARAMETERS           = 14;
	protected    static byte SIZE_OF_JUMPER_OBJECT = (byte) (SIZE_JUMPER_PA     RAMETERS + 2  );
	protected static byte IMAG   E_OBJECT_       TYPE = 1;
	
	@O   verride
	publi c void outputToLcdFile(LcdImageContainer imageContainer,
			LcdPageContainer pageC        ontainer, LcdEibAddresses    eib     Addresses,
 			LcdSoundContainer soundContainer, Component[]    background    Comp, Color pageBackg   r ound  Color,
  			Displa   yPr          opert  ies dor, LcdListenerCon tainer listener,    L   cdTimeoutContainer timeout, int myPage) {
		// create background image to support tr    ansparency
		BufferedImage localImageButtonU       p = getElementB    ackgrou   nd (backgroundCom     p,            pageBackgroundColo r);
		BufferedIm        age localImageButtonDown = getElem         entBackground (backg     roundComp         , pageBackgroundColor      );
		// me rge icon and text for button up ont   o background
		Graphics gbi = localImageButtonUp.createGra      ph    ics();
		 p aint_button_up (gbi);
	    	// m er ge icon and text for button down onto background
		gbi = loc  alIm   ageBu      tt   onDown.createGraphics();
		paint_bu tt     on_down (gbi);
		// add im    age to image con   t    ainer    
		int myImageIndex = 0;
		byte[] parameter = new byte [SIZE_JUMPER_PARAMETERS];
		// ima  g e index button   up
		myI   mageIndex = imageC   ontainer.addI   mage(localImageButtonUp, getWidth(), getHeight());
		paramet   er [1] = (byte) ((  myImageIndex >     > 8) & 0xff);
		paramete      r [0    ] = (byte) ((     myImageIndex >> 0) & 0xff);
		// image index but       ton down
		m    yImageIndex = imageContainer.add  Image(localIma        geButtonDown, getWidth(), getHeight  ());
		parameter [3]  = (byte) (   (myImageIndex >> 8) & 0xff)   ;
		parameter [2] = (by   te) ((myImageIndex >> 0) & 0xff);
		// start pos x
	   	Point origin = dor.getElementOrigin(getXPos(), getYPos(), new Dimension (getWidth  (), getHeig     ht()));
		parameter [5] = (byte) ((origin.x >> 8) & 0   xff);
		parameter [4] = (byte) ((o rigin.x >> 0) & 0xff);
		/      / start pos y
		parameter [7] = (  byte)     ((origin.y >> 8) & 0xff);
		parameter [6] = (byte) ((origin.y >> 0  ) & 0xff);
		// Target page
		int myT    argetPage = ((LcdEditor) myParent).getPageIndex (elementName);
		parameter [8] = (byte) myTargetPage   ;
		// add sound
		int mySoundIndex      = soundContainer.addSound(soundIDup, sounds);
		parameter [10] = (byte)        ((mySoundIndex >> 8) & 0xff);
		parameter  [9] = (byte) ((mySoundIndex >> 0) & 0xff);
		mySoundInd  ex = soundContainer.addSound(soun  dIDdown, sounds);
		para      meter [12] = (byte) ((mySoundIndex >> 8) & 0xff);
		p     arameter [11] = (byte) ((mySoundIndex >> 0) & 0xff);
		parameter [13] = (byte) autoJumpDelay;
		// store element to   current pag  e
		pageContainer.addElement(SIZE_OF_JUMPER_OBJEC      T, IMAGE_O   BJECT_TYPE, pa    rameter);
	}

	@Override
	public void registerEibAddr   esses(LcdEibAddresses groupAddr) {
		// this function is not required		
	}
	
	@Override
	public void changePageName(String oldName, String newName) {
		// check, if this jumper refers to the page name to be        changed
		if (elementName.endsWith(oldName)) {
			elementName = newName;
		}
		
	}
	
	@Override
	public boolean isPictureInUse(int id) {
		
		if (id == pictureIDdo      wn) return true;
		if (id == pictureIDup) return true;

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
