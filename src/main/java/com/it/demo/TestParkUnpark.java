package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author ch
 * @date 2021-1-13
 */
@Slf4j
public class TestParkUnpark {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
            LockSupport.park();
            log.debug("resume2...");

        }, "t1");
        t1.start();

        Thread.sleep(2000);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
