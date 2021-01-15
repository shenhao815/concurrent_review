package com.it.demo.n7;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class Test1 {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    synchronized (sdf) {

                        log.debug("{}", sdf.parse("2020-11-12"));

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
