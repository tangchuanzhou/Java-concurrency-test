package com.test.classicalproblems;

/**
 * chopstick that only can be taken by one
 * */
public class Chopstick {
	private boolean taken = false;
	public synchronized void take() throws InterruptedException {
		while(taken) {
			wait();
		}
		taken = true;
	}
	
	public synchronized void drop() {
		taken = false;
		notifyAll();
	}
 }
