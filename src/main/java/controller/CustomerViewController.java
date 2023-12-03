package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.CustomerDto;
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
import dto.tm.CustomerTm;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.net.URL;

import java.sql.*;

import java.util.List;
import java.util.ResourceBundle;

public class CustomerViewController implements Initializable {

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

    private CustomerModel customerModel=new CustomerModelImpl();

    @FXML
    void customerTableOnAction(ActionEvent event) {

    }

    @FXML
    void reloadBtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        reloadCustomerTable();
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        CustomerDto customerDto=new CustomerDto(
                idTxtField.getText(),
                nameTxtField.getText(),
                addressTxtField.getText(),
                Double.parseDouble(salaryTxtField.getText())
                );

        boolean isSaved = customerModel.saveCustomer(customerDto);

        try {
            reloadCustomerTable();
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
            }
        }catch (SQLIntegrityConstraintViolationException e){
            new Alert(Alert.AlertType.INFORMATION,"Duplicate entry").show();
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) throws ClassNotFoundException, SQLException  {
        CustomerDto customerDto=new CustomerDto(
                idTxtField.getText(),
                nameTxtField.getText(),
                addressTxtField.getText(),
                Double.parseDouble(salaryTxtField.getText())
        );

        boolean isUpdated = customerModel.updateCustomer(customerDto);
        reloadCustomerTable();
        if (isUpdated){
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

        boolean isDeleted = customerModel.deleteCustomer(id);
        reloadCustomerTable();
        if (isDeleted){
            new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Something went wrong").show();
        }
    }

    private void reloadCustomerTable() throws ClassNotFoundException, SQLException  {
        ObservableList<CustomerTm> customerTmList = FXCollections.observableArrayList();

        List<CustomerDto> dtoList = customerModel.getAllCustomers();
        for (CustomerDto dto:dtoList) {
            Button button=new Button("Delete");

            CustomerTm customerTm=new CustomerTm(
                    dto.getId(),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getSalary(),
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
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainView.fxml"))));
        stage.setTitle("Customer View");
    }
}
