package com.jenjetsu.course.Database;

import com.jenjetsu.course.Enums.DebugCategory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class LogsDatabase {
    private static LogsDatabase inner;
    private ArrayList<String> logsPool;
    private ArrayList<Map.Entry<DebugCategory, String>> logsPool1;

    private volatile boolean stop;

    private LogsDatabase(){
        stop = false;
        logsPool = new ArrayList<>();
        logsPool1 = new ArrayList<>();
    }

    public void add(String info){
        if(!stop){
            logsPool.add(info);
        }
    }

    public void add(DebugCategory category, String info){
        if(!stop){
            logsPool1.add(new AbstractMap.SimpleEntry<DebugCategory, String>(category, info));
        }
    }

    public ArrayList<String> getAllLogs(){
        return logsPool;
    }

    public ArrayList<Map.Entry<DebugCategory, String>> getAllLogs1(){
        return logsPool1;
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
