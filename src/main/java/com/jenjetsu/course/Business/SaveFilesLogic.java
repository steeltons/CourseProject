package com.jenjetsu.course.Business;

import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFilesLogic {

    public void saveSites() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = chooser.showSaveDialog(new Stage());
        if(dir != null){
            try {
                FileWriter writer = writer = new FileWriter(dir);
                LinkedList<Site> sites = SiteDatabase.getInstance().getList();
                for(Site site : sites){
                    writer.write(site.toString()+"\n");
                    writer.flush();
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveProducts(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = chooser.showSaveDialog(new Stage());
        if(dir != null){
            try {
                FileWriter writer = writer = new FileWriter(dir);
                LinkedList<Product> products = ProductDatabase.getInstance().getList();
                for(Product product : products){
                    writer.write(product.toString()+"\n");
                    writer.flush();
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
