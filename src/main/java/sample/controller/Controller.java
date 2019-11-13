package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import loader.InstanceLoader;
import sample.objects.DataContainerDefinitionFile;
import sample.objects.DataContainerDescriptor;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class Controller implements Initializable {

    private static InstanceLoader instanceLoader;
    private static List<DataContainerDefinitionFile> tableauList;

    private Stack<List> listStack;

    @FXML
    TreeView<String> tableauChooserTree;
    @FXML
    ListView<String> tableauChooserList;
    Image folderIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        TreeItem<String> root = getNodesForDirectory(new File("/Users/joaosousa/Desktop/Liverpool"));
//        tableauChooser.setRoot(root);



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

    public static void setInstanceLoader(InstanceLoader loader) {
        Controller.instanceLoader = loader;
    }

    public static void setTableauChooserList(List<DataContainerDefinitionFile> tableauChooserList) {
        Controller.tableauChooserList = tableauChooserList;
    }
}
