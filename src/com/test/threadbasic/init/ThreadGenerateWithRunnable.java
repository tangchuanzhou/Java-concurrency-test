package com.test.threadbasic.init;

public class ThreadGenerateWithRunnable implements Runnable{
	public void run() {
		System.out.println("thread generate test");
	}
	public static void main(String[] args) {
		Thread thread = new Thread(new ThreadGenerateWithRunnable());
		thread.start();
	}
}
