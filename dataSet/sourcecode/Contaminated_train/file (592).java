package Pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 连接池实现类
 * 
 * @author Arnold
 * 
 */
public class ConnectionPoolImp implements ConnectionPool {

	private DBbean dbBean;// 连接池配置属性
	private boolean isActive = false;// 连接池活动状态
	private int contActive = 0;// 记录创建的总连接数
	private ScheduledExecutorService executorService;// 执行周期检查

	// 空闲连接
	private List<Connection> freeConnection = new Vector<Connection>();
	// 活动连接
	private List<Connection> activeConnection = new Vector<Connection>();
	// 将线程和连接绑定，保证事务能统一执行
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public ConnectionPoolImp(DBbean dbBean) {
		this.dbBean = dbBean;
		init();
		checkPool();
	}

	// 初始化
	public void init() {
		// TODO Auto-generated method stub
		try {
			Class.forName(dbBean.getDriverName());
			for (int i = 0; i < dbBean.getInitConnections(); i++) {
				Connection conn = newConnection();
				if (conn != null) {
					freeConnection.add(conn);
					contActive++;
				}
			}
			isActive = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 创建一个新连接
	private synchronized Connection newConnection()
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		if (dbBean != null) {
			Class.forName(dbBean.getDriverName());
			conn = DriverManager.getConnection(dbBean.getUrl(),
					dbBean.getUsername(), dbBean.getPassword());
		}
		return conn;
	}

	// 获得连接
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			// 判断是否超过最大连接数限制
			if (contActive < dbBean.getMaxActiveConnections()) {
				if (freeConnection.size() > 0) {
					conn = freeConnection.get(0);
					if (conn != null)
						threadLocal.set(conn);
					freeConnection.remove(0);
				} else {
					conn = newConnection();
				}
			} else {
				// 继续获得连接，直到重新获得连接
				wait(dbBean.getConnTimeOut());
				conn = getConnection();
			}
			if (isValid(conn)) {
				activeConnection.add(conn);
				contActive++;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 获得当前连接
	@Override
	public Connection getCurrentConnection() {
		// TODO Auto-generated method stub
		// 从默认线程里取
		Connection conn = threadLocal.get();
		if (!isValid(conn))
			conn = getConnection();
		return conn;
	}

	// 释放连接
	@Override
	public void releaseConn(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		if (isValid(conn)) {
			freeConnection.add(conn);
			activeConnection.remove(conn);
			contActive--;
			threadLocal.remove();
			// 唤醒所有等待进程去抢资源
			notify();
		}
	}

	// 销毁连接池
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		for (Connection conn : freeConnection) {
			try {
				if (isValid(conn))
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (Connection conn : activeConnection) {
			try {
				if (isValid(conn))
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		isActive = false;
		contActive = 0;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return isActive;
	}

	// 定时检查连接池状态
	@Override
	public void checkPool() {
		// TODO Auto-generated method stub
		if (dbBean.isCheckPool()) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleWithFixedDelay(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("空线池连接数：" + freeConnection.size());
					System.out.println("活动连接数：：" + activeConnection.size());
					System.out.println("总的连接数：" + contActive);
					try{
						if (freeConnection.size() < dbBean.getMinConnections()){
							for(int i = 0; i < dbBean.getMinConnections()- freeConnection.size(); i++){
								Connection conn = newConnection();
								if(conn != null)
									freeConnection.add(conn);
							}
						}
						if(freeConnection.size() > dbBean.getMaxConnections()){
							for(int i = 0; i < freeConnection.size() - dbBean.getMaxConnections(); i++){
								freeConnection.remove(0);
							}
						}
					}catch(ClassNotFoundException e){
						
					}catch(SQLException e){
						
					}
				}
				
			}, dbBean.getLazyCheck(), dbBean.getPeriodCheck(), TimeUnit.MILLISECONDS);
		}
	}

	// 判断连接是否可用
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
