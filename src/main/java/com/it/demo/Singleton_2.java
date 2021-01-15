package com.it.demo;

/**
 * @author ch
 * @date 2021-1-14
 */
public final class Singleton_2 {

    private Singleton_2(){
    }

    private static final Singleton_2 SINGLETON_2 = new Singleton_2();

    public static Singleton_2 getInstance() {
        return SINGLETON_2;
    }

    public Object readResolve() {
        return SINGLETON_2;
    }
}
