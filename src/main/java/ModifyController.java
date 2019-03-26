import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class ModifyController implements Initializable {

    @FXML Button btnEdit;
    @FXML Button btnAdd;
    @FXML Button btnDelete;
    @FXML Button btnLoadNode;
    @FXML TextField nodeIDTF, xCoordTF, yCoordTF, floorTF, buildingTF, nodeTypeTF, longNameTF, shortNameTF, enterNodeIDTF;
    @FXML Label errorLabel;
    boolean hasNode = false;
    String nodeID, building, nodeType, longName, shortName;
    int xCoord, yCoord, floor;
    Node curNode, delNode;
    int element;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDisableTF(true);

        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnAdd.setDisable(true);
    }

    @FXML
    public void loadOnClick(ActionEvent event){

        hasNode = false;
        LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
        for(Node n: fileReaderNodeList){
            if(n.getNodeID().equals(enterNodeIDTF.getText())){
                hasNode = true;
                curNode = n;
            }
        }

        if(hasNode){
            setDisableTF(false);
            nodeIDTF.setDisable(true);

            btnAdd.setDisable(false);
            btnDelete.setDisable(false);
            btnEdit.setDisable(false);
            nodeIDTF.setText(enterNodeIDTF.getText());
            errorLabel.setText("Node Found");
            nodeID = curNode.getNodeID();
            xCoord = curNode.getXCoord();
            yCoord = curNode.getYCoord();
            floor = curNode.getFloor();
            building = curNode.getBuilding();
            nodeType = curNode.getNodeType();
            longName = curNode.getLongName();
            shortName = curNode.getShortName();

            xCoordTF.setText(Integer.toString(xCoord));
            yCoordTF.setText(Integer.toString(yCoord));
            floorTF.setText(Integer.toString(floor));
            buildingTF.setText(building);
            nodeTypeTF.setText(nodeType);
            longNameTF.setText(longName);
            shortNameTF.setText(shortName);

        } else {
            errorLabel.setText("Node Not Found. Create new node?");
            setDisableTF(false);
            nodeIDTF.setDisable(true);

            btnAdd.setDisable(false);
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
            nodeIDTF.setText(enterNodeIDTF.getText());
            xCoordTF.setText("");
            yCoordTF.setText("");
            floorTF.setText("");
            buildingTF.setText("");
            nodeTypeTF.setText("");
            longNameTF.setText("");
            shortNameTF.setText("");
        }
    }

    @FXML
    public void addOnClick(ActionEvent event) throws  IOException{
        getTextFieldEntries();

        Node newNode = new Node(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
        LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
        fileReaderNodeList.addFirst(newNode);
        DBController.setNodeList(fileReaderNodeList);

        try{
            DBController.addNode(newNode);
        }
        catch (SQLException e){
            e.printStackTrace();
        }


        //bellow closes the pop up window, everything above this in this method should handle the inputs from the TFs

        Stage stage;
        stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void deleteOnClick(ActionEvent event) throws  IOException{

        element = 0;
        LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
        for(Node n: fileReaderNodeList){
            if(n.getNodeID().equals(nodeID)) {
                delNode = n;
                fileReaderNodeList.remove(element);
                break;
            }
            element++;
        }

        DBController.setNodeList(fileReaderNodeList);

        try{
            delNode.deleteNode();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        //bellow closes the pop up window, everything above this in this method should handle the inputs from the TFs
        Stage stage;
        stage = (Stage) btnDelete.getScene().getWindow();
        stage.close();

        String id = enterNodeIDTF.getText();
        //String delNode = "DELETE * FROM Node WHERE nodeID = " + id + "";
    }

    @FXML
    public void editOnClick(ActionEvent event) throws IOException{
        getTextFieldEntries();

        Node newNode = new Node(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);

        element = 0;
        LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
        for(Node n: fileReaderNodeList){
            if(n.getNodeID().equals(nodeID)) {
                delNode = n;
                fileReaderNodeList.remove(element);
                break;
            }
            element++;
        }
        fileReaderNodeList.add(element, newNode);
        DBController.setNodeList(fileReaderNodeList);

        try{
            delNode.editNode(xCoord, yCoord, floor, building, nodeType, longName, shortName);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        //bellow closes the pop up window, everything above this in this method should handle the inputs from the TFs
        Stage stage;
        stage = (Stage) btnEdit.getScene().getWindow();
        stage.close();
    }

    public void getTextFieldEntries(){
        nodeID = nodeIDTF.getText();
        xCoord = Integer.parseInt(xCoordTF.getText());
        yCoord = Integer.parseInt(yCoordTF.getText());
        floor = Integer.parseInt((floorTF.getText()));
        building = buildingTF.getText();
        nodeType = nodeTypeTF.getText();
        longName = longNameTF.getText();
        shortName = shortNameTF.getText();
    }

    public void setDisableTF(boolean value){
        nodeIDTF.setDisable(value);
        xCoordTF.setDisable(value);
        yCoordTF.setDisable(value);
        floorTF.setDisable(value);
        buildingTF.setDisable(value);
        nodeTypeTF.setDisable(value);
        longNameTF.setDisable(value);
        shortNameTF.setDisable(value);
    }
}
