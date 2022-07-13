package com.jenjetsu.course.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jenjetsu.course.Business.*;
import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.ScreenTextDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.ProductKey;
import com.jenjetsu.course.Entity.Site;
import com.jenjetsu.course.Stages.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML private CheckBox forcedRemove;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField priceProduct;
    @FXML private TextField nameProduct;
    @FXML private TextField nameSite;
    @FXML private TextField typeProduct;
    @FXML private TextField regionSite;
    @FXML private TextField urlProduct;
    @FXML private TextField urlSite;

    @FXML void createProduct(ActionEvent event) {
        setDefault();
        boolean flag = false;
        if(urlProduct.getText().isEmpty()){
            urlProduct.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(nameProduct.getText().isEmpty()){
            nameProduct.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(priceProduct.getText().isEmpty()){
            priceProduct.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(typeProduct.getText().isEmpty()){
            typeProduct.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(flag){
            new WarningAlert("Filed error", "Field error", "Ошибка ввода, проверьте поля");
        } else
            new EntityCreator().createProduct(nameProduct.getText(), priceProduct.getText(), typeProduct.getText(), urlProduct.getText());
    }

    @FXML void createSite(ActionEvent event) {
        setDefault();
        boolean flag = false;
        if(urlSite.getText().isEmpty() || (!urlSite.getText().startsWith("www.") && !urlSite.getText().contains("https://"))){
            urlSite.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(nameSite.getText().isEmpty()){
            nameSite.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(regionSite.getText().isEmpty()){
            regionSite.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(flag)
            new WarningAlert("Filed error", "Field error", "Ошибка ввода, проверьте поля");
        else
            new EntityCreator().createSite(urlSite.getText(), nameSite.getText(), regionSite.getText());
    }

    @FXML
    void removeProduct(ActionEvent event) {
        setDefault();
        boolean flag = false;
        if(urlProduct.getText().isEmpty()){
            urlProduct.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(nameProduct.getText().isEmpty()){
            nameProduct.setStyle("-fx-border-color: red;");
            flag = true;
        }
        if(flag)
            new WarningAlert("Filed error", "Field error", "Ошибка ввода, проверьте поля");
        else
            new EntityRemover().removeProduct(nameProduct.getText(), urlProduct.getText());
    }

    @FXML
    void removeSite(ActionEvent event) throws MalformedURLException {
        setDefault();
        if(urlSite.getText().isEmpty()){
            urlSite.setStyle("-fx-border-color: red;");
            new WarningAlert("Field error","Field error","Ошибка ввода, проверьте поля");
            return;
        }
        new EntityRemover().removeSite(urlSite.getText(), forcedRemove.isSelected());
    }

    @FXML
    void searchProduct(ActionEvent event) {
        setDefault();
        ProductDatabase database = ProductDatabase.getInstance();
        if(!nameProduct.getText().isEmpty() && !urlProduct.getText().isEmpty() && database.contains(new ProductKey(nameProduct.getText(), urlProduct.getText()))){
            Product product = database.find(new ProductKey(nameProduct.getText(), urlProduct.getText()));
            if(!typeProduct.getText().isEmpty() && !product.getType().equals(typeProduct.getText()))
                return;
            if(!priceProduct.getText().isEmpty() && product.getCost() != Double.parseDouble(priceProduct.getText()))
                return;
            ScreenTextDatabase.getInstance().changeTextColor(product.toString(), "red");
        } else if(!urlProduct.getText().isEmpty()){
            LinkedList<Product> temp = database.findByUrl(urlProduct.getText());
            for(Product product : temp){
                if (!priceProduct.getText().isEmpty() && product.getCost() != Double.parseDouble(priceProduct.getText()))
                    continue;
                if(!typeProduct.getText().isEmpty() && product.getType().equals(typeProduct.getText()))
                    continue;
                ScreenTextDatabase.getInstance().changeTextColor(product.toString(), "red");
            }
        }  else if(!nameProduct.getText().isEmpty()){
            LinkedList<Product> temp = database.getList();
            for(Product product : temp){
                if (!priceProduct.getText().isEmpty() && product.getCost() != Double.parseDouble(priceProduct.getText()))
                    continue;
                if(!typeProduct.getText().isEmpty() && product.getType().equals(typeProduct.getText()))
                    continue;
                ScreenTextDatabase.getInstance().changeTextColor(product.toString(), "red");
            }
            } else if(!typeProduct.getText().isEmpty()){
            LinkedList<Product> temp = database.findByType(typeProduct.getText());
            for(Product product : temp){
                if (!priceProduct.getText().isEmpty() && product.getCost() != Double.parseDouble(priceProduct.getText()))
                    continue;
                ScreenTextDatabase.getInstance().changeTextColor(product.toString(), "red");
            }
        } else if(!priceProduct.getText().isEmpty()){
            LinkedList<Product> temp = database.getList();
            for (Product product : temp){
                if(product.getCost() == Double.parseDouble(priceProduct.getText())){
                    ScreenTextDatabase.getInstance().changeTextColor(product.toString(), "red");
                }
            }
        }
    }

    @FXML
    void searchSite(ActionEvent event) {
        setDefault();
        SiteDatabase database = SiteDatabase.getInstance();
        if(!urlSite.getText().isEmpty() && database.contains(urlSite.getText())){
            Site site = database.find(urlSite.getText());
            if(!nameSite.getText().isEmpty() && !site.getName().equals(nameSite.getText()))
                return;
            if(!regionSite.getText().isEmpty() && !site.getRegion().equals(regionSite.getText()))
                return;
            ScreenTextDatabase.getInstance().changeTextColor(site.toString(),"red");

        } else if(!regionSite.getText().isEmpty()){
            LinkedList<Site> sites = database.findByRegion(regionSite.getText());
            for(Site site : sites){
                if(site.getName().equals(nameSite.getText()) || nameSite.getText().isEmpty()){
                    ScreenTextDatabase.getInstance().changeTextColor(site.toString(),"red");
                }
            }
        } else {
            LinkedList<Site> sites = database.getList();
            for(Site site : sites){
                if(site.getName().equals(nameSite.getText())){
                    ScreenTextDatabase.getInstance().changeTextColor(site.toString(),"red");
                }
            }
        }
    }

    @FXML void ShowProductScreen(ActionEvent event) throws IOException {
        Stage stage = new ProductHashStage();
        stage.initOwner(priceProduct.getScene().getWindow());
        stage.show();
    }

    @FXML void showLogScreen(ActionEvent event) throws IOException {
        Stage stage = new LogStage();
        stage.initOwner(regionSite.getScene().getWindow());
        stage.show();
    }

    @FXML void showSearchScreen(ActionEvent event) throws IOException {
        SearchStage stage = new SearchStage();
        stage.initOwner(regionSite.getScene().getWindow());
        stage.show();
    }

    @FXML void showSiteTableScreen(ActionEvent event) throws IOException {
        Stage stage = new SiteHashStage();
        stage.initOwner(urlProduct.getScene().getWindow());
        stage.show();
    }

    @FXML void displayProductTreeView(ActionEvent event) throws IOException {
        Stage stage = new ProductTreeStage();
        stage.initOwner(urlProduct.getScene().getWindow());
        stage.show();
    }

    @FXML void displaySiteTreeView(ActionEvent event) throws IOException {
        Stage stage = new SiteTreeStage();
        stage.initOwner(priceProduct.getScene().getWindow());
        stage.show();
    }

    @FXML void readProductFile(ActionEvent event) throws FileNotFoundException { new ReadFilesLogic().readSites(); }

    @FXML void readSiteFile(ActionEvent event) throws FileNotFoundException { new ReadFilesLogic().readProducts(); }

    @FXML private void saveSitesToFile(ActionEvent event){ new SaveFilesLogic().saveSites(); }

    @FXML private void saveProductsToFile(ActionEvent event){ new SaveFilesLogic().saveProducts(); }

    @FXML private void close(ActionEvent event){
        Stage stage = (Stage) priceProduct.getScene().getWindow();
        stage.close();
    }

    @FXML private void about(){
        Stage currentStage = (Stage) regionSite.getScene().getWindow();
        Stage about = new AboutStage();
        about.initOwner(currentStage);
        about.show();
    }

    private void showAlertMessage(String alertName, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertName);
        alert.setContentText(message);
        alert.setHeaderText("Result");
        alert.showAndWait();
    }

    private void setDefault(){
        priceProduct.setStyle("-fx-fill: black;");
        urlProduct.setStyle("-fx-fill: black;");
        nameProduct.setStyle("-fx-fill: black;");
        typeProduct.setStyle("-fx-fill: black;");
        nameSite.setStyle("-fx-fill: black;");
        urlSite.setStyle("-fx-fill: black;");
        regionSite.setStyle("-fx-fill: black;");
        ScreenTextDatabase.getInstance().setDefaultColor();
    }

}
