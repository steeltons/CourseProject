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
        return Objects.hash(name, url);
    }
}
