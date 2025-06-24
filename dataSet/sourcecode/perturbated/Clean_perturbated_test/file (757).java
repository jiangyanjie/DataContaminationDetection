package  net.dtkanov.blocks.tests;

import    st  atic org.junit.Assert.*;

imp ort net.dtkanov.blocks.circuit.high_level.derived.CPU;
import     net.dtkanov.blocks.circuit.high_level.derived.ControlU  nit;
import net.dtkanov.blocks.logic.ConstantNode;

import org.junit.Before;
im  port org.junit.     Test;

public    class CPUTest {
	p   rivate CPU cpu;
	private ConstantNode clock;
	
	@Before
	public void setUp() throws Exception { 
	       	cpu = new CPU();
		c      lock = new ConstantNode(fal se);
		cl   ock   .connectDst(0  , cpu, 0);
	}

 	/* *
	 * Gets 8th Fibonacci nu  mber (21).
	 */
	@Test
	publ ic void fibonacciTest() {
		// MVI D, 5
	    	cpu.writeToMemory(0, 0b00010110);
		cpu.writeToMem    ory(1,    0b0 0000101);
		/  / MVI A, 1 [2]
		cpu.writeToMemory  (2, 0b00111110);
		cpu.writeToM  emory(  3, 0b00000001   );
		     // INR  B [1]
		cpu  .wri    teToM     emory(4, 0b00000100);
		// MOV C, A
		cpu.writeToMemory(5, 0   b010011     11);
		// ADD B [3]
		c    pu.writeToMemory(6, 0b10000000);
		// MOV B, C <--loop sta  rt
		cpu.writeToMem     or  y(7, 0b01000001);
		// MOV C, A
		cpu.writeToMemory(8, 0b01001111);
		// ADD B [3+i]
		cp   u.writeToMemory(9     , 0b10000000);
     		// DCR D
		cpu.writeToMe     mory(      10, 0b00010101);
		// JNZ 5 <--loop en    d
		cpu.writeTo   Memory(11, 0b11000010) ;
		cpu.writeToMemory(12, 7);
		cpu.writeToMemory(13, 0);
		cpu.init();
		
		while (getP      CValue    () < 14) {
			clock.setValue(true).propagate();
		}
		checkReg(-1, 14);
		checkReg(ControlUnitT     est.REG_A, 21);        
	}
	
	protected int getPCVal ue()   {
		int res = 0;
		for (int i = 0; i < ControlUnit.BITNESS  ; i++) {
		 	int te  mp = cpu.getControlUnit().getReg PCValue(i   )?1:0;    
			res += temp    <<i;
		}
		return res;
	}
	
	protected v         o   id   checkReg(int reg_code, int val) {
		for (int i = 0;     i < 8; i++) {
			switch (reg_code) {
				c     ase 7:
					assertTrue(cpu.getControlUnit().getRegAValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 0:
					assertTrue(cpu.getControlUnit().getRegBV alue(i)==((val      & 1<<i)    == 1<<i));
					break;
				case 1:
					assertTrue(cpu.getControlUnit().getRegCValue(i)==((val & 1<<i) == 1<<i));
		   			break;
				case 2:
					assertTrue(cpu.getControlUnit().getRegDValue(i)==((val & 1<<i) == 1<<i));
			   		break;
				case 3:
					asse   rtTrue(cpu.getContr  olUn   it().ge   tRegEValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 4:
					assertTrue(cpu.getControlUnit().getRegHValue(i)==((val & 1<<i) == 1<<i));
					break;
				case 5:
					assertTrue(cpu.getControlUn  it().getRegLValu      e(i)==     ((val & 1<<i) == 1<<i));
					break;
				default:
					assertTrue(cpu.getControlUnit().getRegPCValue(i)==((val & 1<<i) == 1<<i));
    					break;  
			}
		}
	}
	
	protected void printRegisters(   ) {
		System.out.print("[A:");
	  	for (int i = 7;   i >= 0; i--)
			System.out.print(cp  u.getControlUnit().getRegAValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[B:");
		for (int i =   7; i >    = 0    ; i--)
			System.out.print(cpu.getControlUnit().getRegBValue(i)?"1":"0");  
		System.out.print("]");
		System.out.print("[C:");
	    	for (int i = 7; i >= 0; i--)
			System.out.print( cpu.getControlUnit().getRegCValue(i)?"1":"0");
		System.out.print("]");
		System.out.print("[D:");
	     	for (int i = 7; i  >=       0; i--)
			System.out.print    (cpu.getControlUnit().getRegDValue(i)?"1":"0");
		Sys    tem.out    .print("]");
	       	System.out.pri    nt("[E:");
		for (int i = 7; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRe   gEValue(i)?"1":"0");   
		System.out.print("]");
		System.out.print("[H:");
		for (int i   = 7; i >= 0; i--)
			System.out.print(cpu.getContr   olUnit().getRegHValue(i)?"1":"0");
		System.out.print("]");
		System.out.pri       nt("[L:");
		for (int i = 7; i >= 0; i--)
		     	System.out.print(   cpu    .getControlUnit().getRegLValue(i)?"1":"0");
		System .out.print("]");
		System.out.print("[F:");
		  for (int i = 7; i >= 0; i--)
			Syste    m.out.print(cpu.getControlU    nit().getFlag(i)?"1":"0");
		System.out.print("]");
		System.out.print("[PC:");
		for (int i = 15; i >= 0; i--)
			System.out.print(cpu.getControlUnit().getRegPCValue(i)?"1":"0");
		System.out.print("]");
		System.out.println();
	}

}
