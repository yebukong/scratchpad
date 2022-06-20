package pers.mine.scratchpad.base.classload;

import java.util.concurrent.TimeUnit;

/**
 * 类加载死锁 ,java语言规范12.4.2
 */
public class DeadLock {
    public static class A {
        static {
            System.out.println("class A init");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            B.test();
        }

        public static void test() {
            System.out.println("aaa");
        }
    }

    public static class B {
        static {
            System.out.println("class B init");
            A.test();
        }

        public static void test() {
            System.out.println("bbb");
        }
    }

    public static void main(String[] args) {
        new Thread(() -> A.test()).start();
        new Thread(() -> B.test()).start();
    }
}
