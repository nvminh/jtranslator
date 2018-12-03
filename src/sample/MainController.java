package sample;

import com.sun.webkit.dom.HTMLElementImpl;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public WebView webContent;
    public TextField textField;
    private Stage primaryStage;

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String url = getUrl();
            System.out.println("open url: " + url);
            webContent.getEngine().load(url);
        }
    }

    private String getUrl() {
        return "https://translate.google.com/#auto/vi/" + URLEncoder.encode(textField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webContent.getEngine().getLoadWorker().progressProperty().addListener(
                (o, old, progress) -> updateFields(progress));
    }

    private void updateFields(Number progress)
    {
        System.out.println(progress);
        Document doc = webContent.getEngine().getDocument();
        if (doc != null) {
            try {
                Element inputField = (Element)
                        XPathFactory.newInstance().newXPath().evaluate(
                                "//*[@id='result_box']",
                                doc, XPathConstants.NODE);
                if (inputField != null) {
                    System.out.println("found element:");
                    if(inputField.getChildNodes().getLength() == 1) {
                        Node node = inputField.getChildNodes().item(0);
                        //System.out.println(((HTMLElementImpl) node).getInnerText());
                        String title = textField.getText() + ": " + ((HTMLElementImpl) node).getInnerText();
                        System.out.println(title);
                        primaryStage.setTitle(title);
                    }
                    //System.out.println(inputField.getChildNodes().item(0).getNodeValue());
                }
            } catch (XPathException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void translate(String text) {
        textField.setText(text);
        String url = getUrl();
        System.out.println("open url: " + url);
        webContent.getEngine().load(url);
        primaryStage.toFront();
    }
}
