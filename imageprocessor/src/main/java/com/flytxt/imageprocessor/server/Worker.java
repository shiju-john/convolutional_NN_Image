package com.flytxt.imageprocessor.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.flytxt.imageprocessor.Invoker;

public class Worker implements Runnable {
	
	private Socket socket =null;
	private final  DataInputStream in;
	private final DataOutputStream out;
	private final Invoker invoker;
	
	public Worker(Socket socket, Invoker invoker) throws IOException{
		this.invoker = invoker;
		this.socket = socket;
		in = new DataInputStream(socket.getInputStream());  
        out = new DataOutputStream( socket.getOutputStream() );
	}

	@Override
	public void run() {
		try{
			boolean running =true;
			while(running){
				
				String line = in.readUTF();
				if("exit".equalsIgnoreCase(line)){
	               	running =false;
	               	out.writeUTF( "Stop request initiated ");
	               	out.flush();
	            }else{
	            	String result = invoker.invoke(line);
	            	out.writeUTF( result);
	               	out.flush();
	            }   
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			stopClient();
		}
			
		
	}

	/**
	 *  
	 */
	private void stopClient() {
	 try {
			in.close();
			out.close();
	        socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
