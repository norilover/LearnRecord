package nori.programming.java.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/***
 * Map:
 *  HashMap, TreeMap, LinkedHashMap
 */
public class T2_8_1 {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();

        Random random = new Random();
       Thread[] threads = new Thread[100];

        CountDownLatch latch = new CountDownLatch(threads.length);
        long start = System.currentTimeMillis();

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    map.put("a" + random.nextInt(100000), "a" + random.nextInt(100000));
                }

                latch.countDown();
            });
        }

        Arrays.asList(threads).forEach(thread -> thread.start());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(map.size());
    }
}