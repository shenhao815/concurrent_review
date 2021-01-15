package com.it.demo;

/**
 * @author ch
 * @date 2021-1-14
 */
public final class Singleton_3 {
    private Singleton_3(){}

    private static class _S{
        private final static Singleton_3 singleton3 = new Singleton_3();
    }

    public static Singleton_3 getInstance() {
        return _S.singleton3;
    }

}
