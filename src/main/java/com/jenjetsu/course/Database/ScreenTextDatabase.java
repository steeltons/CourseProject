package com.jenjetsu.course.Database;

import com.jenjetsu.course.Collection.CircleList;
import com.jenjetsu.course.Collection.HashTable;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScreenTextDatabase {

    private static ScreenTextDatabase inner;
    private TextFlow siteScreen;
    private TextFlow productScreen;
    private HashTable<Integer, Text> siteTextTable;
    private HashTable<Integer, Text> productTextTable;
    private ArrayList<Text> lastColored;
    private Font currentFont;
    private int siteCounter;
    private int productCounter;

    private ScreenTextDatabase(TextFlow site, TextFlow product){
        siteScreen = site;
        productScreen = product;
        siteTextTable = new HashTable<>(100);
        productTextTable = new HashTable<>(600);
        lastColored = new ArrayList<>();
        currentFont = Font.font("Comic sans", 16);
        siteCounter = 0;
        productCounter = 0;
    }

    public void displaySite(String siteText){
        LogsDatabase.getInstance().stopLogging();
        Text temp = createText((++siteCounter)+". "+siteText+"\n");
        siteTextTable.put(siteText.hashCode(), temp);
        siteScreen.getChildren().add(temp);
    }

    public void displayProduct(String productText){
        LogsDatabase.getInstance().stopLogging();
        Text temp = createText((++productCounter)+". "+productText+"\n");
        productTextTable.put(productText.hashCode(), temp);
        productScreen.getChildren().add(temp);
    }

    public void changeTextColor(String text, String color){
        if(siteTextTable.contains(text.hashCode())){
            Text temp = siteTextTable.find(text.hashCode());
            temp.setStyle("-fx-fill: "+color+";");
            lastColored.add(temp);
        } else if(productTextTable.contains(text.hashCode())){
            Text temp = productTextTable.find(text.hashCode());
            temp.setStyle("-fx-fill: "+color+";");
            lastColored.add(temp);
        } else {
            System.out.println("Wasn't founded");
        }
    }

    public void removeSite(String text){
        LogsDatabase.getInstance().stopLogging();
        if(siteTextTable.contains(text.hashCode())){
            Text text1 = siteTextTable.find(text.hashCode());
            siteTextTable.remove(text.hashCode());
            siteScreen.getChildren().remove(text1);
            siteCounter--;
//            siteScreen.getChildren().clear();
//            siteCounter = 0;
//            Iterator<Map.Entry<Integer, Text>> iterator = siteTextTable.iterator();
//            while (iterator.hasNext()){
//                String tempVal = iterator.next().getValue().getText();
//                String temp = tempVal.substring(tempVal.indexOf('.')+2, tempVal.length()-1);
//                Text text2 = createText((++siteCounter)+"."+temp+"\n");
//                siteScreen.getChildren().add(text2);
//                siteTextTable.put(text.hashCode(), text2);
//            }
        }
    }

    public void removeProduct(String productText){
        LogsDatabase.getInstance().stopLogging();
        if(productTextTable.contains(productText.hashCode())){
            Text text = productTextTable.find(productText.hashCode());
            productScreen.getChildren().remove(text);
            productTextTable.remove(productText.hashCode());
            productCounter--;
        }
    }

    public void setDefaultColor(){
        if(!lastColored.isEmpty()) {
            for(int i = 0; i < lastColored.size(); i++){
                lastColored.get(0).setStyle("-fx-fill: black;");
                lastColored.remove(0);
            }
        }
    }
    public static ScreenTextDatabase init(TextFlow site, TextFlow product){
        if(inner == null){
            inner = new ScreenTextDatabase(site, product);
        }
        return inner;
    }

    public static ScreenTextDatabase getInstance(){
        if(inner == null)
            throw new NullPointerException("Is null");
        return inner;
    }

    private Text createText(String line){
        Text text = new Text(line);
        text.setFont(currentFont);
        return text;
    }
}
