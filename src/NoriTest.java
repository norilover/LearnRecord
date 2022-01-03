import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
public class NoriTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Start!");

        BigInteger bg = new BigInteger("0");
        System.out.println(bg.toString());

        bg = bg.add(new BigInteger("100"));


    }

    private static void extracted1() {
        HashMap<Integer, Character> map = new HashMap<>();

        Integer i = 1;

        Integer.toHexString(16);
        Integer.toBinaryString(16);
        Integer.toString(16,1);
//        Integer.toOctalString()


        int[] arr = new int[]{3,4,6,8};

        List<Integer> ans;
        ans = new ArrayList<Integer>(arr.length);
        int val = Arrays.binarySearch(arr, 5);
        int val1 = Arrays.binarySearch(arr, 7);
        int val2 = Arrays.binarySearch(arr, 9);
        System.out.println(val);
        System.out.println(val1);
        System.out.println(val2);
    }

    private static void extracted() {
        HashMap<Character, Integer> mapp = new HashMap<>();
        mapp.put('c', 22);
        mapp.remove('c');
    }

    private static void futureTaskUsage() throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            return 1 + 2;
        });

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);

        Integer result = futureTask.get();
        System.out.println(result);
    }

    private static void futureUsage() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        class Result {
        }
        ;

        class Task implements Runnable {
            private Result result;

            public Task(Result result) {
                this.result = result;
            }

            @Override
            public void run() {
                // Operate result
            }
        }


        // Future usage

        Result result = new Result();

        // <T> Future<T> submit(Runnable task, T result);
        Future<Result> future = executorService.submit(new Task(result), result);

        Result result1 = future.get();
    }

    private static void copyOnWriteList() {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        int i = 100;
        while (i-- > 0)
            copyOnWriteArrayList.add(i);

        Iterator it = copyOnWriteArrayList.iterator();
    }

    private static void cyclicBarrierWait() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("Check task state!");
            System.out.println("Task 1 and 2 is over!");
        });

        Executor executor = Executors.newFixedThreadPool(2);

        Integer times = 100;
        while (times-- > 0) {
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

    /***
     * StampedLock 相比较 ReentrantReadWriteLock 更适合读多写少
     */
    private static void readWriteLockForRead() {
        StampedLock stampedLock = new StampedLock();

        Lock readLock = stampedLock.asReadLock();

        Lock writeLock = stampedLock.asWriteLock();
    }

    /***
     * ReentrantReadWriteLock 适合读多写少
     */
    private static void readWriteLockForWrite() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
    }

    private static void lockBasicMethod() throws InterruptedException {
        Lock lock = new ReentrantLock();

        // 中断
        lock.lockInterruptibly();

        // 直接返回结果，进入过可以获取锁返回true，否则返回false
        lock.tryLock();

        // 与tryLock()类似，只不过在等待时间结束后返回结果
        lock.tryLock(30, TimeUnit.NANOSECONDS);
    }
}
