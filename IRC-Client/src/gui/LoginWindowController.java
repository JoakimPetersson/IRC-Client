package gui;
//TODO add realname to form
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class LoginWindowController implements Initializable {
	
/**
 * Properties
 */	 
	
	 @FXML
		private TextField addServerName;
	
	 @FXML
	    private TextField serverNameText;	
	 
	 @FXML
	    private TextField serverRegionText;
    
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
	    private Label errorMsgServer;

	 
	
	 public TreeItem<String> serverRoot = new TreeItem<String>("asdas"); 
	 
	/**
	 * Events
	 */	 
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTree();		
	}
	
	@FXML
	void addServerBtn_Click(ActionEvent event) {
		hideAllForms();
		addServerInfo.setVisible(true);
	}
	
	@FXML
    void addServerOKbtn_Click(ActionEvent event) {
		addServers();
		hideAllForms();
		serverInfo.setVisible(true);
		serverNameText.setText(null);
		serverRegionText.setText(null);
    }	
	
	@FXML
    void serverDeleteBtn_Click(ActionEvent event) {
		removeSelectedItem();
    }	
	
	@FXML    
	/**
	 * Methods
	 */
	
	private void removeSelectedItem() {
		TreeItem<?> selected = treeViewServers.getSelectionModel().getSelectedItem();
		try {
		selected.getParent().getChildren().remove(selected);
		}
		catch (NullPointerException e) {
			System.out.println("No server selected, no server deleted");
			errorMsgServer.setText("No server selected, no server deleted");
		}
		
		
	}
	
	private void addServers() {
		TreeItem<String> item = new TreeItem<>(serverNameText.getText().toString());
		serverRoot.getChildren().add(item);
		if (serverRegionText != null) {
			TreeItem<String> region = new TreeItem<>(serverRegionText.getText().toString());
			item.getChildren().add(region);
		}
	}
	
	public void StartLoginScene() {
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
	
	private void createTree() {		
		setupTreeItems();		
		menuSelectCheck();	
	}    

	private void setupTreeItems() {
		TreeItem<String> mainRoot, server, usersetup, placeHolderNetwork, euServer, usServer;		
		mainRoot = new TreeItem<String>("Connect");
		treeView_login.setRoot(mainRoot);				
		treeViewServers.setRoot(serverRoot);
		treeViewServers.setShowRoot(false);
		
		usersetup = makeBranch("User info", mainRoot);
		server = makeBranch("Server", mainRoot);
		placeHolderNetwork = makeBranch("Placeholder server", serverRoot);
		euServer = makeBranch("EU server (placeholder)", placeHolderNetwork); 
		usServer = makeBranch("US server (Placeholder)", placeHolderNetwork);
		
		
	}	

	private void menuSelectCheck() {
		treeView_login.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {			
			if (newValue == null) hideAllForms();				
			if (newValue.getValue().equals("User info")) {hideAllForms(); userInfo.setVisible(true);}
			if (newValue.getValue().equals("Server")) {hideAllForms(); serverInfo.setVisible(true);}						
		});		
	}
	
	private void hideAllForms() {
		userInfo.setVisible(false);
		serverInfo.setVisible(false);
		addServerInfo.setVisible(false);
	}
	
	public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
		TreeItem<String> item = new TreeItem<String>(title);
		parent.getChildren().add(item);
		return item;
	}

	

}





