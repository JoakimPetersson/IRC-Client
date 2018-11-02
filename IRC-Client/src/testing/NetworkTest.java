package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import network.ConnectionHandler;
import network.Message;
import network.UserInfo;

//ip: chat.freenode.net
//port: 6665-6667

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

	// Connects to server and joins a channel, then leaves as soon as it gets the list of users in the channel.
	@Test
	void test() throws InterruptedException {
		
		UserInfo user = new UserInfo();
		user.setNickname("IRC_Bot_TEST_JP_1");
		user.realname = "Joakim Petersson";
		user.setUsername("JoakimPetersson");
		
		ArrayList<String> channels = new ArrayList<String>();
		
		channels.add("#joakimpetersson");
				
		ConnectionHandler handler = new ConnectionHandler(serverAddress, port, user, channels);
		handler.connectToServer();
		
		while(true) {
			Message msg = handler.readMessage();
			
			if(msg != null) {
				System.out.println(msg.user + "(" + msg.target + ") :" + msg.content);
				
				if(msg.content != null) {
					if(msg.content.contains("End of /NAMES list.")) {
						break;
					}
				}
			}
		}
	}
	
	@Test
	void removeWhiteSpaceFromNames() {
		UserInfo user = new UserInfo();
		user.setUsername(" Test Test Test    ");
		assert user.getUsername().equals("TestTestTest");
	}
}
