package com.test.executor;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


/**
 * this class is used for scheculedTask to Test
 * */
public class ScheduledTask implements Callable<String>{
	private static final Logger logger = Logger.getLogger(ScheduledTask.class);
	private String name;
	
	public ScheduledTask(String name) {
		this.name = name;
	}
	
	public String call() {
		logger.info(this.name + " starting at " + new Date());
		return "Hello world";
	} 
	
	public static void main(String[] asrgs){
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
		logger.info("Main: starting at " + new Date());
		for (int i = 0; i < 5; i++) {
			ScheduledTask task = new ScheduledTask("Task " + i);
			executor.schedule(task, i+1, TimeUnit.SECONDS);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Main: ends at " + new Date());
	}
}
