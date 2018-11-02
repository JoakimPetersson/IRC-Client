package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;


public class ConnectionHandler implements Runnable {
	private Socket server;
	private OutputStream out;
	private ServerInfo info;
	
	LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
	
	public ConnectionHandler(String serverName, int port){
		try {
			server = new Socket(serverName, port);
			out = server.getOutputStream();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		Thread listener = new Thread(new ServerListener(server, messageQueue));
		listener.start();
	}
	
	public Message readMessage() {
		try {

			if(!messageQueue.isEmpty()) {
				return messageQueue.take();	
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendMessage(String message) {
		byte[] bytes = (message + "\r\n").getBytes();
		
		try {
			out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
