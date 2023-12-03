package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import dto.tm.ItemTm;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

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

    private Statement statement= DbConnection.getStatement();


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
    void saveBtnOnAction(ActionEvent event) {
        ItemTm itemTm=new ItemTm(codeTxtField.getText(),descriptionTxtField.getText(),Double.parseDouble(unitPriceTxtField.getText()),Integer.parseInt(qtyTxtField.getText()));
        String sql="INSERT INTO item VALUES('"+itemTm.getCode()+"','"+itemTm.getDescription()+"','"+itemTm.getUnitPrice()+"','"+itemTm.getQty()+"')";
        try {
            int number = statement.executeUpdate(sql);
            //reloadItemTable();
            if (number>0){
                new Alert(Alert.AlertType.INFORMATION,"Update Success").show();;
            }
        }catch (SQLIntegrityConstraintViolationException e){
            new Alert(Alert.AlertType.INFORMATION,"Duplicate entry").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        String searchQuery="SELECT * FROM item where code='"+code+"'";
        ResultSet resultSet = statement.executeQuery(searchQuery);
        resultSet.next();
        ItemTm itemTm=new ItemTm(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getDouble(3),
                resultSet.getInt(4)
        );
        fillTextFields(itemTm);
    }

    private void fillTextFields(ItemTm itemTm) {
        codeTxtField.setText(itemTm.getCode());
        descriptionTxtField.setText(itemTm.getDescription());
        unitPriceTxtField.setText(itemTm.getUnitPrice()+"");
        qtyTxtField.setText(itemTm.getQty() + "");
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) throws SQLException {
        ItemTm itemTm=new ItemTm(codeTxtField.getText(),descriptionTxtField.getText(),Double.parseDouble(unitPriceTxtField.getText()),Integer.parseInt(qtyTxtField.getText()));
        String updateQuery="UPDATE customer SET code='"+itemTm.getCode()+"',desctiption='"+itemTm.getDescription()+"',unitPrice="+itemTm.getUnitPrice()+", qtyOnHand="+itemTm.getQty()+" WHERE id='"+itemTm.getCode()+"'";
        int number = statement.executeUpdate(updateQuery);
//        reloadCustomerTable();
        if (number>0){
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainView.fxml"))));
        stage.setTitle("Item View");
    }

    public void initialize() {
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
        String selectQuery="SELECT * FROM item";
        ResultSet resultSet = statement.executeQuery(selectQuery);
        while (resultSet.next()){
            JFXButton button=new JFXButton("Delete");
            ItemTm itemTm=new ItemTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
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
        String deleteQuery="DELETE FROM item WHERE code='"+code+"'";
        int number = statement.executeUpdate(deleteQuery);
        loadItemTable();
        //customerTable.refresh();   just like reloadCustomerTable()
        if (number>0){
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
    }//
}
