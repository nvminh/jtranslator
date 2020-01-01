package com.jtranslator;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    public ComboBox selectedDicCombo;
    public TextArea dictionariesText;

    public void onSelectedDicComboAction(ActionEvent actionEvent) {
        Config.SELECTED_DICT_URL.set(selectedDicCombo.getValue().toString());
    }

    public void onSaveAction(ActionEvent actionEvent) {
        Config.ONLINE_DICT_URLS.set(dictionariesText.getText());
        initCombo();
    }

    public void onResetAction(ActionEvent actionEvent) {
        String dics = Config.GOOGLE_TRANSLATOR.get() + "\n" + Config.HND_DIC.get();
        Config.ONLINE_DICT_URLS.set(dics);
        dictionariesText.setText(dics);
        initCombo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String dics = Config.ONLINE_DICT_URLS.get();
        if(dics == null || dics.isEmpty()) {
            dics = Config.GOOGLE_TRANSLATOR.get() + "\n" + Config.HND_DIC.get();
            Config.ONLINE_DICT_URLS.set(dics);
        }
        dictionariesText.setText(dics);

        initCombo();

    }

    private void initCombo() {
        String[] lines = dictionariesText.getText().split("[\\r\\n]+");
        selectedDicCombo.getItems().clear();
        for(String line : lines) {
            selectedDicCombo.getItems().add(line);
        }
        selectedDicCombo.setValue(Config.SELECTED_DICT_URL.get());
    }
}
