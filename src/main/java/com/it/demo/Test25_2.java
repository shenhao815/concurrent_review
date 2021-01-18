package com.it.demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test25_2 {
    static volatile boolean t2runned = false;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!t2runned) {
            }
            log.debug("t1");

        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("t2");
            t2runned = true;
        }, "t2");

        t1.start();
        Thread.sleep(2000);
        t2.start();
    }
}
