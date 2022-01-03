package nori.programming.java.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/***
 * Phaser
 */
public class T1_16_2 {
    static ManagerPhaser managerPhaser = new ManagerPhaser();

    private static class Person implements Runnable{
        String name;

        public Person(String name) {
            this.name = name;
        }

        void arrive(){
            System.out.println(this.name + "到达！");
        }

        void eat(){
            System.out.println(this.name + "已吃！");
        }

        void leave(){
            System.out.println(this.name + "离开！");
        }

        void collectTable(){
            if(this.name.startsWith("Worker")){
                System.out.println(this.name + "收拾饭桌！");
                managerPhaser.arriveAndAwaitAdvance();
            }else{
                // Arrives at this phaser and deregisters from it without waiting for others to arrive
                // 降低完成 phaser 需要到达的数量
                managerPhaser.arriveAndDeregister();
            }

        }

        @Override
        public void run() {
            arrive();
            managerPhaser.arriveAndAwaitAdvance();

            eat();
            managerPhaser.arriveAndAwaitAdvance();

            leave();
            managerPhaser.arriveAndAwaitAdvance();

            // 只等待worker, 减少parties
            collectTable();
        }
    }

    private static class ManagerPhaser extends Phaser {

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase){
                case 0:
                    System.out.println("所有都人到了！");
                    return false;
                case 1:
                    System.out.println("所有人吃完！");
                    return false;
                case 2:
                    System.out.println("所有人离开！");
                    return false;
                case 3:
                    System.out.println("收拾饭桌！");
                    //返回 true 如果 phaser 结束
                    return true;
                default:
                    return false;
            }
        }
    }

    public static void main(String[] args) throws InstantiationException {

        managerPhaser.bulkRegister(5);

        for (int i = 0; i < 3; i++) {
            final int nameIndex = i;

            new Thread(new Person("Person " + nameIndex)).start();
        }

        new Thread(new Person("Worker1 Person ")).start();
        new Thread(new Person("Worker2 Person ")).start();
    }
}
