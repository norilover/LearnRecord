package nori.record.programming;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ConcurrentProgramming {
    public static void main(String[] args) {
        FutureTask<String> futureTask1 = new FutureTask<>(new Task1());
        FutureTask<String> futureTask2 = new FutureTask<>(new Task2(futureTask1));

        // 实现 Task1 执行完 再执行 Task2
        new Thread(futureTask2).start();
        new Thread(futureTask1).start();

        // 简写形式
        new Thread(new FutureTask<String>(() ->  "")).start();
        new Thread(new FutureTask<String>(() -> { System.out.println("---------");return "";})).start();
    }

    static class Task1 implements Callable<String> {
        @Override
        public String call() throws Exception {
            String task1 = "Task1";
            System.out.println("Over " + task1);
            return task1;
        }
    }
    static class Task2 implements Callable<String> {
        private final FutureTask<String> futureTask1;

        public Task2(FutureTask<String> futureTask1) {
            this.futureTask1 = futureTask1;
        }

        @Override
        public String call() throws Exception {
            System.out.println("Wait get task1!");
            String task1 = this.futureTask1.get();
            System.out.println("Over " + task1);
            System.out.println("Get task1!");

            String task2 = "Task2";
            System.out.println("Over " + task2);
            return task2;
        }
    }
}
