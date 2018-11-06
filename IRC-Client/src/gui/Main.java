package gui;

import javafx.application.Application;
import javafx.stage.Stage;


	public class Main extends Application {
		public Stage currentMainStage;

	public static void main (String[] args) {
	Application.launch(args);	
	}
	
	@Override
	public void start (Stage stage) throws Exception {		
		MainWindowController mainWindow = new MainWindowController();
		mainWindow.start(stage);
	}	
	
}
