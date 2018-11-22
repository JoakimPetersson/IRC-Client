package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import network.UserInfo;

//TODO add hover transparency on main menu
//TODO add option to increase/decrease the fontsize
//TODO remake the tabs into treeitems for channels
//TODO "Save state" for chanels and such

public class MainWindowController implements Initializable {
	
	/******************************************************************************************
	 * Properties
	 ******************************************************************************************
	 */

	@FXML
	private SettingsWindowController settingsWindowController;

	@FXML
	private GridPane mainWindow;

	@FXML
	private Menu menu;

	@FXML
	private MenuItem menu_close;

	@FXML
	private TreeView<String> treeview_main;

	@FXML
	private Button sendBtn;

	@FXML
	private Button addUserPh;

	@FXML
	private TextField chatTextIn;

	@FXML
	private ScrollPane channelUserListScrollPane;
	
	private ArrayList<UserInfo> createdUsers = new ArrayList<UserInfo>();

	public ArrayList<UserInfo> GetCreatedUsers() {
		return createdUsers;
	}

	public void AddCreatedUser(UserInfo userToAdd) {
		createdUsers.add(userToAdd);
	}

	private int fontSize;

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public TreeItem<String> serverHeader;
	
	private ConnectEventBus connectEventBus;

	/******************************************************************************************
	 * Events
	 ******************************************************************************************
	 */

	// Creates a treeview on the left side, showing the currently connected servers
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CreateTree();

		ListView<String> chanelUserList = new ListView<String>();
		channelUserListScrollPane.setContent(chanelUserList);
		chanelUserList.prefWidthProperty().bind(channelUserListScrollPane.widthProperty());
		chanelUserList.minHeightProperty().bind(channelUserListScrollPane.heightProperty());
		
		connectEventBus = ConnectEventBus.getInstance();
		EventBus eBus = connectEventBus.getEventBus();
		eBus.register(new Object() {
			@Subscribe
			void handleConnectEvent(String serverName) {
				connectToServer(serverName);
			}
		});
		
	}

	// Shuts down the app when you click the "close" button on the main-menu under
	// "file"
	@FXML
	void menu_close_click(ActionEvent event) {
		Platform.exit();
	}

	// FIXME Change name of this action to something more relevant
	@FXML
	void menu_connect_click(ActionEvent event) {
		try {	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/SettingsWindow.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Settings");
			stage.setScene(new Scene(root, 450, 450));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Adding text from the chat-text to the chat-window when you click the "send"
	// button
	@FXML
	void sendBtn_Click(ActionEvent event) {
		sendChatMessage();
	}

	// Sends the content of the chat-text to the chat-window when you press the
	// enter-key
	@FXML
	void chatText_OnKeyDown(KeyEvent event) {
		if (event.getCode().toString().equals("ENTER")) {
			sendChatMessage();
		}
	}

	// FIXME adduser button is not needed, BUT the function can be used later to add users from the server userlist
	@FXML
	void addUserPh_Click(ActionEvent event) {
		ListView<String> channelUserList = (ListView<String>) channelUserListScrollPane.getContent();
		channelUserList.getItems().add("User");
	}

	/******************************************************************************************
	 * Methods
	 ******************************************************************************************
	 */

	// Takes the text from the chat-text and adds to the chat-window, if null, empty
	// or whitespace nothing happens
	private void sendChatMessage() {
		try {
			if (isEmptyOrNull(chatTextIn.getText()))
				throw new NullPointerException();
			// Label currentLabel = getActiveLabel();
			// currentLabel.setText(currentLabel.getText() + chatText.getText().toString() +
			// "\n");
			chatTextIn.setText(null);
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	}

	// sets up the treeitem-menu to the left of the app
	private void CreateTree() {
		serverHeader = new TreeItem<>("Connected servers");
		treeview_main.setRoot(serverHeader);
		treeview_main.setShowRoot(false);
	}
	
	// TODO This function should handle the visual parts of the program, the actual connection should be decouple as much as possible
	public void connectToServer(String serverName) {
		// Get serverinfo
		// open new treethingy with tab in focus
		// Connect to server (Store connectionhandlers in a collection?)
		
		//TreeItem<String> serverHeader = new TreeItem<>("Connected servers");
		TreeItem<String> server = new TreeItem<>(serverName);
		//TreeItem<String> channel = Helper.makeBranch("#Buffbois", server);
		treeview_main.setRoot(serverHeader);
		treeview_main.setShowRoot(false);
		serverHeader.getChildren().add(server);	
	}
	
	public void start(Stage stage) throws IOException {
		GridPane grid = FXMLLoader.load(getClass().getResource("fxml/MainWindow.fxml"));
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		stage.show();
	}
	
	public static TreeItem<String> makeBranch(String title, TreeItem<String> parent){
		TreeItem<String> item = new TreeItem<String>(title);
		parent.getChildren().add(item);
		return item;
}
	
	public static boolean isEmptyOrNull(final String s) {
		return s == null || s.trim().isEmpty();		
	}
}
