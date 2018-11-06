/**
 * 
 * Handles the connection to a single IRC server.
 * 
 * Creates a separate thread for listening to messages coming from that server.
 * 
 * TODO: Special case for when third choice of nickname is already taken
 * TODO: Connect function that can connect to server that requires a password
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
	private ServerListener listener = null;
	private int nickChoice = 1;
	
	LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();
	ArrayList<String> joinOnConnectChannels = null;

	public ConnectionHandler(String serverName, int port, UserInfo user, ArrayList<String> joinOnConnectChannels){
		this.user = user;
		this.joinOnConnectChannels = joinOnConnectChannels;
		this.serverName = serverName;
		this.port = port;
	}
	
	public void runServerMessageListener() {
		listener = new ServerListener(server, messageQueue);
		listener.start();
	}
	
	public Message readMessage() {
		try {
			if(!messageQueue.isEmpty()) {
				Message output =  messageQueue.take();
				
				if(output.type == MessageType.PING) {
					sendMessage("PONG" + "\r\n");
				}
				
				if(output.content != null) {
					if(output.content.contains("Nickname is already in use") && output.target.equals("*")) {
						// Reconnect with second nickname
						quitMessage("Changing nickname");
						listener.stopThread();
						nickChoice += 1;
						Thread.sleep(2000);
						connectToServer();		
					}
				}
				
				return output;	
			}	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendMessage(String message) {
		byte[] bytes = (message + "\r\n").getBytes();
		
		try {
			out.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void connectToServer() {		
		try {
			server = new Socket(serverName, port);
			out = server.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		runServerMessageListener();
		
		nickMessage();
		userMessage();
		
		if(joinOnConnectChannels != null) {
			for(String channel: joinOnConnectChannels) {
				joinMessage(channel);
			}
		}
	}
	
	private void nickMessage() {
		String nick;
		
		switch (nickChoice){
		case 1:
			nick = user.getNickname();
			break;
		case 2:
			nick = user.getSecondchoice();
			break;
		case 3:
			nick = user.getThirdchoice();
			break;
		default:
			nick = user.getNickname();
			break;
		}
		
		sendMessage("NICK " +  nick);
	}
	
	public void quitMessage(String message) {
		sendMessage("QUIT " + message);
	}
	
	public void sendPRIVMSG(String message, String target) {
		sendMessage("PRIVMSG " + target + " :" + message);
	}
	
	// TODO (maybe) Add an option to pick mode (Not sure if this feature is needed yet)
	private void userMessage() {
		sendMessage("USER " + user.getUsername() +  " 0 * :" + user.getRealname());
	}
	
	public void joinMessage(String channel) {
		sendMessage("JOIN " + channel);
	}
}
