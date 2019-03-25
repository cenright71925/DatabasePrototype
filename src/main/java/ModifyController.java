import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyController implements Initializable {

    @FXML Button btnEdit;
    @FXML Button btnAdd;
    @FXML Button btnDelete;
    @FXML Button btnLoadNode;
    @FXML TextField nodeIDTF, xCoordTF, yCoordTF, floorTF, buildingTF, nodeTypeTF, longNameTF, shortNameTF, enterNodeIDTF;
    @FXML Label errorLabel;

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

        if(enterNodeIDTF.getText().length() > 5){
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
