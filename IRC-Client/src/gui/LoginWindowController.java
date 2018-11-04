package gui;
import java.io.IOException;
import java.net.URL;
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
	    private Label errorMsgAddServer;
	 
	 @FXML
	    private Label errorMsgServer;	 
	
	 private TreeItem<String> serverRoot = new TreeItem<String>(); 
	 
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
			
			addServers();
			hideAllForms();
			serverInfo.setVisible(true);
			serverNameText.setText(null);
			serverRegionText.setText(null);
			}else throw new NullPointerException();
		}
		catch (NullPointerException e) {
		System.out.println("You must enter a servername");
		errorMsgAddServer.setText("You must enter a servername");
		}
	}    	
	//removes the selected server or server-region when you click the delete-button
	@FXML
    void serverDeleteBtn_Click(ActionEvent event) {
		removeSelectedItem();
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
	//Takes then info from the "add-server"-form and adds to the server-treeview
	//cannot be empty, whitespace or null
	private void addServers() {
			
		TreeItem<String> item = new TreeItem<>(serverNameText.getText().toString());
		serverRoot.getChildren().add(item);
		if (!Helper.isEmptyOrNull(serverRegionText.getText())) {
			TreeItem<String> region = new TreeItem<>(serverRegionText.getText().toString());
			item.getChildren().add(region);
			}
		
	}
	//Sets up the server- and user options screen
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
	
	//sets up the leftmost treeview and opens the selected options-form
	private void createTree() {		
		setupTreeItems();		
		menuSelectCheck();	
	}    

	//Creates the leftmost treeview
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
	//Gets the currently selected treeItem in the leftmost menu and shows the relevant form
	private void menuSelectCheck() {
		treeView_login.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {			
			if (newValue == null) hideAllForms();				
			if (newValue.getValue().equals("User info")) {hideAllForms(); userInfo.setVisible(true);}
			if (newValue.getValue().equals("Server")) {hideAllForms(); serverInfo.setVisible(true);}						
		});		
	}
	//Sets all forms to be invisible
	private void hideAllForms() {
		userInfo.setVisible(false);
		serverInfo.setVisible(false);
		addServerInfo.setVisible(false);
	}
	
	//Generates a new branch for the server-treeview
	public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
		TreeItem<String> item = new TreeItem<String>(title);
		parent.getChildren().add(item);
		return item;
	}

	

}





