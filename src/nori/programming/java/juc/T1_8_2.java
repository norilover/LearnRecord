package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;

/***
 * volatile
 * Data a: 0, b: 99
 */
public class T1_8_2 {

    private static class Data{
        int a;
        int b;


        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    volatile static Data data;
    public static void main(String[] args) {


        Thread w = new Thread(() ->{
            for (int i = 0; i < 100; i++) {
                data = new Data(i, i);
            }
        }, " Thread1 ");


        Thread r = new Thread(() ->{
            while (data == null){}


            int a = data.a;
            int b = data.b;
            System.out.println("Data a: " + a + ", b: " + b);

        }, " Thread1 ");
        r.start();
        w.start();

        try {
            r.join();
            w.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
