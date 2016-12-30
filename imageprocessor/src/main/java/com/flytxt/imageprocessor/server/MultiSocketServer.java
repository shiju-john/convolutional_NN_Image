package com.flytxt.imageprocessor.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.flytxt.imageprocessor.Invoker;

public class MultiSocketServer implements Runnable{
	
	private ServerSocket serverSocket = null;
	private ExecutorService executor = null;
	int port = 9081;
	private Invoker invoker = null;
	
	
	public void startServer(String strategy) throws IOException{		
		serverSocket = new ServerSocket(port);	
		executor = Executors.newFixedThreadPool(6);
				
		invoker = new Invoker();
		
		if("init".equalsIgnoreCase(strategy)){
			invoker.initalizeImageProcessor();
		}else if("load".equalsIgnoreCase(strategy)){
			invoker.loadImageProcessor();
		}else if("loadT".equalsIgnoreCase(strategy)){
			invoker.loadandTrainImageProcessor();
		}else{
			invalidStrategy();
			return;
		}		
		new Thread(this).start();
	}

	private void invalidStrategy() {
		/**
		 * Please provide the Run Time Parameter strategy 
		 */
		System.out.println("\n \t Please provide the Run Time strategy");	
		System.out.println("*****************************************************************");
		System.out.println("*	init   : For initalize the network			*");
		System.out.println("*	load   : For load the saved network 			*");
		System.out.println("*	loadT  : For load the saved network and Train  again	*");
		System.out.println("*****************************************************************");
		
		
	}

	@Override
	public void run() {	
		System.out.println("Server listening  on port " + port);
		while(true){
			Socket socket;
			try {
				socket = serverSocket.accept();
				executor.execute(new Worker(socket,invoker));
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}		
	}
	
	public static void main(String[] args) {
		try {
			
			if(null!=args && args.length>0){
				new MultiSocketServer().startServer(args[0]);
			}else{
				new MultiSocketServer().invalidStrategy();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
