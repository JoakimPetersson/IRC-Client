package network;

public class MessageParser {

	Message currentMessage;
	
	public Message parseRawMessage(String rawMessage) {
		Message outputMessage = new Message();
		
		String temp = rawMessage;
		
		// Test if message is of type PING
		if(temp.substring(0, 4).equals("PING")) {
			outputMessage.type = MessageType.PING;
		} else {
		
		
		// Example:     :JoakimPetersson_!~JoakimPet@h-162-99.A205.priv.bahnhof.se PRIVMSG #joakimpetersson :test test test
		
		// Split string into parts
		
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
		// TODO, if needed
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
			outputMessage.content = parts[1];
		}
		
		outputMessage.user = nick;
		outputMessage.type = type;
		outputMessage.target = target;
		}
		
		return outputMessage;
	}
	
}
