package com.flytxt.imageprocessor;

import com.flytxt.imageprocessor.server.ImageProcessorLifeCycle;

public class Invoker {
	
	
	/**
	 * 
	 */
	public void initalizeImageProcessor(){
		ImageProcessorLifeCycle lifeCycle =  LifeCycleImplementer.getInstance();
		lifeCycle.training();
		lifeCycle.trainingWithTransformation();
		lifeCycle.saveImageProcessor();
	}
	
	/**
	 * 
	 */
	public void loadImageProcessor(){
		LifeCycleImplementer.loadInstance();		
	}
	
	
	/**
	 * 
	 */
	public void loadandTrainImageProcessor(){
		ImageProcessorLifeCycle lifeCycle =  LifeCycleImplementer.loadInstance();
		lifeCycle.training();
		lifeCycle.trainingWithTransformation();
		lifeCycle.saveImageProcessor();
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public String invoke(String path){
		ImageProcessorLifeCycle lifeCycle =  LifeCycleImplementer.getInstance();	
		return lifeCycle.process(path);
	}

}
