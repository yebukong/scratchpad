package pers.mine.scratchpad.base.concurrent.locks;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author wangxiaoqiang
 * @description TODO
 * @create 2022-01-10 19:59
 */
public class LockSupportTest {
    private static final Logger LOG = LoggerFactory.getLogger(LockSupportTest.class);

    @Test
    public void park() {
        Thread main = Thread.currentThread();
        LOG.info("start {}", Thread.currentThread().isInterrupted());
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                LOG.info("main {}", main.isInterrupted());
                //唤醒
                LockSupport.unpark(main);
                //中断
                //main.interrupt();
                LOG.info("main {}", main.isInterrupted());
            } catch (Exception e) {
                LOG.error("err", e);
            }
        }).start();
        // 参数blocker是用来标识当前线程在等待的对象，该对象主要用于问题排查和系统监控
        LockSupport.parkNanos(this, TimeUnit.SECONDS.toNanos(10));
        LOG.info("end {}", Thread.currentThread().isInterrupted());
    }
}
