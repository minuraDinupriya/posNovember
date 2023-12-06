package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.CustomerDto;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import model.CustomerModel;
import model.ItemModel;
import model.impl.CustomerModelImpl;
import model.impl.ItemModelImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlaceOrderViewController {
    public JFXComboBox custIdCombo;
    public JFXComboBox itemCodeCombo;
    public JFXTextField nameField;
    public JFXTextField descriptionField;
    public JFXTextField qtyField;
    public JFXTextField priceField;

    List<ItemDto> itemDtoList;
    List<CustomerDto> customerDtoList;
    ItemModel itemModel=new ItemModelImpl();
    CustomerModel customerModel=new CustomerModelImpl();

    public void custIdComboOnAction(ActionEvent actionEvent) {
    }

    public void itemCodeComboOnAction(ActionEvent actionEvent) {
    }
    public void initialize(){
        loadCustomerIdCombo();
        loadItemCodeCombo();
        itemCodeCombo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldCode, newCode) -> {
            for (ItemDto dto:itemDtoList) {
                if (dto.getCode().equals(newCode)){
                    descriptionField.setText(dto.getDescription());
                    priceField.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        });

        custIdCombo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldID, newID) -> {
            for (CustomerDto dto:customerDtoList) {
                if (dto.getId().equals(newID)){
                    nameField.setText(dto.getName());
                }
            }
        });
    }

    private void loadItemCodeCombo() {
        try {
            itemDtoList=itemModel.getAllItems();
            ObservableList itemCodeList=FXCollections.observableArrayList();

            for (ItemDto dto:itemDtoList) {
                itemCodeList.add(dto.getCode());
            }

            itemCodeCombo.setItems(itemCodeList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIdCombo() {
        try {
            customerDtoList=customerModel.getAllCustomers();
            ObservableList customerIDList=FXCollections.observableArrayList();

            for (CustomerDto dto:customerDtoList) {
                customerIDList.add(dto.getId());
            }

            custIdCombo.setItems(customerIDList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
