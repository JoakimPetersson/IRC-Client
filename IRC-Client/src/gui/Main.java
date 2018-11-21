package gui;

/**
 * Features to add:
 * 		Chat logger / Saving previous channel and server activity
 * 		Connect to servers that require a password
 * 		Fullscreen mode
 * 		Connect to channels on startup
 * 		React to /join and similar commands by opening/closing treeitems
 * 
 * Feature to skip:
 * 		bots/ plugins /scripts (DO NOT DO THIS! way to big project)
 * 		Sound
 * 		advanced gui customization and preferences
 * 		file transfer and network settings
 */


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
