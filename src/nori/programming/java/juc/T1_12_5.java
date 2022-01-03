package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * reentrantlock必须手动释放锁
 *  默认非公平锁
 *  ReentrantLock(true) : 公平锁
 */
public class T1_12_5 {
    Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InstantiationException {
        T1_12_5 t1_12_1 = new T1_12_5();

        Thread t1 = new Thread(t1_12_1::m1, "Thread1");
        t1.start();

        Thread t2 = new Thread(t1_12_1::m1, "Thread2");
        t2.start();
    }

    private void m1() {
        for (int i = 0; i < 100; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " Require Lock!");
            }finally {
                lock.unlock();
            }
        }
    }
}
