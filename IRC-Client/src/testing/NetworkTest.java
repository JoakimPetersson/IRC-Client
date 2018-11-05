package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import network.ConnectionHandler;
import network.Message;
import network.UserInfo;

//ip: chat.freenode.net
//port: 6665-6667

class NetworkTest {

	String serverAddress = "chat.freenode.net";
	int port = 6665;
	String channel = "#joakimpetersson";
	UserInfo user = new UserInfo();
	ArrayList<String> channels = new ArrayList<String>();
	
	@BeforeEach
	void setUp() throws Exception {
		user.setNickname("IRC_Bot_TEST_JP1");
		user.setSecondchoice("IRC_Bot_TEST_JP2");
		user.setThirdchoice("IRC_Bot_TEST_JP3");
		user.setRealname("Bot McBotsson");
		user.setUsername("BotTest");
		
		channels.add("#joakimpetersson");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	// Connects to server and joins a channel, then leaves as soon as it gets the list of users in the channel.
	@Test
	void mainTest() throws InterruptedException {			
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
	void nickNameTest() throws InterruptedException {			
		user.setNickname("test !\"#§%&/()=?``ß1@£$Ä6{[]}\\^~~*≈ƒ÷Ä<>|‰÷*'");
		
		ConnectionHandler handler = new ConnectionHandler(serverAddress, port, user, channels);
		handler.connectToServer();
		
		while(true) {
			Message msg = handler.readMessage();
			
			if(msg != null) {
				System.out.println(msg.raw);
				
				if(msg.content != null) {
					if(msg.content.contains("End of /NAMES list.")) {
						handler.quitMessage("Successful test");
						break;
					}
					
					if(msg.raw.contains("Erroneous Nickname")) {
						fail("Test failed: Faulty nickname");
					}
				}
			}
		}
	}
	
	@Test
	void firstChoiceNickNameNotAvailable() {		
		ConnectionHandler firstHandler = new ConnectionHandler(serverAddress, port, user, channels);
		ConnectionHandler secondHandler = new ConnectionHandler(serverAddress, port, user, channels);
		
		firstHandler.connectToServer();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		secondHandler.connectToServer();
		
		while(true) {		
		Message secondmsg = secondHandler.readMessage();
			if(secondmsg != null) {
				System.out.println(secondmsg.raw);
				
				if(secondmsg.content != null) {
					if(secondmsg.content.contains("End of /NAMES list.")) {
						secondHandler.quitMessage("Test finished without any errors");
						break;
					}
				}
			}
		}
		secondHandler.quitMessage("Test finished without any errors");
	}
	
	@Test
	void multipleChannelsTest() {
		channels.add("#joakimpeterssontest");
		
		ConnectionHandler handler = new ConnectionHandler(serverAddress, port, user, channels);
		handler.connectToServer();
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendPRIVMSG("Writing to channel 1", channels.get(0));
				handler.sendPRIVMSG("Writing to channel 2", channels.get(1));
				handler.quitMessage("Tests worked correctly");
			}
		}, 30*1000);
		
		while(true) {
			Message msg = handler.readMessage();
			
			if(msg != null) {
				System.out.println(msg.raw);
			}
		}
	}
	
	@Test
	void quitMessage() {		
		ConnectionHandler firstHandler = new ConnectionHandler(serverAddress, port, user, channels);
		ConnectionHandler secondHandler = new ConnectionHandler(serverAddress, port, user, channels);
		
		firstHandler.connectToServer();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		secondHandler.connectToServer();
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				firstHandler.sendPRIVMSG("please stop", channel);
				firstHandler.quitMessage("This should show up");
			}
		}, 6*60*1000);
		
		
		while(true) {	
		Message firstmsg = firstHandler.readMessage();
		Message secondmsg = secondHandler.readMessage();
			if(secondmsg != null) {
				System.out.println(secondmsg.raw);
				
				if(secondmsg.content != null) {
					if(secondmsg.content.contains("End of /NAMES list.")) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(secondmsg.content.contains("please stop")) {
						secondHandler.quitMessage("Test finished without any errors");
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
