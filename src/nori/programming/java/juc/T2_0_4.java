package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/***
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * volatile
 * LockSupport park() & unpark()
 */
public class T2_0_4 {
    volatile List list = new ArrayList();

    static Thread thread1 = null;
    static Thread thread2 = null;

    void add(Object object){list.add(object);}
    int size(){return list.size();}

    public static void main(String[] args) throws InstantiationException {
        final T2_0_4 t2 = new T2_0_4();

        thread2 = new Thread(()-> {
            System.out.println("Thread 2");

            // if(t2.size() != 5){
                LockSupport.park();
                System.out.println("Thread 2 find the length of list is 5");
                LockSupport.unpark(thread1);
            // }
        });


       thread1 = new Thread(()-> {
            System.out.println("Thread 1");

                for (int i = 1; i < 10; i++) {
                    t2.add(new Object());
                    System.out.println("Add " + i + " to list!");

                    // 尽量使用notifyAll
                    if (i == 5) {
                        // 通知 Thread 2
                        LockSupport.unpark(thread2);
                        LockSupport.park();
                    }
                }
        });

        thread1.start();
//        //
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }

        thread2.start();



    }
}
