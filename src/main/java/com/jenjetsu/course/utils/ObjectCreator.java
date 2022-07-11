package com.jenjetsu.course.utils;

import com.jenjetsu.course.Entity.Product;
import com.jenjetsu.course.Entity.Site;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ObjectCreator {

    public Product[] readProductsFromFile(String filepath) throws Exception {
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

    public Site[] readSitesFromFile(String filepath) throws Exception {
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

    public Product createProductFromLine(String line) throws Exception{
        String words[] = line.split(" ");
        String name = words[0];
        double cost = Double.parseDouble(words[1]);
        String type = words[2];
        String url = words[3];
        return new Product(name, cost, type, url);
    }

    public Site createSiteFormLine(String line) throws Exception{
        String words[] = line.split(" ");
        if(words.length != 3)
            throw new Exception();
        String url = words[0];
        String name = words[1];
        String region = words[2];
        return new Site(url, name, region);
    }

    public Product createProductFromLineV2(String line) throws Exception {
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

}
