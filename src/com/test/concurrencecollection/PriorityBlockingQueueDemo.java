package com.test.concurrencecollection;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * this class is used to test the PriorityBlockingQueue 
 */

public class PriorityBlockingQueueDemo implements Runnable{
	private int id;
	private PriorityBlockingQueue<EventComaprable> queue;
	
	public PriorityBlockingQueueDemo(int id, PriorityBlockingQueue<EventComaprable> queue) {
		this.id = id;
		this.queue = queue;
	}
	
	public void run() {
		for(int i = 0; i < 1000; i++) {
			EventComaprable event = new EventComaprable(id,i);
			queue.add(event);
		}
	}
	
	public static void main(String[] args) {
		PriorityBlockingQueue<EventComaprable> queue = new PriorityBlockingQueue<EventComaprable>();
		Thread taskThread[] = new Thread[5];
		for (int i = 0; i < taskThread.length; i++) {
			PriorityBlockingQueueDemo task = new PriorityBlockingQueueDemo(i,queue);
			taskThread[i] = new Thread(task);
		}
		
		for (int i = 0; i < taskThread.length; i++) {
			taskThread[i].start();
		}
		
		for (int i = 0; i < taskThread.length; i++) {
			try {
				taskThread[i].join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Main: Queue Size: " + queue.size());
		for ( int i = 0; i < taskThread.length; i++) {
			EventComaprable event = queue.poll();
			System.out.println("Thread: " + event.getThread() + " priority: " + event.getPriority());
		}
	}
}
