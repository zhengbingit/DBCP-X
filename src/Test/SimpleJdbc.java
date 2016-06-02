package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleJdbc {

	private final static String JdbcDriver = "com.mysql.jdbc.Driver";
	private final static String databaseURL = "jdbc:mysql://127.0.0.1:3307/test_ssm";
	private final static String username = "root";
	private final static String password = "950906";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//1. 加载数据库驱动
		Class.forName(JdbcDriver);
		System.out.println("Driver loaded!");

		//2. 建立连接
		Connection connection = DriverManager.getConnection(databaseURL, username, password);
		System.out.println("Database connected!");
		System.out.println(connection.isValid(100));
		System.out.println(connection.isClosed());
		//3. 创建可执行SQL语句的连接
		Statement statement = connection.createStatement();
		System.out.println(connection.isValid(100));
		System.out.println(connection.isClosed());
		//4. 执行SQL语句
		ResultSet resultSet = statement.executeQuery("select MAX(id) from user");
		
		int maxId = 0;
		//5. 返回执行结果
		if(resultSet.next()) {
			maxId = resultSet.getInt(1);
		}
		
		System.out.println(maxId);
		System.out.println(connection.isValid(100));
		System.out.println(connection.isClosed());
//		connection.close();
		System.out.println(connection.isValid(100));
		System.out.println(connection.isClosed());
		Thread th = new Thread();
		th.start();
		try {
			th.sleep(1000 * 6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
