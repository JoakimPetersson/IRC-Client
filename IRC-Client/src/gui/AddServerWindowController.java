package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddServerWindowController implements Initializable {
	
	/**
	 * Properties
	 */
	@FXML
 		private TextField addServerName;

	@FXML
		private Button addServerCancel;

	@FXML
    	private Button addServerOk; 
	
		private LoginWindowController WindowLogin;
		
		public LoginWindowController getWindowLogin() {
			return WindowLogin;
		}

		public void setWindowLogin(LoginWindowController lastWindow) {
			WindowLogin = lastWindow;
		}
	
	/**
	 * Events
	 */
	
	@FXML
	   void addServerOk_Click(ActionEvent event) {
	    getWindowLogin().makeBranch(addServerName.getText().toString(), getWindowLogin().serverRoot);
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void StartAddserverScene(Class lastWindow) {
		setWindowLogin(lastWindow);
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
