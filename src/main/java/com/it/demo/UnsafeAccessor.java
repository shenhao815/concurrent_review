package com.it.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ch
 * @date 2021-1-14
 */
public class UnsafeAccessor {

    private static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new Error(e);
        }


    }

    public static Unsafe getUNSAFE() {
        return UNSAFE;
    }
}
