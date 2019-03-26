import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    Node curNode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nodeIDTF.setDisable(true);
        xCoordTF.setDisable(true);
        yCoordTF.setDisable(true);
        floorTF.setDisable(true);
        buildingTF.setDisable(true);
        nodeTypeTF.setDisable(true);
        longNameTF.setDisable(true);
        shortNameTF.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnAdd.setDisable(true);



    }

    @FXML
    public void loadOnClick(ActionEvent event){

        LinkedList<Node> fileReaderNodeList = FileReader.getNodeList();
        for(Node n: fileReaderNodeList){
            if(n.getNodeID().equals(enterNodeIDTF.getText())){
                hasNode = true;
                curNode = n;
            }
        }

        if(hasNode){
            xCoordTF.setDisable(false);
            yCoordTF.setDisable(false);
            floorTF.setDisable(false);
            buildingTF.setDisable(false);
            nodeTypeTF.setDisable(false);
            longNameTF.setDisable(false);
            shortNameTF.setDisable(false);

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
            errorLabel.setText("Node Not Found");
        }
    }

    @FXML
    public void addOnClick(ActionEvent event) throws  IOException{

        //bellow closes the pop up window, everything above this in this method should handle the inputs from the TFs
        Stage stage;
        stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void deleteOnClick(ActionEvent event) throws  IOException{

        //bellow closes the pop up window, everything above this in this method should handle the inputs from the TFs
        Stage stage;
        stage = (Stage) btnDelete.getScene().getWindow();
        stage.close();

        String id = enterNodeIDTF.getText();





        //String delNode = "DELETE * FROM Node WHERE nodeID = " + id + "";




    }

    @FXML
    public void editOnClick(ActionEvent event) throws IOException{
        //.getText will read the TextField into a String that can then be put somewhere (like the database...)
        System.out.println(nodeIDTF.getText());

        //bellow closes the pop up window, everything above this in this method should handle the inputs from the TFs
        Stage stage;
        stage = (Stage) btnEdit.getScene().getWindow();
        stage.close();
    }
}
