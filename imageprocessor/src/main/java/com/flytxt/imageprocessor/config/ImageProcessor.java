package com.flytxt.imageprocessor.config;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.datasets.iterator.MultipleEpochsIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.dataset.DataSet;

import com.flytxt.imageprocessor.image.ImageConfiguration;
import com.flytxt.imageprocessor.image.ImageIterator;
import com.flytxt.imageprocessor.image.ImageSplit;
import com.flytxt.imageprocessor.image.ImageSplitter;
import com.flytxt.imageprocessor.image.ImageSplitterFactory;
/**
 * 
 * @author shiju.john
 *
 */
public class ImageProcessor {

	private final MultiLayerNetwork multiLayerNetwork ; 	
	private final Configuration configuration;
	
	Logger log = LoggerFactory.getLogger(ImageProcessor.class);
	
	final ImageIterator imageIterator;
	
	/**
	 * 
	 * @param configuration
	 */
	public ImageProcessor(Configuration configuration) {
		this.configuration = configuration;
		multiLayerNetwork =  new MultiLayerNetwork(configuration.getConfiguration());
		init();		
		imageIterator =  new ImageIterator(this); 		
	}
	
	
	/**
	 * @param configuration
	 * @param multiLayerNetwork
	 */
	public ImageProcessor(Configuration configuration, MultiLayerNetwork multiLayerNetwork) {
		this.configuration = configuration;
		this.multiLayerNetwork = multiLayerNetwork;
		imageIterator =  new ImageIterator(this); 
		getMultiLayerNetwork().setListeners(new ScoreIterationListener(1));
	}

	/**
	 * 
	 */
	public  void init() {
		getMultiLayerNetwork().init();
		getMultiLayerNetwork().setListeners(new ScoreIterationListener(1));
		
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	
	
	/**
	 * 
	 * @param imageLabel
	 * @param path
	 * @param imageConfig
	 * @throws IOException
	 */
	public void addTrainingData(String imageLabel, Path path,ImageConfiguration imageConfig) throws IOException {
		imageIterator.initialize(imageLabel, path, imageConfig);		
	}
	
	/**
	 * 
	 */
	public void train(){
		if(null!=imageIterator.getDataIterator()){
			MultipleEpochsIterator trainIter = new MultipleEpochsIterator(configuration.getEpochs(), 
								imageIterator.getDataIterator(), Runtime.getRuntime().availableProcessors());
			getMultiLayerNetwork().fit(trainIter);
		}else{
			log.error("Please add the training Data Before start the training");
		}
	}

	/**
	 * 
	 * @param imageTrainingPath
	 * @param path
	 * @param imageLabel
	 * @param config
	 * @return
	 * @throws IOException
	 */
	public String evaluate(Path imageTrainingPath, Path path,String imageLabel, ImageConfiguration config) throws IOException {
		
		ImageSplitter<ImageSplit , ImageConfiguration>  imageSplitter = ImageSplitterFactory.getDefaultImageSpliter(imageLabel, this);
		ImageSplit imageSplit = imageSplitter.getImageSplit(path, config);	
		
		RecordReaderDataSetIterator dataSetIterator  = imageIterator.processWithEvalData(imageSplit,config);
		Evaluation eval = getMultiLayerNetwork().evaluate(dataSetIterator);
		eval.stats(true);
		
		dataSetIterator.reset();
	    String predictVal =null;
	    for(;dataSetIterator.hasNext();){
	        DataSet testDataSet = dataSetIterator.next();
	        testDataSet.setLabelNames(getLabels(imageTrainingPath,config));	        
	        List<String> predict = getMultiLayerNetwork().predict(testDataSet);
	        predictVal = predict.get(0);
	        System.out.println("Predication  :" + predictVal);
	    }
	    return  predictVal;
	}
	
	/**
	 * 
	 * @param path
	 * @param imageConfig
	 * @return
	 * @throws IOException
	 */
	public List<String> getLabels(Path path,ImageConfiguration imageConfig) throws IOException{
		
		ImageSplitter<ImageSplit , ImageConfiguration>  imageSplitter = ImageSplitterFactory.getDefaultImageSpliter(null, this);
		ImageSplit imageSplit = imageSplitter.getImageSplit(path, imageConfig);	
		
		try(ImageRecordReader recordReader = new ImageRecordReader(imageConfig.getImageHeight(), imageConfig.getImageWidth(), 
				getConfiguration().getChannel(), imageSplit.getLabelGenerator())){
			recordReader.initialize(imageSplit.getImageSplit());
			return recordReader.getLabels();
		}
		
	}

	
	/**
	 * 
	 * @param imageConfiguration
	 * @throws IOException 
	 */
	public void trainWithTransformation(ImageConfiguration imageConfiguration) throws IOException {		
		//imageIterator.processWithTransformation(imageConfiguration,getMultiLayerNetwork());
				
	}

	/**
	 * @return the multiLayerNetwork
	 */
	public MultiLayerNetwork getMultiLayerNetwork() {
		return multiLayerNetwork;
	}

}
