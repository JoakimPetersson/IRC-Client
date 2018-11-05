package network;

public class UserInfo {
	private String nickname;
	private String secondchoice; 
	private String thirdchoice;
	private String username;
	private String realname;
	
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
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	private String removeForbiddenCharacters(String input) {
		//Allowed: numbers, letters, -  [ ] \ ^ { }
		
		String output = input;
		
		if(!output.matches("[a-zA-Z0-9+=*/^()_-]+")) { 
			System.out.println("Faulty nickname! Test will fail!");
			output = output.replaceAll("[^a-zA-Z0-9\\[\\]\\{\\}\\\\-]", "");
			System.out.println("Name after replace: " + output);
		}
		// input.replaceAll("\\s", "")
		
		return output;
	}

}
