package com.it.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ch
 * @date 2021-1-13
 */
@Slf4j(topic = "c.Test3")
public class Test3 {
    static int count = 10;
    static final Object lock = new Object();
    public static void main(String[] args) {

        new Thread(() -> {
            while (count > 0) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                log.debug("count: {}",count);
            }
        },"t1").start();

        new Thread(() -> {
            while (count <20) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                log.debug("count: {}",count);
            }
        },"t2").start();
    }
}
