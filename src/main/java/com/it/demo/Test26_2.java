package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Test26_2 {

    public static void main(String[] args) throws InterruptedException {
        AwaitSignal as = new AwaitSignal(5);
        Condition a = as.newCondition();
        Condition b = as.newCondition();
        Condition c = as.newCondition();

        new Thread(() -> {
            as.print("a",a,b);
        }).start();
        new Thread(() -> {
            as.print("b",b,c);
        }).start();
        new Thread(() -> {
            as.print("c",c,a);
        }).start();
        Thread.sleep(1000);
        as.lock();
        try{
            a.signalAll();
        }finally{
            as.unlock();

        }
    }
}

class AwaitSignal extends ReentrantLock{
    private int loopNum;

    public AwaitSignal(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String str,Condition cur,Condition next) {
        for (int i = 0; i < loopNum; i++) {
            lock();
            try{
                cur.await();
                System.out.print(str);
                next.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                unlock();
            }
        }
    }

}
