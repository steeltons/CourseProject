package com.jenjetsu.course.Stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SearchStage extends Stage {

    public SearchStage(){
        super();
        try {
            File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\SearchView.fxml");
            FXMLLoader loader = new FXMLLoader(file.toURL());
            setTitle("Search");
            initModality(Modality.NONE);
            Scene scene = new Scene(loader.load(), 450, 650);
            setScene(scene);
            setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
