package com.jenjetsu.course.Business;

import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.ScreenTextDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.ProductKey;
import com.jenjetsu.course.Entity.Site;
import com.jenjetsu.course.utils.ObjectCreator;

public class EntityCreator {

    public void createSite(String url, String name, String region){
        SiteDatabase database = SiteDatabase.getInstance();
        if(!database.contains(url.replace("https://","www.")) && !database.contains(url.replace("www.","https://"))){
            Site site = null;
            try {
                site = new ObjectCreator().createSiteFormLine(url + " "+ name + " " + region);
            } catch (Exception e) {
                new WarningAlert("Site create error","Field error","Ошибка ввода, проверьте поля");
                return;
            }
            database.add(site);
            ScreenTextDatabase.getInstance().displaySite(site.toString());
        } else {
            new WarningAlert("Site create error","Creation error","Сайт с таким URL "+url+"уже существует");
        }
    }

    public void createProduct(String name, String cost, String type, String url){
        ProductDatabase database = ProductDatabase.getInstance();
        if(!database.contains(new ProductKey(name, url)) && (SiteDatabase.getInstance().contains(url.replace("www.","https://")) || SiteDatabase.getInstance().contains(url.replace("https://","www.")))){
            Product product = null;
            try {
                product = new ObjectCreator().createProductFromLine(name+" "+Double.parseDouble(cost)+" "+type+" "+url);
            } catch (Exception e) {
                new WarningAlert("Site create error","Field error","Ошибка ввода, проверьте поля");
                return;
            }
            database.add(product);
            ScreenTextDatabase.getInstance().displayProduct(product.toString());
        } else {
            new WarningAlert("Product create error","Creation error","Продукт с таким именеи и URL "+name+" "+url+" уже существует");
        }
    }
}
