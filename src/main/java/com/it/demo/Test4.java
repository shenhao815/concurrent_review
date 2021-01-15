package com.it.demo;

/**
 * @author ch
 * @date 2021-1-14
 */
public class Test4 {

    static boolean run = true;
    static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                //synchronized (lock) {
                    if (!run) {
                        break;
                    }
                //}
            }

        }, "t1");

        t1.start();
        Thread.sleep(1000);
        synchronized (lock) {
            run = false;
        }
    }

}
