package com.test.threadtoolclass;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * this is the video conference class with CountDownLatch and
 * parameter in the number of the current conferences.
 * */
public class Videoconference implements Runnable{
	private static final Logger logger = Logger.getLogger(Videoconference.class);
	private final CountDownLatch controller;
	
	public Videoconference(int number) {
		controller = new CountDownLatch(number);
	}
	
	public void arrive(String name) {
		logger.info(name + " has arrived.");
		controller.countDown();
		logger.info("video conference waiting for " + controller.getCount() + " participant");
	}
	
	public void run() {
		logger.info("video conference: Initialization " + controller.getCount() + " participants");
		try {
			controller.await();
			logger.info("video conference: All the participants have come");
			logger.info("video conference: let's start");
		} catch(InterruptedException e) {
			logger.error(e);
		}
	}
}
