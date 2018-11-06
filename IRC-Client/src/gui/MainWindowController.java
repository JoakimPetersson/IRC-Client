package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import network.UserInfo;

//TODO add hover transparency on main menu
//TODO add option to increase/decrease the fontsize
//TODO remake the tabs into treeitems for chanels
//TODO "Save state" for chanels and such


public class MainWindowController implements Initializable {	
	

	/******************************************************************************************
	 * Properties
	 ******************************************************************************************
	 */
	
    @FXML
    private TextArea chatTextOut;
    
	@FXML
	private LoginWindowController login;
	
	@FXML
	private GridPane gridPane;	
	
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
    private ScrollPane chanelUserListScrollPane;    
    
    private ArrayList<UserInfo> createdUsers = new ArrayList<UserInfo>();    
    public ArrayList<UserInfo> GetCreatedUsers()
		{
				return createdUsers;
		}

	public void AddCreatedUser(UserInfo userToAdd)
		{
				createdUsers.add(userToAdd);
		}
	
	private int fontSize;
	
	public int getFontSize()
		{
				return fontSize;
		}

	public void setFontSize(int fontSize)
		{
				this.fontSize = fontSize;
		}
    
    /******************************************************************************************
     * Events
     ******************************************************************************************
     */
    
    //Creates a treeview on the left side, showing the currently connected servers
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		chatTextOut.setEditable(false);
		
		CreateTree();		
		
		ListView<String> chanelUserList = new ListView<String>();
		chanelUserListScrollPane.setContent(chanelUserList);
		chanelUserList.prefWidthProperty().bind(chanelUserListScrollPane.widthProperty());
		chanelUserList.minHeightProperty().bind(chanelUserListScrollPane.heightProperty());
		
		
		
	}		

	//Shuts down the app when you click the "close" button on the main-menu under "file"
	@FXML
    void menu_close_click(ActionEvent event) {
		Platform.exit();
    }
	
	//Shows options for server- and user options when you click the "Connect"-button under "file"
	@FXML
    void menu_connect_click(ActionEvent event) {
		LoginWindowController loginWindow = new LoginWindowController();
		loginWindow.Start(this);
    }
	
	// Adding text from the chat-text to the chat-window when you click the "send" button
	@FXML
    void sendBtn_Click(ActionEvent event) {	
		sendChatMessage();
    }
	
	//Sends the content of the chat-text to the chat-window when you press the enter-key
	@FXML
    void chatText_OnKeyDown(KeyEvent event) {
		if (event.getCode().toString().equals("ENTER")) {
			sendChatMessage();
		}
    }	
	
    @FXML
    void addUserPh_Click(ActionEvent event) {
    	ListView<String> chanelUserList = (ListView<String>) chanelUserListScrollPane.getContent();
    	chanelUserList.getItems().add("User");
    }
	
	/******************************************************************************************
	 * Methods
	 ******************************************************************************************
	 */
	
	//Takes the text from the chat-text and adds to the chat-window, if null, empty or whitespace nothing happens 
	private void sendChatMessage() {
		try {
			if (Helper.isEmptyOrNull(chatTextIn.getText())) throw new NullPointerException();
		//Label currentLabel = getActiveLabel();
		//currentLabel.setText(currentLabel.getText() + chatText.getText().toString() + "\n");
		chatTextIn.setText(null);
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	}
	
	//sets the "currentLabel" variable to the currently active chat-window
	/*private Label getActiveLabel() {
		// Getting The Anchor-pane in the active tab
		AnchorPane currentPane = ((AnchorPane)chatTabs.getSelectionModel().getSelectedItem().getContent());
		
		// Getting The Scroll-pane in the active tab
		ScrollPane currentScroll = (ScrollPane)currentPane.getChildren().get(0);
				
		// Getting The Label in the active tab
		Label currentLabel = (Label)currentScroll.getContent();
		return currentLabel;
	}*/
	
	//sets up the treeitem-menu to the left of the app
	private void CreateTree() {		
	    TreeItem<String> serverHeader = new TreeItem<>("Connected servers");	 	
	    TreeItem<String> server = new TreeItem<>("Dudenet");
	    TreeItem<String> chanel = Helper.makeBranch("#Buffbois", server);
	    treeview_main.setRoot(serverHeader);
	    treeview_main.setShowRoot(false);
	    serverHeader.getChildren().add(server);		
	}
	
	//creates a new chat-window and places it in a new tab
	/*public Tab createChatTab(String s) {
		Tab tab = new Tab();
		tab.setText(s);
		chatTabs.getTabs().add(tab);
		AnchorPane anchor = new AnchorPane();		
		tab.setContent(anchor);
		ScrollPane chatScroll = new ScrollPane();
		chatScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		chatScroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		anchor.getChildren().add(chatScroll);	
		chatScroll.minWidthProperty().bind(chatTabs.widthProperty());
		chatScroll.prefHeightProperty().bind(chatTabs.heightProperty().subtract(29));		
		Label l = new Label();
		chatScroll.vvalueProperty().bind(l.heightProperty());
		l.isWrapText();
		chatScroll.setContent(l);
		return tab;
	}*/	
	
	public void start(Stage stage) throws IOException {
		GridPane grid = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));		
		Scene scene = new Scene(grid);	
		stage.setScene(scene);
		stage.show();
	}
}
