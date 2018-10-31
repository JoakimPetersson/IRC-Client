package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class ConnectionHandler implements Runnable {
	private Socket server;
	private OutputStream out;
	private ServerInfo info;
	
	ConnectionHandler(String serverName, int port){
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
	
	void sendMessage(String message) {
		byte[] bytes = (message + "\r\n").getBytes();
		
		try {
			out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
