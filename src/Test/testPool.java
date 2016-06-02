/**
 * Copyright 2016 Zhengbin's Studio.
 * All right reserved.
 * 2016年6月1日 下午11:21:11
 */
package Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Runnable.RunnblePool;

/**
 * @author zhengbinMac
 */
public class testPool {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new RunnblePool());
		Thread t2 = new Thread(new RunnblePool());
		Thread t3 = new Thread(new RunnblePool());
		Thread t4 = new Thread(new RunnblePool());
		Thread t5 = new Thread(new RunnblePool());
		t1.start();
		System.out.println("开始1");
		t2.start();
		System.out.println("开始2");
		t3.start();
		System.out.println("开始3");
		t4.start();
		System.out.println("开始4");
		t5.start();
		System.out.println("开始5");
		Thread t = new Thread();
		t.start();
		t.sleep(1000 * 4);
	}

}
