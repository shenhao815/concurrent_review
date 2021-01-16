package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {
    public static void main(String[] args) {
        DataContainer d = new DataContainer();

        new Thread(() -> {
            d.read();
        },"t1").start();

        new Thread(() -> {
            d.write();
        },"t2").start();
    }
}

@Slf4j
class DataContainer{
    private Object data;

    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read() {
        log.debug("get readLock...");
        r.lock();
        try{
            log.debug("read ....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return data;
        }finally{
            log.debug("release readLock");
            r.unlock();
        }
    }

    public void write() {
        log.debug("get writeLock...");
        w.lock();
        try{
            log.debug("write ...");
        }finally{
            log.debug("release writeLock");
            w.unlock();
        }
    }
}

