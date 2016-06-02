/**
 * Copyright 2016 Zhengbin's Studio.
 * All right reserved.
 * 2016年6月1日 下午9:13:38
 */
package Pool;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import Util.ReadProperties;

/**
 * @author zhengbinMac 1.实现java.sql.DataSource接口 2.重写getConnection()方法
 * 
 *         当用户申请数据库连接时，从连接池中获取（LinkedList<Connection>），返回数据库连接Connection
 *         如果连接池（LinkedList.size==0）为空时，从新申请新的临时连接，并返回连接。
 *         当用户关闭连接时，将数据库连接Connection添加到连接池（LinkedList<Connection>）中。
 */
public class DbcpXPool implements DataSource {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static int initialSize;
//	private static int maxActive;
	private static int againInitialSize;
	private static List<Connection> listConn;

	// 初始化变量
	static {
		driver = (String) ReadProperties.getValue("driver");
		url = (String) ReadProperties.getValue("url");
		username = (String) ReadProperties.getValue("username");
		password = (String) ReadProperties.getValue("password");
		initialSize = Integer.parseInt((String) ReadProperties.getValue("initialSize"));
//		maxActive = Integer.parseInt((String) ReadProperties.getValue("maxActive"));
		againInitialSize = Integer.parseInt((String) ReadProperties.getValue("againInitialSize"));
		System.err.println("获得连接池配置，成功。");
		try {
			// 1.加载驱动程序
			Class.forName(driver);
			// 2.初始化连接池
			listConn = new LinkedList<Connection>();
			for (int i = 0; i < initialSize; i++) {
				Connection connection = DriverManager.getConnection(url, username, password);
				listConn.add(connection);
			}
			System.out.println("初始化连接池，成功。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * initialSize=3 maxActive=20 maxIdle=20 minIdle=3 maxWait=6000
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if (listConn.size() != 0) {
			System.err.println("当前连接池不为空，大小为："+listConn.size());
			final Connection tempConn = listConn.remove(listConn.size() - 1);
			return (Connection) Proxy.newProxyInstance(DbcpXPool.class.getClassLoader(),
					new Class[] {Connection.class}, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							System.out.println("调用Connection的方法");
							// 判断执行方法
							if (method.getName().equals("close")) {
								System.out.println("用户调用Close()方法");
								System.out.println("将连接放回连接池。。。");
								listConn.add(tempConn);
								System.out.println("放回成功，池大小为："+listConn.size());
								return null;
							} else {
								System.out.println("向用户返回一个连接");
								return method.invoke(tempConn, args);
							}
						}
					});
		} else {
			System.err.println("当前连接池为空");
			// 重新申请一组连接
			for (int i = 0; i < againInitialSize; i++) {
				Connection tempConn = DriverManager.getConnection(url, username, password);
				listConn.add(tempConn);
			}
			System.out.println("临时增加连接池数量。");
			System.out.println("增加后，大小为："+listConn.size());
			// 返回连接
			return listConn.remove(listConn.size() - 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
