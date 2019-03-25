import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyController implements Initializable {

    @FXML Button btnEdit;
    @FXML Button btnAdd;
    @FXML Button btnDelete;
    @FXML TextField nodeIDTF, xCoordTF, yCoordTF, floorTF, buildingTF, nodeTypeTF, longNameTF, shortNameTF, enterNodeIDTF;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set TF with the node's current values (bellow is just examples)
        nodeIDTF.setText("Hello");
        xCoordTF.setText("World");
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
