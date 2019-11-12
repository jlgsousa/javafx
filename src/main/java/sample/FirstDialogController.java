package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstDialogController implements Initializable {

    private Stage primaryStage;

    @FXML
    Button rootDir;
    @FXML
    Button rootTableau;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleGetPosRootDir(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);

        DirectoryChooser directoryChooser = new DirectoryChooser();

        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        System.out.println("selected directory");
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

}
