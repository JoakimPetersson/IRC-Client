package network;

enum MessageType{
	NOTICE, PRIVMSG, PING, CODE
}

public class Message {
	public String user;
	public String content;
	public MessageType type;
	public String target;
}
