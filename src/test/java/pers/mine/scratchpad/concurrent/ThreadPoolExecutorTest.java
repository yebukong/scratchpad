package pers.mine.scratchpad.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

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
	 * 
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
				executorService.execute(()->{
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"\t 处理 "+tmp);
				});
			}
		}catch (Exception e) {
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
}
