package nori.programming.java.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 * synchronized 优化
 * Less than 1000_0
 */
public class T1_9_1 {
    private int count;

    synchronized void m(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Core Code
        this.count++;

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   void m1(){
       try {
           TimeUnit.SECONDS.sleep(2);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }

       synchronized (this){
           // Core Code
           this.count++;
       }

       try {
           TimeUnit.SECONDS.sleep(2);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }

    public static void main(String[] args) {
        T1_9_1 t1_8_3 = new T1_9_1();
    }
}
