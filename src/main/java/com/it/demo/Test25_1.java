package com.it.demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test25")
public class Test25_1 {
    static final Object lock = new Object();
    static boolean t2runned = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t1");
            }
        }, "t1");

        t1.start();

        new Thread(() -> {
            log.debug("t2");
            t2runned = true;
            synchronized (lock) {
                lock.notifyAll();
            }
        },"t2").start();

    }
}
