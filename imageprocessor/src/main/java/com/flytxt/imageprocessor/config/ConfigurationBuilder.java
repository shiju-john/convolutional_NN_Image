package com.flytxt.imageprocessor.config;

import java.util.Map;

/**
 * 
 * @author shiju.john
 *
 */
public class ConfigurationBuilder  implements Builder<Configuration>{
	

	@Override
	public Configuration build(Map<String,Object> builderProperties) {
		return new Configuration(builderProperties,true);
	}

	@Override
	public Configuration load(Map<String, Object> builderProperties) {
		return new Configuration(builderProperties,false);
	}

	
	
}
