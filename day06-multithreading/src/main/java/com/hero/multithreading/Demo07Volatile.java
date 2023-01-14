package com.hero.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo07Volatile {
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();

        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(demo);
            t.start();
        }

        Thread.sleep(1000);
        System.out.println("count = "+demo.count);
    }

    static class VolatileDemo implements Runnable {
        public AtomicInteger count = new AtomicInteger(0);

        public void run() {
            addCount();
        }

        public void addCount() {
            for (int i = 0; i < 10000; i++) {
                count.incrementAndGet();//线程安全的原子操作：原子是如何实现的呢？一条CPU原子指令
            }
        }
    }
}
