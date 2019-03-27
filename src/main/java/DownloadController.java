import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.LinkedList;

public class DownloadController {

    @FXML
    Button btnDownload;
    @FXML
    Button btnOverwrite;


    @FXML
    public void downloadOnClick(){
        LinkedList<Node> nodeList = DBController.getNodeList();
        CSVMaker.makeCSVFile(nodeList);
        Stage stage;
        stage = (Stage) btnDownload.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void overwriteOnClick(){

        Stage stage;
        stage = (Stage) btnDownload.getScene().getWindow();
        stage.close();
    }
}
