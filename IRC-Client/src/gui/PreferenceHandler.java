package gui;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.google.gson.Gson;

import network.ServerInfo;
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

	// TODO test this code when Dowie is done with the serverwindow
	public ArrayList<ServerInfo> getAllServerInfo() {
		Preferences prefs;
		prefs = Preferences.userRoot().node("serverInfo");

		String[] keys = null;

		ArrayList<ServerInfo> allServers = new ArrayList<ServerInfo>();

		try {
			keys = prefs.keys();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(keys.length != 0) {
			for (String key : keys) {
				allServers.add(getServerInfo(key));
			}
		}
		return allServers;
	}

	public void setAllServerInfo(ArrayList<ServerInfo> allServers) {
		for (ServerInfo server : allServers) {
			setServerInfo(server);
		}
	}

	public void setServerInfo(ServerInfo serverInfo) {
		Gson gson = new Gson();
		Preferences prefs = Preferences.userRoot().node("serverInfo");

		String jsonServerInfo = gson.toJson(serverInfo);
		prefs.put(serverInfo.serverName, jsonServerInfo);
	}

	public ServerInfo getServerInfo(String key) {
		Gson gson = new Gson();
		ServerInfo serverInfo = null;
		Preferences prefs = Preferences.userRoot().node("serverInfo");

		String jsonPreferencesString = prefs.get(key, "");
		
		if(!jsonPreferencesString.equals("")) {
			serverInfo = gson.fromJson(jsonPreferencesString, ServerInfo.class);
			System.out.println(serverInfo.serverName);
		}
		
		return serverInfo;
	}
	
	public void removeServer(String key) {
		Preferences prefs = Preferences.userRoot().node("serverInfo");
		prefs.remove(key);
	}

}
