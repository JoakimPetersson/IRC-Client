package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import network.ConnectionHandler;

class NetworkTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		
		ConnectionHandler handler = new ConnectionHandler("test", 1111);
		
		handler.run();
		handler.readMessage();
		
		fail("Not yet implemented");
	}

}
