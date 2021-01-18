package com.it.demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test26_1 {
    public static void main(String[] args) throws InterruptedException {
        WaitNotify wn = new WaitNotify(2, 10);
        new Thread(() -> {
            wn.print("a",1,2);
        }).start();
        new Thread(() -> {
            wn.print("b",2,3);
        }).start();
        new Thread(() -> {
            wn.print("c",3,1);
        }).start();
    }
}

class WaitNotify {
    private int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String str, int cur, int nex) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != cur) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(str);
                flag = nex;
                this.notifyAll();
            }
        }
    }
}
