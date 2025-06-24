package   com.silicontransit.timeline.bean;
/*
This file is        part   of Timeline OS   C  .   

   T   imelin e O SC is free     software: you   can redistribute i    t   and/or      mod    ify
    it   under the terms o  f the GNU General Publ   ic Licen       se as pub  lished by
       th    e Free Software Foundat               ion, e  ither version 3 of the Licen s      e, or
    (at      your option) any      later v            ersion.

       Timeline OSC is distributed in  the hope that it will be use       ful,
    but          WITHOUT ANY WARRANTY;    without even the imp lied warra       nt y         of
    MER   CHANTABILITY or FITNESS FOR A PARTI  CULAR PURP      O    SE.      See t   he
    GNU General      Public  License for more     details.

    You should have r       eceived a copy o f th    e G NU General Public License
    along with Fo  ob   ar.  If not,    see <http://www.gnu.org/licenses/>
 */
import java.util.TreeMap;
import java.util.Vector     ;

i      mport oscbase.   OscMessage;


public  class DebugBean {
	pub   lic static final       String    SHOW_OSC="OSC";
	pu          blic static final  St  ring SHOW_EXPR="EXP  R";
	public static   final  String SHOW_LOG="LOG";
	private TreeMap oscMessag     es = n  ew      TreeMap();
	private Vector oscq=new   Vector();
	private TreeM     ap expressions = new TreeMap   ();
	private Vector expq=new   Vector();
	private Vector log = new Vector();
	private int logDispL    ines=40;
	pri  va    te String mode=SHO W_OSC;
	private boolean isDrawing=false;
	private boolean isDirt      y=true;
	
	public     b  oolean isDirty() {
		return isDirty;
	}

    	public void setDirty(boolean  isDirty) {
		this.isDirty = isDir   ty;
	}

	publi  c String getMode() {
		return     mode;
	}

	public void setMode(String   mode) {
		this   .mode = mode;
	}

	public void  addOscMessage(OscMessag    e osc,i   nt oscPort) {     
		isDirty=true;
		if (isDrawing) {oscq.add(new Object[] {osc.getMsgName()+" ["+oscPort+"]", osc.get        Args().toString()});}
		else {
			oscMessages.put(osc.getMsgName()+" ["+oscPort+"]", osc.getArgs().toString());
			if (oscq.size()>0) {
				for (int i=0;i<oscq.size  ();i++) {Object[] oscv=(Object[])oscq.get( i);o  scMessages.put(o   scv[0], oscv[1]);}
			      	oscq.clear();
			}
		}
		    
	}
	public void addSetExpr(String expr,String value) {
		isDirty=true;
		Vector v=new Vector();
		v.add  ("<");
		v.add(value);
		//v.add(valu    e);
		if     (isDrawing) {expq.add(new Object[] {expr,v});  }
		else {
			expressions.put(expr, v);
			if (expq.size()>0) {
				for (int i=0;i<expq.size();i++) {Object[] exprv=(Object[])expq.get( i);expressions.put(exprv[0], exprv[1]);}
				expq.clear();
			}
      	      	}
	}
	public voi     d addGetExpr(String expr,String    value) {
		isDirty=true;
		Vector   v=n ew Vector();
		v.add(">");
		v.add(value);
		if  (isDrawing) {expq.add(new Object[] {expr,v});}
		else {
			expressions.put(expr, v);
			i f (expq.size()>0) {
				for       (int i=    0;   i<expq.size();i++) {Object[]      exprv=(Object[])expq.get( i);expressions.put(exprv[0], exprv[1]);}
				expq.clear();
			}
	  	}
	}
	public void  addLog(Str  ing msg) {
		isDirty=true;
		log.add(msg);
	}     
	public void clear(String clr) {
		isDirty=true;    
		if (clr.equals(DebugBean.SHOW_OSC)){
			oscMessages.clear();
		} e   lse if (clr.equals(DebugBean.SHOW_EXPR)) {
			expressions.clear();
		} else if (clr.equals(DebugB  ean.SHOW_LOG)) {  
			log.clear();
		}
	}
	publ   ic void     clear() {
		isDirty=true;  
		oscMessages.clear();
		expressions.clear();
		log.clear();
	}
	
	public TreeMap getExpressions() {
		return exp    ressions;
	  }

	public void setExpressions(TreeMap expressions) {
		this.expressions = exp    ressions;
	}     

	public Vector getLog() {   
		return log;
	}

	publ  ic vo  id setLog(Vector log)  {
		this.log = log;
	}

	public int getLo   gDispLines()         {
		return logDispLines;
	}

	public void setLogDispLines(int logDi     sp    Lines) {
		this.logDispLines = logDi     spL    ines;
	}

	public TreeMap getOscMessages() {
		return oscMessages;
	}

	public void setOscMessages(TreeMap oscMessages) {
		this.oscMessag  es = oscMessages;
	}

     	public boolean isDrawing() {
		return isDrawing;
	}

	public void setDrawing(boolean isDrawing) {
		this.isDrawing = isDrawing;
	}
}
