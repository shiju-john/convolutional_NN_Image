package com.flytxt.imageprocessor.image;

import java.io.File;
import java.nio.file.Path;
import java.util.Random;

import org.datavec.api.io.filters.BalancedPathFilter;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.io.labels.PathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputSplit;
import org.datavec.image.loader.NativeImageLoader;

import com.flytxt.imageprocessor.config.ImageProcessor;
/**
 * 
 * @author shiju.john
 *
 */
public class DefaultImageSplitter implements ImageSplitter<ImageSplit,ImageConfiguration> {

	private ImageProcessor imageProcessor;
	private PathLabelGenerator labelGenerator = null;

	public DefaultImageSplitter(String imageLabel,ImageProcessor imageProcessor) {
		this.imageProcessor = imageProcessor ;
		 this.labelGenerator = new ParentPathLabelGenerator();
	}

	@Override
	public ImageSplit getImageSplit(Path path,ImageConfiguration configuration) {
		 File mainPath = path.toFile();
		
		 FileSplit fileSplit = new FileSplit(mainPath, NativeImageLoader.ALLOWED_FORMATS, getRandom());
		
		 
		 BalancedPathFilter pathFilter = new BalancedPathFilter(getRandom(), labelGenerator, getImageCount(configuration), getLabelCount(), configuration.getBatchSize()); // ----- batch size 
		 InputSplit[] inputSplit = fileSplit.sample(pathFilter, 1.0);
		 InputSplit trainData = inputSplit[0];		   
		 return new ImageSplit(trainData,labelGenerator);
	}
	
	
	private int getLabelCount() {
		return imageProcessor.getConfiguration().getOutLabelCount();
	}

	/**
	 * @param configuration
	 * @return
	 */
	private int getImageCount(ImageConfiguration configuration) {
		return configuration.getTotalImageCount();
		
	}

	/**
	 * 
	 * @return
	 */
	private Random  getRandom(){
		return imageProcessor.getConfiguration().getRandom();
	}

	@Override
	public ImageSplit getEvalImageSplit(Path path, ImageConfiguration configuration) {
		 File mainPath = path.toFile();			
		 FileSplit fileSplit = new FileSplit(mainPath, NativeImageLoader.ALLOWED_FORMATS, getRandom());		 
		 BalancedPathFilter pathFilter = new BalancedPathFilter(getRandom(), labelGenerator, getImageCount(configuration), 1, configuration.getBatchSize()); // ----- batch size 
		 InputSplit[] inputSplit = fileSplit.sample(pathFilter, 1.0);
		 InputSplit trainData = inputSplit[0];		   
		 return new ImageSplit(trainData,labelGenerator);
	}
	
	


}
