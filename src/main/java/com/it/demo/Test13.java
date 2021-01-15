package com.it.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ch
 * @date 2021-1-14
 */
@Slf4j
public class Test13 {
    public static void main(String[] args) throws InterruptedException {
        TwoTerminal tt = new TwoTerminal();
        //Thread t1 = new Thread(tt::start, "t1");

        tt.start();
        tt.start();

        tt.start();



    }
}

@Slf4j
class TwoTerminal {

    private volatile boolean flag = false;

    // 监控线程
    private Thread monitorThread;

    // 判断监控线程是否已运行
    private volatile boolean running = false;

    public void start() {
        if (running) {
            return;
        }
        synchronized (this) {
            if (running) {
                return;
            }
            running = true;
        }
        monitorThread = new Thread(() -> {

            while (true) {
                if (flag) {
                    log.debug("被打断，退出。。。");
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    log.debug("interrrupted by stop。。。");

                }
                log.debug("执行监控...");
            }

        });
        monitorThread.start();
    }

    public void stop() {
        this.flag = true;
        monitorThread.interrupt();
        running = false;

    }
}
