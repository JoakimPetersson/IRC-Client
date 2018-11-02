package gui;
//TODO add hover transparancy on main menu 
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
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
    TreeView<String> treeview_main;
    
    /******************************************************************************************
     * Events
     ******************************************************************************************
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		CreateTree();
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
	
}
