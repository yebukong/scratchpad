package pers.mine.scratchpad.other;

import java.util.concurrent.atomic.AtomicInteger;

public class SafepointTest {
    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
//        Runnable runnable = () -> {
//            for (int i = 0; i < 1000000000; i++) {
//                num.getAndAdd(1);
//            }
//        };
//
//        Thread t1 = new Thread(runnable);
//        Thread t2 = new Thread(runnable);
//        t1.start();
//        t2.start();
//        Thread.sleep(1000);
//        System.out.println("num = " + num);
        Class<? extends Object> aClass = new Object() {
        }.getClass();
        System.out.println(aClass == Object.class);
    }
}
