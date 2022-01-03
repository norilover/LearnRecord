package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * reentrantlock必须手动释放锁
 *  tryLock()
 */
public class T1_12_3 {
    Lock lock = new ReentrantLock();

    void m1(){
        try {
            try {
                lock.lock();
                System.out.println("t1 start");
                for (int i = 0; i < 10; i++) {
                    // TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("m1 " + i);
                }
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
        boolean tryLock = false;
        try{
            // 如果获取不到锁，则返回false
            tryLock = lock.tryLock(1, TimeUnit.SECONDS);

            if(tryLock)
                System.out.println("m2 lock!");
        }catch (Exception e){

        }finally {
            if(tryLock){
                lock.unlock();
                System.out.println("m2 unlock!");
            }
        }
    }

    public static void main(String[] args) throws InstantiationException {
        T1_12_3 t1_12_1 = new T1_12_3();

        Thread t1 = new Thread(t1_12_1::m1, "Thread1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(t1_12_1::m2, "Thread2");
        t2.start();
    }
}
