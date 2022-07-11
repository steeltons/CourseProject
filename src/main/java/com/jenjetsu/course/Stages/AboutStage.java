package com.jenjetsu.course.Stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class AboutStage extends Stage {

    public AboutStage(){
        super();
        try {
            File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\About.fxml");
            FXMLLoader loader = new FXMLLoader(file.toURL());
            Scene scene = new Scene(loader.load(), 400, 250);
            initModality(Modality.NONE);
            setTitle("About");
            setScene(scene);
            setResizable(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
