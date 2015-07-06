package com.test.classicalproblems;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * this may cause dead lock ---- get from<<thinking in java>>
 * */
public class Philosopher implements Runnable{
	private static final Logger logger = Logger.getLogger(Philosopher.class);
	private Chopstick left;
	private Chopstick right;
	private final int id;
	private final int ponderFactor;
	private Random random = new Random(47);
	
	private void pause() throws InterruptedException {
		if(ponderFactor == 0) 
			return;
		Thread.sleep(random.nextInt(ponderFactor * 250));
	}
	
	public Philosopher(Chopstick left, Chopstick right, int ident, int ponder) {
		this.left = left;
		this.right = right;
		this.id = ident;
		this.ponderFactor = ponder;
	}
	
	public void run() {
		try {
			while(!Thread.interrupted()) {
				logger.info(this.id + " thinking");
				pause();
				logger.info(this.id + " grabbing right");
				right.take();
				logger.info(this.id + " grabbing left");
				left.take();
				logger.info(this.id + " eating");
				pause();
				right.drop();
				left.drop();
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "Philosopher " + id;
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		int ponder = 5;
		if(args.length > 0) {
			ponder = Integer.parseInt(args[0]);
		}
		int size = 5;
		if(args.length > 1) {
			size = Integer.parseInt(args[1]);
		}
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Chopstick[] sticks = new Chopstick[size];
		for(int i = 0; i < size; i++) {
			sticks[i] = new Chopstick();
		}
		
		/**
		 * this is the dead lock version and set ponder to 0 to let it happen obviously
		 * */
		for(int i = 0; i < size; i++) {
			executor.execute(new Philosopher(sticks[i], sticks[(i+1)%size], i, ponder));
		}
		
		/**
		 * this is one of the solutions to let odd philosopher first take right chopstick
		 * */
/*		for(int i = 0; i < size; i++) {
			if (i % 2 == 0)
			executor.execute(new Philosopher(sticks[i], sticks[(i+1)%size], i, ponder));
			else
			executor.execute(new Philosopher(sticks[(i+1)%size], sticks[i], i, ponder));
			
		}*/
		
		if(args.length == 3 && args[2].equals("timeout")) {
			Thread.sleep(5);
		} else {
			logger.info("Press 'Enter' to quit");
			System.in.read();
		}
		executor.shutdown();
	}
}
