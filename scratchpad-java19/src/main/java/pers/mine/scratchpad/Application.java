package pers.mine.scratchpad;

import cn.hutool.core.exceptions.ExceptionUtil;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        Thread.UncaughtExceptionHandler ueh = (t, e) -> {
            String msg = "ueh thread: %s ,target thread: %s \n %s"
                    .formatted(Thread.currentThread(), t, ExceptionUtil.stacktraceToString(e));
            System.err.println(msg);
        };
        Runnable r = () -> {
            System.out.println(Thread.currentThread());
            throw new IllegalArgumentException();
        };
        Thread pThread = Thread.ofPlatform().uncaughtExceptionHandler(ueh).unstarted(r);
        Thread vThread = Thread.ofVirtual().uncaughtExceptionHandler(ueh).unstarted(r);
        pThread.start();
        vThread.start();
        pThread.join();
        vThread.join();
        Thread.sleep(10);
        System.out.println("end");
    }
}
