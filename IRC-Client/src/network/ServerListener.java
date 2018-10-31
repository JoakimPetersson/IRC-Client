package network;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerListener extends Thread implements Runnable {

	LinkedBlockingQueue<Message> messageQueue = null;
	
	ServerListener(Socket socket, LinkedBlockingQueue<Message> messageQueue){
		this.messageQueue = messageQueue;
	}
	
	public void run() {
		
		Message newMessage = new Message();
		newMessage.content = "Test test test";
		newMessage.user = "Test Testsson";
		
		for(int i = 0; i < 50; i++) {
			messageQueue.add(newMessage);
		}
	}
}
