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
		globalUserInfo.setNickname(prefs.get("NICKNAME", ""));
		globalUserInfo.setSecondchoice(prefs.get("SECONDCHOICE", ""));
		globalUserInfo.setThirdchoice(prefs.get("THIRDCHOICE", ""));
		globalUserInfo.setUsername(prefs.get("USERNAME", ""));
		globalUserInfo.setRealname(prefs.get("REALNAME", ""));
		
		return globalUserInfo;
	}
}
