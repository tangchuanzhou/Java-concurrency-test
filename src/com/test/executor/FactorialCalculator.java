package com.test.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * this class is used to test the Callable result of the executors framework
 * the future result will return with each thread's result
 * */
public class FactorialCalculator implements Callable<Integer>{
	private static final Logger logger = Logger.getLogger(FactorialCalculator.class);
	private Integer number;
	
	public FactorialCalculator(Integer number) {
		this.number = number;
	} 
	@Override
	public Integer call() throws Exception {
		int result = 1;
		
		if ( number == 0 || number == 1 ) {
			result = 1;
		} else {
			for (int i = 2; i <= number; i++) {
				result *= i;
				Thread.sleep(20);
			}
		}
		logger.info(Thread.currentThread().getName() + " calculated result is " + result);
		return result;
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
		List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
		Random random = new Random();
		
		/*
		 * submit the task and add the Future to the result list
		 **/
		for(int i = 0; i < 10; i++) {
			FactorialCalculator calculator = new FactorialCalculator(i);
			Future<Integer> result = executor.submit(calculator);
			resultList.add(result);
		}
		
		do {
			logger.info("Main: number of the completed tasks are " + executor.getCompletedTaskCount());
			for (int i = 0; i < resultList.size(); i++) {
				Future<Integer> result = resultList.get(i);
				logger.info("Main: Task is " + result.isDone());
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while(executor.getCompletedTaskCount() < resultList.size());
	
	
	logger.info("Main: Results");
	for (int i = 0; i < resultList.size(); i++) {
		Future<Integer> result = resultList.get(i);
		Integer number = null;
		try {
			number = result.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		logger.info("Main: Task " + i + " result is " + number);
		
	}
	executor.shutdown();
	}
}
