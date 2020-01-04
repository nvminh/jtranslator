package com.nvm.onlinetranslator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Main extends Application  {

    MainController mainController;
    String preText;
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        mainController.setStage(primaryStage, this);
        primaryStage.setTitle("Translator");
        primaryStage.setScene(new Scene(root, 850, 550));
        primaryStage.show();

        final Clipboard systemClipboard = Clipboard.getSystemClipboard();

        new com.sun.glass.ui.ClipboardAssistance(com.sun.glass.ui.Clipboard.SYSTEM) {
            @Override
            public void contentChanged() {
                System.out.print("System clipboard content changed: ");
                if ( systemClipboard.hasImage() ) {
                    System.out.println("image");
                } else if ( systemClipboard.hasString() ) {
                    translate(primaryStage, systemClipboard.getString());
                } else if ( systemClipboard.hasFiles() ) {
                    System.out.println("files");
                }
            }
        };

        ScheduledService<Void> svc = new ScheduledService<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() {
                        try {
                            Platform.runLater(()->translate(primaryStage, systemClipboard.getString()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
//                        translate(primaryStage, systemClipboard.getString());
                        return null;
                    }
                };
            }
        };
        svc.setPeriod(Duration.seconds(1));
        svc.start();
    }

    private void translate(Stage primaryStage, String text) {
        try {
            if(!Objects.equals(preText, text)) {
                preText = text;
                primaryStage.show();
                primaryStage.toFront();
                mainController.translate(text);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
