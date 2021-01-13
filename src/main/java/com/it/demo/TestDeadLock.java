package com.it.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ch
 * @date 2021-1-13
 */
@Slf4j(topic = "c.TestDeadLock")
public class TestDeadLock {
    public static void main(String[] args) {
        Chopstick2 c1 = new Chopstick2("c1");
        Chopstick2 c2 = new Chopstick2("c2");
        Chopstick2 c3 = new Chopstick2("c3");
        Chopstick2 c4 = new Chopstick2("c4");
        Chopstick2 c5 = new Chopstick2("c5");

        new Philosopher2("苏格的拉底", c1, c2).start();
        new Philosopher2("柏拉图", c2, c3).start();
        new Philosopher2("亚里士多德", c3, c4).start();
        new Philosopher2("赫拉克利特", c4, c5).start();
        new Philosopher2("阿基米德", c5, c1).start();
    }

}

@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread{
    Chopstick2 left;
    Chopstick2 right;

    public Philosopher(String name, Chopstick2 left, Chopstick2 right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (left) {
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    private void eat() {
        log.debug("eating...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Chopstick {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "name='" + name + '\'' +
                '}';
    }
}
