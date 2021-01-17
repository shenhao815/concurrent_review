package com.it.threadLocalDemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Demo01")
public class Demo01 {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
        Demo01 demo01 = new Demo01();
        log.debug("start...");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                synchronized (Demo01.class) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    demo01.setContent(Thread.currentThread().getName());
                    log.debug("--------------------");
                    log.debug(Thread.currentThread().getName() + "---->" + demo01.getContent());
                }
            });
            thread.setName("线程"+i);
            thread.start();
        }
    }
}
