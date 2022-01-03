package nori.programming.java.juc;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/***
 * Collection Array Queue
 */
public class T2_5_1 {

    public static void main(String[] args) {
//        blockingQueue();

//        array();

        collectionSetQueue();
    }

    private static void collectionSetQueue() {
        Collection<Integer> collection = new ArrayList<>();

        int i = 0;
        collection.add(++i);
        collection.add(++i);
        collection.add(++i);
        collection.stream().forEach(System.out::println);

        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
    }

    private static void array() {
        int[] a = {1,7,3,4,5,6,19,78,990,16};
        Arrays.stream(a).map(i->i + 1).forEach(i -> System.out.print(i + " "));
    }

    private static void blockingQueue() {
        Queue<Integer> queue = new ArrayBlockingQueue<>(2);

        int i = 0;
        queue.add(i++);
        queue.add(i++);
        queue.add(i++);
        queue.add(i++);
        queue.add(i++);

        System.out.println(queue);

        /***
         * Exception in thread "main" java.lang.IllegalStateException: Queue full
         */}
}