package nori.programming.java.juc;

import jdk.nashorn.internal.runtime.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * CountDownLatch
 */
public class T1_13_1 {

    public static void main(String[] args) throws InstantiationException {
//        basicUsageCountDownLatch();

//        usageJoinCountDownLatch();
    }

    private static void basicUsageCountDownLatch() {
        Thread[] threadArr = new Thread[100];

        CountDownLatch countDownLatch = new CountDownLatch(threadArr.length);

        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread(()->{
                int result = 0;
                for (int j = 0; j < 10000; j++) {
                    result += j;
                }
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName());
            }, "Thead " + i);
        }

        for (Thread thread : threadArr) {
            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End await!");
    }


    private static void usageJoinCountDownLatch(){
        Thread[] threadArr = new Thread[100];

        CountDownLatch countDownLatch = new CountDownLatch(threadArr.length);

        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread(()->{
                int result = 0;
                for (int j = 0; j < 10000; j++) {
                    result += j;
                }
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName());
            }, "Thead " + i);
        }

        for (Thread thread : threadArr) {
            thread.start();
        }

        for (Thread thread : threadArr) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End join!");
    }
}
