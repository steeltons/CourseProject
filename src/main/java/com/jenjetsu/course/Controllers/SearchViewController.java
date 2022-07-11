package com.jenjetsu.course.Controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import com.jenjetsu.course.Collection.AVLTree;
import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.ScreenTextDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SearchViewController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private CheckBox isFlushToFile;
    @FXML
    private TextField regionField;
    @FXML
    private TextFlow resultScreen;
    @FXML
    private TextField typeField;

    @FXML
    private void onSearchButtonClick(ActionEvent event){
        resultScreen.getChildren().clear();
        ScreenTextDatabase.getInstance().setDefaultColor();
        if(regionField.getText().isEmpty()){
            regionField.setStyle("-fx-border-color: red;");
            return;
        }
        if(typeField.getText().isEmpty()){
            typeField.setStyle("-fx-border-color: red;");
            return;
        }
        ArrayList<Map.Entry<String, LinkedList<Product>>> res = result();
        if(res == null)
            return;
        for(Map.Entry<String, LinkedList<Product>> pair : res){
            for (Product pr : pair.getValue()){
                ScreenTextDatabase.getInstance().changeTextColor(pr.toString(), "red");
                Text text = new Text(pair.getKey() + " -> "+pr.getName()+" "+pr.getCost()+" "+pr.getType()+"\n");
                text.setFont(Font.font("Comic Sans",16));
                resultScreen.getChildren().add(text);
            }
        }

        if(isFlushToFile.isSelected()){
            try {
                FileWriter writer = new FileWriter("searchResult.txt");
                for(Map.Entry<String, LinkedList<Product>> pair : res){
                    for(Product pr : pair.getValue()){
                        String text = pair.getKey() + " -> "+pr.toString()+"\n";
                        writer.write(text);
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Map.Entry<String, LinkedList<Product>>> result(){
        LinkedList<Site> sites = SiteDatabase.getInstance().findByRegion(regionField.getText());
        LinkedList<Product> products = ProductDatabase.getInstance().findByType(typeField.getText());
        if(sites.isEmpty() || products.isEmpty()){
            Text text = new Text("Nothing wasn't founded");
            text.setFont(Font.font("Comic sans",18));
            text.setStyle("-fx-fill: red;");
            resultScreen.getChildren().add(text);
            return null;
        }
        AVLTree<Product> productAVLTree = new AVLTree<>();
        productAVLTree.setComparator(((o1, o2) -> o1.getUrl().compareTo(o2.getUrl())));
        ArrayList<Map.Entry<String, LinkedList<Product>>> res = new ArrayList<>();
        int ii = sites.size();
        for(int i = 0; i < products.size(); i++){
            productAVLTree.add(products.get(i));
        }

        for(int i = 0; i < sites.size(); i++){
            if(productAVLTree.contains(new Product("", 0.0, "", sites.get(i).getUrl()))){
                LinkedList<Product> productCircleList = productAVLTree.find(new Product("", 0.0, "", sites.get(i).getUrl()));
                if(!productCircleList.isEmpty()){
                    ScreenTextDatabase.getInstance().changeTextColor(sites.get(i).toString(), "red");
                    res.add(new AbstractMap.SimpleEntry<String, LinkedList<Product>>(sites.get(i).getUrl(), productCircleList));
                }
            }
        }
        return res;
    }
}
