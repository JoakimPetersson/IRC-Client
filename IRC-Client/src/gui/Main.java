package gui;

import java.net.URL;

import javax.print.attribute.standard.PrinterMoreInfoManufacturer;

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
		loader.setLocation(new URL("file://F:/Programering/Java/IRCclient/IRC-Client/IRC-Client/src/gui/MainWindow.fxml"));
		VBox vbox = loader.<VBox>load();
		
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	

}
