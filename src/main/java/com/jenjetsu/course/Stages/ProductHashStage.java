package com.jenjetsu.course.Stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProductHashStage extends Stage {

    public ProductHashStage(){
        try {
            File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\ProductTableView.fxml");
            FXMLLoader loader = new FXMLLoader(file.toURL());
            setTitle("Site table");
            Scene scene = new Scene(loader.load(), 700, 600);
            setScene(scene);
        } catch (IOException e){

        }
    }
}
