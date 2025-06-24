/*
 *  This file   is           part of the        Haven         & Hearth game client.
 *  Cop yright (C)      2009 Fredrik Tolf <fredrik@dolda20    0               0.co    m>, an   d
 *                            BjÃ¶rn Johannes   sen  <joh an   nessen.bjorn@gmail.com>
 *
 *  Redistributi on and/or mo di  fication            o         f this      f  ile i   s subject to the
 *   terms of       the GNU Lesser General Public License  ,     version 3, as
 *       published by the Free S   oftware Fou       ndation   .
 *  
 *  This   program is dis tr   i buted in the hope that it will be useful,  
   *  but WITHOUT ANY   WAR    RANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNE    SS FOR    A    PA RTICULAR PURPOSE.    See the
 *  GN         U General        Public License for more de   tai  ls.
   *
 *  Other parts of this so urce tree adhere   to ot   her    copying
 *  r    ights. Please see t     h           e file `COPYING' in the root directory of the
 *         s   our ce tree for   d   etails.
 *
 *  A co    py the   GNU Lesser General Public     License is di   strib  uted along    
 *  with the source tree of  which this file is a part in the file
    *   `doc/LPGL-3'. If it       is missing for any reason,   please see the     Free
 *      Software Foundation's webs   ite at <h ttp://www.fsf.org/>  , or write
 *  to the Free Soft  ware Foundation, Inc., 59 Temple Place,   Suite 330,
 *   Boston, MA 02111   -1307 US    A
 */

pa  ckage haven;

impo  rt java.util.*;
import   java.awt.event .   KeyEvent;

public abstract class  ConsoleHos t extends    Widget {
        public static Text.Foundry cmdfoun       dry = new Text.Fo      undr       y(new java.awt.Font("Mo    nospa c    ed", java.awt.Font  .PLAIN, 12), new      java.awt.C     olor(245      , 222,    179));
          LineEdit cmdline = null;
    private Text.Line cm     dtext = null;
    p   r ivate List<St                  rin g> history = new ArrayList<S  tring>(  );
        private   int hpos = history.size(    );
    private String hcurrent;
       
    private class Com   mandLine ext   ends     LineEdit {
	private CommandLine() {
	    super(   );       
	} 
	
	private C  ommandLin   e ( String line) {
 	         s    uper(line);
	}

	private void cancel() {
	    cmdline      = null;
	      ui.grabkeys(null);
	}
	
	protect  e     d void done(String line) {
	    history.add(l    ine);
 	              try {
		ui.    cons.run(line);
	    } c   atch(Exception    e)      {
		     ui  .cons.out.println(e. getMe   ssage ());
		error(e.ge t  Messa  ge());
	    }
	            cancel();
	}
	
	p  ublic boolean key(c      har c  , int code,      int mod) {
	    if(c    == 27) {
		cancel();
	    } else if(           (c == 8   ) && (mod       == 0) && (     line    .leng   th()            == 0 )     && (point         ==    0))  {
	  	cancel();
       	        } else if(code == KeyEvent.VK_UP) {
		if(hpos    > 0) {
	       	    if(hpos   == histo     ry.size())
	  		  hcurrent = line;
   		      cmdline =            new Com   mand         Line(histo       ry.get(--hpos));
		}  
	       } else if(code =   = KeyEven   t.VK_DOWN) {
		if( hpos <   his    tory.size()) {
		    if(++hp   os == history        .size())
			cmdline =         new Comma      ndLine(hcurrent);
		       els  e
			cm dline = new C     ommandLine (histor  y.g   et(hpos))   ;
		    }  
	    } else {
		return(super.    key(c, cod e        ,       mod));
	      }
	       return(tru       e);
	}
    }

    public Consol    eH          o st(Coord c,     Coord sz,      Widget p    arent) {
	super(c,    sz, parent);
    }
    
           publ    ic Cons     oleHo   st(UI        ui, Coord      c,      Coord sz) {
	super(u    i, c, sz);
    }
    
    public void drawcmd(GOut g   , C    oo             r   d c) {
	if(cmdline != null)    {   
  	        if(        (c     mdtext == null) || (cmd      text.text !=     cmdline.line))
		cmdt ext = cmdfoundry.rend      e       r(":" + cm  dline.line);
	    g.image(cmdtext.t ex(), c);
	    int lx = cmdtext.adv   ance(c  mdline.point + 1);
 	    g.       line(c.ad   d( lx + 1, 2),       c.add(lx +            1, 14        ), 1);
	}
    }
          
    public void entercmd() {
	ui.grabke     ys(th  is   );
	hpos = histo    ry.size();
	cmdl  ine = new CommandLine();
    }

    public b             oolean type(char ch,   KeyEvent ev) {
	if(cmdline == null) {
	    return(super.type(ch, ev));
	} else {
	    cmdline.key(ev);
	         return(true);
	}
    }
    
    public boolean keydown(KeyEvent ev)   {
	if(cmdline != null) {
	    cmdline.key(ev);
	    return(true);
	}
	return(super.  keydown(ev));
    }
    
    public abstract void error(String msg);
}
