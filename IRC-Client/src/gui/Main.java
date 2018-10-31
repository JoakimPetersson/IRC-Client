package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
