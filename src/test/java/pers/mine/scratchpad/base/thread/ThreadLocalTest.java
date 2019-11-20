package pers.mine.scratchpad.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;

/**
 * 线程本地变量
 * 
 * @author Mine
 * @date 2019/07/17 07:19:00
 */
public class ThreadLocalTest {
	/**
	 * 普通ThreadLocal变量
	 */
	final ThreadLocal<Integer> local = new ThreadLocal<Integer>() {
		/*
		 * 可以使用重写的方式赋初始值
		 * 
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		protected Integer initialValue() {
			return -1;
		};
	};
	/**
	 * 可继承的ThreadLocal变量：父子线程之间传递
	 */
	ThreadLocal<Integer> inheritableLocal = new InheritableThreadLocal<Integer>() {
		protected Integer initialValue() {
			return -2;
		};
	};

	/**
	 * 可在线程池调用中传递的ThreadLocal变量,需要搭配TtlRunnable使用
	 */
	ThreadLocal<Integer> transmittableLocal = new TransmittableThreadLocal<Integer>() {
		protected Integer initialValue() {
			return -3;
		};
	};

	@Test
	public void test1() throws InterruptedException {
		test3("init");
		test2("run");
		TimeUnit.SECONDS.sleep(2);
		new Thread(() -> {
			test3("init");
			test2("run");
			// 用完记得remove,防止内存泄漏
			local.remove();
		}).start();
		new Thread(() -> {
			test3("init");
			test2("run");
		}).start();

		TimeUnit.SECONDS.sleep(2);
		test3("end");

	}

	public static void main(String[] args) throws InterruptedException {
		new ThreadLocalTest().testThreadPool();
	}

	@Test
	public void testThreadPool() throws InterruptedException {
		test3("init");
		test2("run");
		System.out.println();
		TimeUnit.SECONDS.sleep(1);
		//可以使用TtlExecutors包装线程池
		//ExecutorService execService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));
		ExecutorService execService = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 5; i++) {
			execService.submit(TtlRunnable.get(() -> {

				test3("init1");
				test2("run1");
				// 用完记得remove,防止内存泄漏,以及污染复用线程的变量
//				local.remove();
//				inheritableLocal.remove();
//				transmittableLocal.remove();

			}));
			execService.submit(TtlRunnable.get(() -> {
				test3("init2");
				test2("run2");
//				local.remove();
//				inheritableLocal.remove();// inheritableLocal.remove会造成父进程变量丢失
//				transmittableLocal.remove(); //transmittableLocal remove 操作不影响父进程变量继承，不操作也不受进程复用影响

			}));
		}

		TimeUnit.SECONDS.sleep(4);
		System.out.println();
		test3("end");
	}

	public void test2(String mark) {
		int tmp = (int) (Math.random() * 100);
		local.set(tmp);
		inheritableLocal.set(tmp);
		transmittableLocal.set(tmp);
		test3(mark);
	}

	public void test3(String mark) {
		System.out.println(String.format("%18s [%5s]- local : %5s  inheritableLocal : %5s transmittableLocal : %5s",
				Thread.currentThread().getName(), mark, local.get(), inheritableLocal.get(), transmittableLocal.get()));
	}
}
