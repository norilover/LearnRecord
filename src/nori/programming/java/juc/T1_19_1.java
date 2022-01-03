package nori.programming.java.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

/***
 * Exchanger
 */
public class T1_19_1 {

    // permit = 1 表示只允许一个线程执行
    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) throws InstantiationException {
        new Thread(()->{
            String content = "T1";
            String exchange = "";
            try {
                System.out.println("T1 Running!");
                exchange = exchanger.exchange(content);
                System.out.println("Get content： " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " content is: " + content + ", exchange content is: " + exchange);
        }).start();

        new Thread(()->{
            String content = "T2";
            String exchange = "";
            try {
                System.out.println("T2 Running!");
                exchange = exchanger.exchange(content);
                System.out.println("Get content： " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " content is: " + content + ", exchange content is: " + exchange);
        }).start();

    }
}
