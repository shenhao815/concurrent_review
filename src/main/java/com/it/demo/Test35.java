package com.it.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ch
 * @date 2021-1-14
 */
public class Test35 {
    public static void main(String[] args) {
        AccountBigDecimal a = new AccountBGCas(new BigDecimal(10000));
        AccountBigDecimal.demo(a);
    }
}

class AccountBGCas implements AccountBigDecimal {

    private AtomicReference<BigDecimal> balance;

    public AccountBGCas(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal get() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {

        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }

    }
}

interface AccountBigDecimal{
    BigDecimal get();

    void withdraw(BigDecimal account);

    static void demo(AccountBigDecimal account){
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }

        list.forEach(Thread::start);
        list.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(account.get());
    }
}
