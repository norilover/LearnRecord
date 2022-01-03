package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/***
 * 面试题：写一个固定容量同步容器，拥有add和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 使用wait和notify/notifyAll来实现
 */
public class T2_1_1_INTERVIEW {

    public static class NoriContainer<T>{
        private static final int MAX_SIZE = 10;
        private List<T> containerList = new ArrayList<>();

        public synchronized void add(T ele){
            while(this.containerList.size() == MAX_SIZE) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Add element: " + ele);
            this.containerList.add(ele);

            System.out.println("Size: " + this.containerList.size());

            this.notifyAll();
        }

        public synchronized T get(){
            while(this.containerList.size() == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T remove = this.containerList.remove(this.containerList.size() - 1);
            System.out.println("Get element: " + remove);
            this.notifyAll();
            return remove;
        }
        public int getCount(){

            System.out.println("Size: " + this.containerList.size());

            return this.containerList.size();
        }

    }
    public static void main(String[] args) throws InstantiationException {
        final T2_1_1_INTERVIEW t2 = new T2_1_1_INTERVIEW();

        final NoriContainer<String> noriContainer = new NoriContainer<>();
        for (int i = 1; i < 11; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    System.out.println(noriContainer.get());
                    System.out.println(noriContainer.getCount());
                    System.out.println(Thread.currentThread().getName());
                }
            }, "Read Thread" + i).start();
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


//    public static class NoriContainer<T>{
//        private List<T> containerList = new ArrayList<>();
//        private final Object lock = new Object();
//        private volatile boolean isHold = false;
//        public void add(T ele){
//            if(isHold) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            isHold = true;
//
//            this.containerList.add(ele);
//
//            isHold = false;
//
//            System.out.println("Size: " + this.containerList.size());
//
//            lock.notify();
//        }
//
//        public int getCount(){
//            if(isHold) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            isHold = true;
//
//            try{}
//            finally {
//                isHold = false;
//                System.out.println("Size: " + this.containerList.size());
//
//                lock.notify();
//            }
//            return this.containerList.size();
//
//        }
//    }
