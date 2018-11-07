package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.management.RuntimeErrorException;

import com.sun.javafx.scene.layout.region.SliceSequenceConverter;

import gui.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import network.ServerInfo;
import network.UserInfo;

//TODO Fix edit button on add-server window
//TODO Finish the edit function

public class LoginWindowController implements Initializable {
	
	/****************************************************************************************
	 * Properties
	 ****************************************************************************************
	 */	
	
	 @FXML 
	 private TextField addServerName;
	
	 @FXML
	 private TextField serverNameText;	
	 
	 @FXML
	 private TextField serverRegionText;
	 
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
	 private TextField serverIpAdress;

	 @FXML
	 private TextField serverPort;
    
	 @FXML
	 private TreeView<String> treeView_login;
	 
	 @FXML
	 private GridPane userInfo;
	 
	 @FXML
	 private GridPane serverInfo;
	 
	 @FXML
	 private GridPane addServerInfo;
	 
	 @FXML
	 private Button addServerBtn;
	 
	 @FXML
	 private Button addServerOKbtn;
	 
	 @FXML
	 private Button serverDeleteBtn; 

	 @FXML
	 private Button serverEditBtn;
	 
	 @FXML
	 private Button addUserOk;
	 
	 @FXML
	 private Label errorMsgAddServer;
	 
	 @FXML
	 private Label errorMsgServer;	
	 
	 @FXML
	 private Label createUserReporter;
	 
	 @FXML
	 private ListView<String> serverListview; 
	 
	 @FXML
	 private ScrollPane serverScrollPane;
		
	 private ArrayList<ServerInfo> serverList = new ArrayList<>(); 
		
	 
		
	 
	 /****************************************************************************************
	  * Events																				 
	  ****************************************************************************************
	  */	 
	 
	//Sets up the menu on the left side with the needed options
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTree();
		serverListview.minHeightProperty().bind(serverScrollPane.heightProperty());
		serverListview.maxWidthProperty().bind(serverScrollPane.widthProperty());
		serverScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}
	
	//Hides all other forms and shows the "add-server form"
	@FXML
	void addServerBtn_Click(ActionEvent event) {
		hideAllForms();
		addServerInfo.setVisible(true);
	}
	
	//Adds the server put in in the "add-server"-form to the tree-item menu showing current servers
	//Server-name cannot be null, empty or whitespace, shows an error-message when this is the case
	//Hides all forms then shows the currently added servers
	@FXML
    void addServerOKbtn_Click(ActionEvent event) {	
    	CreateServer();
	}
	
	//removes the selected server or server-region when you click the delete-button
	@FXML
    void serverDeleteBtn_Click(ActionEvent event) {
		removeSelectedItem();
    }	
	
	//Takes the info from the "create user"-form and creates the user
	//Adds the created user to the "createdUser-list"
	
	//Takes the info from the create user-form and sets up a new user
	//Username and/or nickname cannot be empty, whitespace or null, shows error-message on screen 
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
	

    @FXML
    void serverEditBtn_Click(ActionEvent event) {
    	editServer();
    	
    	
    }
	    
	/****************************************************************************************
	 * Methods	 
	 ****************************************************************************************
	 */
	
	//Attempts to create the server based on the information filled out in the add-server form
	//Throws exception when any field is empty or if a duplicate server-name exists
	private void CreateServer()
		{
			String errorMessage = null;
			try {
				if (Helper.isEmptyOrNull(serverNameText.getText())) {
					errorMessage = "Server name cannot be empty";
					throw new Exception();	
					
				} else if (Helper.isEmptyOrNull(serverIpAdress.getText())) {
					errorMessage = "Server adress cannot be empty";
					throw new Exception();
					
				} else if (Helper.isEmptyOrNull(serverPort.getText())) {
					errorMessage = "Server port cannot be empty";
					throw new Exception();
				}				
				else {					
				ServerInfo currentServer = new ServerInfo();				
				addServerInfo(currentServer);			
				
				if (!isNotDuplicate(currentServer)) {				
					errorMessage = "Server with that name already exists";
					throw new Exception();
					
				} else { 
					addServerToList(currentServer);
					hideAllForms();
					serverInfo.setVisible(true);
					serverNameText.setText(null);
					serverIpAdress.setText(null);
					serverPort.setText(null);
					serverRegionText.setText(null);					
				}				
				}
			}
			catch (Exception e) {
			errorMsgAddServer.setText(errorMessage);			
			}
		}
	
	//Looks up what server is currently selected and removes it from the treeview
	//Shows error-message if none is selected
	private void removeSelectedItem() {
		String selected = serverListview.getSelectionModel().getSelectedItem();
		try {
		serverListview.getItems().remove(selected);
		}
		catch (NullPointerException e) {
			errorMsgServer.setText("No server selected, no server deleted");
		}		
	}
	
	//Takes then info from the "add-server"-form a and adds to the server-treeview
	//cannot be empty, whitespace or null
	private ServerInfo addServerInfo(ServerInfo currentServer) {
			currentServer.serverName = serverNameText.getText();
			currentServer.serverAddress = serverIpAdress.getText();
			currentServer.port = Integer.parseInt(serverPort.getText());
			return currentServer;		
	}
	
	//Adds the server name to the server menu and adds the server-object to an array called "serverList"
	private void addServerToList(ServerInfo server){		
			serverList.add(server);	
			serverListview.getItems().add(server.serverName);
		}
	
	//Checks for any other server already added with the same server name
	private boolean isNotDuplicate(ServerInfo server) {		
		for (int i = 0; i < serverList.size(); i++)			
			{				
				ServerInfo existingServer = serverList.get(i);
				if (existingServer.serverName.equals(server.serverName))
					{
						return false;
					}
			}
		return true;
	}
	
	//Sets up the server- and user options screen
	public void Start(MainWindowController mainWindow) {
		System.out.println(mainWindow);
		BorderPane grid;
		try {
			grid = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));		
		Stage stage = new Stage();
		Scene scene = new Scene(grid);	
		stage.setScene(scene);
		stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	

	}		
	
	//sets up the leftmost treeview and opens the selected options-form
	private void createTree() {		
		setupTreeItems();		
		menuSelectCheck();	
	}    

	//Creates the leftmost treeview
	private void setupTreeItems() {
		TreeItem<String> mainRoot, server, usersetup, looks;		
		mainRoot = new TreeItem<String>("Connections");
		treeView_login.setRoot(mainRoot);
		treeView_login.setShowRoot(false);
		
		usersetup = Helper.makeBranch("User info", mainRoot);
		server = Helper.makeBranch("Server", mainRoot);
		looks = Helper.makeBranch("Appearance", mainRoot);
		
		
	}	
	//Gets the currently selected treeItem in the leftmost menu and shows the relevant form
	private void menuSelectCheck() {
		treeView_login.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {			
			if (newValue == null) hideAllForms();				
			if (newValue.getValue().equals("User info")) {
				hideAllForms(); 
				userInfo.setVisible(true);
				
				fillUserInfoFieldsFromPrefs();

			}
			if (newValue.getValue().equals("Server")) {hideAllForms(); serverInfo.setVisible(true);}
			if (newValue.getValue().equals("Appearance")) {hideAllForms();}
		});		
	}
	//Sets all forms to be invisible
	private void hideAllForms() {
		userInfo.setVisible(false);
		serverInfo.setVisible(false);
		addServerInfo.setVisible(false);
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

	private void editServer()
		{
			String selected = serverListview.getSelectionModel().getSelectedItem();
			ServerInfo selectedServer = new ServerInfo();
			System.out.println(selectedServer.serverName);
			
			for (ServerInfo serverInfo : serverList)
				{
					if (selected.equals(serverInfo.serverName)){
						selectedServer.serverName = serverInfo.serverName;
						selectedServer.serverAddress = serverInfo.serverAddress;
						selectedServer.port = serverInfo.port;
						System.out.println(serverInfo.serverName);
						break;
					}
				}
			System.out.println(selectedServer.serverName);
			if (selected != null) {
				hideAllForms();
				addServerInfo.setVisible(true);
				addServerName.setText(selectedServer.serverName);
				System.out.println(selectedServer.serverName);
				serverIpAdress.setText(selectedServer.serverAddress);
				serverPort.setText(Integer.toString(selectedServer.port));
				
				
			}else System.out.println("fel");
		}

}





