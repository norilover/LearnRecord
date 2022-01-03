package nori.programming.java.juc;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 * ThreadLocal
 */
public class T2_3_1 {
    public static class ThreadLocal1{
        volatile static Person p = new Person();

        public static void main() {
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(p.name);
            }).start();

            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                p.name = "Tom";
            }).start();
        }

        public static class Person{
            String name = "Nori";
        }
    }

    /***
     * ThreadLocal线程局部变量
     *
     * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
     * 比如在hibernate中session就存在与ThreadLocal中，避免synchronized的使用
     *
     */
    public static class ThreadLocal2{
        static ThreadLocal<ThreadLocal2.Person> tl = new ThreadLocal<>();

        public static void main(){
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(tl.get());
            }).start();

            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tl.set(new ThreadLocal2.Person());
            }).start();

        }

        public static class Person{
            String name = "Nori";
        }
    }
}
