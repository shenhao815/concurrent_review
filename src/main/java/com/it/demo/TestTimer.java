package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ch
 * @date 2021-1-15
 */
@Slf4j
public class TestTimer {

    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        pool.schedule(()->{
            log.debug("1");
            // int i =1/0;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1, TimeUnit.SECONDS);
        pool.schedule(()->{
            log.debug("2");

        },1, TimeUnit.SECONDS);

        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
        }, 1, 1, TimeUnit.SECONDS);
    }

}
