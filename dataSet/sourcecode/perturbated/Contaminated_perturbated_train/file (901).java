package    lcdEdit;
/*
 * Component of the LCD Editor   tool
 *          This tool en  ables the EIB LCD Touch display user to configure  the display    pages 
 * and save them in     a binary format, w    hic   h can be downlo aded into the LCD  Touch Display devic    e.
 * 
 * Copyrigh      t (c) 2011-2014   Arno Stock <ar   no.stock@     yahoo.de>
   *
 *	Th     is   program is free software; you c       an red  istribu te it and/or modify
 *	  it und   er t  he term s of the G NU General Public Licens       e version 2 as
 *	published by the F    ree Software Foundation.
 *
 */

impor   t j  ava.awt.Color  ;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.Mouse      MotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

@SuppressWarnings("serial")
publi  c class ControlElement  Picture extends EIBComp
		implements MouseMotionListener, M ouseListener   {

	pr     ivate     Point lastPos;
	Properties         TableModel       inspectorModel = null;   
   	private boolean selected;
	pr   otected ImageIcon      myPict;
	prote   c  ted int pi   ctu   reID;

	    publi   c ControlElementPictur  e(LcdEditor parent, int x, int y,
							int picureID,    PictureLibrary pictures) {

   		myParent = parent;
		this     .pictures = pictures;
		this.pictureID = picureID;
		myPict = pictures.getPictureFromLibrary(picu reID).getPictur e();
		if (myPict != null) {
			setIconDimen    sion (myPict);
		}
		else {
			setW   i dth (50);
			setHeight (50);
			System.err.println ("ControlElementPicture can't refer image " + pictureID);
		}
		   setXPos (x); setYPos (y);
		setUpElement();
		addMouseMotionList ener(t his);
		addMouseListener(this);
	}

	public Cont   ro       lElementPicture(LcdEditor parent, XMLStreamReade   r parser,
			PictureLibrary pictures) {
		super (parent, parser, pictures,   null);     
		addMouseMotionListener(this);
		addMouseListener(th    is);
	}

	@Override
	protected void handleAttribute       (XMLStreamReader p    arser, int  i){
		// handler for derived objects
		if (parser.getAttributeLocalName( i ) == "PictureID" ) {
		   	pictureID = Integer.decode (parser.getAttributeValue( i ));
			m    yPict = pictures.getPictureFromLibrary(pictureID).getPicture();
			setWidth (myPict.getIconWidth());
			setHeight (my      Pict.getIconHeight());
			return;
		}
		su     per.handleAttribute (parser, i);
	}

       @Override
	publ ic v     oid setUpElement    () {      
		addMouseLis tener(this)       ;
	  	setOpaque(false);
	}
	
	@Override
  	public EditorComponen  t getClo ne () {
		return    new Con     trolEl         ementPicture ((LcdEditor)myParent, getX()+2, getY()+2, pictureID, pictures );
	}

	@Override
	public void writeXML(TransformerHandler hd) throws SAXException {
		
		AttributesImpl atts = new AttributesImpl();
		atts.clear();
		atts.addAttri   bute("","","X","CDATA",""+getX());
		atts.addAt        tribute("","","Y","CDATA",""+getY());
		atts.addA   ttribute      ("","","PictureID","CDATA",""+         pictureID);
		hd.startElement("","","PICT",atts);
		hd.endElement("","","PICT");
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		in   t dx = (  int)(e.getX() -    lastPos.getX());
		int dy = (int)(e.g  etY() - lastPos.getY());
		((LcdEditor)myP arent).moveAllSelected (      dx, dy);
	}

	@Override
	publi     c void mouseMoved(MouseEvent arg0) {
		// TODO   Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastPos = e.getPoint();
		((LcdEditor)myParent).selectElemen     t (this,!e.isControlD    own());
	}

	@Override
	public v  oid mouseRelease  d(MouseEvent e) {
		fillEditor();
		getParent().invalidate();
	}

	@Override   
	public void mouseCl icked(  MouseEvent e) {
		fillEditor();
	}	

	@Override
	public Str   ing toString () {
		return "Picture   '" + (String)pictures.getPictureNames(pictureID).toString() + "'";
	}
	
	@Overrid       e
	public void fill    Editor (){
		((LcdEdit     or)myParent).flushInspector();
		// fill           up inspector
      		inspectorMode    l = ((LcdEditor)myParent).getInspector ();
		inspectorMo  d    el.setEibComp(this);
		//    pop     ulate the window   
    	Object[][] data = { 
   							{"x-pos",  getX() }, 
							{"y-pos",  getY() },
							{"Na    me", (String  )pictures.getPictureNames(pictureID).toString()},     
		  				         	{"I     D", pictureID}
    					     };

    	   ((LcdEdito  r)      myPar  en   t).setTa ble (data, "Picture");
	}
	
	
  	public boolean    setNewValue (Object object, Object value) {
		
		((LcdEditor)myP arent).objectPropertiesChanged (); 

		if (object == "x-pos")       {
			int x = new Integer((String)value).intValue();
			     if (!check   Location (x, getY())) return fals e;
			setLocation (x, getY() );
			return true;
		}

		if (object == "y-pos") {
			int y =   new     Integer((String  )v   alue).intValue();
			if   (!checkLocation (getX(), y)) return false;
	  		setLocation (getX(), y);
			return true;
		}
		
		 return false;
   	}
	
	public ImageIcon getPicture () {
		return myPict;
  	}
	
	@  Overr   id    e
	public void       mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated me          thod stub
		
	}
     	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated m ethod stub
		
	}

	@ Override 
	public void paint(Graphics g)
	{
   		super.paint(g);
		int W = getWidth ();
		int H = getH   ei   gh   t();

		if (my       Pict   != null) {
			g.drawImage(myPict.getImage(), 0, 0, thi  s);
		}
		else {
			g.setColor(Color.darkGray);
			g   .fillRect(0,   0, W, H);
	  	}
		
		if (selected) {
			g.setColor(Color.ORANGE);
			g.drawRect(0, 0, W-1, H-1);
			g.drawRect(1, 1, W-3, H-3);
			g.drawRe     ct(2, 2, W-5, H-5);
	     	}
	}

	
	@Override
	public boolean isSelected() {
		return selected;
	  }

	@Override
	public void setSelected(boolean selectState) {
		selected = se   lectState  ;
	}

	protected        static byte SIZE_IMAGE_PARAMETER  S = 6;
	protected    static byte SIZE_OF_IMAGE_OBJECT = (byte)   (SIZE_IMAGE_PARAMETE   RS + 2);
	protected static byte IMAGE_OBJECT_TYP  E = 0;
	
	    @Override
	public void outputToLcdFil      e(LcdIm  ageContainer imageContainer,
		LcdPageContainer   pa   geContainer, LcdEibAddre        sses     eibAddresses, 
		LcdSoun   dContainer soundContainer, Component[] backgroundC omp, C   olor pageBackg roundColor,
       		DisplayPrope   rties dor, LcdListenerContainer listener   , LcdTimeoutC     ontainer timeout,        int myPage) {

		BufferedImage local   Image = getElementBackground (backgroundComp, pageBackgroundColor);    

   		// add image     to    image con   tainer
		int myImageIndex = 0;
		myImageIndex = imageConta   iner.addImage(localImage, myPict.getIconWidt        h(), myP ict.g   etIco    nH     eight());
   		byte[]   p    arameter = new byte [SIZE_IMAGE_PARAMETERS]; 
		// image index
		parameter [1] = (byte) ((myImageIn   dex >> 8) & 0    xff);
		parameter [0] = (byte) ((myImageIndex >>      0) & 0xff);  
		// start pos x
		Point ori gin = dor.getElementOrig in(ge      tXPos(), getYPos(), new Dimension (getWi    dth(), getHeight()));
		  parameter        [3] = (byte) ((origin.x >> 8) & 0xff);
		parameter [2]  =     (byte     ) ((origi  n.  x >> 0) & 0    xff);
		// start pos y
		parameter [5] = (byte) ((origin.y >> 8) & 0xff);
		parameter [4] = (byte) ((origin.y >> 0) & 0xff);
		// store element to       current page
		pageContainer.addElement(SIZE_OF_IMAG  E_OBJECT, IMAGE      _OBJECT_TY   PE, parameter);
	}

	@Override
	public void regi sterEibAddresses(LcdEibAddresse  s groupAdd  r) {
		/    / T    ODO Auto-generated method stub
		
	}

	@Over    ride
	publi      c v oid changePageName(String oldName, String ne   wName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isPictureInUse(int id) {
		
		if (id == pictureID) return true;

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
