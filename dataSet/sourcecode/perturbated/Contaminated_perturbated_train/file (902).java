package     lcdEdit;
/*
 * Compo        nent of the LCD Editor tool
     *    This t      ool enabl  es the EIB LCD    T   ouch displ  ay user to con               figure the disp  lay   page     s 
 * and save them in a binary format, which can be    downlo   aded into the LCD      To  uch Display   device.
 * 
 * Copyrig     ht (c) 201   1-2        014 Arno Stock <arno.stock@yahoo.de>
 *
 *	Thi   s program is free software; you can redistribute i   t a    nd/or modi         fy
 *	it under the ter ms       of the GNU General Public License version 2 as    
 *  	published      by the Free So  ftwar  e Foundat  ion.
 *
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent  ;
i     mport java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.TransformerHan    dler;

import or g.xml.sax.SAXException;
imp ort org.xml.sax.helpers.AttributesImpl;

@SuppressWarning      s(   "seria     l")
publi  c class ControlElementStateButton extends EIBComp     implem         ents       
						MouseMotion    Listener, MouseLis  tener {

    private Point la   stPos = new Point (0,0);
	PropertiesTableModel i   nspectorModel = nul  l;
	private boolean    selected;
	private int pict  ureIDupOff;
	private int pictureIDdownOff;
	private int pictureIDu  pOn;
	private int    pictureIDdo   wnOn;
	private ImageIcon imageButtonUpOff;
	private ImageIcon imageButt   onDo    wnOff;
	private ImageIcon imageButtonUpOn;
	private ImageIcon imageB        uttonDownOn;
  	private	int TextX;
	priva   te int TextY;
	private int Text     DX;
	private in  t TextDY;
	privat e Font buttonFont;
	private Color fontColo r;
	protected boo   lean s   howBut     t    onDown = f  alse;
	private SButtonFunctionType buttonFunction;    
	private int soundIDdown;
	private int   soundIDup;


	  public ControlE le   mentStateB  utton(LcdEditor server, 
			int x, int y, int w, int h, Stri   n  g name, char sAddr0, boolean addr1Used, char  s   Addr  1,
			int pictureIDupOff, in t pictu    reIDdow    nOff, 
			int pictureIDupOn ,  int picture    IDdownOn, PictureLibr      ary pictures, 
			i n         t s     o   undIDup, int   soundIDdown,   SoundLibrary sounds,     i     nt buttonFunctio  nSelection, 
			Font butt onFon t, Color fontCol          or, int TextX, int    Tex     tY,      in   t Tex tDX, in   t  TextDY) {

		super (server, x, y, w,         h, na    me);
    	this.pictures = pictures;
        	this.sounds =     sounds;
    	this.pict        ureIDupOff = pictureIDupOff;
    	this.pictureIDdow     nOff = pictureIDdownOff;
    	    th    is.pictureIDupOn =         pictureIDupOn;
    	this.pictureID  downOn = pictureIDdownOn;      
		eib   Obj[0] =  new EIBObj (sA   ddr0);
		eibObj[1] = new EIBObj (s    Addr1);
		im a    geBut tonUpOff = getIcon (pictureIDupOff);
		imageButtonDownOff = getIcon (pictureIDdownOff);
		imageButtonUpOn = getIcon (pictu    reIDupOn);
		imageButtonDownOn =    getIcon (pictureIDdown  O n);
		if (imageButtonUpOff != null) {
			setIconDimension (imageButtonUpOff   );
		}
		this.TextX = TextX;	TextY = this.TextY = TextY;
		this.TextDX = TextDX; this.TextDY = TextDY;
		this.fontColor = fontColo    r;
		this.but     tonFont = new Font (buttonFont.getFamily(), button   Font.getStyle(), but tonFont.getSize());
		buttonFunction = new S   ButtonFunctionType (     buttonFunctio      nSelection)  ;
		this.soundIDup = soundIDup;
		this.soun   dIDdown = soundIDdown;
		setUpElement();
		setCursor(Cursor.getPred   ef  inedCurs    or(Cursor     .DEFAULT_CURSOR));
		showBut  tonDow   n = fa lse;
	}

	public ControlEl      ementS        tateButton(LcdEdito  r        server,   
			int x,  int y, int w, int h, String   name, char sAddr0, boolean      a  ddr1Used, cha   r s Add    r1,
			int pictureIDupOff, i    nt p  ictureIDdownOff, 
			int pictureIDup     On, int pictureIDdo  w    nOn, Pi   c    tureLibrary p  ictures, 
			int soundIDup , int soundIDdow n, S    ou  ndLibrary sounds, int buttonFunctionSelection) {

		super (   server, x,   y, w, h, name);

    	th  is.pictures = pictures;
    	this.    sounds = sounds;
		eibObj[0] = new EIBObj (sAddr0);
		if (addr1Used)
			eibObj[1] = n    e      w EIBObj (sAddr1);
  		this.pictureI     DupOff = pic tureIDupOff;
		imageButton      UpOff = getIcon (pictur    eIDupOff);
		this.pictureIDdownOff = p   ictureIDdownOff;
		imageButtonDownOff = getIcon (pict  ureIDd     ownOff);
		this.pictu   reIDupOn = pictureID   upOn;
		imageButtonUpOn = getIcon (  pictureIDupOn);
		thi   s.pic tureIDdownOn = pictureIDdownO  n;
		imageButtonDownOn = getIcon (pictureID    downOn);
		if  (imageButtonUpOff != null) {
			setIconDimension (imageButtonUpOff);
		}
		TextX =       (int) getWidth()/10        ;	TextY = (int) (0.6*getHeight ());
		TextDX  = 1; TextDY = 1;
		butto  nFont = new Font ("Arial", Font.BOLD, 16);   
   		fontCol  or = Color.black;
		butt onFunction    = ne   w SButtonFunctionType (buttonFunctionSelection);
		this.soundIDup = soundIDup;
		this  .soundIDdown = soundIDdown;
		setUpElemen      t();
		setCursor(Cursor.getPredefin    edCursor(Curs   o     r.DEFAULT_CURSOR));
		showButtonDown =        false;
	}


	public     ControlElementStateButton(    LcdEditor server, XMLStreamReader parser, 
			    		PictureLibrary pictures, So     undLibrary sounds) {
		super(server ,    parser,   pi      ctures, sounds);
	}

	@Ove       rride
	public Edi torComponent getCl      one () {
		  if (eibObj[1] != null)
		    	return new ControlElemen   tStateButton ((Lcd  Editor   )myParent, getX()+5, getY()+5, getWidth(), getHeight(), 
										elemen   tName, eibObj[0].getAddr(), true, ei          bObj[1]     .getA  ddr(),
										pictureIDupOff, pictureIDdownOff, pictureIDupOn, pictureIDdownOn, pictures,   
										soundIDup, sou   ndIDdown, sounds, buttonFunction.getButtonFunction       (),
										buttonFont, fontColor, TextX, TextY, T   extDX, TextDY);
		else 
			return new ControlElementStateButton ((LcdEditor)myParent, getX()+5, getY()+5, getWidt   h(),          getHei   ght(), 
					elementName, eibObj[0].getAddr(), false, (char) 0, 
					pictureIDupOff, pi ctureI      DdownOff,       pictureIDupOn, pictureIDdownOn, picture  s, 
					soundIDup, sou     ndIDdow     n, sounds, buttonFunction.getButtonFunction(), 
					buttonFont, fontColor, TextX, TextY, TextDX, TextDY);
	}

	@Override
	public void setUpElement()     {
		if (buttonFont == null    ) buttonFont = new Font ("Arial", Font.PLAIN, 12);
		if (fontColor == null) fontColor = Color.black;
		if (buttonFunction == null) buttonFunct    ion = new SButtonFun    ctionType (ButtonFunctionType.FUNCTION_TOGGLE);
		addMouseMotionListener(this);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
		addMouseListener(this);
		setOpaque(false);
		selected = false;
	}

	
	@Override
	public void writeXML(TransformerHandler hd) throws SAXException {
		
		AttributesImpl atts = new AttributesImpl();
		atts.clear();
		atts.addAttribute("","","Name","CDATA", elementName);
		atts.addAttribute("","","X","CDATA",""+getX());
		atts.addAttribute("",""  ,"Y","CDATA",""+getY());
		atts.addAttribute("","","W","CDATA",""+getWidth());
		atts.addAttribute("","","H","CDATA",""+getHeight());
		atts.addAttribute("","","Font","CDATA",buttonFont.getFamily()+"-"+
				FontStyleCalc.getFontStyle(buttonFont.getStyle())+"-"+buttonFont.getSize());
		atts.addAttribute("",     "","FontColor","CDATA",""+fontColor.getRGB());
		atts.addAttribute("","","FontAlpha","CDATA",""+fontColor.getAlpha());
    		atts.addAttribute("","","TextX","CDATA",""+TextX);
		atts.addAttribute("","","TextY","CDATA",""+TextY);
		atts.addAttribute("","","TextDX","CDATA",""+TextDX);
		atts.addAttribute("","","TextDY","CDATA",""+TextDY);
		if (eibObj[0] != null) {
			atts.addAttribute("","","ObjAddr0","CDATA",""+(0+eibObj[0].getA   ddr()));
		}
		if (eibObj[1] != null) {
			atts.addAttribute("","","ObjAddr1","CDATA",""+(0+eibObj[1].getAddr()));
			if (eibObj[1].init)
	       			atts.addAttribute("","","initObj1","CDATA","");
		}
		atts.add Attribute("","","ButtonFunction","CDATA"," "+buttonFunction.getBut    to    nFunction());
		atts.addAt  tribute("","","PictureUpOff","CDATA",""+pictureIDupOff);
		atts.  addAttribute("","","PictureDownOff","CDATA",""+pictureIDdownOff);
		atts.addAttribute("","","PictureUpOn","CDATA",""+pictureIDupOn);         
		atts.addAttribute("","","PictureDownOn","CDATA",""+pictureIDdownOn);
		atts.addAttribute("","","SoundUp","C DATA ",""+soundIDup);
		att     s.ad dAttribute("","","SoundDown","CDATA",""+so undIDdown);

		hd.startEle     ment("","","SBTN",at       ts) ;
		hd.endElement("","","SBTN");
	}

	@Overri  de
	protected void     handleAttribute (X MLStreamRe   ader parser, int i){
		// handler for derived ob      jects
		if (parser.getAttributeLocalName( i ) == "PictureUpOff" ) {
			  pictureI   DupOff = Integer.decode (p a         rs     er.getAttributeVa   lue(  i ));
			image ButtonUpOff = getIcon (pictureIDu   pOff);
			return;
		}
		if (parser.getAttributeLocalName(     i ) == "PictureDownOff"     ) {
			pictureIDdownOff = Int     eger.decode (parser.getAttributeV   alue( i ));
	    		imageButtonDo  wnOff = getIcon (pictureIDdownOff);
			return;
		}
		if (parse  r.getAttribute    LocalName( i     ) == "PictureUpOn" ) {
			pictureIDupOn = Integer.decode (parser.getAttributeValue( i ));
			imageButtonUpOn       = getIcon (pictureIDupOn);
			return;
		}
		if (p    arser.get    Attribute      LocalN ame( i ) == "Pi  ctureDownOn" ) {
			pictureIDdownOn = Integer.deco   de (parser.getAttribu   teValue( i ));
			imageButtonDownOn    = getIcon  (pictureIDdownOn);
			retur        n;
		}
		if (parser.getAttributeLocalNam   e( i ) == "Font" ) {
			buttonFont = Font.decode(parser.getAtt     ributeValue( i ) );
			return;
		}
		if (parser.getAttri buteL ocalName( i ) == "TextX" ) {
			TextX = Int    ege r  .decode (parser.getAttributeValue( i ));
			r   eturn;
		     }
		if (parser.ge tAttributeLoc  alName( i ) == "TextY" ) {
			TextY =      Integer.d     ecode (p   arser.g   etAttributeValue( i ));
			return;
		}
		if (        parser.getAttributeLocalName( i ) == "TextDX" ) {
			TextDX = Integer.decode (parse     r.getAttributeValue( i ))      ;
			return;
		}
		if (parser.getAttribut        eLocal    Name( i ) == "T  extDY" ) {
			TextDY = Integer.decode (   parser.      getAttributeValue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i   ) ==      "ButtonFunction" ) {
			buttonFunction = new S  ButtonFunctionType (Int ege       r.decode (pars      er.getAttri      buteValue( i )));
			return;
		}
	        	if (parser    .getAttribu  teLocalName( i ) == "Font   Co    lor" ) {
			fontColor = setColorKeepAlpha (fontColor, Color.decode (parser.getAttribut   eValue( i     )));
			return;
		}
		if (parse     r.ge   tAttrib   uteLoca       lName( i ) == "Fon     tAlpha" ) {
			fontColor = setAlphaKeepColor (fontColor, Integer.decode(parser.getAttributeValue( i )))    ;
			return;
		}
		if (parser.getAttributeLocalName( i ) == "SoundUp" ) {
			soundIDup = Integer.decode (parser.getAttri   buteV    alue( i ));
			return;
		}
		if      (parser   .    getAttributeLoca     lName( i ) == "SoundDown" ) {
			soundIDdo       wn = Intege  r.decode (parser.getAttributeValue( i ));
 			return;
		}
		super.handle   Attribu   te (parser,   i);
	}
	
	
   	@     Override
	public void mouseDragged(MouseEven     t    e) {
		if (e.getModifi    ers() == 16) {
			int dx = (int)(    e.getX() - lastPos.getX     (  ));
			int dy = (int)(e.getY() - lastPos.getY());
			(( LcdEditor)myParent).moveAllSelected (dx, dy);
		      }
	   }

	@Overrid  e
	public void mouseMoved(MouseEvent   arg0) {
		// TODO Auto-generated metho    d stub
		
	}

	@Override 
	public v    oid   m  ousePress    ed            (MouseEvent e) {

		if ((e   .getModifiers() == 16) || (e.getMo   di   f  iers() == 18))          {
			lastPos = e.getPoint();
			((LcdEditor)myParent).selectEle      me   nt (this,!e.isControlDown());
	  	}
		els      e if (e. getModifiers () == 4) {
			sh    ow  B uttonDown = true;
			st  ate = (st       ate + 1) & 1   ;
		      	so  unds.playSound (soundIDdow      n);
			re  paint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e       ) {

		if (e.getModifiers() == 16) {
			fill    Edit      or();
		     	getParent().      invalidate();   
		}
		els    e if (e.g    etModifiers()   ==   4) {
		 	showB  uttonDown = false;
			sounds.playSou  nd (soundIDup);
			repaint();
		}
	}

    	@Override  
	p     ubli  c void mouseClicked(MouseEvent    e) {
	 	if (e.  getModifier    s() !=  1  6) retur      n;

		if (e.get   ClickCount()        > 1) {
	 		Str   ing    s = (String)JOptionPane.showInputDia       lo  g(
	                     this,
	                        "Address:",
	                            "Object Address",    JOptionPane.PL      AIN_M ESSAGE,
	                    null,
	                null,
	                AddrT    ranslator.getAdrString (    eibObj[0]));
	
			//I     f a string was returned, say so.
     			if ((s != nu ll) && (s.length() > 0)) {
				eibObj[0].setAddr( AddrTranslator.getAdrValue (s)    );
				setToolTipText( AddrTrans   la  tor.getAdrString (eibOb       j[0])      );
			}
		  }
		fillEditor();
	       }	

	@Override 
	publi c String toString () {
		return "SButton '" + el  ementName + "' "    + AddrTranslator.getAdrString (    ei  bObj[0]) + " " + AddrTranslator.getAdrString (eibO       bj[1]);
     	}

	@Override
	public void fill Editor (){
	    	((   LcdEditor)myParent).  flushInspector();
		// fill up inspector
		inspectorModel = ((LcdEditor)myParent).get Inspector ();
		     inspectorModel.setEibC omp(t    his);
		// populate t  he window
    	Object[][] data =  { 
							{"label",  elementName      }, 
							{"width",  Math.round(getWi     dth()) }, 
							{"hei  ght",  Math.round   (getHeight()) }, 
							{"x-pos ",  getX() }, 
							{"y-pos",  getY() }, 
							{"font",  buttonFont }, 
		   					{"color",  fontCo   lor }, 
							{"Text xpos",  TextX }, 
							{"T     ext ypos",  TextY }, 
							{"Text dx",  TextDX }, 
							{     "Text dy"   ,  TextDY }, 
    						{"send",     AddrTranslator.getAdrString (e      ibObj[0]) },
    						{"listen", AddrTranslato  r.getAd   rString (eibObj[1]) },
							{"btn up off", pictures.ge       tPictureNames (pictureIDupOff) },
							{"bt       n down off"   , pictures.getPictur eNames (pictureIDdownOff) },   
				  			{"btn    up         on", pi   cture   s.getPictureNames (pictureIDupOn) },
							{"btn down on"    , pi      cture    s.getPictureNames (pictureIDdownOn) },
							{"sound up",  sounds.getSou    ndNames (soundIDup) },
 							 {"sound down", sounds.g      etSoundNa      mes (soundIDdown) }
    					  };

       	((LcdEditor)myParent).setTable   (data,  "State Button");
	}
   	
	
	pub  lic boolean setNewValue (Object object, Object value) {
		
		((Lc      dEdi  tor)myPar   ent).objectPropertiesChanged ();

		if (object == "lab   el")   {           
			elementName = (Stri  ng)value;
			repaint();
			re    turn true;
      		}
		if (object == "send")    {
			if (va      lue.toS      tring().isEmpty()) {
			   	return fals  e;
			    }
			eibObj[0].setAddr(AddrTr anslator.get    AdrValue ((String)value));
			return true;
		}
		   if (obje  ct       == "listen"  )  {
			if (value.toString().isEmpty()) {
				eibO   bj[1] = nul l;
			}
			else {
				if (eibObj[1] == null) {
					eibObj[1] = new EIBObj (AddrTranslator.getAdrValue ((String)value));
	       			}
				else    eibObj[1].se  tAddr(AddrTransl   ator.getAdrValu e ((String)val    ue));
			}
			ret   urn true;
		}

		if (object == "x-pos") {
			int      x      = n        ew Integer((String)value).int  Value ();
			if (!checkLocation   (x, getY())) retu  rn false;
			setXPos (x);
			return true;
  		}

	     	if (object =   = "y-pos") {
			int y = new Integer((String)value).intValue();
			if  (!checkLocation       (getX(), y)) re   turn false;
			setYPos (y);
			return true;
		}
		
		if (object == "Text xpos") {
			int   x = new Integer((String)va   lu    e).intValue();
			if ( (x < 0) || ( x > this.g  etParent().getWidth())) retur      n fals  e;
			TextX     = x;
			repaint();
			return true;
		}

		if (object == "Te xt y      pos") {
   			int y = new Intege  r((S     tring)va   lue).intValue()    ;
      			if ((y < 0)  || (y > this.getParen       t().getHeight())) return false;  
			Te      xtY = y;
			repaint();
			   return true  ;
		}

		if (object == "Text d     x") {
			i    nt x = ne w Intege     r((S  tring)value).intValue();
			if ((x < -10) || (x > 10)) return false;  
			TextDX = x;
			return t   rue;
		}

		if (object == "Text d        y") {
      			int y = new Integer     ((String)val   ue).intValue();
			if ((y < -10) || (y > 10)) return fal  se;
			TextDY = y     ;
			return true;
   		}

	   	if (object == "font") {
			buttonFont = (Font) value;
			  repaint();
			 return true;
		}

		if (obje  ct == "color") {
			fontColor = (Color) value;
			rep     ain t();
		   	return true;
   		}
		
		if (object == "width") {
			int W = new Integer((String)val    ue).int Value();
			setWidth (W);
			repaint()     ;
			return true;
		}
		if (object == "height")    {
		  	int H = ne        w Integer((String)value).in    tValue();
			setHeight (H);
			repaint();
			return true;
		}
				
		if ((object ==         "init")     && (value instanceof Boolean)) {
			if ((ei    bObj[1]) != null)
				eibObj   [1].init = (Boolean)value;
			return      true;
		}
		if (object == "functio  n") {
		 	return true;
		}
		if (object == "btn up off") {
			pictureIDupOff = ((PictureNames)value).getSelectedPictureID();
			imageButtonU   pOff = ge    tIcon     (pi        ctureIDupOff);
			if (imageB  uttonUp   Off != null) {
				s     etIconDimension (imageButtonUpOff );
			}
		 	repa     int();
			return true;
		}
		if (object ==   "btn down off") {
			     picture    IDdownOff = ((Pi      ctureNames)value).getSelectedPicture  ID();
			imageButt  onDow     nOff = get   Icon (pi     ct  ureIDd   ownOff);
			if (imageButtonDownOff != null) {
 				setIconDimension (imageButtonDownOff);
			}
			repaint();
			ret   urn true;
		}
		if (object == "btn up on") {
			pic  tureIDupOn = ((Picture   Names)value).getSelectedPictureID();
			imageButtonUpOn =  getIco   n (pictureIDupOn);
			if (imageBu      ttonUpOn != null) {
				setIconDimension (imageButtonUpOn);
			}
			repaint();
			re  turn true;
		}
     		if (object == "btn down on") {
			pictureIDdownOn = ((PictureNames)value  ).getSelectedPi   ctureID(); 
			imageButtonDownOn = getIcon (pictureIDdownOn);
			if           (imageButtonDownOn != null) {
				setIconDimension (imageButtonDownOn);
			}
			repaint();
			return true;
		}
		if (object == "sound up") {
			soundIDup = ((SoundNames)va         lue).getSelectedSoun       dID();
			return          true;
		}
		if (object == "sound down") {
			soundIDdown = ((SoundNames)value).getSelecte   dSoundID();
	     		return true;
		}

		return false;
	}

	@Ove  rri de
	public void mouseEntered(MouseEv   ent     e) {    
		// TODO Auto-generated method stub
		
	}

	@Override
	  public void mouseE   xited(Mous    eEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override 
	public v   oid paint(Graphics g)
	{
		ImageIcon showIcon;
		int textDx = TextX;   
		   int textDy =     TextY;
		int W = getWidth  ();
		int H = getHeight();   
		super.paint(g);
		if (!showButtonDown) {
			if (state == 0) {
		     		showIcon = imag    eB  uttonUpOff;
			}
			else {
   				show    Icon = ima   geButtonUpOn;
			}
		}
		else {
			textDx    += TextDX; 
			t   extDy += TextDY; 
			if (state ==    0) {
				showIcon = imageButtonDownOff;
			}
			else   {
				showIcon = imageBu  ttonDownOn;
			}
		}		
		
		if (showIcon != null) {
			g.drawImage(showIcon.ge   tIma      ge(), 0,         0, th is);
		}
		g.      setFont(buttonFon    t);
		g.setC         olor(fontColor);
		g.dra   wString(e  lementName,    textDx, textDy);
		if (selected)      {
			g.setColor(Color.ORANGE);
			g   .   drawRect(0, 0, W-1, H-1);
			g.drawRect(1, 1, W-3, H-3);
			g.drawRect(       2, 2, W-5, H-5);
		}   
	}
   	
	@Override
	public boolea   n isSelected() {
		return s elected;
	}

	@Override
	public void setSelected(boolean selectState) {
		selected = selec      tState;
	}

	protec    ted static byte SIZE_SBUTTON_PARAMETERS     = 19;
	protected stat     ic byte SIZE_OF_SBUTTON_OBJECT = (byte) (SIZE_SBUTTON_PARAM  ETERS + 2);
	protected static byte      SBUTTON_OBJECT_TYPE = 6;

	@O    verride
	p  ublic void outputToLcdFile(Lcd           ImageContainer imageContainer,
	    		LcdPageContainer pageCon    tainer, LcdEi  bAddresses eib   Addresses,
			L cdSoundContainer     s       oundCon   tainer, Component[] backgroun    dComp, Color pageBackgroundColor,
			DisplayProp  erties dor, LcdListenerContainer listener, LcdTimeoutContainer timeout, int myPage) {

		// create backgro     und image t   o support transparency
	   	BufferedImage localImageButtonUpOff = getElementBackground (backg  roundComp,    pageB  ackgroundColor);
		Buff    eredImage localImageButtonD   ownOff = getElementBackground (backgroundComp, pageBackgroundColor);
		BufferedImage localImageButtonUpOn = getElementBackground (backgroundComp,     pageBackgroundColor);
		Buffer   edImage localImageButtonDownOn = getElementBackground (backgroundComp, pageBackgroun       dColor   );
		
		/  / merge icon a   nd text for button up onto background
		Graphics gbi =     localImageButtonUpOff.create     Graphics( );
		if (imageButtonUpOff != null) {
			gbi.drawImage(ima geButtonUpOff.ge   tImage(), 0, 0, null  );
		}
	  	       gbi.setFo    nt   (but  tonFont);
	    gb           i.setColo   r(f       ontColor);
	    gbi.drawString(elementName, TextX, TextY);	

  		// me    rge icon an    d text for button down onto    backgr       ound
		gbi = loc        alImageBu   ttonDownOff.createGraphics();
	         	if (imageButtonDownOff != null) {
			g bi.drawImage(imageButtonDownOff.get           Image(), 0, 0,  null);
		}
	    gb  i.setFont(buttonF   ont);
	    gbi.setColor  (fontColor   );
   	    gbi        .drawString(    elemen    tName, TextX+TextDX, TextY+TextDY);
		  
		// merge icon    and text for button up onto background 
  		gbi = loca     lIma  geButtonUpOn.createGraphics();
		if (imageButtonUpOn !=    null) {
			g   bi.d   rawImage(imageButtonUpOn.getImage(), 0, 0, null);
		}
		gbi.s    etFont(buttonFont);
	               gbi.setColor(fontCo    lor);
	    gbi.drawString(elementName, Text              X, TextY);	

		// merge ico    n and text for button down onto backg   round
		gbi = localImageButtonDo     wnOn   .createGr   aphics()    ;
		if (i     mageButtonDownO  n != null) {
			gbi.drawImage(imageButto          nDownOn.getImage(), 0, 0,      null);
		}    
	    gbi.setFont(bu ttonFont);
	    gb  i.setColor(fontColor);
	    gbi.drawString  (eleme     ntN   ame, TextX+TextDX, TextY+Text DY);
		
	    // add image to image container
		int m   yImageIndex = 0;
		byte[] parameter = new byte    [SIZE_SBUTT  ON_PARAMETERS];
		// image index button up off   
     		m   yImageIndex = imageCon    tainer.addImage(localImageButto    nUp      Off, getWidth(), getHeight());
		parameter [   1]      = (byte) ((myImageInd       ex >> 8) & 0xff);
		   parameter [0]   = (by     te) ((myImageIndex >> 0) & 0xff);
		// image index button     d  own o    ff
		myIma      geIndex = imageContainer.addImage(localImageButton Do wnOff, getWidth(), getHe  ight())      ;
		parameter [3] = (byte) (    (myImageIn   dex >> 8) &    0xff  );
	     	parameter [2] =    (byte) ((myIma      geIndex >> 0) &      0xff);
		// image in    dex button up on
		myImageIndex     = imageContainer.addImage(localImageBu      ttonUpOn, getWidth(), ge    tHeight());
		parameter [5] = (byte) ((myImageIndex >> 8) & 0xff);
		parameter [4] = (byte) ((myImageIndex >> 0) & 0xff);
		// im           age index button down on
		myImageIndex = im  ageContainer.addImage(localImageButtonD     ownOn, getWidth(), getHeight());
		parameter [7] = (byte) ((myImageIndex >> 8)   & 0xff);
		parameter [6] = (byte) ((myImageIndex >> 0) & 0xff);
		// star   t pos x
		Point origin =   dor.getElementOrigin(getXPos(),       getYPos(), new Dimension (getWidth(), getHeight()) );
		parameter [9] = (byte) ((origin.x >> 8) & 0xff);
		parameter [8] =    (byte) ((origin.x >> 0 ) & 0xff);
		// start pos y
		parameter [11] = (byte) ((origin.y >> 8) & 0xff);
		parameter [10] = (byte) ((origin.y >>  0) & 0xff);
		// EIB Object #1
		parameter [12] = (byte) (eibAddresses.getAdd    rIndex (eibO  bj[0].getAddr()) & 0xff)     ;
		// EIB Object #2
		if (eibObj[1] != null)
			parameter [13] = (byte) (ei     bAddresses .getAddrIndex(eibObj[1].getAddr()) & 0xff);
		else
			parameter [13] = parameter [12];
		// EIB Obje   ct value
		parameter [14] = (byte) buttonFunction.getButton     Function();
		// store sounds
		int mySoundIndex = soundContainer.addSound(soun   dIDup, sounds) ;
		parameter [16] = (byte) ((mySoundIndex >> 8) & 0xff);
		parameter [15] = (by    te) ((my  SoundIndex >>    0) & 0xff);
		mySoundIndex = soundContainer.addSound(soundIDdown, sounds);  
		parameter [18] = (byte) ((mySoundIndex >> 8) & 0xff);
		parameter [17] = (byte) ((mySoundIndex > > 0) & 0xff);
		// store element to current page
		pageContainer.addElement       (SIZE_OF_SBUTTON_OBJECT, SBUTTON_OBJECT_TYPE, parameter);
	}

	@Override
	public void registerEibAddresses(LcdEibAddresses groupAddr) {
		groupAddr.addAddr(eibObj[0].getAddr());
		if (eibObj[1] != null)
			groupAddr.addAddr(eibObj[1].getAddr());
	}

	@Override
	public void changePageN    ame(String oldName, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isP   ictureInUse(int id) {
		
		if (id == pictureIDdownOff) re   turn true;
		if (id == pictureIDdownOn) return true;
		if (id == pictureIDupOff) return true;
		if (id == pictureIDupOn) return true;

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
		return false;
	}

}
