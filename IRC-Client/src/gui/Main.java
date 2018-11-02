package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


	public class Main extends Application {

	public static void main (String[] args) {
	Application.launch(args);	
	}
	
	@Override
	public void start (Stage stage) throws Exception {
		
		GridPane grid = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));		
		Scene scene = new Scene(grid);	
		stage.setScene(scene);
		stage.show();		
	}	
	
}
