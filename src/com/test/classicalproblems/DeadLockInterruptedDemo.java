package com.test.classicalproblems;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockInterruptedDemo {
	public static void main(String[] args) {
		Lock object1 = new ReentrantLock();
		Lock object2 = new ReentrantLock();
		Producer3 producer = new Producer3(object1, object2);
	    Consumer3 consumer = new Consumer3(object1, object2);
		producer.start();
		consumer.start();
		producer.interrupt();
		consumer.interrupt();

	}
}

class Producer3 extends Thread {
	Lock object1;
	Lock object2;
	
	Producer3(Lock o1, Lock o2) {
		this.object1 = o1;
		this.object2 = o2;
	}
	public void run() {
		int i = 0;
		while (true && i < 100) {
			try {
				object1.lockInterruptibly();
				object2.lockInterruptibly();
				System.out.println("Producer start " + i++);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				object1.unlock();
				object2.unlock();
			}
		}
	}}

class Consumer3 extends Thread{
	Lock object1;
	Lock object2;
	
	Consumer3(Lock o1, Lock o2) {
		this.object1 = o1;
		this.object2 = o2;
	}
	
	public void run() {
		int i = 0;
		while (true && i < 100) {
			try {
				object1.lockInterruptibly();
				object2.lockInterruptibly();
				System.out.println("Consumer start " + i++);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				object1.unlock();
				object2.unlock();
			}
		}
	}
}
