package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

	@FXML
	private Button serverConnectBtn;
	
	private ObservableList<String> allServerNames;
	
	/**
	 * 
	 *	Events 
	 *
	 */
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
	}
	
	@FXML
	void serverDeleteBtn_Click(ActionEvent event) {
		removeSelectedItem();
	}
	
	@FXML
	void serverConnectBtn_Click(ActionEvent event) {
		
		
		
		// TODO Close window
		// TODO Add server to main window server list
		// TODO Connect to the selected server
		// TODO join all preselected channels and add them as tabs in the main window server list under the joined server
		/*
		 * 
		 * This event should somehow pass the selected server name to the MainWindowController, and call some function there. And then close this window and let the
		 * MainWindowController handle everything from there.
		 * 
		 * ServerInfo might need some new fields, like:
		 * 		boolean connectOnStartup; - connect to this server when program starts 
		 * 		
		 * 		For much later:
		 * 			ArrayList<String> joinChannelsOnConnect; - join these channels after connecting to the server
		 * 			Fields that handle passwords and nickserv logins. I don't know how this works and need to read about it.
		 * 			UserInfo CustomUserInfo; - Custom userinfo for the server in case you don't want to use the same info on all servers
		 * 
		 */
	}

	@FXML
	void serverEditBtn_Click(ActionEvent event) {
		// editServer();
	}
	
	// Looks up what server is currently selected and removes it from the treeview
	// Shows error-message if none is selected
	private void removeSelectedItem() {
		String selected = serverListview.getSelectionModel().getSelectedItem();
		try {

			ReadOnlyObjectProperty<String> name = serverListview.getSelectionModel().selectedItemProperty();

			System.out.println("REMOVED SERVER: " + name.get());

			PreferenceHandler prefs = new PreferenceHandler();
			prefs.removeServer(name.get());

			serverListview.getItems().remove(selected);
		} catch (NullPointerException e) {
			errorMsgServer.setText("No server selected, no server deleted");
		}
	}

	/*
	 * 
	 *	Other Methods
	 * 
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		serverListview.minHeightProperty().bind(serverScrollPane.heightProperty());
		serverListview.maxWidthProperty().bind(serverScrollPane.widthProperty());
		serverScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		allServerNames = FXCollections.observableArrayList();

		loadServerList();

		Preferences prefs = Preferences.userRoot().node("serverInfo");

		prefs.addPreferenceChangeListener(new PreferenceChangeListener() {

			@Override
			public void preferenceChange(PreferenceChangeEvent arg0) {
				Gson gson = new Gson();
				ServerInfo serverInfo = gson.fromJson(arg0.getNewValue(), ServerInfo.class);
				allServerNames.add(serverInfo.serverName);
			}

		});

		allServerNames.addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change arg0) {

				// Using platform.runlater to avoid JavaFX thread error
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						serverListview.setItems(allServerNames);
					}
				});
			}
		});
	}

	private void loadServerList() {
		PreferenceHandler prefs = new PreferenceHandler();
		ArrayList<ServerInfo> allServerInfo = prefs.getAllServerInfo();

		if (allServerInfo != null) {
			for (ServerInfo info : allServerInfo) {
				allServerNames.add(info.serverName);
				serverListview.getItems().add(info.serverName);
			}
		}
	}

	public void setVisible(boolean b) {
		serverListPane.setVisible(b);
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
}
