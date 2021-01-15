package com.it.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ch
 * @date 2021-1-14
 */
public class Test34 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(5);

        i.getAndUpdate(operand ->
                operand * 5);

        System.out.println(i.get());
    }

}
