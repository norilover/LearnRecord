package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;

/***
 * synchronized without volatile
 * Less than 1000_0
 */
public class T1_8_4 {

    int count1 = 0;

    synchronized void m1(){
        for (int i = 0; i < 1000; i++) {
            count1++;
        }
    }

    public static void main(String[] args) {
        T1_8_4 t1_8_3 = new T1_8_4();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t1_8_3::m1, "Thread " + i));
        }

        threads.forEach((o)-> o.start());

        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t1_8_3.count1);

    }
}
