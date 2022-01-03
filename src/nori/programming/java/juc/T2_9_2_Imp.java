package nori.programming.java.juc;

import jdk.nashorn.internal.ir.Block;

import java.net.Socket;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.*;

/***
 * ConcurrentQueue, LinkedBlockingQueue, ArrayBlockingQueue
 * PriorityQueue, DelayQueue,
 *
 * // 生产者或消费者任意一方不在，一方就回阻塞
 * SynchronizeQueue,
 *
 * LinkedTransferQueue
 *      LinkedTransferQueue是 SynchronousQueue 和 LinkedBlockingQueue 的合体，性能比 LinkedBlockingQueue 更高（没有锁操作），比 SynchronousQueue能存储更多的元素。
 *      当 put 时，如果有等待的线程，就直接将元素 “交给” 等待者， 否则直接进入队列。
 *      put和 transfer 方法的区别是，put 是立即返回的， transfer 是阻塞等待消费者拿到数据才返回。transfer方法和 SynchronousQueue的 put 方法类似。
 */
public class T2_9_2_Imp {

    public static void main(String[] args) {
//        usingConcurrentLinkedQueue();
//        usingLinkedBlockingQueue();
//        usingArrayBlockingQueue();
//        usingPriorityQueue();
//        usingDelayQueue();
//        usingSynchronousQueue();
        usingTransferQueue();

    }

    private static void usingTransferQueue() {
        LinkedTransferQueue<String> strings = new LinkedTransferQueue<>();

        try {
            // 传递一个目标给消费者， 如果没有等待的消费者，直接抛弃目标
            strings.put("aaa1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(()->{
            try {
                System.out.println(strings.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            // 不会阻塞，直接返回
            strings.put("aaa");

            // 类似SynchronousQueue的put(),等待阻塞
            strings.transfer("aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void usingSynchronousQueue() {
        BlockingQueue<String> strings = new SynchronousQueue<>();

//        try {
//            strings.put("aaa");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new Thread(()->{
            try {
                System.out.println(strings.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            // 没有消费者则一直等待
            strings.put("aaa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(strings.size());
    }

    private static void usingDelayQueue() {
        class MyTask implements Delayed{

            private final String name;
            private final long runTime;

            public MyTask(String name, long runTime) {
                this.name = name;
                this.runTime = runTime;
            }

            @Override
            public long getDelay(TimeUnit unit) {
                return unit.convert(runTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }

            @Override
            public int compareTo(Delayed o) {
                if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
                    return -1;
                else if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS))
                    return 1;
                else
                    return 0;
            }

            @Override
            public String toString() {
                return name + " " + runTime;
            }
        }

        long now = System.currentTimeMillis();
        MyTask task0 = new MyTask("t0", now + 1000);
        MyTask task1 = new MyTask("t1", now + 2000);
        MyTask task2 = new MyTask("t2", now + 1500);
        MyTask task3 = new MyTask("t3", now + 2500);
        MyTask task4 = new MyTask("t4", now + 500);

        BlockingQueue<MyTask> tasks = new DelayQueue<>();

        try {
            tasks.put(task0);
            tasks.put(task1);
            tasks.put(task2);
            tasks.put(task3);
            tasks.put(task4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(tasks);

        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(tasks.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void usingPriorityQueue() {
        PriorityQueue<String> queue = new PriorityQueue<>();

        char c = 'z';
        final String EMPTY = "";

        // 插入时自动排序
        queue.add((c--) + EMPTY);
        queue.add((c--) + EMPTY);
        queue.add((c--) + EMPTY);
        queue.add((c--) + EMPTY);
        queue.add((c--) + EMPTY);
        queue.add((c--) + EMPTY);
        queue.add((c--) + EMPTY);
        queue.add((c--) + EMPTY);

        // 正序输出
        while (queue.size() > 0) {
            System.out.println(queue.poll());
        }
    }

    private static void usingArrayBlockingQueue() {
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(10);
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            try {
                strings.put("a" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            // if strings 满了就会等待，程序阻塞，只等待规定的时间
            strings.offer("aaa1", 1, TimeUnit.SECONDS);
            strings.offer("aaa", 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("-------");
        }

        System.out.println(strings);
    }

    private static void usingLinkedBlockingQueue() {
        BlockingQueue<String> strings = new LinkedBlockingQueue<>();

        Random random = new Random();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                try {
                    // if 满了就会等待
                    strings.put("a" + i);
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "p1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                for (;;){
                    try {
                        // if strings 中数量为0，就会在这里等待
                        System.out.println(Thread.currentThread().getName() + " take - " + strings.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "c"+ i).start();
        }

        /***
         * c0 take - a0
         * c1 take - a1
         * c3 take - a2
         * c2 take - a3
         * c4 take - a4
         * c0 take - a5
         * c1 take - a6
         * c3 take - a7
         * c2 take - a8
         * c4 take - a9
         */
    }

    private static void usingConcurrentLinkedQueue() {
        Queue<String> strings = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            strings.offer("a" + i);
        }

        System.out.println(strings);
        System.out.println(strings.size());
        System.out.println(strings.poll());
        System.out.println(strings.size());
        System.out.println(strings.peek());
        System.out.println(strings.size());

        /***
         * [a0, a1, a2, a3, a4, a5, a6, a7, a8, a9]
         * 10
         * a0
         * 9
         * a1
         * 9
         */
    }



}