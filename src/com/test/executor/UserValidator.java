package com.test.executor;

import java.util.Random;

import com.sun.istack.internal.logging.Logger;

public class UserValidator {
	private static final Logger logger = Logger.getLogger(UserValidator.class);
	private String name;
	
	public UserValidator(String name) {
		this.name = name;
	}
	
	public boolean validate(String name, String password) {
		Random random = new Random();
		try {
			long duration = (long)(Math.random() * 10);
			logger.info("Validating the user " + name + " during " + random + " seconds");
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			return false;
		}
		return random.nextBoolean();
	}
	
	public String getName() {
		return name;
	}
}
