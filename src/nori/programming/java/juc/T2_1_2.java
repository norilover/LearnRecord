package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 面试题：写一个固定容量同步容器，拥有add和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 使用Lock和Condition来实现
 * 对比两种方式，Condition的方式可以更加精确的指定哪些线程被唤醒
 */
public class T2_1_2 {

    public static class NoriContainer1<T>{
        private static final int MAX_SIZE = 10;
        private List<T> containerList = new ArrayList<>();

        private Lock lock = new ReentrantLock();
        private Condition producer = lock.newCondition();
        private Condition consumer = lock.newCondition();

        public synchronized void add(T ele){
            try {
                lock.lock();

                while(this.containerList.size() == MAX_SIZE) {
                    try {
                        producer.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Add element: " + ele);
                this.containerList.add(ele);

                System.out.println("Size: " + this.containerList.size());

                consumer.signalAll();

            }finally {
                lock.unlock();
            }
        }

        public synchronized T get(){

            T remove = null;
            try{
                lock.lock();

                while(this.containerList.size() == 0) {
                    try {
                        consumer.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                remove = this.containerList.remove(this.containerList.size() - 1);
                System.out.println("Get element: " + remove);

                producer.signalAll();
            }finally {
                lock.unlock();
            }

            return remove;
        }
        public int getCount(){

            System.out.println("Size: " + this.containerList.size());

            return this.containerList.size();
        }

    }
    public static void main(String[] args) throws InstantiationException {
        final T2_1_2 t2 = new T2_1_2();

        final NoriContainer1<String> noriContainer = new NoriContainer1<>();
        for (int i = 1; i < 11; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    System.out.println(noriContainer.get());
                    System.out.println(noriContainer.getCount());
                    System.out.println(Thread.currentThread().getName());
                }
            }, "Read Thread" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 1; i < 3; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    noriContainer.add(new String() + ((char)j));
                    System.out.println(Thread.currentThread().getName());
                }
            }, "Add Thread" + i).start();
        }
    }
}
