package com.flytxt.imageprocessor.config;

public class Logger {
	
	private org.slf4j.Logger logger ;
	
	public Logger(Class<?> logClass) {
		this.logger =  org.slf4j.LoggerFactory.getLogger(logClass);
	}

	public void error(String message) {
		logger.error(message);
		
	}

	public void info(String message) {
		logger.info(message);
		
	}

}
