package com.jenjetsu.course;

import com.jenjetsu.course.Collection.CircleList;
import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.LogsDatabase;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.ScreenTextDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;
import com.jenjetsu.course.utils.Constatnces;
import com.jenjetsu.course.utils.ObjectCreator;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {

    ScreenTextDatabase database;

    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        initialize(scene);
    }

    private void initialize(Scene scene){
        TextFlow siteScreen = (TextFlow) scene.lookup("#siteScreen");
        TextFlow productScreen = (TextFlow) scene.lookup("#productScreen");
        ScreenTextDatabase screenData = ScreenTextDatabase.init(siteScreen, productScreen);
        LogsDatabase.getInstance();
        SiteDatabase siteData = SiteDatabase.getInstance();
        ProductDatabase productData = ProductDatabase.getInstance();
        LinkedList<Site> siteCircleList = SiteDatabase.getInstance().getList();
        LinkedList<Product> productCircleList = ProductDatabase.getInstance().getList();
        for(Site s : siteCircleList){
            screenData.displaySite(s.toString());
        }
        for(Product pr : productCircleList){
            screenData.displayProduct(pr.toString());
        }
    }

    private Text createText(String line){
        Text text = new Text(line);
        text.setFont(Font.font("Comic sans", 16));
        return text;
    }

    @Override
    public void stop() throws Exception {
//        SiteDatabase.getInstance().close();
//        ProductDatabase.getInstance().close();
//        System.out.println("Закрываемся");
//        super.stop();
    }


    public static void main(String[] args) {
        launch();
    }
}