package com.flytxt.imageprocessor.image;

import com.flytxt.imageprocessor.config.ImageProcessor;
/**
 * 
 * @author shiju.john
 *
 */
public class ImageSplitterFactory {
	
	public static ImageSplitter<ImageSplit,ImageConfiguration> getDefaultImageSpliter(String imageLabel,ImageProcessor imageProcessor){		
		return new DefaultImageSplitter(imageLabel,imageProcessor);
		
	}

}
