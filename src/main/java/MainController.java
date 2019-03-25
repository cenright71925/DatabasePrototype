import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Button btnDownload, btnRead, btnModify;

    @Override //this code runs as soon as the program boots up. start() in the Main class also does that. but here is for UI things
    public void initialize(URL location, ResourceBundle resources) {
        //btnModify.setDisable(true); //code to disable the Modify Node button until a node is selected from the table
    }

    @FXML
    public void downloadOnClick(ActionEvent event){
        //here put code that needs to run to download the new CSV file
    }

    @FXML
    public void readOnClick(ActionEvent event){
        //here put code to start the reading of the CSV file and populating the table full of data

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
