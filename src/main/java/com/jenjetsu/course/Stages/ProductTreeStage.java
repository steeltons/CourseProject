package com.jenjetsu.course.Stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProductTreeStage extends Stage {

    public ProductTreeStage(){
        try{
            File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\ProductTreeView.fxml");
            FXMLLoader loader = new FXMLLoader(file.toURL());
            setTitle("Product tree container");
            Scene scene = new Scene(loader.load(), 500, 500);
            setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
