package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {

    public JFXButton sceneChangeBtn;

    public void sceneChangeBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) sceneChangeBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerViewForm.fxml"))));
    }
}
