package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ch
 * @date 2021-1-14
 */
@Slf4j
public class Test5 {

    public static void main(String[] args) {
        Account account = new AccountCas(10000);
        Account.demo(account);

    }
}

class AccountCas implements Account {
    private AtomicInteger balance;

    public AccountCas(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        /*while (true) {
            int prev = balance.get();
            int next = prev - amount;
            if (balance.compareAndSet(prev,next)) {
                break;
            }
        }*/
        balance.addAndGet(-1 * amount);
    }
}

class AccountUnsafe implements Account{

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        this.balance -= amount;
    }
}

interface Account{
    // 获取余额
    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }

        long start = System.nanoTime();
        ts.forEach(Thread::start);

        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();

        System.out.println(account.getBalance() + " cost: " + (end-start)/1000_000 + " ms");
    }
}
