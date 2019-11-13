package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TreeView<String> tableauChooser;
    Image folderIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        folderIcon = new Image(getClass().getResourceAsStream("folder.jpg"));

        TreeItem<String> root = getNodesForDirectory(new File("/Users/joaosousa/Desktop/Liverpool"));

        tableauChooser.setRoot(root);
    }

    public TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<>(directory.getName());

        for (File f : directory.listFiles()) {
            System.out.println("Loading :" + f.getName());

            if (f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }
}
