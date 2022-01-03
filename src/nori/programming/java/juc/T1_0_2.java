package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;

public class T1_0_2 {
    private static class T1 extends Thread{
        @Override
        public void run() {
            System.out.println("T1 Thread!");
        }
    }

    private static class T2 implements Runnable{
        @Override
        public void run() {
            System.out.println("T2 Thread!");
        }
    }


    public static void main(String[] args) {
        new T1().start();

       //  public Thread(Runnable target) {
        new Thread(new T2()).start();
    }
}