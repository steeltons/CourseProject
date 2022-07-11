package com.jenjetsu.course.Database;

import com.jenjetsu.course.Collection.LinkedList;
import com.jenjetsu.course.Enums.DebugCategory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class LogsDatabase {
    private static LogsDatabase inner;
    private LinkedList<Map.Entry<DebugCategory, String>> logsPool;

    private volatile boolean stop;

    private LogsDatabase(){
        stop = false;
        logsPool = new LinkedList<>();
    }

    public void add(DebugCategory category, String info){
        if(!stop){
            if(logsPool.size() > 5000){
                //flushLogs();
            }
            logsPool.pushFront(new AbstractMap.SimpleEntry<DebugCategory, String>(category, info));
        }
    }

    private void flushLogs(){
        int size = logsPool.size();
        LinkedList<Map.Entry<DebugCategory, String>> flushList = new LinkedList<>();
        while (size > 5000){
            flushList.add(logsPool.popBack());
            size--;
        }
        try {
            FileWriter writer = new FileWriter("logs.txt", true);
            for(Map.Entry<DebugCategory, String> pair : flushList){
                String line =pair.getKey().toString() + " : "+ pair.getValue()+"\n";
                writer.write(line);
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public LinkedList<Map.Entry<DebugCategory, String>> getAllLogs(){
        return logsPool;
    }

    public void startLogging(){
        stop = false;
    }

    public void stopLogging(){
        stop = true;
    }


    public static LogsDatabase getInstance(){
        if(inner == null){
            inner = new LogsDatabase();
        }
        return inner;
    }
}
