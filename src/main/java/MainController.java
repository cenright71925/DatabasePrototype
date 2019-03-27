import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
//import main.java.Node;//i had to comment this out fot it to run


import java.io.File;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //btnModify.setDisable(true); //code to disable the Modify Node button until a node is selected from the table

        nodeIDCol.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
        xCoordCol.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
        yCoordCol.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<>("building"));
        nodeTypeCol.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
        longNameCol.setCellValueFactory(new PropertyValueFactory<>("longName"));
        shortNameCol.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        //tableView.setItems(getNodes());
        DBController.readFile();
        btnModify.setDisable(true);
        btnDownload.setDisable(true);
        btnUpdate.setDisable(true);

    }

    @FXML
    public void downloadOnClick(ActionEvent event){
        LinkedList<Node> nodeList = DBController.getNodeList();
        CSVMaker.makeCSVFile(nodeList);
        //here put code that needs to run to download the new CSV file
    }

    @FXML
    public void readOnClick(ActionEvent event){
        //here put code to start the reading of the CSV file and populating the table full of data

        tableNodeList.removeAll();
        LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
        //System.out.println(FileReader.nodeList);
        for(Node n: fileReaderNodeList)
        {
            tableNodeList.add(n);
        }
        System.out.println("List length is " + fileReaderNodeList.size());
        tableView.setItems(tableNodeList);
        btnDownload.setDisable(false);
        btnModify.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    public void modifyOnClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ModifyScene.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    @FXML
    public void updateOnClick(){
        tableNodeList.clear();
        LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
        for(Node n: fileReaderNodeList)
        {
            tableNodeList.add(n);
        }
        System.out.println("List length is " + fileReaderNodeList.size());
        System.out.println("Table length is " + tableNodeList.size());
        tableView.setItems(tableNodeList);
    }



}