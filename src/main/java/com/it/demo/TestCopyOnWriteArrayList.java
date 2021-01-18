package com.it.demo;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestCopyOnWriteArrayList {

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Iterator<Integer> iterator = list.iterator();

        new Thread(() -> {
            list.remove(0);
        }).start();

        Thread.sleep(1000);

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
