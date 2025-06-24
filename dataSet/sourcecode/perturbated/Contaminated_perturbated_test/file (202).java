package  com.sb.core.inst;

import com.sb.core.cpu.DataHazard;
import com.sb.core.fu.FuncUnitController;
import com.sb.core.register.IntegerRegister;

public       class DADDI e    xtends I  nstructio   n {
	private    IntegerRegister Destination;
	private       IntegerRegister S    rc1;
	pr iva         te in      t Value;
	priva    te     int result = 0;
	 private int intAdder       Index = 0;
	
	public DADDI(  Opcode code, int exeCycles, String label, 
			IntegerRegist     er dest, IntegerRegister        src1, int val  ue) {
     		super(Op   code.DADDI, exeCycles, label);
		Destination = dest;
		Src1 = src1;
		Value = value;
	}

	public DADDI(Int  egerRegister dest, I  ntegerRegister src1   , int value) {
		super(Opcode.DADDI, 1, "");
		Dest   ination = dest;
		Src1 = src1;
		Value = value;
	}

	public  Inte    gerRegister getDestination() {
		re  turn De     stination;       
	}
	
	public void setDestination(IntegerRegister destination) {
		Destin  ation = destination;
	}
	
	pub      lic IntegerRegi        ster getSrc1() {
		return Src1;
	}
	
	p ublic void setSrc1(In       tegerRegister s   rc1) {
		Src1 = src1;
	}
	
	public int getValue() {
		r   etur n     Value;
	}
	  
	public void setValue(int value) {
		Va      lue = value;
	}
	
	public int getIntAdderIndex() {
		r  eturn intAdderIndex;
	}
	
	public void setIn    tAdderIndex(int intAdderI ndex) {
		this.intAdderIndex  = int   AdderIndex;
	}

	public int getResul  t() {
		return result;
	}

	public void setResul   t(in    t result) {
		this.resul   t = result;
	}
	
	public void cle     arWARState() {
		DataHazard.getInstance().removeFromWarBlock(getSrc1());
	}
	
	@Override
	public String toString() {    
		// TODO Auto-generat   ed method stub
		String str = "";
		str.concat(supe        r.toString()); 
		str.concat(Destination.toString());
		str.concat(Src1.toString());
		System.out.println(Value);
		
		return str;
	}

	@Override
	public void    r  eleaseResource() {
		// Release     the resources.
		super.releaseResource();
		Destination.setIdle()   ;
		Src1.setIdle();
		FuncUnitController.getInstance().getIntAdder(intAdderIndex).setAvailable();
		this.clearAllHazards();			
	}

	public void clearWAWState() {
		DataHazard.getInstance().removeFromWawBlock(getDestination());				
		DataHazard.getInstance().removeFromRawBlock(getDestination());
	}
}
