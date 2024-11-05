package edu.seg2105.edu.server.ui;

import ocsf.client.*;
import ocsf.server.AbstractServer;

import java.io.*;
import java.net.ServerSocket;
import java.util.Scanner;

import edu.seg2105.client.backend.ChatClient;
import edu.seg2105.client.common.*;
import edu.seg2105.client.ui.ClientConsole;


public class ServerConsole extends AbstractServer implements ChatIF {
	
	
		
	final public static int DEFAULT_PORT = 5555;
		 
	ChatClient server;
		
	Scanner fromConsole;
	
	private ServerSocket serverSocket;
	
	private ObjectOutputStream output;

	private ObjectInputStream input;
	
	public ServerConsole(String host, int port) {
		try 
		{
			server= new ChatClient(host, port, this);
	          
		} 
		catch(IOException exception) 
		{
			System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
			System.exit(1);
		}
		
		// Create scanner object to read from console
		fromConsole = new Scanner(System.in);
	}

	public void display(String message) {
		
		System.out.println("SERVER MSG> " + message);
		
	}
	
	public void accept() {
	   try{
		   
		   String message;
	      
	       while (true) {
	    	   	message = fromConsole.nextLine();
	    	   	message.sendToAllClients();
	       }
	   } 
	   catch (Exception ex) {
		   
		   System.out.println("Unexpected error while reading from console!");
		   
	   }
	 }
	
	public static void main(String[] args) 
	  {
	    String host = "";
	    int port = 0;


	    try
	    {
	      host = args[0];
	      port = Integer.parseInt(args[1]);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	      port = DEFAULT_PORT;
	    }
	    catch(NumberFormatException ne) {
	    	
	    	port = DEFAULT_PORT;
	    	
	    }
	    ServerConsole chat= new ServerConsole(host, port);
	    chat.accept();  //Wait for console data
	  }
	
	public void handleMessageFromServerUI(String message)
	  {
	    try
	    {
	    	if(message.startsWith("#")) {
	    		if(message.equals("#quit")) {
	    			
	    			serverSocket.close();
	    			
	    		}
	    		else if(message.equals("#stop")) {
	    			
	    			serverStopped();
	    			
	    		}
	    		else if(message.equals("#close")) {
	    		
	    			if(isConnected() == false) {
	    				
	    				setHost(clientUI.toString());
	    				
	    			}
	    			else {
	    				
	    				clientUI.display("Must be logged out to set host");
	    				
	    			}
	    			    			
	    		}
	    		else if(message.equals("#setport")) {
	    			
	    			if(isConnected() == false) {
	    				
	    				setHost(clientUI.toString());
	    				
	    			}
	    			else {
	    				
	    				serverUI.display("Must be logged out to set port");
	    				
	    			}
	    			
	    		}
	    		else if(message.equals("#start")) {
	    		
	    			listen();
	    			
	    		}
	    			
	    		else if(message.equals("#getport")) {
	    			
	    			getPort();
	    			
	    		}
	    		
	    		
	    	}
	    	else {
	    		
	    		sendToServer(message);
	    		
	    	}
	    }
	    catch(IOException e)
	    {
	      clientUI.display
	        ("Could not send message to server.  Terminating client.");
	      quit();
	    }
	    
	 }
	    
	 @Override
	 protected void serverStopped() {
	
		 
	 
	 }
	
}
