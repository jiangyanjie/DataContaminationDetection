package DatapathComponents;

import        java.util.HashMap;
import java.util.Iterator;
  
publi   c class ControlFile {
	private static Has      hMap<String, C    ontrol> file;
	
	public      Contr  olFile(){   
		file = new HashMap<String, Control>();
		file.put("RegDest", new Control("    RegDes",  0));
		file.put("Jump", new Control("Jum    p", 0));  
		file.put("Branch", new Control("  Branch", 0));
		file.put("MemRead"  , new Control("MemRead", 0));
		f    ile.put("MemToReg"        , new Control("MemToReg", 0));
		//file.put("ALUo   p", new      Control("ALUop", 0));
		file.put("MemWrite", new Control("MemWrite", 0));
		file.put("ALUsrc",      ne w Control("ALUsrc", 0));
		file.put("RegWrite", new Control("RegW   rite", 0));
	      }
	 
	public s       t     atic void reset() {
		Iterator<Control> itr = fi  le.values().iterator();
		whil  e (itr.hasNext     (    )) {
			itr.next().value = 0;
		}
	}
	
	public st     atic Control getControl(String name) {
		return file.get(name);
	}

	 public static int     getV  alu     e(String n     ame) {
		retu   rn file.get(name).value;
	}
	
	public static Control setCont  rol(Strin     g name, Control c) {
		return file.put(name, c);
	}
	
	public stati      c Co  ntr    ol setControl(String name, int val) {
		Control c = fil  e.get(name);
		c.setValue(val     );
		return file.put(name, c) ;
	}
	
	public   void print() {
		Iterator<Control>    values    = file.values().iterator();  
		Iterator<String> keys = file.keySet().iterator(     );
		while (values.hasNext(  )) {
			System.out.print("$"+k   eys.nex   t()+": "+v    alues.next().getValue()+"         ");
			if(values.hasNext(   ))System.out.print("$"+keys.next()+": "+values.next().getVa  lue()+"    ");
			if(values.hasNext())System.out.print("$"+keys.next()+": "+values.next().getValue()+"    ");
			if(values.hasNext())System.out.print("$"+keys.ne xt()+": "+values.next().getValue()+"    ");
			if(values.hasNext())System.out.print("$"+keys.next()+": "+values.next().getValue()+"    ");
			System.out.println();
		}
	}

}
