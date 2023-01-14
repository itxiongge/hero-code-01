package com.hero.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Demo08ABA {

    static AtomicInteger ar = new AtomicInteger(100);
    static AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("======ABA问题的产生======");

        Thread t1 = new Thread(() -> {
            ar.compareAndSet(100, 101);
            ar.compareAndSet(101, 100);
            //100 -- 101 -- 100
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            System.out.println(ar.compareAndSet(100, 2023) + "\t" + ar.get());
        }, "t2");
        t2.start();

        //顺序执行，AtomicInteger案例先执行
        t1.join();
        t2.join();

        System.out.println("======ABA问题的解决======");
        new Thread(() -> {
            int stamp = asr.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号： " + stamp);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            asr.compareAndSet(100,101, asr.getStamp(), asr.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t第二次版本号： " + asr.getStamp());

            asr.compareAndSet(101,100, asr.getStamp(), asr.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t第三次版本号： " + asr.getStamp());
        }, "t3").start();

        new Thread(() -> {

            int stamp = asr.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号： " + stamp);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean result= asr.compareAndSet(100,2023, stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t修改成功与否："+result+"  当前最新版本号"+ asr.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前实际值："+ asr.getReference());
        }, "t4").start();
    }
}
