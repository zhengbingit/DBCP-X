/**
 * Copyright 2016 Zhengbin's Studio.
 * All right reserved.
 * 2016年6月1日 下午9:40:21
 */
package Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhengbinMac
 *
 */
public class ReadProperties {
	private final static String filePath = "/dbcpX.properties";
	private static Properties prop;
	private static InputStream in;
	static {
		prop = new Properties();
		in = Object.class.getResourceAsStream(filePath);
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	
	public static Object getValue(String key) {
		Object result = prop.getProperty(key);
		return result;
	}
	
}
