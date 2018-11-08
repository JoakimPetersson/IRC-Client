package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.Helper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

//TODO Fix edit button on add-server window
//TODO Finish the edit function

public class SettingsWindowController implements Initializable {
	
	/****************************************************************************************
	 * Properties
	 ****************************************************************************************
	 */	
	
	@FXML
	private ServerListPaneController serverListPaneController;
	
	@FXML
	private UserInfoPaneController userInfoPaneController;
	
	@FXML
	private TreeView<String> treeView_login;
	
	@FXML
	private BorderPane settingsWindow;
	
	/****************************************************************************************
	 * Events																				 
	 ****************************************************************************************
	*/	 
	 
	//Sets up the menu on the left side with the needed options
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTree();
	}
	
	//Sets up the server- and user options screen
	public void Start() {		
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
				userInfoPaneController.setVisible(true);
			}
			if (newValue.getValue().equals("Server")) {
				hideAllForms(); 
				serverListPaneController.setVisible(true);}
			if (newValue.getValue().equals("Appearance")) {hideAllForms();}
		});		
	}
	
	//Sets all forms to be invisible
	public void hideAllForms() {
		serverListPaneController.setVisible(false);
		userInfoPaneController.setVisible(false);
		//addServerInfo.setVisible(false);
	}	
}





