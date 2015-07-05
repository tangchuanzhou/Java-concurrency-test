package com.test.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;


/**
 * this class is used to test running the multiple threads and handing all the results
 * */
public class ExecutorInvokAllTest implements Callable<Result>{
	private static final Logger logger = Logger.getLogger(ExecutorInvokAllTest.class);
	private String name;
	
	public ExecutorInvokAllTest(String name) {
		this.name = name;
	}
	
	public Result call() {
		logger.info("starting:" + this.name);
		try {
			long duration =(long)(Math.random()*10);
			logger.info(this.name + " waiting " + duration + " seconds for results");
			Thread.sleep(duration);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		int value = 0;
		for(int i = 0; i < 5; i++) {
			value += (int)(Math.random()*100);
		}
		Result result = new Result();
		result.setName(name);
		result.setValue(value);
		logger.info("ends " + this.name);
		return result;
	}
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		List<ExecutorInvokAllTest> taskList = new ArrayList<ExecutorInvokAllTest>();
		for(int i = 0; i < 3; i++) {
			ExecutorInvokAllTest task = new ExecutorInvokAllTest(String.valueOf(i));
			taskList.add(task);
		}
		
		List<Future<Result>> resultList = null;
		try {
			resultList = executor.invokeAll(taskList);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		
		logger.info("Main: printing the results");
		for(int i = 0; i < resultList.size(); i++) {
			Future<Result> future = resultList.get(i);
			try {
				Result result = future.get();
				logger.info(result.getName() + ": " + result.getValue());
			} catch(InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
