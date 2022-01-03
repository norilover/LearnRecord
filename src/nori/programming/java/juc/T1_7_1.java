package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;

/***
 * 可重入锁
 */
public class T1_7_1 {

    int count = 0;
    synchronized void m(){
        System.out.println("Thread name: " + Thread.currentThread().getName() + " start!");

        while (true){
            count++;
            System.out.println("Thread name: " + Thread.currentThread().getName() + " count: " + count);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(count == 5){
                int i = count / 0;
                System.out.println(i);
            }

        }
    }

    public static void main(String[] args) {
        T1_7_1 t1_7_1 = new T1_7_1();


        Runnable runnable = () ->{
            t1_7_1.m();
        };


        new Thread(runnable, " Thread1 ").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(runnable, " Thread2 ").start();

    }
}
