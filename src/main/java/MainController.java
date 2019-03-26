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
    @FXML Button btnDownload, btnRead, btnModify;
    @FXML TableView<Node> tableView;
    @FXML TableColumn<Node, String> nodeIDCol;
    @FXML TableColumn<Node, Integer> xCoordCol;
    @FXML TableColumn<Node, Integer> yCoordCol;
    @FXML TableColumn<Node, Integer> floorCol;
    @FXML TableColumn<Node, String> buildingCol;
    @FXML TableColumn<Node, String> nodeTypeCol;
    @FXML TableColumn<Node, String> longNameCol;
    @FXML TableColumn<Node, String> shortNameCol;

    private static ObservableList<Node> nodes = FXCollections.observableArrayList();



    public static void addNode(Node n)
    {
        nodes.add(n);
    }

    @Override //this code runs as soon as the program boots up. start() in the Main class also does that. but here is for UI things
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
        tableView.setItems(getNodes());
    }

    public ObservableList<Node> getNodes(){

        //ObservableList<Node> nodes = FXCollections.observableArrayList();
        //nodes.add(new Node("BCONF00112", 675, 1231, 2, "45 Francis", "CONF", "Duncan Reef Conference Room", "B0102"));
        //nodes.add(new Node("BCONF12321", 456, 1211, 3, "35 Camiles", "CONF", "Big Ass Room", "BAR"));

        return nodes;
    }

    @FXML
    public void downloadOnClick(ActionEvent event){
        //here put code that needs to run to download the new CSV file
    }

    @FXML
    public void readOnClick(ActionEvent event){
        //here put code to start the reading of the CSV file and populating the table full of data
        FileReader.readFile();
    }



    @FXML
    public void modifyOnClick(ActionEvent event) throws IOException {
        //code to handle what node is about to be modified (maybe...idk)


        //code below is to create the pop up window
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ModifyScene.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }



}