package gui;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

	public class Main extends Application {

	public static void main (String[] args) {
	launch(args);
	}
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		String path = new File("").getAbsolutePath();
		String fullPath = "file:" + File.separator + File.separator + File.separator + path + 
				File.separator+ "src" + File.separator + "gui" + File.separator + "MainWindow.fxml";
		System.out.println(fullPath);
		loader.setLocation(new URL(fullPath));
		VBox vbox = loader.<VBox>load();
		
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}	

}
