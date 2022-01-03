package nori.programming.java.juc;

import java.util.SortedMap;

public class T1_0_4 {
    private static class T1 extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread :" + i);
            }
        }
    }
    public static void main(String[] args) {
        T1 t1 = new T1();

        t1.start();

        System.out.println(t1.getState());

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t1.getState());
    }


}