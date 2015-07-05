package com.test.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;


/**
 * this class is used to test the Executors Framework to return any first returned result
 * */
public class TaskValidator implements Callable<String>{
	private static final Logger logger = Logger.getLogger(TaskValidator.class);
	private UserValidator validator;
	private String user;
	private String password;
	
	public TaskValidator(UserValidator validator, String user, String password) {
		this.validator = validator;
		this.user = user;
		this.password = password;
	}
	
	public String call() throws Exception{
		if (!validator.validate(user, password)) {
			logger.info("the user not found: " + this.user);
			throw new Exception("error validating user");
		}
		logger.info("the user has been found:" + this.user);
		return validator.getName();
	}
	
	public static void main(String[] args) {
		String username="test";
		String password="test";
		
		UserValidator ladpValidator = new UserValidator("LDAP");
		UserValidator dbValidator = new UserValidator("Database");
		
		TaskValidator ldapTask = new TaskValidator(ladpValidator, username, password);
		TaskValidator dbTask = new TaskValidator(dbValidator, username, password);
		
		
		List<TaskValidator> taskList = new ArrayList<TaskValidator>();
		taskList.add(ldapTask);
		taskList.add(dbTask);
		
		ExecutorService executor =(ExecutorService)Executors.newCachedThreadPool();
		String result;
		
		try {
			result = executor.invokeAny(taskList);
			logger.info("Main: result is " + result);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}
		
		executor.shutdown();
		logger.info("Main: end of the execution");
	}
}
