package com.flytxt.imageprocessor.server;

import com.flytxt.imageprocessor.config.ImageProcessor;

public interface ImageProcessorLifeCycle {
	
	/**
	 * 
	 * @return
	 */
	public ImageProcessor initImageProcessor();
	
	
	
	/**
	 * 
	 */
	public void training();
	
	/**
	 * 
	 */
	public void trainingWithTransformation();
	
	
	/**
	 * 
	 * @return
	 */
	public String process(String path);
	
	
	/**
	 * 
	 * @return
	 */
	public ImageProcessor loadImageProcessor();
	
	/**
	 * 
	 * @return
	 */
	public void saveImageProcessor();
	
	

}
