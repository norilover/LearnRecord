package nori.programming.java.juc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/***
 * Future
 */
public class T2_10_2 {

    public static void main(String[] args) throws Exception {
//        usingFuture();

//        usingCompletableFuture();

//        usingNewSingleThreadExecutor();

//        usingNewCachedThreadPool();

//        usingNewFixedThreadPool();

//        usingNewScheduledThreadPool();

//        usingNewWorkStealingPool();

        //NORI
//        usingRecursiveActionOrTask();
    }

    private static void usingRecursiveActionOrTask() {
        int[] nums = new int[1000_000];
        final int MAX_NUM = 5000_0;
        Random random = new Random();

        class AddAction extends RecursiveAction{

            private final int start;
            private final int end;

            public AddAction(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            protected void compute() {
                if(end - start <= MAX_NUM){
                    long sum = 0L;
                    for (int i = start; i < end; i++) {
                        System.out.println("From: " + start + " to: " + end + " = " + sum);
                    }
                }else{
                    int middle = start + (end - start) / 2;


                    AddAction subTask1 = new AddAction(start, middle);
                    AddAction subTask2 = new AddAction(middle, end);

                    subTask1.fork();
                    subTask2.fork();
                }
            }
        }
        ForkJoinPool forkJoinPool0 = new ForkJoinPool();
        AddAction addAction = new AddAction(0, nums.length);
        forkJoinPool0.execute(addAction);

        // 只为了显示输出
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * From: 468750 to: 500000 = 0
         * */


        class AddTask extends RecursiveTask<Long>{

            private final int start;
            private final int end;

            public AddTask(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            protected Long compute() {
                if(end - start <= MAX_NUM){
                    long sum = 0L;
                    for (int i = start; i < end; i++) {
                        sum += nums[i];
                    }

                    return sum;
                }else{
                    int middle = start + (end - start) / 2;


                    AddTask subTask1 = new AddTask(start, middle);
                    AddTask subTask2 = new AddTask(middle, end);

                    subTask1.fork();
                    subTask2.fork();

                    return subTask1.join() + subTask2.join();
                }
            }
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AddTask addTask = new AddTask(0, nums.length);
        forkJoinPool.execute(addTask);
        long result = addTask.join();
        System.out.println(result);
        /**
         * 0
         * */

    }

    /***
     * Executors.newWorkStealingPool();
     * execute()后： 产生的任务不会阻塞主线程
     */
    private static void usingNewWorkStealingPool() {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        class MyRunnable implements Runnable{

            private int time;

            public MyRunnable(int time) {
                this.time = time;
            }

            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(this.time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(time + " " + Thread.currentThread().getName());
            }
        }

        service.execute(new MyRunnable(1000));
        service.execute(new MyRunnable(1000));
        service.execute(new MyRunnable(2000));
        service.execute(new MyRunnable(2000));
        service.execute(new MyRunnable(2000));

        try {
            //由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /***
         * 8
         * 1000 ForkJoinPool-1-worker-5
         * 1000 ForkJoinPool-1-worker-3
         * 2000 ForkJoinPool-1-worker-9
         * 2000 ForkJoinPool-1-worker-7
         * 2000 ForkJoinPool-1-worker-11
         */
    }

    private static void usingNewScheduledThreadPool() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

        Runnable command = ()->{
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName());
        };

        service.scheduleAtFixedRate(command,0,500,TimeUnit.MILLISECONDS);

        /***
         * pool-1-thread-4
         * pool-1-thread-4
         * pool-1-thread-4
         * pool-1-thread-4
         * pool-1-thread-4
         * pool-1-thread-4
         * pool-1-thread-4
         * ...
         */
    }


    /***
     * 比较：
     * 1.直接处理
     * 2.将任务分解处理
     */
    private static void usingNewFixedThreadPool() {
        long start = System.currentTimeMillis();

        getPrime(1, 200000);

        long end = System.currentTimeMillis();
        System.out.println("Total Time is: " + (end - start));

        final int cupCoreNum = 4;

        ExecutorService service = Executors.newFixedThreadPool(cupCoreNum);

        class MyTask implements Callable<List<Integer>>{

            private int startPos;
            private int endPos;

            public MyTask(int startPos, int endPos) {
                this.startPos = startPos;
                this.endPos = endPos;
            }

            @Override
            public List<Integer> call() throws Exception {
                return getPrime(this.startPos, this.endPos);
            }
        }


        MyTask task1 = new MyTask(1, 80000);
        MyTask task2 = new MyTask(80001, 130000);
        MyTask task3 = new MyTask(130001, 170000);
        MyTask task4 = new MyTask(170001, 200000);

        Future<List<Integer>> future1 = service.submit(task1);
        Future<List<Integer>> future2 = service.submit(task2);
        Future<List<Integer>> future3 = service.submit(task3);
        Future<List<Integer>> future4 = service.submit(task4);

        start = System.currentTimeMillis();

        try {
            future1.get();
            future2.get();
            future3.get();
            future4.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        end = System.currentTimeMillis();

        System.out.println("Total Time is: " + (end - start));


        /***
         * Total Time is: 1227
         * Total Time is: 472
         */
    }

    private static List<Integer> getPrime(int start, int end) {

        List<Integer> results = new ArrayList<>();

        for (int j = start; j < end; j++) {
            if(isPrime(j))
                results.add(j);
        }

        return results;
    }

    private static boolean isPrime(int num) {
        for (int i = 2; i <= num/2; i++)
            if((num % i) == 0)
                return false;

        return true;
    }


    private static void usingNewCachedThreadPool() {
        ExecutorService service = Executors.newCachedThreadPool();

        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service + " !!!");

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(service + " !!!");

        /***
         * java.util.concurrent.ThreadPoolExecutor@1ddc4ec2[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
         * java.util.concurrent.ThreadPoolExecutor@1ddc4ec2[Running, pool size = 2, active threads = 2, queued tasks = 0, completed tasks = 0] !!!
         * pool-1-thread-2
         * java.util.concurrent.ThreadPoolExecutor@1ddc4ec2[Running, pool size = 2, active threads = 2, queued tasks = 0, completed tasks = 0] !!!
         * pool-1-thread-1
         */
    }

    private static void usingNewSingleThreadExecutor() {
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            final int j = i;

            service.execute(()->{
                System.out.println(j + " " + Thread.currentThread());
            });
        }
    }

    private static void usingCompletableFuture() {
        /***
         * 假设你能够提供一个服务
         * 这个服务查询各大电商网站同一类产品的价格并汇总展示
         */

        long start, end;

        start = System.currentTimeMillis();

        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(()->priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(()->priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(()->priceOfJD());

        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();

        CompletableFuture.supplyAsync(()->priceOfJD())
                .thenApply(String::valueOf)
                .thenApply(str->"finally price " + str)
                .thenAccept(System.out::println);

        end = System.currentTimeMillis();

        System.out.println("Use completable future: " + (end - start));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void delay(){
        int time = new Random().nextInt(500);

        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("After %s sleep!\n", time);
    }

    private static double priceOfTM() {
        delay();

        return 1.00;
    }

    private static double priceOfTB() {
        delay();

        return 2.00;
    }

    private static double priceOfJD() {
        delay();

        return 3.00;
    }


    private static void usingFuture() {
        FutureTask<Integer> task = new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });

        new Thread(task).start();

        try {
            System.out.println("---");
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}