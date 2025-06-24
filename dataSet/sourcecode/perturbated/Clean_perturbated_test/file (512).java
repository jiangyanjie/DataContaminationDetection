package lcdEdit;
/*
      *  Component of          the LCD Ed  it       or tool
 * This   tool en   ables the EIB L    CD  Touch disp  lay    user  to configure              the display      pages 
      * and save them in a binary forma     t, which c   an   be do wn  loaded  into   th   e LCD    Touch Displ   ay device.     
 * 
    * Co    pyright (c) 2011-2014 A   rno Stock <arno.stock@y  ahoo.de>
   *
 *	This progr   am is free software; you can re distr   ibute it and/or modify    
 *	it un  der the terms of the GNU General   Public License version 2 as
 *	published by the Free Sof    tware Foundation.
 *
 */

import java.awt.Color;
import java.        awt.Component;
imp       ort java.awt.   Dimension;
import java.awt.Graphics;
import java.awt.Point;
import jav    a.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.Mo  useMotionListener;
import java.awt.image.BufferedImage;

import jav ax.swing.Ima     geIcon;
imp      ort javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;   
import org.xml.sax.helpers.AttributesImpl;

@SuppressWarnings("serial")
public class ControlElementPicture extends E IBComp
 		implements MouseMotionListener, MouseListener {

	priva  te  Point l   astPos;
	Propert       iesTableModel inspectorModel = null;
	priva    te boolean selected;
	protecte   d I     mageIcon myPict;
	protected int pictureID;

   	public Contro     lElementPicture(LcdEditor parent, int x, int y,
						  	int picureID, Pict  ureLibrary   pictures) {

		myParent = parent;
		this.pictures = pictures;
		this.pictureID = picureID;
		myPict = pictures      .getPictureFromLibrary(picureID).getPicture()    ;
		if (myPict != n   ul    l) {
			setIconDimension (myPict);
		}
		else {
			setWidth (50);
			setHeight (50);
			Syste    m.err.println ("Con     trolElementPicture    can't refer image " + pictureID    );
		}
		setXPos (x); setYPos (y);
		setUpEleme   nt();
		addMouseMotionListener(this);
		add   MouseListener(this);
	}

	public ControlElementPicture(LcdEditor parent, XMLS t       reamReader parser,
    			PictureLibrary pictures)   {
 		super (parent, parser,      pictures, null);
		addMouseMotionList  ener(this);     
		a     ddMouseListener(t  his);
	}

	@Override
	prote       cted void handleAttrib  ute (XMLStreamReader parser, int i){
		//   handler for derived objects
		if   (parser.getAttributeLocalName( i ) == "PictureID" ) {
			pictureID = Integer.decode (parser.getAttributeValue( i ));
			myPict = pictures.getPictureFromLibrary(pictureID).getPicture();
			setWidth (myPict.getIconWidth());
			s  etHe  ight (myPict.getIc on Height());
			return;
		}
		super.handleAtt  ribute (parser, i);
	}

    @Over  ride
	public      void setUpEle    m    ent() {
		addMouseListener(this);
		setOpaque(false);
	}
	
	@Override      
	public Edi  torCompo   nent getClone () {
		return new ControlElementPicture ((LcdEditor)myParent, getX()+2, getY()+2, pictureID, pictures );    
	}

	@Override
	public void  writeXML(Transformer Handler hd) throws SAXException {
		
		AttributesImpl atts    = new AttributesImpl();
		atts.clear();
		atts.addAttribut    e("","","X","CDATA",""+getX());     
		atts.addAttribute("","","Y","CDATA",""+get    Y());
		att s.addAttribute("","","PictureID","CDATA",  ""+ pictureID);
	   	hd.startElement("","","PICT",   att s);
		hd.endElement("","","PICT");
	}


	@Override
	public voi    d mouseDragged(MouseEvent e) {
		      int dx = (int)(e.getX() - lastPos.g  etX());
		int dy = (int)(e.getY() - lastPos.  g    etY());
		((LcdEditor)myParent)  .moveAllSelected (dx, dy);
	}

	@Ove  rride
	public void mouseMoved(MouseEvent arg0) {
		// TODO Au  to-generated m  ethod stub
		
	}

	@  Override
	public void mouse      Pressed(MouseEvent e) {
		las      tPos = e.getPoint();
		((LcdEditor)myParent).selectElement (th   is,!e.isC  ontrolDown());
	}

	@Override
  	public void mouseRele  ased(MouseEvent e) {
		fillEditor();
		getParent().invalidate();
	}

	@O    verri   de
	public void mouseClicked(MouseEvent e)  {
		fillEditor( );
	}	

	@Override
	public   Stri   ng toSt     ring () {
		re          turn "Picture '" + (String)pictures.getPictureNam     es(pictureID).toString() + "'";
	}
	
	@Override    
	   public void fillEditor (){     
		((LcdEdi tor)myParent).flushInspe    ctor();   
		// f    ill up inspector
		inspectorM   ode   l = ((LcdEdit  or)myParent).get    Inspector ();
		inspectorModel.setEibComp(this);
		// populate the          window
    	Object[][] data = { 
							{"       x-p    os",      getX() }, 
		    		  			{"y-pos",       getY() },
							{"Nam e", (String)pictures.getPictureNames(pictureID).toStrin     g()},
							{"ID", p  ictureID}
    					  };

     	((LcdEditor)myParent).setTable (dat    a, "Pi  cture");
	}
	
	
	publ  ic boolean setNewValue (Object object, Object value) {
		
		((LcdEditor)myParent       ).objectPropertiesChanged ();

		if (object == "x-pos") {
			int x =    new Integer((Stri     ng)value).intValue();  
			if (!checkLocati     on      (x, getY())) return false;
			setLocation (x, getY() );
			    return true;
		}

		if (object =    = "y-pos")     {
			int y = new Integer((String)value).       intValue();
      			if (!checkLocation (getX(), y)) return false;
			setLocation (getX(), y);
			return     t   rue;
	    	}
		
		return false;
	}
	
	public I  mageIcon getPicture ()     {
		return myPict;
	}
	
	@Override
	p     ublic void mouseEntered(MouseEvent        arg0)   {
		// TOD   O Auto-generated     method stub
		
	}
	@Override     
	public void mouseExited(MouseEvent arg0)     {
		// TODO Auto-generated method stub
		
	}

	@Override 
	public void paint (Graphics g)
	{
		super.paint(g);
		int W = getWidth ();
		int H = getHeight();

		if (myPict != null) {
	  		g.drawImage(myPict.getImage(), 0, 0, this);
		}
		else {
			g.setColor(Color.darkGra  y);
			g.fillRect(     0, 0, W, H);
		}
		
		if (selected) {
			g.setCo              lor(Color.ORANGE);
			g.    drawRect(0, 0, W   -1, H-   1);
			g.drawRe   ct(1, 1, W-3, H-3);
			g.drawRect(2, 2, W-5, H-5);    
		}
	}

	
	@Override
	public boolea  n isSelected() {
		retur   n selected;
	}

	@Override
	    public      void setSelected(boolean selectState) {
		selected = selectState;
	}

	protected s  tatic byte SIZE_IMAGE_PAR   AMETERS = 6   ;
	protected static byte SIZ    E_OF_IMAGE_OBJECT = (byte) (SIZE_IMAGE_PARAMETERS  + 2     );
	protected  static byte I MAGE         _    OBJECT_TYPE = 0;
	
	@Overrid  e
	public void outputToLcdFile(Lc   dImageC    ontainer imageContainer,
		L  cdPageContainer pa  geContainer, LcdEibAddresses eibAddre        sses, 
		LcdSoundContainer soundContainer, Comp    onent[] background   Comp, C olor pageBa ckgrou  ndColor,
		DisplayProperties dor, LcdListenerContainer listener, LcdTimeoutCon    tainer time      out, i    nt myPage) {

		Buffered     Image localImage = ge    tElementBackgr ound    (backgroundComp, page  Backgro            undColo r);

		// add im    age to image     container
		int my     Ima  geIndex = 0;
		myImageI ndex = imageContainer.addImage(localImage  , myPict.getIconWidth(), myPict.getIconHeight());
		byte[]     parameter = new byte [SIZE_IMAGE_PARAMETERS];
		// imag    e index
		par    ameter [1] = (b   yte) ((myImageIndex >> 8) &   0   xff);
		parameter [0] = (byte) ((myImage  Index >> 0) & 0xff);
		// start pos x
		Point origin = dor.getEleme ntOri    gin(getXPos(), getYPos(),     new     Dimension (getWidth(), getHeight()));
		parameter [3]     = (byte) ((origin.x >> 8) & 0xff);
		parameter [2] = (byte) ((origin.x >> 0) & 0xf      f);
		// start p     os y
		parameter [5] = (byte) ((origin.y >> 8) & 0xff);
		parameter [4] = (byte) ((origin.y >> 0) & 0xff);
		// store element to      current page
		pageContainer.addElement(SIZE_OF_IMAGE_     OB   JECT,      IMAGE_OBJECT_TYPE, parameter);   
	}

	@Override
	public void registerEibAddresses(LcdEibAddresses groupAddr)     {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePageN   ame(String oldName, String newName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isPictureInUse(int id) {
		
		if (id == pictureID)  return true;

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
