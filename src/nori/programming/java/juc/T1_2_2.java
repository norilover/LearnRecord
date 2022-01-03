package nori.programming.java.juc;

public class T1_2_2 {
    private int count;
    public static void main(String[] args) {
        T1_2_2 t1_1_1 = new T1_2_2();

        t1_1_1.count = 10;
        t1_1_1.callFunc();
    }

    private synchronized void callFunc() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    private void callFunc1() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
