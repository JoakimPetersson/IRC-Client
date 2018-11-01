package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginWindowController implements Initializable {
	
/**
 * Properties
 */	
	 @FXML
	    private TreeView<String> treeView_login;

/**
 * Events
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		createTree();
	}	
	
	public void StartLoginScene() {
		GridPane grid;
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
	
/**
 * Methods
 */	
	private void createTree() {
		TreeItem<String> connectHeader = new TreeItem<>("Connect");
		treeView_login.setRoot(connectHeader);
		TreeItem<String> servers = new TreeItem<>("Servers");
		connectHeader.getChildren().add(servers);
	}

}


