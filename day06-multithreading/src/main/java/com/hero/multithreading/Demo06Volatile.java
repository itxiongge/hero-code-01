package com.hero.multithreading;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo06Volatile {
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();

        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(demo);
            t.start();
        }

        Thread.sleep(1000);
        System.out.println("count = "+demo.count);//20000
    }

    static class VolatileDemo implements Runnable {
        public volatile int count;
        //public static AtomicInteger count = new AtomicInteger(0);
        private Lock lock = new ReentrantLock();


        public void run() {
            addCount();
        }

        //public synchronized void addCount() {
        public void addCount() {
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                lock.lock();
                lock.lock();
                count++;//怎么理解原子性操作？
                //count++;是一行代码，但是却不是原子性的，是jvm指令层面的原子操作
                //count.incrementAndGet();//作用等同于++
                lock.unlock();
            }
        }
    }
}
