/**
 * 
 */
package com.test.classicalproblems;

/**
 * @author tangchuanzhou
 * synchronized lock can't release the lock until the critical area finished
 * synchronized lock can't be interrupted too 
 *
 */
public class DeadLockBadDemo {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Object object1 = new Object();
		Object object2 = new Object();
		
		Producer producer = new Producer(object1, object2);
	    Consumer consumer = new Consumer(object1, object2);
		producer.start();
		consumer.start();
		
		producer.interrupt();
	}
}

class Producer extends Thread {
	Object object1;
	Object object2;
	
	Producer(Object o1, Object o2) {
		this.object1 = o1;
		this.object2 = o2;
	}
	public void run() {
		int i = 0;
		while (true && i < 1000) {
			synchronized (object1) {
				synchronized (object2) {
					System.out.println("Producer start " + i++);
				}
			}
		}
	}
}

class Consumer extends Thread {
	Object object1;
	Object object2;
	
	Consumer(Object o1, Object o2) {
		this.object1 = o1;
		this.object2 = o2;
	}
	
	public void run() {
		int i = 0;
		while (true && i < 1000) {
			synchronized (object2) {
				synchronized (object1) {
					System.out.println("Consumer start " + i++);
				}
			}
		}
	}
}
