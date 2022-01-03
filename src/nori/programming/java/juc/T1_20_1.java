package nori.programming.java.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/***
 * LockSupport.park() .unPark()
 */
public class T1_20_1 {

    public static void main(String[] args) throws InstantiationException {
        Thread t = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);

                if(i == 5){
                    LockSupport.park();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        LockSupport.unpark(t);
    }
}
