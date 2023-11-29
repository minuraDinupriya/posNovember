package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
=======
import javafx.scene.control.Alert;
>>>>>>> 640e532 (sql connected)
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
<<<<<<< HEAD
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
>>>>>>> 640e532 (sql connected)
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
<<<<<<< HEAD
    void saveBtnOnAction(ActionEvent event) {

=======
    void saveBtnOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        String sql="INSERT INTO customer VALUES('"+customerIdField.getText()+"','"+customerNameField.getText()+"','"+addressTxtField.getText()+"','"+Double.parseDouble(salaryTxtField.getText())+"')";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "minura");
        Statement statement = connection.createStatement();
        int number = statement.executeUpdate(sql);
        if (number>0){
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
        }
>>>>>>> 640e532 (sql connected)
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
