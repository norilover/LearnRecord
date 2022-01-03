package nori.programming.java.juc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/***
 * Executor
 * ExecutorService submit
 * Callable = Runnable
 * Executors
 * ThreadPool
 *
 * fixed cached single scheduled workstealing forkjoin
 *
 * ThreadpoolExecutor
 *
 * PStreamAPI
 */
public class T2_10_1 {

    public static void main(String[] args) throws Exception {
//        usingExecutor();

        /***
         * 1）如果当前运行的线程数少于corePoolSize，则创建新线程来执行任务。
         * 2）在线程池完成预热之后（当前运行的线程数等于corePoolSize），将任务加入
         * LinkedBlockingQueue。
         * 3）线程执行完1中的任务后，会在循环中反复从LinkedBlockingQueue获取任务来执行。
         * FixedThreadPool使用无界队列LinkedBlockingQueue作为线程池的工作队列（队列的容量为
         * Integer.MAX_VALUE）。使用无界队列作为工作队列会对线程池带来如下影响。
         * 1）当线程池中的线程数达到corePoolSize后，新任务将在无界队列中等待，因此线程池中
         * 的线程数不会超过corePoolSize。
         * 2）由于1，使用无界队列时maximumPoolSize将是一个无效参数。
         * 3）由于1和2，使用无界队列时keepAliveTime将是一个无效参数。
         * 4）由于使用无界队列，运行中的FixedThreadPool（未执行方法shutdown()或
         * shutdownNow()）不会拒绝任务（不会调用RejectedExecutionHandler.rejectedExecution方法）。
         */
//        usingnewFixedThreadPool();
//        usingExecutorService_newFixedThreadPool();


        /***
         * CachedThreadPool使用没有容量的SynchronousQueue作为线程池的工作队列，但
         * CachedThreadPool的maximumPool是无界的。这意味着，如果主线程提交任务的速度高于
         * maximumPool中线程处理任务的速度时，CachedThreadPool会不断创建新线程。极端情况下，
         * CachedThreadPool会因为创建过多线程而耗尽CPU和内存资源。
         *
         * SynchronousQueue是一个没有容量的阻塞队列。每个插入操作必须等待另一
         *      个线程的对应移除操作，反之亦然。CachedThreadPool使用SynchronousQueue，把主线程提交的
         *      任务传递给空闲线程执行。CachedThreadPool中任务传递的示意图如图10-7所示。
         */
//        usingCallable_newCachedThreadPool();


//        usingThreadPoolExecutor();

    }

    private static void usingnewFixedThreadPool() {

        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 6; i++){
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(service);
            });
        }

        System.out.println(service);

        service.shutdown();
        // Returns true if all tasks have completed following shut down.
        System.out.println(service.isTerminated());

        // Returns true if this executor has been shut down.
        System.out.println(service.isShutdown());
        System.out.println(service);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }

    private static void usingThreadPoolExecutor() {

        class Task1 implements Runnable{

            private final int i;

            public Task1(int i) {
                this.i = i;
            }

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " Task " + i);

                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String toString() {
                return "Task1{" +
                        "i=" + i +
                        '}';
            }
        }

        /***
         * public ThreadPoolExecutor(int corePoolSize,
         *                           int maximumPoolSize,
         *                           long keepAliveTime,
         *                           TimeUnit unit,
         *                           BlockingQueue<Runnable> workQueue,
         *                           ThreadFactory threadFactory,
         *                           RejectedExecutionHandler handler)
         *
         * corePoolSize – the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set
         * maximumPoolSize – the maximum number of threads to allow in the pool
         * keepAliveTime – when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
         * unit – the time unit for the keepAliveTime argument
         * workQueue – the queue to use for holding tasks before they are executed. This queue will hold only the Runnable tasks submitted by the execute method.
         * threadFactory – the factory to use when the executor creates a new thread
         * handler – the handler to use when execution is blocked because the thread bounds and queue capacities are reached
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                4,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 8; i++) {
            executor.execute(new Task1(i));
        }

        System.out.println(executor.getQueue());

        executor.execute(new Task1(100));

        System.out.println(executor.getQueue());

        executor.shutdown();

        /***
         *     [Task1{i=2}, Task1{i=3}, Task1{i=4}, Task1{i=5}]
         *     pool-1-thread-2 Task 1
         *     pool-1-thread-4 Task 7
         *     pool-1-thread-1 Task 0
         *     main Task 100
         *     pool-1-thread-3 Task 6
         *
         *     output 1
         *
         *     [Task1{i=2}, Task1{i=3}, Task1{i=4}, Task1{i=5}]
         *     pool-1-thread-2 Task 2
         *     pool-1-thread-4 Task 3
         *     pool-1-thread-2 Task 4
         *     pool-1-thread-4 Task 5
         */
    }


    /***
     * 认识Callable，对Runnable进行了扩展
     * 对Callable的调用，可以有返回值
     */
    private static void usingCallable_newCachedThreadPool() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();

        // 异步
        Future<String> future = service.submit(callable);

        // 阻塞
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdown();

        /***
         * Hello Callable
         */
    }

    private static void usingExecutorService_newFixedThreadPool() throws IOException {
        class NetWorkService implements Runnable{

            private final Executor pool;
            private final ServerSocket serverSocket;

            public NetWorkService(int port, int poolSize) throws IOException {
                this.serverSocket = new ServerSocket(port);
                this.pool = Executors.newFixedThreadPool(poolSize);
            }

            @Override
            public void run() {
                for (;;){
                    try {
                        pool.execute(new Handler(serverSocket.accept()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            class Handler implements Runnable{

                private Socket socket;

                public Handler(Socket socket) {
                    this.socket = socket;
                }

                @Override
                public void run() {
                    System.out.println("Handle Service!" + Thread.currentThread().getName());
                }
            }
        }

        int port = 8181;

        // Server terminal
        new Thread(()->{
            NetWorkService netWorkService = null;
            try {
                netWorkService = new NetWorkService(port,4);
            } catch (IOException e) {
                e.printStackTrace();
            }
            netWorkService.run();
        }).start();


        // Client terminal
        new Thread(()->{
            String ipAddress = "127.0.0.1";
            for (int i = 0; i < 100; i++) {
                try {
                    Socket socket = new Socket(ipAddress, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /**
         * Handle Service!pool-2-thread-2
         * Handle Service!pool-2-thread-1
         * Handle Service!pool-2-thread-2
         * Handle Service!pool-2-thread-2
         * Handle Service!pool-2-thread-2
         * Handle Service!pool-2-thread-3
         * ...
         */

        ExecutorService executorService = Executors.newCachedThreadPool();
    }

    private static void usingExecutor() {
        class MyExecutor implements Executor{

            @Override
            public void execute(Runnable command) {
                command.run();
            }
        }

        new MyExecutor().execute(()->{
            System.out.println("Executor First!");
        });
    }


}