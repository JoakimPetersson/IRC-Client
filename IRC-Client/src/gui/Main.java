package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


	public class Main extends Application {
		public Stage currentMainStage;

	public static void main (String[] args) {
	Application.launch(args);	
	}
	
	@Override
	public void start (Stage stage) throws Exception {		
		MainWindowController mainWindow = new MainWindowController();
		
		/**
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		root = loader.load();
		//Stage stage = new Stage();
		stage.setTitle("Settings");
		stage.setScene(new Scene(root, 450, 450));
		stage.show();
		
		System.out.println(loader.getController().toString());
		System.out.println(this.toString());
		**/
		
		mainWindow.start(stage);
	}	
	
}
