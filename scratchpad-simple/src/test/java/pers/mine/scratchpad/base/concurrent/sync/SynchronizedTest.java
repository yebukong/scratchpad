package pers.mine.scratchpad.base.concurrent.sync;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class SynchronizedTest {
    @Test
    public void test1() {
        String[] arr = {""};
        synchronized (arr) {
            System.out.println(arr);
        }
    }

    final static A a;

    static {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a = new A();
    }

    @Test
    public void test3() throws InterruptedException {
        synchronized (a) {
            new Object().notifyAll();
        }
    }

    public synchronized void test2() {
        System.out.println(11);
    }

    @Test
    public void test4() {
        ClassLoader classLoader = Object.class.getClassLoader();
        System.out.println(classLoader);
    }

    static class A {
        public int a;

        void show() {
            System.out.println(a);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("start");
        System.out.println("A - " + ClassLayout.parseInstance(a).toPrintable());
//        System.out.println(o.hashCode());
        synchronized (a) {
            try {
                System.out.println("B - " + ClassLayout.parseInstance(a).toPrintable());
                a.wait(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("C - " + ClassLayout.parseInstance(a).toPrintable());
            a.notifyAll();
            System.out.println("C1 - " + ClassLayout.parseInstance(a).toPrintable());
        }
        System.out.println("D - " + ClassLayout.parseInstance(a).toPrintable());
        System.out.println("E - " + ClassLayout.parseInstance(new A()).toPrintable());

        Thread.sleep(5000);

        System.out.println("D1 - " + ClassLayout.parseInstance(a).toPrintable());
    }

}
