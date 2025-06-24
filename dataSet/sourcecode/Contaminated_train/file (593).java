package Pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * 连接管理类，用工厂方法生成所有连接池，本身用单例模式
 * 
 * @author Arnold
 * 
 */
public class ConnectionPoolManager {

	// 连接池存放
	public Map<String, ConnectionPool> pools = new Hashtable<String, ConnectionPool>();

	// 初始化
	private ConnectionPoolManager() {
		init();
	}

	// 单例实现Manager
	public static ConnectionPoolManager getInstance() {
		return Singleton.instance;
	}

	// 使用内部类确保线程安全
	private static class Singleton {
		private static ConnectionPoolManager instance = new ConnectionPoolManager();
	}

	// 初始化所有连接池
	public void init() {
		// TODO Auto-generated method stub
		for (int i = 0; i < DBInitInfo.beans.size(); i++) {
			DBbean bean = DBInitInfo.beans.get(i);
			ConnectionPool pool = new ConnectionPoolImp(bean);
			if (pool != null)
				pools.put(bean.getPoolName(), pool);
			System.out.println("Info:Init connection successed ->"
					+ bean.getPoolName());
		}
	}

	// 根据连接池名字获得连接
	public Connection getConnection(String poolName) {
		Connection conn = null;
		if (pools.size() > 0 && pools.containsKey(poolName))
			conn = pools.get(poolName).getConnection();
		else
			System.out.println("Error:Can't find this connecion pool ->"
					+ poolName);
		return conn;
	}

	// 关闭，收回连接
	public void close(String poolName, Connection conn) {
		ConnectionPool pool = getPool(poolName);
		if (pool != null) {
			try {
				pool.releaseConn(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("连接池已经销毁");
				e.printStackTrace();
			}
		}
	}

	// 清空连接池
	public void destory(String poolName) {
		ConnectionPool pool = getPool(poolName);
		if (pool != null)
			pool.destroy();
	}

	//获得连接池
	public ConnectionPool getPool(String poolName) {
		// TODO Auto-generated method stub
		ConnectionPool pool = null;
		if (pools.size() > 0)
			pool = pools.get(poolName);
		return pool;
	}
}
