package nori.programming.java.juc;

import java.util.concurrent.TimeUnit;

/***
 * dirty read
 */
public class T1_6_1 {

    private static class Account{
        private String name;
        private int account;

        public synchronized void setProperties(String name, int account){
            this.name = name;
            this.account = account;
        }

        public int getAccount(){return this.account;}
    }

    public static void main(String[] args) {

        Account acc = new Account();
        new Thread(()-> acc.setProperties("Nori", 100)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(acc.getAccount());

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(acc.getAccount());
    }
}
