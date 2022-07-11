package com.jenjetsu.course.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.jenjetsu.course.Collection.AVLTree;
import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;
import com.jenjetsu.course.Entity.SiteView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeSiteController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TreeView<String> treeScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        treeScreen.setShowRoot(true);
        LinkedList<AVLTree<Site>.Node> list = SiteDatabase.getInstance().getSortedList();
        ArrayList<String> addedList = new ArrayList<>();
        TreeItem<String>[] array = new TreeItem[list.size()];
        int counter = 0;
        for(AVLTree<Site>.Node node : list){
            if(addedList.contains(node.toString())){
                int index = addedList.indexOf(node.toString());
                if(node.getLeft() != null){
                    TreeItem<String> left = new TreeItem<>(node.getLeft().toString());
                    array[index].getChildren().add(left);
                    addedList.add(node.getLeft().toString());
                    array[++counter] = left;
                }
                if(node.getRight() != null){
                    TreeItem<String> right = new TreeItem<>(node.getRight().toString());
                    array[index].getChildren().add(right);
                    addedList.add(node.getRight().toString());
                    array[++counter] = right;
                }

            } else {
                TreeItem<String> item = new TreeItem<>(node.toString());
                addedList.add(node.toString());
                if(node.getLeft() != null){
                    TreeItem<String> left = new TreeItem<>(node.getLeft().toString());
                    item.getChildren().add(left);
                    addedList.add(node.getLeft().toString());
                    array[++counter] = left;
                }
                if(node.getRight() != null){
                    TreeItem<String> right = new TreeItem<>(node.getRight().toString());
                    item.getChildren().add(right);
                    addedList.add(node.getRight().toString());
                    array[++counter] = right;
                }
                array[0] = item;
            }
        }
        treeScreen.setRoot(array[0]);
    }
}