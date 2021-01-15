package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author ch
 * @date 2021-1-14
 */
@Slf4j
public class Test39 {
    public static void main(String[] args) {

        demo(
                ()->new int[10],
                (array)->array.length,
                (array,index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array))
        );

        demo(
                () -> new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array,index) -> array.getAndIncrement(index),
                (array) -> System.out.println(array)
        );

    }

    private static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T,Integer> lengthFun,
            BiConsumer<T,Integer> putConsumer,
            Consumer<T> printConsumer
    ){
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array,j%length);
                }
            }));
        }

        ts.forEach(Thread::start);
        ts.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        printConsumer.accept(array);
    }
}


