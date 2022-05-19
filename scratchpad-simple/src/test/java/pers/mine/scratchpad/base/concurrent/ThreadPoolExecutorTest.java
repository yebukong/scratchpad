package pers.mine.scratchpad.base.concurrent;

import cn.hutool.core.util.StrUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程池参数
 *
 * @author Mine
 */
public class ThreadPoolExecutorTest {
    @Test
    public void test1() {
        int corePoolSize = 2;
        int maximumPoolSize = 5;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = null;
        ThreadFactory threadFactory = null;
        RejectedExecutionHandler handler = null;
        // 1.优先使用核心线程
        // 2.核心线程满了，进入工作队列
        // 3.队列满了，创建新线程执行任务，直到，线程数扩充到最大线程数
        // 4.如果在扩充到最大线程数，且工作队列也满了，还有新的任务进入，使用饱和拒绝策略
        // 5.如果一个线程执行完，会立即从任务队列取任务，当没有取到任务，就会判断存活时间，超时将销毁，线程数慢慢降到核心线程数
        new ThreadPoolExecutor(corePoolSize, // 核心线程数
                maximumPoolSize, // 最大线程数
                keepAliveTime, // 空闲线程（超过核心线程数的线程）存活时间
                unit, // keepAliveTime时间单位
                workQueue, // 任务队列，在核心线程满了后，就会使用任务队列，被提交但尚未执行的任务
                threadFactory, // 生成线程池中工作线程的工厂，用于生成线程池，一般使用默认
                handler);// 拒接策略，当最大线程满了，且任务队列也满了后的策略
        // 四种拒绝策略：
        // 1.AbortPolicy默认策略：直接抛出异常RejectedExecutionException
        // 2.CallRunsPolicy:调用者运行
        // 3.DiscardOldestPolicy:抛弃队列等待时间最久的任务
        // 4.Discard:直接丢弃任务,不处理，也不抛异常
    }

    /**
     * 一般使用自定义线程池，因为JDK提供的线程池可能导致OOM
     */
    @Test
    public void test2() {
        int corePoolSize = 2;
        int maximumPoolSize = 5;//cpu密集型：一般取核心数+1 IO密集型：做测试
        long keepAliveTime = 10L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        RejectedExecutionHandler handler = new ThreadPoolExecutor
                //.AbortPolicy();//丢弃并抛异常
                //.CallerRunsPolicy();//调用者处理
                .DiscardOldestPolicy();//抛弃等待最久的
        //.DiscardPolicy();//直接丢弃
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);

        try {
            for (int i = 0; i < 20; i++) {
                int tmp = i;
                executorService.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 处理 " + tmp);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 拒绝策略影响 Future.get,可能会造成永久阻塞
     */
    @Test
    public void futureTaskTest() throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor es = new ThreadPoolExecutor(
                1
                , 1
                , 1L
                , TimeUnit.MINUTES
                , new ArrayBlockingQueue<>(1)
                 , new ThreadPoolExecutor.DiscardPolicy()
                //, new ThreadPoolExecutor.DiscardOldestPolicy()
//                , new ThreadPoolExecutor.AbortPolicy()
        );

        Future futureOne = es.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("start runable one");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Future futureTwo = es.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("start runable two");
            }
        });
        Future futureThree = null;
        try {
            futureThree = es.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("start runable three");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("task one " + futureOne.get());//(5)等待任务one执行完毕
        System.out.println("task two " + futureTwo.get());//(6)等待任务two执行完毕
        System.out.println("task three " + (futureThree == null ? null : futureThree.get()));// (7)等待任务three执行完毕
        es.shutdown();
    }

    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolExecutorTest.class);

    @Test
    public void testScheduledPool() throws InterruptedException {
        String nameFormat = StrUtil.format("{}-%d", "scheduled-heartbeat");
        final ScheduledExecutorService heartbeatScheduledPool = Executors.newScheduledThreadPool(4,new ThreadFactoryBuilder().setNameFormat(nameFormat).build());
        heartbeatScheduledPool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                LOG.info("one 1");
                try {
                    Thread.sleep(10000);
                }catch (Exception e){

                }
                LOG.info("one 2");

            }
        }, 0L, 3, TimeUnit.SECONDS);

        heartbeatScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                LOG.info("two");
            }
        }, 0L, 10, TimeUnit.SECONDS);

        //普通的execute依旧可以调用
//        heartbeatScheduledPool.execute(()->{
//            LOG.info("execute");
//        });
        LOG.info("sleep");
        Thread.sleep(100000);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    heartbeatScheduledPool.shutdown();
                    boolean flag = heartbeatScheduledPool.awaitTermination(30, TimeUnit.MILLISECONDS);
                    if (!flag) {
                        heartbeatScheduledPool.shutdownNow();
                    }
                } catch (Throwable e) {
                    LOG.error(e.getMessage(), e);
                }
                LOG.info("Shutdown heartbeatScheduledPool");
            }
        });
    }
}
