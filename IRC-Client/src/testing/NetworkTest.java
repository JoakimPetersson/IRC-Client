package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import network.ConnectionHandler;

////ip: chat.freenode.net
////port: 6665-6667

class NetworkTest {

	String serverAddress = "chat.freenode.net";
	int port = 6665;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		
		ConnectionHandler handler = new ConnectionHandler(serverAddress, port);
		
		handler.run();
		handler.readMessage();
		
		fail("Not yet implemented");
	}

}
