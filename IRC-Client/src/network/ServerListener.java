/**
 * 
 * Listens to the incoming messages from a single IRC server and adds them to a BlockingQueue for later reading by a consumer class
 * 
 * It also maintains the connection to the server by responding to PING messages.
 * 
 */

package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerListener extends Thread implements Runnable {

	private LinkedBlockingQueue<Message> messageQueue = null;
	private Socket socket;

	private boolean run = true;
	
	ServerListener(Socket socket, LinkedBlockingQueue<Message> messageQueue){
		this.messageQueue = messageQueue;
		this.socket = socket;
	}
	
	public void stopThread() {
		run = false;
	}
	
	public void run() {
		try {
			MessageParser parser = new MessageParser();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Message temp = new Message();
		while(run) {
			if(br.ready()) {
				temp = parser.parseRawMessage(br.readLine());
				
				if(temp.type == MessageType.PING) {				
					try {
						socket.getOutputStream().write(("PONG" + "\r\n").getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				messageQueue.add(temp);
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	}}
