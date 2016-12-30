package com.flytxt.imageprocessor.image;

import org.datavec.api.io.labels.PathLabelGenerator;
import org.datavec.api.split.InputSplit;
/**
 * Contains the splitted images  
 * @author shiju.john
 *
 */
public class ImageSplit {
	
	private InputSplit imageSplit;
	private PathLabelGenerator labelGenerator;
	
	public ImageSplit(InputSplit imageSplit, PathLabelGenerator labelGenerator){
		this.setLabelGenerator(labelGenerator);
		setImageSplit(imageSplit);		
	}
	
		
	/**
	 * @return the imageSplit
	 */
	public InputSplit getImageSplit() {
		return imageSplit;
	}

	/**
	 * @param imageSplit the imageSplit to set
	 */
	private void setImageSplit(InputSplit imageSplit) {
		this.imageSplit = imageSplit;
	}


	/**
	 * @return the labelGenerator
	 */
	public PathLabelGenerator getLabelGenerator() {
		return labelGenerator;
	}


	/**
	 * @param labelGenerator the labelGenerator to set
	 */
	private void setLabelGenerator(PathLabelGenerator labelGenerator) {
		this.labelGenerator = labelGenerator;
	}

}
