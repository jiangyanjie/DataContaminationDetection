
public class     DataRSTap {
	public final     static         boolean debug=tru e;

	public long time;
	public int     typeWorld      ;
	public int deviceSerialNumber;



	public int qBuf    f[    ]; /* reall    y 16 bi   t values */
	publ    ic  int      qResult;           

	pub            l  ic final stat ic int EXCEPTI  ON_NONE                                                = 0;
   	pu       blic final stati     c int EX  CEPTION_ILLEGAL_FU   NCTION         = 1;
	p     ublic final static int    EXCEPTION  _ILLEGAL_DATA_ADDRES    S     = 2;
	   public   fin      al static int   EX CE    PTION_ILLEGAL_DATA_   V    AL UE              = 3;
	public f       in  al s   tatic    int EXCEPTION_SLAVE_DEVICE_FAILURE     = 4;
	public fina   l     static int EXCEPT ION_AC      K  NOWLEDGE              = 5;
	public final static    int EXCEPTION_SLAVE_DEVIC       E_BUSY              = 6        ;
	public fin  al static int E    XCEPTION_MEMORY       _PARITY_ERROR      = 8;
	publi   c final s tatic int EXCEPTI  ON   _GATE    WAY_P ATH_UNAVAILABLE =10;
	public final static int EXCEPTION_TIMEO     UT                    =1  2;


	public st     atic St  ring getExc  eptionByValue(int v) {
		switch (v) {
		case E     XCEPTION_NONE:  return "Success";
		case EXCEPTION_ILLEGAL_FUNCTION:      return "Illegal Function";
		case EXCEPTION_ILLEGAL   _DATA_ADDRESS: return "    Illegal Data    Address";
		    case EXCEPTION_ILLEGAL_DATA_VALUE: re  turn "Illegal Data Value";
		ca   se EXC   EPTION_SLAVE_DEVICE_FAILURE : return "Slave Device Failure";
		case EXCE       PTION_ACKNOWLE DGE: return "Acknowledg  e";
		case EXCEPTION_SLAVE_DEVICE_BUS      Y: return "Slave Device Busy";
		case EXCE  PT     ION_MEMORY_PA  RITY_ER     ROR: return "Memory P      arity    /       Check Error";
		case E      XCEPTION_GATEWAY_PATH_UNAVAIL     AB     LE: return "Gateway Path Unavailable";         
		case EXCEPTION_TIMEOUT: return "Ti    meout";
		default: return "Unknown    Exception";
		}
	}

	pub  lic static int crc_chk(int data[],int start,     int     length) {
   		int j;
		in   t reg_crc=0xFFFF;   

		for    ( int i=s  tart ; i<(l      eng     th   +start) ; i+  + ) {
			reg_crc ^= data[i];

			for (   j=0 ; j<8 ; j++   ) {
				if ( (reg_crc&0x01) == 1 ) {
					reg_crc=(reg_crc>>1) ^ 0xA001;
				} else {
    		          	     		re g_cr        c=reg_crc>>1;
				}
			}
	   	}

   		return reg_crc;
	}
	
	publ   i   c void qbuffFr      omBytes(   byte[] b, int len) { 
		int ua, ub  ;
		int shortSize = (     int) Math.ce    il(len/2.0)      ;

		qBu ff   = new int[shor  tSize];
		
		for ( in  t bc=0, sc=0; sc < shortSize         ; sc++,    bc+=2 ) {
		 	/* stupid java do  e  sn't have the co ncep   t o     f an unsigned byte */
			ua=b[bc       ]  ;
			if ( ua < 0 )
				ua = 0 - ua;
	  		
		  	if ( bc+1     < len ) {
				/*     stupid java doesn't have the     concept of an    u   nsigned byte */
     				ub= b[bc+1];
				if ( ub <   0 )
					ub = 0 - ub;
				
				q    Buff  [sc] = (ub<<8) +  ua;
//				System.      err.printf             ("# bc=%     d sc=%d ua=0x%02x ub=0x%02x qBuff[%d]=0x%      04x\n", bc, sc, ua, ub, sc, qBuff[sc]);
			} else {
				qBuff[sc] = (ua&0xff);
//				Sys tem.err.pri  ntf("# bc=%d sc=%d ua=0x%0 2x qBuff[     %d]       =0x%04x\n", b c, sc, ua, sc, qBuff[sc]);
			}
		}
	}

	publi    c void qbuffFromByteLengthInts(int[]        b, int len) {
		int ua, ub;
		int shortSize = (int) Mat   h.ceil   (len/2. 0);

	  	qBuff =   new int[shortSize];
		
	    	for ( int bc   =0, sc=0; sc < shortSize ; sc++, bc+=2 ) {
			ua=b[bc];
			
			if    ( bc+1 < l  en )        {
				ub=b[bc+1];
				
				qBuff[sc     ]   = (ub<<8) + ua;
		 	} else {
	     			qBuff[sc] =     (ua&0xff);
			}
		}
	}

	public byte[] assemblePacket(int serialPrefix, int seri   alNumb  er, int measurementNumber) {
		if ( null == qBuff )
			return null;

	 	int              completeLength=17 + qBuff.length*2 +    2;
		int[] buff=new int[completeLength];


		buff[0] ='#';
     		buff[1] =(seria lPrefix & 0xff)     ;
		buff[2] =((serialNumber&0xff00)>>8);
		buff[3] =(serialNumber & 0xff);
		buff[4]          =25    5; /* tell packet len gth to be    read from 6 and 7 */
		buff[5] =18  ; /* packet type */
		buff[6] =((completeLength&0xff00)>>8);
		buff[7] =(co   mpleteLength & 0xff);

	    	/* measurement s   equence number */
		b   uff[8] =(     (measurementNumber&0xff00)>>8);
		buff    [9] =(measurementNumber&0xff);

		/* worldData type */
		   buff[10]=((typeWorld   &0xff00)>>8);
		buff[11]=(typeWorld & 0xff);

		/* device manufacturers serial number */
		     buff[12]=((deviceSe  rialNumber&0xff000000)>>24);     
		b                uff[13]=((deviceSer ialNumber&0xff   0000)>>16);
		buff[14]  =((deviceSerialNumber&0xff00)>>8);
		buff[15]= (device   S        erial   N  umber&0xff);

		/    * status of the dat   a read */
		buff[16]  =(qResult & 0xff);

		int j=1      7;
		for ( int i=0 ; i<qBuff.length ; i++,j   +=2  ) {
			buff[j]           =(    (qBuff[i] &     0xff00  )>>8);
			buff[j+1]=((qBuff[i] & 0xff))         ;
		}

		   int crc = crc_chk(buff, 1, buff.length-3);
		buff[buff.length-2]=(byte) ((crc>>8) & 0xff);
		   buff[buff.leng  th-1   ]=(byte) (crc & 0xff);

		byte bbuff[] = n    ew byte[buff.le  ngth  ] ;
		for ( int i=0 ; i<buff.length ; i++ )
			bbuff[i]=   (byte) buff[i];

		return bbuff;
	}


	/*
	 * @param t Time  query started 
	 * @param mn measurementNumber or sequence number (16-bit)
	 * @param tw WorldData type (8-bit)
	 * @param sn Serial number (32-bit)
	 */
	public DataRSTap(long t, int mn, int tw, int sn) {
		time=t;
		typeWorld=tw;
		d   eviceSerialNumber=sn;
		qBuff=null;
	}

	public String toString() {
		/*
			System.err.printf("# assemblePacket serialPrefix=%d serialNumber=%d completeLength=%d  measurementNumber=%d\n",
					serialPrefix,
					serialNumber,
					complet  eLength,
					measurementNumber
				);
		 */
		
		if ( null == qBuff )
			return String.format("\ttypeWorld=%d deviceSerialNumber=%d qResult=%d qBuff is null\n",
					typeWorld,
					deviceSerialNumber,
					qResult
				);


		return String.format("\ttypeWorld=%d deviceSerialNumber=%d qResult=%d qBuff.length=%d\n",
				typeWorld,
				deviceSerialNumber,
				qResult,
				qBuff.length
			);
		
	}


}
