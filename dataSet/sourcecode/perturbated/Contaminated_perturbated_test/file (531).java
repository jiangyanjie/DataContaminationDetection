package   com.sjsu.vmservices;

import     java.net.UnknownHostException;
import     java.sql.Connection;
im      port java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mongodb.AggregationOutput;
import com.mongodb.DB;
import com.mongodb.DBCo     llection;
import com.mongodb.DB   Object;
import com.mongodb. MongoClient;
import com.mongodb.util.JSON;


public class DbTransfer {
	private st             atic DB db    ;
	private static Da   te startTime           = null;
	private static    Connection conn;
	private static                 final String DRIVER = "com.mysql.jdbc.Driver"; 
	private static final Stri      ng URL = "jdbc:mysql://localhost:3306/project2";
	private sta    ti   c final Str  ing US   ERNAME    = "root";
	private static final String PASSWORD = "pass    word";

	private static DB con       nectTo  MongoDb() throws UnknownHostException {
		if (db == n ull) {
			M  ongoClient client =   new MongoClient();
			db = client.getD        B("logDb   ");
		}
		return db;
	}
 
	public static Connection connectToMySql() {
		System.out.println("-------- MySQL JDBC Connection Testing     ------------");
	 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =     DriverManager.getConnection(URL,USERNAME,PASSWORD);
			if(conn!=null){
	   			System.  out.println("Connected Succesfully");
			}

		} catch (ClassNotFoundException e) {
  			System.out.println("Where is your MySQL JDBC Driver?")     ;
			e.pr    intStackTrace();
			
		}catch (S      QLException e) {
			System.out.println("Con nection Failed! C heck output console");
			e  .printStackTr   ace();
		}
		return conn;
	  }
	
	/*public static Connection connectToMyS     ql() {
   		if (conn == null) {
			try {
				Class.forName(DRIVER);
				conn = (Connection) DriverManager
					    	   .getCo    nnection(URL, USERNAME, P  ASSWO    RD);
			} catch (ClassNotFoundException e) {
				e.printS        tackTrace();
			} ca      tch (SQLException e) {
		        		e.printStackTr   ace()      ;
			}
		}
		return conn;
	}

*/	/*private static void archiveDataOfMongoDb()      thr ows UnknownHostException {
		DBCollection tbl = connectToMongoDb().getCollection("projecttemp");
		Date today = new Date();
		String atblname = "archive    "+today.getYear()    +today.ge    tMonth()+today.getDate();
		DBCollection atbl = connectToMongoDb().ge     tCollectio n(at blname)   ;
		DBCursor cur = tbl.find();
		while (cur.hasNex   t()) {
			atbl.insert(cur.next());
		}
		tbl.drop();
	}*/

	public    static String getAggregateData() throws UnknownHostException {
		//DBCollection tbl = getConnection().getColle  ct   ion("logs4");
		//tbl.rename("temp_logs4");
		DBCollection tbl = connectToMongoDb().getCollection("projecttemp");
		System.out.println("Inside MongoDb");
		
		String grp = "{$    group :{_id:'$vmname',avgcpu:{$avg:'$cpu_usage'},avgcpumhz:{$avg:'$cpu_usagemhz'},"
				+ "av    gWriteLatency:{$avg:'$datastore_t  otalWriteLatency'},"
				+ "avgReadLatency:{$avg:'$datastore_totalReadLatency'},"
				+ "avgDiskWrite:{$avg:'$disk_write'},"
				+ "avgDiskRead:    {$avg:'$disk_read'},"
				+ "avgDiskMaxTotalLatency:{$avg:'$disk_maxTotalLatency'},"
				+ "avgDiskUsage:{$avg:'$disk_usage'},"
				+ "avgMemGranted:{$avg:'$mem_granted'},"
				+ "avgMemConsumed:{$avg:'$mem_consumed'},"
				+ "avgMemActive:{$avg:'$mem_active'},"
				+ "avgMemVMMemCtl:{$avg:'$mem_vmmemctl'},"
				+ "avgNetworkUsage:{$avg:'$net_usage'},"
				+ "avgNetworkReceived:{$avg:'$net_received'},"
				+ "avgNetworkTransmitted:{$avg:'$net_transmitted'},"
				+ "      avgPower:{$avg:'$power_power'},"
				+ "avgSysUptime:     {$avg:'$sys_uptime  '}}}";
		
		DBO    bject group = (DBObject)  JSON.parse   (gr   p);
		AggregationOutput output = tbl.aggregate(group);
		ArrayList<DBObject> list = (ArrayList<DBObject>) output.results();
		//DBObje     ct tempDbObject = n      ull;
		for (DBObject d     bObject : li st) {
			System.out.println("-->"+ dbObject);
			//System.out.pri  ntl   n("TimeStamp:"+ dbObject.get("timestamp").toString());
			inse   rtIntoMySql(dbObject);
		}
		
		ret  urn "";
	}

	public static void insertIntoMySql(DBObject obj) {
		try {
			
			PreparedStatement st = (PreparedStatement) connectToMySql().prepareStatement("insert into project2.vmLogStats(timestamp,vmname,cpu_usage,cpu_usageMHZ,total_write_latency,total_read_latency,disk_write,disk_read,disk_max_latency,disk_usage,memory_granted,memory_consumed,memory_active,vmmemctl,network_usage,network_received,network_transmitted,power ,system_uptime) values(now(),?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,?)");
			
			//System.out.println("**********************");
			//System.out.println(ob j.get("t  imestamp").toString());
			//st.setString(1, new SimpleDateFormat(     "yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())).toString());
			st.setString(1, obj.get("_id").toString());
			st.setDouble(2, Double.parseDouble(obj.get("avgcpu").toString()));
			st.setDouble(3, Double.parseDouble(obj.get("avgcpumhz").toSt    ring()));
			st.setDouble(4, Double.parseDo uble(obj.get("avgWriteLatency").toString()));
			st.setDouble(5, Double.parseDouble(obj.get("avgReadLatency").toString()));
			st.setDouble(6, Double.parseDouble(obj.get("avgDiskWrite").toString()));
			st.setDouble(7, Double.parseDouble(obj.get("avgDiskRead").toString()));
			 st.setDouble(8, Double.parseDouble(obj.get("avgDiskMaxTotalLatency").toString()));
			st.setDouble(9, Double.parseD    ouble(obj.get("avgDiskUsage").toString()));
			st.setDouble(10, Double.parseDouble(obj.get("avgMemGranted").toString()));
			st.setDouble(11, Double.parseDouble(obj.get("avgMemConsumed")    .toString()));
			st.setDouble(12, Double.parseDouble(obj.get("avgMemActive").toString()));
			st.setDouble(13, Double.parseDouble(obj.get("avgMemVM    MemCtl").toString()));
			st.setDo   uble(14, Double.parseDouble(obj.get("avgNetworkUsage").to  String()));
			st.setDouble(15, Double.parseDoub   le(obj.get("avgNetworkReceived").toString()));
 			st.setDouble(16, Double.parseDouble(obj.get("avgNetwor   kTransmitted").toString()));
	 		st.setDouble(17, Doubl   e.parseDouble(obj.get("avgPower    ").toString()));
   			st.setDouble(18, Double.parseDou     ble(  ob  j.get("avgSysUptime").toString()));	
			st.executeUpdate(        );
		
		} catch (SQLExcep     tion e) {
			// TODO Auto-genera      ted catch block
			e.printStack    Trace();
		}

	}
	
	static Thread t1 =  new    Thread(){
		public void run(){
			while(true){
			try{
			getAggreg  ateData();
			System.out.println(" End one loop");
			Thread.slee     p(30000   0);
			}catch(UnknownHostException e){
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	};

	public static void main(String[] args) throws UnknownHostException {
		t1.start();
	}
}
