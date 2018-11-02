package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddServerWindowController {
	
	/**
	 * Properties
	 */
	@FXML
 		private TextField addServerName;

	@FXML
		private Button addServerCancel;

	@FXML
    	private Button addServerOk; 	
	
	/**
	 * Events
	 */
	
	@FXML
	   void addServerOk_Click(ActionEvent event) {
		LoginWindowController loginWindow = new LoginWindowController();
	    loginWindow.makeBranch(addServerName.getText().toString(), loginWindow.serverRoot);
	    System.out.println("asljdjals");
	}
	
	@FXML
        void addServerCancel_Click(ActionEvent event) {
		Stage stage = (Stage) addServerCancel.getScene().getWindow();
    	stage.close();
    } 
	
	/**
	 * Methods
	 */
	
	public void StartAddserverScene() {
		GridPane grid;
		try {
			grid = FXMLLoader.load(getClass().getResource("AddServerWindow.fxml"));		
		Stage stage = new Stage();
		Scene scene = new Scene(grid);	
		stage.setScene(scene);
		stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
