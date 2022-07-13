package com.jenjetsu.course.Collection;

import com.jenjetsu.course.Database.LogsDatabase;
import com.jenjetsu.course.Enums.DebugCategory;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class HashTable<K, V> implements Iterable<Entry<K, V>>{
    private Entry<K, V>[] table;
    private int bucketCount;
    private int currentBucketLoad = 0;
    private float loadFactor = 0.75f;
    private boolean[] tableCellsRemoveStatus;

    public HashTable(){
        bucketCount = 8;
        table = new Entry[bucketCount];
        LogsDatabase.getInstance().add(DebugCategory.INSERT,"Инициализация таблицы размером 8");
        tableCellsRemoveStatus = new boolean[bucketCount];
    }

    public HashTable(int size){
        if(size <= 0)
            throw new IllegalArgumentException("Illegal size "+size);
        bucketCount = size;
        table = new Entry[size];
        LogsDatabase.getInstance().add(DebugCategory.INSERT, "Инициализация таблицы размером "+ size);
        tableCellsRemoveStatus = new boolean[bucketCount];
    }

    public HashTable(int size, float loadFactor){
        if(size <= 0)
            throw new IllegalArgumentException("Illegal size "+size);
        if(loadFactor == 0 || Math.abs(loadFactor) > 1 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal loadFactor "+loadFactor);
        bucketCount = size;
        table = new Entry[size];
        tableCellsRemoveStatus = new boolean[bucketCount];
        this.loadFactor = loadFactor;
        LogsDatabase.getInstance().add(DebugCategory.INSERT, "Инициализация таблицы размером "+ size+" и процентом загрузки "+loadFactor);
    }

    public void put(K key, V value){
        int index = firstHashFunction(key);
        LogsDatabase.getInstance().add(DebugCategory.INSERT, "Добавляем пару <"+key.toString()+" : "+value.toString()+"> "+" текущий индекс "+index);
        if(table[index] != null && table[index].getKey().equals(key)){
            putItemToTable(index, key, value);
            LogsDatabase.getInstance().add(DebugCategory.INSERT, "Добавляем по индексу "+index);
            return;
        }
        if(table[index] != null) {
            LogsDatabase.getInstance().add(DebugCategory.COLLISION, "Коллизия пары <"+key.toString()+" : "+value.toString()+"> на индексе "+index+" разрешаем коллизию");
            index = collisionsResolver(key, index);
        }
        LogsDatabase.getInstance().add(DebugCategory.SECOND_INSERT, "Добавляем по индексу "+index);
        putItemToTable(index, key, value);
    }

    private void putItemToTable(int index, K key, V value){
        Entry<K, V> prev = table[index];
        if(prev != null){
            LogsDatabase.getInstance().add(DebugCategory.COLLISION, "Заменяем пару <"+prev.getKey().toString()+" : "+prev.getValue().toString()+"> на <"+key.toString()+" : "+value.toString()+"> ");
            LogsDatabase.getInstance().add(DebugCategory.COLLISION, "Статус предыдущей ячейки : "+((tableCellsRemoveStatus[index]) ? " удалён" : " не удалён"));
        }
        table[index] = new AbstractMap.SimpleEntry(key, value);
        if(prev == null || tableCellsRemoveStatus[index]){
            tableCellsRemoveStatus[index] = false;
            currentBucketLoad++;
            if(currentBucketLoad >= bucketCount * loadFactor)
                remakeTable(bucketCount * 2);
        }
    }

    public void remove(K key){
        int index = (int) (firstHashFunction(key) % bucketCount);
        LogsDatabase.getInstance().add(DebugCategory.REMOVE, "Удаляем по ключу : "+key.toString()+" текущий индекс "+index);
        if(!remove(key, index)){
            LogsDatabase.getInstance().add(DebugCategory.COLLISION, "Коллизия по ключу "+key.toString()+" на индексе "+index+" разрешаем коллизию");
            index = collisionsResolver(key, index);
            LogsDatabase.getInstance().add(DebugCategory.SECOND_REMOVE, "Удаляем по ключу : "+key.toString()+" текущий индекс "+index);
            //LogsDatabase.getInstance().add("Текущая заполненость таблицы : "+bucketCount+" макксимум заполнения для пересоздания при удалении : "+(bucketCount * (1 - loadFactor)));
            if(tableCellsRemoveStatus[index])
                return;
            remove(key, index);
        }
    }

    private boolean remove(K key, int index){
        if(table[index] == null){
            return true;
        }
        if(table[index].getKey().equals(key)){
            if(tableCellsRemoveStatus[index])
                return true;
            currentBucketLoad--;
            tableCellsRemoveStatus[index] = true;
            if (currentBucketLoad <= bucketCount * (1 - loadFactor))
                remakeTable(bucketCount / 2);
            return true;
        }
        return false;
    }

    public V find(K key){
        int index = (int) (firstHashFunction(key) % bucketCount);
        if(table[index] == null)
            return null;
        if(table[index].getKey().equals(key) && !tableCellsRemoveStatus[index]){
            return table[index].getValue();
        }
        index = collisionsResolver(key, index);
        if(table[index] == null)
            return null;
        if(table[index].getKey().equals(key) && !tableCellsRemoveStatus[index]){
            return table[index].getValue();
        }
        return null;
    }

    public boolean contains(K key){
        int index = (int) (firstHashFunction(key) % bucketCount);
        if(table[index] == null)
            return false;
        if(table[index].getKey().equals(key) && !tableCellsRemoveStatus[index])
            return true;
        index = collisionsResolver(key, index);
        if(table[index] == null)
            return false;
        if(table[index].getKey().equals(key) && !tableCellsRemoveStatus[index]){
            return true;
        }
        return false;
    }

    public void print(){
        for(int i = 0; i < bucketCount; i++){
            if(table[i] != null){
                System.out.println(i+" =>{"+table[i].getKey().toString()+" : "+table[i].getValue().toString()+" status : } "+tableCellsRemoveStatus[i]);
            } else {
                System.out.println("NULL");
            }
        }
    }

    public void clear(){
        for(int i = 0; i < bucketCount; i++){
            if(table[i] != null)
                table[i] = null;
        }
        System.gc();
    }

    public int size(){
        return currentBucketLoad;
    }

    public float getLoadFactor(){
        return loadFactor;
    }

    public void setLoadFactor(float loadFactor){
        if(Math.abs(loadFactor) > 1)
            throw new IllegalArgumentException("loadFactor is "+loadFactor);
        this.loadFactor = loadFactor;
    }

    public Entry<K,V>[] getTable(){
        return table;
    }

    public boolean[] getRemoveStatus(){
        return tableCellsRemoveStatus;
    }

    private int firstHashFunction(K key){
        int hash = Math.abs(key.hashCode());
        LogsDatabase.getInstance().add(DebugCategory.FIRST_HASH,"Значение первичной хэш-функции для ключа "+key.toString()+" : "+hash+" индекс в таблице : "+(hash % bucketCount));
        return hash % bucketCount;
    }

    private int secondHashFunction(int keyHash, int step, int q1, int q2){
        int hash = Math.abs(keyHash + step * q1 + step * step * q2);
        LogsDatabase.getInstance().add(DebugCategory.SECOND_HASH, "Значение вторичной хэш-функции для первичного хэша "+keyHash+" : "+hash+" индекс в таблице : "+(hash % bucketCount)+" шаг "+step+" q1 = "+q1+" q2 = "+q2);
        return hash % bucketCount;
    }

    private Long secondHashFunction(long keyHash, int step, int k){
        return keyHash + step * k;
    }

    private int collisionsResolver(K key, int currentIndex){
        final int q1 = (31 % bucketCount == 0 ? 30 : 31);
        final int q2 = bucketCount;
        int insertIndex = -1;
        final int oldIndex = currentIndex;
        int keyHash = firstHashFunction(key);
        int step = 1;
        do {
            currentIndex = secondHashFunction(keyHash, step, q1, q2);
            if(table[currentIndex] == null) {
                return (insertIndex != -1) ? insertIndex : currentIndex;
            }
            if(table[currentIndex] != null && tableCellsRemoveStatus[currentIndex] && insertIndex == -1){
                    insertIndex = currentIndex;
            }
            if(table[currentIndex] != null && table[currentIndex].getKey().equals(key) && ! tableCellsRemoveStatus[currentIndex]) {
                return currentIndex;
            }
            step++;
        } while (oldIndex != currentIndex);
        return (insertIndex != -1) ? insertIndex : currentIndex;
    }

    private void remakeTable(int newSize){
        LogsDatabase.getInstance().add(DebugCategory.INSERT, "Изменяем размер таблицы с " + bucketCount +" на "+newSize);
        Entry<K, V>[] tempTable = table;
        boolean[] tempRemove = tableCellsRemoveStatus;
        bucketCount = newSize;
        currentBucketLoad = 0;
        table = new Entry[newSize];
        tableCellsRemoveStatus = new boolean[newSize];
        for(int i = 0; i < tempTable.length; i++){
            if(tempTable[i] != null && !tempRemove[i]){
                put(tempTable[i].getKey(), tempTable[i].getValue());
            }
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                while (table[index] == null || tableCellsRemoveStatus[index]){
                    if(index >= bucketCount)
                        return false;
                    index++;
                }
                return true;
            }

            @Override
            public Entry<K, V> next() {
                return table[index];
            }
        };
    }
}
