package com.test.concurrencecollection;

public class EventComaprable implements Comparable<EventComaprable>{
	
	private int thread;
	private int priority;
	
	public EventComaprable(int thread, int priority) {
		this.thread = thread;
		this.priority = priority;
	}
	
	public int getThread() {
		return this.thread;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public int compareTo(EventComaprable e) {
		if (this.priority > e.getPriority()) {
			return -1;
		} else if (this.priority < e.getPriority()) {
			return 1;
		} else {
			return 0;
		}
	}
}
