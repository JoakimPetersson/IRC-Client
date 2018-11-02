package network;

public class UserInfo {
	private String nickname;
	private String secondchoice; 
	private String thirdchoice;
	private String username;
	public String realname;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = removeForbiddenCharacters(nickname);
	}
	public String getSecondchoice() {
		return secondchoice;
	}
	public void setSecondchoice(String secondchoice) {
		this.secondchoice = removeForbiddenCharacters(secondchoice);
	}
	public String getThirdchoice() {
		return thirdchoice;
	}
	public void setThirdchoice(String thirdchoice) {
		this.thirdchoice = removeForbiddenCharacters(thirdchoice);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = removeForbiddenCharacters(username);
	}
	private String removeForbiddenCharacters(String input) {
		return input.replaceAll("\\s", "");
	}
}
