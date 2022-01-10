package pers.mine.scratchpad;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author wangxiaoqiang
 * @description TODO
 * @create 2021-10-18 22:12
 */
public class BulkRebiasDemo {
    private static Thread t1, t2;

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(new Object());
        }
        System.out.println("THREAD-2 Object 11: " + ClassLayout.parseInstance(list.get(10)).toPrintable());
        System.out.println("THREAD-2 Object 26: " + ClassLayout.parseInstance(list.get(25)).toPrintable());
        System.out.println("THREAD-2 Object 36:" + ClassLayout.parseInstance(list.get(35)).toPrintable());
        System.out.println("------");

        t1 = new Thread(() -> {
            for (int i = 0; i < list.size(); i++) {
                synchronized (list.get(i)) {
                }
            }
            System.out.println("");
            LockSupport.unpark(t2);
        });
        t2 = new Thread(() -> {
            LockSupport.park();
            for (int i = 0; i < 30; i++) {
                Object o = list.get(i);
                synchronized (o) {
                    if (i == 10 || i == 25 || i == 35) {
                        System.out.println("THREAD-2 Object" + (i + 1) + ": " + ClassLayout.parseInstance(o).toPrintable());
                    }
                }
            }
        });
        t1.start();
        t2.start();
        t2.join();

        TimeUnit.SECONDS.sleep(3);
        System.out.println("THREAD-2 Object 11: " + ClassLayout.parseInstance(list.get(10)).toPrintable());
        System.out.println("THREAD-2 Object 26: " + ClassLayout.parseInstance(list.get(25)).toPrintable());
        System.out.println("THREAD-2 Object 36:" + ClassLayout.parseInstance(list.get(35)).toPrintable());
    }
}
