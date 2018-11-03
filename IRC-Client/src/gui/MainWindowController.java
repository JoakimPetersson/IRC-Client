package gui;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

//TODO add hover transparancy on main menu


public class MainWindowController implements Initializable {

	/******************************************************************************************
	 * Properties
	 ******************************************************************************************
	 */
	@FXML
	private GridPane gridPane;	
	
    @FXML
    private Menu menu;
    
    @FXML
    private MenuItem menu_close;
    
    @FXML
    private TreeView<String> treeview_main;
    
    @FXML
    private TabPane chatTabs;
    
    @FXML
    private Button addTestTab;   
    
    @FXML
    private Button sendBtn;
    
    @FXML
    private TextField chatText;
    
    /******************************************************************************************
     * Events
     ******************************************************************************************
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		CreateTree();		
		createChatTab("Console");
	}		

	@FXML
    void menu_close_click(ActionEvent event) {
		Platform.exit();
    }
	
	@FXML
    void menu_connect_click(ActionEvent event) {
		LoginWindowController loginWindow = new LoginWindowController();
		loginWindow.StartLoginScene();
    }
	
	@FXML
    public void addTestTab_Click(ActionEvent event) {		
		createChatTab("Test");
		
    }
	
	@FXML// Adding text from the chat-text to the window
    void sendBtn_Click(ActionEvent event) {	
		sendChatMessage();
    }
	
	@FXML
    void chatText_OnKeyDown(KeyEvent event) {
		if (event.getCode().toString().equals("ENTER")) {
			sendChatMessage();
		}
    }	
	
	/******************************************************************************************
	 * Methods
	 ******************************************************************************************
	 */
	
	private void sendChatMessage() {
		try {
			if (Helper.isEmptyOrNull(chatText.getText())) throw new NullPointerException();
		Label currentLabel = getActiveLabel();
		currentLabel.setText(currentLabel.getText() + "\n" + chatText.getText());
		chatText.setText(null);
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	}
	
	private Label getActiveLabel() {
		// Getting The Anchor-pane in the active tab
		AnchorPane currentPane = ((AnchorPane)chatTabs.getSelectionModel().getSelectedItem().getContent());
		
		// Getting The Scroll-pane in the active tab
		ScrollPane currentScroll = (ScrollPane)currentPane.getChildren().get(0);
				
		// Getting The Label in the active tab
		Label currentLabel = (Label)currentScroll.getContent();
		return currentLabel;
	}
	
	private void CreateTree() {
		
	    TreeItem<String> serverHeader = new TreeItem<>("Connected servers");	 	
	    TreeItem<String> server = new TreeItem<>("Dudenet");	 
	    treeview_main.setRoot(serverHeader);
	    serverHeader.getChildren().add(server);		
	}
	
	private Tab createChatTab(String s) {
		Tab tab = new Tab();
		tab.setText(s);
		chatTabs.getTabs().add(tab);
		AnchorPane anchor = new AnchorPane();
		tab.setContent(anchor);
		ScrollPane chatScroll = new ScrollPane();
		chatScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		chatScroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		chatScroll.setMinWidth(1605);
		chatScroll.setMaxHeight(800);
		anchor.getChildren().add(chatScroll);	
		Label l = new Label();
		l.setMaxWidth(1605);
		chatScroll.setContent(l);
		return tab;
	}	
}
