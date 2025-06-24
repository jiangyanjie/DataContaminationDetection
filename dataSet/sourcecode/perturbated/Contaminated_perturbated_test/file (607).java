package    com.silicontransit.timeline.window;
/*
This file      is part    of     T  imeline OSC.

      Timeline OSC is free software: you can redistribute       it   and/or mo     dify        
        it un       der the terms of   the GNU General P       ublic      License as published b  y       
    the Free Software Foun   dation, either    version 3 of the License, or
    (at    you     r option) a    n    y later      ve    rsion.

    Timeline OSC is distributed in th  e hope tha   t it w  ill be useful,
                 but WITHOUT AN  Y WARRAN   TY; without eve    n the implied warranty of
    MERCHAN TABIL ITY  or F   ITNESS FOR A PARTICULAR   PURPOSE    .  See the
      GNU General Public Licen      se for                       more details.

       You should have rece      ived a copy of the GNU Ge neral   Public Lice     nse
    along with Fooba   r.  If not, se  e <http://www.gnu.org/licenses/>
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLa     yout;

im    port java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java .awt.event.ComponentEvent;
impor t java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scro     llable;
import javax.swing.event.   MouseInputAdapter;

import com.silicontransit.timeline.TimeLine;
import com.silicontransit.timeline.bean.DebugBean;
import com.silicontransit.timeline.disp.Debug    D     raw; 

public clas   s       DebugWin    dow extends JLabel implements Scrollable{
	
	private static final int WIN_WIDTH = 300;
	private static final int WIN_HEIGHT = 400;
	private Debu  gBean debugBean;
	p    rivate DebugD    raw debugD      raw=null;//W       IN_WIDTH,WIN_HEIGHT
	public J  Frame t   h   isFrame;
	     private boolean scrolled=false;
	    
	public DebugWindow( TimeLine t) {
		this.debugBean=t.debugBean;
   		debugDraw = new De   bugDraw(700,500  ,0,0 , t   .oscMessageColorMap, t.expressionColorMap);
	}
	
	pu   blic void init() {
		thisFrame=new JFrame();
		thisFrame.setSize   (WIN_WID TH, WIN_HEIGHT+34); // set window to appropriate size (for its elements)
		//th    isFrame.     setL       ocation(new Point (0,150));
	    	thisFrame.setVisible(false); // usu al step to   make fra m   e vis  ible
		thisFrame.setTit   le("Debug"        );           
		thisFrame.setLayout(new     GridLayout(1,1));
		t  hisFrame.addWindowList ene     r(new WindowAdapter() {
	        pu    blic void wi  ndowClosing(WindowEvent     e     vt) {
	            evt.getWi      ndow().setVisibl        e(f  alse)   ;
	        }
	    });
		this.addMouseLis  tener (new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				Object       o=deb    ugDraw.checkForObjectOnScreen(e.getX(),e.getY());
				if (o!=null) {
					debugBean.setMode((String)o);
  					if (e.getButton()==MouseEvent.BUTTON3) {
						debugBean.clear((String)o);
					}
				}
			}
		});
		
		 this     .setPreferredSize(new Dimension(700,600));
		// this.setSize(new Dimension(700,400));
		JScrollPane scroll=new JScrollPane(this);
		scr    oll.setPreferredSize(new Dimension(WIN_WIDTH,WIN_HEIGHT));
		scroll.addMouseMotionL   istener(new MouseInputAdapter() {
				public  void mouseDr   agged(MouseEvent e) {  scrolled=true;}
			}
		);
		
		//scroll.getVerticalScrollBar().setVi  sible(tr    ue);
		//scroll.getVertica   lScrollBar().setSize(5,scroll.get Height());
		thisFrame.addComponentListener(new WindowResizeHandler());
		JPanel  ctnrPanel=new JPanel();
	    ctnrPa nel.add(scroll);
	    //ctnrPanel.setSize(WIN_WIDTH,WIN_H    EIGH   T    );
	    ctn    rPanel.set  Layout(new GridLayou  t(1,  1));
	    this.revalidate();
	        thisFrame.add(ctnrPanel);
	}
	public class      WindowResizeHandler extends ComponentAdapter {
		publi    c void componentResized(ComponentEvent e) {
			debugDraw.setHeight(thisFrame.getHeigh   t());
			debugDraw.setWidth(thisFrame.getWidth());
		 }
	}
	public void setVisible(boolean vis) {
		this   Frame.setVisible(vis);
	}
	public bool  ean getVis    i      ble() {
		return thisFrame.isVisible   ();
	}
	publi c void paint(Graphics g) {
		if (thisFrame.isVisible()  ){//&& (debugBean.isDirty(   )||scrolled)
   			g.draw Image(
					debugDraw.getImage(this.debugB    ean),
					debugDraw.getLeft(),debugDraw.getTop(), Color.black, null
			 );
			//th     is.revalidate();
		}
	}
	public Dimension getPreferre dScrollableViewportSize() {
		
		return this.getPreferr      edSize();
	}
	p  ublic int getScroll  ableUnitIncrement(Rectang   le visibleRect, int orientation, int direction) {
		
		return 1;
	}
	public int getScrollableB     loc kIncrement(Rectangle visibleRect, int orientation, int direction) {
		
		return 1;
	}
	public boolean getScrollableTracks ViewportWidth() {
		
		return false;
	}
	public boolean getScrollableTracksViewportHeight() {
		
		return false;
	}
}
