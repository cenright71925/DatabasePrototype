import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    int xCoord, yCoord, floor, element;
    Node curNode, delNode;
    Boolean goodTF, goodNumbers;

    /**
     * This is the initialize function which handles disabling certain buttons so the user cannot select them until
     * entering a nodeID. Has parameters that are not called. I think it just has something to do with the @Override
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDisableTF(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnAdd.setDisable(true);

        onlyDigits(xCoordTF);
        onlyDigits(yCoordTF);
        onlyDigits(floorTF);

    }

    /**
     * This method is called when the Load Node button is clicked. It gets the LinkedList<Node> from the DBController
     * and looks for the inputted nodeID. If the node is in the list, it will populate the text fields with the Node's
     * information. It will also enable the Edit Node button and Delete Node button. If the node is not found in the LL
     * it will allow the user to input the properities of the node in the text fields. It will then enable the Add Node
     * button. Assuming a valid node ID can be one or more characters.
     */
    @FXML
    public void loadOnClick(){
        if (enterNodeIDTF.getText().length() < 1 || enterNodeIDTF.getText().length() > 10) {
            errorLabel.setText("Enter a valid nodeID length");
            btnAdd.setDisable(true); //needed if the user enters valid name then a non valid one
        } else {
            hasNode = false;
            LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
            for (Node n : fileReaderNodeList) {
                if (n.getNodeID().equals(enterNodeIDTF.getText())) {
                    hasNode = true;
                    curNode = n;
                }
            }

            if (hasNode) {
                setDisableTF(false);
                nodeIDTF.setDisable(true);
                btnAdd.setDisable(true);
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

            } else { //if the given Node ID doesn't match a current node in the LL
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
    }

    /**
     * This method is called every time the Add Node button is clicked. It creates a new node with the info entered
     * in the text fields. It then adds that node to the LL in DBController. It also calls the method to add the new
     * node to the database. Then closes the popup windown.
     */
    @FXML
    public void addOnClick() {
        if (validNode() && getTextFieldEntries()) {
            Node newNode = new Node(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
            LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
            fileReaderNodeList.addFirst(newNode);
            DBController.setNodeList(fileReaderNodeList);

            try {
                DBController.addNode(newNode);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Stage stage;
            stage = (Stage) btnAdd.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Entered properties are not valid");
        }
    }

    /**
     * This method is called when the Delete Node button is pressed. It looks through the LL and looks for the node
     * with the given nodeID, then deletes that element of the LL. Then updates the LL in DBController as well as
     * removing the node from the Database. Then closes the pop up.
     */
    @FXML
    public void deleteOnClick() throws NumberFormatException{
        if (validNode() && getTextFieldEntries()) {
            element = 0;
            LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
            for (Node n : fileReaderNodeList) {
                if (n.getNodeID().equals(nodeID)) {
                    delNode = n;
                    fileReaderNodeList.remove(element);
                    break;
                }
                element++;
            }

            DBController.setNodeList(fileReaderNodeList);

            try {
                DBController.deleteNode(delNode);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //bellow closes the pop up window, everything above this in this method should handle the inputs from the TFs
            Stage stage;
            stage = (Stage) btnDelete.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Entered properties are not valid");
        }

    }

    /**
     * This method is called when the Edit Node button is pressed. It deletes a node, then adds a new (edited) node in
     * same spoit in the LL. Then updates the Database. The process of deleting the node is the same as deleteOnClick
     * but here it then uses the element value to add a new node in the same spot.
     */
    @FXML
    public void editOnClick() throws NumberFormatException{
        if (validNode() && getTextFieldEntries()) {
            Node newNode = new Node(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);

            element = 0;
            LinkedList<Node> fileReaderNodeList = DBController.getNodeList();
            for (Node n : fileReaderNodeList) {
                if (n.getNodeID().equals(nodeID)) {
                    delNode = n;
                    fileReaderNodeList.remove(element);
                    break;
                }
                element++;
            }
            fileReaderNodeList.add(element, newNode);
            DBController.setNodeList(fileReaderNodeList);

            try {
                DBController.editNode(delNode, newNode);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Stage stage;
            stage = (Stage) btnEdit.getScene().getWindow();
            stage.close();
        } else {
            errorLabel.setText("Entered properties are not valid");
        }
    }

    /**
     * This helper method will read all the text fields and set the appropriate fields with the information. Will also
     * check for number format exceptions when parsing strings to integers.
     */
    public boolean getTextFieldEntries() {
        goodNumbers = true;
        try {
            nodeID = nodeIDTF.getText();
            xCoord = Integer.parseInt(xCoordTF.getText());
            yCoord = Integer.parseInt(yCoordTF.getText());
            floor = Integer.parseInt((floorTF.getText()));
            building = buildingTF.getText();
            nodeType = nodeTypeTF.getText();
            longName = longNameTF.getText();
            shortName = shortNameTF.getText();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            goodNumbers = false;
        }
        return goodNumbers;
    }

    /**
     * This is a helper method that will enable or disable all the text fields.
     * @param value a boolean value to enable or disable the buttons
     */
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

    /**
     * Helper method to check if the user entered data in the text fields meets some sort of level of valid input or
     * do not crash the program.
     * @return
     */
    public boolean validNode() {
        goodTF = true;
        if (xCoordTF.getText().length() == 0) {
            goodTF = false;
        }
        if (yCoordTF.getText().length() == 0) {
            goodTF = false;
        }
        if (floorTF.getText().length() ==0 ) {
            goodTF = false;
        }
        if (buildingTF.getText().length() == 0) {
            goodTF = false;
        }
        if (nodeTypeTF.getText().length() == 0) {
            goodTF = false;
        }
        if (longNameTF.getText().length() == 0) {
            goodTF = false;
        }
        if (shortNameTF.getText().length() == 0) {
            goodTF = false;
        }
        return goodTF;
    }

    /**
     * Helper method that is a listener on a text field to allow only digits to be entered. This is called in the
     * initialize method and it is called for xCoord yCoord and floor.
     * @param tf
     */
    public void onlyDigits(TextField tf){
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
