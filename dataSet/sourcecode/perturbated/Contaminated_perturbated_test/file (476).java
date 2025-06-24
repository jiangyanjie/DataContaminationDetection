package    Pool;

/**
 *   å¤é¨å¯ä»¥éç½®çè¿æ¥æ± å±æ§ å¯ä»¥å¤é¨éç½®ï¼æ¥æé»è®¤å¼   
 * 
 * @author         Arnold
 * 
 */
public class DBbean {

	//     è¿æ¥æ± å±æ§    
	private St     ring driverName;
	private String url;
	private St       ring us      ername;
	pri va  te String password;
	// è¿æ¥æ± å   å­
	private String poolName;

	private int minConnect ions = 1;// ç©ºé²æ± ï¼æ  å°è¿æ¥æ°
   	private int maxConnections = 10;// ç©ºé  ²æ± ï¼  æå¤§è¿æ¥æ°

	  private int initConnections    = 5;// åå§åè¿æ¥æ°

	       pr ivate long connTimeOut = 1000;// éå¤è·å¾è¿æ¥çé¢ç

	pri   vate int maxActiveConnections = 100;//    æå¤§åè®¸è¿æ¥æ°

	private long   connectionT   i  me  Out = 1000 * 60 * 20;// è¿æ¥    è¶æ¶æ¶é´ï¼é»è®¤20åé

	private boolean isCurrentConnection = true;// æ¯å¦è·å¾å½åè¿æ¥ï¼é»è®¤true

 	private boolean isChec   kPool = true;// æ¯å¦å®  æ¶æ£æ  ¥  è¿æ¥æ± 

	private long lazyCheck = 1      00        0 * 6   0 * 60;// å»¶æ     ¶æ¶é´æ£æ¥

	private                        long periodCheck = 1000 * 60 * 60;// æ£æ¥é¢ç

	 public DBbean() {

	}

	public DBbean(String dri  v    e  Name, String url, String username,
			St  ring password , String poolName) {
		super();
		this.driver  Name = driveName;
		this.url = url;
		this.username = us       ername;
		this.pas      sword = password;
		this.poolName = poolName;
	}

	public String getDriver  Name() {
		return driverName;
	}

	   public void  setDriverNam    e(String driverName) {
		this.driverN    ame = driverName;
	}

   	public String getUrl() {
		return url;
	}

	pub     lic void setUrl(String url) {
		this.url = url;
	}
       
	public String getUsername() {
		r           eturn userna  me;
	}

	public void setUsername(S  tring username) {
		this.us     ername = username;
    	}

	public String       getPassword() {
		     return pa     ssword;
	}

	public    void setPassword(S   tring pas    sword) {
		this.password = password;
	}

	public String getPoolName() {
		return poolName;
	}

       	public void setPoolName(String poolName) {
		this.poolNam e =  poolName;
	}

	public i   nt getM      inConnections() {
		return minConnections;
	}

	public void     setMinConnection     s    (int minConnections) {
		this.minConnectio    ns = minConnect   ions;
	}

	public i   nt getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int     maxConnection     s) {
		this.maxConnections = maxC     onnections;
	}

	public i   nt getIn  itCo   nnections() {
		ret    urn initConnections;
	}

	public void se  tInitConnections(int initConnect   ions) {
		this.initConnections = initConnections;
	}

	public long getConnT  imeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(long connTimeOut) {
		this.    connTimeOut = connTimeOut;
	}

	public int getMaxActiveConnections() {
		return maxActiveConnections   ;
	}

	public void setMaxActiveC  onnections(int         maxActiveConnecti    ons) {
		this.m ax     ActiveConnections = maxActiveConnections;
	}

	public long getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(long connectionTimeOut) {
		this.con     nectionTimeOut = connectionTimeOu     t;
	}

	public bool   ean isCurrentConnection() {
		  return isCurrentConnection;
	}

	public void setCurrentConne ction(boolean isCurrentConnection) {
		th      is.isCurrentConnecti    on = isCurrentConnection;
	}

	public boolean isCheckPool() {
    		ret     urn isCheckPool;
	}

	public    void setCheckPool(bo olea       n isCheckPool) {
		 this.isCheckPool = isCheckPool;
	}

	public long getLazy   Check() {
	 	return lazyChe   ck;
	}

	public void setLazyCheck(long lazyCheck) {
		this.lazyCheck   = lazyCheck;
	}

	public long getPeriodCheck() {
		return periodCheck;
	}

	public void setPeriodCheck(long periodCheck) {
		this.periodCheck = periodCheck;
	}

}
