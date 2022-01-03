package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/***
 * synchronized & AtomicInteger & LongAddr 数字运算运行比较
 * Output:
 *      Number is : 100000,synchronized Lock Spend Time is: 24
 *      Number is : 100000,AtomicInteger Spend Time is: 6
 *      Number is : 100000,LongAddr Spend Time is: 8
 * Conclusion:
 *      LongAddr > AtomicInteger > synchronized
 */
public class T1_11_1 {
    static Object lock = new Object();
    static long count1;
    static AtomicInteger count2 = new AtomicInteger(0);
    static LongAdder count3 = new LongAdder();

    static int times = 10000;
    static void m1(){
        for (int i = 0; i < times; i++) {
            synchronized (lock){
                count1++;
            }
        }
    }

    static void m2(){
        for (int i = 0; i < times; i++) {
            count2.incrementAndGet();
        }
    }

    static void m3(){
        for (int i = 0; i < times; i++) {
            count3.add(1);
        }
    }

    public static void main(String[] args) {
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new Thread(T1_11_1::m1, "Thread " + i));
        }

        long startTime = System.currentTimeMillis();
        list.forEach((o)->{o.start();});

        list.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long endTime = System.currentTimeMillis();

        System.out.println("Number is : " + count1 + ",synchronized Lock Spend Time is: " + (endTime - startTime));



        List<Thread> list2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list2.add(new Thread(T1_11_1::m2, "Thread " + i));
        }

        startTime = System.currentTimeMillis();
        list2.forEach((o)->{o.start();});

        list2.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        endTime = System.currentTimeMillis();

        System.out.println("Number is : " + count2.get() + ",AtomicInteger Spend Time is: " + (endTime - startTime));



        List<Thread> list3 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list3.add(new Thread(T1_11_1::m3, "Thread " + i));
        }

        startTime = System.currentTimeMillis();
        list3.forEach((o)->{o.start();});

        list3.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        endTime = System.currentTimeMillis();

        System.out.println("Number is : " + count3.longValue() + ",LongAddr Spend Time is: " + (endTime - startTime));
    }
}
