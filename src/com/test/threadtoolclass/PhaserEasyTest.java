package com.test.threadtoolclass;

import java.util.concurrent.Phaser;

import org.apache.log4j.Logger;

public class PhaserEasyTest implements Runnable{
	private static final Logger logger = Logger.getLogger(PhaserEasyTest.class);
	private Phaser phaser;
	private String name;
	private String task1;
	private String task2;
	private String task3;
	
	public PhaserEasyTest(String name, Phaser phaser, String task1, String task2, String task3) {
		this.phaser = phaser;
		this.name = name;
		this.task1 = task1;
		this.task2 = task2;
		this.task3 = task3;
	}
	
	public void run() {
		
		if (task1.equals("problem")) {
			logger.info(this.name + " wrong task, other threds goes on, I will leave with no wait");
			phaser.arriveAndDeregister();
			return;
		} else {
			logger.info(this.name + " acomplished the " + task1);
			phaser.arriveAndAwaitAdvance();
		}
		
		if (task2.equals("problem")) {
			logger.info(this.name + " wrong task, other threds goes on, I will leave with no wait");
			phaser.arriveAndDeregister();
			return;
		} else {
			logger.info(this.name + " acomplished the " + task2);
			phaser.arriveAndAwaitAdvance();
		}
		
		if (task3.equals("problem")) {
			logger.info(this.name + " wrong task, other threds goes on, I will leave with no wait");
			phaser.arriveAndDeregister();
			return;
		} else {
			logger.info(this.name + " acomplished the " + task3);
			phaser.arriveAndAwaitAdvance();
		}
		/*
		this.checkValidTask(task1,phaser);
		this.checkValidTask(task2,phaser);
		this.checkValidTask(task3,phaser);
		*/
	}
	
	private void checkValidTask(String task, Phaser phaser) {
		if (task.equals("problem")) {
			logger.info(this.name + " wrong task, other threds goes on, I will leave with no wait");
			phaser.arriveAndDeregister();
			return;
		} else {
			logger.info(this.name + " acomplished the " + task);
			phaser.arriveAndAwaitAdvance();
		}
	}
	
	public static void main(String[] args) {
		Phaser phaser = new Phaser(3);
		PhaserEasyTest task1 = new PhaserEasyTest("thread1", phaser, "test1", "test2", "test3");
		PhaserEasyTest task2 = new PhaserEasyTest("thread2", phaser, "test1", "problem", "test3");
		PhaserEasyTest task3 = new PhaserEasyTest("thread3", phaser, "test1", "test2", "problem");
		new Thread(task1).start();
		new Thread(task2).start();
		new Thread(task3).start();
	}
}
