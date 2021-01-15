package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Test25_3 {
    static final ReentrantLock LOCK = new ReentrantLock();
    static Condition condition = LOCK.newCondition();


    static boolean t2runned = false;
    public static void main(String[] args) {

        new Thread(() -> {
            try{
                LOCK.lock();
                while (!t2runned) {
                    condition.await();
                }
                log.debug("t1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                LOCK.unlock();
            }
        },"t1").start();

        new Thread(() -> {
            try{
                LOCK.lock();
                log.debug("t2");
                t2runned =true;
                condition.signalAll();
            }finally{
                LOCK.unlock();
            }
        },"t2").start();
    }
}
