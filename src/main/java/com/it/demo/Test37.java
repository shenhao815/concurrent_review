package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author ch
 * @date 2021-1-14
 */
@Slf4j
public class Test37 {

    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("a",0);

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");

        String prev = ref.getReference();
        int stamp = ref.getStamp();

        other();

        Thread.sleep(1000);

        log.debug("change A->C {}",ref.compareAndSet(prev,"c",stamp,stamp +1));
    }

    private static void other() {

        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("A->B {}", ref.compareAndSet(ref.getReference(), "b",stamp,stamp+1 ));
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("B->A {}", ref.compareAndSet(ref.getReference(), "a",stamp,stamp+1));
        }).start();
    }
}
