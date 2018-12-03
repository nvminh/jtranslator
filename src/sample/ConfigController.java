package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    public RadioButton radioUseProxy;
    public RadioButton radioNoneProxy;
    public TextField txtHost;
    public TextField txtPort;
    public TextField txtUserName;
    public PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
