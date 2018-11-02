package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import network.ConnectionHandler;
import network.Message;
import network.UserInfo;

////ip: chat.freenode.net
////port: 6665-6667

/*
 * TODO: Parser test. Make sure it can handle any random input and refuse messages that doesn't fit the IRC protocol
 * TODO: Network test with two ConnectionHandlers connecting with different names and talking to each other so we can have actual fail/success states for the test.
 */

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

	// This test is terrible and needs to be reworked into multiple smaller tests.
	@Test
	void test() throws InterruptedException {
		
		UserInfo user = null;
		
		user.nickname = "";
		
		ConnectionHandler handler = new ConnectionHandler(serverAddress, port, user);
		
		handler.run();
		
		handler.sendMessage("NICK IRC_Bot_Test_JP");
		Thread.sleep(1000);
		handler.sendMessage("USER JoakimPetersson null null JoakimPetersson");
		Thread.sleep(1000);
		handler.sendMessage("JOIN " + channel);
		
		while(true) {
			Message msg = handler.readMessage();
			
			if(msg != null) {
				System.out.println(msg.user + "(" + msg.target + ") :" + msg.content);
			}
		}
		
	}

}
