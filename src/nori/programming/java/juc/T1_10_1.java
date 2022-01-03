package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 不能以字符串常量，或基本数据类型的包装类为锁对象
 */
public class T1_10_1 {
    private AtomicInteger integer = new AtomicInteger(0);

    void m1(){
        for (int i = 0; i < 100; i++) {
            integer.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        T1_10_1 t1_8_3 = new T1_10_1();
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new Thread(t1_8_3::m1, "Thread " + i));
        }

        list.forEach((o)->{o.start();});

        list.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        System.out.println(t1_8_3.integer);

    }
}
