package com.flytxt.imageprocessor.config;

import java.util.Map;

/**
 * 
 * @author shiju.john
 *
 * @param <T>
 */
public interface Builder<T> {
	
	
	/**
	 * 
	 * @param builderProperties
	 * @return
	 */
	public T  build(Map<String,Object> builderProperties);
	
	
	/**
	 * 
	 * @param builderProperties
	 * @return
	 */
	public T load ( Map<String, Object> builderProperties) ;

}
