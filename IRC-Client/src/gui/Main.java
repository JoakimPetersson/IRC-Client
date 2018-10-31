package gui;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;

	public class Main extends Application {

	public static void main (String[] args) {
	Application.launch(args);
	}
	
	@Override
	public void start (Stage stage) throws Exception {
		HBox box = FXMLLoader.load(getClass().getResource("test.fxml"));
		
		Scene scene = new Scene(box);
		stage.setScene(scene);
		stage.show();
		
//		GridPane grid = new GridPane();	
//		grid.setMinWidth(600);
//		grid.setMinHeight(400);		
//		
//		grid.setHgap(10);
//		grid.setVgap(10);
//		grid.setGridLinesVisible(false);
//		
//		TableView table = new TableView();
//		table.setMinHeight(300);
//		table.setMinWidth(550);		
//		
//		HBox progressArea = new HBox();
//		progressArea.getChildren().addAll(new Label("Progress"),
//										new Spinner<Integer>(0, 100, 0),
//										new CheckBox("Completed"));
//		progressArea.setAlignment(Pos.CENTER_RIGHT);
//		progressArea.setSpacing(10);
//		//GridPane.setConstraints(progressArea, 1, 3, 2, 1);
//		
//		GridPane.setConstraints(table, 1, 1, 3, 1);
//		
//		TextField taskName = new TextField();
//		taskName.setPromptText("Enter task name here");
//		GridPane.setConstraints(taskName, 2, 2);
//		
//		TextField another = new TextField();
//		GridPane.setConstraints(another, 2, 3);
//		another.setText("Default Text");
//		
//		ComboBox priority = new ComboBox();
//		priority.getItems().addAll("High", "Low", "Medium");
//		priority.setPromptText("Enter priority");
//		GridPane.setConstraints(priority, 1, 2);
//		
//		Button addButton = new Button("Add");
//		addButton.setMinWidth(100);
//		GridPane.setConstraints(addButton, 3, 2);
//		
//		Button cancelButton = new Button("Cancel");
//		cancelButton.setMinWidth(100);
//		GridPane.setConstraints(cancelButton, 3, 3);
//		
//		TableColumn column1 = new TableColumn("Priority");
//		TableColumn column2 = new TableColumn("Description");
//		TableColumn column3 = new TableColumn("Progress");
//		table.getColumns().addAll(column1,column2,column3);
//		
//		progressArea.getChildren().add(table);
//		
//		grid.getChildren() .addAll(table, taskName, another, priority, addButton, cancelButton);
//		
//		Scene scene = new Scene(grid, 600, 400);		
//		stage.setScene(scene);
//		stage.setTitle("this is a title, bitch");
//		stage.setAlwaysOnTop(false);
//		stage.setResizable(true);
//		stage.show();
				
	
	
	}
}
