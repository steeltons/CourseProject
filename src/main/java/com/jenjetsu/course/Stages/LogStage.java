package com.jenjetsu.course.Stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LogStage extends Stage {

    public LogStage(){
        super();
        Scene scene = null;
        try {
            File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\LoggerView.fxml");
            FXMLLoader loader = new FXMLLoader(file.toURL());
            setTitle("Search");
            scene = new Scene(loader.load(), 600, 800);
            setScene(scene);
            setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
