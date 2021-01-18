package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ch
 * @date 2021-1-15
 */
@Slf4j
public class Test8 {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<?> r1 = pool.submit(() -> {
            log.debug("b1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end1");
        });
        Future<?> r2 = pool.submit(() -> {
            log.debug("b2");
            Thread.sleep(1000);
            log.debug("end2");
            return 1;
        });
        Future<?> r3 = pool.submit(() -> {
            log.debug("b3");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end3");
        });

        List<Runnable> runnables = pool.shutdownNow();
        log.debug("other... {} ", runnables.size());
    }
}
