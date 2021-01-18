package com.it.threadLocalDemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Demo01")
public class Demo02 {

    ThreadLocal<String> t1 = new ThreadLocal();

    private String content;

    public String getContent() {
        // return content;
        return t1.get();
    }

    public void setContent(String content) {
        // this.content = content;
        t1.set(content);
    }


    public static void main(String[] args) {
        Demo02 demo01 = new Demo02();
        log.debug("start...");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {

                demo01.setContent(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("--------------------");
                log.debug(Thread.currentThread().getName() + "---->" + demo01.getContent());
            });
            thread.setName("线程" + i);
            thread.start();
        }
    }
}
