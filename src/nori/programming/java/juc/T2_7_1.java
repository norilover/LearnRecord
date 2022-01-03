package nori.programming.java.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/***
 *  同步容器类:
 *     1：Vector Hashtable ：早期使用synchronized实现
 *     2：ArrayList HashSet ：未考虑多线程安全（未实现同步）
 *     3：HashSet vs Hashtable StringBuilder vs StringBuffer
 *     4：Collections.synchronized***工厂方法使用的也是synchronized
 *
 *  Q:
 *     有N张火车票，每张票都有一个编号
 *     同时有10个窗口对外售票
 *     请写一个模拟程序
 *
 *     分析下面的程序可能会产生哪些问题？
 *     重复销售？超量销售？
 *
 *     使用Vector或者Collections.synchronizedXXX
 *     分析一下，这样能解决问题吗？
 *
 *     就算操作A和B都是同步的，但A和B组成的复合操作也未必是同步的，仍然需要自己进行同步
 *     就像这个程序，判断size和进行remove必须是一整个的原子操作
 *
 *     使用ConcurrentQueue提高并发性
 */
public class T2_7_1 {
    private static int NUMBER = 1000;
    private static int THREAD_NUMBER = 10;
    public static void main(String[] args) {

//        useList(new ArrayList<>());

        // size() 和 remove() 两个操作执行未必是同步的
//        useList(new Vector<>());

//        useSynchrinized();

        useConcurrentLinkedQueue();
    }

    private static void useConcurrentLinkedQueue() {
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();

        for (int i = 1; i <= NUMBER; i++) {
            queue.add(i);
        }

        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                while(true){
                    Integer index = queue.poll();
                        if(index == null)
                            break;

                        System.out.println("Sell ticket " + index + Thread.currentThread().getName());
                    }
            }, " Thread " + i).start();
        }
    }

    private static void useSynchrinized() {
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= NUMBER; i++) {
            list.add(i);
        }

        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                while(true){
                    synchronized (list){
                        if(list.size() <= 0)
                            break;

                        System.out.println("Sell ticket " + list.remove(0) + Thread.currentThread().getName());
                    }
                }
            }, " Thread " + i).start();
        }
    }

    public static void useList(List list) {
        for (int i = 1; i <= NUMBER; i++) {
            list.add(i);
        }

        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                while(true){
                    if(list.size() <= 0)
                        break;

                    System.out.println("Sell ticket " + list.remove(0) + Thread.currentThread().getName());
                }
            }, " Thread " + i).start();
        }
    }


}