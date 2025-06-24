package lcdEdit;
/*
 * Component  of the LCD Editor tool
 * This tool    enables      the EIB   LCD   T      ouch display user to configure          the displ  ay      pa     ges 
 * and save  them in    a bin   ary format, which     can be    downloaded         in  to the LCD Touch Display dev  ice.
 *   
 * Copyright (   c) 2011-2014 Arno Stock    <arno.sto ck      @yahoo.d     e>
 *
 *	This pro   gr     am is   free     software;  you can redistribute it and/or modify
 *	i t      u  n der th       e te  rms of the GNU    General Public License version 2 as
 *	   publis     hed by the Free Software  Foundation.
 *
 */

import java.awt.Color;
impor    t java.awt. Component;
import java.awt.Cursor;
import java.awt.Dime    nsion;
import java.awt.Font;
import java.awt.Graphics;
import    java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;    
import java.awt.event.MouseMotionListener;
i  mport java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
imp   ort javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transfor   m.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

@SuppressWarnings("serial")
pub      lic class         ControlElementLED extends EIBComp implements 
						 	Mo      useMo tionListener, MouseListener  {

    pr  ivate       Point    lastPos = new Point (0,0);
	PropertiesTableModel inspectorModel = null;
	priva  te b oolean selected;
	private   ImageIcon imageLedOff;
	private int pictureIDoff;
	private int pictureIDon;
	private int pic tureIDwarning;
	private ImageIcon ima   geLedOn;
	private ImageIcon imageLedWarning  ;
	protected boolean showLedOn     = false  ;
	pr   ivate	int Te xtX;
	private int TextY;
	private int TextDX;
	private int TextDY;
	private Color fontColor;   
	private Font buttonFont;
	private String   textOff;
	private String textOn;
	privat   e int soundIDdown;
       	private   int soundIDup;
	private int soundIDwarning;
	private int bitPosition;
	privat  e LedFunctionTy     pe led Function;
	priv       ate LedVisib  leType ledVisible;
	private in     t radioValue;
	private     in     t sou ndRe  peti  tions;
	
	public Contr  olElementL ED(LcdEdit    or server, int         x, int y, int w, int h, 
				char sAddr0, char sAddr1    , int ledFunction, int      pictureIDoff,      int pictureIDon, 
				P      ict ure  Library  pi    ctures, in    t ledVi   sible, int soundIDup, int soundIDdown, SoundLi    brary soun  ds)   {

		super (server  , x, y, w, h, "");
            	this.pictures =     pictur   es;
    	this.sounds = sounds;
		eibObj[0] = ne   w EIBObj (sAddr0);
		if (sAddr1 > 0) {
		   	eibObj[1] =    new EI  BObj (sAddr1);
		}
		bitPosition = 0;
		radioValue = 0;
		this.pictureIDof     f = pictureIDoff;
		i   mageLedOff = getIcon (pictureID    off);
		this.pictureIDon =    pictureIDon;
		imag  eLedOn = getIcon (pictur   eIDon);
		imageLedWarning = null;
		pi   ctureIDwarning = PictureLibrary.PICTURE_ID_NONE;
		imag  eLedWarning = null;
		if (ima geLedOff != nu     ll) {
			setWidth (imageLedOff.getIconWidth());
		   	setHeight (imageLedOff.getIconHeight());
		}
		TextX = (int) getWidth   ()/10;	TextY = (int) (0.6*getHeight());
		TextDX = 1; TextDY    = 1;
		buttonFont = new Font ("Arial", Font.BOL      D, 16);
		fontColor = Col  or.  black;
  		textOn =       "";
		te   xtOff = "";
		this.soundIDup =   soun    dIDup;
		this.soundID down = soundIDdown;
		this.soundIDwarning = SoundLibrary.S     OUND_ID_SILENT;
		this.soundRe   pet  itions = 0;
   		setUpElement ();
		showLedOn = false;
		this.       ledFunction = ne      w LedFunctionTy pe (ledFunction);
		this.ledVisib        le       = new LedVisib   leType (  ledVisible);
	}
	
	public ControlElementLED(LcdEditor server, int x, int y, int   w, int h, 
			char sAddr0, char sAddr1, int ledFun ction, int bitPosition, int radioV   alue, 
			int       pi    ctureID off, int pictur     eIDon, int pict ureIDwarning,
			PictureLib          rary pictures, int ledVis   ible, int   soundIDup, int soundIDdown, int  soundID       warning, S     oundLibrary sounds,
			  Font b  uttonFont, Color fontColor,    int TextX, int TextY, int Tex       tDX, int TextDY, 
			Stri        ng textO  n, String textOff,    int soundRepetitions) {

	s    uper (server, x, y, w, h, "");
	this.pictures = pictures;     
	th  is.sounds = sounds;
   	eibObj[0] = new EIBObj (sAddr0);
	if (sAddr1 > 0) {
		eibO      bj     [1] = new EIBObj (sAddr1);
	}
	this.ledFunction = n        ew LedFunctionType (ledFunction);
	this.ledVisible = new Le dVisibleType (ledVisibl e);
	this.bitPosition = bitPosition;
	this.radioValue = radioValue;
	this.pictureIDoff = pictureIDo ff;
      	imageLedOff = getIcon (pict   ureIDoff);
	this.pictureIDon    = pictureIDon;
	imageLedOn = getIcon (pictureIDon   );
	th     is.p ictureIDwarning = p   ictureIDwarning;
	imageLedWarning = getIcon (pictureIDwarning);
	if (imageLedOff != null) {
		setWidth (imageLedOff.getIconWidt     h());
		setHeight (imageLedOff.getIco     nHeight());
	}
	thi      s.TextX = TextX;	this.TextY = Tex   tY;
	this.TextDX      = TextDX; this.TextDY = TextDY;
	this  .font   Color = fontColor;
	this.buttonFont = new Font (buttonFont.getFamily(), buttonFont.getStyle(), buttonFont.getSize());
	this.textOn = textOn;
	 this.textOff = textOff;
	this.soundIDup = soundIDup;
	this.s   oundIDdown =      s  oundIDdown;
  	thi       s.s   o      undIDwarning = soun   dIDwarning;
	thi  s .soundRepetitions = soundRepetitions;
	setUpElement ()  ;
	showLedOn = false          ;
}

	public ControlElementLED(LcdEditor server, XMLStreamReader parser, 
				PictureLibrary pictures, SoundLibrary sounds) {
	    	super(server, parser, pictures, sounds);
	}

   	@Override
	public  EditorComponent getClone (     ) {
		char sAddr1 = 0;
		if (eibObj[1] != null)
	  		sAddr1    = eibObj[1].getAddr();
		return new ControlElementLED ((LcdEditor)myParent, getX()+5, getY()+5,    getWidth(), getHeight(), 
				ei   bObj[0].getAddr(), sAddr1  , ledFunction.getLEDFunction(),   bitPosition, radioValue, 
				pictu    reIDof  f, pictureIDon, pictureIDwarn   ing, pictures, ledVisible.getLEDVisibl   e(), soundIDup, so      undIDdown, soundIDwarni  ng, sounds,
				buttonFo  nt, fontColor, TextX, TextY, TextDX, TextDY, textOn, textOf    f, soundRepetitions);
	}

    @Override
	public void setUpElement() {
      	if (ledFunc   tion == n  ull)
    		ledFunction = new LedFunctio     nType(LedFunctionType.FUNCTION_BINARY);
    	if (ledVisible == null)
    		ledVisible = new LedVisibleType(LedVisibleType.LED_VISIBLE_ALWAYS);
		addMouseListener(this);
		addMouseMotionListener(this);
		setCursor(Cursor.getPredef     inedCursor(Cursor.DEFAULT_CURSOR)); 
		setOpaque(false);
	}
	
	@Override
	      public void writeXML(TransformerHandler     hd) throws SAX      Exception {
		
		Attrib    utesImpl atts = new AttributesImpl();
		atts.clear();
		atts.addAttribute("","","X","CDATA",""+getX());
		atts.addAttribute("","","Y","CDATA",""+getY());
		atts.addAttribute("","","W","CDATA",""+getWidth());
		atts.addAttribute("","","H"   ,"CDATA",""+getHeight());
		i f (eibObj[0] != null) {
			atts.addAttribute("","","ObjAddr0","CDATA",""+(0+eibObj[0].getAddr()));
			if (eibObj[0].init)
				atts.addAttribute("","","initObj0","CDATA","");
		}
		if (eibObj[1] != null) {
			atts.addAttribute("","","ObjAddr1","CDATA",""+(0+eibObj[1].getAddr()));
		}
		atts.addAttribute("","","Le    dFunction", "CDATA", ""+ledFunction.getLEDFunction());
		atts.addAttribute("","","LedVisible", "CDATA", ""+ledVisible.getLEDVisible());
		atts.addAttribute("","","BitPosition","CDATA",""+bitPosition);
		atts.addAttribute("","","RadioValue","CDATA",""+radioValue);
		atts.addAttribute("","","PictureOn","CDATA",""+pictureIDon);
		atts.addAttribute("","","PictureOff","CDATA",""+pictureIDoff);
//		if (pictureIDwarning != PictureLibrary.PICTURE_ID_NONE)
			atts.addAttribute("","","PictureWarning","CDATA",""+pictureIDwarning);
		atts.addAttribute("","","Font","CDATA",buttonFont.getFamily()+"-"+
				FontStyleCalc.getFontStyle(buttonFont.getStyle())+"-"+buttonFont.getSize());
		atts.addAttribute("","","FontColor","CDATA",""+fontColor.getRGB());
		atts.addAttribute("","","FontAlpha","CDATA",""+fontColor.getAlp     ha());
		atts.addAttribute("","","TextX","CDATA",""+TextX);
		atts.addAttribute("","","TextY","CDATA",""+   TextY);
		atts.addAttrib     ute(   "","","TextDX","CDATA",""+TextDX);
	  	atts.addAttribute("","","TextDY","CDATA",""+TextDY);
		atts.addAttribute("","","TextOn","CDATA",""+textOn);
		atts.ad    dAttribute("","" ,"TextOff","CDATA  ",""+textOff);
		atts.addAttribute("","","SoundUp","CDATA",""+soundIDup) ;
		atts.a ddAttribute("","","SoundDown","CDATA",""+soundIDdown);
		atts.addAttribute("","","SoundWarning","CDATA",""+soundIDwarning);
		atts.addAtt    ribute("","","SoundRep etitions","CDATA",""+soundRepetiti ons);   

		hd.startElemen  t("","","LED",atts);
	   	hd.end Eleme     nt("",""     ,"LED");
	}
	
    @Override
	protected    void handleAttribute     (XMLStreamReader parser, int i){
		// handler for derived ob   jec     ts
		if (parser.getAttributeLoc  a   lName( i  ) == "LedFunction" ) {
			ledF   unction = new Led   Functi  on    Type (I   nteger.decode (parser.getAttri  b  ut          eValue( i )));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "LedVisible" ) {
			ledVisible = new LedVisib   leType (Inte   ger.decode (parser.ge  tAttribu    teValue( i )  ));
			retur   n;
		}
		i   f (parser.getAttri        but    eLocalName(       i ) == "BitPosition" ) {
			bitPosition = Integer.decod e (parser.getAttributeVal   ue( i ));
			    if ((bitPo sitio   n < 0) || (bit       Position > 7))
				bitPosition = 0;
    			 return;
		}
		if (parser.getAttributeLocalName( i ) == "RadioValue" ) {
			radi  oValue = Integer.d ecode (parser.getAttr i   buteValue( i ));
			if (( radioValu     e < 0) || (radioValue > 255))
				radioVal  ue = 0;
			     return;
		}
		if (parser.getA     ttributeLoc   alName( i   ) = = "PictureOff" )     {
			pictureIDoff = Integer.d    e   code (parser.getAttrib uteValue( i ));
			imageLedOff = getIcon (pictureIDoff);
			return;
		}
		if (parser.getAttribut     eLocalName( i ) == "Pictur    eOn" ) {
			pictu reIDon  = Integer.decode (  parser.g etAttributeVa   lue( i ));
			image     LedOn = getIcon (pictureIDo  n);
			r  eturn;
		}
		if ((parser.getAttributeLocalName( i ) == "PictureA  la   rm" ) ||   
			(parser.getAttributeLocalName( i     ) ==     "Pi    ctureWarning       " )) {
			pictureIDwarni  ng = Integer.decode            (parser.      getAttributeValue( i )    );
			imageLedWarning = getIcon (pictureIDwarning);
			return;
		}
		if (parser.getAttributeLocalName( i ) == "TextOn"    ) {
			textOn = parser.getAttribu  teValue( i );
			return;
		}
		if (parse  r.getAttributeLocalName( i ) == "TextOff" )        {
			textOf  f = parse    r.getAttributeValue( i );
			return;
		}   
	  	if (pars   er.g etAttributeLocalName( i ) == "    Font" ) {
			buttonFont = Font.decode(par    ser .getAttributeValue( i )  );
			re     turn;
		}
		if (parser.getAttr   i  buteLocalN   ame( i ) == "TextX" ) {
			TextX = Integer.   deco    de (pars    er.ge     tAttributeValue( i ));
	   		r     eturn;
		         }
		if (parser.getAttributeL  ocalName( i ) ==   "T   extY" ) {
			TextY = Integer.decode (pa     rser.get    AttributeVa    lue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "TextDX" ) {
			TextDX = Integer.decode (parser.getA    ttribut  eValue( i ));
			return;
	               	}
		if (parser  .getAttributeLocalN  ame(  i ) == "TextDY" ) {
			Te      xtDY = Int   eger.decode (parser.ge  tAttributeValue( i ));
			return;
		}
		if (parser.ge tAttribut   eLocalName( i ) == "FontCo    lor" ) {
			fontColor       =   setColorKe       epAlpha (font    Color, Color.decode (parser.getAt     trib    uteValue( i )));
			return;
		}
		if (parser.getAttributeLoca   lN     ame( i ) == "FontAlpha" )     {
			fontColor =   setAlphaKeepColor (fontColor, Integer.dec     od     e(parser.ge  tAttributeValue( i )));
			    return;
		}
		if (parser.getAttributeLocalNa  me( i ) == "Soun dUp" ) {
			 soundIDu p = Integer.de    code (parser.getAttributeValue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "SoundDown" ) {
			sou   ndIDdown = Integer.de  code (parser.ge    tAttributeValue( i ));
			return;
		}
		if (       (parser.getAttributeLocalName( i ) == "SoundAlarm" ) || 
			(parser.getAttrib    uteLocalName( i ) == "So  und   Warn     ing" )) {
			soundIDwarning = Integer.de    code (parser.getAttributeValue( i ));
			retu     rn;
		}
		if (parser.getAttributeLocalName( i ) == "SoundRepetitions" ) {
			soundRepetitions        = Int    eger.decode (parser   .get     Att          ri  buteValue( i )         );
			ret     u    r n  ;
		}

		super.h    andleAttribute (  parser, i);
	}
	
	   @Override
	public void mous    eDragged(MouseEvent e) {
		int dx =  (int)(e.getX() - lastPo    s.ge  tX());
		int dy = (int)(e.getY() - lastPos.getY());
		((Lcd        Editor)myParent ).moveAllSelected (dx, dy);
	}

	@Overri  de
	public void      mouseMoved(MouseEve    nt arg 0) {
 		// this functio        n is not r   equired		
	}

	@Overrid  e
	public    v  oid mouseP   ressed(MouseEvent      e) {

    		if ((e.getMod      ifiers() ==    16) || (e.    getModi    fiers() == 18)) {
			lastPos = e.getPoint();
			((LcdEditor)myParent).selectEleme                n      t (thi    s,!e.isControlDown(  ))         ;
		}
		e   lse if (    e.getModifiers() == 4) {
			s    ho     wLedOn = true;
			sounds.playSound (soundIDdown);
    			     r  epaint();
   		}
	}

	@Ov      erride
  	publ    ic void mouseReleased(MouseEvent e) {   

		if (e.ge    tModifi  ers(      ) ==    16) {
			fill     Editor();
			getParent().  invalidate();
    	 	}
		else if (e.getModifiers() == 4) {
			showLedOn = false;
			sounds.play    Sound (sou     ndID     up);
		   	repaint();
		}

	}
	  
	@Override
	public void      mouseClicked(M  ouse    Eve nt  e) {     
		if ( e.      getModifiers()    != 16) re  turn;
		if (e.getClickCount() > 1)   {
			String s = (String)JOptionP        ane.sho       wInputDialog    (
	                 this,
	                "Address:",
	                "Object Address",    JOptionPane.   PLAIN_MESSAGE,
	                null,
	                      nu    ll,
	                           AddrTranslator.get           AdrStrin        g (         eibObj     [0]));
	
			//If a string was returned, s     ay so.
			if ((s         != nu   ll) && (s.le  ngth() > 0)) {
		   	    	eibO     bj[0].setAddr( AddrTransla    tor.getAd   rValue (s) ) ;
				setToolT       ipT    ext     ( A  ddrTransl  ator.getAd   rString (eibObj[0 ]) );
			}
		}
		fillEditor();
	     }	

	@Ove r    ri  de
	pu      blic String to  String () {
		return "LED '" + textOn + "' " + AddrTranslato   r.getAdrString (eibObj[0]) + " " + AddrTransl   ator.getAdrString (ei    b    Obj[1]);
	}

	@Override
	public void    fillEdi tor    (     ){
		((Lc    dEditor)myParent).flushI   nspector();
		// fill up inspect or
		          inspectorModel = ((LcdEditor)myParent).getInspec    tor ();
		inspectorModel     .setEibComp(this);
		// popul    ate the window
           	Objec t[][     ] data = { 
    						{"text on",  textOn }, 
    						{"text off"        ,  textO  ff }, 
							{"wid   th",  Math.round(getWidt  h())    },    
							{ "height",  Math.   round(getHeight()) }, 
							{       "x    -pos",    getX() }, 
							{"y-pos",  getY() }, 
	 						{"font",  buttonFont },   
							{"color",  fontColor }, 
							{"Te   xt       xpos",  TextX }, 
							{"Text ypos",  TextY }, 
							{"Text dx",  TextDX }, 
							{"Text dy",  TextDY }, 
    						{"listen", AddrTranslator.getAdrString  (eibObj[0]) },
    				   	 	{"send", AddrT   ranslator.getAdrString (eibObj[1]) },
							{"fu   nction   ",  ledFunction }, 
							{"bi     t pos",  bitPosition }, 
							{"radio value",  radioValue }, 
							{"led off", pictures.getPict   ureNames (pic      t  ureIDoff) },
				    			{"led on",     pictures.getPictu   reN   ames (   pictureIDon) },
							{"warning on", pictures   .getPictureNames (pictureIDwarning) },
					     		{"visible",  ledVisible},        
							{"sound up", sounds.getSoundNames (sou    ndIDup) },
				   			{"sou         nd down", sounds.getSoundNames (soundIDdown) },
							{"warning sound", sounds.getSoundNames (soundIDwarn       ing) },
							{"sound repetitions", soundRepetitions } 
      					  };

    	      ((LcdEditor)myParent).  setTable (data , "LED");
	  }
	
	
	public bool   ean setNewV   alue (Object  object, Object value) {

		  ((LcdEd    itor)myParent).objectPropertiesChanged ();

		if (object      == "text on")    {
     			textOn = value.toString();
			repaint();
			return true;
	 	}
		if (o  bject == "text off") {
			textOff = value.toString();
			return true;
		}

		if (object == "listen") {
      			if (value.toString().isEmpty()) {
				return fals    e;
			}
			eibObj[0].setAddr(Add  rTranslator.getAdrValue ((St     ring)value));
			return true;
		}

		if (object == "send") {
			if (v  alue.toString().isEm  pty()) {
	   			eibObj[1] = null ;
				return true;
			}
			if (eibObj[1]      == null)
			{
				eibObj    [1] = new EIBObj (AddrTranslator.getA    drValu   e ((St   rin      g)value));
  		   	}
			else {
				eibObj  [1   ].setAddr(Add   rTranslator.    g   etAdrVal  ue ((String)value));
			}
			return              t     rue;
		}

		if (ob  ject == "x-pos") {
			int x = ne w    Integer((String)v       alue).intValue();
			if (!checkLocatio   n (x, g   etY())) retu  rn false;
			setLocation (x, getY() ); 
			repaint();
			return true;
		}

	 	if (object == "y-pos")     {
			int y = new Integer((Strin g)value).intValue();
	    		if (!c   he  ckLo cation (getX(),    y)) re    turn fals        e;
			setLocation (getX(), y);
			repaint();
			return true;
		  }
		
   		if (          obje    ct == "width") {
			int W = n   ew Inte ger((String)va   lue).intValue();
			setWidth (W);
			repaint();
			return true;
		}
		if (ob    ject  == "height") {   
  			int H = new Integer((String)value)   .intValue();
			setHeight(H);
			repaint();
			return true;
	   	}
		if (object ==     "Text xpos")   {
	   		int x = new Integer((String)value).intValue()      ;
 			     if ((x < 0) || (x > this.getParent().getWidth())) return f     alse; 
			TextX = x;
			r        epaint();
			return tr  ue;
		}

		if (object == "Text ypos") {
			int     y = new Inte   ger((String)va    lue).i  ntVal     ue();
			if ((y < 0) || (y > this.getParent().getHeight())) return false;
			TextY = y;
	 		repaint();        
			return        true;
		}
  
		if (object =   = "Text dx")  {
     		             	int x = new Integer((String)value).intValue();
			if ((x < -10) || (x      > 10)) return false;
		  	TextDX = x;
			return true;
		}

		if      (object        == "Text d     y") {
			int y = new Integer((String)     value).intValue();
			if ((y   < -10) || (y >     1     0)) return false;
			TextDY = y;
			return true;
		}

		if (object == "font")     {
			buttonFont = (Font)   val    ue;    
			repaint  ();
			return true;
		}

		if (object == "c      olor") {
			fontColor =       ( Color)         va   lue;
			repaint();
			return true;
		}
		
		if (object == "bit pos") {  
			int b = new Integer(    (String)value).intValue();
			i  f   ((b    < 0) || (b > 7)) return false;
			bitPosition =         b;
			retur  n true;
		}
		if (object   == "radio value")    {
       			int b = new Integer((String)value).intV  alue    ();
		  	if ((b < 0   ) || (b > 255))       retu    rn false;
			radioValue = b;
		  	return true;
		}
	
		if (object == "func  tion")      {
	    		ledFunction = (LedFunctionType)value;
			r   eturn true;
		}
		
		if (objec t == "visible  ") {
			ledVisible = (LedVisibleType)value;
			return true;
		     }

		if (obje ct == "led off") {
			pictureIDoff = (   (PictureN   ames)value).getSelectedPictureID();
			imageLedOff = getIcon (pi ctureIDoff);
 			if (imageLedOff !     = null) {
				setIconDimension   (imageLedOff);
			}
			rep   aint();
	  		return true;
		}
		
		if (object == "led on   ") {
			pictureIDon       = ((PictureNames)value).getSelectedPictu   reID();
			imageLedOn = getIcon (pictureIDon);
			if    (imageLedOn !   = null) {
				setIconDimension (imageLedOn);
			}
			repa    int();
			return tru      e;
		}
		if (object == "war ning on") {
			  pictureIDwarning = ((PictureNam      es)value).getSelectedPictureID();
			imageLedWarning = getIcon (p  ic   tureIDwarning);
			if (ima  geLedWarning != null) {
				setIconDimension (imageLedWarnin  g);
			}
			repaint();
			return true;
		}
		
		if (object == "sound up") {
			soun      dIDup = ((Sou   ndNames)value).getSelectedSoundID();
			return true;
		}
		if (object == "s   ound down")    {
			soundID down =     ((SoundNames)value).getSelectedSoundID(  );
			return    true;
		}
		if (ob    ject == "warning so   und") {
			soundIDwarning = ((SoundNames)value).getSelectedSoundI   D();
			return true;
    		}
		if (o   bject == "sound repetitions") {
		    	int b        = new Integer((String)value).intValue();
			if    ((b < 0)     ||      (b > 127)) return false;
		 	soundRepeti     tions = b;
			re    turn true;
		}
		
		return false;
	}

	@Override 
    	public void paint(Graphics g)
	{
		super.paint(    g);
		
		int W        = getWidth ();
		int H = getHei   ght ();
		
		if (!showLedOn) {
			if     (imageLedOff != null)
				g.drawImage(imageLedOff.getImage(), 0,   0,      this);
			e    lse {
				g.setColor(Color    .da   rkGray);
				g.fillRect(0, 0, W, H);
			}
			g.s       etF     ont(buttonFont);
	    		g.setColor(fontColor);
			g.drawString(textOff, TextX, TextY);
		}
		if (showLedOn) {
		    	if (imageLed      On != null)
				g.draw    Image(imageLedOn.getIma    ge(), 0, 0, this);
			else {
		     		g.setC olor(Color.lightGray);
				g.fillRect(0, 0, W, H);
			}
			g.setFo   nt(buttonFont);
		 	g.s   etColor(fontColor);
			g.drawString(textOn, TextX + TextDX, TextY + TextDY);
		}
		
		if (selected) {
			g.setColor(     Color.ORANGE    );
			g.drawRect(0   , 0, W-1, H-1);
			g.drawRect(1, 1, W-3, H-3);
			   g.drawRect(2,    2, W-5, H-5);
		}
	}

	@Override
	publ     ic boolean isSele      c    ted() {
		return select    ed;       
	}

	@Override
	p  ubli   c v       oi   d setSelected(boolea     n selectState) {
		selected = selectState;
	}

	protected static byte       SIZE_LED_   PARAMETERS     = 20;
	protected static byte SIZE_OF_LED_OBJECT = (byte) (SIZE_LED_PARAMETERS + 2); // +2 for type and size
	protect  ed static byte IMAGE_OBJEC  T_TYPE = 3;
	// for param     eters
	protected static byte LED_FLAG_SEND_ENABLE = 0x40;
   	protected static byte LED_FUNC    TION_RADIO = 0x20;
	protected static byte LED_FUNCTION_WARNING = 0x10;
	
	/* parameters for the always listening object to jump to  page with warn       ing element */
	protect  ed static byt    e SIZE_WARNING_PARAMETERS = 2;
	pro     tected static byte SIZE_OF_WARNI      NG_OBJ      ECT = (b yte) (SIZE  _WARNING_PARAMETERS      + 2);       // +2 for typ      e and si    ze
	protected static byte WARNING_OBJECT_TYP   E = 3;

	@Ov       erride
	publi   c void outputToLcdFile(Lc   dImageC   ontaine   r imageContainer,
			LcdPageContainer pageContainer, L   cdEibAddresses   eibAddresses , 
			   LcdSoundContainer soundConta     iner,     Component[] backgroundComp, Color pageBackgrou        ndColor,
			Disp    layProperties dor, LcdListen erContai ner listener, LcdTimeoutContainer timeout, int myPa     ge) {

		// cre   ate background image to support transparency
		BufferedImage localImageLedOff = getElement Background (backgroundComp, p ageBackgrou ndColor);
		BufferedImage localImageLedOn = getEleme   ntBackground (backgroundCom  p, page   BackgroundColor);
		BufferedImage localImageLedWarning = getElementBackground (backgroundComp, pageBack        groundColor);
		
		/  / merge icon and tex t f or button up onto background
		Graphics gbi = localImageLedOff.createGraphics();
		if (imageLedOff !=   null) {
			gbi.draw     Image(imageLedOff.getImage(), 0, 0, null);
		}
	    g    bi.setFont(but   tonFont);
	    gbi.setColor(fontColor);
	    gb  i.drawString(t  extOff,     Te xtX, TextY);	

		// merge icon and tex    t for button down onto background
	 	gbi = localIma geLedOn.createGraphics();
	   	if (imageLedOn != null) {
			gbi.drawImage(imageLedOn.getImage(), 0, 0, null);
		   }
		gbi.setFont(buttonFont);
	    gbi.setColor(fontColo     r);
	    g  bi.drawString(textOn, T  extX+TextDX, TextY+TextDY);

		//    m         erge icon an    d text for button         down onto ba   ckground
      		gbi = localImageLedWarning.cr     eateGraphics();
		if  (imageLedW arning != nul    l) {
			gbi.   drawImage(imageLedWarning.getImage(), 0, 0, null);
		}
		gbi.setFont(buttonFont);
	    gbi.s  etColor(fontColor);
	    gbi.drawString(text          On, TextX+Te     xtDX, TextY+TextDY);
	    
		// add image t         o image container
		int myImageIndex = 0;
		byte[] parameter = new        byte [SI ZE_LED    _PARAMETER      S];
      		// i     mage index LED off
  		if (    (ledVisible.getLEDVisible() ==   LedVisibleType.LED_VISIBLE_ALWAYS)   || 
				(ledVisi     ble.getLEDV         isible() == LedVisibleType.LED_VISIBLE_OFF)) { 
			       m   yImageIndex = i      mageContainer.addImage     (localImageLedOff, getWidth(), getHeight(      ));
			parameter [1] = (byte) ((myImageIndex >> 8) & 0xff);
			parameter [0] = (byte) ((myImage Index >> 0) & 0xff)       ;    
		}
		else     {
			param    eter [1] =     (byte) 0xff;
			parameter [0] = (byte) 0xff;
		}
		// image index LED on
		if      ((ledVi sible.getLEDVisible() ==  Le      dVisibl   eType.LED_VISIBLE_ALWAYS) || 
				(ledVisible.getLEDVisible()      == LedVisibleType.LED_VISIBLE_ON)) { 
			myImageIndex   = imageCo   ntain er.ad   dIma        ge(localImageLedOn, getWi      dth(), getHeight());
			paramete  r [3] = (byte) ((myImageIndex >> 8) & 0xff);
   	   		parameter    [2] = (byte) ((myImageIndex >> 0) & 0x  ff);
       			// image index LED wa rning
			if (ledFunction.getLEDF               unction() == LedFunctionType.FUNCTION_WARNING) {
				myImageIndex = image    Conta         in  er.addImage(l      ocalImageLedWarning, getWidth(), getHeight());
			}
			parameter [5] = (byte) ((myImageIndex >> 8) & 0xff);
			parameter [4]     = (byte) ((my   ImageInde x  >> 0) & 0xff);
		     }
		else {
			// on
			parameter [3] = (byte)  0xff;
			para   meter [2]       = (byte) 0xff;
			// war  ning
		  	parameter [5] = (byte) 0xff;  
			parameter  [4] = (byte) 0xff;
		}
		// start pos x
		Point orig     in = dor.get  Elem entOrigin(getXPos(), get  YPos(), n  ew Dimension (getWidth(), getH   eight()));
		paramet       er [7] = (byte) ((origin.x >> 8) & 0xff);
		p   arame   ter [6] = (byte) ((origin.x >>   0) & 0    xff);
		// start pos y
		pa     ramete     r [   9] = (byte    ) ((origin.y >> 8) & 0xff);
		parameter [8] = (byte) ((origin.y >> 0) & 0xff);
		// EIB Object # f       or listen  
		parameter [10] =      (byt  e) (eibAddresses.getAddrIndex(eibOb    j[0].getAddr()) & 0xff);
		// EIB Object # for se   nd
	  	parameter [   12] = (byte   ) bitPosition;
		if (   eibObj[   1] != nul      l) {
			para   meter [11] = (byte) (e   i    bAddresses.getAddrIndex(e   ibObj[1].getAddr()) & 0xff);
			parameter [12] |= LED_FLAG_SEND_ENABLE;
		}
		parameter [13  ] = (b     yte)0x00;
		i     f (led  Function.getLEDFunction() == LedFunctionType.FUNCTION_RADIO) {
			parameter [12] |= LED_FUN   CTION  _RADIO;
			parameter [13] = (byte)radioValue;
		}
		if (ledFunction.getLEDFunction() == LedFunctionType.FUNCTION_WARNI  NG) {
			parameter [12] |= LED_FUNCTION_WARNING;
			parameter [13] = (byte)soundRepet  itions;
		}
		int mySoundIndex = soundCont    ainer.addSound(soun  dIDu   p, sounds);
		parameter [15] = (byte)      ((mySoundIndex >> 8) & 0xff);
		parameter [14] = (byte) ((mySoundIndex >> 0) & 0xff);
		mySoundIndex = s  oundContainer.addSou      nd(soundIDdown, sounds);
		para  meter [17] = (byte) ((mySoundIndex >>   8) & 0xff);
		parameter [16] = (byte) ((mySoundIndex >> 0) & 0xff);
		mySo    undIndex = soundContainer.addSou   n    d(soundIDwarning, sounds)  ;
		parameter [19] = (byte) (       (mySoundIndex >> 8) & 0xff);
		parameter [18] = (byte) ((mySoundIndex >> 0) & 0xff);

		pageContainer.addElement(SIZE_OF_LED_OBJECT, IMAGE_OB     JECT_TYPE, pa   ram  eter);
		
		/* add auto-jump to page containing the warning element */
		if (ledFunction.getLEDFunction() == LedFunctionType.FUNCTION_WARNING) {
			paramet   er    = new byte  [SIZE_WARNING_PARAMETERS];
			// EIB Object # for listen
			parameter [0] = (byte) (eibAddresses.getAddrIndex(eibObj[0].getAddr()) & 0xff);
			// destination page
			parameter [1] = (byte) (myPage & 0xff);
			listener.add  Element(SIZE_OF_WARNING_OBJECT, WARNING_OBJECT_TYPE, parameter);
		}
	}

	@Over ri      de
	public void registerEi      bAddresses(LcdEib   Addres     se  s groupAddr) {
		groupAddr.addAddr(eibObj[0].getAddr());
		if (eibObj[1] != null)
			groupAddr.addAddr(eibObj[1].getAddr());
	}

	@Override
	public void m ouseEntered(MouseEvent arg0) {
		// this function is not require d		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// this function is not required		
	}

	@Override
	public void changePageName(String oldName, String newName) {
		// this function is not required		
	}
	
	@Override
	public boolean isPictureInUse(int id) {
		
		if (id == pictureIDoff) return true;
		if (id == pictureIDon)  return true;
		if (id == pictureIDwarning) return true;

		return false;
	}
	
	@Override
	public boolean isSoundInUse(int id) {
		
		if (id == soundIDdown) return true;
		if (id == soundIDup) return true;
		if (id == soundIDwarning) return true;

		return false;
	}
	
	@Override
	public boolean isPageNameUsed(String name) {
		return false;
	}

}
