/**
 * 
 */
package com.test.classicalproblems;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tangchuanzhou
 *
 */
public class DeadLockGoodDemo {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Lock object1 = new ReentrantLock();
		Lock object2 = new ReentrantLock();
		Producer2 producer = new Producer2(object1, object2);
	    Consumer2 consumer = new Consumer2(object1, object2);
		producer.start();
		consumer.start();
		producer.interrupt();
		consumer.interrupt();

	}

}

class Producer2 extends Thread {
	Lock object1;
	Lock object2;
	
	Producer2(Lock o1, Lock o2) {
		this.object1 = o1;
		this.object2 = o2;
	}
	public void run() {
		int i = 0;
		while (true && i < 100) {
			if(object1.tryLock()) {
				if(object2.tryLock()) {
					System.out.println("Producer start " + i++);
					object2.unlock();
					object1.unlock();
					continue;
				}
				object1.unlock();
			}
		}
	}
}

class Consumer2 extends Thread {
	Lock object1;
	Lock object2;
	
	Consumer2(Lock o1, Lock o2) {
		this.object1 = o1;
		this.object2 = o2;
	}
	
	public void run() {
		int i = 0;
		while (true && i < 100) {
			if(object1.tryLock()) {
				if(object2.tryLock()) {
					System.out.println("Consumer start " + i++);
					object2.unlock();
					object1.unlock();
					continue;
				}
				object1.unlock();
			}
		}
	}
}
