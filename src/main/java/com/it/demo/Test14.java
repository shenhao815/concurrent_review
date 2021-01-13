package com.it.demo;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程打断 park/unpark
 *
 * @author ch
 * @date 2021-1-11
 */
public class Test14 {
    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    private static void test3() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("park...");
            LockSupport.park();
            System.out.println("unpark...");
            System.out.println("打断状态"+Thread.currentThread().interrupted());

            LockSupport.park();
            System.out.println("unpark....2");
        }, "t1");
        t1.start();

        Thread.sleep(1000);

        // LockSupport.unpark(t1);
        t1.interrupt();
    }
}
