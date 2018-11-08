package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import network.ServerInfo;

public class ServerListPaneController implements Initializable {
	@FXML
	private TextField addServerName;

	@FXML
	private GridPane serverListPane;

	@FXML
	private GridPane addServerInfo;

	@FXML
	private Button addServerBtn;

	@FXML
	private Button serverDeleteBtn;

	@FXML
	private Button serverEditBtn;

	@FXML
	private Label errorMsgServer;

	@FXML
	private ListView<String> serverListview;

	@FXML
	private ScrollPane serverScrollPane;

	@FXML
	private AddServerWindowController addServerWindow;

	// private ArrayList<ServerInfo> serverListArray = new ArrayList<>();

	// Hides all other forms and shows the "add-server form"
	@FXML
	void addServerBtn_Click(ActionEvent event) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("AddServerWindow.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Add Server");
			stage.setScene(new Scene(root, 450, 450));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// addServerInfo.setVisible(true);

		// Set up addServerWindow and pass serverListArray to it

	}

	// Looks up what server is currently selected and removes it from the treeview
	// Shows error-message if none is selected
	private void removeSelectedItem() {
		String selected = serverListview.getSelectionModel().getSelectedItem();
		try {
			serverListview.getItems().remove(selected);
		} catch (NullPointerException e) {
			errorMsgServer.setText("No server selected, no server deleted");
		}
	}

	/**
	 * private void editServer() { String selected =
	 * serverListview.getSelectionModel().getSelectedItem(); ServerInfo
	 * selectedServer = new ServerInfo();
	 * System.out.println(selectedServer.serverName);
	 * 
	 * for (ServerInfo serverInfo : serverListArray) { if
	 * (selected.equals(serverInfo.serverName)){ selectedServer.serverName =
	 * serverInfo.serverName; selectedServer.serverAddress =
	 * serverInfo.serverAddress; selectedServer.port = serverInfo.port;
	 * System.out.println(serverInfo.serverName); break; } }
	 * System.out.println(selectedServer.serverName); if (selected != null) {
	 * addServerInfo.setVisible(true);
	 * addServerName.setText(selectedServer.serverName);
	 * System.out.println(selectedServer.serverName);
	 * serverIpAdress.setText(selectedServer.serverAddress);
	 * serverPort.setText(Integer.toString(selectedServer.port));
	 * 
	 * 
	 * }else System.out.println("fel"); }
	 */

	// Adds the server put in in the "add-server"-form to the tree-item menu showing
	// current servers
	// Server-name cannot be null, empty or whitespace, shows an error-message when
	// this is the case
	// Hides all forms then shows the currently added servers

	// removes the selected server or server-region when you click the delete-button
	@FXML
	void serverDeleteBtn_Click(ActionEvent event) {
		removeSelectedItem();
	}

	// Takes the info from the "create user"-form and creates the user
	// Adds the created user to the "createdUser-list"

	// Takes the info from the create user-form and sets up a new user
	// Username and/or nickname cannot be empty, whitespace or null, shows
	// error-message on screen

	@FXML
	void serverEditBtn_Click(ActionEvent event) {
		// editServer();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		serverListview.minHeightProperty().bind(serverScrollPane.heightProperty());
		serverListview.maxWidthProperty().bind(serverScrollPane.widthProperty());
		serverScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

	}

	public void setVisible(boolean b) {
		serverListPane.setVisible(b);
	}

}
