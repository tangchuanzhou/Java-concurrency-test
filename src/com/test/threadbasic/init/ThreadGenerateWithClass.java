package com.test.threadbasic.init;

public class ThreadGenerateWithClass extends Thread{
	public void run() {
		System.out.println("thread generate from extends thread");
	}
	public static void main(String[] args) {
		ThreadGenerateWithClass thread = new ThreadGenerateWithClass();
		thread.start();
	}
}
