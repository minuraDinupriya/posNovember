package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import model.tm.ItemTm;

import java.io.IOException;

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

    @FXML
    void refreshTableOnAction(ActionEvent event) {

    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {

    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {

    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/MainView.fxml"))));
    }
}
