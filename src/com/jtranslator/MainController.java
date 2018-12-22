package com.jtranslator;

import com.sun.webkit.dom.HTMLElementImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public WebView webContent;
    public TextField textField;
    public Button configButton;
    private Stage primaryStage;

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String url = getUrl();
            System.out.println("open url: " + url);
            webContent.getEngine().load(url);
        }
    }

    private String getUrl() {
        //return "https://dict.laban.vn/find?type=1&query=" + URLEncoder.encode(textField.getText());
        return Config.ONLINE_DICT_URL.get() + URLEncoder.encode(textField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webContent.getEngine().getLoadWorker().progressProperty().addListener(
                (o, old, progress) -> updateFields(progress));

        if(Config.ONLINE_DICT_URL.get() == null || Config.ONLINE_DICT_URL.get().trim().length() == 0) {
            Config.ONLINE_DICT_URL.set(Config.GOOGLE_TRANSLATOR.get());
        }
    }

    public Optional<String> input(String title, String header, String context, String defaultText) {
        TextInputDialog alert = new TextInputDialog(defaultText);
        alert.setWidth(500);
        setTexts(alert, title, header, context);

        return alert.showAndWait();
    }

    private void setTexts(Dialog alert, String title, String header, String context) {
        if (title != null) {
            alert.setTitle(title);
        }
        if (header != null) {
            alert.setHeaderText(header);
        }
        if (context != null) {
            alert.setContentText(context);
        }
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

    public void onConfigAction(ActionEvent actionEvent) {
        Optional<String> input = input("Config", "", "Set online dictionaty URL: ", Config.ONLINE_DICT_URL.get());
        if(input.isPresent()) {
            Config.ONLINE_DICT_URL.set(input.get());
            if(Config.ONLINE_DICT_URL.get() == null || Config.ONLINE_DICT_URL.get().trim().length() == 0) {
                Config.ONLINE_DICT_URL.set(Config.GOOGLE_TRANSLATOR.get());
            }
        }
    }

    public void onReloadAction(ActionEvent actionEvent) {

    }
}
