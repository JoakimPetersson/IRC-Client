package gui;

import java.util.prefs.Preferences;

import network.UserInfo;

public class PreferenceHandler {	
	public void setGlobalUserInfo(UserInfo globalUserInfo) {
		Preferences prefs;
		prefs = Preferences.userRoot().node("globalUserInfo");
		
		prefs.put("NICKNAME", globalUserInfo.getNickname());
		prefs.put("SECONDCHOICE", globalUserInfo.getSecondchoice());
		prefs.put("THIRDCHOICE", globalUserInfo.getThirdchoice());
		prefs.put("USERNAME", globalUserInfo.getUsername());
		prefs.put("REALNAME", globalUserInfo.getRealname());
	}
	
	public UserInfo getGlobalUserInfo() {
		Preferences prefs;
		prefs = Preferences.userRoot().node("globalUserInfo");
		
		UserInfo globalUserInfo = new UserInfo();
		globalUserInfo.setNickname(prefs.get("NICKNAME", "nickname"));
		globalUserInfo.setSecondchoice(prefs.get("SECONDCHOICE", "secondchoice"));
		globalUserInfo.setThirdchoice(prefs.get("THIRDCHOICE", "thirdchoice"));
		globalUserInfo.setUsername(prefs.get("USERNAME", "username"));
		globalUserInfo.setRealname(prefs.get("REALNAME", "realname"));
		
		return globalUserInfo;
	}
}
