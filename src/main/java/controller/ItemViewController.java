package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DbConnection;
import dto.ItemDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import dto.tm.ItemTm;
import model.ItemModel;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.function.Predicate;

public class ItemViewController {

    public JFXTreeTableView<ItemTm> itemTable;
    public JFXButton backBtn;
    @FXML
    private JFXTextField codeTxtField;

    @FXML
    private JFXTextField descriptionTxtField;

    @FXML
    private JFXTextField unitPriceTxtField;

    @FXML
    private JFXTextField qtyTxtField;

    @FXML
    private TreeTableColumn<?, ?> codeCol;

    @FXML
    private TreeTableColumn<?, ?> descriptionCol;

    @FXML
    private TreeTableColumn<?, ?> unitPriceCol;

    @FXML
    private TreeTableColumn<?, ?> qtyCol;

    @FXML
    private TreeTableColumn<?, ?> optionCol;

    @FXML
    private JFXTextField searchTxtField;



private ItemModel itemModel=new ItemModelImpl();
    @FXML
    void refreshTableOnAction(ActionEvent event) {
        clear();
        try {
            loadItemTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) throws SQLException {
        ItemDto itemDto=new ItemDto(codeTxtField.getText(),descriptionTxtField.getText(),Double.parseDouble(unitPriceTxtField.getText()),Integer.parseInt(qtyTxtField.getText()));
        boolean isSaved = itemModel.saveItem(itemDto);

        if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Update Success").show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Duplicate entry").show();
        }
    }

    @FXML
    void searchBtnOnAction(ActionEvent event){
        String code = searchTxtField.getText();
        try {
            searchItemCode(code);
        }catch (SQLException e){
            new Alert(Alert.AlertType.INFORMATION,"Item not Found");
        }
    }

    private void searchItemCode(String code) throws SQLException {
//        String searchQuery="SELECT * FROM item where code='"+code+"'";
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(searchQuery);
//        resultSet.next();
//        ItemTm itemTm=new ItemTm(
//                resultSet.getString(1),
//                resultSet.getString(2),
//                resultSet.getDouble(3),
//                resultSet.getInt(4)
//        );
//        fillTextFields(itemTm);
    }

    private void fillTextFields(ItemTm itemTm) {
        codeTxtField.setText(itemTm.getCode());
        descriptionTxtField.setText(itemTm.getDescription());
        unitPriceTxtField.setText(itemTm.getUnitPrice()+"");
        qtyTxtField.setText(itemTm.getQty() + "");
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) throws SQLException {
        ItemDto itemDto=new ItemDto(codeTxtField.getText(),descriptionTxtField.getText(),Double.parseDouble(unitPriceTxtField.getText()),Integer.parseInt(qtyTxtField.getText()));
        boolean isUpdated = itemModel.updateItem(itemDto);
        if (isUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainView.fxml"))));
        stage.setTitle("Item View");
    }

    public void initialize() {
        searchTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                itemTable.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> itemTmTreeItem) {
                        return itemTmTreeItem.getValue().getCode().contains(t1)||itemTmTreeItem.getValue().getDescription().contains(t1);
                    }
                });
            }
        });

        itemTable.getSelectionModel().selectedItemProperty().addListener((observableValue, itemTm, newItemTmValue) -> fillTextFields(newItemTmValue));
        try {
            loadItemTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillTextFields(TreeItem<ItemTm> itemTm) {
        try {
            if (itemTable != null) {
                codeTxtField.setText(itemTm.getValue().getCode());
                descriptionTxtField.setText(itemTm.getValue().getDescription());
                unitPriceTxtField.setText(itemTm.getValue().getUnitPrice()+"");
                qtyTxtField.setText(itemTm.getValue().getQty() + "");
            }
        }catch (NullPointerException ignored){}
    }

    private void loadItemTable() throws SQLException {
        ObservableList<ItemTm> itemTmList = FXCollections.observableArrayList();

        List<ItemDto> itemDtoList = itemModel.getAllItems();

        for (ItemDto dto:itemDtoList) {
            JFXButton button=new JFXButton("Delete");

            ItemTm itemTm=new ItemTm(
                    dto.getCode(),
                    dto.getDescription(),
                    dto.getUnitPrice(),
                    dto.getQty(),
                    button
            );

            button.setOnAction(actionEvent -> {
                try {
                    deleteBtnOnAction(itemTm.getCode());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            itemTmList.add(itemTm);
        }

        TreeItem<ItemTm> treeItem= new RecursiveTreeItem<>(itemTmList, RecursiveTreeObject::getChildren);
        itemTable.setRoot(treeItem);
        itemTable.setShowRoot(false);

        codeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("code")); // itemTm eke properties wala thiyen nama denna oona
        descriptionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        unitPriceCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        qtyCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        optionCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("button"));
    }

    private void deleteBtnOnAction(String code) throws SQLException {

        boolean isDeleted = itemModel.deleteItem(code);
        loadItemTable();
        if (isDeleted){
            new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Something went wrong").show();
        }
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        codeTxtField.clear();
        descriptionTxtField.clear();
        unitPriceTxtField.clear();
        qtyTxtField.clear();
        searchTxtField.clear();
    }
}
