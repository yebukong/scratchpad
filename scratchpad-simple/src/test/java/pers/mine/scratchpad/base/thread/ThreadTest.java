package pers.mine.scratchpad.base.thread;

import cn.hutool.core.lang.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 线程相关
 *
 * @author Mine
 * @date 2019/07/01 20:00:12
 */
public class ThreadTest {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadTest.class);

    /**
     * 获取当前所有线程
     */
    @Test
    public void showThreads() {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        int activeCount = Thread.activeCount();//当前活动线程数
        System.out.println(activeCount);

        for (Thread t : allStackTraces.keySet()) {
            System.out.println(t.getName());
        }
    }

    @Test
    public void showThreadsInfo() {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Thread t : allStackTraces.keySet()) {
            System.out.println(t.getName());
            StackTraceElement[] stackTraceElements = allStackTraces.get(t);
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                System.out.println(stackTraceElement.getClassName());
            }
        }
    }

    /**
     * 另一种获取方式：通过线程组
     * 线程组相关:https://www.cnblogs.com/noteless/p/10354721.html
     */
    @Test
    public void showThreadsX() {
        ThreadGroup currTg = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = currTg;
        // 遍历线程组树，获取根线程组
        while (currTg != null) {
            topGroup = currTg;
            currTg = currTg.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 枚举根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("Thread list size == " + list.length);
        for (Thread thread : list) {
            System.out.println(thread.getName());
        }
    }

    /**
     * 从堆栈信息可以看到，UncaughtExceptionHandler方法还是由抛异常的线程调用的，且具体方法为
     * @java.lang.Thread#dispatchUncaughtException(java.lang.Throwable)
     */
    @Test
    public void testUncaughtExceptionHandler() throws InterruptedException {
        Thread tt = new Thread() {
            @Override
            public void run() {
                Assert.notNull(null);
            }
        };
        tt.setUncaughtExceptionHandler((thread, ex) -> {
            // TODO 该异常需要预警
            LOG.error("[Worker] {} 执行异常", thread, ex);
            try {
                throw new RuntimeException();
            }catch (Exception e){
                LOG.error("异常", e);
            }

        });
        tt.setName("QueryLogServiceImpl-Worker");
        tt.start();

        tt.join();
    }
}
