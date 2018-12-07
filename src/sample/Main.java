package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;

public class Main extends Application {

    MainController mainController;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        mainController.setStage(primaryStage);
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
    }

    private void translate(Stage primaryStage, String string) {
        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
//            Parent root = loader.load();
//            MainController mainController = loader.getController();
//            mainController.setStage(primaryStage);
//            primaryStage.setTitle("Translator");
//            //primaryStage.setScene(new Scene(root, 850, 550));
            primaryStage.show();
            mainController.translate(string);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
