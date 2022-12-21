package com.hero.jvm.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
-Xmx600m -Xms600m -XX:+PrintGCDetails
*/
public class HeapInstance {
    public static void main(String[] args) {
        List<Picture> list = new ArrayList<>();
        while (true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024 * 1024)));//4K + 12K =16K
        }
    }
}

class Picture{
    private byte[] pixels;
    public Picture(int length){
        this.pixels = new byte[length];
    }
}
