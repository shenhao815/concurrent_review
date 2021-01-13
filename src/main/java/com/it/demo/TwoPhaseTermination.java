package com.it.demo;

/**
 * 两阶段终止
 *
 * @author ch
 * @date 2021-1-11
 */
public class TwoPhaseTermination {

    public static void main(String[] args) throws InterruptedException {
        _TwoPhaseTermination tpt = new _TwoPhaseTermination();
        tpt.start();
        Thread.sleep(4500);

        tpt.stop();

    }
}

class _TwoPhaseTermination{
    private Thread monitor;

    public void start(){
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                System.out.println(monitor.hashCode());
                System.out.println(current.hashCode());
                System.out.println(current == monitor);
                if (monitor.isInterrupted()) {
                    System.out.println("料理后事。。。");
                    break;
                }
                try {
                    Thread.sleep(2000);
                    System.out.println("执行监控");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    monitor.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }

}
