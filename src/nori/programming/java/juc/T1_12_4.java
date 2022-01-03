package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * reentrantlock必须手动释放锁
 *  lockInterruptibly()
 */
public class T1_12_4 {
    Lock lock = new ReentrantLock();

    void m1(){
        try {
            try {
                lock.lock();
                System.out.println("t1 start");
                // TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();
            System.out.println("m1 unlock!");
        }
    }

    void m2() {
        try{
            // 如果获取不到则进入catch语句
            // 如果获取锁则正常执行
            lock.lockInterruptibly();
            System.out.println("m2 Start!");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("m2 End!");
        } catch (InterruptedException e) {
            System.out.println("m2 interrupted!");
        }finally {
            lock.unlock();
            System.out.println("m2 unlock!");
        }
    }

    public static void main(String[] args) throws InstantiationException {
        T1_12_4 t1_12_1 = new T1_12_4();

        Thread t1 = new Thread(t1_12_1::m1, "Thread1");
        t1.start();

        Thread t2 = new Thread(t1_12_1::m2, "Thread2");
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.interrupt();
    }
}
