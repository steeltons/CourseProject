package com.jenjetsu.course.utils;

import com.jenjetsu.course.Collection.CircleList;

import java.io.*;
import java.util.Scanner;

public class FileManipulator {

    private FileManipulator(){}

    public static String[] readSFromFile(String filepath){
        try {
            File inputFile = new File(filepath);
            if(!inputFile.exists())
                throw new FileNotFoundException("Файл по пути "+filepath+" не существует");
            Scanner scanner = new Scanner(inputFile);
            CircleList<String> inputStrings = new CircleList<>();
            while (scanner.hasNext()){
                inputStrings.add(scanner.nextLine());
            }
            String array[] = new String[inputStrings.size()];
            int arrayCounter = 0;
            for(String s : inputStrings){
                array[arrayCounter++] = s;
            }
            scanner.close();
            return array;
        } catch (FileNotFoundException e){
            System.out.println("Нет такого файла по пути "+filepath);
        }
        return new String[0];
    }

    public static void writeToFile(String[] information, String filepath){
        try {
            FileWriter writer = new FileWriter(new File(filepath));
            for(String s : information){
                writer.write(s+"\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(String filepath, String[] args){
        try {
            FileWriter writer = new FileWriter(new File(filepath), true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for(String arg : args){
                bufferedWriter.write(arg+"\n");
                bufferedWriter.flush();
            }
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeLineFormFile(String filepath, String removeLine){
        File file = new File(filepath);
        try {
            Scanner scanner = new Scanner(file);
            StringSearchDriver driver = RaitaAlgorithm.Driver(removeLine);
            CircleList<String> stringArray = new CircleList<>();
            while (scanner.hasNext()){
                String inputline = scanner.nextLine();
                if(driver.search(inputline, false).size() == 0){
                    stringArray.add(inputline);
                }
            }
            FileWriter writer = new FileWriter(file);
            for(String output : stringArray){
                writer.write(output+"\n");
                writer.flush();
            }
            writer.close();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void eraseFile(String filepath){
        File file = new File(filepath);
        if(file.exists()){
            file.delete();
        }
    }
}
