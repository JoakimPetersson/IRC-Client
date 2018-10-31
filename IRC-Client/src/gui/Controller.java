package gui;

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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller implements Initializable {

        
    @FXML
    private Menu menu;
    
    @FXML
    private MenuItem menu_close;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	
	@FXML
    void menu_close_click(ActionEvent event) {
		Platform.exit();
    }
}
