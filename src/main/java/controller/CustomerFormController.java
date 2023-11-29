package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableView customerTable;

    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn salaryCol;

    @FXML
    private TableColumn optionCol;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXTextField customerIdField;

    @FXML
    private JFXTextField addressTxtField;

    @FXML
    private JFXTextField salaryTxtField;

    @FXML
    private JFXButton reloadBtn;

    @FXML
    private Label customerSalaryField;

    @FXML
    private JFXTextField customerNameField;

    @FXML
    void customerTableOnAction(ActionEvent event) {

    }

    @FXML
    void reloadBtnOnAction(ActionEvent event) {

    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {

    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
