package pers.mine.scratchpad.base.thread;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThreadPoolTest {
	static Logger LOG = LoggerFactory.getLogger(ThreadPoolTest.class);

	private ExecutorService processPool;

	@Before
	public void init() {
		String processName = "测试线程-process-%d";
		processPool = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2),

				new ThreadFactoryBuilder().setNameFormat(processName).build(), new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						try {
							int nextInt = new java.util.Random().nextInt(10000);
							LOG.debug("拒绝策略开启 {}", nextInt);
							executor.getQueue().put(r);
							LOG.debug("拒绝策略结束 {}", nextInt);
						} catch (InterruptedException e) {
							throw new RejectedExecutionException("Unexpected InterruptedException", e);
						}
					}
				});

	}

	@Test
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				
				LOG.debug("start {}", i);
				processPool.execute(new RealRunnable(i));
				LOG.debug("end {}", i);
			}
			Thread.sleep(100*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class RealRunnable implements Runnable {
		int index;
		 RealRunnable(int index){
			 this.index = index;
		 }
		@Override
		public void run() {
			try {
				String name = Thread.currentThread().getName();
				LOG.debug("doRun start {}", index);
				Thread.sleep(5000);
				LOG.debug("doRun end {}", index);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}
}
