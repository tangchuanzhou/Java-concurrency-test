package com.test.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * this class is used to test the ThreadExecutors--->>>>newCachedThreadPool
 * newCachedThreadPool is suitable for the task that number is limited or the running time is limited
 * */
public class Server {
	private final Logger logger = Logger.getLogger(Server.class);
	private ThreadPoolExecutor executor;
	
	public Server() {
//		executor =(ThreadPoolExecutor)Executors.newCachedThreadPool();
		executor =(ThreadPoolExecutor)Executors.newFixedThreadPool(5);
	}
	
	public void executeTask(Task task) {
		logger.info("Server: A new task has arrived");
		executor.execute(task);
		logger.info("Server:Pool Size is " + executor.getPoolSize());
		logger.info("Server:active count is " + executor.getActiveCount());
		logger.info("Server:task count is " + executor.getTaskCount());
		logger.info("Server: completed Tasks: " + executor.getCompletedTaskCount());
	}
	
	public void endServer() {
		executor.shutdown();
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		for(int i = 0; i < 100; i++) {
			Task task = new Task("Task " + i);
			server.executeTask(task);
		}
		//need to manually stop the executos otherwise they will wait for the task to submit
		server.endServer();
	}
}
