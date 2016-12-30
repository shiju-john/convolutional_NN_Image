package com.flytxt.imageprocessor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.flytxt.imageprocessor.config.ImageProcessor;
import com.flytxt.imageprocessor.config.ImageProcessorBuilder;
import com.flytxt.imageprocessor.image.ImageConfiguration;
import com.flytxt.imageprocessor.server.ImageProcessorLifeCycle;

public class LifeCycleImplementer implements ImageProcessorLifeCycle{
	
	
	private ImageProcessor processor =  null;
	private static volatile ImageProcessorLifeCycle me =null;
	
	Path traingPath= Paths.get("src/main/resources/dataset");
	
	private LifeCycleImplementer(boolean isloadRequest){
		if(!isloadRequest)
			initImageProcessor();
		else{
			loadImageProcessor();
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static ImageProcessorLifeCycle getInstance(){
		if(me==null){
			synchronized (LifeCycleImplementer.class) {
				me = new LifeCycleImplementer(false);
			}
		}
		return me;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static ImageProcessorLifeCycle loadInstance(){
		if(me==null){
			synchronized (LifeCycleImplementer.class) {
				me = new LifeCycleImplementer(true);
			}
		}
		return me;
	}
	
	
	private ImageConfiguration getImageConfiguration() {
		ImageConfiguration imageConfig = new ImageConfiguration();
		imageConfig.addConfiguration(ImageConfiguration.TOTAL_IMAGE_COUNT, 576);
		imageConfig.addConfiguration("BATCH_SIZE",50);
		return imageConfig;
	}
	
	

	private Map<String, Object> prepareConfiguration() {
		Map<String, Object> builderProperties = new HashMap<>();
		builderProperties.put("OUT_LABEL_COUNT",6);
		builderProperties.put("EPOCHS",60);
		return builderProperties;
	}


	@Override
	public ImageProcessor initImageProcessor() {
		processor = new ImageProcessorBuilder().build(prepareConfiguration());
		return processor;
	}


	@Override
	public void training() {
		//Path path= Paths.get("src/main/resources/animals");
		
		//Path path= Paths.get("src/main/resources/dataset");
		try {
			processor.addTrainingData(null,traingPath, getImageConfiguration());
			processor.train();
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}


	@Override
	public void trainingWithTransformation() {
		try {
			processor.trainWithTransformation(getImageConfiguration());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public String process(String path) {
		Path testpath= Paths.get(path);
		try { 
			return processor.evaluate(traingPath,testpath,null,getImageConfiguration());
		} catch (IOException e) {
			return "Error While Processing The data";
		}
	}


	@Override
	public ImageProcessor loadImageProcessor() {
		processor = new ImageProcessorBuilder().load(prepareConfiguration());
		return processor;
		
	}


	@Override
	public void saveImageProcessor() {
		new ImageProcessorBuilder().save(processor);
		
	}

	

}
