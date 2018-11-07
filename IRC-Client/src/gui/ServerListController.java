package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import network.ServerInfo;

public class ServerListController implements Initializable {
	 @FXML 
	 private TextField addServerName;
	
	 @FXML
	 private TextField serverNameText;	
	 
	 @FXML
	 private TextField serverRegionText;
	 
	 @FXML
	 private TextField serverIpAdress;

	 @FXML
	 private TextField serverPort;
	 
	 @FXML
	 private GridPane serverList;
	 
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
	 private Label errorMsgAddServer;
	 
	 @FXML
	 private Label errorMsgServer;	
	 
	 @FXML
	 private ListView<String> serverListview; 
	 
	 @FXML
	 private ScrollPane serverScrollPane;
		
	 private ArrayList<ServerInfo> serverListArray = new ArrayList<>(); 
	 
 
	 //Hides all other forms and shows the "add-server form"
	 @FXML
	 void addServerBtn_Click(ActionEvent event) {
		 addServerInfo.setVisible(true);
	 }
	 
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
						serverList.setVisible(true);
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
			serverListArray.add(server);	
				serverListview.getItems().add(server.serverName);
			}
		
		//Checks for any other server already added with the same server name
		private boolean isNotDuplicate(ServerInfo server) {		
			for (int i = 0; i < serverListArray.size(); i++)			
				{				
					ServerInfo existingServer = serverListArray.get(i);
					if (existingServer.serverName.equals(server.serverName))
						{
							return false;
						}
				}
			return true;
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
		
		private void editServer()
		{
			String selected = serverListview.getSelectionModel().getSelectedItem();
			ServerInfo selectedServer = new ServerInfo();
			System.out.println(selectedServer.serverName);
			
			for (ServerInfo serverInfo : serverListArray)
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
				addServerInfo.setVisible(true);
				addServerName.setText(selectedServer.serverName);
				System.out.println(selectedServer.serverName);
				serverIpAdress.setText(selectedServer.serverAddress);
				serverPort.setText(Integer.toString(selectedServer.port));
				
				
			}else System.out.println("fel");
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
	    void serverEditBtn_Click(ActionEvent event) {
	    	editServer();
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			serverListview.minHeightProperty().bind(serverScrollPane.heightProperty());
			serverListview.maxWidthProperty().bind(serverScrollPane.widthProperty());
			serverScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			
		}

		public void setVisible(boolean b) {
			serverList.setVisible(b);
		}
	
}
