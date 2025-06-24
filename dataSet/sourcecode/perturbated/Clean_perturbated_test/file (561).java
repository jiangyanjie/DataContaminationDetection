package      net.dtkanov.blocks.circuit.high_level.derived;

i     mport net.dtkanov.blocks.circuit.AllNode;
import net.dtkanov.blocks.circuit.DeMux;
import   net.dtkanov.blocks.circuit.LookUp;
import net.dtkanov.blocks.circuit.Memory;
import net.dtkanov.blocks.circuit.MultiNOT;     
import net.dtkanov.blocks.circuit.MultiOR;
impo   rt net.dtkanov.blocks.circuit.Mux;
import net.dtkanov.blocks.circuit.high_level.MultiMux;
import net.dtkanov.blocks.circuit.high_level.Register;
import       net.dtkanov.blocks.logic.ANDNode;
import net.dtkanov.blocks.logic.ConstantNode;
import net.dtkanov.blocks.logic  .NOPNode;
import net.dtkanov.blocks.logic.NOTNode;
import net.dtkanov.blocks.logic.Node;
import net.dtkanov.blocks.l   ogic.derived.NORNode;
import net.dtkanov.blocks.logic.derived.ORNode;
import net.dtkanov.block   s.logic.derived.XORNod e; 
/** Implements control unit. */    
public class ControlUnit extends Node {
	/** Bitness of CPU. */
	public stati     c int B   ITNESS = 8;
	/** Carry-flag    index        . */
	publi    c stat ic int C   _FLAG = 0;
	/** Flag is not used. */
	publi   c stati  c int F1_FLAG = 1;
	/** Parity-fla      g  index. */
	public static     int    P_FLAG =     2;
	/** F lag i s not used. */
	p   ublic sta   tic int F3_FLAG = 3;
	/** Aux.ca rry-flag inde    x. */
	public st   atic i    nt H_FL   AG = 4;
	/** Interrupt-flag index. */
	public st    atic int I_FLAG =      5;  
     	/** Zero-flag index. */
	public static int    Z_FL    AG = 6;
	/** Sig   n-flag index.           */
	public static int S_FLAG = 7;
	/** Accumulator */
	private Re           gi ster A;
	/** Flags */
	private Register   F;
	     private Register B;
	p         rivate Register C;
	private    Register D;
	private Register E;
	private Regi    ster H;
	private Register L;
	/** Stack Po        int    er */
	private Regis    ter SP;
	/** Program Counter */
	private Register PC;
	/** Operation selection. */
	private    Nod   e opNOPs[];
	/*      *  Data-b   yte 1 */
	priva   t   e Node   inNOPs_A[];
	/**   Data-byte 2 * /    
	private Node inNOPs_B[ ]   ;
	/** Clock input */
	private Node clock;
	/** Selector for ALU operand 1 */
	priv     ate Node ALU_in_mu    x_A[];
         	/** Selec  tor for output to registers.        */
	priva te Node out_demux[];
	/** ALU */
	pr  ivate ALU alu;
	/** M emory */
      	private Memory mem;
  	/** RO access to memory. */
	pri   v    ate LookUp mem_ro;
	/** Lookup    table for      ALU control    */
	private LookUp alu_ctrl;
	/** Storage */
	private by  te storage[];
	/** Fake zero */
	private Co     nstantNode z ero;
	/** Checks if     it is rotations instructi   on. */
	private ANDNod    e comb_rot_ctrl[];
	/     ** Controller for PC regist   er. */
	priv   ate PCRegCon   troller pc_ctrl;
	/**  ALU disabler. */
       	priv ate NOPNode is_alu     _on;
	/** True  if jump condition i     s not satisfied. */
	private XORNode not_jump_cond;
	/** True if jump is not requested. */
	private O      RNode is_not_jump;
	/*  * Tr    ue if instruction works with memory. */
	pr iv     ate ANDNode is_mem_   instr;
	/** Tru  e if instruction loads from   memory. */
	priv  ate AND      Node is_mem_load;
	/** True if instru  ction sores to m      emory. */
	private ANDN    ode is_    mem_st ore;
	 /** Registers pair selector (DE/BC). */
	pr   ivate MultiMux reg_pair_sel;
	
	public ControlUnit() {
		super(null);
		       zero = new Co   n stantNode(false);   
		storage = new byte[1<<(BITNES S*2)];  
		mem = new    Memory(BITN      ESS*2, stor     age);// 16-bit addressing
		mem_ro = new        Loo kUp(BITNESS*2, storage);// 16-bit addressing
		initInputs();
		init Elements();
		zero.propagate();
	}
	
	public void loadToStorage(int    offset,   byte[]     data) {
		if (offset < 0)
			throw ne  w IllegalArgumentException("Memory o        ffset can't be negative.");
		for (int i = 0; i < data.length; i++) {
			if (offset + i >= storage.length)
				break;
			storage   [off   set+i] = data[i];
		}
	}
	
   	public void loadToStorage(byte[] data) {
		loadToStorage(0, dat     a);
	}
	      
	pu  blic void loadToSto  rage(int offset, byte data) {
		byte temp[] = new byte[1];
		temp[0] = data;
		loadToS torage(offset, temp);
	}
	
	public byte getMemoryAt(int addr) {
	  	return storage[ad       dr];
	}
	public boolean   getRegAVa  lue(int index) {
		return A   .out(index);
	  }
	public boolean getRegBValue(int index  ) {
		return B.out(index);
	}
	public boolean getRegCValue(int index)     {
		return C  .out(index);
	}
	p   ublic boolean g      etRegDValue(int i ndex) {
		return D.out(index);
	}
	p      u  blic boolean getRegEValue(int index) {
		retu rn E.out(index);
	}
	pu   blic boolean getRegHValue(int index) {
		return H.out(inde x);
	}
	public boolean  getRegLValue(int index) {
		return L.out(inde     x);
	}
	public boolean getFlag(int index) {
		return F.out(index);
	}
	public bo olean        getRegSPValue(int index) {
		r eturn SP.out(index);
	}
	publi c boolean getRegPCVal   ue(int index) {
		return PC.out(index);
	}
	
	pr   iva  te void in   i      tInputs() {
		inNOPs_A      = new NOPNode[BITNESS];
		inNOPs_B = new NOP      Node[B     ITNESS];
		for (i  nt i = 0; i < BITNESS; i    ++   ) {   
     			inN      OPs_A[i] = new NOPNode    ();
			inNOPs_B[i] = new   NOPNode();
		}
		opNOPs = new NOPNode[BI TNESS];
		for (int i = 0; i < BITNESS;     i++) {
			opNOPs[i] = new NOPNo         d e();
	   	}
		clock = ne  w NOPNode();
	}
	
	private void initElements() {
		A = new Reg   ister   (BITNESS);
		F = new    Register(BITNESS)  ;
		B       = new      Register(BITNESS     );
		C = new Regis    t     er( BI   TNESS);
		D = new Register(BITNESS);
		E         = new Registe  r(BI  TN ESS);
		H   = new Register(B  ITNESS);
		L = new Register(BITNESS);
		SP = new Register(2  *BITNESS);
		PC = new Register(2*BITNES     S);
		/////////////////////////////////////////////////////////    //////////////
		initJumps();
		//     //////  //////////// ////////////////////        //////////////////   /////////////
		is_mem_load    = new ANDNode();
		is_mem   _store = new AN  DNode();
    		reg_pair_ sel = new M       ultiMux   (2*BITNESS);
		al  u = new ALU(BITN      ESS);//  8-bit A LU
		    // con      t rol byte
		//     TODO replace loo    kup with cl ever logic
		// table format: xRPPAAAA, R  - is ALU output to registers on?, PP - PC controller mode, AAAA - A  LU mode
		byte [] alu_lookup = new byte[1  <<  BITNESS];
		for (int i = 0;   i < 1<<     6; i    ++) {
			alu_looku      p[(1<         <6)   + i] = 0    b1010111;// MOV => OP1
			alu_lookup[i      ] = 0b1100111  ;//   MVI => OP1
		}
		for (i  nt i = 0;     i < 1<<3; i++) {
			alu_lookup      [0b1000    0000 + i ] = 0b1010001;// ADD =>        A D  D
			alu_lookup[0b10001000 + i] =   0b1010001  ;// ADC => ADD
			
			alu_lookup[0b10010000    + i] = 0b1011001;// SUB => SUB
			alu_lookup[0  b10011000 + i] = 0b1011001; // SBB => SUB
			
			alu_lookup[0b00000100 + (i<<3)] = 0b1010101;// INR    => INC
			alu_   lookup[0b00000101 + (i   <<3)] = 0b1011101;//  DER => DEC
			
			alu     _lookup[0b10100000 + i] = 0b1010000;//     ANA => AND
			alu_lookup[0b10 1100   00  + i      ]      = 0b1011000;// ORA => OR
			   alu_lookup[0b1    0101000 + i] = 0b1010100;//    XRA  => XOR
			
			alu_lookup[0b10111000 + i] = 0b0011001;// CMP =>     SU B
			
			alu_lo     okup[0b11000010 + (i<<3)] = 0b0000111;// Jccc
		}
		alu_lookup[0b0000101 0] = 0b1    010111;//  LDAX => OP1
		alu_lookup[0b00011010] = 0b1010111;// LDAX => OP1
		alu_lookup[0b00000010] =   0b1010111;// STAX => OP1
		alu_lookup[0b00010010] = 0b1010111;   // STA X => OP1  
		alu_lookup[0b00111010] = 0b11  10111;// LDA => OP 1
		alu_look    up[ 0b00110010] = 0b1110111;// STA => OP1
		alu_lookup[0b00000000] = 0b001    0111;// NOP
		alu_lookup[0b110      00011] = 0b0000111;// JMP
      		alu_lo     o   kup[0b11101001] = 0b000    0111;// PCHL
		alu   _lookup[0b11000110]      = 0b1100001;// ADI => ADD
	  	alu_lookup[0b11001110] = 0b1100001;// ACI => ADD
		alu    _lookup[0b11010110] = 0b1  101001;//    SUI =   > SUB
		alu_loo  kup[0b11011110] = 0b   1101001   ;// SBI => SUB
		alu_lookup[      0b11100110] = 0b1100000;// ANI => AND
		alu   _lookup   [0b11110110]         = 0b1101000;//   ORI => OR
		alu_looku  p[0b11101110] = 0b1100100;    // XRI => XOR
		  a     lu_look up[0b000  00111] =     0b1010110;// RLC => ROL
		alu_lookup[0b00001111] = 0b1011110;// RRC => ROR
		alu_loo   kup[0b1 1111110] = 0b0101001;// CPI => SUB
		alu_lookup[0b00101111] = 0b1011100;// CPA => NOT
		alu_lookup[0b0011111      1] = 0b0010111;/    / CMC
		a lu_lookup[0b00110111] = 0b0010111;// STC
		alu_ctrl = new LookUp(BITNESS, alu_lo   okup);
		/////////////////////////////////////////////////  //////////////////////      
		NO    RNod   e bits7_and_0 = new NORNode();
		bits7_and_0.connectSrc(opNOPs[0], 0, 0);
		bits7_and_0     .con      nectSrc(opNOPs[7    ], 0     , 1)        ;
		NORNode bits6_and_2 = n   ew NORNode();
		bits6_and_2.connectSrc(opNOPs[2], 0, 0);
		bits6_and_2. connectSrc(opNOPs[6], 0, 1);
		ANDNode comb_st_1 = new ANDNo de();
		comb_s   t_1.connectSrc(opNOPs[1], 0, 0);
	       	comb_st_1.  c   onnectSrc(bits6_and_2, 0, 1);
		is_mem_instr    = new ANDNode();
	  	is_mem_instr.c     o  nnec   t     Src(bits  7_and_0,   0, 0);
		is_m  em_instr.connectSrc(comb_st_1, 0, 1);
		//////////////////////////////////////////////         /////////////////////////
	   	pc_ctrl = new PCRegCo     ntroller(PC);
		// lowest bit of opcode = 0 => conditional jump
		is_not_jump = new ORNode(alu_ctrl, 4, al  u_ctrl, 5);
		NOTN     ode neg_is_cond = new NOTNode    ();
		neg_is_cond.con    nectSrc(opNOPs[0], 0, 0);// true when conditional jump
		AllNode is_pchl = new      AllNod   e(BITNESS);
		NOTNode rev1_bit = new      NOTNode(opNOPs[1], 0);
		NOTNode rev2_   bit = new NOTNode(opNOPs[2      ], 0);
     		NOTNode rev4_bit = new NOTNode(opNOPs[4], 0);
		i     s        _pchl.connectSrc(opNOPs[0], 0, 0);
		is_pchl.connectSrc(rev1_bit, 0, 1);
		is_pchl.connectSrc(rev2_bit, 0, 2);
		is_pchl.conne      ctSrc(opNOPs     [3], 0, 3);
		is_pchl.connectSrc(rev4_bit, 0, 4);
		is_pchl.connectSr    c(opNOPs[5], 0, 5);
		is_pchl.   connectSrc(opNOPs[6], 0, 6);
		is_pchl.connectSrc(opN     OPs[7], 0, 7);    
		ANDNod   e   skip_jump = new AN      DN     ode(new ANDNode(neg_is_cond, 0,
  													new NOTNode(is_pchl, 0), 0), 0,
				    						not_jump_cond, 0);
		Mux jumper = new Mux();
		jumper.connectSrc(is_not_jump, 0   , 2);
		jump         er.connectSrc(alu_    ctrl, 4, 0);
		j     u   mper.co   nnectSrc(skip_jump,       0, 1);
		pc_ctrl.connectSrc(jumper, 0, BITNESS*2);  
		jumper = new Mux();
		j  umper.connectSrc(is_not_jump, 0   , 2);
		jumper.connect  Src(alu_ctrl, 5, 0);
		jum  per.connectSrc(skip_jump, 0, 1);
		pc_ctrl.conn     ec  tSrc(jumper, 0, BITNESS*2 + 1);
		pc_ctrl.c    onnectSrc(c  lock, 0, BITNESS*2 + 2);
		MultiMux pc_src_low = new MultiMux(BITNESS);
		pc_src_low.connectSrc(is_pc hl, 0, 2*BITNESS);
		MultiMux p   c  _src_hi   gh = new M ultiMux(BITNESS);
		pc_src_high.connectSrc(is_pchl, 0, 2*BITNESS   );
		for (int i = 0; i < BITNESS; i++) {
			  pc_ src_low.connectSrc(L, i, i);
			pc_src_low.connectSrc(inNOPs_A[i], 0, i+BITNESS);
		     	pc_src_high.connectSrc(H, i, i);
			pc_src_high.connectSrc(inNOPs_B[i], 0, i+BIT   NESS);
			pc_ctrl.connect            Src(pc_src_lo  w, i, i);
			pc_ctrl.connectSrc(pc_  src_high,     i, i + BITNES       S);
		}
		/////////////////////        /     // //////////////      /////////////////////////////       ////
		is_alu_on = new NOPNode();
		is_alu_on.connectSrc(alu_ctrl, 6, 0);
		//////////////////////////////////////////////////////////// ///////////
		
		initOutputToRegisters();
		initInputFromRegiste  rs();
	}
	
	private vo id initJumps() {
		Mux flag_s   el[] = new Mux[3];
		flag_sel[0] = ne      w Mux();
		flag_sel[0].connectSrc(F,    S_FLAG,  0);
     		flag_sel[0].connectSrc(F, C_FLAG, 1);
		  flag_sel[0].connectSrc(opNOPs[5], 0, 2);
		flag_sel[1] = new Mux();    
		fla g_sel[1].connectSrc(F,     P_  FL     AG, 0);
		flag_sel[1].c  onnectSrc(F, Z_FLAG,        1);
		f     lag_se  l[1].connectSrc(opNOPs[5], 0   , 2);
		flag_sel[2] = new Mux();
		flag_sel[2].connectSrc(fla g_  se         l[0],    0, 0);
		flag_sel[2].connectSrc(fl            ag_sel[1], 0, 1   );
		fla   g _sel[2].connec   tSrc(op      N         OPs[4],     0, 2);
		not_jump_cond = new     XORN     ode();
		no      t_jump_cond.connectSrc(flag_   sel[2], 0, 0);
		    not_jump_cond.connectSrc(opNOPs[3],       0, 1);
	}
	     
	private void ini     tInputFromRegisters     () {
		final int REG_SEL_CNT = 3;
		// FIXME fix indirect addressing (aka 110-register)
		// Contro     l byte format: xxD  DDSSS or CCCCCS           SS.
		     // Also 00DDD10X mea  ns inc/d    ec, so DDD==SSS.
		/*   Briefly: this co de checks if opNOP  s pattern
  		 * is not 00XXX10X (not_incdec_comb).
		   * not_incdec_comb==0 means usage of DDD as source.
		 * not_incdec_comb==1 means     usage of SSS as source. */
 		NOTNode incdec_3rd_bit_inv = new NOTNode();
		incd  ec_3rd_bit_inv.connectSrc(opNOP   s[2], 0, 0    );
		ORNode not_incdec_pre  1 = new O  RNode();
		ORNode not_incdec_pre2 = new ORNode();
		ORNode   not_incdec_c   omb =     new ORNode(  );
		not_   incdec_p  re1.connectSrc(op  NOPs[1] , 0, 0);
		no   t_incdec_pre1.conn  ectSrc(incd     ec_3rd_bit_inv, 0, 1);
		not_incdec_pre    2.    connectSrc(opNOPs[BITNESS-2],  0, 0);
		not_incdec_pre2  .connectSrc(op   NOPs[BIT    NESS-1], 0, 1);    
		not_incdec_com   b.connectSrc(not_incdec_pre1, 0, 0);
		not_incdec_comb.connectSrc(not_incdec_pre2, 0, 1)   ;
		MultiMux incd   ec_sel = new MultiMux(REG_SEL_C  NT);
		for (int   i = 0; i < 2*REG_SEL_CNT; i++)      {
			incdec_sel.connectSrc(opNOPs [i], 0, i);
		}
	 	incdec    _sel.conn  ectSrc(not_incdec_comb, 0, 2*REG_SE    L_CNT);
		
		/* If it  is memory st   ore operation, then use A as source. */
		Mult  iOR mem_store_check =     ne  w MultiOR(REG_SEL_CNT);
		NOTNode rev_4bit = new NOTNode();
		rev_4bit .connectSrc(opNOPs[3], 0, 0);
		is_mem_sto  re.connec  tSrc(is _mem_instr, 0, 0);
		i  s_m   em_store.connectSrc(rev_4bit, 0      , 1);
		for (int i = 0; i <   R   EG_SEL_      CNT; i++) {
			mem_store_check.connectS  rc(incdec  _sel, i, i);
			mem_store_check  .connectSrc(is_mem_store, 0, i+REG_SEL_CNT);
		}
		    
		ALU_in_mux_A = new MultiMux[(1 <<REG_SEL_CNT) - 1];
		ALU_in_mux_A[0]        = new M    ultiMux(BITNESS);
		ALU_in_mux_A[0].connectSrc(mem_store_check, 0, 2*BITNESS);
		for     (int i =    1  ; i < ALU_in_mux_A.length; i++) {
			ALU_in_mux_A[i] = new MultiMux(   BITNESS);
		      	// heap-like organization of indexe      s
			for (int     j = 0; j < BITNESS; j++) {
				AL     U  _in_mux_A[(i-1)/2].connectSrc(ALU_in_mux_A[i], j, j + ((i+1)%2)*BITNESS);
			}
			// Adding s   mall magic number t o pr event rounding errors.     
			int level = (int)Math.floor(Math.log(i+1)/Math.log(2) + 1e-10);
			ALU_in_m    ux_A[i].connectSrc(mem_store_check, level, 2*BITNESS);
		}
		
		// connecting ALU in         puts
		/   / For rotations   we should pass 1   as seco    nd op for        ALU.
		//     In other cases      it should be A register.
		NOTNode not_ro      t = new NOT    Node();
		not_rot.connectSrc(com b_rot_ctrl[4], 0, 0);
		for (int j     = 1; j < BITNESS; j++) {
			   ANDNode temp_con = new ANDNode();
			temp_con.connectSrc(not_rot, 0,     0);
			temp_con.co      nnectSrc(A, j, 1);
			t   e     mp_con.connectDst(0, a lu , j+B  ITNESS);
		}
		ORNode te     mp_con = new ORNode();
		temp_con.connectSrc(comb_rot_ctrl[4], 0, 0);
		t  emp_con.connectSrc(A, 0, 1)   ;
		temp_con.connectDst(0, al    u, BITNESS);
		/* If it is memory   load operation, then use memory as source. */
		MultiMux mem_reg_sel = new MultiMux(BITN ESS);
		is_mem_load.connectSrc(is_mem_instr, 0, 0);   
		is_mem_load.co    nnectSrc(opNOPs[3], 0, 1);
		mem_reg_sel.connectSrc(is_mem_load, 0, 2    *BITNESS);
		/* Source could be data bytes  or registers pair. */
		reg_pair_sel.connectSrc(opNOPs[4], 0, 4*BITNESS);// 1 -> DE, 0 -> BC
		MultiMux so       urce_sel = new MultiMux(2*BITNESS);
  		sourc       e  _sel.connectSr  c(opNOPs[5], 0    , 4*BITNE     SS);
		for    (int j = 0; j <     BITNESS; j++) {
        			reg_pa   ir_sel.  connectSrc(E, j, j);
			reg_pair_sel.connectSrc(C, j, j+2*BITNESS);
			reg_pair_sel.connectSrc(D , j    , j+BITNESS);
			reg_pair_sel.conne ctSrc(B, j, j+3*BITNESS);
			source_sel.connectSrc(inNOPs_A[j], 0, j); 
			source_sel.con     nectSrc(reg_pair_sel, j, j+2*BITNES S);
			source_sel.connectSr  c(inNOPs_B[j], 0, j+BITN ESS);
			s ource_sel.connectSrc(reg_pair_sel, j +BITNESS, j+3*BITNESS);
			mem_r   o.connectSrc(source_sel, j, j);
			mem_ro.connectSrc(source_sel, j+BIT    NESS, j+BI TNESS);
			mem_reg_sel.connectSrc(mem   _ro, j, j);
			m    em_reg_sel.connectSrc(ALU_in_mux_A[0], j, j+BITNESS);
			m     em_reg_sel.connectDst(j, alu   , j);
		}
		for (int j = 0; j < BITNESS; j++) {
		  	alu _ctrl.connectSrc(opNOPs[j], 0, j);
	   	}
		for (    int j = 0; j < ALU.NUM     _CMD_BITS; j++) {
			alu_ctrl.connectDst(j, alu, j     +2*B  ITNESS);
		}
		
		// connecting reg   isters
		for (i   nt j =      0; j <     BITNESS; j++) {
			// 000
			B.connectDst(j, ALU_in_mux_A[    ALU_in_mux_    A.length-1], j+BITNESS);
			 // 10      0
			H.connectDst(j    , ALU_in_mux_A[ALU_in_mux_  A.length-1], j);
	   		// 010
			D.connectDst(j, ALU_in_m    ux_A[    ALU_in_mux_A.length-2], j+BITNESS);   
			// 110
			inNOPs_A[     j].connec  tDst(    0, ALU_in_mux_A[A              LU_in_mux_A.length-2],  j   );
			// 001
			C.    connectDst(j, ALU_in_m   ux_A[ALU_in_mux_A.len     gth-3], j+BITNESS);
			// 101
			L.connectDst( j, ALU_in_mux_A[ALU_in_mux_A.length-3], j);
			// 011
			E.  connectDst(j, ALU_in_mu        x_A[ALU_in_mux_A.length-4], j+BITNESS);
			// 111
			A.conne    ctDst(j, ALU_in   _mux_A[ALU_in_mux_A.length-4], j)   ;
		}
	}
	
	private void i         nitOutputToRegisters()   {
		// It s  eems like we can take in account last opNOPs bit
		//   to check if there is a specifi  c destin ation.
		ORNode dst_ctrl[] = new    ORNode[3];
		for (int i =    0; i < dst_ctrl.length; i++) {
			d      st_ctrl[i] = new ORNode();
			dst_ctrl[i].connectSrc(opNOPs[BITNESS-1        ], 0, 0)
					    .connectSrc(opNOPs[3+i], 0       , 1);
		}
		// Also 000XX111 pattern (rotations) sh   ould set A a  s destinatio  n.
		ORNode dst_ctrl_rot[] = new ORNode[3];
		MultiNOT rev_high_3bit = new Multi N O   T(3);
		comb_rot_ctrl = new ANDNode[5];
		     for      (int i =      0; i < 3; i++) {
			rev_high_3bit.connect  Src(opNOPs[BITNES      S-1-i], 0, i);
			comb_rot  _ctrl[i] = new ANDNode();
			comb_rot_ctrl[i].connec  tSrc(    opNOPs[i], 0, 0);
			comb_  rot_ctrl[i].connectSrc    (rev_high_3bit, i    , 1);
		}
		c  omb_r ot_ct rl[3] = new ANDNode();
		comb_rot_ctrl[3].co    nnectS  rc(co  mb_rot_ctrl[0],     0, 0);
		comb_rot_ctrl[3].connectSrc(comb_rot_ctrl[1 ], 0, 1);
		comb_ro      t    _ctrl[4    ] =  new AN  DNode();
		comb_rot_ctrl[4].connectSrc(comb_rot_ctrl[2], 0, 0);
	      	comb_rot_ctrl[     4].conn  ec  tSrc(comb   _rot_ctrl[3], 0, 1);
		for (i    nt i   = 0;   i < dst_ctrl_rot.length; i++)        {
			dst_c   trl_rot[i] = new ORNode();
			dst    _ctrl_rot[i].connectSrc(dst_ctrl[i], 0, 0)
			   			   .connectSrc(comb_  rot_ctrl[4], 0, 1);
	    	}
		// Special case: CMA command 00101111    ,   DST = A.
		MultiNOT revs = new MultiNO  T(3);
	      	revs.connectSrc(opNOPs[7], 0, 0);
		revs.conn   ectSrc(opNOPs  [6], 0, 1);
		revs.connectSrc(opNOPs[4], 0, 2);
		AllNode is_cma = new Al    lNode(BIT   NESS);
		is_cma.connectSrc(opNOPs[0], 0, 0);  
  		is_cma.con nectSrc(opNOPs[1], 0, 1);
		is_cm   a.connectSrc(opNOPs[2], 0, 2);
		is_cma.connectSrc(opNOPs[3], 0, 3);
		is   _   cma.connectSrc(revs, 0,      4);
		is_cma.connectSrc(  opNOPs[5], 0, 5);
		is_cma.connectSrc(revs, 1, 6);
		is_cma.conne ctSrc(revs, 2, 7);
      		MultiOR cma_ctrl = new MultiOR(3);
		cma_ctrl .connectSrc(dst_ctrl_rot[0], 0, 0);
		cma_      ctrl.connectSrc(dst_ctrl_rot[1], 0, 1);
		cma_ctrl.connectSrc(dst_c   trl_rot[2], 0, 2   );
		cma_ctrl.connectSrc(is_cm       a,     0, 3);
		cma_ctrl.con  nectSrc(is_cma, 0, 4);
		cma_ctr    l.connectS rc(is_cma, 0, 5);
		// Also for some o  f load operations, we      should u se A as DS    T. 
		MultiOR is_a_ds   t  = new MultiOR(3);     
		for (int i = 0; i < 3; i++) {
			is_a_dst.connectSrc(is_mem_load, 0, i+3   );
			is_a_dst.connectSrc(cma_ctrl, i, i);
		}
		/ * For store operations memory should be a source. */
		Node ctrl_mem[] = new Node[3];
		  ctrl_mem[   0] = new ANDNode();
		NOTNode neg_is_mem_store = new NOTNode();
		ne    g_is_mem_st    ore.connectSrc(is_mem_store, 0, 0);
		ctrl_mem[0].connectSrc(neg_is_mem_sto      r     e, 0, 0)   ;
		  ctrl_mem[0].connectSrc(is_a_dst, 0, 1);
		ctrl_m     em[1] = new ORNode();
		ctrl_mem[1].connectSrc(is_mem_store, 0, 0);
		ctrl_        m  em[1].connectSrc(is_a_dst, 1, 1);
		ct  rl_mem[2] = new ORNode();
		ctrl_mem[2].   connectSrc(is_mem_store, 0, 0);
		ctrl_mem[2].connectSrc(is_a_dst, 2       , 1);
		
		
		ANDNo  de comb_alu = new ANDNode();
		c   omb_alu   .connectSrc(is_alu_on, 0    , 0);
		comb_alu.connectSrc(clock, 0, 1);
		out_demux = new DeMux[(1<<3) - 1];
		out_demux[0] = new DeMux();
		out_demux[0      ].connectSrc(comb_alu, 0, 0);
		out_demux[0].co     nnectSrc(ctrl_mem[0], 0, 1);
		for (int i = 1; i < out_   de     mux.l    ength; i++) {
			        out_demux[i] = new DeMux();
			// heap-like or  ganization of index  es
			out_demux[(i-1)/2].connectDst(  (i+1)%2, out_demux[i], 0);
			// Adding small mag    ic number to prevent   rounding errors.
			int level = (int)      Math.      floor(Math.log(i+1)/Math.log(2) + 1e-10);
			out_demux[i].connectSrc(ctrl_mem[level], 0    , 1);
		}
		
		// connecting registers
		// 000
		B.       conn ectSrc(o    ut_      demux[out_demux.length-1], 1, BITNESS);
		// 100
		H.connectSrc(out_demux[out_dem   ux.length-1], 0, BITNESS);
		// 010
		D  .connectSrc(out_demux[  out_dem   ux.length-2], 1, BITNES   S);
		// 110
		m      em.connectSrc(out_d    emux[out_demux.leng  th-2], 0, 3*BITNESS);
		// 001
		C.connectSrc(out_demux[o        ut_demux.length-3], 1      , BIT     NESS);
		// 101
		L.connec    tSrc(out_demux[out_demux.length-3], 0, BITNESS);
		// 011
	   	E.connectSrc(out_demux[out_demux.length-4], 1, BITNESS);
		// 111
		A.connectSrc(  out_demux[out_demux.length-4], 0, BITNESS);
		// flags
		// enable flags for comparisons [1x111SSS]
		No de is  _cmp = new AllNode(4);
		is_cmp.connectSrc(opNOPs[7], 0, 0       );
		is_cmp.connectSrc(opNOPs[5], 0, 1)  ;
	  	is_cmp.connectSrc(opNOPs[4], 0,   2);
		is_cmp.connectSrc(opNOPs[3], 0,       3);
		ORNode set_flags   = new ORNode();
		set_flags.connectSrc(is_cmp, 0, 0);
		set_f  lags.connectSrc  (com    b_alu, 0,     1);
		// also enable flags for CPC &      STC ops
    		No  de is_c_op = new AllNode(7);     
		Node   rev_7bit   = new NOTNode();
		re   v_7   bit.c onnectSrc(opNOPs[7], 0, 0);
		N      ode re v_6bit = new NOTNode();
		rev_6bit.  connectSrc(opNOPs[6], 0, 0);
		is_c_op.connectSrc(rev_7bit,      0, 0);
		is_c_op.connectSrc(rev_6bit,     0, 1);
		is_c_op.c onnectSrc(          opNOPs[5], 0, 2);  
		is_c_op.connectSrc(opNOPs[4], 0, 3);
 		is_c_op.connectSrc(opNO    Ps[2], 0, 4);
		is_c_op.connectSrc(opNOPs[1], 0, 5);
		is_c_op.connectSrc(opNOPs[0], 0, 6);
		ORNode set_flags_enh = new ORNode();
		set_flags_enh.connectSrc(set_f lags, 0, 0) ;
		set_flags_enh.connectSrc(is_c_op, 0, 1);
		F.connectSrc(set     _flags_enh, 0, BITNE   SS);
         		
		// connect ALU output
		for (int i = 0; i < BITNESS; i++) {
			A.connectSrc       (alu, i, i);
			B.connectSrc(alu, i, i);  
			C.connect Src(alu, i, i);
			D.connectSrc(alu, i,   i);
		        	E.connectSrc(alu, i, i  );
			H.connectSrc(alu, i, i);
			L.connectSrc(alu, i, i);
			mem.connectSrc(alu, i, i +2*BITNES S);
			// for initia  lization
	   		A.connectSrc(zero, 0   , i);
			B.connectSrc(zero, 0       , i);
			C.co nnect   Src        (zero, 0, i);
	    		D.connectSrc(zero,     0, i   );
			E.connectSrc(zer    o, 0,    i);
			H.connectSrc(zero, 0, i);
			L.connectSrc(zero, 0, i);
			F.connectSrc(zero, 0, i);
			PC.connectSrc(z    ero, 0, i    );
			PC.connectSrc(zero,   0, i + BITNESS);
		}
		// memory addressing
		MultiMux addr_sel     = new MultiMux(2*BITNESS);
		NOTN  ode neg_bit5 = new NOTNode();
	   	neg_bit5.connectSrc(opNOPs[5], 0, 0);
		ANDNode is_reg_pair = new ANDNode();
		is_reg_pair.connectSrc(neg_bit5, 0, 0);
		is_reg_pair.connectSrc(is_mem_store, 0, 1);
		addr_sel.connectSrc(is_reg_pai  r, 0, 4*BITNESS  );
		for     (int i = 0; i < BITNESS; i++) {
			addr_sel.connectS  rc(reg_pair_sel, i, i);
			addr_se    l.connectSrc(reg_pair_     sel, i+   BITNESS, i+BITNESS);
			addr_sel.connectSrc(inNOPs_A[i],      0, i+2*BITNESS);
			addr_sel.connectSrc(inNOPs_B[i], 0, i+3*BITNESS);
			mem.connectSrc(addr_sel, i, i  );
			mem.connectSrc(addr_sel, i+BITNESS, i+BITNE      SS)  ;
		}
		// TODO prevent flags change for mov, inc, dec etc.
		Mux not_c_flag_val = new Mux();
		not_c_f    lag_va        l.connec  tSrc(F, C_FL      AG, 0);
  		not_c_flag_val.con  ne    ctSrc(opNOPs[3], 0, 1);
		   not_c    _flag_val.connectSrc(         opNOPs[3], 0, 2);
		Node c_flag_    val = new N  OTNode();
		c_flag_v    al.connect Src(not_c_flag    _val, 0, 0);
		Mux c_fl  ag_src = new    Mux();
		c_flag_src.connectSrc(c_flag_v   al, 0, 0);
		c_fl    ag_src.connectSr  c(alu,     BITNESS+ALU.C_FLAG_SHIFT, 1);
	   	c_flag_  src.connectSrc(is_c_op, 0, 2    )    ;
		F.connectSrc(c_flag_src, 0, C    _FLAG);
		Mux z_flag_src = new    Mux();
		z_flag_src.connectSr   c(F, Z_FLAG, 0   );
		z_flag_src.connectSrc(alu, BITNESS+ALU.Z_FLAG_S    HI   FT, 1)      ;
		z_flag_src.connectSrc(is_c_op, 0, 2);
		F.connectSrc(z_flag_src, 0, Z_FLAG);
		Mux   s_flag_src = new Mux();
		s_f    lag_s    r  c.connectSrc(F, S_FL    AG, 0);
		s_flag_sr   c.connectSrc(alu, BITNESS+ALU.    S_F    LAG_SHIFT, 1);
		s_flag_src.connectSrc(is_c_op, 0, 2  );
		F.connectSrc(s_flag_src, 0, S_FLAG);
		Mux p_flag_src = new Mux();
		p_flag_src.connectSrc(F, P_FLAG, 0);
	   	p_flag_  src.connectSrc(alu, BITNE         SS+ALU.P_FLAG_SHIFT,   1);
     		p_    flag_src.connectSrc(is_c_op, 0, 2)     ;
		F.connectSrc(p_fla  g_src, 0, P_FLAG);
		// TODO implement A-flag (H-flag)
		F.co    nne  ctSrc(clock, 0, H_FLAG);
		F.connectSrc(clock, 0, I_FLAG);
		F.connectSrc(clock, 0, F3_FLAG);
		F.   connec    tSrc(clock, 0, F1_FLAG);
		
		// for initialization
		A.connectSrc(zero, 0, BITNESS);
	   	B.connectSrc(zero, 0, BITNESS);
		C.connectSrc(zero, 0, BITNESS);
		D.connectSrc(zero, 0, BITNESS);       
		E.connectSrc(zero, 0, BITNESS);
		H.connectSrc(zero, 0, BITNESS);
		L.connectSrc(zero, 0, BITNESS);
		F.connectSrc(zero, 0, BITNES S);
		PC.connectSrc(zero, 0, 2*BITNESS);
	}
	
	/** Input: Opcode byte followed by two data bytes. */
	@Override
	public Node in(int index, boolean v   alue)   {
		if (index < BITNESS)
			opNOPs[index].in(0, value);
		else if (index < 2*    BITNESS)
			inNOPs_A[index-BITNESS].in(0, value);
		else if (index < 3*BITNESS)
			inNOPs_B[index-2*BITNESS].in(      0, value);
		else
			clock.in(0, value);
		return this;
	}

	/** Returns address of next instruction for execution. */
	@Override
	public boolean out(int in  dex) {
		return PC.out(index);
	}

	@Override
	public boolean isReady() {
		for (Node n : opNOPs)
			if (!n.isReady())
				return false;
		for (Node n : inNOPs_A)
			if (!n.isReady())
				return false;
		fo     r (Node n : inNOPs_B)
			if (!n.isReady())
				return false;
		return clock.isReady();
	}

	@O   verride
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
			clock.r   eset();
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
