package com.flytxt.imageprocessor.input;

import java.io.File;
import java.net.URI;

import org.datavec.api.io.labels.PathLabelGenerator;
import org.datavec.api.writable.Text;
import org.datavec.api.writable.Writable;

public class LabelGenerator  implements PathLabelGenerator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String imageLabel;
	
	
	public LabelGenerator(String imageLabel) {
		this.imageLabel =imageLabel;
	}

	@Override
	public Writable getLabelForPath(String path) {
		return new Text(imageLabel);
	}

	@Override
	public Writable getLabelForPath(URI uri) {
		return this.getLabelForPath(new File(uri).toString());
	}

}
