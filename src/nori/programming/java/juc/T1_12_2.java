package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * reentrantlock用于替代synchronized
 */
public class T1_12_2 {
    Lock lock = new ReentrantLock();

    void m1(){
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("m1" + i);

                if(i == 2)
                    m2();
            }
        }finally {
            lock.unlock();
            System.out.println("m1 unlock!");
        }
    }

    synchronized void m2() {
        lock.lock();
        try{
            System.out.println("m2");
        }finally {
            lock.unlock();
            System.out.println("m2 unlock!");
        }
    }

    public static void main(String[] args) throws InstantiationException {
        T1_12_2 t1_12_1 = new T1_12_2();

        new Thread(t1_12_1::m1, "Thread1").start();
//        new Thread(t1_12_1::m2, "Thread2").start();
    }
}
