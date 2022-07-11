package com.jenjetsu.course.Database;

import com.jenjetsu.course.Collection.AVLTree;
import com.jenjetsu.course.Collection.CircleList;
import com.jenjetsu.course.Collection.HashTable;
import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.ProductKey;
import com.jenjetsu.course.utils.Constatnces;
import com.jenjetsu.course.utils.FileManipulator;
import com.jenjetsu.course.utils.ObjectCreator;

import java.util.Iterator;
import java.util.Map;

public class ProductDatabase {

    private static ProductDatabase data;
    private AVLTree<Product> tree;
    private HashTable<ProductKey, String> table;
    private AVLTree<Product> urlTree;

    private ProductDatabase(){
        tree = new AVLTree<>();
        urlTree = new AVLTree<>();
        tree.setComparator(((o1, o2) -> o1.getType().compareTo(o2.getType())));
        urlTree.setComparator(((o1, o2) -> o1.getUrl().compareTo(o2.getUrl())));
        table = new HashTable<>();
        Product products[] = new ObjectCreator().readProductsFromFile(Constatnces.productFilepath);
        for(Product pr : products){
            tree.add(pr);
            urlTree.add(pr);
            table.put(pr.createKey(), pr.getType());
        }
        tree.printTree();
    }

    public void add(Product product){
        LogsDatabase.getInstance().startLogging();
        tree.add(product);
        table.put(product.createKey(), product.getType());
    }

    public Map.Entry<ProductKey, String>[] getTable(){
        return table.getTable();
    }

    public boolean[] getRemove(){
        return table.getRemoveStatus();
    }

    public void remove(Product product){
        if(table.contains(product.createKey())) {
            String type = table.find(product.createKey());
            product.setType(type);
            tree.remove(product);
            table.remove(product.createKey());
        }
    }

    public void remove(ProductKey key){
        LogsDatabase.getInstance().startLogging();
        String region = table.find(key);
        table.remove(key);
        Product temp = new Product(key.getName(), 0, region, key.getUrl());
        tree.remove(temp);
        urlTree.remove(temp);
    }

    public LinkedList<Product> getListByUrl(String url){
        return urlTree.find(new Product("",0,"",url));
    }

    public LinkedList<Product> getList(){
        Iterator<LinkedList<Product>> iter = tree.iterator();
        LinkedList<Product> temp = new LinkedList<>();
        while (iter.hasNext()){
            for(Product pr : iter.next()){
                temp.add(pr);
            }
        }
        return temp;
    }

    public boolean contains(Product product){
        return table.contains(product.createKey());
    }

    public boolean contains(ProductKey key){
        return table.contains(key);
    }

    public LinkedList<Product> findByType(String type){
        return tree.find(new Product("", 0, type, ""));
    }

    public LinkedList<Product> findByUrl(String url){ return urlTree.find(new Product("",0,"", url)); }

    public Product find(ProductKey key){
        if(table.contains(key)){
            String type = table.find(key);
            Product product = new Product(key.getName(), 0, type, key.getUrl());
            return tree.find(product).get(product);
        } else {
            return null;
        }
    }

    public void close(){
        CircleList<String> info = new CircleList<>();
        for(LinkedList<Product> products : tree){
            for(Product pr : products){
                info.add(pr.toString());
            }
        }
        FileManipulator.eraseFile(Constatnces.productFilepath);
        String[] array = new String[info.size()];
        int counter = 0;
        for(String s : info){
            array[counter++] = s;
        }
        tree.clear();
        info.clear();
        FileManipulator.writeToFile(array, Constatnces.productFilepath);
        data = null;
    }

    public LinkedList<AVLTree<Product>.Node> getSortedList(){
        return tree.BFS();
    }

    public Iterator<LinkedList<Product>> getIterator(){
        return tree.iterator();
    }

    public static ProductDatabase getInstance(){
        if(data == null)
            data = new ProductDatabase();
        return data;
    }
}
