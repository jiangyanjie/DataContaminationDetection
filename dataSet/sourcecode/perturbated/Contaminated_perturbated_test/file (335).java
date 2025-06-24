package   com.sb.core.cpu;

import     java.util.Hashtable;
     import org.apache.log4j.Logg er;

import com.sb.core.inst.Instruction;
im      port com.sb.core.register.Register;

public clas      s      DataHazard {

	// Single instanc    e.
	private static   DataHazard instance = null;

   	private  static  H   ashtable<Integer, Re  serveBlock> WawB     lock = null;      
	private static Hashtable<Integer, Reser      veBlock>      RawBlock = null;
	private s       tatic Hashtable<Integer    , ReserveBlock> WarBlock = null;
	Logger log = Logger.getLogger(this.getClass(  ).g  etName());

	public DataHazard() {
		WawBlock = new    Hashtable<Integer, ReserveBlock>();
		  RawBlock = new Hashtable<Integer, ReserveBlock>();
  		WarBlock = new Hashtable  <Integer, ReserveBlock>();
	}

	public st atic DataHaza rd getInstance  () {
		if (instance == null) {
			instance = new DataHazard();
		}
		return instance;
	}

	// Data pop  ulation functions  .
	public boolean addToWawBlock(Reg ister reg, Instruct     ion inst) {

		ReserveBlock rblk = null;
		b  oolean   r    esult = fals e;
		
		if (!WawBlock.containsKey(reg.hashCode())) {
			rblk = new ReserveBlock(reg.getName(),   inst);
			WawBlock.  put(reg .hashCode(), rblk);
			log.debug("Reg  ist  er: " + reg.getName()   + " added in WAW         Block b    y "
	     				+ inst.getOpcode() + "[" + ins    t.getOrder() + "]");
			re      su  lt = true;
		} else {
			if ((rblk = WawBlock.get(reg.hashCode())) != null) {
				  S     tring opCode     = rblk.getReservedBy().getOpcode().name(   );
		  		int  order = rblk.get R   eserv edBy().getOrder();
				
				log.debug("Regist    er: " + reg.getName()
		     				+ " alr    eady in WAW Block; adde    d by " + opCode + "["
		    				+ order + "]");
				result = false;
			}
		}
		
		return result;
	}

	public boolean removeFromWa wB   lock(Register reg)      {
		WawBlock.remove(reg.hashCode());
		   return true;
	     }

	public boolean addToRawBlock(Register     reg, Instructio        n inst) {

		ReserveBlock rblk = null;
		boolean result = false;
		
		i   f (!Ra      wBlock.con  tain   sKey(reg.hashCode())) {
			rblk = new ReserveBlock(reg.getName(), in st);
			RawBlock.put(r   eg.hashCode     (), rblk);
			log.debug   (         "Re  gister: " + reg.getName() + " a dded in RA   W Block by "
					+ inst  .getOpcode() + "[" + inst.getOrder() + "]");
			result = true;
		} else {
			if ((rblk = RawBlock.g et(reg.hash    Code())) != null) {
				String opCode = r        blk.  getReservedBy().getOpcode().name();
				int order = rblk.getReservedBy().getOr   der();
				
				lo  g.debug("Register: " + reg.getName()
						+ " alre       ady in RAW Block; a   dded by " + opCode + "["
						+ or der + "]");
				result =  false;
			}
		}
		
		return result;
	}

	public boolean removeFromRawBlock(Register reg       ) {         
		RawBlock.remove(reg.hashCode());
		retu    rn true;
	}

	public boolean ad dToWarBlo ck(Register reg   , Instruction inst) {
     
		Res    erveBlock rblk = null;
		boolean result = false;
		
	 	i    f (!WarBlock.co  ntainsKey(reg.ha shCode())) {
			rblk = new ReserveBlock(reg.ge tName(), inst);
			WarBl   oc   k.put(reg.hashCode(), rblk);
			log.debug("  Regist   er: " + reg.getName(  ) +   "     added in W   AR Block by "
					+ inst.getO    pcode() + "[         " + in    st.getOrder() + "]");
			result = true;
		} else {
			if ((rblk = WarBlock.get(reg.hashCode())) != null) {
				St ring opCode = rblk.getReservedBy().getOpcode().name();
				int order = rblk.getReservedBy().getOrder()  ;        
		 		
				log.debug("Register: " + reg.getName()     
						+ " alre  ady in WAR Block; added by " + opCode + "["     
 						+    order + "]");
				res      ult = false;
			}
		}
		
		return result;
	}

	public boolean removeFromWarBlock(Re  giste   r reg) {
		WarBlock.remove(reg.hashCode());
	    	  return true;
	}

	// Check for dat   a hazards.
	pub  lic        boolean checkWAWHaza rd    (Register reg   , Instruction   ins    t) {

		ReserveBlock rb   lk = null;

		if (WawBlock.containsKey(reg.hashCode  ())) {
			if ((rblk = WawBlock.get   (reg.hashCode    ())) != null) {
				if (r  blk.getRegName().  equals(reg.g     etName())
						&& rblk.getReservedBy().hashCode() != inst.hashCode() ) {

					l   og.debug("WA   W Haza             rd found - Register "
							+ rblk.getRegNa    me() + " res   erved b      y "   
							+ rblk.getReservedBy().getOpcod        e() + "["
	  						+ rblk.getReservedBy().getOrder()      + "]");

    					return true;
				}
			}
		}

		log.debug("WAW Hazard not found - reserve d   by requesting instruction");
		return false;
	}

	publ  ic boolean checkRAWHazard(R  egister        reg, Instruction   i    nst    ) {

	      	ReserveBlock rblk = n   ull;

		if (RawBlock.containsKey  (reg.   ha shCode  ())) {
			if ((rblk = RawBlock.get(reg.h ashCode())) != null) {     
				if (rblk.getRegName().equals(reg.get     Name())
					    	&& rblk.getReservedBy().hashCode() != inst.hashCode() 
					     	&& rblk.getReservedB   y().getOrder() < inst.getOrder()) {

					log.debug("RAW Hazard found - Register "
		    					+ rblk.getRe     gName() + " reserved by "
							+ rblk.getReserve dBy().getOpcode() +     "["
							+ rblk.getReservedBy().getOrder() + "]");

					return true;
				}
			}
		}

		log.debug("RAW Hazard n ot found - reserved by requesting instruction");
		            return false;
    	}

	public boolean checkWARHazard(Register reg, Ins    truc    tion inst )     {

	   	ReserveBl   ock rblk = null;

		if (WarBlock.containsKey(reg     . hashCode())   ) {
			if ((rblk = WarBlock.get(reg.hashCode())) != null) {
				if (rblk.getRegName(    ).equals(reg.getName())
						&& rblk.getReserve     dBy().hashCode() != inst.hash    Code()) {

					log.debug("WAR Hazard found - Register "
  							+ rblk.getRegName() + " reserved by "
							+ rblk.getReservedBy().getOpcode() + "["
							+ rblk.getReservedBy().getOrder() + "]");

					return true;
				}
			}
		}

		log.debug("WAR Hazard not found - reserved by requesting instruction");
		return false;
	}
}
