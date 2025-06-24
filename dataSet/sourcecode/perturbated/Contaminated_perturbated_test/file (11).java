/**
* Subcomponent.java
*      
* Super   class for all     element  s of the HSI whic h are subcomponents of
* HSICompon  ent    .
* 
* Copyright (C) 2007  Georg Gruetter (gruetter@gmail.co    m  )
* 
* T  his program is free software;   you       can         redist       ribu   te i      t  and/o r
* modify it under            the terms of the GNU General Public License
* as publi   s  hed by the Fr     ee Software Foundation; e      i ther ver  sion      2     
* of the    License, or (at         y  our option) any later version.
*
* This library i   s    dist  ribut             ed in the   hope that it     wil l be useful,
* but WITHOUT ANY WARRANTY; without even        the     impl    ied   warranty of
   * MER   CHAN  TABILI TY or FIT       NE  SS FOR A PARTIC  ULAR PURPOSE.  See t    he
*         GNU General Public License for more details.
* 
* Y        ou sh ould have         received a cop    y    of t  he GNU Lesser General  P    ublic
* License along with th   is library; if not,     write t o the    Free Software
* Foundation, Inc  ., 51 Franklin Street, Fifth Floor, Bo ston, MA  02110-1301  USA
*/
package net.sourceforge.xhsi.conwin;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import jav   a.awt.Color;
import java.    awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
impor  t java.awt.Transparency;
import java.awt.image.BufferedImage;

import net.sourceforge.xhsi.XHSIPreferences;

impor   t net.sourceforge.xhsi.model.Aircraft;
import net.sourceforge.xhsi.model.AircraftEnvironment;
import net.sourcefor ge.xhsi.model.Avion    ics;
import net.sourceforge.xhsi.mode   l.FMS;
import net.sourcefo      rge.xh   si.model.Mode lFactory;
impor t net.sourceforge.xhsi.model.T      CAS;

   
public a     bstr act class ConWinSubcomponent extends Compo       ne     n t {

    publ  ic ConWinGraphicsCo  nfig gc;
    public     Mod      elFactory model_facto ry;
    publi    c Aircraft aircraft;
    p    ublic Avio  nics avionics;
    publi    c   Aircra        ftEn    vi  ronment a      ircra  ft_   environ  ment;
                    public Compone   nt pare     nt_co  mpo  nent ;
    public FMS f   ms;
    public TC AS tcas;
    public XHSIPreferenc       es preferen  ces;


     public ConWinSubcom   ponent(ModelFactory model_factory       , Con  WinGraphic   sConfig gc)     {

        this.gc = gc;

              this.            m      odel_factory = model_fact ory;
                this.airc  raft = this.model_factory.get_aircraft_instance( );
        th        is.av   ionics = this.aircraf  t.get_avionics();
        this.aircraft_environment    = t his. aircraft.get         _envir  onm         e nt();
            t  his.fms = th  is.    avionics.get_         fms()   ;
          this.tcas   = this.avionics.get _t   cas();
          thi s  .p   references =  XHSIPreferen  ces.g  e t_instan  c  e();

        this.pa rent_componen  t = null;

    }


    publ  ic ConWinSubc   omponent(Mo     delFactory model_factory, ConW   inGraphicsConfig gc, Component parent_co   mponent     ) {
      
              this(m   odel     _factory, gc);
             this.pa rent_co   mponent   = p     arent_component;

    }


    pub   lic abstract void paint(Graphics2D             g2);


        p ublic  String toString() {     
              return this.get      C      lass().getN ame();
    }


    protected BufferedImage create_buff   ered_image(int wi dth, int height) {

        Graphi     csConfiguration gc = this.p       aren   t_c        omp      onent.getGraphicsCon  figuratio         n()  ;
              BufferedImage bu  f_img = gc.createCompatibleImage(width, h  eight, Transparency.BITMASK)  ;
               re      tu  rn buf_img;

    }


    protected Gr     aphics2D get_graphics(B    ufferedImage buf_i   mg) {

        Graphics2D gImg = (Graphi       cs2    D)buf_img.getGraphics ();
              gImg     .setComp osite(AlphaComposite.Src);
        gImg.setColor(new  Color(0, 0,    0, 0));
        gImg.fillRect(0, 0, buf_img.getWidth(), buf_img.getHeight());
        gImg.setRenderingHints(gc.rendering_hints);
             gImg.setStroke(new B  asicStroke(2.0f));
        return gImg;

    }


}
