package nori.programming.java.juc;

public class T1_0_3 {

    public static void main(String[] args) {
//        sleep();

//        yield();

//        join();
    }

    private static void join() {
        final Thread a = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Join Thread A: " + i);
            }
        });
        a.start();

        final Thread b = new Thread(() -> {
            try {
                System.out.println("Start Thread B");

                System.out.println("Wait Thread A Finish!");
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 100; i++) {
                System.out.println("Join Thread B: " + i);
            }
        });
        b.start();
    }

    private static void yield() {
        new Thread(()->{
            for (int i = 0; i < 200; i++) {
                System.out.println("Yield Thread A: " + i);
                if(i % 10 == 0)
                    Thread.yield();
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 200; i++) {
                System.out.println("Yield Thread B: " + i);
                if(i % 10 == 0)
                    Thread.yield();
            }
        }).start();
    }

    private static void sleep() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(5000);
                        System.out.println("Test Sleep Thread!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}