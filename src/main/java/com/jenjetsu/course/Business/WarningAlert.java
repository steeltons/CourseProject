package com.jenjetsu.course.Business;

import javafx.scene.control.Alert;

public class WarningAlert {

    public WarningAlert(String title, String header, String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(info);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
