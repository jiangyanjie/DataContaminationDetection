package     lcdEdit;
/*
   * Component of the  LCD Edito  r tool
 * This     tool        enab        les the EIB LCD Tou    ch display use    r to configu    re the display pages 
       * and save th  em in a binary format, which c    an be downloaded   into    the   LC   D Touch Display device.
 * 
 *   Copyright (c) 2011-2014 Arno Stock <ar   no.stock@yaho  o.de>
     *
 *	This pro gram is free  software; y  ou can redis     tribute it and/or modify
 *	it under t   he     term  s of    t    he   GNU Gen  eral Publ   ic License       version 2 as
 *	published by the Free Software Foundat    ion.
 *
 */

import java.awt.Color;
impo    rt java.awt.Component;
import java.awt.Cursor  ;
import     java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java     .awt.event.MouseMot  ionListener;
import java.awt         .image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
im    port javax.xml.strea    m.XMLStreamReader;
import javax.xml.transform.sax.TransformerHandler;

i  mport org.xml.sax.SAXException;
import o   rg.xml.sax.helpers.Attribut  esImpl;

@SuppressWarnings(" serial")
public class ControlElementState       B   utton ext  ends EIBComp implements 
						MouseMoti    onListener, MouseListener    {

    private Point lastPos =   new Point (0,0);
	PropertiesTableModel inspectorMode l = null;
	pri  vate boolean selected;
	p rivate int pictureIDupOff;
	private int pictureIDdownOff ;
	private int pictureIDupOn;
	pr  ivate int pictureIDdownOn;
	private ImageIcon imageButtonUpOff;
	private ImageIcon imageBu    ttonDow nOff;
    	private I   mageIcon imageButtonUpOn;
	pr     ivate ImageIcon imageButtonDownOn;
	private	int TextX;
	private int TextY;
	priva   te int TextDX;
	p    rivate int TextDY;
	private Font bu       ttonFont;
	private Color fontColor;
	protecte         d boolea   n showButtonDown =  false;
	private SButtonFun   ctionType but  tonFunction;
	pr  ivate int sound      IDdown;
	p rivate int sound  IDu      p;


	public ControlElementStateButton(LcdEditor serve  r, 
	       		int x  , int y, in            t w, int h, S  trin        g name, char sAddr0, boolean addr1Use        d, char sAddr1,
		  	int pictureIDupOff, i     nt pictureIDdownOf f, 
			in       t pictureIDupOn, in         t pictureIDdown   O  n,  Pi   ctureLib  ra  ry pi ctures, 
			int soundIDup, int soundIDdown  , SoundLibrary soun  ds    , int buttonF unctionSelec  tion      , 
			Font buttonFont, Color fon    tColo  r, int TextX, int TextY, int TextDX, int  T     extDY) {

	 	sup  er (s   e rver, x, y, w, h, name);
    	this.pic    tures = pictures;
    	this.sounds       =     sounds;
    	t his.pictureIDupOff =     picture               IDupOff;
    	this.pictu      reIDdownOff = pictureIDdownOff;
    	this.pictureIDupOn = pictureIDupO  n;
        	this.picture     IDdown   On = pictureIDdownOn;
		eibObj[0] = new EIBObj (sAddr  0);
		eibObj[1] =     new EIBObj (   sAddr1);
		imag  eButtonUpOff = getIcon (pi          ctureIDupOff);
		imageButtonDownOff = g   etIcon (pictureIDdownOff);
		imageButt  onUpOn = getIcon (pictureIDup    On);
		imageButtonDownOn  = getIcon (pictureIDdownOn);
		if (imageButtonUpOff != null) {
			setIc    onDimensio  n (imageButtonUpOff);
		}
		this.TextX = TextX;	TextY = this.TextY = TextY;
		this.TextDX = TextDX; this.TextDY = TextDY;
		this.fontColor    = fontColor;
		this.buttonFont = new Font         (buttonFont.getFamily(), butto      nFont.getStyle(), buttonFont.getSize()     );
		buttonFunction =    new SButtonFunctionType (buttonFunctionSelecti  on);
		th    is   .sou  n dIDup = soundIDup;
		this.soundIDd  own = soundIDdown;
		setUpElemen  t();
		setCursor(Cur sor.getP    redefinedC    ursor(Cursor.DEFAULT_CURSOR))   ;
		showButto  nDown = false;
	}  

	public Con   trolElementStat eButt on(Lc   dEditor server, 
			int x, int y  , int w, i     nt h  , String name, char sAddr 0, boolean addr1Used, char sAdd    r1,   
			int pictureIDupOff, int p    ictureI       Ddow nOff, 
			int pict     ureIDupOn, int pictureIDdownOn, PictureLibrary picture   s, 
			int soundIDup, int soundIDdown, SoundLibra  ry   sounds, i    n t buttonFunctionSelection) {

		super (server, x, y, w, h, name);

     	this.pictures = pictures;
    	th      is.sounds = sounds;
		eibObj[0] = new      EIBObj (sAddr0 );
		if   (addr1Used)
			eibObj[1]    = new EIBObj (sAddr1);
		this.pict   ureIDupOff =   pic  tureIDupOff;
		imageButtonUp  Off = getIcon (pictur   eI  DupOff);
		thi  s.pictureIDdownOff = pic   tureIDdownOff;
		imageButtonDownOff = getIcon (p      ictureIDdownOff);
		thi       s.pictureIDupOn         = pictureIDupOn;
		image ButtonUpOn = getIcon (pictureIDupOn);
		this.pictureID  do  wnOn = pictureIDdow    nOn;
		imageButtonD ownOn = getIcon (picture       IDdownOn);
		if (imageButtonUpOff != null) {
			setIconDimension (imageButto  nUpOff);
		}
		TextX = (int) getWidth()/10;	TextY          = (int) (0.6*get          Height ());
		TextDX = 1; TextDY = 1;
		buttonFont = new Font ("Arial", Font.BOLD, 16);
		fontColor   = Color.black;
		   buttonFunction = new SButtonFunctionTy pe (buttonFunctionSelection);
		this.soundI Dup = soundID   up;
		this   .    soun dIDdown = soundIDdo    wn;
		setUpElement();
		setCursor(Cursor.getPredefinedCursor(Curs      or.D  EFA   ULT_CURSOR));
		showButtonDown = false;
	}


       	public ControlElementStateButton(LcdEditor server, XMLStreamReader parser, 
					PictureLibrary pictures, SoundLibra      ry sounds) {
		super(server, parser, pic tures, sounds);
	}

	@Override
	public EditorComponent getClone () {
		    if (eibO       bj[1] != null)
			return new     ControlElemen  tStateButton   ((LcdEditor)myP  arent, getX()+5, getY()+5, getWidth(), getHei   ght(), 
										elementNa       me, eibObj[0].getAddr(), true,      eibObj[1     ].getAddr(),
										pictureIDupOff, p  ictureIDdownOff, pictureIDupOn, pictureIDdownOn, pictures, 
		 								soundIDup, soundIDdown, sounds, buttonFu   nction.getButtonFunctio   n(),
										buttonFont   , fontColor,  TextX,  TextY, TextDX, TextDY);
		e   lse 
			r   eturn new ControlElementStateB utton ((LcdEditor)myParent,      getX()+5, getY()+5, ge  tWidth(), getHeight(), 
					elementName, eibObj[0].getAddr(), false, (char) 0, 
					pictureIDupOff, pictureIDdownOff, pictureIDup      On, pictureIDdownOn, pictures, 
					soundIDup          , s  oundIDdown, sounds,    buttonFunction   .getButtonFuncti    on(), 
					buttonFont, fontColor, TextX, TextY, TextDX, TextDY);
	}

	@ Override
	public void setUpElement() {
		if (buttonFont == null) buttonFont = new Font ("Arial", Font.PLAIN, 12);
		if (fontColor == null) fontColor = Color.black;
		if (buttonFunction == null) buttonFunction = new SButtonFunctionType (ButtonFunctionType.FUNCTION_TOGGLE);
		addMouseMotionListener(this);
		setCur  sor(Cursor.getPredefinedCursor(Cursor.DEFAULT_C URSOR)); 
		addMouseListener(this);
		setOpaque(false);
		selected = false;
	}

	
	@      Override
	public void writeXML(TransformerHandler hd) throws SAXException {
		
		AttributesImpl atts = new AttributesImpl();
		atts.clear();
		atts.addAttribute("","","Name","CDATA", elementName);
		atts.addAttribute("","","X","CDATA",""+getX());
		atts.addAttribute("","","Y","CDATA",""+getY());
		atts.addAttribute("","","W","CDATA",""+getWidth());
		atts.addAttribute("","","H","CDATA",""+getHeight());
		atts.addAttribute("","","Font","CDATA",buttonFont.getFamily()+"-"+
				FontStyleCalc.getFontStyle(butt     onFont.getStyle())+"-"+buttonFont.getSize());
		atts.addAttribute("","","FontColor","CDATA",""+fontColor.getRGB());
		atts.addAttribute("","   ","FontA    lpha","CDATA",""+fontColor.getAlpha());
		atts.addAttribute("","","TextX","CDATA",""+TextX);
		atts.addAttribute("","","TextY","CDATA",""+TextY);
		atts.addAttribut e("","","TextDX","CDATA",""+TextDX);
		atts.addAttribute("","","TextDY","CDATA",""+TextDY);
		if (eibObj[0] != null) {
			atts.addAttribute("","","ObjAddr0","CDATA",""+(0+eibObj[0].getAddr()));
		}
		if (eibObj[1] != null) {
			atts.addAttribute("","","ObjAddr1","CDATA",""+(0+eibObj[1].getAddr()));
			if (eibObj[1].init)
				atts.addAttribute(  "","","initObj1","CDATA","");
		}
    		atts.addAttribute("","","ButtonFunction","  CDATA",""+buttonFunction.getButtonFunction());    
		atts.addAttribute("","","PictureUpOff","CDATA     ",""+pictureIDupOff);
		atts.addAttribute("",""," PictureDownOff","CDATA",""+pictureIDdownOff  );
		atts.addAttribute("","   ","PictureUpOn",   "CDATA",""+pictureIDupOn);
		atts.addAttribute("","","PictureDownOn","CDATA",""+pictureIDdownOn);
		atts.addAttri      bute("","","SoundUp","CDATA",""+soundIDup);
		atts.addAttribute("","","SoundDown","CDATA",""+soundI    Ddown);

		hd.startElement("",""      ,"SB   TN",atts)  ;
		hd.endE    l  ement(""    ,"","SBTN")   ;
	}

	@Ove  rride       
	p  rotected void han  dl        eAttribute  (X      MLStreamReader   parser, int i){
		// handler for derived objects
		if (parser.  getAttributeLocalName( i )     == "PictureUpOff" ) {
			pictureIDup   Off = Integer.d  ecode (parser.getAttribute  Value( i ));
			imageBut      tonUpOff = getIcon (pictureIDupOff);
			return;
		}
  		if (parser.getAttribute  Lo      calName( i ) == "     PictureDownOff" ) {
	      		pictureIDdownOff = Integer.decode   (parser.getAttributeValue( i ));
			imageButtonDownOff        = getIcon (pict       ur         eIDdownOff)           ;
			r  eturn;
		}
		if (parser.getAttrib uteLocalName( i ) == "Pict  ureUpOn" ) {
			pictureIDupOn = Integer.decode (parser.getAttrib  uteValue( i )   );
		  	imageButtonUpOn =       getIcon     (pict ureIDupOn);
			return;
		}
		if (parser.       getAttributeLocalName( i   ) == "PictureDownOn" ) {
			pict  ureIDdow nOn = Integ   er.decode (parser.getAttributeValue( i ));
			imageButtonDownOn = getIcon (pictureIDdownOn);
		    	return;
		}
		if (par   ser.getAttributeLocalName( i ) == "Font" ) {
			buttonFont = Font.decode(parser.getA ttri  buteValue( i ));
			return;
		}
		if        (parser.getAttributeLocalName( i ) == "TextX" ) {
			TextX = Integer.decode (parser.g    etAttributeValue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "TextY" ) {
			  TextY = Integer.dec   ode (parser.getAttributeVal      ue( i ));  
			return;
		}
		if (parser.getAt       tri   b    ute   LocalName( i ) == "Text     DX"     ) {
			TextDX = Integer.decode (parser.getAttribut       eValue( i ));
			return;
		}
		if (parser.getAttributeLocalName( i ) == "TextDY" ) {
			TextDY        = Integer.de code     (parser.getAttributeValue( i ));
			retu    rn;
  		}
		if (parser.g   etAttribute  L   ocalName( i ) == "ButtonFunction" )      {
			buttonFunction = new SButtonFunctionT   ype (Integer.decode (parser.getAttributeValue( i )));
			return;
		}
		if (parser.getA    ttrib   uteLocal    Name( i ) ==     "FontColor" ) {
			fontColor =  setColorKeepAlpha (f   ontColor, Color.decode (parser.getAtt   ributeVal     ue( i )));
			return  ;
		}
		if (pa    rser.getA      ttributeLocalName( i ) == "FontAlpha" ) {
			fontColor = setAl  phaKeepColor (fontColor, I nteger.dec  ode(parser.getAttributeValue( i )));
			return;
		}
		if (parser.getAttributeLoc alName( i     ) == "SoundUp" ) {
			soundIDup = Integer.decode (parser.getAttributeValue( i ));
			return;
		}
		if (p  a rse   r.getAttributeLocalName( i ) == "SoundDown"  ) {
			soundIDdown = Integer.decode (p    arse     r.getAtt  ribut eValue( i )   );
			return;
		}
		super.handleAttribute (parser, i);
	}
	
	
    	         @O  verride   
	public      v  oid mouseDragged(MouseEvent e        ) {
		if (e.    getModif iers() ==        16) {
			int dx = (int)(e     .getX()                     - lastPos.getX());
			i nt dy = (int)(e.getY() -         lastPos.getY());
			((LcdEdito  r)myParent).moveAllSelected (    dx, dy);
	   	}
	}

	     @Overr   ide
	public void mouseMoved(MouseEvent ar    g0  ) {
		/     / TODO Au    to-gene  rated method stub
		
	}

	@O v  erride
	public void m ousePr      essed(MouseEv ent e) {

	            	if  ((e.getModifiers() == 16)    || (e.get         Modifi  ers() ==    18)) {
			lastPos  = e.getPoint();
			         ((LcdEditor)myParent).selectElement (t  his,!e.isCon  trolDo    wn());
		}
      		else if (e           .getModifiers()     == 4) {
	     		   show  Button  Down = true;
			state =  (state + 1) & 1;
			soun  ds.playSound (s    oundIDdown);
			repaint();
		}
	}

	@O   verri     de
	public vo   id mouseReleased(MouseEvent e  ) {

		if (e.getModifiers(    ) == 1     6) {
 	 		fillEditor();
			getP     arent(  ).invali date();
		}
		else if (e .getMo     difiers() ==       4       ) {
			showButtonDown = fals         e;
		     	sounds.playSound (soundIDup      );
		  	repaint();
	 	}
	}

	@Overri   de
	public void mouseClicked(MouseEvent e) {
		         if (e.getModifiers() != 16) return;

		if (e.getClickCount   () >          1) {
			String    s =      (String)    JOptionPane.  sh     owInputDialog(
	                     this,
	                   "A    ddress:",
	                  "Object            Address", J      OptionPane.PLA IN_ME   SSAGE,
	                null,
	                      null,
	                  AddrTransl    ator.getAdrStri    ng (eibObj[0]));
	
			//If a string was returned, say      so.
			if ((s != null) &    & (s.length() > 0)) {
				eibObj[0].se tAddr( A    ddrTranslato  r.getAdrValue (s)      );
	 			setToolTipText( AddrTranslator.getAd     rString (eibObj[0]) ); 
			}
		}
		fillEditor();
	}	

	@Override 
	public String toString  () {
		 return "SButton '" + eleme          ntName + "' " + AddrTransl     ator.getAdrString (eibObj[0]) +   " " +   AddrTrans  lator.getAdrString (eibOb   j[1]);
	}
   
	@Override
	public void fillEditor (){
		((LcdEditor)myParent).flushInspector();
		// fill up inspector
		inspectorModel = ((LcdE   ditor)myParent).getInspector ();
		inspectorMo     del.setEibComp(this);
		      // populate the window
    	Object[][] data = { 
							{"label",         elementName }, 
		   					{"width",     Math.round(g    etWidth()) }, 
							{"height",  Math.round(getH eight()) }, 
							  {"x-pos",   getX() }, 
							{"y-pos",  getY() },   
							{"font",  buttonFont }, 
							{"color",  f     ontColor }  , 
				      			{"Text xpos",  TextX },   
							{"Text ypos",  TextY }, 
					 		{"Text dx",  TextDX }, 
							{"Text d   y",  Text DY }, 
    	  					{"send", AddrTranslator.getAdrString (ei bObj[0]) },
    						{"listen", AddrTran    s    lator.getAdrString (eibObj   [1]) },
							{"btn up off", pictures.getP ictureNames (pictureIDupOff) },
							{"btn down off", pictur  es.getPicture        Names   (pictureIDdownOff) },
							      {"btn    up on", pict ures.ge tPictureNames (pictureIDupOn ) },
						   	{"btn down on", pictures.getPictureN ames (pictureIDdownO  n) },
							{"sound up", sounds.getSoundNames (soundIDup) },
						  	{"sound down", sounds.getSoundNames (soundIDdown)       }
    					  };

    	((LcdEd  itor)myParent).     setTable (data, "State Button");
	}
	
	
	public boolean setNewValue (Obj  ect ob   ject, Object value)     {
		
		     ((LcdEditor)myParent).objectPropertiesChanged ();   

		if (object == "label") {
			el ementName = (   St  ring)value;
			repai     nt()   ;
			return true;
		}
		if (object == "s     end") {
			if (    value.toString().isEmpty())    {
				ret       urn false;
			}
	    	     	eibObj[0].setAddr(AddrTranslator.getAdrValue ((S     tring)value));
			return   true;
		}
		if (obje  ct == "listen") {
			if (value .toSt   ring().     isEmpty()) {
				eibObj[1] = null;
			}
			else {
				if (eibObj[1] == null  ) {
					eibObj[1] = new EIBObj (AddrTranslator.getAdrValue    ((String)value));
				}
      				else eibOb  j[1].setAddr(AddrT     r  anslator.getAdrValue ((String)value));
			}
			return true   ;
		}

		if (object ==    "x-pos") {
			int x = n    ew In    teger((String)valu     e).intValue();
			if (!checkLocation (x,        getY()))    return false;  
			setXPos (x);
			return true;
		}

		if     (objec    t == "y-pos") {
			int y = new Integer((String)          value).intValue();
			 if (!checkLocation   (get    X(), y)) return false;
			setYPos   (y);
			return true;
		}
		
		if (object ==    "Text xpos") {
			int  x      = new           Intege     r((String)value).intValue();
			if (( x <     0) || (x >  this.g et   Parent().getWidth())) return false;
			TextX = x;
			rep  aint();
			return true;
		}

		if (object == "Text ypos") {
			int y = n    ew Integer((String)value).intValue  ();
			if ((y < 0) || (y >    this.getP   arent().getHeight())) ret  urn false   ;
			TextY = y;
			repaint();
			     return true;
		}

		     if (object == "Text d   x") {
			int x = new Integer((String)value).intValue();
	  		if ((x < -10) || (x > 10)) return false;
	  		TextD  X = x;
			return true;
		}

	   	if (object == "  Text dy") {
			int y = new Integer((String)v      alue).intValue();
			if ((y < -10) |  | (y > 10)) retur        n false;
			TextDY = y;
			return true;
		}

		if (object == "font") {
			buttonFont       = (Font) value;
			repaint();
			return true;
		}

		if (object ==   "color") {
			fontColor = (  Color) value   ;
			repaint();
			return t  rue;
	  	}
		
		if (object == "width") {
			int W = new Integer((Stri  ng)value).intValue();
			setWidth (W);
			repaint();
			return true;
		}
		if (object == "height") {
			int H = new  Integer((   String)value).intValue();
			setHeight (H);
			repaint();
			return true;
		}
				
		i   f (   (object == "init") && (value instanceof Boole    an)) {
			if ((           eibObj[1] ) != null)
				eibObj[1].init = (Boolean)v     alue;
			return tru     e    ;
		}
		if (obj ect == "fun    ction") {
 			re  turn tr        ue;
		}
		if (object == "btn up off") {
			pictureID    upOff = ((Pi ctureNames)value).getSelectedPictureID();
			imageButtonUpOff = getIcon (pictureIDupOff);
			if (i    mageButtonUpOff != null) {
				setIcon Dimension (imageButtonUpOff);
			}
			repaint();
			return true;
		}
		if (object == "bt    n do   wn      off") {
			pictureIDdownOff = ((PictureNames)value).getSelectedPictureID();
			im  ageButtonDownOff = getIcon (pictureI DdownOff)    ;
			if (im a    geButtonDownOff != nu   ll)     {
				setIconDimension (imageButtonDownOff);
		  	}
			repaint();
			return true;
		}
		if (object == "btn up on") {
     			pictureIDupOn = ((PictureNames)va   lue).ge    tSelectedPictureID();
			imageBut  tonUpOn = getIcon (pictureIDupOn);
			if (imageB  uttonU      pOn       != nul l)     {
				setIconDimension (imageButtonUpOn);
			}
			repaint();
			retur    n true;
		}
		if (obj   ect == "btn down on") {
			pi     ctureIDdownOn = ((P     ictureNames  )value).getSelectedPictureID();
			im    ageButtonDown    On     = getIcon (pictureIDdownOn);
			if (image    B    uttonDownOn != null) {
		  		setIconDimension (im    ageButtonDow  nOn);
			}
			repaint();
			retur    n true;
		}
		if (object == "sound up") {
			soundIDup = ((SoundNames)value).getSelected   Sou    ndID();
			return true;
		}
		if (o   b   ject == "sound down") {
 			soundIDdown =     ((SoundNames)value).getSelectedSound     ID();
			return   t rue;
		}

		retu       rn false       ;
	}

	@Override
	   public void mouseEntered(MouseEvent e) {
		// TODO Auto-gen erated method stub
	  	
	}

	@Override
	publi  c void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override 
	public void paint(Graphics g)
	{
		Ima     geIcon showIcon;
		int textDx = TextX;
		int textDy = TextY;
		int W = getWidth ();
		int H = getHe ight();
		super.paint(g);
		if (!showBut   tonDown) {
			if    (state == 0) {
				showIcon = image    ButtonUpOff;
            			}
			else {
				 showIcon  = imageButtonUpOn;
     			}     
		}
		else {
			text     Dx += T    extD    X; 
			textDy += TextDY; 
			if (state == 0) {
				showIcon = imageButtonDownOff;
			}
			else {
				showIcon = imageButtonDownOn;
			}
		}		
		
		if (showIcon != null) {
			g.drawImage(showIcon.getImage(), 0,   0, this);
		}
		g.set  Fo nt(buttonFont);
		g.setCol or(font Colo  r)             ;
      		g.drawSt  ring(elementName,           textDx, text   Dy);
		if (selected) {
			g.setColor(Color.ORANGE);
			g.drawRect(0, 0     , W-1, H-1);
			g.drawRect(1  , 1, W-3, H-3);
			g.drawRect(2, 2, W-5, H-5);
		}
	}
	
	@Override
	public boolean   isSe  lected() {
	  	return sele    cted;
	}

	@Override
	public void setSele cted(boolean selectState) {
		selected = se      lectState;
	}

	protected static byte SIZE_SBUTTON_PARAM ETERS = 19;
	protected static      byt  e SIZ      E_OF_SBUTTON_OBJECT = (byte) (SIZE_SBUTTON_PARAMETERS + 2);
	prot        ected static b  yte SBUTTON_OBJECT_TYPE = 6;

	@Override
	public void outputToLcdFile(LcdImageContainer imageContainer,
			LcdPageC    ontainer pa      geContainer, LcdEibAddresses eibAddresses,
			LcdSoundContainer soundContainer  , Compon     e  nt[] backgr   oundComp, Color    pageBackgroundColor, 
			DisplayProperties dor,     LcdListenerContainer listener, LcdTimeoutContainer timeout, int myPage) {   

		//         create background imag            e to sup  port transparency
		BufferedImage lo     calImageButtonUpOff = getElem    entBackground (bac kgroundComp,  pageBackground     C   olor);
   		BufferedIma   ge localImageButtonDownOff = getElementBa   ckgroun  d (backgro         undComp, pageB   ackgroundColor);
		B  uffe  redImage localImageButt onUpOn = getElementBackground (background      Comp, pageB  ackg roundColor);    
		BufferedImage localImageButtonDownO    n = getEl   ementBackground    (backgrou     ndComp, pageBackgroundColor);
		
		// merge icon and text for butto   n up onto background
		Graphics gbi = localImageButtonUpOff.createGraphics()  ;
		if (imageButtonUpOff != null) {
			gbi.drawImage(imageBut  tonUpOff.getImage(), 0, 0, null);
		}
		gb   i.setFont(buttonFon  t);
	    gbi.setCol   or(fontColor);
	         gbi.drawString(elementName, TextX, TextY);	

		//    merge icon and text for button down onto background
		gbi =     localImageButtonDownOff.createGraphics();
		if (ima   geButto  nD    ownO        ff != null) {
			gbi.draw       Ima    ge(imag   eButtonDownOff.ge  tImage(), 0, 0, null);
		}
	    gbi.      se   tFont(buttonFo  nt);
	     gbi.setC     olor(fontColor);
	    gbi.drawStrin      g(elem  entName, TextX+TextDX, TextY+Text  DY);
		
		// merge icon and text for bu   tton         up onto background
		gbi = localIma  geButtonUpOn   .createGraphics();
		if (imageButtonU         pOn != null) {
		    	gbi.drawImage(ima  geButtonUpOn.getImage(), 0, 0, null);  
		}
	       	gbi.setFont(buttonFont);
	    gbi.setCo            lor(fontColor);
     	    gbi.drawStrin  g(eleme n   t Na    me, TextX, Tex  tY);	   

		// merge icon and       text f   or button down o   nto backg round
		gbi =  local ImageButtonDownO  n.createGraphics();
		   if (imageButtonDown   On != null) {
			gbi.drawImage(imageButtonDownOn.getImage(), 0, 0 , null);
		}
	    g    bi.setFont(buttonFont);
	    g   bi.setColor(fontColor);
	    gbi.drawString(element     Na   me, TextX+Text D  X, TextY+TextDY);
		
	    // add image to image container
		int m   yImageIndex = 0;
		byte[] param   eter = new byte [SIZE_SBUTTON   _PARAMETERS];
		//      image index button up off
		myImageI ndex = image Container.addImage(localImageButtonUpOff  , getWidth(), getHeight());    
		parameter [1] = (byte) ((myImageIndex >> 8) & 0    xff);
		parameter             [0] =       (byte) ((myImageIndex >> 0) & 0xff);
		// image index button down off
		myImageIndex =    imageContainer .addImage(localIm ageB           utton     DownOff, ge    tWidth(), getHeig ht());
		 par   ameter [3] = (byte) ((myImageIndex   >> 8) & 0xff);
		paramete  r [2] = (b      yte) ((myImageIndex >> 0) & 0xff);
		// i     mage index button up on
		myImageIndex = imageContai  ner.addImage(l  ocalImageButtonUpOn, getWidth(), getHeight());
		   par  am         eter [   5] = (b  yte) ((myImage   Index >> 8) & 0xff  );    
		parameter [4] = (byte) ((myImageIndex >>    0) & 0xff);
		// image index button do  wn on
		myImageIndex = imageContainer.addImag  e(localImageButton  DownOn, getWidth(), getHei   ght());
		parameter [7] = (byte) ((myImageIndex >>     8) & 0  xff);
	 	parameter [6] = (byte) ((myImageIndex >> 0) & 0xff);
		// st     art pos x
		Point origin     = dor.getElementOrigin(getXPos(),  getYPos(), new Dimension (getWidth(), getHeight()));
		parameter [9] = (byte) ((origin.x >> 8) & 0xff);
		p  arameter [8] = (    byte) ((origin.x >> 0) & 0xff);
		// start pos y
		pa rame   ter [11] = (byte) ((origin.y >> 8) & 0xff);
		pa  rameter [10] = (byte) ((origin.y >> 0) & 0xff);
	      	/    / EIB   Object #1
		parameter [12] = (by    te) (eibAddresses.getAddrIndex(eib    Obj[0].getAddr()) &       0xff);
		// EIB Object #2
		if (eibObj[1] != null)
			parameter [13] = (byte      ) (eibAddresses.getAddrIndex(ei bObj[1].getAddr()) & 0xff);
		else
			parameter [13] = parameter [12];
		// EIB Object value
		parameter [14] = (byte) buttonFunction.      getButtonFunction();
		// store sounds
		int mySoundIndex = soundContainer.addSound(sou  ndI   Dup, sounds);
		parameter [16] = (byte) ((mySou  ndIndex >> 8) & 0xff);
		parameter [15] = (byte) ((mySoundIndex      >> 0) & 0xff);
		mySoundIndex = soundContainer.addSound(soundIDdown, sounds);
		parameter [   18] = (byte) ((mySoundIndex >> 8) & 0xff);
		parameter [17] = (byte) ((mySoun      dIndex >> 0) & 0xff);
		// store element to current page
		pageContainer.addElement(SIZE_OF_SBUTTON_OBJECT, SBUTTON_OBJECT_TYPE, pa      rameter);
	}

	@Override
	public void registerEibAddresses(LcdEibAddresses groupAddr) {
		groupAddr.addAddr(eibObj[0].ge      tAddr());
		if (eibObj[1] != null)
			groupAddr.addAddr(e      ibObj[1].getAddr());
	}

	@Override
	public void changePageName(String oldName, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPictureInUse(int id) {
		
		if (id == pictureIDdownOff) return true;
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
