package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ch
 * @date 2021-1-14
 */
@Slf4j
public class Test36 {

    static AtomicReference<String> ref = new AtomicReference<>("a");

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");

        String prev = ref.get();

        other();

        Thread.sleep(1000);

        log.debug("change A->C {}",ref.compareAndSet(prev,"c"));
    }

    private static void other() {

        new Thread(() -> {
            log.debug("A->B {}", ref.compareAndSet(ref.get(), "b"));
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            log.debug("B->A {}", ref.compareAndSet(ref.get(), "a"));
        }).start();
    }
}
