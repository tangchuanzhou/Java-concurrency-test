package com.test.threadtoolclass;

import org.apache.log4j.Logger;

/**
 * this is the main class that used to launch the participants
 * */
public class Participant implements Runnable{
	private final Logger logger = Logger.getLogger(Participant.class);
	private Videoconference conference;
	private String name;

	public Participant(Videoconference conference, String name) {
		this.conference = conference;
		this.name = name;
	}
	
	public void run() {
		try {
			Thread.sleep(2000);
		}catch (InterruptedException e) {
			logger.error(e); 
		}
		conference.arrive(name);
	}
	
	public static void main(String[] args) {
		Videoconference conference = new Videoconference(10);
		Thread threadConference = new Thread(conference);
		threadConference.start();
		
		for (int i = 0; i < 10; i++) {
			Participant p = new Participant(conference, "participant " + (i+1));
			Thread t = new Thread(p);
			t.start();
		}
	}
}
