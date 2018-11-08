package gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import network.ServerInfo;

public class AddServerWindowController {	
	
	@FXML
	private BorderPane addServerWindow;
	
	@FXML
	private Button addServerOKbtn;
	
	@FXML
	private TextField serverNameText;	
	 
	@FXML
	private TextField serverRegionText;
	 
	@FXML
	private TextField serverIpAdress;
	
	@FXML
	private TextField serverPort;
	
	@FXML
	private Label errorMsgAddServer;
	
	private ArrayList<ServerInfo> serverListArray = new ArrayList<>(); 
	
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
					//addServerToList(currentServer);
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
	/*
	private void addServerToList(ServerInfo server){		
		serverListArray.add(server);	
			serverListview.getItems().add(server.serverName);
		}
	*/
	
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
	
	@FXML
	void addServerOKbtn_Click(ActionEvent event) {	
		CreateServer();
	}
	
}
