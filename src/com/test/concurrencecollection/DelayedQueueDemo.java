package com.test.concurrencecollection;

import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * this class is used to test the DelayQueue
 * */

public class DelayedQueueDemo implements Runnable {
	private int id;
	private DelayQueue<EventDelayed> queue;
	
	public DelayedQueueDemo(int id, DelayQueue<EventDelayed> queue) {
		this.id = id;
		this.queue = queue;
	}
	
	public void run() {
		Date now = new Date();
		Date delay = new Date();
		delay.setTime(now.getTime() + (id*1000));
		System.out.println("Thread: " + id + " delay: " + delay);
		
		for (int i = 0; i < 100; i++) {
			EventDelayed event = new EventDelayed(delay);
			queue.add(event);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<EventDelayed> queue = new DelayQueue<EventDelayed>();
		Thread threads[] = new Thread[5];
		
		for (int i = 0; i < threads.length; i++) {
			DelayedQueueDemo demo = new DelayedQueueDemo(i+1, queue);
			threads[i] = new Thread(demo);
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		
		do {
			int counter = 0;
			EventDelayed event;
			do {
				event = queue.poll();
				if (event != null) counter++;
			}while(event != null);
			System.out.printf("At %s you have read %d events\n",new Date(),counter);
			Thread.sleep(500);
		}while(queue.size() > 0);
	}
	
}
