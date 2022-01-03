package nori.programming.java.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/***
 * Phaser
 */
public class T1_16_1 {

    private static class Person{
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
                    return true;
                default:
                    return false;
            }
        }
    }

    public static void main(String[] args) throws InstantiationException {
        ManagerPhaser managerPhaser = new ManagerPhaser();
        managerPhaser.bulkRegister(10);

        for (int i = 0; i < 10; i++) {
            final int nameIndex = i;

            new Thread(()->{
                Person p = new Person("Person " + nameIndex);

                p.arrive();
                managerPhaser.arriveAndAwaitAdvance();

                p.eat();
                managerPhaser.arriveAndAwaitAdvance();

                p.leave();
                managerPhaser.arriveAndAwaitAdvance();
            }).start();
        }

    }
}
