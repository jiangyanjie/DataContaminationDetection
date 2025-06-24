package      concurrency.cruise;

import java.awt.*;

public    class CruiseDisplay   ex    tends Canvas {


                    priva te int reco   r  de     d       = 0;     /    /   recorded speed
    pri      vate boolean cr       uiseOn =  false;    //cruise contr      o     l        state
          pr            ivate final static int botY = 200;
    private Font small = new Font("Helvetica",Font.BOL   D,14);
                 private Fon      t big =  n     ew Font     ("Helveti   c   a",Font.BOLD,18);

    publ     ic Cr ui   seDisplay() {
        super ()        ;
          resize(150,260);
    }


                  Image offs   creen;
         Dimension offscreen  si     ze;
                Graphics off             gr aphics;

    public v            oid back  dr op() {
                 Dimen   s       io  n d = size()    ;
	       if ((offs  c ree    n == null)    ||   (d.widt          h != offscre   ensize   .wid     th)
	                                        || (d.height             != offscreensiz e.heigh     t)) {
	        offscreen = cr eateImag       e    (d.width, d.height);
	        of         fscreensize = d;
	            offgrap      hics = offscreen.getGrap   hics();
	             offgra         phics.setF    ont(small);    
    	               }
        of  fgraphics.s    etColor(Color.black);
          offgr    aphics.fill         Rect(0, 0, s     ize    (      ).       wi   dth, size( ).height);
        off   graphic       s.se   tColor(Color.white   );
        offgra  phics. drawRect(5,10,size().width-15,size().    hei        ght-40);
           o ff     graphics   .set       Color  (Color.blue           );
        of  fgraphics.fillRect( 6,11,s    ize().w   idth-17,size   ().h  eig     ht-42);
       }

    public     void paint(Graphics g)      {
             update(    g);
      }

    public        void       update                   (Graph   ics        g) {
          b    ac kdrop       ();
             // di   s    p      lay rec      orded speed
        o   ffgraphics.setColor(Color.white);    
              offgraphics.setFont(big)     ;
                     offgraphic    s .    d  rawString   ("Cruis   e Control",     10   ,35);
               offgraphics.setFont(    small);
            draw     R    ecorde   d(  off    graphics,2         0,80,recorded);
               if    (cruiseOn       )
                    off  gr a               ph     ics.drawString("Ena    bl    ed",20,     botY+15);
        else
            offgraphics.dra wString("Disabled",20,botY+15);
           i   f                   (cruiseOn)
                offgraphics. setColor(Colo r.gree   n);  
        els  e
           offgraphics.se    tColo r(Color.red);  
        offgraphics.f  illArc(90,botY,20,2   0,0,360);
        g.d   rawImage(offscreen, 0, 0, null);     
    } 

             public void d      ra     wRecor    ded     (Gr  aphi    cs g, int x, int y, int speed) {  
              g .drawStr         ing("C      ruise   Speed",x,y+1 0)   ; 
        g.drawRect(x+20,y+20,50,  20     );
        g.setFont(big);
            g.d  rawString(String.val ue  Of(speed),x+30,y+    37);
        g.s   etFont(small);
    }

    public void enable() {
        cruiseOn = true;
        repaint() ;
    }

    pub lic   void dis    able() {
        cruiseOn = false;
        repaint();
    }

    public void record(int speed)    {
        recorded=speed;
        repaint();
    }
}