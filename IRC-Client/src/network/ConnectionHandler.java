/**
 * 
 * Handles the connection to a single IRC server.
 * 
 * Creates a separate thread for listening to messages coming from that server.
 * 
 * 
 * TODO: JOIN function
 * TODO: NICK function
 * TODO: USER function
 * TODO: Manage first, second, and third choice of nickname
 * TODO: Connect function that can connect to server that requires a password
 * TODO: Testclass for sending and receiving messages at the same time
 * 
 */

package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class ConnectionHandler implements Runnable {
	private Socket server;
	private OutputStream out;
	private ServerInfo info;
	private UserInfo user;
	
	LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
	ArrayList<String> joinOnConnectChannels = new ArrayList<String>();
	
	public ConnectionHandler(String serverName, int port, UserInfo user){
		this.user = user;
		
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
	
	public void connectToServer() {
		nickMessage();
		userMessage();
		
		//joinOnConnectChannels.forEach(action);
		
	}
	
	private void nickMessage() {
		sendMessage("NICK " + user.nickname);
	}
	
	private void userMessage() {
		sendMessage("USER " + user.username +  " null null " + user.realname);
	}
	
	private void joinMessage(String channel) {
		sendMessage("JOIN " + channel);
	}
}
