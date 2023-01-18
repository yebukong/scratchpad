package pers.mine.scratchpad.base.thread;

import java.util.Date;

public class JavaExitTest {
    public static void main(String[] args) {
        System.out.println("start");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Hook :" + getName());
            }
        });
        Thread test1 = new Thread("test1") {
            @Override
            public void run() {
                while (true) {
                    System.out.println(new Date());
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        test1.setDaemon(false);
        test1.start();
        System.out.println("end");
    }
}
