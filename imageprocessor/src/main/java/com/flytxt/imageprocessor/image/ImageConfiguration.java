package com.flytxt.imageprocessor.image;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author shiju.john
 *
 */
public class ImageConfiguration {
	
	public static final String TOTAL_IMAGE_COUNT = "TOTAL_IMAGE_COUNT";
	public static final String IMAGE_HEIGHT = "IMAGE_HEIGHT";
	public static final String IMAGE_WIDTH ="IMAGE_WIDTH";
	public static final String BATCH_SIZE = "BATCH_SIZE";
	
	
	private Map<String,Object> imageConfiguration = null ;
	
	public ImageConfiguration(){
		imageConfiguration = new HashMap<>(8);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void addConfiguration(String key,Object value){
		imageConfiguration.put(key, value);
	}

	/**  @return the imageConfiguration	 */
	public Map<String,Object> getImageConfiguration() {
		return imageConfiguration;
	}
	
	public int getTotalImageCount(){
		return (int)imageConfiguration.get(TOTAL_IMAGE_COUNT);
		
	}

	public int getImageHeight() {
		if(imageConfiguration.containsKey(IMAGE_HEIGHT)){
			return (int)imageConfiguration.get(IMAGE_HEIGHT);
		}
		return 100;
	}
	
	public int getImageWidth() {
		if(imageConfiguration.containsKey(IMAGE_WIDTH)){
			return (int)imageConfiguration.get(IMAGE_WIDTH);
		}
		return 100;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getChannel() {
		return 3;
	}

	/**
	 * 
	 * @return
	 */
	public int getBatchSize() {
		if(imageConfiguration.containsKey(BATCH_SIZE)){
			return (int)imageConfiguration.get(BATCH_SIZE);
		}
		return 5;
	}

	

}
