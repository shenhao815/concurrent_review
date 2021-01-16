package com.it.demo.n7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
    public static void main(String[] args) {
        /*ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new MyTask(5)));*/

        System.out.println(test(5));
    }

    private static int test(int i) {
        if (i == 1) {
            return 1;
        }
        return i + test(i - 1);
    }
}

class MyTask extends RecursiveTask<Integer> {
    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            return 1;
        }
        MyTask t1 = new MyTask(n - 1);
        t1.fork();
        Integer re = t1.join();
        return n+re;

    }
}
