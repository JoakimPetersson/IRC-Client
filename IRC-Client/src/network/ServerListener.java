package network;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerListener extends Thread implements Runnable {

	LinkedBlockingQueue<Message> messageQueue = null;
	
	ServerListener(Socket socket, LinkedBlockingQueue<Message> messageQueue){
		this.messageQueue = messageQueue;
	}
	
	public void run() {
		
		for(int i = 0; i < 50; i++) {
			Message newMessage = new Message();
			newMessage.user = "Test Testsson";
			newMessage.content = i + " Test test test";
			
			messageQueue.add(newMessage);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
