/*
 * 
 * MessageParser for turning raw IRC messages into more easily workable parts. 
 * 
 * Example of a standard IRC message:     :JoakimPetersson_!~JoakimPet@h-162-99.A205.priv.bahnhof.se PRIVMSG #joakimpetersson :test test test
 * Ping messages has the format: "PING: other message"
 * 
 */

package network;

public class MessageParser {
	public Message parseRawMessage(String rawMessage) {
		Message outputMessage = new Message();
		String temp = rawMessage;
		
		/* 
		 * Test if message is of type PING
		 * Because the PING message has a special message format, we have to check that first before doing the standard message parsing routine.
		 */
		if(temp.substring(0, 4).equals("PING")) {
			outputMessage.type = MessageType.PING;
		} else {
		// Remove first :
		temp = rawMessage.substring(1);
		
		// Split string at first whitespace
		String parts[] = temp.split(" ", 2);
		
		// Check is first part contains a !, if it does, split string and remove second part
		String nick = parts[0];
		if(nick.contains("!")) {
			int idx = nick.indexOf("!");
			nick = nick.substring(0, idx);
		};
		
		temp = parts[1];
		// Determine message type
		parts = temp.split(" ", 2);
		
		MessageType type;
		
		switch (parts[0]){
			case "PRIVMSG": 
				type = MessageType.PRIVMSG;
				break;
			case "NOTICE":
				type = MessageType.NOTICE;
				break;
			case "PING":
				type = MessageType.PING;
				break;
			default:
				type = MessageType.CODE;		
		}
		
		// Determine target
		temp = parts[1];
		parts = temp.split(" ", 2);
		String target = parts[0];
		
		// Determine message content
		if(parts.length == 2) {
			temp = parts[1].substring(1);
			outputMessage.content = temp;
		}
		
		outputMessage.user = nick;
		outputMessage.type = type;
		outputMessage.target = target;
		}
		
		return outputMessage;
	}
	
}
