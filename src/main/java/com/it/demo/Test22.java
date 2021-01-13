package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ch
 * @date 2021-1-13
 */
@Slf4j
public class Test22 {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                if (!lock.tryLock(1, TimeUnit.HOURS)) {
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                log.debug("获取到锁..");
            }finally{
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        t1.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("打断 t1");
        t1.interrupt();
    }

}
