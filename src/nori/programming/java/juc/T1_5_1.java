package nori.programming.java.juc;

public class T1_5_1 {

    public static void main(String[] args) {
        T1_5_1 t1_5_1 = new T1_5_1();

        new Thread(t1_5_1::func, "Thread 1").start();
        new Thread(t1_5_1::func1, "Thread 2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                t1_5_1.func1();
            }
        }).start();
    }

    private synchronized  void func() {
        try {
            Thread.sleep(1000_0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " func");
    }

    private void func1() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " func1");
    }


}
