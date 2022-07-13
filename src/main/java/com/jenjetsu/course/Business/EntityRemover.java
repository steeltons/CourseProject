package com.jenjetsu.course.Business;

import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Database.ProductDatabase;
import com.jenjetsu.course.Database.ScreenTextDatabase;
import com.jenjetsu.course.Database.SiteDatabase;
import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.ProductKey;
import com.jenjetsu.course.Entity.Site;

import java.util.Locale;

public class EntityRemover {

    public void removeSite(String url, boolean forcedRemove){
        SiteDatabase database = SiteDatabase.getInstance();
        url = url.toLowerCase(Locale.ROOT);
        String key = getCorrectSiteUrl(url);
        if(!key.isEmpty()){
            Site site = database.find(key);
            LinkedList<Product> products = ProductDatabase.getInstance().findByUrl(key);
            if(products.size() == 0 || forcedRemove){
                for(Product product : products){
                    ProductDatabase.getInstance().remove(product);
                    ScreenTextDatabase.getInstance().removeProduct(product.toString());
                }
                database.remove(key);
                ScreenTextDatabase.getInstance().removeSite(site.toString());
            } else {
                new WarningAlert("Remove error", "Site remove error", "Ошибка удаления сайта "+url+". Данный сайт содержит продукты в кол-ве "+products.size()+". Удалите все продукты вручную или выберите опцию forced remove");
            }
        } else {
            new WarningAlert("Remove error", "Site remove error", "Сайт с URL "+url+" не существует");
        }
    }

    public void removeProduct(String name, String url){
        ProductDatabase database = ProductDatabase.getInstance();
        url = url.toLowerCase(Locale.ROOT);
        String key = "";
        Product product = database.find(new ProductKey(name, url));
        if(product != null){
            database.remove(product.createKey());
            ScreenTextDatabase.getInstance().removeProduct(product.toString());
        } else {
            new WarningAlert("Remove error", "Product remove error", "Продукт с таким именеи и URL "+name+" "+url+" не существует");
        }
    }

    private String getCorrectSiteUrl(String url){
        String key = "";
        if(SiteDatabase.getInstance().contains(url.replace("https://","www."))){
            key = url.replace("https://","www.");
        } else if(SiteDatabase.getInstance().contains(url.replace("www.","https://"))){
            key = url.replace("www.","https://");
        }
        return key;
    }
}
