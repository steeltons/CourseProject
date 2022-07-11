package com.jenjetsu.course.Business;

import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.ScreenTextDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;
import com.jenjetsu.course.utils.ObjectCreator;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFilesLogic {

    public void readSites() throws FileNotFoundException {
        final FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        chooser.setTitle("Select site file");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = chooser.showOpenDialog(new Stage());
        if(dir != null){
            Scanner scanner = new Scanner(new File(dir.getAbsolutePath()));
            ObjectCreator creator = new ObjectCreator();
            LinkedList<String> notAddedProducts = new LinkedList<>();
            while (scanner.hasNext()){
                String productLine = scanner.nextLine();
                try {
                    Product product = creator.createProductFromLine(productLine);
                    if(!ProductDatabase.getInstance().contains(product.createKey()) && SiteDatabase.getInstance().contains(product.getUrl())){
                        ProductDatabase.getInstance().add(product);
                        ScreenTextDatabase.getInstance().displayProduct(product.toString());
                    } else {
                        notAddedProducts.add(productLine);
                    }
                } catch (Exception e) {
                    notAddedProducts.add(productLine);
                }
            }
            if(!notAddedProducts.isEmpty()){
                new ErrorMessage("Product adding error", "Продукты, которые не были добавлены", notAddedProducts);
            }
        }
    }

    public void readProducts() throws FileNotFoundException {
        final FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        chooser.setTitle("Select site file");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = chooser.showOpenDialog(new Stage());
        if(dir != null){
            Scanner scanner = new Scanner(new File(dir.getAbsolutePath()));
            LinkedList<String> notAdded = new LinkedList<>();
            ObjectCreator creator = new ObjectCreator();
            while (scanner.hasNext()){
                String siteLine = scanner.nextLine();
                try {
                    Site site = creator.createSiteFormLine(siteLine);
                    if(!SiteDatabase.getInstance().contains(site.getUrl())){
                        SiteDatabase.getInstance().add(site);
                        ScreenTextDatabase.getInstance().displaySite(site.toString());
                    } else {
                        notAdded.add(site.toString());
                    }
                } catch (Exception e){
                    notAdded.add(siteLine);
                }
            }
            if(!notAdded.isEmpty()){
                new ErrorMessage("Adding site error", "Сайты, которые не были добавлены", notAdded);
            }
        }
    }
}
