package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    public JFXButton sceneChangeBtn;
    public Label timeLbl;
    public JFXButton itemViewBtn;
    public JFXButton placeOrderBtn;

    public void sceneChangeBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) sceneChangeBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"))));
        stage.setTitle("Customer View");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTime();
    }

    private void setTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                actionEvent -> timeLbl.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void itemViewOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemViewBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemView.fxml"))));
        stage.setTitle("Item View");
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) placeOrderBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderView.fxml"))));
        stage.setTitle("Place Order View");
    }
}
