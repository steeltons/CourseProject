package com.jenjetsu.course.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;

import com.jenjetsu.course.Database.LogsDatabase;
import com.jenjetsu.course.Enums.DebugCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LoggerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextFlow loggerScreen;

    @FXML
    void updateLog(ActionEvent event) {
        loggerScreen.getChildren().clear();
        ArrayList<Map.Entry<DebugCategory, String>> res = LogsDatabase.getInstance().getAllLogs1();
        ArrayList<Map.Entry<DebugCategory, String>> temp = new ArrayList<>();
        for(int i = 0; i < 3000; i++){
            temp.add(res.get(res.size() - i - 1));
        }

        int counter = 0;
        for(Map.Entry<DebugCategory, String> pair : temp){
            Text text = new Text((++counter)+" "+pair.getValue().toString()+"\n");
            String color = getColorFromDebugCategory(pair.getKey());
            text.setFont(Font.font("Comic Sans", 18));
            text.setStyle("-fx-fill: "+color+";");
            loggerScreen.getChildren().add(text);
        }
//        ArrayList<String> res = LogsDatabase.getInstance().getAllLogs();
//        ArrayList<String> temp = new ArrayList<>();
//        for(int i = 0; i < 3000; i++){
//            temp.add(res.get(res.size() - i - 1));
//        }
//        int counter = 0;
//        for(String s : temp){
//            Text text = new Text(counter+". "+s+"\n");
//            text.setFont(Font.font("Comic Sans",18));
//            counter++;
//            loggerScreen.getChildren().add(text);
//        }
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

}

