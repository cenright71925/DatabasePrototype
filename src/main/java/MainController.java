import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML Button btnDownload, btnRead, btnModify, btnUpdate;
    @FXML TableView<Node> tableView;
    @FXML TableColumn<Node, String> nodeIDCol;
    @FXML TableColumn<Node, Integer> xCoordCol;
    @FXML TableColumn<Node, Integer> yCoordCol;
    @FXML TableColumn<Node, Integer> floorCol;
    @FXML TableColumn<Node, String> buildingCol;
    @FXML TableColumn<Node, String> nodeTypeCol;
    @FXML TableColumn<Node, String> longNameCol;
    @FXML TableColumn<Node, String> shortNameCol;
    private static ObservableList<Node> tableNodeList = FXCollections.observableArrayList();

    /**
     * public void initialize(URL location, ResourceBundle resources) - initializes the scene for controlling nodes
     * @param location not used
     * @param resources not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nodeIDCol.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
        xCoordCol.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
        yCoordCol.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<>("building"));
        nodeTypeCol.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
        longNameCol.setCellValueFactory(new PropertyValueFactory<>("longName"));
        shortNameCol.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        btnModify.setDisable(true);
        btnDownload.setDisable(true);
        btnUpdate.setDisable(true);
    }

    /**
     * public void downloadOnClick() - controller to download the new csv file to replace the old one
     * @throws IOException standard input output exception thrown by Javafx controller methods
     */
    @FXML
    public void downloadOnClick() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DownloadScene.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * readOnClick() - reads in the csv file and populates the table with nodes
     * a few buttons are now enabled so the user can now manipulate the data
     */
    @FXML
    public void readOnClick(){
        DBController.readFile();
        System.out.println("file has been read");
        populateTableWithNodes();
        btnDownload.setDisable(false);
        btnModify.setDisable(false);
        btnUpdate.setDisable(false);
        System.out.println("read on click has been finished");
    }

    /**
     * public void modifyOnClick() - creates a new stage so admin can modify nodes as needed
     * @throws IOException standard input output exception thrown by Javafx controller methods
     */
    @FXML
    public void modifyOnClick() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ModifyScene.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * public void updateOnClick() - clears the table and repopulates it using the internal list
     * calls populateTableWithNodes()
     */
    @FXML
    public void updateOnClick(){
        tableNodeList.clear();
        populateTableWithNodes();
    }

    /**
     * private void populateTableWithNodes() - repopulates the table using the internal list
     * updates the list and table lengths, helper method
     */
    private void populateTableWithNodes(){
        LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
        for(Node n: fileReaderNodeList) {tableNodeList.add(n);}
        System.out.println("List length is " + fileReaderNodeList.size());
        System.out.println("Table length is " + tableNodeList.size());
        tableView.setItems(tableNodeList);
    }

}