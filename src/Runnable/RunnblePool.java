/**
 * Copyright 2016 Zhengbin's Studio.
 * All right reserved.
 * 2016年6月1日 下午11:54:16
 */
package Runnable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import Pool.DbcpXPool;

/**
 * @author zhengbinMac
 *
 */
public class RunnblePool implements Runnable {
	private static DbcpXPool dbPool = new DbcpXPool();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("获得连接：");
		try {
			Connection connection = dbPool.getConnection();
			System.out.println(connection.isClosed());
			Statement state = connection.createStatement();
			ResultSet resultSet = state.executeQuery("select MAX(id) from film");
			if (resultSet.next()) {
				System.out.println(resultSet.getInt(1));
			}
			Thread thread = new Thread();
			thread.start();
			thread.sleep(1000 * 2);
			System.out.println("休眠结束！1");
			System.out.println("----------开始close");
			connection.close();
			System.out.println("----------完成close");
			thread.sleep(1000 * 2);
			System.out.println("休眠结束！2");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
