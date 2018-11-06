/*
 *
 *	Data type for storing IRC message data.
 * 
 * 	Important: If the target starts with a '#', the message is sent to channel. If it lacks a prefix symbol, it is a direct PM to the user.
 * 
 * 
 */

package network;

enum MessageType{
	NOTICE, PRIVMSG, PING, CODE, JOIN, QUIT, PART, AWAY
}

public class Message {
	public String user;
	public String content;
	public MessageType type;
	public String target;
	public String raw;
}
