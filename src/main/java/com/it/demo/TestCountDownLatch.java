package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(10);
        String[] all = new String[10];
        CountDownLatch latch = new CountDownLatch(10);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int k = i;
            pool.submit(() -> {
                for (int j = 0; j <= 100; j++) {
                    try {
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    all[k] = j + "%";
                    System.out.print("\r" + Arrays.toString(all));

                }
                latch.countDown();
            });
        }

        latch.await();
        log.debug("\n" + "游戏开始");
        pool.shutdown();

    }
}
