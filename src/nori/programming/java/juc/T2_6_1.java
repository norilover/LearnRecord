package nori.programming.java.juc;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/***
 *Hashtable, HashMap, Collections.synchronizeMap(new Map()), ConcurrentHashMap
 */
public class T2_6_1 {

    public static final int COUNT = 10000;
    public static final int THREAD_COUNT = 100;


    public static void main(String[] args) {

        map_UUID_thread(new Hashtable<UUID, UUID>());
        map_UUID_thread(new HashMap<UUID, UUID>());

        map_UUID_thread(Collections.synchronizedMap(new Hashtable<UUID, UUID>()));
        map_UUID_thread(new ConcurrentHashMap<UUID, UUID>());
    }

    private static void map_UUID_thread(Map _map) {
        UUID[] keys = new UUID[COUNT];
        UUID[] values = new UUID[COUNT];
        Map<UUID, UUID> map = _map;


        for (int i = 0; i < COUNT; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }

        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] =new Thread(()->{
                for (int j = 0; j < COUNT; j++) {
                    map.put(keys[j], values[j]);
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);


        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] =new Thread(()->{
                for (int j = 0; j < COUNT; j++) {
                    map.get(keys[j]);
                }
            });
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}