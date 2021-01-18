package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ch
 * @date 2021-1-15
 */
@Slf4j
public class Test7 {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(() -> {
            log.debug("1");
        });

        pool.execute(() -> {
            log.debug("2");
        });
        pool.execute(() -> {
            log.debug("3");
        });
        pool.execute(() -> {
            log.debug("4");
        });
    }

}
