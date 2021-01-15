package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Test25_4 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("t2");
            LockSupport.unpark(t1);
        }, "t2");

        t1.start();
        Thread.sleep(2000);
        t2.start();
    }
}
