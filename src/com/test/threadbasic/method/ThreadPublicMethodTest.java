package com.test.threadbasic.method;

import org.apache.log4j.Logger;


public class ThreadPublicMethodTest {	
	private static final Logger logger = Logger.getLogger(ThreadPublicMethodTest.class);	
	private static final int size = 3;
	public static void main(String[] args) {
		
		//when call the run method, the run() functions still runs but with order not concurrently
		//Share the same Thread ID && Name
		for(int i = 0; i < size; i++) {
			Thread test = new Thread( new ThreadTest(i));
			test.setName(new String(String.valueOf(i)));
			test.run();
		}
		
		//Thread ID is generated auto and we could set name manually
		for(int i = 0; i < size; i++) {
			Thread test = new Thread( new ThreadTest(i));
			test.setName(new String(String.valueOf(i)));
			test.start();
		}
		
		//Thread start twice will throw exception
//		test.start();
		
		//Thread join
		//TODO will imply future
	}
}

class ThreadTest implements Runnable{
	private static final Logger logger = Logger.getLogger(ThreadTest.class);
	private int ID ;
	private static final int totalTime = 10;
	
	public ThreadTest(int ID) {
		this.ID = ID;
	}
	@Override
	public void run() {
		try {
			(Thread.currentThread()).sleep(totalTime-ID);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("call method....from Thread ID: " + Thread.currentThread().getId() +
				    " Thread name:" + Thread.currentThread().getName());
	}
	
}
