//package com.jenjetsu.course.utils;
//
//import com.jenjetsu.course.Collection.AVLTree;
//import com.jenjetsu.course.Collection.CircleList;
//import com.jenjetsu.course.Collection.LinkedList;
//import com.jenjetsu.course.Database.ProductDatabase;
//import com.jenjetsu.course.Database.SiteDatabase;
//import com.jenjetsu.course.Entity.Product;
//import com.jenjetsu.course.Entity.Site;
//
//import java.util.HashMap;
//import java.util.Map;
//
//// Объединяет две таблички в одну
//public class Searcher {
//
//    public Map<Site, CircleList<Product>> find(String region, String productType){
//        Map<Site, LinkedList<Product>> map = new HashMap<>();
//        LinkedList<Product> productCircleList = ProductDatabase.getInstance().findByType(productType);
//        CircleList<Site> siteByRegion = SiteDatabase.getInstance().findByRegion(region);
//        AVLTree<Product> productAVLTree = new AVLTree<>();
//        productAVLTree.setComparator(((o1, o2) -> o1.getUrl().compareTo(o2.getUrl())));
//        if(productCircleList.isEmpty() || siteByRegion.isEmpty())
//            return new HashMap<>();
//        for(Product pr : productCircleList){
//            productAVLTree.add(pr);
//        }
//        for(Site site : siteByRegion){
//            LinkedList<Product> result = productAVLTree.find(new Product("",0,"",site.getUrl()));
//            if(!result.isEmpty()){
//                map.put(site, result);
//            }
//        }
//        return map;
//    }
//
//}
