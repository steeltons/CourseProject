package com.jenjetsu.course.Entity;

import java.util.Objects;

public class ProductKey {

    private final String name;
    private final String url;

    public ProductKey(String name, String url){
        this.name = name;
        this.url = url;
    }

    ProductKey(){
        name = "";
        url = "";
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString(){
        return name + " " + url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductKey that = (ProductKey) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if(name.length() % 3 != 0){
            int sum = 0;
            for(int i = 0; i < name.length() % 3; i++)
                sum += name.charAt(i);
            hash += sum * 31;
        }
        for(int i = name.length() % 3; i < name.length(); i+=3){
            int sum = 0;
            for (int j = 0; j < 3; j++){
                sum+= name.charAt(i+j);
            }
            hash+= sum;
        }

        if(url.length() % 3 != 0){
            int sum = 0;
            for(int i = 0; i < url.length() % 3; i++)
                sum += url.charAt(i);
            hash += sum * 31;
        }
        for(int i = url.length() % 3; i < url.length(); i+=3){
            int sum = 0;
            for (int j = 0; j < 3; j++){
                sum+= url.charAt(i+j);
            }
            hash+= sum;
        }
        return hash;
    }

}
