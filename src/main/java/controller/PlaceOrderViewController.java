package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DbConnection;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailDto;
import dto.OrderDto;
import dto.tm.ItemTm;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import model.ItemModel;
import model.OrderModel;
import model.impl.CustomerModelImpl;
import model.impl.ItemModelImpl;
import model.impl.OderModelImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class PlaceOrderViewController {
    public JFXComboBox custIdCombo;
    public JFXComboBox itemCodeCombo;
    public JFXTextField nameField;
    public JFXTextField descriptionField;
    public JFXTextField qtyField;
    public JFXTextField priceField;
    public JFXButton cartBtn;
    public TreeTableColumn optionCol;
    public TreeTableColumn amountCol;
    public TreeTableColumn qtyCol;
    public TreeTableColumn descriptionCol;
    public TreeTableColumn codeCol;
    public JFXTreeTableView<OrderTm> orderTable;
    public Label totalLbl;
    public Label orderLbl;


    private List<ItemDto> itemDtoList;
    private List<CustomerDto> customerDtoList;
    private ItemModel itemModel=new ItemModelImpl();
    private CustomerModel customerModel=new CustomerModelImpl();
    private OrderModel orderModel=new OderModelImpl();
    private ObservableList<OrderTm> orderTmList=FXCollections.observableArrayList();
    private double total=0;

    public void custIdComboOnAction(ActionEvent actionEvent) {
    }

    public void itemCodeComboOnAction(ActionEvent actionEvent) {
    }
    public void initialize(){
        codeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        descriptionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        qtyCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        amountCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        optionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("button"));

        generateId();
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

    public void cartBtnOnActon(ActionEvent actionEvent) {
        JFXButton button=new JFXButton("Delete");

        OrderTm tm=new OrderTm(
                itemCodeCombo.getValue().toString(),
                descriptionField.getText(),
                Integer.parseInt(qtyField.getText()),
                Double.parseDouble(priceField.getText())*Integer.parseInt(qtyField.getText()),
                button
        );

        button.setOnAction(actionEvent1 -> {
            orderTmList.remove(tm);
            total-= tm.getAmount();
            orderTable.refresh();
            totalLbl.setText(String.format("%.2f",total));//add to cart button ek obaddi run wenne cartOnAction method ek withri ee nisa label ek wens wenn na eeka wenas wenna nn eekath wens wenna kiyal me method ek athulem liyann oona
        });

        boolean isExist=false;

        for (OrderTm orderTm:orderTmList) {
            if (orderTm.getCode().equals(tm.getCode())){
                orderTm.setQty(orderTm.getQty()+tm.getQty());
                orderTm.setAmount(orderTm.getAmount()+ tm.getAmount());
                isExist = true;
                total+=tm.getAmount();
            }
        }

        if (!isExist){
            orderTmList.add(tm);
            total+=tm.getAmount();
        }

        totalLbl.setText(String.format("%.2f",total));

        TreeItem<OrderTm> treeItem= new RecursiveTreeItem<>(orderTmList, RecursiveTreeObject::getChildren);
        orderTable.setRoot(treeItem);
        orderTable.setShowRoot(false);

//        codeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
//        descriptionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
//        qtyCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
//        amountCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
//        optionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("button"));
    }

    public void generateId(){
        try {
            OrderDto orderDto = orderModel.lastOrder();
            if (orderDto!=null){
                String id=orderDto.getOrderId();
                int num=Integer.parseInt(id.split("[D]")[1]);
                orderLbl.setText(String.format("D%03d",++num));
            }else {
                orderLbl.setText("D001");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void placeOrderBtnOnAction(ActionEvent actionEvent) {
        List<OrderDetailDto> list=new ArrayList<>();
        for (OrderTm tm:orderTmList) {
            list.add(new OrderDetailDto(
                    orderLbl.getText(),
                    tm.getCode(),
                    tm.getQty(),
                    tm.getAmount()/tm.getQty()
            ));
            System.out.println("tm-"+tm);
        }
        System.out.println(list);
//        if (!orderTmList.isEmpty()){
            boolean isSaved=false;
            try {
                OrderDto orderDto=new OrderDto(
                        orderLbl.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                        custIdCombo.getValue().toString(),
                        list
                );
                System.out.println(orderDto);
                isSaved = orderModel.saveOrder(orderDto);

                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
                }else {
                    new Alert(Alert.AlertType.WARNING,"Something went Wrong!").show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }



//        }

    }
}
