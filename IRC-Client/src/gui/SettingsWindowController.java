package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;

import gui.Helper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
	
	private EventBus eventBus;
	
	
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
		
		usersetup = makeBranch("User info", mainRoot);
		server = makeBranch("Server", mainRoot);
		looks = makeBranch("Appearance", mainRoot);
		
		
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
	}	
	
	public static TreeItem<String> makeBranch(String title, TreeItem<String> parent){
		TreeItem<String> item = new TreeItem<String>(title);
		parent.getChildren().add(item);
		return item;
	}
}





