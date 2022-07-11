package com.jenjetsu.course.Controllers;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.ProductKey;
import com.jenjetsu.course.Entity.ProductView;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductTableController implements Initializable {

    @FXML // fx:id="index"
    private TableColumn<ProductView, Integer> index; // Value injected by FXMLLoader

    @FXML // fx:id="key"
    private TableColumn<ProductView, String> key; // Value injected by FXMLLoader

    @FXML // fx:id="removed"
    private TableColumn<ProductView, Boolean> removed; // Value injected by FXMLLoader

    @FXML // fx:id="value"
    private TableColumn<ProductView, String> value; // Value injected by FXMLLoader
    @FXML // fx:id="tab"
    private TableView<ProductView> tab; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        index.setCellValueFactory(cellsData -> cellsData.getValue().indexProperty().asObject());
        key.setCellValueFactory(cellsData -> cellsData.getValue().keyProperty());
        value.setCellValueFactory(cellsData -> cellsData.getValue().valueProperty());
        removed.setCellValueFactory(cellsData -> cellsData.getValue().statusProperty());

        Map.Entry<ProductKey, String>[] table =  ProductDatabase.getInstance().getTable();
        System.out.println(table.length);
        boolean[] removed = ProductDatabase.getInstance().getRemove();
        int counter = 0;
        ArrayList<ProductView> list = new ArrayList<>();
        for(Map.Entry<ProductKey, String> pair : table){
            ProductView view = new ProductView();
            view.status = new SimpleBooleanProperty(removed[counter]);
            view.index = new SimpleIntegerProperty(counter++);
            if(pair != null){
                view.key = new SimpleStringProperty(pair.getKey().toString());
                view.value = new SimpleStringProperty(pair.getValue());
            } else {
                view.key = new SimpleStringProperty("NULL");
                view.value = new SimpleStringProperty("NULL");
            }
            list.add(view);
        }
        tab.getItems().addAll(list);
    }

}


