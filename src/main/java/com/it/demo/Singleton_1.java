package com.it.demo;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021-1-14
 */
public final class Singleton_1 implements Serializable {

    private Singleton_1(){}

    private static volatile Singleton_1 INSTANCE = null;

    public static Singleton_1 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton_1.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton_1();
                }
            }
        }
        return INSTANCE;
    }

    public Object readResolve(){
        return INSTANCE;
    }

}
