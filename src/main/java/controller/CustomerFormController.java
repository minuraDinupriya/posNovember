package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import model.tm.CustomerTm;

import java.io.IOException;
import java.net.URL;

import java.sql.*;

import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public JFXButton clearBtn;
    public JFXTextField idTxtField;
    public JFXTextField nameTxtField;
    public JFXButton backBtn;
    @FXML
    private TableView<CustomerTm> customerTable;

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
    private JFXTextField addressTxtField;

    @FXML
    private JFXTextField salaryTxtField;

    @FXML
    private JFXButton reloadBtn;

    @FXML
    private Label customerSalaryField;

    private Statement statement=DbConnection.getStatement();

    @FXML
    void customerTableOnAction(ActionEvent event) {

    }

    @FXML
    void reloadBtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        reloadCustomerTable();
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        String sql="INSERT INTO customer VALUES('"+idTxtField.getText()+"','"+nameTxtField.getText()+"','"+addressTxtField.getText()+"','"+Double.parseDouble(salaryTxtField.getText())+"')";
        try {
            int number = statement.executeUpdate(sql);
            reloadCustomerTable();
            if (number>0){
                new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
//                clear();
            }
        }catch (SQLIntegrityConstraintViolationException e){
            new Alert(Alert.AlertType.INFORMATION,"Duplicate entry").show();
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) throws ClassNotFoundException, SQLException  {
        String updateQuery="UPDATE customer SET name='"+nameTxtField.getText()+"',address='"+addressTxtField.getText()+"',salary="+Double.parseDouble(salaryTxtField.getText())+" WHERE id='"+idTxtField.getText()+"'";
        int number = statement.executeUpdate(updateQuery);
        reloadCustomerTable();
        if (number>0){
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.getSelectionModel().selectedItemProperty().addListener((observableValue, customerTm, newCustomerTmValue) -> fillTextFields(newCustomerTmValue));
        reloadCustomerTable();
    }

    private void fillTextFields(CustomerTm customerTm) {
        if (customerTm != null) {
            idTxtField.setText(customerTm.getId());
            nameTxtField.setText(customerTm.getName());
            addressTxtField.setText(customerTm.getAddress());
            salaryTxtField.setText(customerTm.getSalary() + "");
        }
    }

    public void deleteBtnOnAction(String id) throws SQLException, ClassNotFoundException{
        String deleteQuery="DELETE FROM customer WHERE id='"+id+"'";
        int number = statement.executeUpdate(deleteQuery);
        reloadCustomerTable();
        //customerTable.refresh();   just like reloadCustomerTable()
        if (number>0){
            new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Something went wrong").show();
        }
    }

    private void reloadCustomerTable() throws ClassNotFoundException, SQLException  {
        ObservableList<CustomerTm> customerTmList = FXCollections.observableArrayList();
        String selectQuery="SELECT * FROM customer";
        ResultSet resultSet = statement.executeQuery(selectQuery);
        while (resultSet.next()){
            Button button=new Button("Delete");
            CustomerTm customerTm=new CustomerTm(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    button
            );

            button.setOnAction(actionEvent -> {
                try {
                    deleteBtnOnAction(customerTm.getId());
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            customerTmList.add(customerTm);
        }

        customerTable.setItems(customerTmList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        optionCol.setCellValueFactory(new PropertyValueFactory<>("optionBtn"));
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        idTxtField.clear();
        nameTxtField.clear();
        addressTxtField.clear();
        salaryTxtField.clear();
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainView.fxml"))));
    }
}
