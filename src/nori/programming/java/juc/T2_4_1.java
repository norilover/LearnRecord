package nori.programming.java.juc;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/***
 * 线程安全的单例模式：
 *
 * 阅读文章：http://www.cnblogs.com/xudong-bupt/p/3433643.html
 *
 * 更好的是采用下面的方式，既不用加锁，也能实现懒加载
 */
public class T2_4_1 {

    public static void main(String[] args) {
        Thread[] threads = new Thread[200];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                System.out.println(Singleton1.getInstance());
            });
        }

        System.out.println("---------------");

//        Arrays.asList(threads).forEach(o->o.start());
    }
}

class Singleton{
    private Singleton() {
        System.out.println("Single");
    }

    private static class Inner{
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance(){
        return Inner.singleton;
    }
}

class Singleton1{
    private static Singleton1 singleton = new Singleton1();

    private Singleton1(){
        System.out.println("Single1");
    }

    public static Singleton1 getInstance(){
        return singleton;
    }
}
