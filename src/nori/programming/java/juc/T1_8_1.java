package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;

/***
 * volatile
 * Go on cycle!1245425
 * Go on cycle!1238015
 */
public class T1_8_1 {

    volatile boolean is = true;
    void m(){
        System.out.println("m start!");

        int i = 0;
        while (is){
            System.out.println("Go on cycle!" + i++);
        }

        System.out.println("m end!");
    }

    public static void main(String[] args) {
        T1_8_1 t1_7_1 = new T1_8_1();


        Runnable runnable = () ->{
            t1_7_1.m();
        };


        new Thread(runnable, " Thread1 ").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1_7_1.is = false;
    }
}
