package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		primaryStage.setTitle("This is a title");
		
		primaryStage.show();
	}
	
	public static void main (String[] args) {
		Application.launch(args);
	}

}
