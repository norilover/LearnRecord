package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/***
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * volatile
 * Semaphore acquire() & release()
 */
public class T2_0_5 {
    volatile List list = new ArrayList();

    static Thread thread1 = null;
    static Thread thread2 = null;

    void add(Object object){list.add(object);}
    int size(){return list.size();}

    public static void main(String[] args) throws InstantiationException {
        final T2_0_5 t2 = new T2_0_5();

        Semaphore semaphore = new Semaphore(1);

        thread2 = new Thread(()-> {
            System.out.println("Thread 2");

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread 2 find the length of list is 5");

            semaphore.release();
        });


       thread1 = new Thread(()-> {
            System.out.println("Thread 1");

           try {
               semaphore.acquire();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           for (int i = 1; i < 6; i++) {
                    t2.add(new Object());
                    System.out.println("Add " + i + " to list!");
           }
           semaphore.release();

                thread2.start();
           try {
               thread2.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

           try {
               semaphore.acquire();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           for (int i = 6; i < 10; i++) {
               t2.add(new Object());
               System.out.println("Add " + i + " to list!");
           }
           semaphore.release();

       });

        thread1.start();
    }
}
