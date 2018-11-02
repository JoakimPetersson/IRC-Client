package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import network.ConnectionHandler;
import network.Message;

////ip: chat.freenode.net
////port: 6665-6667

class NetworkTest {

	String serverAddress = "chat.freenode.net";
	int port = 6665;
	String channel = "#joakimpetersson";
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() throws InterruptedException {
		
		ConnectionHandler handler = new ConnectionHandler(serverAddress, port);
		
		handler.run();
		
		handler.sendMessage("NICK IRC_Bot_Test_JP");
		Thread.sleep(1000);
		handler.sendMessage("USER JoakimPetersson null null JoakimPetersson");
		Thread.sleep(1000);
		handler.sendMessage("JOIN " + channel);
		
		while(true) {
			Message msg = handler.readMessage();
			
			if(msg != null) {
				System.out.println(msg.user + " " + msg.content);
			}
		}
		
	}

}
