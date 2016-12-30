package com.flytxt.imageprocessor.image;

import java.nio.file.Path;
/**
 * 
 * @author shiju.john
 *
 * @param <T>
 * @param <E>
 */
public interface ImageSplitter<T,E> {
	
	public T getImageSplit(Path path, E e);
	
	public T getEvalImageSplit(Path path, E e);
	
	

}
 