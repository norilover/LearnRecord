package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;

/***
 * volatile
 * Less than 1000_0
 */
public class T1_8_3 {

    volatile int count = 0;

    void m(){
        for (int i = 0; i < 1000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        T1_8_3 t1_8_3 = new T1_8_3();

        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new Thread(t1_8_3::m, "Thread " + i));
        }

        list.forEach((o)-> o.start());

        list.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t1_8_3.count);

    }
}
