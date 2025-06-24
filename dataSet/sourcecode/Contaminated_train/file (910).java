package DatapathComponents;

import java.util.HashMap;
import java.util.Iterator;

public class ControlFile {
	private static HashMap<String, Control> file;
	
	public ControlFile(){
		file = new HashMap<String, Control>();
		file.put("RegDest", new Control("RegDes", 0));
		file.put("Jump", new Control("Jump", 0));
		file.put("Branch", new Control("Branch", 0));
		file.put("MemRead", new Control("MemRead", 0));
		file.put("MemToReg", new Control("MemToReg", 0));
		//file.put("ALUop", new Control("ALUop", 0));
		file.put("MemWrite", new Control("MemWrite", 0));
		file.put("ALUsrc", new Control("ALUsrc", 0));
		file.put("RegWrite", new Control("RegWrite", 0));
	}
	
	public static void reset() {
		Iterator<Control> itr = file.values().iterator();
		while (itr.hasNext()) {
			itr.next().value = 0;
		}
	}
	
	public static Control getControl(String name) {
		return file.get(name);
	}

	public static int getValue(String name) {
		return file.get(name).value;
	}
	
	public static Control setControl(String name, Control c) {
		return file.put(name, c);
	}
	
	public static Control setControl(String name, int val) {
		Control c = file.get(name);
		c.setValue(val);
		return file.put(name, c);
	}
	
	public void print() {
		Iterator<Control> values = file.values().iterator();
		Iterator<String> keys = file.keySet().iterator();
		while (values.hasNext()) {
			System.out.print("$"+keys.next()+": "+values.next().getValue()+"    ");
			if(values.hasNext())System.out.print("$"+keys.next()+": "+values.next().getValue()+"    ");
			if(values.hasNext())System.out.print("$"+keys.next()+": "+values.next().getValue()+"    ");
			if(values.hasNext())System.out.print("$"+keys.next()+": "+values.next().getValue()+"    ");
			if(values.hasNext())System.out.print("$"+keys.next()+": "+values.next().getValue()+"    ");
			System.out.println();
		}
	}

}
