package nori.programming.java.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/***
 * CyclicBarrier
 */
public class T1_14_1 {

    public static void main(String[] args) throws InstantiationException {
        basicUsageCyclicBarrier();
    }

    private static void basicUsageCyclicBarrier() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, ()-> {
            System.out.println("已达上限！");
        });

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    // 当执行10次后，即count = 0 时，初始化时放在其中的任务将会执行
                    // 并且count恢复初始化后的值
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                // System.out.println(Thread.currentThread().getName());
            }, "Thead " + i).start();
        }

        System.out.println("End CyclicBarrier!");
    }

}
