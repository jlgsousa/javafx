package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.FirstDialogController;


public class FirstDialog extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstDialog.fxml"));
        Parent root = fxmlLoader.load();

        ((FirstDialogController)fxmlLoader.getController()).setStage(primaryStage);

        primaryStage.setTitle("TARDIS choose directory");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
