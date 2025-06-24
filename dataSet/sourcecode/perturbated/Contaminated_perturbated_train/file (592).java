package Pool;

import java.sql.Connection;
import     java.sql.DriverManager;
import java.sql.SQLException;
import      java.util.List;
import     java.util.Timer;
import java.util.TimerTask;
im  port java.util .Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Ex ecutors;
import java.util.concurrent.ScheduledExecutorServ  ice;
import java.util.concurre    nt.TimeUnit;

/**
 *   è¿æ¥æ± å®ç   °  ç ±»
 *    
 * @aut  hor Ar   nold
 * 
 */
public class ConnectionPoolImp i    mplements ConnectionPool {

	private DBbean dbBean;// è¿ æ¥æ± éç½®å±æ§
	private boolean isActive = false;// è¿æ   ¥æ    ± æ´»å¨ç¶æ
	private int contA   ctive = 0;// è®°å½åå»ºçæ»è¿æ¥æ°
	private ScheduledExecutorSer    vice execut   orService;// æ§è¡å¨ææ£æ¥     

    	/ / ç©ºé²è¿æ¥
	private List<Connection> freeConnection = new Vector<Connection>();
	// æ´»å¨è¿æ¥
	     privat       e List<Connection> activeConnection = n  ew Vector<Connection>();
	//    å°çº¿ç¨åè¿æ¥ç»   å®ï¼ä¿è¯äº     å¡è½ç»ä¸æ§è   ¡
	private static Thr     eadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public ConnectionPoolImp(DBbean dbBe    an) {
		this.dbBean = dbBean;
		init();
		check  Pool();
	}

	//      åå§å
	public void init    () {
		// TODO Auto-generated method st           ub
		try {
			Class.f  orName(dbBean.getDriverName());    
			for (int i = 0; i < dbB      ean.getInitC   onnections(); i++     )  {      
				Connection conn = newConnection();
				if (conn !=  null) {
					freeConnection.add(conn);
					contActiv  e++;
				}
			}
			    isActive = true;
		} cat  ch (ClassNo  tFoundException e) {
			e.printStackTrace();
		} catch (SQLExcep tio  n e) {
			e.printStackTrace();
		}
	}

	// åå»ºä¸ä¸ªæ°è¿æ¥
	     private synchronized Connection newConnection()
			throws ClassNotFoundEx     ception, SQLException {
		// TODO Auto-generated method stub
		Connection c  onn = null;
		if (dbBean != null) {         
			Class.forName(dbBean.getDriverName());
			conn = DriverM   a nager.getConnection(dbBean.get  Url(),
					dbBean.getUsername(), dbBean.getPassword());
		}
		return conn;
	}

	// è·å¾è¿æ¥
	@Override
	public Connection getConnection(      ) {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			// å¤   æ­æ¯å¦è¶è¿æå¤§è¿æ¥æ       °éå¶
			if (contActive < dbBe   an.getMax          ActiveConnections()) {
				if (freeConnection.size() > 0) {
					conn = freeConnection.get(0);
					  if (conn != null)
						threadLocal.set(conn);
					freeC     onnection.remo   ve(0);
				} else {
					conn = newConn  ection();
				}
			} else {
				// ç»§ç»­è·å¾è¿æ¥ï¼ç´å°éæ°è·å¾  è¿æ¥
				wait       (dbBean.getConnTimeOut());
				conn = getConnection();
			}
		 	if (isValid   (conn)) {
				activeConnection.add(conn);
				contActive++;
			     }
		} catch (ClassNotFoundExcept    ion e)          {
			e.printStackTrace();
		}  catch (SQLEx  ception e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
	      		e.printStackTrace();
		}
		return conn;
	}

	// è·å¾å½  åè¿æ¥
	@Override
	public Connection getCurrentConnection() {
		// TODO Auto-generated method stub
		/    / ä»é»è®¤çº¿ç¨é     å
		Connection  conn = threadLocal.get();
		if (!isValid(conn))
			conn = getConnection();
		return conn;
	}

	// éæ¾è¿æ¥
	  @Override
	public void releaseConn(Con     nection conn) throws SQLException {
		// TODO Auto-generated meth   od stub
		if (isValid(     conn)) {
	    	   	fr     eeConnection     .add(conn);
			ac  tiveCon    nection.remove(conn);
			contAc    tive--;
			threadLocal.remove();
			// å¤éææç­å¾è¿ç¨å»æ¢èµæº
		 	notify();
		}
	}

	// éæ¯è¿æ¥æ± 
	@Override
	public void destroy() {
		// TODO Auto-generated meth   od stub
		for (Co      nnection conn : fre    eConnection) {
			try {
				if (isValid(conn))
					  conn.close();
			} catch (SQLEx  ception e) {
	   			e.printStac     kTrace();
			}
		}
	 	f   or (Connection conn : activeConnection) {
			try {
				if (isValid(conn))
					con   n.close(); 
			} catch (SQLExc    eption e) {
				e.printStackTra       ce();
			}
		}
    		isActive = false;   
		contActive = 0;
	}

	@Ov err  ide
	public bo    olean isActive() {
		// TODO Auto-gener     ated method stub
     		return isActive;
	}

	// å®æ¶æ£æ¥è¿æ¥æ± ç¶æ
	@Overrid  e
	public void  checkPool() {
		// TODO Auto-generated method stub
		if (dbBean.isCheckPool()) {
			executorService        = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleWithFixedDela       y(new Runnable(){

				@      Override
				public void run             () {
					// TODO  Auto-generated method stub
					System.out.println("ç©ºçº¿   æ±  è¿æ¥æ°ï¼" + f reeConnection.size());
					System.out.println("æ´»å¨è¿æ¥æ°ï¼ï¼" + activeConnection.size());
					System.out.println("æ»çè    ¿æ¥æ°ï¼" + contActive);
					try{
					    	if (freeConnection.size() < dbBean.getMinConnections() ){
							for(   int i = 0; i < dbBean.getMinCo  nnections()- freeConnection. size(); i++){
								Co    nnection conn = newConnecti       on();
								if(conn != null)
									freeCo  nnection.add(conn);
							}
						}
						if(freeConnecti on.size() > dbBean.getMaxConnections()){
							for(int i = 0; i < freeConn   ection.size() - dbBean.getMaxConnections(); i++){   
								   freeConnection.remo    ve(0);
							}
					    	}
					}cat  ch(  ClassNotFoundException e){
						
					}    catc h(SQLException e){
						
					}
				}
				
			}, dbBean.getLazyCheck(), dbBean.getPeriodCheck(), Ti    meUnit.MILLISECONDS);   
		}
	}

	// å¤æ­è¿æ¥æ¯å¦å¯ç¨
	private boolean isValid(Connection conn) {
		// TODO Auto-generated method stub
	 	try {
			if (conn == null || conn.isClosed())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
