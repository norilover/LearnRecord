package nori.programming.java.juc;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 * Normal Reference
 * +-
 */
public class T2_2_3 {

    public static void main(String[] args) throws InstantiationException {
        // NormalReference1.doWork();
        // ThreadLocal2.main();
    }

    public static class M{
        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalize");
        }
    }

    public static class NormalReference1{
        public static void doWork(){
            M m = new M();
            m = null;

            System.gc();

            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 软引用
     * 软引用是用来描述一些还有用但并非必须的对象。
     * 对于软引用关联着的对象，在系统将要发生内存溢出异常之前，将会把这些对象列进回收范围进行第二次回收。
     * 如果这次回收还没有足够的内存，才会抛出内存溢出异常。
     * -Xmx20M
     */
    public static class SoftReference1{
        public static void doWork(){
            java.lang.ref.SoftReference<byte[]> softReference = new java.lang.ref.SoftReference(new byte[1024 * 1024 * 10]);

            System.out.println(softReference.get());
            System.gc();

            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(softReference.get());

            byte[] b = new byte[1024 * 1024 * 15];

            System.out.println(softReference.get());
        }
    }

    /***
     *  弱引用遭到gc就会回收
     */
    public static class WeakReference1{
        public static void doWork(){
            java.lang.ref.WeakReference<M> weakReference = new java.lang.ref.WeakReference<>(new M());

            System.out.println(weakReference.get());
            System.gc();
            System.out.println(weakReference.get());

            ThreadLocal<M> threadLocal = new ThreadLocal<>();
            threadLocal.set(new M());

            threadLocal.remove();
        }
    }


    /**
     *     一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，
     *     也无法通过虚引用来获取一个对象的实例。
     *     为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知。
     *     虚引用和弱引用对关联对象的回收都不会产生影响，如果只有虚引用活着弱引用关联着对象，
     *     那么这个对象就会被回收。它们的不同之处在于弱引用的get方法，虚引用的get方法始终返回null,
     *     弱引用可以使用ReferenceQueue,虚引用必须配合ReferenceQueue使用。
     *
     *     jdk中直接内存的回收就用到虚引用，由于jvm自动内存管理的范围是堆内存，
     *     而直接内存是在堆内存之外（其实是内存映射文件，自行去理解虚拟内存空间的相关概念），
     *     所以直接内存的分配和回收都是有Unsafe类去操作，java在申请一块直接内存之后，
     *     会在堆内存分配一个对象保存这个堆外内存的引用，
     *     这个对象被垃圾收集器管理，一旦这个对象被回收，
     *     相应的用户线程会收到通知并对直接内存进行清理工作。
     *
     *     事实上，虚引用有一个很重要的用途就是用来做堆外内存的释放，
     *     DirectByteBuffer就是通过虚引用来实现堆外内存的释放的。
     */

    public static class PhantomReference1{
        private static final List<Object> LIST = new LinkedList<>();
        private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

        public static void doWork(){
            PhantomReference<M> phantomReference = new PhantomReference<>(new M(), QUEUE);

            new Thread(()->{
                while (true){
                    LIST.add(new byte[1024 * 1024]);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(phantomReference.get());
                }
            }).start();

            new Thread(()->{
                while (true){
                    Reference<? extends M> poll = QUEUE.poll();

                    if(poll != null){
                        System.out.println("--- 虚引用对象被JVM回收 ---- " + poll);
                    }
                }
            }).start();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
