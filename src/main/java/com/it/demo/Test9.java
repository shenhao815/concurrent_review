package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ch
 * @date 2021-1-15
 */
@Slf4j
public class Test9 {
    static final List<String> menu = Arrays.asList("地三鲜", "123", "abas", "工");
    static Random random = new Random();
    static String cooking(){
        return menu.get(random.nextInt(menu.size()));}
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(() -> {
            log.debug("处理点餐...");
            Future<String> future = pool.submit(()->{
                log.debug("做菜");
                return cooking();
            });
            try {
                log.debug(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        pool.submit(() -> {
            log.debug("处理点餐...");
            Future<String> future = pool.submit(()->{
                log.debug("做菜");
                return cooking();
            });
            try {
                log.debug(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
