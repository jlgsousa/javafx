package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FirstDialogController implements Initializable {

    private Stage primaryStage;
    private File posRootDir;

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

        posRootDir = directoryChooser.showDialog(stage);

        Path posRootPath = Paths.get(posRootDir.getAbsolutePath());

        File installPath = new File(System.getProperty("user.dir") + "\\resources\\install");
        List<File> files = getFiles(installPath);

        copyFilesToPosRoot(files, installPath, posRootPath);

        System.out.println("Files copied successfully");
    }

    @FXML
    public void handleGetMainDescriptor(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);

        FileChooser fileChooser = new FileChooser();

        File tableauFolderOrDescriptor = fileChooser.showOpenDialog(stage);
        if (isDescriptorValid(tableauFolderOrDescriptor)) {
            // TODO: DEV HANDLING HERE

            ProcessBuilder pb = new ProcessBuilder("cmd", "/c start \"\", start_TableauEditor.bat");
            pb.directory(posRootDir);
            pb.start();
        }
    }

    @FXML
    public void handleGetTableauFolderData(ActionEvent event) {
        File tableauFolder = new File(posRootDir.getAbsolutePath() + "\\config\\standard\\parameter\\client\\tableau-data");
        if (isFolderValid(tableauFolder)) {
            // TODO: QA HANDLING HERE
            System.out.println("QA HANDLING HERE");
        }
    }

    private boolean isFolderValid(File tableauFolder) {
        if (!tableauFolder.exists()) {
            return false;
        }
        for (File file : tableauFolder.listFiles()) {
            if (file.isDirectory() || !file.getName().toLowerCase().contains("tableau")) {
                return false;
            }
        }
        return true;
    }

    private boolean isDescriptorValid(File tableauMainPosDescriptor) {
        return tableauMainPosDescriptor.getName().toLowerCase().contains("descriptor");
    }

    private List<File> getFiles(File file) {
        List<File> files = new ArrayList<>();
        if (file != null && file.isDirectory()) {
            for (File subfile : file.listFiles()) {
                files.addAll(getFiles(subfile));
            }
        } else {
            files.add(file);
        }
        return files;
    }

    private void copyFilesToPosRoot(List<File> files, File installPath, Path posRoot) {
        for (File file : files) {
            try {
                String installDir = installPath.getAbsolutePath();
                String fileDir = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\"));

                if (!installDir.equals(fileDir)) {
                    String diff = fileDir.substring(installDir.length());
                    String newDir = posRoot.toFile().getAbsolutePath() + diff;

                    new File(newDir).mkdir();

                    Path folderInRoot = Paths.get(newDir);
                    Files.copy(file.toPath(), folderInRoot.resolve(file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.copy(file.toPath(), posRoot.resolve(file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

}
