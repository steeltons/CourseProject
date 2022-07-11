package com.jenjetsu.course.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.LogsDatabase;
import com.jenjetsu.course.Enums.DebugCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LoggerController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextFlow loggerScreen;

    @FXML
    void updateLog(ActionEvent event) {
        update();
    }

    private void update(){
        loggerScreen.getChildren().clear();
        LinkedList<Map.Entry<DebugCategory, String>> res = LogsDatabase.getInstance().getAllLogs();
        Iterator<Map.Entry<DebugCategory, String>> iterator = res.iterator();
        int counter = 0;
        while (iterator.hasNext()){
            Map.Entry<DebugCategory, String> pair = iterator.next();
            Text text = new Text((++counter)+" "+pair.getValue().toString()+"\n");
            String color = getColorFromDebugCategory(pair.getKey());
            text.setFont(Font.font("Comic Sans MS", 16));
            text.setStyle("-fx-fill: "+color+";");
            loggerScreen.getChildren().add(text);
            if(counter >= 999)
                break;
        }
    }

    private String getColorFromDebugCategory(DebugCategory category){
        return switch (category){
            case INSERT , REMOVE -> new String("green");
            case SECOND_INSERT, SECOND_REMOVE -> new String("#f90");
            case COLLISION -> new String("red");
            case FIRST_HASH -> new String("#307fb8");
            case SECOND_HASH -> new String("#a530b8");
            default -> new String("");
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }
}

