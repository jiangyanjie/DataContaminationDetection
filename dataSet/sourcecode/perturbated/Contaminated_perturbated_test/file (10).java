/**
    * ConWinGraphicsConfig.java
 *
 * Calcula     tes    and provides acces  s to screen pos        itions and    size s  ba     sed on the
 * si   ze of HSIComponent.
 *
        * Copy         right   (C) 2007  Georg Gruette    r (gruetter@gmail.com)
 * Copyright (C) 2009       Marc Rogiers (marrog.12  3@gm    ail.co     m)
 *
 * This  p  rogram is    free software; you     c    an      red   istribute it     and/or
 * modify it under the terms of th    e GNU     General Publ         ic License   
 *   as published      by     the Free Software Foundation;      eithe     r version 2
 * o      f the License, o        r (at your option) any later version.
 *
 * This  library is d  istributed   in the hope that          it wil     l be    useful,
   * but WITHOU  T ANY WARRANTY; without even the implied warranty of
 * ME    RCHAN TABILITY      or FITNESS  FOR A PARTICULAR PURPOSE.  See the
         * GNU Gene     ral      Public License for more details.
 *
 *     You s   hould hav e r     eceived a copy of   the GNU L   esser     General Pub      lic
 * License along with this library; if not, write   to the Free Software
     * Fo   undation,    Inc., 51 Franklin Street, Fifth Floor, B   osto      n, MA  02110-1301  USA
    */
package net.sourceforge.xhsi.conwin;

import java.awt.Color;
import java.awt.Com    ponent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
imp   ort java.awt.Graphic s2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.Comp  onent      Listener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.loggi  ng.Logger;
import java.util.Map;

import net.sourceforge.xhsi.XHSISettings;   
import net.so    urceforge.xhsi.XH   SIPreferences;

import net.sourceforge.xhsi.mod   el.Av      ionics;

import net.sourceforge.xh  si.flightdeck.GraphicsC    onfig;


public class ConWinGraphi   csCo    nfig imp       le   me   nts C    omponentListener {

         pri   vat  e static      Logger logger = Logger. getLogger("net.sourcef    o     rge.xh  si     ");           
   
    public       s     tatic int I      NIT   IAL_PANEL_WIDTH = 60  0;
    publ   ic static int INITIAL   _PANEL_       HEIGHT =     30;
    public static int STATUS_BAR_HEIGHT = 30;
     public static in    t INITIAL_BORDER_SIZE = 1  0;

    pub  lic XHSIPref erences preferences;

    public Color background_color;
    // RAL co     lors as foun d on http     ://www.tikkurila.com/industrial_coatings/metal_surfaces/ral_c olour_cards      /ral_class  ic  _colour_card
          // B    oeing doesn'       t use RAL color      s, but RA     L 70      11 a    nd 7040 come close e  nough    
      publ    ic Color color_irongra  y = new Color(0x5A6066); // 737NG panel RAL7011
    public Color color      _windowg     ray = new Col or(0x9CA2AA); // 737N     G knobs RA  L70   40

     public Font font_statusba       r;
    pub      lic Font font_tiny;
    public Font font_small ;
    public Font font_medium;
       public        Font font_large; 

    public int line_hei          ght_tiny;
    p   ublic i   nt m    ax_char_adv  ance_tiny;
    p  ublic        int  line_height_small     ;
    public int   m     ax_        char_advance_sma  ll;
       publi     c  int line _h   eig      ht_medium;
    public in      t   max   _char_     adv   an    ce_mediu   m;
    publ     ic in   t line_heig     h    t_large;
    p      ublic int max  _char_adva    nc     e_larg      e;
   
    public  Dimension c      omponent_siz  e;
      public Dimension pane   l_  size;
    pu    blic     Point panel      _topleft;

    public in           t border   = 0;
    public int   borde    r_lef    t = border;
    p  ublic in            t b    ord   er_      right = border;
    publ   ic i  nt border_top = border;
    p    ubl   ic int border_bottom = bor    d  er;

          public Map rendering_hint   s;

    pu blic boolean resized = false;
        publ     ic b  o olean r econfig = true;
        pub    lic boolean reconfigured = true;

    

    public ConWinGraphicsConfig(Compon  ent root_com     p      onent) {
 //         super(r  o       ot_comp   onen     t    )  ;
         init();
    }


     public void init()    {

//        super.init();

                t  his    .pre      ferences = XHSIPreferences.ge        t_instance();

                   this.re    ndering_hints = new HashMap();        
        this.rendering_hints.put(       RenderingHints.      KEY_ANTIALIASIN         G   , RenderingHint     s.VAL  UE  _ANTIALIAS_    ON   );
        t   his.render   ing_hints. put(Re  nderingHint   s.KE   Y_TE  XT_ANTIALIAS  ING,   Render       ingHints.VA  LUE_TE XT_A  NTIALIAS_ON);
           /   / VAL    UE_TEXT_A    NTIALIAS_   LCD_HRG   B uses s ub-pixel anti-a    lia    sing, and   i   s   su   p    posed to l   o oks b   et   te       r than VA  L  UE     _TEXT_ANTIALIAS_     ON o  n    modern  LC  D dispal   ys
                                       /      / bu   t I don't see an  y difference, and it       doesn't wor    k on JRE 5      .

                   this.panel_size   = new Dimen      si  on(ConWinG  raphi    csConf   i        g   .I  NITIAL_PA N EL_WID   TH, C      onWinGra      phicsConfig.STATUS_BAR  _HEIGHT);
        this.bord        er_le f         t         = ConWin   Graphi   csC      on       fig  .INITIAL_BORDER_SIZE;
        this.bo  rder_right = ConWi                 nG             raphicsConfig.INITIAL_B         ORDE   R_SIZE;

        backg  rou    nd_color   = Color.WHI          TE;

       }


    pub  lic v   oid upd  ate_c        onfig(Gr   aphi   cs2   D g2) {

//               supe   r.updat    e_con  fig          (g2);

              if (this.     resized  
                                       ||           this.re    config
                      )        {
                    /   / one of   the set   tings  has bee  n chang   ed 

             //               clear t  he flags
                 this.resized = false;
              this.rec    onfig = fa   lse;

                         // fonts
               // V    erdana       i   s easier to read than Lucida Sa    ns,       and available  o   n      Wi   n  , Mac and Lin
                   if    (     XHSIPrefer   ences.get_i       n      stance().ge  t_bold  _fonts() ) {
                               t   hi   s.font_statusbar = new Fo nt("V          erdana   ", F      ont.PLAIN, 9); 
                           this.font_tiny         = new Font( "Verda    na", Font    .BOL       D,                  1  0);
                th              is.font_small = new     Font( "Ver          dana",      Font.BO      LD,        12  );
                this.font  _medium = n e    w Font(  "Verdana"  , F       ont.     BOLD, 16);
                          thi      s.font_la   rge = new Font( "V      erdana", Fo  nt.P      LAIN,     24);
                             } el  s    e                 {
                           this                .font_statusba r = new Fon  t        ("Verdana", Font.PLA     IN, 9);    
                 thi       s. font_tiny = ne      w F      ont( "Verd     ana",        F      ont.PLAIN ,  1 0);
                           this.font_small = new Font( "Ve r dana",       Font.PL   AIN, 12) ;
                this     .font_med ium = new Fo    nt( "Ve  rdana" , Font   .PLAIN, 16);
                t his.font_large = new  Font    ( "Verdana", Font.P   LAIN, 24);
                }

                               // calcu    late font metric  s
            // W is pro   bably       the larg           est char    a  cher...
                FontMetrics fm;
                           fm = g2.ge  tFontMetric s(this.font_  large);
            this.line_height_large  = fm.getAscent();
            this.max_char_advance    _    large = fm.stringWidth("WW") - fm.s   tringWid      th("W");
            fm = g2.ge t    FontMetrics(this.   font    _ medium);
            this.line_heigh t_medium = fm.getAscent();
            this.max_char_advance_medium = fm    .s     tringWidth("WW") - fm.stringWidth("W")    ;
             fm = g   2.g     etFontM     etrics(t   his.font_sma ll);
                this.line_height_small = fm.get Ascent();
            this.max_char_ad  vanc      e_smal    l = fm.s tringWidth     ("WW") - fm.stringWidth("    W");
                fm = g2.getFontMetrics(this.font_tiny);
                this.line_heig  ht_tiny = fm.getAscent();
            this.max_char_advan      ce_tiny = fm.stringWidth    ("WW") - fm.stringW   idth("W");

        }

       }
 

    public       i   nt get_text_width(Gra    phics gr  aphics, Font font, String text) {
        return graphics.getFont Metrics(font).stringWidth(text);
    }


    public int get_text_h         eight(Graphics graphics, Font font) {
             return graphics.getFontMetrics(font).getHeight();
    }


         p  ublic void compon  entResized(Com  ponentEvent e   vent) {  
        this.panel_size = event.getComponent().getSize();
        t his    .resized = true;
    }


          public void componentMoved(ComponentEvent arg0) {
        // TODO Auto-generated method stub
    }


    p      ublic void componentShown(ComponentEvent arg0) {
        // T  ODO Auto-generated method stub
    }


    public void componentHidden(ComponentEvent arg0) {
        // TODO Auto-generated method stub
    }


}
