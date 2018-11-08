package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import network.UserInfo;

public class UserInfoPaneController {
	
	 @FXML
	 private GridPane userInfoPane;

	 @FXML
	 private TextField nickNameText;

	 @FXML
	 private TextField secondChoiceText;

	 @FXML
	 private TextField thirdChoiceText;

	 @FXML
	 private TextField userNameText;
	 
	 @FXML
	 private TextField realNameText;
	 
	 @FXML
	 private Button addUserOk;
	 
	 @FXML
	 private Label createUserReporter;
	
	
	public void initialize() {
		
	}
	
	@FXML
    void addUserOk_Click(ActionEvent event) {
		String createUserErrorMsg = "";
		try
			{
				setGlobalUserInfo();
				
				if (Helper.isEmptyOrNull(nickNameText.getText().toString())) {
					createUserErrorMsg = "Nickname cannot be empty";
					throw new NullPointerException();
				}
				
				else if (Helper.isEmptyOrNull(userNameText.getText())){
					createUserErrorMsg = "Username cannot be empty";
					throw new NullPointerException();
				}
				
				//mainWindow.AddCreatedUser(createdUser);
				// TODO Don't think we need this message
				// createUserReporter.setText(createdUser.getUsername() + " Created");
				
				
				// By calling fillUserInfoFieldsFromPrefs() we make sure the user sees what the actual nickname will be when the forbidden characters are removed
				fillUserInfoFieldsFromPrefs();
			} catch (Exception e)
			{
				createUserReporter.setText(createUserErrorMsg);
				e.printStackTrace();
			}	
    }	
	
	private void setGlobalUserInfo()
	{
		PreferenceHandler prefs = new PreferenceHandler();
		UserInfo createdUser = new UserInfo();
		createdUser.setUsername(userNameText.getText().toString());
		createdUser.setNickname(nickNameText.getText().toString());
		createdUser.setSecondchoice(secondChoiceText.getText().toString());
		createdUser.setThirdchoice(thirdChoiceText.getText().toString());
		createdUser.setRealname(realNameText.getText().toString());
		prefs.setGlobalUserInfo(createdUser);
	}

private UserInfo getGlobalUserInfo() {
	PreferenceHandler prefs = new PreferenceHandler();
	return prefs.getGlobalUserInfo();
}

private void fillUserInfoFieldsFromPrefs() {		
	UserInfo globalUserInfo = getGlobalUserInfo();
	
	if(nickNameText == null) {
		System.out.println("NickNameText is null");
	}
	
	if(globalUserInfo.getNickname() != null) {
		nickNameText.setText(globalUserInfo.getNickname());
		secondChoiceText.setText(globalUserInfo.getSecondchoice());
		thirdChoiceText.setText(globalUserInfo.getThirdchoice());
		userNameText.setText(globalUserInfo.getUsername());
		realNameText.setText(globalUserInfo.getRealname());
	}
}


public void setVisible(boolean bool) {
	if(bool) {
		fillUserInfoFieldsFromPrefs();
	}
	
	userInfoPane.setVisible(bool);
}


public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	
}


}
