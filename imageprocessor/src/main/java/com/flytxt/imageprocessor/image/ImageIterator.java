package com.flytxt.imageprocessor.image;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.datavec.image.recordreader.ImageRecordReader;
import org.datavec.image.transform.FlipImageTransform;
import org.datavec.image.transform.ImageTransform;
import org.datavec.image.transform.WarpImageTransform;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.datasets.iterator.MultipleEpochsIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;

import com.flytxt.imageprocessor.config.Configuration;
import com.flytxt.imageprocessor.config.ImageProcessor;

public class ImageIterator {
	
	private ImageRecordReader recordReader = null;	
	private RecordReaderDataSetIterator dataIterator  = null;
	
	private DataNormalization scaler = null;
	private ImageSplit imageSplit = null;
	
	private final ImageProcessor processor;
//	private final ImageConfiguration imageConfig;
	
	
	/**
	 * 
	 * @param processor
	 * @param imageConfig
	 */
	public ImageIterator(ImageProcessor processor){
		this.processor = processor;		
	}
	
	
	/**
	 * initialize
	 * @throws IOException 
	 * 
	 */
	public void initialize(String imageLabel, Path path,ImageConfiguration imageConfig) throws IOException{
		
		initializeInputData(imageLabel,path,imageConfig,processor);
		initializeImageReader(imageConfig);				
		dataIterator = new RecordReaderDataSetIterator(recordReader, 
							imageConfig.getBatchSize(),	1, getLabelCount()); 	
		initializeScaler();		
		dataIterator.setPreProcessor(scaler);
		
	}
	
	/**
	 * 
	 * @param config
	 * @throws IOException
	 */
	private void initializeImageReader(ImageConfiguration config) throws IOException {
		recordReader = new ImageRecordReader(config.getImageHeight(), config.getImageWidth(), 
								getConfiguration().getChannel(), imageSplit.getLabelGenerator());
		recordReader.initialize(imageSplit.getImageSplit(), null);		
	}

	/**
	 * 
	 * @return
	 */
	private Configuration getConfiguration() {
		return processor.getConfiguration();
	}

	/**
	 * @param imageLabel
	 * @param path
	 * @param config
	 * @param processor
	 */
	private void initializeInputData(String imageLabel,Path path,ImageConfiguration config,ImageProcessor processor) {
		ImageSplitter<ImageSplit , ImageConfiguration>  imageSplitter = ImageSplitterFactory.getDefaultImageSpliter(imageLabel, processor);
		imageSplit = imageSplitter.getImageSplit(path, config);		
	}


	/**
	 * 
	 */
	private void initializeScaler() {
		scaler = new ImagePreProcessingScaler(0, 1);
		scaler.fit(dataIterator);		
	}
	
	/**
	 * 
	 * @return
	 */
	private int getLabelCount() {
		return processor.getConfiguration().getOutLabelCount();
	}

	
	/**
	 * 
	 * @return
	 */
	public RecordReaderDataSetIterator getDataIterator() {
		return dataIterator;
	}


	
	/**
	 * 
	 * @param evalImageSplit
	 * @param imageConfig
	 * @return
	 * @throws IOException
	 */
	public RecordReaderDataSetIterator processWithEvalData(ImageSplit evalImageSplit, 
					ImageConfiguration imageConfig) throws IOException { 
		ImageRecordReader recordReader = new ImageRecordReader(imageConfig.getImageHeight(), imageConfig.getImageWidth(), 
				getConfiguration().getChannel(), evalImageSplit.getLabelGenerator());	
		
		recordReader.initialize(evalImageSplit.getImageSplit());
		RecordReaderDataSetIterator dataIterator = new RecordReaderDataSetIterator(recordReader, 
								imageConfig.getBatchSize(), 1,getLabelCount());//----------------------
	
		
		DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
		scaler.fit(dataIterator);		
        dataIterator.setPreProcessor(scaler);
        return dataIterator; 
       
	}
	
	
	
	/**
	 * Data Setup -> transformation
	 *  - Transform = how to transform images and generate large dataset to train on
	 * @param multiLayerNetwork 
	 **/
	public void processWithTransformation(ImageConfiguration imageConfig, MultiLayerNetwork multiLayerNetwork) throws IOException {

        ImageTransform flipTransform1 = new FlipImageTransform(processor.getConfiguration().getRandom());
        ImageTransform flipTransform2 = new FlipImageTransform(new Random(123));
        ImageTransform warpTransform = new WarpImageTransform(processor.getConfiguration().getRandom(), 42);
       // ImageTransform colorTransform = new ColorConversionTransform(processor.getConfiguration().getRandom(), COLOR_BGR2YCrCb);
        List<ImageTransform> transforms = Arrays.asList(new ImageTransform[]{flipTransform1, warpTransform, flipTransform2});
            
        for (ImageTransform transform : transforms) {
			 System.out.print("\nTraining on transformation: " + transform.getClass().toString() + "\n\n");
	         recordReader.initialize(imageSplit.getImageSplit(), transform);
	         dataIterator  = new RecordReaderDataSetIterator(recordReader, imageConfig.getBatchSize(), 1, getLabelCount());
	         scaler.fit(dataIterator);
	         dataIterator.setPreProcessor(scaler);
	         MultipleEpochsIterator trainIter = new MultipleEpochsIterator(processor.getConfiguration().getEpochs(), 
	  				dataIterator, Runtime.getRuntime().availableProcessors());
	     	 multiLayerNetwork.fit(trainIter); 		
	          
		 }	
       
	}

}
