package com.jenjetsu.course.Controllers;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.SiteView;
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

public class HashTableController implements Initializable {

    @FXML // fx:id="index"
    private TableColumn<SiteView, Integer> index; // Value injected by FXMLLoader

    @FXML // fx:id="key"
    private TableColumn<SiteView, String> key; // Value injected by FXMLLoader

    @FXML // fx:id="removed"
    private TableColumn<SiteView, Boolean> removed; // Value injected by FXMLLoader

    @FXML // fx:id="value"
    private TableColumn<SiteView, String> value; // Value injected by FXMLLoader
    @FXML // fx:id="tab"
    private TableView<SiteView> tab; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        index.setCellValueFactory(cellsData -> cellsData.getValue().indexProperty().asObject());
        key.setCellValueFactory(cellsData -> cellsData.getValue().keyProperty());
        value.setCellValueFactory(cellsData -> cellsData.getValue().valueProperty());
        removed.setCellValueFactory(cellsData -> cellsData.getValue().statusProperty());
        Map.Entry<String, String>[] table = SiteDatabase.getInstance().getTable();
        boolean[] removed = SiteDatabase.getInstance().getRemove();
        int counter = 0;
        ArrayList<SiteView> list = new ArrayList<>();
        for(Map.Entry<String, String> pair : table){
            SiteView view = new SiteView();
            view.status = new SimpleBooleanProperty(removed[counter]);
            view.index = new SimpleIntegerProperty(counter++);
            if(pair != null){
                view.key = new SimpleStringProperty(pair.getKey());
                view.value = new SimpleStringProperty(pair.getValue());
            } else {
               view.key = new SimpleStringProperty("NULL");
               view.value = new SimpleStringProperty("NULL");
            }
            list.add(view);
        }
        tab.getItems().addAll(list);
    }

    public void refreshTable(){
        if(tab != null){
            tab.refresh();
        }
    }
}

