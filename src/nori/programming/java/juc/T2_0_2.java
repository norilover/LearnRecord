package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * volatile
 * Object 的 notify() & wait()
 */
public class T2_0_2 {
    volatile List list = new ArrayList();

    void add(Object object){list.add(object);}
    int size(){return list.size();}

    public static void main(String[] args) throws InstantiationException {
        final T2_0_2 t2 = new T2_0_2();
        final Object lock = new Object();

        Thread thread2 = new Thread(()-> {
            System.out.println("Thread 2");

            synchronized (lock){
                if(t2.size() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread 2 find the length of list is 5");



//                     // 通知 Thread 1
//                     lock.notify();


                }
            }
        });
        thread2.start();

        Thread thread1 = new Thread(()-> {
            System.out.println("Thread 1");

            synchronized (lock){
                for (int i = 1; i < 10; i++) {
                    t2.add(new Object());
                    System.out.println("Add " + i + " to list!");

                    // 尽量使用notifyAll
                    if(i == 5){
                        // 通知 Thread 2
                        lock.notify();



//                        try {
//                            // Thread 1先释放锁，等待Thread 2的通知
//                            lock.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }



                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();

    }
}
