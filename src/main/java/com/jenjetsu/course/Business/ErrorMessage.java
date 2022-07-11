package com.jenjetsu.course.Business;

import com.jenjetsu.course.Collection.LinkedList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ErrorMessage {

    public ErrorMessage(String title, String header, LinkedList<String> info){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        StringBuilder builder = new StringBuilder();
        for(String line : info){
            builder.append(line+"\n");
        }
        VBox dialogPane = new VBox();
        TextArea area = new TextArea();
        area.setText(builder.toString());
        dialogPane.getChildren().add(area);
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }
}
