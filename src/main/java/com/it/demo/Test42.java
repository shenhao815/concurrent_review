package com.it.demo;

import sun.misc.Unsafe;

import java.io.File;

/**
 * @author ch
 * @date 2021-1-14
 */
public class Test42 {
    public static void main(String[] args) {
        Account account = new MyA(10000);
        Account.demo(account);
    }
}

class MyA implements Account{

    MyAtomicInteger balance;

    public MyA(int balance) {
        this.balance = new MyAtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        balance.decrement(amount);
    }
}

class MyAtomicInteger{
    private volatile int value;

    private static final long valueOffset;
    private static final Unsafe UNSAFE;
    static {

        UNSAFE = UnsafeAccessor.getUNSAFE();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this,valueOffset,prev,next)) {
                break;
            }
        }
    }
}