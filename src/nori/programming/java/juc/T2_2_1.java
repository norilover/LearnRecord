package nori.programming.java.juc;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 实现类似 ReentrantLock 锁
 * 结构：
 *   MLock -> Lock
 *     |
 *   Sync -> AbstractQueuedSynchronizer
 */
public class T2_2_1 {
    static int m = 0;
    static Lock lock = new MLock();
    public static void main(String[] args) throws InstantiationException {
        Thread[] threads = new Thread[100];

        for (int i = 0; i < threads.length; i++){
            threads[i] = new Thread(()->{
                try{
                    lock.lock();
                    for (int j = 0; j < 100; j++) {
                        m++;
                    }
                }finally {
                    lock.unlock();
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(m);


        ReentrantLock reentrantLock = new ReentrantLock();
    }


    public static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, 1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);

            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }


    public static class MLock implements Lock{

        private Sync sync = new Sync();

        @Override
        public void lock() {
            sync.acquire(1);
        }

        @Override
        public void unlock() {
            sync.release(1);
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

}
