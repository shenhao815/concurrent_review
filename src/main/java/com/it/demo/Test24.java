package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ch
 * @date 2021-1-13
 */
@Slf4j(topic = "c.Test24")
public class Test24 {
    static ReentrantLock ROOM = new ReentrantLock();
    static boolean hasCig = false;
    static boolean hasTake = false;
    static Condition cigSet = ROOM.newCondition();
    static Condition takeSet = ROOM.newCondition();


    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("有烟没？[{}]", hasCig);
                while (!hasCig) {
                    try {
                        cigSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟，干活...");
            } finally {
                ROOM.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            ROOM.lock();
            try{
                log.debug("有外卖没？[{}]", hasTake);
                while (!hasTake) {
                    try {
                        takeSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有外卖，干活...");
            }finally{
                ROOM.unlock();
            }

        }, "小女").start();

        Thread.sleep(1000);

        new Thread(() -> {
            ROOM.lock();
            try{
                hasTake = true;
                takeSet.signalAll();
            }finally{
                ROOM.unlock();
            }
        }).start();
        Thread.sleep(1000);

        new Thread(() -> {
            // ROOM.lock();
            // try{
                hasCig = true;
                cigSet.signalAll();
            // }finally{
            //     ROOM.unlock();
            // }
        }).start();
    }
}
