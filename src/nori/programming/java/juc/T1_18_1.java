package nori.programming.java.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * Semaphore
 */
public class T1_18_1 {

    // permit = 1 表示只允许一个线程执行
    private static Semaphore semaphore = new Semaphore(1, true);

    public static void main(String[] args) throws InstantiationException {
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T1 Running!");
                Thread.sleep(200);
                System.out.println("T1 Running!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();

        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T2 Running!");
                Thread.sleep(200);
                System.out.println("T2 Running!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();
    }
}
