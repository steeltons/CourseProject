package com.jenjetsu.course.Database;

import com.jenjetsu.course.Collection.AVLTree;
import com.jenjetsu.course.Collection.CircleList;
import com.jenjetsu.course.Collection.HashTable;
import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;
import com.jenjetsu.course.utils.Constatnces;
import com.jenjetsu.course.utils.FileManipulator;
import com.jenjetsu.course.utils.ObjectCreator;

import java.util.Iterator;
import java.util.Map;


public class SiteDatabase {

    private static SiteDatabase data;
    private AVLTree<Site> tree;
    HashTable<String, String> table;

    private SiteDatabase(){
        tree = new AVLTree<>();
        tree.setComparator(((o1, o2) -> o1.getRegion().compareTo(o2.getRegion())));
        table = new HashTable<>();
        Site[] sites = new Site[0];
        try {
            sites = new ObjectCreator().readSitesFromFile(Constatnces.siteFilepath);
        } catch (Exception e) {
            // Логгер для добавления
        }
        for(Site site : sites){
            tree.add(site);
            table.put(site.getUrl(), site.getRegion());
        }
        //tree.printTree();
    }

    public LinkedList<Site> getList(){
        Iterator<LinkedList<Site>> iter = tree.iterator();
        LinkedList<Site> temp = new LinkedList<>();
        while (iter.hasNext()){
            for(Site site : iter.next()){
                temp.add(site);
            }
        }
        return temp;
    }
    public void add(Site site){
        if(!table.contains(site.getUrl())) {
            LogsDatabase.getInstance().startLogging();
            tree.add(site);
            table.put(site.getUrl(), site.getRegion());
        }
    }

    public void remove(String name){
        if(table.contains(name)){
            LogsDatabase.getInstance().startLogging();
            String region = table.find(name);
            Site temp = new Site(name, "", region);
            tree.find(temp).remove(temp);
            table.remove(name);
        }
    }

    public boolean contains(String name){
        return table.contains(name);
    }

    public LinkedList<Site> findByRegion(String region){
        return tree.find(new Site("","",region));
    }


    public Map.Entry<String, String>[] getTable(){
        return table.getTable();
    }

    public boolean[] getRemove(){
        return table.getRemoveStatus();
    }

    public Site find(String url){
        if(table.contains(url)){
            String region = table.find(url);
            Site site = new Site(url,"",region);
            return tree.find(site).get(site);
        } else {
            return null;
        }
    }

    public void close(){
        LinkedList<String> info = new LinkedList<>();
        for(LinkedList<Site> sites : tree){
            for(Site site : sites){
                info.add(site.toString());
            }
        }
        FileManipulator.eraseFile(Constatnces.siteFilepath);
        String[] array = new String[info.size()];
        int counter = 0;
        for(String s : info){
            array[counter++] = s;
        }
        tree.clear();
        info.clear();
        table.clear();
        FileManipulator.writeToFile(array, Constatnces.siteFilepath);
        data = null;
    }

    public LinkedList<AVLTree<Site>.Node> getSortedList(){
        return tree.BFS();
    }

    public static SiteDatabase getInstance(){
        if(data == null)
            data = new SiteDatabase();
        return data;
    }
}
