package com.test.concurrencecollection;

import java.util.concurrent.SynchronousQueue;

/**
 * this class is used to test the SynchronousQueue this queue hold no data and
 * have to wait until another thread to get or take
 * */

public class SynchronousQueueDemo {

	public static void main(String[] args) {
	final SynchronousQueue<String> queue = new SynchronousQueue<String>();
	
	Thread Producer = new Thread("Producer") {
		public void run() {
			String test = "for test";
			System.out.println("put into the synchronous queue: " + test);
			try {
				queue.put(test);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	Producer.start();
	
	Thread Consumer = new Thread("Consumer") {
		public void run() {
			try {
				String test = queue.take();
				System.out.println("get from the synchronous queue: " + test);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	Consumer.start();
	
	}
}
