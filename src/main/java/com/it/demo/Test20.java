package com.it.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author ch
 * @date 2021-1-12
 *
 * 保护性暂停模式
 */
@Slf4j
public class Test20 {
    public static void main(String[] args) {
        GuardedObject go = new GuardedObject();
        new Thread(() -> {
            log.info("等待结果");
            List<String> list = (List<String>) go.get(200);
            if (list == null || list.size() <= 0) {
                log.info("null");
            } else {
                log.info(list.size() + "");
            }
        },"t1").start();

        new Thread(() -> {
            log.info("执行下载");
            try {
                List<String> res = DownLoader.download();
                go.complete(res);
                log.info("下载完成");
            } catch (IOException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }

}



class GuardedObject {
    // 结果
    private Object response;

    public Object get(long timeout){
        synchronized (this) {
            long begin = System.currentTimeMillis();
            long passedtime = 0;
            while (response == null) {
                long waittime = timeout - passedtime;
                if (waittime <= 0) {
                    break;
                }
                try {
                    this.wait(waittime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedtime = System.currentTimeMillis()-begin;
            }
            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}
