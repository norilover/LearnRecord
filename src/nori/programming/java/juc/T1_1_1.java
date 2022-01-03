package nori.programming.java.juc;

public class T1_1_1 {
    private int count;
    private Object o = new Object();
    public static void main(String[] args) {
        T1_1_1 t1_1_1 = new T1_1_1();

        t1_1_1.count = 10;
        t1_1_1.callFunc();
    }

    private void callFunc() {
        synchronized (o){
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
