package com.test.executor;

import java.util.Date;

import org.apache.log4j.Logger;


public class Task implements Runnable{
	private final static Logger logger = Logger.getLogger(Task.class);
	private Date initDate;
	private String name;
	
	public  Task(String name) {
		initDate = new Date();
		this.name = name;
	}
	
	public void run() {
		logger.info("task "+ Thread.currentThread().getName() + "created " + initDate);
		logger.info("task "+ Thread.currentThread().getName() + "started " + new Date());
		try {
			Long duration = (long)(Math.random()*10);
			logger.info("doing a task during " + duration + " seconds");
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("task " + Thread.currentThread().getName() + " finished on " + new Date());
	}
}
