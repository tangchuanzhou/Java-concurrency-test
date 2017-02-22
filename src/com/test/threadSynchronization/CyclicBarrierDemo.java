/**
 * 
 */
package com.test.threadSynchronization;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

/**
 * @author tangchuanzhou
 *CyclicBarrier just like CountDownLatch but which needs all threads to wait until all threads finish jobs
 */
public class CyclicBarrierDemo {
	private static final Logger logger = Logger.getLogger(CyclicBarrierDemo.class);
	
	private static class Task implements Runnable {
		private CyclicBarrier barrier;
		private int id;
		
		public Task(int id, CyclicBarrier barrier) {
			this.id = id;
			this.barrier  = barrier;
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
			try {
				barrier.await();
				logger.info(Thread.currentThread().getName() + " sleep for " + sleeptime/1000 + " seconds between two barrier");
				barrier.await();				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.info(Thread.currentThread().getName() + " finished");
		}
	}
	
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3, new Runnable(){
			public void run() {
				logger.info("all threads have arrive at the barrier, let's play");
			}
		});
		
		for(int i = 0; i < 3; i++) {
			Thread thread = new Thread(new Task(i, barrier));
			thread.setName("Thread" + String.valueOf(i));
			thread.start();
		}
	}
}
