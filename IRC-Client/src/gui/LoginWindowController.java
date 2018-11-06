package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import gui.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import network.ServerInfo;
import network.UserInfo;

//TODO Fix edit button on add-server window
//TODO add connect button to serverlist
//TODO make listview instead of label on the server-form

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
	 private TreeView<String> treeViewServers;
	 
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
	 private Button addUserOk;
	 
	 @FXML
	 private Label errorMsgAddServer;
	 
	 @FXML
	 private Label errorMsgServer;	
	 
	 @FXML
	 private Label createUserReporter;
	
	 private TreeItem<String> serverRoot = new TreeItem<String>(); 
	 
	 private int fontSize;
		public int getFontSize()
			{
					return fontSize;
			}

		public void setFontSize(int fontSize)
			{
					this.fontSize = fontSize;
			}
		
	  private ArrayList<ServerInfo> serverList = new ArrayList<>(); 
		
	 
		
	 
	 /****************************************************************************************
	  * Events																				 
	  ****************************************************************************************
	  */	 
	 
	//Sets up the menu on the left side with the needed options
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTree();
		
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
		try {
			if (!Helper.isEmptyOrNull(serverNameText.getText())) {			
			addServerToList(createServer());			
			hideAllForms();
			serverInfo.setVisible(true);
			serverNameText.setText(null);
			serverRegionText.setText(null);
			}else throw new NullPointerException();
		}
		catch (NullPointerException e) {
		errorMsgAddServer.setText("You must enter a servername");
		}
	}
	//removes the selected server or server-region when you click the delete-button
	@FXML
    void serverDeleteBtn_Click(ActionEvent event) {
		removeSelectedItem();
    }	
	
	//Takes the info from the "create user"-form and creates the user
	//Adds the created user to the "createdUser-list"
	@FXML
    void addUserOk_Click(ActionEvent event) {
    	CreateUser();    	
    }	
	    
	/****************************************************************************************
	 * Methods																				
	 ****************************************************************************************
	 */
	//Looks up what server is currently selected and removes it from the treeview
	//Shows error-message if noone is selected
	private void removeSelectedItem() {
		TreeItem<?> selected = treeViewServers.getSelectionModel().getSelectedItem();
		try {
		selected.getParent().getChildren().remove(selected);
		}
		catch (NullPointerException e) {
			errorMsgServer.setText("No server selected, no server deleted");
		}		
	}	
	//Takes then info from the "add-server"-form a and adds to the server-treeview
	//cannot be empty, whitespace or null
	private ServerInfo createServer() {
			ServerInfo currentServer = new ServerInfo();
			currentServer.serverName = serverNameText.getText();
			currentServer.serverAddress = serverIpAdress.getText();
			currentServer.port = Integer.parseInt(serverPort.getText());
			return currentServer;		
	}
	
	private void addServerToList(ServerInfo server){
			if (checkForDuplicate(server) == false) {
			TreeItem<String> item = Helper.makeBranch(server.serverName, serverRoot);			
			serverList.add(server);
			System.out.println(server.serverName);
			} else errorMsgAddServer.setText("Duplicate server found, rename");
		}
	
	private boolean checkForDuplicate(ServerInfo server) {
		boolean dupeCheck = false;
		for (int i = 0; i < serverList.size(); i++)			
			{				
				ServerInfo user = serverList.get(i);
				if (user.serverName.equals(server.serverName))
					{
						dupeCheck = true;
					}
			}
		return dupeCheck;
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
		treeViewServers.setRoot(serverRoot);
		treeViewServers.setShowRoot(false);
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
				
				PreferenceHandler prefs = new PreferenceHandler();
				
				UserInfo globalUserInfo = prefs.getGlobalUserInfo();
				
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
	
	//Takes the info from the create user-form and sets up a new user
	//Username and/or nickname cannot be empty, whitespace or null, shows error-message on screen 
	private void CreateUser()
		{
			String createUserErrorMsg = "";
			try
				{
				
					PreferenceHandler prefs = new PreferenceHandler();
					UserInfo createdUser = new UserInfo();
					createdUser.setUsername(userNameText.getText().toString());
					createdUser.setNickname(nickNameText.getText().toString());
					createdUser.setSecondchoice(secondChoiceText.getText().toString());
					createdUser.setThirdchoice(thirdChoiceText.getText().toString());
					createdUser.setRealname(realNameText.getText().toString());
					
					prefs.setGlobalUserInfo(createdUser);
					
					if (Helper.isEmptyOrNull(nickNameText.getText().toString())) {
						createUserErrorMsg = "Nickname cannot be empty";
						throw new NullPointerException();
					}
					
					else if (Helper.isEmptyOrNull(userNameText.getText())){
						createUserErrorMsg = "Username cannot be empty";
						throw new NullPointerException();
					}
					
					//mainWindow.AddCreatedUser(createdUser);
					createUserReporter.setText(createdUser.getUsername() + " Created");
					
				} catch (Exception e)
				{
					createUserReporter.setText(createUserErrorMsg);
					e.printStackTrace();
				}
		}

}





