package com.jenjetsu.course.utils;

import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ObjectCreator {

    public Product[] readProductsFromFile(String filepath){
        String lines[] = FileManipulator.readSFromFile(filepath);
        Product array[] = new Product[lines.length];
        int arrayCounter = 0;
        for(String s : lines){
            String words[] = s.split(" ");
            Product product = createProductFromLine(s);
            if(!product.equals(new Product()))
                array[arrayCounter++] = product;
        }
        return array;
    }

    public Site[] readSitesFromFile(String filepath){
        String lines[] = FileManipulator.readSFromFile(filepath);
        Site array[] = new Site[lines.length];
        int arrayCounter = 0;
        int totalLines = 1;
        for(String s : lines){
            Site site = createSiteFormLine(s);
            if(!site.equals(new Site()))
                array[arrayCounter++] = site;
        }
        return array;
    }

    public Product createProductFromLine(String line){
        String words[] = line.split(" ");
        try {
            if(words.length > 4)
                throw new ArrayIndexOutOfBoundsException();
            String name = words[0];
            double cost = Double.parseDouble(words[1]);
            String type = words[2];
            String url = words[3];
            return new Product(name, cost, type, url);
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Слов в строке меньше 4-ех слов");
        } catch (ClassCastException e){
            System.out.println("Неверно введена стоимость товар");
        }
        return new Product();
    }

    public Site createSiteFormLine(String line){
        String words[] = line.split(" ");
        try{
            String url = words[0];
            String name = words[1];
            String region = words[2];
            return new Site(url, name, region);
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Слов в строке меньше 3-её");
        }
        return new Site();
    }

    public Product createProductFromLineV2(String line){
        Product tempProduct = createProductFromLine(line);
        StringSearchDriver algorithm = RaitaAlgorithm.Driver(tempProduct.getUrl());
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\User\\IdeaProjects\\CourseProject\\src\\main\\java\\sites.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()){
                stringBuilder.append(scanner.next()+" ");
                scanner.nextLine();
            }
            if(algorithm.search(stringBuilder.toString(), false).size() > 0)
                return tempProduct;
            else
                throw new FileNotFoundException("Нет такого сайта в файле");
        } catch (FileNotFoundException e) {
            System.out.println("Сайт не существует");
        }
        return new Product();
    }

    public Site createSiteFromFileV2(String line){
        Site s = createSiteFormLine(line);
        String pattern = (s.getUrl().contains("//") ? s.getUrl().substring(s.getUrl().indexOf("//")+2, s.getUrl().lastIndexOf("."))
                : s.getUrl().substring(s.getUrl().indexOf(".")+1,s.getUrl().lastIndexOf(".")));
        StringSearchDriver algorithm = RaitaAlgorithm.Driver(pattern);
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\User\\IdeaProjects\\CourseProject\\src\\main\\java\\sites.txt"));
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()){
                String url = scanner.next();
                builder.append(url.substring(url.indexOf(".")+1,url.lastIndexOf("."))+" ");
                scanner.nextLine();
            }
            if(algorithm.search(builder.toString(), false).size() == 0){
                return s;
            } else
                throw new FileNotFoundException("Url сайта повторяется");
        } catch (FileNotFoundException e) {
            System.out.println("Сайт повторяется");
        }
        return new Site();
    }

    public void removeProduct(String productName){

    }
}
