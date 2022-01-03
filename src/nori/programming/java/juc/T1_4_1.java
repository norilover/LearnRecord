package nori.programming.java.juc;

/***
 * 加锁机制既可以保证原子性也可以保证可见性，而volatile只能保证可见性
 */
public class T1_4_1 implements Runnable {
    // 只保证了可见性, 当run方法被synchronized修饰后，这里可以不加volatile
    private volatile int count;

    @Override
    // 保证操作的原子性
    public synchronized void run() {
        count--;
        System.out.println("Thread name is " + Thread.currentThread().getName() + " count is " + count);
    }

    public static void main(String[] args) {
        T1_4_1 t1_4_1 = new T1_4_1();

        for (int i = 0; i < 100; i++) {
            new Thread(t1_4_1, "Thread " + i).start();
        }
    }
}
