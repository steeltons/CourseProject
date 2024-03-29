package com.jenjetsu.course.Entity;

import java.util.Objects;

public class Site implements Comparable<Site>{

    private String url;
    private String name;
    private String region;

    public Site(String url, String name, String region) {
        this.url = url;
        this.name = name;
        this.region = region;
    }

    public Site() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return Objects.equals(url, site.url) && Objects.equals(region, site.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name, region);
    }

    @Override
    public String toString() {
        return url + " " + name + " " +region;
    }

    @Override
    public int compareTo(Site o) {
        if(this.equals(o))
            return 0;
        if(this.url.compareTo(o.url) != 0)
            return this.url.compareTo(o.url);
        if(this.name.compareTo(o.name) != 0)
            return this.name.compareTo(o.name);
        return this.region.compareTo(o.region);
    }
}
