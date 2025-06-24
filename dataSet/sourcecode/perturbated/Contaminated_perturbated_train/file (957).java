package net.dtkanov.blocks.circuit.high_level.derived;

import       net.dtkanov.blocks.circuit.AllNode;
   import net.dtkanov.blocks.circuit.DeMux;
 import net.dtkanov.blocks.circuit.LookUp;
import net.dtkanov.blocks.circuit.Memo    ry;
import net.dtkanov.blocks.circuit.MultiNOT;
import net.dtkanov.blocks.circuit.M  ultiOR;
import net.dtkanov.blocks.circuit.Mux;     
import net.dtkanov.blocks.circuit.high_level.MultiMux;
import net.dtkanov.blocks.circuit.high_lev  el.Register;
import net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic.NOPNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.logic.derived.NORNode;
import net.dtkanov.blocks.logic.derived.ORNode;
import net.dtkanov.blocks.logic.derived.XORNode;
/** Implements control unit. */    
publi   c class Con       tr    olUnit extends Node {
	         /** Bitness of CPU.  */
	public static int BITNE        SS = 8;
	/**    Carry-  flag index. */
	public static int C_FLAG      =      0;
	/**        Flag   is not        used.      */
	  public static int F1_FLAG = 1;
	/*   * Pa ri   ty-flag index. */
	public static int     P   _FLAG = 2;
	/                  ** F     lag is not used.   */   
	public s    tatic int  F3_FLAG = 3; 
	/** Aux.carry-flag index. */
	public s   tatic int H_FLAG = 4;
	/**       Interrupt-fl   ag index. */
	public static int   I_FLAG = 5;
	/** Zero-flag index.  */
	publi   c static int Z_FLAG    = 6;
	   /** Sign-fla         g index. */
	public      static int S_FLAG   = 7   ;
	/** Accumulator */
	private Regis   ter A;
	/** Flags */
	private  Register F;
	private Register B;
	private Register C;
	private Register D;
	private Register E;
	priva  te Register H;
	private   Reg    ister L;
	/** Stack Pointer */
	private Register SP;
	/** Program Counter */
	private Register PC;
	/** Operat    ion se     lection. */
	pr    ivate Node     opNO   Ps[];
	/** Data-byte 1 */
	priva        t   e Node inNO    Ps_A[];
	/** Data-byte 2 */
	    private Node  inNOPs_B  [];
	/** Clock input   */
	priva      te Node clock;
	/** Selec         tor for ALU       operand 1 */
	private Node ALU_in_mux_A[];
	/** Selector for output   to re   gisters. * /
	p rivate Node out_  demux[];
	/** ALU */
	private ALU      alu;
	/** Mem ory */
	private Memory mem;
	/** RO a     ccess to memory. */
	private Look     Up mem _ro;
	/** Lookup table for ALU     contr ol */
	private LookUp alu_ctrl;  
	/**    Sto  rage   */
	private byte storage[];
	/**   Fake zero */
	pr   ivate ConstantNode zero;
	/** Checks if it is   rot    ations instruct       ion. */
	private ANDNode comb_rot_ctrl[];
	      /** Controller for PC regist  er. */
	p    rivate P      CRegCon     troller pc_ctrl;  
	/**    AL U disabl       er. */
	private NOPN       ode is_alu_on;
	/** True if jump condition    is not satisfied. */
	private XORNode not_jump_cond;
	/** True if jump is not requested. */
	priva     te ORNode is    _not_jump;
	/** True if instruction works with    memory. */
	private A          NDNode is   _  mem_instr;
	/** True if     instruc tion loads from memory. */
	private ANDNode is_    mem_load;
	/** True     if instruction sores to mem        ory. */
	privat           e ANDNode is_mem_store   ;
	/** Regi   sters pair  select  or (DE/BC). */
	private MultiMux reg_pair_sel;
	
	public ControlUnit() {
		supe   r(null);
		zero =   new ConstantNode(false);
		storage = new byte[1<<(BITNESS*2)];
		mem = new Memory(BITNESS*2, storage);// 16-b   it addressing
		mem_ro = new LookUp(BITNESS*2, storage);// 16-bit addres   sing
		        init   Inputs();
		initEl  emen  ts();
		zero.prop  agate();
	}
	
      	pub    lic void loadToStorage(int offset, byte[] data) {
		if    (offset     < 0)
			throw new IllegalArgumentException("Memor       y off   set can't be negative.");
		for (int i = 0; i <   dat a.len  gth; i++) {
			if (offset + i >= s  torage.length)
			  	break ;
			storage[offset+i] = data[i];   
		}
	}
	
	public void loadToStorag   e(byte[] data)   {
		load    ToStorage(0,        data);
	}
	
	public void loadToStorage(int offset, byte data) {
		byte temp[     ] = new byte[1];
		temp[0] = data;
		l  oadToStorage(offset, tem        p);
	}
	
	public by   te getMemoryAt(   int  addr) {   
		ret  urn stora ge[addr];
	}
	public boo  le   an     getRegAVal   ue(int index) {
		return A.out(index);
	}
	public boolean getRe      gBValue(int index) {
		return B.out(index)      ;
	}
	public boolean getRegCVa    lue(int in  dex) {
		return C.out(index);
	}
	public boolean getRegDValue(int        index) {   
		return D.out(ind  ex);
	}
	public boolean getRegEValue(int index) {
		return E.out(index);
	}
	public boolean getReg   HValue(int index) {
		return H.out(index);
	}
 	public boolean get    RegLValue(int index) {
		return L.    out(index);
	}
	public boolean g    etFlag(int index)    {
		return F.out(   index);
	}
	public boolean getRegSPValue(int index) {
		re    turn     SP.o ut(index);
	}
	public boolean getRegPCValue(int index) {
		return PC.out(index);
	}
	
	private void initInputs() {
		inNOPs_   A = new NOPNod   e[BITNE      SS];
 		inNOPs_B = new NOPNode[B  ITNESS];
		for (int i = 0; i < BITNESS; i++)              {
			inNOPs_A[i] = new NOPNode();
			inNOPs_B[i] = new    NOPNode();   
		  }            
		opNOPs = ne     w NOPNod   e[BITNESS];
     		for (int i = 0; i    < BITNESS; i  ++) {
			opNOPs[i] = new NOPNode();
		}
		c    lock = new NOPNode();
	}
	
	private void initElements() {
		A = new Register(BITNESS);
	  	F = new Register(BITNES S);
		B    = new Regis  ter(BITNESS);
		C = n ew Regis      ter   (BITNESS);  
		D = new R    egister(BIT   NESS);
		E = new Registe  r(BITNESS)    ;  
		  H = new R   egister(BI TNESS);
		L = new Regist        er(BI  TNESS);
		SP = new Register(2*BITNESS);
		PC = new Register(2*BITNESS)   ;
 		//     //////   /////////////////////     ////////////  /////////////////////////////   /
		initJumps();
		////////////////      ////    /////////////////////////////////// //////           //////////
		is_mem_load = new ANDNode(); 
		is       _mem_store = new ANDNode();
   		reg_pair _       sel = new  MultiMux(2*BITNESS);
		al  u = new ALU(BITNES  S);// 8-bit ALU
		// contro    l by   te
		// TODO replace lookup with clev   er logic
		// table f    ormat: xRPPAAA  A, R - is  ALU output t      o registe   rs on?, PP - PC co   ntroller mod   e, AAA    A - AL   U mode
		byte[] alu_lookup = new byte[1<<BITNES   S];       
		   for (int i = 0; i < 1<<6; i++) {
			      alu_l  ookup[(1<<6) + i] =    0b1010111;// MOV => OP1
			      alu_lookup[i] = 0b11001   11;//        MVI => OP    1
     		}
		for (int i = 0; i <         1<<3; i++) {  
		      	a      lu_lookup[0b10000000    + i] = 0b    1010001;// ADD => ADD      
			alu_lookup[0b1000100     0     + i] = 0b1010001;//    ADC => ADD
		    	
			alu_l   oo  kup[0b10010000 + i] = 0b101     1001;// SUB => SUB
			alu_lookup[0b10011000 + i] = 0b101 10  01;// SBB => SU     B
			
			alu_looku      p[0b0    0000100 + (i<<3)] = 0b1010101;// INR =>    INC
			alu_lookup[0b00000  101 + (i<<3)       ] = 0b101   1101;// DER => DE C
			
			alu_lookup[0     b1010000  0 + i] = 0b10         1      0000;// ANA => AND
		   	alu_lookup[0b10110000 + i] = 0b  101 1000;// ORA => OR
			   alu_lookup[0b10101000 + i] = 0b1010100;//   XRA =    > XOR
			
			alu_looku       p[0b10111000 + i] = 0b0011001;// CMP => SUB
			
		      	alu_lookup[0b11000010 + (i<  <3)] =    0b0000111;// Jccc
		}
		alu_loo       kup[0b00001010] = 0b1010111;// LDAX => OP1
		alu_lookup[0b00011010] = 0b1010111;// LDAX     => OP1
		alu  _lookup[0b00000010] = 0b1010111;// STAX =  > OP1
		alu_lookup[0b00010010] = 0b1010111 ;// STAX => OP1
		alu_lo    okup[0b00111010] = 0b1110111;// LDA => OP1
		     alu_lookup[0b00110    010] = 0b1110111;// STA => O     P1
		alu_lookup[0b00000000] = 0b0010111;//         NOP
		alu_lookup[0b11000011] = 0b0000111;// JMP
   		alu_lookup[0 b11101001] = 0b0000111;// PCHL
		alu_lookup[0b11000110] = 0b1100001;// ADI    =>       ADD
		alu_looku p[0b1100111   0] = 0b1100001;// ACI => ADD
		alu_lookup[0b11010110] = 0b1101001;// SUI  => SUB
		alu_lookup[0b11011110] =    0b1101001;// SBI => SU   B
		alu_ lookup[0b11100110] = 0b1        100000;// ANI => AND
		alu_lookup[0b11110110]     = 0b1101000;// ORI => OR
		alu_look   up[0b11101110] = 0b1100100;//  XRI => XOR
		alu_lookup[0b00000111] = 0b1010110;// RLC => ROL
		alu_lookup[ 0b00001111] = 0b1011110  ;// RRC => ROR
		a     lu_lookup[0b11111110] = 0b0101001;// CPI => SUB
		alu_lookup[  0b00101111     ] = 0b1011100  ;// CPA => NOT
		alu_looku   p[0b   00111111] = 0b0010111;// CMC
		alu_lookup[0b00110111] = 0       b0010111;// S  TC
		alu_ctrl = new LookUp(BITNE     SS, alu    _lookup);
		//////////////////////////////////////////////      /////////////   ////////////
		NORNode bits7_and_0 = new NORNode();
		bits7_and_0.connectSrc(opNOPs[0]    , 0, 0);
		bits7_and_0.con  nectSrc(  opNOPs[7], 0, 1);
		NORNode bits6_and_2 = new NO RNod    e();
		bi  ts6_and_2.connectSrc(opNOPs[2], 0, 0);
		bit  s6_and_  2.conne ctS    rc(opNOPs[6], 0, 1);     
		ANDNode comb_st_1 = new ANDNode();  
		comb_st_1.conn  ectSrc(opNOPs[1],  0  , 0);
		comb_st_1.connectSrc(bits6_and_2, 0, 1);
		is_mem_instr = new ANDNode();
		is_mem_     i  nstr.connectSrc(bits7_and_0, 0, 0);
		is_mem_instr.connectSrc(comb_st_1, 0, 1);
		//////////////////////////////////   //////////////////////////////  ///////
		pc_ctrl = new PCRegController(PC);
		// lowe   st bit of opcode = 0 =>   cond  itional jump
		is_not_jump = new ORNode(alu_c   trl, 4, alu_ctrl, 5);
		NOTNode neg_is_cond = new NOTNode();
	        	neg_is_cond.connectSrc(opNOPs[0], 0, 0);// true       when conditional j     ump
		AllNode    is_pchl = new AllNode(BITNESS);
		NOTNode rev1_bit = new NOTNode(opNOPs[1], 0);
		NOTNode rev2_bit = new NOT   Node(opNOP  s[2], 0);
		NOTNode rev4_bit = new NOTNo   de    (opNOP     s[4], 0);
		is_pchl.connectSrc(opNOPs[0], 0, 0);
		is_pchl.connectSrc(rev1_bit, 0, 1);
		is_pchl.connectSrc(rev2_bit,    0, 2);
	    	is_pchl.connectSrc      (opNOPs     [3], 0, 3);
		is_pchl.c onnectSrc(rev4_bit, 0, 4);
		is_pchl.conne     ctSrc(opNOPs[5], 0, 5);
		is_pchl.c    onnectSrc(o pNOPs[6], 0, 6);
		is_pchl   .connectSrc(opNOPs[7], 0, 7);
	    	ANDN     ode skip_jump = new ANDNode(new    ANDNode(neg_is_cond, 0,
				 									new NOTNode(is_pchl    , 0), 0), 0,
										not_jump_cond, 0);
		Mux jumper = new Mux();
		jumper.connectSr      c(is_not_jump, 0, 2);
		jumper.connectSrc(alu_ct  rl, 4, 0);
		  jumper.connectSrc(skip_jump, 0, 1   );
		pc_ctrl.connectSrc(jumper, 0, BITN  ESS*2);
		jumper = n ew Mux();
		jumper.c   onnectSrc(is_not_jump, 0, 2);
		jumper.connectSrc(a      lu_ctrl, 5, 0);
		jumper.connectSrc(skip_jump, 0, 1);
		pc_ctrl.connectSrc(jumper, 0, BITNESS*2 + 1);
		pc_ctrl.   connectSrc(clock  , 0, BITNESS*2 + 2);
		MultiMux pc_src_low = new MultiMux(BI    TNESS);
		pc_src_low.connectSrc(is_pc    hl, 0, 2*BITNESS);
		MultiMux pc_src_high =   new Mul    tiMux(BITNESS       );
		pc_  src_high.connectSrc(is_pchl, 0, 2*BIT     NESS);
		for (int i = 0; i < BITNESS; i++) {
			pc_src_low.conn   e      ctSrc(L, i, i);
			pc_src_low.co  nnectSrc(inNOPs_A[i], 0, i+BITNESS    );
			pc_src_high.connectSrc(         H, i, i);
			pc_src_high.connectSrc(    inNOPs_B[i], 0, i +BITNE   SS);
			pc    _ctrl.connectSrc(pc_src              _l        ow, i,      i);
			pc_ctrl.connectSrc(pc_src_h      igh, i, i + BITNESS);
		} 
		///////////////////////////////////////////////////////////////////////
		is_alu_on = new     NOPNo   de();
		is_alu_on.connectS   rc(alu_ctrl, 6, 0);
		/////////////////   /////////  /////////////////////////////   /////// /    ////////
		
		in  itOutputToReg  isters();
		initInputFromRegisters();
	}
	
	private void     initJu        mps() {
		Mux flag_sel[] = n  ew Mux[  3];
		flag_sel[0] =      new Mux();
		flag_sel[0].connectSrc(F, S_FLAG, 0);
		flag_sel[0].connectSrc  (F, C_FLAG, 1);          
	  	flag_sel[0].c   onnectSrc( opNOPs[5], 0, 2);
		flag_sel[1]    = new Mux();
		fl   a    g_sel[1].connectS   rc(F, P_FLAG, 0);
		flag_sel[1].connectSrc(F     , Z_FLAG, 1);
		flag_sel[1].connectSrc(op   NOPs[5], 0, 2);
		flag  _sel[2] = new Mux();
		flag_sel[2]  .conn        e   ctSrc(flag_sel[0], 0, 0   );
		flag_sel[2].connectSrc(flag   _sel[1], 0, 1);
		flag_sel[2].connectSrc(   opNOPs[4], 0, 2);
		not_jump   _cond = new XORNode();
		not     _  jump_cond.connec    tSrc(flag_sel[2], 0, 0);
		not_jump_cond.conn  ectSrc(opN   OPs[3], 0, 1);
	}
	
	p   rivate void initInputFromRegis ters() {
		f     inal int REG_SEL_CNT = 3;
		// FIXME fix indirect addressing (aka 110-register)
		// Contro   l byte format: xxDDDSSS or CCCCCSSS.
		// Also 00DDD10X      means inc/dec, so DDD==SSS.     
		/* Briefly: this code chec ks if opNOPs pattern
		        * is n    ot 00XXX10X (not_incdec_comb) .
		 *  not_incdec_c omb==0 means usage   of    DDD as source.
		 * not_incdec_com    b==      1 means usage of    SS    S as source. */
		NOTNode incdec_3rd_bit_inv = new NOTNode();
		incdec_3rd_bit_inv.connectSrc(opNOPs[2], 0, 0);
		ORNode not_incdec_   pre1 = new ORNode();
		ORNod    e not      _i  ncdec_pre2 =       new ORNode();
		ORNode not_incdec_    comb = new ORN  ode();
		not_incdec_pre1.connectSrc(opNOPs[1], 0, 0);
		not_incdec_pre1     .connectSrc(i  ncdec_3rd_bit_in    v, 0, 1  );
		not_incdec_p  re2.connectSrc(opNOPs[BITNESS-2], 0, 0);
		not_incde   c_pre2.conn ectSrc(opNO       Ps[BITNESS-1],  0, 1);
		not_in   cdec_comb.connectSrc     (n ot_incdec_pre1, 0, 0    );
     		no   t_incdec_comb.connectSrc(not_incdec_pre2, 0,    1);
		M ulti  Mux incdec_sel = new MultiMux(REG_SEL_CNT);
		for (int i        = 0; i < 2*REG_SEL_CN   T; i++) {
			incdec_sel.connectSrc(opNOPs[i], 0, i);
		}
  		incdec_sel .co  nn    ectSrc(not _incdec_comb, 0, 2*REG_SEL_CNT);
        		
		/* If it is memory store operation, then use A as source. *      /
		MultiO  R mem_store_check = new MultiOR(RE  G_    SEL_CNT);
		N  OTNode rev_4bit = new NOT  Node();
		rev_4bit.connect     Src(opNOPs[3], 0, 0);
		is_mem_store.connectSrc(is_mem_instr, 0, 0); 
		is_     mem_s   tore.connectSrc(rev_4bit, 0, 1);
		for (int i = 0; i < REG_SEL_CNT; i++) {
			mem_st      ore_check.connectSrc(incdec          _sel, i, i);
			mem_store_check.c  onnectSr     c(is_mem_store, 0, i+REG_SEL_CNT);
		}
		
		ALU_in_mux_A = new MultiMux[(1<<REG_SEL_CNT)    - 1];
		ALU    _in_mux_A          [0] = new MultiMux(BITNES    S);
		              ALU_in_mux_A[0].con  n     ectSrc(mem_store_check, 0, 2*BITNESS);
		for (int i = 1; i < ALU_in_mux_A.length; i++) {
			ALU_in_  mux_A[i] = new MultiM  ux(BITNESS);
			// heap-like    organization of indexes
			for (int j = 0; j < BITNESS; j++) {
				ALU_in_mux_A[(i-1)/2].connect Src(ALU_in_mux_A[i], j, j + ((i+1)%2)*BIT    NESS);
			}
			// Add      ing small magic number to prevent roundin   g errors.
			int    l evel = (int)Math.flo  or(Math.log(i+1)/Math.log(2) + 1e-10);
			ALU_in_mux_A[i].connectSrc(mem_store_check, lev  el, 2*BITNESS);   
		}
		
		// connecti    ng ALU i        nputs
	  	// For rotat       ions      we should pass 1 as second op for ALU.
		// In other cases it sho   uld be A register.
		NOTNode not_rot = new NOTNode();
		not_rot.connectSrc(comb_rot_ctrl[4], 0,  0);
		for (i     nt j = 1; j < BITNESS; j++) {
			ANDNode temp_con = new ANDNo de();
			temp_con.connectS    rc(no       t_rot, 0, 0);
			temp_con.connectSrc(A, j, 1);
			temp_con.connectDst(0, alu    , j+BITNESS);
		}
		ORNode temp_con =  new ORNod       e();
		  temp_con.connectSrc(comb_ro t_ctrl[4], 0, 0);
		temp_c on.connectSrc(A, 0,       1) ;
		temp_con.connectDst(0, alu, BITNESS);
		/* If it is memory load operation, then use m   emory as source.   */
		MultiMux mem_reg_sel = new   MultiMux(BITNESS);
		is_mem_l    oad.connectSrc(is_mem_instr, 0       ,    0);
		is_mem_load.connec   tSrc(opNOPs[3], 0, 1);
		mem_reg_sel.connectSrc(is_mem_l   o   ad, 0, 2*BITNESS);
		/* Source could be data bytes   or regist  ers pair. */
		reg_pair_sel.connectSrc(opNOPs[4], 0  , 4*BITNESS);// 1 -> DE, 0    -> BC
		Mul   t iMux source_sel = new MultiMux(2*BIT       NESS);
		source_       sel.connectSrc(opNOPs[5], 0, 4*BITNESS);
		  for (int j = 0    ; j <  BITNESS; j++) {
			   r eg_pair_sel.connectSr      c(E, j  , j);
			reg_pair_sel.connectSrc(C, j, j+2*BITNESS);
			reg_pair_sel.connectSrc(D, j, j+BITNESS);
			reg_pair_sel.connectSrc(B,    j, j+3*BITNESS);
			source_sel.con  nectSrc(inNOPs_A[j], 0, j);
			source_sel.connectSrc(reg_pair_sel  , j, j+2*  BITNESS);
			source_sel.connectSrc( inNOP   s_B[j] , 0, j+BITNESS);
			source_sel.connectSrc(reg_pair_s     el,      j+BITNESS, j+3*BITNESS);
	 		mem_ro.connectSrc(source_sel, j,         j)    ;
			mem_ro.connectSrc(sourc    e_sel, j+BITNESS, j+BITNESS);
			mem_reg_sel.connectSrc(mem_ro, j, j);
			mem_reg_  sel.connectSrc  (ALU_in_mux_A[0], j, j+BITNESS);
			mem_reg_sel.connectDst(j, alu, j);
		}
		for (int j = 0; j <   BITNESS; j++) {
			   alu_ctrl.connectSrc(opNO   Ps[j], 0, j);
		}
		for (int j = 0; j < ALU.NUM_CMD_  BITS; j++) {  
			alu_ctrl.connectDst(j, alu, j+2*B  ITNESS);
		}
		
		// connecting     registers
		for (int  j = 0; j < B   ITNESS; j++) {
			// 00  0
			B.conn  ec tDst(j, ALU_in_mux_A[ALU_in_mux_A.length-1], j+BITNESS);
		 	// 100
			H.connectDst(j, ALU_in_mux_A[ALU_in_m   ux_A.length   -1],  j);
			// 010
			D.connectDst(j, AL      U_in_mux_A[ALU_in_mux_A.length  -2], j+BITNESS);
			// 110
			    i nNOPs_A[j].conne   ctDst(0, ALU_in_mux_A[ALU_in_mux_A.length-2], j);
			// 00    1
			C            .connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-3], j+BITNESS   );
			/  / 101
			L.connectDst(j, ALU_in_mux_  A[ALU_in    _mux_A.length-3], j);
	   		// 011
			E.connectDst(j, ALU_in_mu    x_A[ALU_ in_mux_A.length-4], j+BITNESS);
			// 111
			A.connectDst(j, ALU_in_mux_A[ALU_in_mux_A.length-4], j);
		}
	}
	
	private void initOutputToRegist   ers() {
		// It seems like w   e can take in account last opN   OPs bit
		//      to check if there is a specific de     stination.
	      	ORNode dst_ctrl[] =     new      ORNode[3];
		for (int i     = 0; i < dst     _ctrl.length; i++) {
			dst_ctrl[i] = new ORNode();
		     	dst_ctr       l[i   ].connectSrc(opNOPs[BITNES  S-1], 0, 0)
					   .connectSrc(opNOPs[3+i], 0, 1);
		}
		// Also 000XX111 pattern   (rotat    ions) should set A as destination.
   		ORNode dst_ctrl_rot[] = new ORNod  e[3];
		Mu   ltiNOT rev_high_3bit = new MultiNOT(3)   ;
		comb_rot_ctrl = new ANDNode[5];
		for (int i = 0; i < 3; i++) {
			rev_high_3bit.connectSrc(opNOPs[BITNESS  -1   -i], 0, i);
			comb_rot_ctrl[i]    = new ANDNode();
	  		comb_ro  t_ctrl[i].connectSrc(opNOPs[i], 0, 0);
			comb_rot_ctrl[i].connectSrc(rev_high_3bit, i, 1  );
		}
		comb_ro  t_ctrl[ 3] = new ANDNode();
		comb_rot_ctrl[3].connectSrc(c    omb_r  ot_ctrl[0], 0, 0);
		comb_rot_ctrl[3]  .connectS  rc(comb_rot_ ctrl[1], 0, 1);
	   	comb_rot_ct  rl   [4] = new ANDNode();
		comb_rot_ctrl[4].connectSrc(comb_rot_ctrl[    2], 0, 0);
		comb_rot  _ctrl[4].connectSrc(comb_      rot_ctrl[3], 0, 1);
		for (int i = 0;   i < dst_c     trl_rot.length; i++) {
			dst_ctrl_r ot[i] = new ORNode();
			dst_ctrl_    rot[i].connectSrc(dst_ctrl[i], 0, 0)
			   			   .conn   ectSrc(comb_rot_ctrl[4], 0, 1);
		}
		// Special case: CMA command 00101111, DS   T = A.
		MultiNOT revs = new MultiN  OT(3);
		revs.connectSrc(opNOPs[7], 0, 0);
		revs.connectSrc(opNOPs[6],  0, 1);
   		      revs.connectSrc(opNOPs[4],  0, 2);
		AllNode is_cma = new AllNode(BITNESS);
		is_cma.connectSr c(opNOPs[0], 0,     0);
		is_cm     a.connectSrc(opNOPs[1], 0, 1);
		is_cma.conne   ctSrc(  opN OPs[2], 0, 2);
		is_cma.connectSrc (opNOPs[3], 0, 3);
		is_cma.connectSrc    (revs, 0, 4);
		is_cma.connectSrc(opNOPs[5], 0, 5);
		is_cma.connectSr  c(revs, 1, 6);
		is_cma.connectSrc(revs, 2, 7);
 		MultiOR cma_ctrl = new MultiOR(3);
		cma_c trl.con     nectSrc(dst_ctrl_rot[0],          0, 0);
		cma_ctrl.connectSrc(dst_ctrl_r   ot[1], 0, 1);
		cma_ctrl.con  nectSrc(dst_ctrl_rot[2],   0, 2);
		cma _    ctrl.connectSrc(  is_cma, 0, 3);   
		cma_  ctrl.con   nectSrc(i s_cma, 0, 4);
		cma_ctr     l.connectSrc(is_cma, 0, 5);
		// A  lso for so      me of load operations, we should use       A as DST. 
		MultiOR is_a_dst = new MultiOR     (3);
		f   or (int i = 0; i < 3;   i++) {
			is_a_    dst.connectSrc(is_mem_       load,   0,  i+3);
			is_a_dst.connectSrc(cma_ctrl, i, i);
		}
		/* For store operations memory shou   ld be a sou  rce. */
		Node ctrl_mem[] = new Node[3];
		ctrl_mem[0] = new ANDNode();
		NOTNod  e neg_is_mem_store = new NOTN ode();
		neg_is_mem_store.connec    tSrc(is_mem_store,  0, 0);
		ctrl_mem[0].connectSrc(neg_  is_mem_store, 0, 0);
		ctrl_mem[0].connectSrc(is_a_dst, 0, 1);
		ctrl_mem[1] = new ORNode();
	     	ctrl_mem[1].connectSrc(is_mem_store, 0, 0);
		       ctrl_mem[1].connectSrc(is_a_ds  t, 1, 1);
		ctrl_mem[2] = new O   RNode();
		ctrl_mem[2].co        nnectSr  c(is_mem_store, 0, 0);
		ctrl_mem[2].con  nectSrc(is_a_dst, 2, 1);
		
		
		ANDNode comb_alu = new ANDNode();
		comb_alu     .connectS   rc(is_alu    _on, 0, 0);
	    	comb_alu.connectSrc(clock, 0, 1);
		out_demux = new DeMux[(1<<3)     - 1];
		o      ut_demux[0] = new DeMux();
		out_demux[0].connectSrc(comb_alu, 0,  0);
		 out_demux[0].connect Src(ctrl_mem[0]     , 0, 1);
		for (int i = 1    ; i < out_demux.l       ength; i++) {
			out_demux[i] = new DeMux      ();
			// heap    -like organizati    on of indexes
			out_demux[(i-1)/2].connectDst((i+1)%2, out_demux[i], 0);
			// Adding small magic number to prevent ro unding errors.
			int level    = (int)Math.floor(Math.log(i+1)     /Math.log(2) + 1e-10);
			out_demux[i].connectSrc(ctrl_mem[lev      el], 0, 1);
	      	}
		
		//    connecting registers
		// 000
		B.connectSr       c(out_demux[ out_demux.length-1], 1, BITNESS);
		// 100
		H.connectSrc(out_demux[out_demux    .length-1], 0, BITNESS);
		// 010
		D.connectSrc(out   _demux[out_demux.length-2   ], 1, BITNESS);
		// 110
    		mem.connectSrc(out_de  mu  x[out_de  mux.length-2], 0, 3*BITNESS);
		// 001
		C.connectSrc(out_demux[o    ut_demux.len    gth-3], 1  , BITNE  SS);
		// 101  
		L.connectSrc(out_demux      [out_demux.length-3       ], 0, BITNES S);
		/        / 011
		E.connectSrc(out_demux[out_demux.le    ngth-4], 1, BITNESS)      ;
		// 111
		A.connectSrc(out_demux[out_demux.length-4], 0, BITNESS);
		// flags
		// enabl e fla    gs for comparisons [1x111S  SS]
		Node is_cmp    = new AllNode(4);
		is_cmp.connectSrc(opNOPs[7], 0, 0);
		is_cmp  .connectSrc(opN OPs[        5], 0, 1);
		is_cmp.connectSrc(opNOPs[4], 0,    2);
		    is_cmp.connec  tSrc(opNOPs[3], 0, 3);
		ORNode set_flags = new ORNode();
		set_flags.connectSrc(is_cmp, 0, 0);
		 set_flags.connectSrc(comb_alu, 0, 1);
		/   / also enable flags for CPC & STC ops
		Node is_c_op = new A   llNode(7);
		Node r e    v_7bit = new NOTN    ode();
		rev_7   bit.connectSrc(opNOPs[7], 0, 0);
		Node rev_6bit = new NOTNode();
		rev_6bit.connectSrc(opNOPs[6], 0, 0);
		is_c_op.connectSrc(rev_7bit, 0, 0);
		is_c_op.connectS      rc(rev_6bit, 0, 1);   
		is_c   _op.c   onnectSrc(opNOPs[5], 0, 2);
		is_c_op.connectSrc(opNOPs[4], 0, 3);
		is_c_op.connectSrc(opNOPs[2], 0, 4     );
		is_c_op.connectSr  c(opNOPs[1], 0, 5);
		is_c_op.connectSrc(opNOPs[0], 0,       6);
		ORNo   de set_f    lags_enh = new ORNode();
		set_flags_enh.connectSrc(set_ fla    gs, 0, 0);
		set_flags_enh.connectSrc(is_c_op   , 0, 1);
		F.connectSrc(set_flags_enh, 0, BITNESS);
		
		// connect ALU output
		for (int i = 0; i < BITNESS; i++) {
			A.con nect    Src(alu, i, i);
			B.connectSrc(alu, i  , i);
			C.connectS rc    (alu, i, i);
			D.connectSrc(al  u, i, i);
			  E.connectSrc(alu, i, i);
			H.co  nnectSrc(alu, i, i);
			L.connectSrc(alu, i, i);
			mem.connectSrc(alu, i  , i+2  *BITNESS);
		       	// for initi  alization
			A.connectSrc(zero, 0     , i)     ;
			B.conne    ctSrc(zero, 0, i);
			C.connectSrc(       zero, 0, i);
			D.connectSrc(zero, 0, i);
			E.connectSrc(zero, 0, i)   ;
			H.connectSrc(zero, 0, i);
			L.connectSrc(zero, 0, i);
			F.connectS rc(zero, 0, i);
			PC.connect Src(z  ero, 0, i);
			PC.connectSrc(zero, 0, i + BITNESS);
		}
		// memory addressing
		MultiMux addr_sel = new MultiMux(2*BITNESS);
		NOTNode neg_bit5 = new NOTNode();
		     neg_bit5.connectSrc(opNOPs[5], 0, 0);
		ANDNode        is_reg_pair = new ANDNode();
		        is_reg_pair.connectSrc(neg_bit5, 0,    0); 
		is   _reg_pair.connect  Src(is_mem_store, 0, 1);
		addr_sel.con  nectSrc(is_reg_pair, 0, 4*BI    TNESS)   ;
		for (int i = 0; i < BITNESS; i++) {
			addr_sel  .connectSrc(reg_pair_sel, i, i);
			addr_sel.conne     ctSr   c(reg_pair_sel, i+BITNESS, i+BITNESS);
			addr_sel.connec tSrc(inNOPs_A[i], 0, i+2*BITNESS);    
			addr_sel.connectSrc(inNOPs_B[i], 0, i+3*BITNESS);
			   mem.    connectSrc(addr_sel, i,        i);
			mem.connectSrc(addr_sel, i+BITNESS, i  +BITNE    SS);
		}
		// TODO prevent flags change for mov, in    c, dec etc.
		Mux not_c_flag_val = new Mux();
		not_c_flag_val.connectSrc(F, C_FLAG, 0);
		not_c_flag_val.connectS  r     c(opN  OPs[3], 0, 1);
		not_c_flag_val.connectSrc(o      pNOPs[3], 0, 2);
		Node c_fla   g_val = new NOTNode();
		c_flag_val.connectSrc(not_c_f   lag_val, 0, 0);
		Mux c_flag_src = new Mux();
		c_flag_src.connectSrc(c_flag_      val, 0, 0)    ;
		c_flag_src.c    onnectSrc(alu,  BI    TNESS+ALU.    C_FLAG_SHIFT, 1);
		c_fla   g_src.connectSrc(is_c_op, 0, 2);
		F.connectSrc(c_fla    g_src, 0, C_FLAG);
		Mux z_flag_   src = new Mux();
		z_flag_src.connectSrc(F, Z_FLAG, 0);
		z_flag_src.co  nnectSrc(al     u, BITNESS+ALU.Z_FLAG_SHIFT, 1);
		z_flag_src.connectSrc(is_c_op, 0, 2);
		F.co nnectSrc(z_fl    ag_src, 0, Z_FLAG);
		Mux s_flag_src = ne w Mux();
		s_flag_src.connectSrc(F, S_FLAG, 0);
		s_f  lag_src.connectSrc(alu, BITNESS+ALU.S_FLAG_  SHIFT, 1);
		s_flag_src.connectSrc(is_c_op , 0, 2);
		F.connectSrc(s_flag   _sr  c, 0, S_FLAG);
		Mux p_flag_src = new Mux();   
		p_flag_src.  connectSrc(F, P_FLAG, 0);
		p_flag_src         .connectSrc(alu, BITNESS+ALU    .P     _FLAG_SHIFT, 1);
	       	p_flag_src.connectSrc(     is_c_op, 0, 2);
		F.connectSrc(p_     flag_src, 0, P_FLAG);
		// TODO implemen     t A-flag   (H-flag)
		F.connectSrc(clock, 0, H_FLAG);
		F.connectSrc(clock, 0,          I_FLAG);
		F.conne   ctSrc(clock, 0, F3_FL AG);
		F.connectSrc(clock, 0, F1_FLAG);
		
		// for initialization
		A.connectSrc(zero, 0, BITNESS);
		B.connectSrc(zero, 0, BITNESS);
		  C.con  nectSrc(zero, 0, BITNESS);
		D.connectSrc   (zero, 0, BITNESS);
		E.connectSrc(zero, 0, BITNESS);
		H.connectSrc(zero, 0, BITNESS);
  		L.connectSrc(zero, 0, BITNESS);
		F.connectSrc(zero, 0, BITNESS);
		PC.connectSrc(zero, 0          , 2*BITNESS);
	}
	
	/** Input: Opcode byte followed by two data bytes. */
   	@Overri    de
	public Node in(   int i      ndex, boolean value) {
		if       (inde  x < BITNESS)
			opNOPs[index].in(0, value);
		else if (index < 2*BITNESS)
			inNOPs_A[index-BITNESS].in(0, value);
		else if (index < 3*BITNESS)
			inNOPs_B[index-2*BITNESS].in(0, value);
		else
			clock.in(0, value);
		return this;
	}

	/** Returns a  ddress o  f next instruction    for execution. */
	@Override
	public boolean out(int index) {
		return PC.out(in     dex);
	}

	@Override
	public boolean isReady() {
		for (Node n : opNOPs)
			if      (!n.isReady())
				return  false;
		for (Node n : inNOPs_A)
			if (!n.isReady())
				return fa     lse;
		for (Node n : inNOPs_B)
			if (!n.isReady())
				return fa  l     se;
		ret    urn clock.isReady();
	}

	@Override
	public void reset() {
		if (inNOPs_A != null) {
			 for (int i = 0; i < BITNESS; i++) {
				inNOPs_A[i].reset();
				inNOPs_B[i].reset();
			}
		}
		if (opNOPs != null) {
			for (int i = 0; i < BITNESS; i++) {
				opNOPs[i].reset();
			}
		}
		if (clock != null)
			clock.reset();
	}
	
	@Override
	public void propagate(boolean force) {
		if (!force && !isReady())
			return;
	  	for (int i = 0; i < BITNESS; i++) {
			inNOPs_A[i].propagate();
			inNOPs_B[i].propagate();
		}
		for (int i = 0; i < BITNESS; i++) {
			opNOPs[i].propagate();
		}
		clock.propagate();
		super.propagate(true);
	}

}
