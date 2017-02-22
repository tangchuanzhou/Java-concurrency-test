/**
 * 
 */
package com.test.threadSynchronization;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * @author tangchuanzhou
 * CountDownLatch is used for one or more threads waiting for other threads to finish their jobs
 * but after other threads finish their jobs ther are free to go.
 */
public class CountDownLatchDemo {
	private static final Logger logger = Logger.getLogger(CountDownLatchDemo.class);
	
	private static class Task implements Runnable {
		private CountDownLatch latch;
		private int id;
		
		public Task(int id, CountDownLatch latch) {
			this.id = id;
			this.latch = latch;
		}
		
		public void run() {
			logger.info(Thread.currentThread().getName() + " start working");
			long sleeptime = (long)(Math.random()*10000);
			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(Thread.currentThread().getName() + " sleep for " + sleeptime/1000 + " seconds");
			latch.countDown();
			
			//let thread id 2 wait with main thread together
			if(2 == id) {
				try {
					latch.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info(Thread.currentThread().getName() + " has complished the waiting latch");
			}
			logger.info(Thread.currentThread().getName() + " finished");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(3);
		for(int i = 0; i < 3; i++) {
			Thread thread = new Thread(new Task(i, latch));
			thread.setName("Thread" + String.valueOf(i));
			thread.start();
		}
		latch.await();
		logger.info("count latch finished");
	}
}
