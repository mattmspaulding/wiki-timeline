package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
/**
 * FXML Controller class
 *
 * @author Matthew
 */
public class MainController implements Initializable {
    
    @FXML private TextField wiki_link;
    private String link;
    private TimeLineController timeline = new TimeLineController();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wiki_link.setFocusTraversable(false);
    }    

    @FXML
    private void handleTextField(ActionEvent event) {
        
        link = wiki_link.getText();
       
        try{
            Document doc = Jsoup.connect(link).get();  
        }catch(IOException e){
            System.out.print("Input not Valid!");
        }
        loadStage("TimeLineFXML.fxml");
    }
    
    private void loadStage(String fxml){
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();       
            timeline = (TimeLineController) loader.getController();
            timeline.displayTimeline(link);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
}
