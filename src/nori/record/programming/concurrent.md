# 并发编程

> 管程
```text
    存在：
        Hasen 模型、Hoare 模型和 MESA 模型(Java)

```


> Java Happens-Before 规则 :
> 
> 前面一个操作的结果对后续操作是可见的
```text
    1.程序的顺序规则
    2.volatile变量规则
    3.传递性
    4.管程中锁的规则
        管程：
            同步原语
    5.线程Start()规则：
        EG：
            在A线程中调用B线程的start()，则B线程可以看到自己被start前的(所有共享变量)操作
    6.线程Join()规则
        EG:
           在线程A中调用线程B的join()，即线程A等待线程B执行完毕，在线程B执行完后，线程A可以看到线程B的操作 
    7.线程中断规则：
        对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过Thread.interrupted()方法检测到是否有中断发生
    8.对象终结规则：
        一个对象的初始化完成(构造函数执行结束)先行发生于它的finalize()开始
    
```

* Java sleep() & wait()
```java
/***
 * wait():
 *  
 */
class Test{
    public static void main(String[]args){
        Object o = new Object();
        try {
            o.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            o.wait(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

>  Lock & Condition
* Lock 解决了synchronized的三个问题
```text
    1. 能够响应中断。
        synchronized 的问题是，持有锁 A 后，如果尝试获取锁 B 失败，那么线程就进入阻塞状态，一旦发生死锁，就没有任何机会来唤醒阻塞的线程。但如果阻塞状态的线程能够响应中断信号，也就是说当我们给阻塞的线程发送中断信号的时候，能够唤醒它，那它就有机会释放曾经持有的锁 A。这样就破坏了不可抢占条件了。

    2. 非阻塞地获取锁。
        如果尝试获取锁失败，并不进入阻塞状态，而是直接返回，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。    
    3. 支持超时。
        如果线程在一段时间之内没有获取到锁，不是进入阻塞状态，而是返回一个错误，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。
```

```java
// 对应的代码实现
    Lock lock = new ReentrantLock();

    // 1.中断
    lock.lockInterruptibly();

    // 2.直接返回结果，进入过可以获取锁返回true，否则返回false
    lock.tryLock();

    // 3.与tryLock()类似，只不过在等待时间结束后返回结果
    lock.tryLock(30, TimeUnit.NANOSECONDS);
```

* Wait 机制
```java
class WaitTest{
    /***
     * CyclicBarrier 实现同步机制，当步骤任务完成（调用await()使parties == 0） 调用指定的总任务
     * 可以循环执行，因为当parties == 0 时会被自动恢复到原来设定的值  
     */
    private static void cyclicBarrierWait() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("Check task state!");
            System.out.println("Task 1 and 2 is over!");
        });

        Executor executor = Executors.newFixedThreadPool(2);

        Integer times = 100;
        while(times-- > 0){
            executor.execute(() -> {
                System.out.println("Task 1!");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            executor.execute(() -> {
                System.out.println("Task 2!");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /***
     *  CountDownLatch : 当多个任务调用countDown()后，一直到 count == 0, 开始调用await()后面的程序(如果有程序提前调用await()程序将会挂起)
     *  不同于CyclicBarrier的自动重置功能，CountDownLatch当count == 0 时将不会重置 count
     */
    private static void countDownLatchWait() {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("Check task state!");
            try {
                countDownLatch.await();
                System.out.println("Task 1 and 2 is over!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            System.out.println("Task 1!");
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("Task 2!");
            countDownLatch.countDown();
        }).start();
    }
}
```

* 线程池

```java
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class ThreadPoolTest {
    public static void main(String[] args) {

        /**
         public ThreadPoolExecutor(int corePoolSize,
             int maximumPoolSize,
             long keepAliveTime,
             TimeUnit unit,
             BlockingQueue<Runnable> workQueue,
             ThreadFactory threadFactory,
             RejectedExecutionHandler handler)
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();

        // Executors 提供的很多方法默认使用的都是无界的 LinkedBlockingQueue，
        // 高负载情境下，无界队列很容易导致 OOM，而 OOM 会导致所有请求都无法处理，这是致命问题
        Executors.newFixedThreadPool(10);
    }
}
```

