package nori.programming.java.juc;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * ReadWriteLock
 */
public class T1_17_1 {

    private static int value;

    static void read(Lock lock){
        try{
            lock.lock();
            Thread.sleep(1000);
            System.out.println("Read over");
        }catch (Exception exception){}
        finally {
            lock.unlock();
        }
    }

    static void write(Lock lock){
        try{
            lock.lock();
            T1_17_1.value = new Random().nextInt();
            Thread.sleep(11111);
            System.out.println("Write over");
        }catch (Exception exception){}
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InstantiationException {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        for (int i = 0; i < 20; i++) {
            new Thread(()->{read(readWriteLock.readLock());}).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(()->{write(readWriteLock.writeLock());}).start();
        }
    }
}
