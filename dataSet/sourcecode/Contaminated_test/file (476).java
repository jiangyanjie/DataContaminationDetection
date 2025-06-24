package Pool;

/**
 * 外部可以配置的连接池属性 可以外部配置，拥有默认值
 * 
 * @author Arnold
 * 
 */
public class DBbean {

	// 连接池属性
	private String driverName;
	private String url;
	private String username;
	private String password;
	// 连接池名字
	private String poolName;

	private int minConnections = 1;// 空闲池，最小连接数
	private int maxConnections = 10;// 空闲池， 最大连接数

	private int initConnections = 5;// 初始化连接数

	private long connTimeOut = 1000;// 重复获得连接的频率

	private int maxActiveConnections = 100;// 最大允许连接数

	private long connectionTimeOut = 1000 * 60 * 20;// 连接超时时间，默认20分钟

	private boolean isCurrentConnection = true;// 是否获得当前连接，默认true

	private boolean isCheckPool = true;// 是否定时检查连接池

	private long lazyCheck = 1000 * 60 * 60;// 延时时间检查

	private long periodCheck = 1000 * 60 * 60;// 检查频率

	public DBbean() {

	}

	public DBbean(String driveName, String url, String username,
			String password, String poolName) {
		super();
		this.driverName = driveName;
		this.url = url;
		this.username = username;
		this.password = password;
		this.poolName = poolName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public int getMinConnections() {
		return minConnections;
	}

	public void setMinConnections(int minConnections) {
		this.minConnections = minConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getInitConnections() {
		return initConnections;
	}

	public void setInitConnections(int initConnections) {
		this.initConnections = initConnections;
	}

	public long getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(long connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public int getMaxActiveConnections() {
		return maxActiveConnections;
	}

	public void setMaxActiveConnections(int maxActiveConnections) {
		this.maxActiveConnections = maxActiveConnections;
	}

	public long getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(long connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public boolean isCurrentConnection() {
		return isCurrentConnection;
	}

	public void setCurrentConnection(boolean isCurrentConnection) {
		this.isCurrentConnection = isCurrentConnection;
	}

	public boolean isCheckPool() {
		return isCheckPool;
	}

	public void setCheckPool(boolean isCheckPool) {
		this.isCheckPool = isCheckPool;
	}

	public long getLazyCheck() {
		return lazyCheck;
	}

	public void setLazyCheck(long lazyCheck) {
		this.lazyCheck = lazyCheck;
	}

	public long getPeriodCheck() {
		return periodCheck;
	}

	public void setPeriodCheck(long periodCheck) {
		this.periodCheck = periodCheck;
	}

}
