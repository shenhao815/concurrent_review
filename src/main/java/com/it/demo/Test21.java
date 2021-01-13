package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author ch
 * @date 2021-1-13
 */
@Slf4j
public class Test21 {

    public static void main(String[] args) {
        MessageBox bm = new MessageBox(2);
        for (int i = 0; i < 30; i++) {
            int j = i;
            new Thread(() -> {
                 bm.add(new Message(j,"值"+j));
            },"t1").start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message take = bm.take();
                log.info(take.toString());
            }
        },"t2").start();

    }

}

@Slf4j
class MessageBox{

    private final LinkedList<Message> list = new LinkedList<>();
    private int counter = 0;

    public MessageBox(int counter) {
        this.counter = counter;
    }

    public Message take() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.info("容器为空，暂停取出 {}",list.size());
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = list.removeFirst();
            list.notifyAll();
            return message;
        }
    }

    public void add(Message message) {
        synchronized (list) {
            while (list.size() >= counter) {
                try {
                    log.info("容器已满，暂停存入 {}",list.size());
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(message);
            list.notifyAll();
        }
    }



}

class Message{
    private Integer id;
    private Object obj;

    public Message(Integer id, Object obj) {
        this.id = id;
        this.obj = obj;
    }

    public Integer getId() {
        return id;
    }

    public Object getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", obj=" + obj +
                '}';
    }
}
