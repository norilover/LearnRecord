package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;

/***
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */
public class T2_0_1_INTERVIEW {
    volatile List list = new ArrayList();

    void add(Object object){list.add(object);}
    int size(){return list.size();}

    public static void main(String[] args) throws InstantiationException {
        final T2_0_1_INTERVIEW t2 = new T2_0_1_INTERVIEW();

        Thread thread1 = new Thread(()-> {
            System.out.println("Thread 1");

            for (int i = 0; i < 10; i++) {
                t2.add(new Object());
                System.out.println("Add " + i + " to list!");
            }
        });
        thread1.start();

        // 有时候Thread 2 不会终止，当Thread 1直接运行结束时
        Thread thread2 = new Thread(()-> {
            System.out.println("Thread 2");

            while(true){
                if(t2.size() == 5){
                    System.out.println("Thread 2 find the length of list is 5");
                    break;
                }
            }
        });
        thread2.start();
    }
}
