package nori.programming.java.juc;

import org.w3c.dom.ls.LSInput;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/***
 * 写时复制容器 CopyOnWriteArrayList.java
 * 多线程环境下，写时效率低，读时效率高
 * 适合写少读多的环境
 */
public class T2_9_1 {

    public static void main(String[] args) {
        openThread(new ArrayList<String>());
        openThread(Collections.synchronizedList(new ArrayList<String>()));
        openThread(new Vector<String>());
        openThread(new CopyOnWriteArrayList<String>());

        /***
         *  ArrayList
         *      69
         *      81994  多线程插入操作出问题
         *
         *  Collections.synchronizedList(new ArrayList<String>()
         *      40
         *      100000
         *
         *  Vector
         *      39
         *      100000
         *
         *  CopyOnWriteArrayList.java
         *      2369
         *      100000
         */
    }

    private static void openThread(List<String> list) {
        Random random = new Random();
        Thread[] threads = new Thread[100];

        for (int i = 0; i < threads.length; i++) {
            Runnable task = ()->{
                for (int j = 0; j < 1000; j++) {
                    list.add("a" + random.nextInt(10000));
                }
            };

            threads[i] = new Thread(task);
        }

        runAndComputeTime(threads);

        System.out.println(list.size());
    }

    private static void runAndComputeTime(Thread[] threads) {
        long start = System.currentTimeMillis();

        Arrays.asList(threads).forEach(thread -> thread.start());

        Arrays.asList(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

}