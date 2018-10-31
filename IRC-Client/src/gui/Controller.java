package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements Initializable {

    @FXML
    private Button btn1;
    
    @FXML
    private TextField text;
    
    @FXML
    private Menu menu;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn1.setText("hello there");
		text.setText("nothing here");		
	}
	
	@FXML
    void btn1_click(ActionEvent event) {
		text.setText("now something is working");
    }

}
