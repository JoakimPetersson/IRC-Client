/**
 * 
 * Handles the connection to a single IRC server.
 * 
 * Creates a separate thread for listening to messages coming from that server.
 * 
 * TODO: Manage first, second, and third choice of nickname
 * TODO: Connect function that can connect to server that requires a password
 * TODO: Testclass for sending and receiving messages at the same time
 * TODO: add quit message function
 * TODO: look into other message functions that might be needed
 */



package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class ConnectionHandler {
	private Socket server;
	private OutputStream out;
	private String serverName;
	private int port;
	private UserInfo user;
	
	LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
	ArrayList<String> joinOnConnectChannels = null;

	public ConnectionHandler(String serverName, int port, UserInfo user, ArrayList<String> joinOnConnectChannels){
		this.user = user;
		this.joinOnConnectChannels = joinOnConnectChannels;
		this.serverName = serverName;
		this.port = port;
	}
	
	public void runServerMessageListener() {
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
		
		runServerMessageListener();
		
		nickMessage();
		userMessage();
		
		if(joinOnConnectChannels != null) {
			for(String channel: joinOnConnectChannels) {
				joinMessage(channel);
				// TODO Test if a wait period is needed between JOIN messages or if the server can handle a bunch of almost instant join commands
			}
		}
	}
	
	private void nickMessage() {
		sendMessage("NICK " + user.nickname);
	}
	
	// TODO (maybe) Add an option to pick mode (Not sure if this feature is needed yet)
	private void userMessage() {
		sendMessage("USER " + user.username +  " 0 * :" + user.realname);
	}
	
	public void joinMessage(String channel) {
		sendMessage("JOIN " + channel);
	}
}
