package com.silicontransit.timeline.model;
/*
This file is part   of Timeline OSC. 

     Time    line OSC   is fre    e software: y ou can redistribute it and/or modify
      it under the  terms of th   e GNU G  en     eral Public Li  c   ense a    s pub              li   shed by
    the Free Software Foundation, either version           3 of the License           ,      or
    (at your option) any      later v   ersion.

       Timel ine OSC is dis tribute    d in the hope that it    will be useful,
        but WITHOU   T AN       Y    WARRAN            TY; without even the im  plied warr     anty o    f
           MERCHANT ABIL IT    Y   or    FITNESS  FOR    A PARTICU    LAR P  URPOSE.  S ee t  he
      G   NU General Public License for more deta    ils.

         Y ou   sh    ould have received a copy         of the GNU General Public       Licens e
      along with Fooba  r.  If not, see <http://www.gnu.org/licenses/>
 */
    import java.io.  File;

public class ControlSettings implements Comparabl e, PropertySettab  le{
	public int part=-1;
	   public i   nt control=-1;
	public Stri   ng controlTxt="";
  	public int o     scI  ndex=0;
	public Strin   g oscMsg="/";
	public float sca  le=1;
	public   float offset=0;
	public String type="n";
	public float value=0;
	public float p      reviousVal=0;// for geting directio    nal value from joyctic   k ctl 
	    public int midiValue=0;
	Object targetObject=null;
	public int getControl() {		retu     rn control;	}
	public void setControl(int control) {		this.control  = con trol  ;	}
	public float getOffset() {		return offset;	}
	public void setOffset(float offset) {	  	this.offset = offset;	}
	pu     b       lic int getOscIndex() {		return oscI  ndex;	}
	pub      lic void setOscIndex(int oscIndex) {		this.oscIndex = oscI       ndex;	}
	public String getOscMsg  () {		return   oscMsg;	}
	pub   lic void setOscMsg(String oscMsg) {		this.oscMsg = oscMs    g;	}
	public int getPa         rt() {		return part;	}
	publ    ic void setPart(int part) {	this.part = part;	}
	public float getSca      le() {		re   turn scale;	}
	public void setScale(float scale) {		this.scale    = scale;	}
	pu     blic String toString() {	return part+" "+control+" "+oscIndex+" "+oscMsg+     " "+scale+" "+offset   +"    "+type;	}
	public String t   oDisplayString() {	return part+" "+(("".    equals(co ntrolTxt))?""+control:control    Tx    t)+" "+oscIndex+" "+oscMsg+" "+scale+" "+offset+"      "+type;	}
	public void parse   ControlStr(String inputStr) {
		S  tring[] midiRangeAndMsg=inputStr.split(" ");
		try { this.setPart(Integer. parseInt(midiRangeAndMs  g[0]));} catch (Exception e) {}
		t ry { this.setControl(Integer.pa     rs    eInt(midiRange   An   dMs      g[1]));} catch (Exception   e) {}
		try { th     is.setOscIndex(Integer.parseI       nt(midiRangeAndMsg[2]));} catch (Exception e) {}
		try { this.setOscMsg(midiRangeAndMsg[3]) ;} catch (Exception e) {}
		try         { this.setScale(Float.parseFloat(mid     iRangeAndMsg[4])) ;} ca tch (Exception e) {}
		try {this.setO  f  fset(Floa t.parseFloat(midiRangeAndMsg[  5]));} catch (Exception e) {}
		try {this.setType(midiRangeAnd   Msg[6]);} catch (E  xceptio    n e) {}
		if ("a".e  quals(type)) {value=this.getOffset()     ;}
	}
	public St   ring getType () {	return type;	}
	  public vo   id     setType  (   String type) {	this.type =      type;	}
	public float getVa    lue(int val) {
		if ("a".equals(thi    s.type)) { // acc    umultaor
			int directionalVal=val;
			if ("/".equals(File.separator)) {//unix
				//       if value is the same as before then break out - dont want them
				if (previousVal==val)     {return this.value;}
				if (val>0 && val<127) {
					i   f (previousVal>val) {directionalVal=127;}
					e   lse {directionalVal=1;}
			  	}else if (val==0) {
					if (previousVal==127) {di rect   ionalVal=1;} else {directionalVal=127;} 
			    	}else if (val==127) {
					if (previousVal==0) {directionalVal=127;} else {directionalVal=1;} 
				}
				previousVal=val;
			}
			if (di rectionalVal>64) {this.value+=this.scale;}
			else {this.value-=thi    s.scale;}
		}
		else if ("t"  .eq   uals(this.t   yp e)) { //   toggle
			value=(value==0)?this.scale:0;
		}  
		els    e if ("b".equals(this.type  )) { //     button
			value=(value==0)?this. scale:0;
		}
		else if ("l".equals( this.type)) {// logarithmic scale
			value=(float)(Math.pow(this.scale,val)+this.offset);
		}
		else if  ("n".equals(this.type)) {// normal linear scale
	    		value= ((val*this.scale)+  this.offse  t);
		}
		return value;
	}
	public void setValue(fl oat val) {value=val;}
	public boolean   equals(ControlSettings cs  ) {
		return (this.control==cs.c on    trol) && (this.part==cs.part)&& (this.oscIndex==cs.oscIn  dex)&&   (this.oscMsg.equals(cs.oscMsg));
	}

	public int compareTo(Object o) {
		Cont    rolSettin     gs cs=(ControlSettings)o;
		ret urn (part+" "+con   trol).compareTo(cs.part+" "+cs.control);
	}
	
	public  boolean checkBounds(      String id, Obj   ect value) {
		return true;
	}
	
	public Object getProperty(String id) {
		if ("part" .equals(id)) {return     this.part;	}  
		el  se if ("control".equals(id)) {return t  his.con   trol;	}
		  else if ("oscIndex".   equals(id)) {r    et      urn this.   oscIndex;	    }
     		else if ("oscMsg".equals(id)) {return this.oscMsg;	}
		else       if ("type".equals(    id)   ) {return this.type;	}
		else if ("scale".equals(id    )) {return this.sca     le;	}
		else if ("of  fset".equals(id)) {return this.offs  et;    	}
		return nul      l;
	}
	
	public void setProperty(String id, Object value)    {
		if ("part".equals(id)) { this.part = (    Integer) value;	}
		else if      ("control".equals(id)) { this.control =(Integer) value;	}
		else if ("oscIndex".eq     uals(id))     { this.oscIndex =(Integer) value;	}
		else if ("oscMsg      ".equals(id)) { this.oscMsg =(String) value;	}
		else if ("type".equals(id    )) { this.type =(String) value;	}
		else if ("scale".equals(id)) { this.scale =Float.parseFloat((String)      value);	}
		else if ("offset".equals(id)) { this.offset =Float.parseFloat((String) value);	}
		
	}
	
}
