package com.flytxt.imageprocessor.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
/**
 * 
 * @author shiju.john
 *
 */
public class ImageProcessorBuilder implements Builder<ImageProcessor>{
	
	Logger log = LoggerFactory.getLogger(ImageProcessorBuilder.class);
	private static final String FILE_PATH = "src/main/resources/network/network.zip";

	@Override
	public ImageProcessor build ( Map<String, Object> builderProperties) {		
		Configuration configuration  = new  ConfigurationBuilder().build(builderProperties);		
		return new ImageProcessor(configuration);
	}
	
	/**
	 * 
	 */
	public ImageProcessor load ( Map<String, Object> builderProperties) {		
		Configuration configuration  = new  ConfigurationBuilder().load(builderProperties);	
		try {
			MultiLayerNetwork restored = ModelSerializer.restoreMultiLayerNetwork(FILE_PATH);
			return new ImageProcessor(configuration,restored);
		} catch (IOException e) {
			log.error("Unable to load the network"+ e.getMessage());
		}
		
		log.error("going to start new network");
		return this.build(builderProperties);
	}
	
	/**
	 * 
	 * @param imageProcessor
	 */
	public void save (ImageProcessor imageProcessor ) {		
		 File locationToSave = new File(FILE_PATH); 
		 boolean saveUpdater = true;  
		 try {
			ModelSerializer.writeModel(imageProcessor.getMultiLayerNetwork(), locationToSave, saveUpdater);
		} catch (IOException e) {
			log.error("Unable to save the network"+ e.getMessage());			
		}
	}
}
