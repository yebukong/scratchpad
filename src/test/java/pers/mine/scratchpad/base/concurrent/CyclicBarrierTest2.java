package pers.mine.scratchpad.base.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description TODO
 * @create 2020-04-01 20:07
 */
public class CyclicBarrierTest2 {
    static CyclicBarrier cb = new CyclicBarrier(10);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        cb.reset();
        System.out.println("开始执行...");
        for (int i = 0; i < 9; i++) {

            int x = i;
            new Thread(() -> {
                try {
                    System.out.println("执行完了一些事情" + x);
                    cb.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        System.out.println("等待");
        cb.await();
        System.out.println("看看会不会执行");
        cb.reset();
        for (int i = 0; i < 9; i++) {

            int x = i;
            new Thread(() -> {
                try {
                    System.out.println("执行完了一些事情" + x);
                    cb.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        cb.await();
        System.out.println("看看会不会执行1");

    }
}
