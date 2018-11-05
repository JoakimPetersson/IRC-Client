/**
 * 
 * Listens to the incoming messages from a single IRC server and adds them to a BlockingQueue for later reading by a consumer class
 * 
 */

package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerListener extends Thread implements Runnable {

	private LinkedBlockingQueue<Message> messageQueue = null;
	private Socket socket;
	private InputStream inStream = null;
	private boolean run = true;
	
	ServerListener(Socket socket, LinkedBlockingQueue<Message> messageQueue){
		this.messageQueue = messageQueue;
		this.socket = socket;
		try {
			this.inStream = socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				messageQueue.add(temp);
			}
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}}
