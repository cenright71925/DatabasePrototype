import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.LinkedList;

public class DownloadController {
    @FXML
    Button btnDownload;
    @FXML
    Button btnOverwrite;

    /**
     * public void downloadOnClick() - creates a new csv and downloads it
     */
    @FXML
    public void downloadOnClick(){
        LinkedList<Node> nodeList = DBController.getNodeList();
        CSVMaker.makeCSVFile(nodeList, "newCSVfile.csv");
        Stage stage;
        stage = (Stage) btnDownload.getScene().getWindow();
        stage.close();
    }

    /**
     * public void overwriteOnClick() - overwrites the old csv
     */
    @FXML
    public void overwriteOnClick(){
        LinkedList<Node> nodeList = DBController.getNodeList();
        CSVMaker.makeCSVFile(nodeList, "PrototypeNodes.csv");
        Stage stage;
        stage = (Stage) btnDownload.getScene().getWindow();
        stage.close();
    }
}
