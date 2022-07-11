package com.jenjetsu.course.Entity;

import java.util.Objects;

public class Product implements Comparable<Product> {
    String name;
    double cost;
    String type;
    String url;

    public Product(String name, double cost, String type, String url) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.url = url;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductKey createKey(){
        return new ProductKey(name, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(type, product.type) && Objects.equals(url, product.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, url);
    }

    @Override
    public String toString(){
        return name + " " + cost + " " + type + " " + url;
    }

    @Override
    public int compareTo(Product o) {
        if(this.equals(o))
            return 0;
        if(this.url.compareTo(o.url) != 0)
            return this.url.compareTo(o.url);
        if(this.name.compareTo(o.name) != 0)
            return this.name.compareTo(o.name);
        return this.type.compareTo(o.type);
    }
}
