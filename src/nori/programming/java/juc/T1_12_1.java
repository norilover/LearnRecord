package nori.programming.java.juc;

import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;

/***
 * reentrantlock用于替代synchronized
 */
public class T1_12_1 {
    synchronized void m1(){
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("m1" + i);

            if(i == 2)
                m2();
        }
    }

    synchronized void m2() {
        System.out.println("m2");
    }

    public static void main(String[] args) throws InstantiationException {
        T1_12_1 t1_12_1 = new T1_12_1();

        new Thread(t1_12_1::m1, "Thread1").start();
//        new Thread(t1_12_1::m2, "Thread2").start();
    }
}
