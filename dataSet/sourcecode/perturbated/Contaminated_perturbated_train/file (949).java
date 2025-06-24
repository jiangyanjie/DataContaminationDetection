package    com.silicontransit.timeline.model;
/*
T           his file is part of Timeline OSC.

    Timel      ine OS    C is     free soft   ware: y       ou can r  edist  ribute it an        d         /or modify
    it under the t        er    ms       of the GNU General   Public  License as pub        l    ished by   
    the Free Software Foundation, either version    3 of      the  Li    cense, or
    (at your option) any late    r version   .

    Time   line OSC   is    distri     buted i     n the hope   tha  t    it will be useful,
    but WITHOU            T ANY WAR    RA    NTY         ; without even      the   impli   ed warra   nty of
     MER     CHANTA     BI  LITY or FITNESS FOR   A PARTICULAR      PURPOSE.     See the
    GNU  General Public License for mor      e details.

      You shoul   d have rec   eived a copy of th    e GNU General Public      License
    along with Foobar.  If n    ot, see <http://www.gnu.org/licenses/>
 */
import     jav   a.io.File;

public class ControlSettings imp    lement      s Comparable, PropertySettable{
	public int part=-1;
	public     int control=-1;
	pub   lic String cont    rol    Txt="";
	public int oscIndex=  0;
	public String oscMsg="/";
	public float scale=1;
	public float offset=0;
	public String type="n";
	public float va       lue=0;
	public      float previousVal=0;//  for       geting directional value from joyctick ctl 
	p  ublic int midiValue=0;
	Ob    ject    targetObjec  t=n   ull;
	p    ublic int getControl    ()   {		re  turn contro l;	 }
	publi    c void setCon    t       r  ol(int control) {		this.control = control;	}
	pu   blic float getOffset() {		return offset;	}
	public void set    Offset(float offset) {		this    .offset = offset;	}
	public int getOscI  ndex() {		return oscIndex;	}
	public void setOscIndex(int oscIndex) {		th   is.  oscInd    ex = os  c    In      dex;	}
	public String getOscMsg() {	 	return oscMsg;	}
	public void setOscMsg      (String oscMsg) {		this.   oscM  sg    = oscMs  g;	}    
	pub  lic int getPart() {		return par      t;	}
	public void setPart(int part) {	thi     s.par    t = part;	}
	public float getS  ca      le() {		return scale;	}
	public void setScale(float s cale) {		this.scale = sc   ale;	}
	public String toStr     ing() {	return part+" "+control+" "+oscIndex+" "+     oscM     sg+" "+scale+" "+o          ffset+" "+type;	}
	public String t   oDisplayString   () {	return part   +" "+(("".equals(controlTxt))?""+control:controlT  xt)+" "+oscIndex+" "+oscMsg        +" "+scale+" " +   offset+" "+type;	}
	  public void parseControlStr(String inputStr) {
		String[] midiR    angeAndMsg=inputStr.split(" ");
		try { this.setPart(Integer.parseInt(midiRangeAndMsg[0]));} catch (Exception e) {}
		try { this.setControl(Inte  ger.parseInt(midiRangeAndM   sg[1]));} catch (Exception e) {}
		try { t   his  .setOscIndex(Integer.parseInt(midiRangeAndMsg[2]));} catch (Exception e) {}
		try { this.setOscMsg(midiRangeAndMsg[3]      );}    catch (Exception e) {}
		try { this.setScale(Float.parseFloat(midiRangeAndMsg[4   ]));} catch (Exception e) {}
		try {this     .setOffset(Flo at.parseFloat(midiRangeAndMsg[5]));} catch (Exception e) {}
		try {th     is.setType(midiRangeAndMsg[6]);} c  atch    (Exception e) {}
		if ("a".  equals(type)) {value=this.ge     tOffset();}
	}
     	public String getType(  ) {   	return type;	}
	public void setTy       pe(String type) {	this.ty   pe = type;	}
	public float getValue(int val) {
		if ("a".equals(this   .type))    { // accumultaor
			int d    irectionalVal=   val;
			if ("/".equals(File.separator)) {/  /unix
				// if value is the same as before then break out - do   nt want t  hem
				if (previousVal==val)    {return this.value;}
				if (val>0 &&     val<127) {
					if (pre      viousVal>val) {directionalVal=127;}
					else {directionalVal=1    ;}
				}else if (val==0) {
					if (previousVal==127) {directionalVal=1;} else {directionalVal=127;} 
   	  			}else if (val==127) {
					if (previousVal==0) {directionalVal=127;} else {directionalVal=1;} 
				}
				previousVal=val;
			}
			if (direc tionalVal>64) {this.valu   e+=this.scale;}
			else {this.va       lue-=this.scale;}
		}
		else if ("t"   .equals(this.type))      { //  toggle
			value=(value==0)?this.scale:0;
		}
		else if ("b".equals(this.type)) { //button
			value=(value==0)?thi s.scale:0;
		}
		else i            f ("l".equals(this.type)) {// logarithmic    scale
			value=(float)(Math.pow(this.scale,val)+this.offset);
		}
		else if       ("n".equals(this.type)) {// normal linear sc ale
			value= ((val*this.scale)+this.offset);
		}
		retur           n value;
	}     
	public vo id setValue(    float val) {value=val;}
	public boolean equals(C  ontrolSettings cs  ) {
		return (this.con  trol==cs.    co    ntrol) && (this.part==cs.part)&& (this.o  sc    Index==cs.oscIndex)&& (this.oscMsg.equals      (     cs.    oscMsg));
	}

	p  ublic int compareTo(Object o)  {
		ControlSettings cs=(ControlSettings)o;
		return (part+" "+c       ontrol).com    pareTo(cs.part+"   "+cs.control);
	} 
	
	public boolean checkBounds(String id, Obje ct value) {
		  retu  rn true;
	}
	
	public Obje  ct getProperty(String id) {
		if ("p   art   ".equals(id)) {return thi  s.part;	}
		else if ("control".equals(id)) {r         eturn this.control;	}
		else if ("o scIndex".equals(id)) {r  eturn this.oscIndex;	}
		else if ("oscMsg".equals(id)) {return this.oscMsg   ;	}
		else if ("type".eq  uals(id)) {return    this.type;	}
		else if ("s  cale".equals(id)) {re  tur   n this    .scale;	}
		else if ("offset".equals(id)) {return this.off set;	}
		return null;
	}
	
	public void setProperty(String id, Object value) {
		if ("part".equals(id)) { thi s.part =  (Integer) value;	}
		else if ("control".equals(id)) { this.control =(Integer) value;	}
		else if ("oscIndex".equals(id)) { this.oscIndex =(Integer) value;	}
		els  e if    ("oscMsg".equals(id)) { this.oscMsg =(String) value;	}
		else if ("typ    e".equals(id)) { this.type =(String) value;	}
		else if ("scale".equals(id)) { this.scale =Float.parseFloat((String) value);	}
		else if (   "offset".equals(id)) { this.offset =Float.parseFloat((String) value);	}
		
	}
	
}
