package com.jenjetsu.course.Collection;

import java.util.Iterator;

public class LinkedList<E> implements Iterable<E>{

    private Node head;
    private Node lastElem;
    private int size;

    private class Node{
        Node next;
        Node pred;
        E data;
    }

    public LinkedList(){
    }

    public LinkedList(E elem){
        init();
        head.data = elem;
    }
    private void init(){
        if(head == null){
            head = new Node();
            lastElem = head;
            Node last = new Node();
            lastElem.next = last;
            last.pred = lastElem;
            size = 0;
        }
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void add(E elem){
        if(isEmpty()) {
            init();
            head.data = elem;
            size++;
            return;
        }
        Node newNode = new Node();
        newNode.data = elem;
        newNode.next = lastElem.next;
        lastElem.next = newNode;
        newNode.pred = lastElem;
        lastElem = newNode;
        size++;
    }

    public void pushFront(E elem){
        if(isEmpty()){
            init();
            head.data = elem;
            size++;
        } else {
            Node node = new Node();
            node.data = elem;
            node.pred = null;
            node.next = head;
            head = node;
            size++;
        }
    }

    public E remove(int index){
        if(isEmpty())
            return null;
        int counter = 0;
        Node jumper = head;
        while (jumper != lastElem.next){
            if(counter == index){
                return remove(jumper);
            }
            counter++;
            jumper = jumper.next;
        }
        return null;
    }

    public E remove(E elem){
        if(isEmpty())
            return null;
        Node jumper = head;
        while (jumper != lastElem.next){
            if(jumper.data.equals(elem)){
                return remove(jumper);
            }
            jumper = jumper.next;
        }
        return null;
    }

    private E remove(Node currentNode){
        Node pred = currentNode.pred;
        Node next = currentNode.next;
        if(pred == null && next == lastElem.next){
            head = null;
            return currentNode.data;
        } else if(pred == null){
            head = next;
            head.pred = null;
        } else if(next == lastElem.next){
            pred.next = lastElem.next;
            lastElem = pred;
        } else {
            pred.next = next;
            next.pred = pred;
        }
        size--;
        currentNode.next = null;
        currentNode.next = null;
        return currentNode.data;
    }

    public E get(int index){
        if(isEmpty())
            return null;
        int counter = 0;
        if(index > size ||  index < 0)
            return null;
        Node jumper = head;
        while (jumper != lastElem.next){
            if(counter == index){
                return jumper.data;
            }
            counter++;
            jumper = jumper.next;
        }
        return null;
    }

    public E get(E elem){
        if(isEmpty())
            return null;
        Node jumper = head;
        while (jumper != lastElem.next){
            if(jumper.data.equals(elem)){
                return jumper.data;
            }
            jumper = jumper.next;
        }
        return null;
    }

    public boolean contains(E elem){
        E find = get(elem);
        return elem != null;
    }

    public void clear(){
        while (head != lastElem.next){
            Node currentNode = head;
            head = head.next;
            head.pred = null;
            currentNode.next = null;
            currentNode.pred = null;
        }
        head = null;
        System.gc();
    }
    public int size(){
        return size;
    }

    public E first(){
        return head.data;
    }

    public E last(){
        return lastElem.data;
    }

    public E popBack(){
        return remove(head);
    }

    public E popFront(){
        return remove(lastElem);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node jumper = head;
            @Override
            public boolean hasNext() {
                return head != null && jumper != lastElem.next;
            }

            @Override
            public E next() {
                E temp = jumper.data;
                jumper = jumper.next;
                return temp;
            }
        };
    }

    public String toString(){
        if(isEmpty()) return "";
        String line = "";
        Node jumper = head;
        while (jumper != lastElem.next){
            line+= jumper.data.toString()+"|";
            jumper = jumper.next;
        }
        return line;
    }
}

