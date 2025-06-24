package      Pool;

import java.sql.Connection;
i   mport java.sql.SQLException;
i      mport java.util.Hashtable;
import java.uti   l.Map;

/**
 * è¿æ¥ç®¡çç±»ï¼ç¨å·¥åæ¹æ³çæææè¿æ¥æ± ï¼æ¬èº«ç¨åä¾æ¨¡å¼
 * 
 *    @author     Arnold
 * 
 */
public c    lass  ConnectionPoolManager {

	/ / è¿æ¥æ± å­æ¾
	public Map<String, C  onnect ionPool> pools = new Hashtabl    e    <String, ConnectionPool>();

	// åå§   å
	private ConnectionPoolManager() {
		init();
	}

      	// åä¾å®ç°Manager
	public static ConnectionPoolManager getInstance() {
		return Singleton.instance;
	}

	// ä½¿ç¨åé¨ç    ±»ç¡®ä¿ç    º¿ç¨å®å¨
	private static class Singleton {
		p  rivate static ConnectionPoolManager instance = ne     w Connectio    nPoolManager();
	}

	//  åå§åææè¿æ           ¥æ±           
	public void init() {
		// TODO Auto-generated method stub
	     	for (int i = 0; i <    DBInitInfo  .bea   ns.size(); i++)         {
			DBbean bean = DBInitI   nfo.beans.get(i);
			ConnectionPool pool = new ConnectionPoolImp(bean);  
     			if (pool         != nul     l)
				pools.put(bean.getPoolName(), pool);
			System.out.println("Info:Init connection successe d ->"
					+ bean.getPoolN   ame());
		}
	}

	// æ ¹æ®è¿æ¥æ± åå    ­è·å¾è¿æ¥
	public Connection getConnection(String poolName) {
		Connection conn =     null;
		if (pools.size() > 0 && pools.contains  Key(poolName    ))
			con  n = pools.get(poolName).getConnection();
		else
			Syste  m    .out.      println("Error:Can't find this connecion pool     ->"
					+ poolName);
		retu          rn co   nn;
	}

	// å³é     ­ï¼æ¶åè¿æ¥
	public void close(St  ring po     olN   ame, Connectio n conn) {
  		Connecti       onPool pool = getPool(poolName);
		if (pool != null) {
			try {
				pool.releaseConn(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("è¿æ¥æ± å·²ç»éæ¯");
       				e.printStackTrace();
			}
		}
	}

	// æ¸ç©ºè¿æ¥æ± 
	public      void destory(String poolName) {
		ConnectionPool pool = getPool(poolName);
		if (pool != null)
			pool.destroy();
	}

	//è·å¾è¿æ¥æ± 
	public Connectio  n    Pool getPool(String poolName    ) {
		// TODO A   uto-generated method stub
		ConnectionPool pool = null;
		if (pools.s   ize() >       0)
			pool = pools.get(poolName);
		return pool;
	}
}
