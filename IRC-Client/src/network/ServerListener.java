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

	LinkedBlockingQueue<Message> messageQueue = null;
	Socket socket;
	InputStream inStream = null;
	
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
	
	public void run() {
		try {
			MessageParser parser = new MessageParser();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Message temp = new Message();
		while(true) {
			if(br.ready()) {
				temp = parser.parseRawMessage(br.readLine());
				
				if(temp.type == MessageType.PING) {
					// respond with PONG
					byte[] bytes = ("PONG" + "\r\n").getBytes();
					socket.getOutputStream().write(bytes);
					System.out.println("----- RESPONDED TO PING -----");
				} else {
					messageQueue.add(temp);
				}
			}
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}}
