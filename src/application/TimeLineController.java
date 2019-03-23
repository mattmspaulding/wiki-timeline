package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Properties;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import java.util.List;
/**
 * FXML Controller class
 *
 * @author Matthew Spaulding
 */
public class TimeLineController implements Initializable {
    @FXML private Label labelName;
    @FXML private Label labelSummary;
    @FXML private VBox vb;

    Document doc;
    Elements summary;
    Element firstHeading;
    Elements content;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void displayTimeline(String link){
    	// Ensures link is valid.
        try{
            doc = Jsoup.connect(link).get();    
        }
        catch(IOException e){
            System.out.print("Input not Valid!");
        }
        
        // Displays header and summary.
        loadHeader();
         
        // Gets text from wiki page and splits into sentences.
        content = doc.select("#mw-content-text > div > p:nth-child(1n+1)");
        List sentences = sentenceSplit(content.text());
        
        // Parallel arrays for sentences and the years within those sentences so we can later sort based on year.
		ArrayList<String> timeLineSentences = new ArrayList<String>();
		ArrayList<Integer> timeLineYears = new ArrayList<Integer>();
	
		// Check every sentence for a four digit number. If a sentence has a 4-digit number, add it to a new array.
		Pattern pattern = Pattern.compile("(\\d{4})");
		for(int i =0; i < sentences.size(); i++)
		{
			// Gets sentene at index.
			String sentence = (String) sentences.get(i);
			Matcher matcher = pattern.matcher(sentence);
			String val ="";
			if(matcher.find()) {
				val = matcher.group(0);
				// Creates parallel array.
				timeLineYears.add(Integer.parseInt(val));
				timeLineSentences.add((String) sentences.get(i));
			}
		}
		
		
		cleanText(timeLineYears, timeLineSentences);
		sortArrays(timeLineYears, timeLineSentences);
		
        // Add sentences to vbox as individual blocks.	
        for(int i=0;i<timeLineSentences.size(); i++) {
        	TimeLineBlockController timeLineBlockController = new TimeLineBlockController();
        	timeLineBlockController.getDate().setText(timeLineYears.get(i).toString());
        	timeLineBlockController.getSentence().setText(timeLineSentences.get(i));
        	vb.getChildren().add(timeLineBlockController);
        }
    }
     
    // Displays header name and short summary
    private void loadHeader(){
        firstHeading = doc.select("h1[id=firstHeading]").first();    
        labelName.setText(firstHeading.text());
        summary = doc.getElementsByClass("shortdescription nomobile noexcerpt noprint searchaux");
        labelSummary.setText(summary.text());      
    }
    
    // Splits text into sentences.
    public List<String> sentenceSplit(String text){
        List<String> sentenceList = new ArrayList<>();

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(text);

        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence:sentences){
            List<CoreLabel> labels = sentence.get(CoreAnnotations.TokensAnnotation.class);
            String originalString = edu.stanford.nlp.ling.SentenceUtils.listToOriginalTextString(labels);
            sentenceList.add(originalString);
        }
        return sentenceList;
    }
    
    // Cleans text from odd instances where sentences/dates are irrelevant to person's life.
    private void cleanText(ArrayList<Integer> timeLineYears, ArrayList<String> timeLineSentences) {
    	// Remove the first elements as it's a summary.
    	timeLineYears.remove(0);
		timeLineSentences.remove(0);
		
		// Remove special characters and annotations in each sentence.
		for(int i = 0; i < timeLineSentences.size(); i++)
		{
			timeLineSentences.get(i).replaceAll("[^a-zA-Z0-9\\s+]", "");
		}
		
		// Move sentences with dates before or after person's lifespan somewhere else.
		
    }
    
    // Sorts arrays by the dates within them using Bubble sort.
    private void sortArrays(ArrayList<Integer> timeLineYears, ArrayList<String> timeLineSentences) {
    	boolean swap;
    	int tempYear;
    	String tempSentence;
    	do {
    		swap = false;
    		for(int i = 0; i < timeLineYears.size()-1; i++) {
    			if(timeLineYears.get(i) > timeLineYears.get(i+1)) {
    				tempYear = timeLineYears.get(i);
    				timeLineYears.set(i, timeLineYears.get(i+1));
    				timeLineYears.set(i+1, tempYear);
    						
    				// Sort parallel array
    				tempSentence = timeLineSentences.get(i);
    				timeLineSentences.set(i, timeLineSentences.get(i+1));
    				timeLineSentences.set(i+1, tempSentence);	
    				swap = true;
    			}
    		}	
    	} while(swap);	
    }
}
