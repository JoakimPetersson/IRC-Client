package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.Helper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


//TODO Fix edit button on add-server window
//TODO Finish the edit function

public class LoginWindowController implements Initializable {
	
	/****************************************************************************************
	 * Properties
	 ****************************************************************************************
	 */	
	
	@FXML
	private ServerListController serverListController;
	
	@FXML
	private UserInfoController userInfoController;
	
	@FXML
	private TreeView<String> treeView_login;
	 
		
	 
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
				userInfoController.setVisible(true);
			}
			if (newValue.getValue().equals("Server")) {
				hideAllForms(); 
				serverListController.setVisible(true);}
			if (newValue.getValue().equals("Appearance")) {hideAllForms();}
		});		
	}
	
	//Sets all forms to be invisible
	private void hideAllForms() {
		serverListController.setVisible(false);
		userInfoController.setVisible(false);
		//addServerInfo.setVisible(false);
	}	
}





