package com.flytxt.imageprocessor.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor {
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(6);		
		
		WorkerThread car_side = new WorkerThread("src/main/resources/test/dataset/watch", "watch");
		executor.execute(car_side); 
		
		
		/*
		WorkerThread airplanes = new WorkerThread("src/main/resources/test/dataset/airplanes", "airplanes");
		executor.execute(airplanes);
		
		
		
		WorkerThread butterfly = new WorkerThread("src/main/resources/test/dataset/butterfly", "butterfly");
		executor.execute(butterfly);
		
				
		WorkerThread ketch = new WorkerThread("src/main/resources/test/dataset/ketch", "ketch");
		executor.execute(ketch);
		
		
		WorkerThread watch = new WorkerThread("src/main/resources/test/dataset/watch", "watch");
		executor.execute(watch);
		
		
		WorkerThread brain = new WorkerThread("src/main/resources/test/dataset/brain", "brain");
		executor.execute(brain);*/
		
		executor.shutdown();
		
	
		
	}

}
