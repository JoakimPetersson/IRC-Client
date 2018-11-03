package gui;
//TODO add hover transparancy on main menu 
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.text.AttributeSet.CharacterAttribute;

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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {

	/******************************************************************************************
	 * Properties
	 ******************************************************************************************
	 */
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
    
    private Tab[] tabArray = new Tab[999];
    
    /******************************************************************************************
     * Events
     ******************************************************************************************
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		CreateTree();
		Label consoleText = new Label();
		
		createChatTab("Console", consoleText);
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
		Label chatText = new Label();		
		tabArray[0] = createChatTab("Test", chatText);
		
    }
	
	@FXML
    void sendBtn_Click(ActionEvent event) {
		System.out.println(chatTabs.getSelectionModel().getSelectedItem().getContent());
    }
	/******************************************************************************************
	 * Methods
	 ******************************************************************************************
	 */
	
	private void CreateTree() {
		
	    TreeItem<String> serverHeader = new TreeItem<>("Connected servers");	 	
	    TreeItem<String> server = new TreeItem<>("Dudenet");	 
	    treeview_main.setRoot(serverHeader);
	    serverHeader.getChildren().add(server);		
	}
	
	private Tab createChatTab(String s, Label l) {
		Tab tab = new Tab();
		tab.setText(s);
		chatTabs.getTabs().add(tab);
		AnchorPane anchor = new AnchorPane();
		tab.setContent(anchor);
		ScrollPane chatScroll = new ScrollPane();
		chatScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		chatScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		chatScroll.setMinWidth(1605);
		chatScroll.setMinHeight(854);
		anchor.getChildren().add(chatScroll);				
		l.setMinWidth(1605);
		l.setMinHeight(854);
		chatScroll.setContent(l);
		return tab;
	}	
}
