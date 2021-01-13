package com.it.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ch
 * @date 2021-1-13
 */
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Object A = new Object();
        Object B = new Object();

        new Thread(() -> {
            synchronized (A) {
                log.info("lock A");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    log.info("lock B");
                    log.debug("操作。。。");
                };

            }
        },"t1").start();

        new Thread(() -> {
            synchronized (B) {
                log.debug("lock B");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (A) {
                    log.debug("lock A");
                    log.debug("操作2。。。");
                }
            }
        },"t2").start();
    }
}
