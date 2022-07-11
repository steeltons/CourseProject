package com.jenjetsu.course.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jenjetsu.course.Collection.CircleList;
import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.ScreenTextDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.ProductKey;
import com.jenjetsu.course.Entity.Site;
import com.jenjetsu.course.HelloApplication;
import com.jenjetsu.course.utils.ObjectCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    @FXML private Button searchButton;
    @FXML private TextField urlProduct;
    @FXML private TextField urlSite;

    @FXML
    void createProduct(ActionEvent event) {
        setDefault();
        if(urlProduct.getText().isEmpty()){
            urlProduct.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        if(nameProduct.getText().isEmpty()){
            nameProduct.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        if(priceProduct.getText().isEmpty()){
            priceProduct.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        if(typeProduct.getText().isEmpty()){
            typeProduct.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        ProductDatabase database = ProductDatabase.getInstance();
        if(!database.contains(new ProductKey(nameProduct.getText(), urlProduct.getText())) || ! SiteDatabase.getInstance().contains(urlProduct.getText())){
            Product product = new ObjectCreator().createProductFromLine(nameProduct.getText()+" "+Double.parseDouble(priceProduct.getText())+" "+typeProduct.getText()+" "+urlProduct.getText());
            database.add(product);
            ScreenTextDatabase.getInstance().displayProduct(product.toString());
        } else {
            showAlertMessage("Product create error","Продукт с таким именеи и URL "+nameProduct.getText()+" "+urlProduct.getText()+" уже существует");
        }
    }

    @FXML
    void createSite(ActionEvent event) {
        setDefault();
        if(urlSite.getText().isEmpty() || (!urlSite.getText().startsWith("www.") && !urlSite.getText().contains("https://"))){
            urlSite.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        if(nameSite.getText().isEmpty()){
            nameSite.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        if(regionSite.getText().isEmpty()){
            regionSite.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        SiteDatabase database = SiteDatabase.getInstance();
        if(!database.contains(urlSite.getText().replace("https://","www.")) && !database.contains(urlSite.getText().replace("www.","https://"))){
            Site site = new ObjectCreator().createSiteFormLine(urlSite.getText() + " "+ nameSite.getText() + " " + regionSite.getText());
            database.add(site);
            ScreenTextDatabase.getInstance().displaySite(site.toString());
        } else {
            showAlertMessage("Site create error","Сайт с URL "+urlSite.getText()+" уже существует");
        }
    }

    @FXML
    void removeProduct(ActionEvent event) {
        setDefault();
        if(urlProduct.getText().isEmpty() || (!urlProduct.getText().startsWith("www.") && !urlProduct.getText().contains("https://"))){
            urlProduct.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        if(nameProduct.getText().isEmpty()){
            nameProduct.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        ProductDatabase database = ProductDatabase.getInstance();
        String inputUrl = urlProduct.getText();
        ProductKey key = null;
        if(database.contains(new ProductKey(nameProduct.getText(), inputUrl.replace("www.", "https://")))){
            key = new ProductKey(nameProduct.getText(), inputUrl.replace("www.", "https://"));
        } else if(database.contains(new ProductKey(nameProduct.getText(),inputUrl.replace("https://","www.")))){
            key = new ProductKey(nameProduct.getText(),inputUrl.replace("https://","www."));
        }
        if(key != null){
            Product product = database.find(key);
            database.remove(key);
            ScreenTextDatabase.getInstance().removeProduct(product.toString());
        } else {
            showAlertMessage("Product create error","Продукт с таким именеи и URL "+nameProduct.getText()+" "+urlProduct.getText()+" не существует");
        }
    }

    @FXML
    void removeSite(ActionEvent event) throws MalformedURLException {
        setDefault();
        if(urlSite.getText().isEmpty()){
            urlSite.setStyle("-fx-border-color: red;");
            showAlertMessage("Field error","Ошибка ввода, проверьте поля");
            return;
        }
        SiteDatabase database = SiteDatabase.getInstance();
        String key = "";
        if(database.contains(urlSite.getText().replace("www.","https://"))){
            key = urlSite.getText().replace("www.","https://");
        } else if(database.contains(urlSite.getText().replace("https://","www."))){
            key = urlSite.getText().replace("https://","www.");
        }
        if(!key.isEmpty()){
            Site site = database.find(key);
            LinkedList<Product> productLinkedList = ProductDatabase.getInstance().getListByUrl(key);
            if(productLinkedList.size() > 0 && !forcedRemove.isSelected()){
                showAlertMessage("Site remove error","Ошибка удаления сайта "+urlSite.getText()+". Данный сайт содержит продукты в кол-ве "+productLinkedList.size()+". Удалите все продукты вручную или выберите опцию forced remove");
                return;
            }
            for(Product product : productLinkedList){
                ProductDatabase.getInstance().remove(product);
                ScreenTextDatabase.getInstance().removeProduct(product.toString());
            }
            database.remove(key);
            ScreenTextDatabase.getInstance().removeSite(site.toString());
        } else {
            showAlertMessage("Site create error","Сайт с URL "+urlSite.getText()+" не существует");
        }
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

    @FXML
    void ShowProductScreen(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\ProductTableView.fxml");
        FXMLLoader loader = new FXMLLoader(file.toURL());
        Stage stage = new Stage();
        stage.setTitle("Site table");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load(), 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void showLogScreen(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\LoggerView.fxml");
        FXMLLoader loader = new FXMLLoader(file.toURL());
        Stage stage = new Stage();
        stage.setTitle("Search");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load(), 600, 800);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void showSearchScreen(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\SearchView.fxml");
        FXMLLoader loader = new FXMLLoader(file.toURL());
        Stage stage = new Stage();
        stage.setTitle("Search");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load(), 450, 650);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void showSiteTableScreen(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\HashTableView.fxml");
        FXMLLoader loader = new FXMLLoader(file.toURL());
        Stage stage = new Stage();
        stage.setTitle("Site table");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load(), 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void displayProductTreeView(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\ProductTreeView.fxml");
        FXMLLoader loader = new FXMLLoader(file.toURL());
        Stage stage = new Stage();
        stage.setTitle("Product tree container");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load(), 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void displaySiteTreeView(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\User\\IdeaProjects\\Course\\src\\main\\resources\\com\\jenjetsu\\course\\SiteTreeView.fxml");
        FXMLLoader loader = new FXMLLoader(file.toURL());
        Stage stage = new Stage();
        stage.setTitle("Site tree container");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader.load(), 500, 500);
        stage.setScene(scene);
        stage.show();
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
