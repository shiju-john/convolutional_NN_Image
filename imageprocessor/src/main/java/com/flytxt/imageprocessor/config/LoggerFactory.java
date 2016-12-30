package com.flytxt.imageprocessor.config;

public class LoggerFactory {

	public static Logger getLogger(Class<?> logClass) {
		return new Logger(logClass);
	}
 
}
