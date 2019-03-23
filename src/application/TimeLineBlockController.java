package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
/**
 * FXML Controller class
 *
 * @author Matthew Spaulding
 */
public class TimeLineBlockController extends AnchorPane implements Initializable{
	@FXML private Label date;
	@FXML private Label sentence;
	@FXML private Circle circle;
	
	public TimeLineBlockController() {
		FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("/application/TimeLineBlock.fxml"));	
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch(IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public Label getDate() {
		return date;
	}

	public void setDate(Label date) {
		this.date = date;
	}

	public Label getSentence() {
		return sentence;
	}

	public void setSentence(Label sentence) {
		this.sentence = sentence;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}


}
