package nori.programming.java.juc;

public class T1_3_1 {
    private static int count;
    public static void main(String[] args) {
        T1_3_1 t1_1_1 = new T1_3_1();

        t1_1_1.count = 10;
        t1_1_1.callFunc();

        // Like above call
        callFunc1();
    }

    private void callFunc() {
        synchronized (T1_3_1.class){
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    private synchronized static void callFunc1() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
