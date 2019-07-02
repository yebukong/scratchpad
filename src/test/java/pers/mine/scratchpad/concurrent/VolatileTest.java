package pers.mine.scratchpad.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * Volatile相关 - 保证可见性 - 不保证原子性 - 禁用指令重排（通过插入内存屏障指令[Memory Barrier]来保证）
 */
public class VolatileTest {

	/**
	 * JMM 内存模型 工作内存和主内存 同步延迟导致可见性问题
	 * 可见性验证 添加 volatile可以保证可见性
	 */
	@Test
	public void test1() throws InterruptedException {
		XData x = new XData();
		new Thread(() -> {
			System.out.println("线程A：");
			try {
				// 暂停一会
				TimeUnit.SECONDS.sleep(2);
				x.change10();
				System.out.println("值已更新");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		while (x.n == 0) {
			// 检测n的值
		}
		System.out.println("检测到n不为0");

		TimeUnit.SECONDS.sleep(10);
	}

	/**
	 * volatile 修饰数组可见性验证
	 */
	@Test
	public void test3() throws InterruptedException {
		XData x = new XData();
		new Thread(() -> {
			System.out.println("线程A：");
			try {
				// 暂停一会
				TimeUnit.SECONDS.sleep(2);
				x.strs[0] = null;
				System.out.println("值已更新");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		while (x.strs[0] != null) {
			// 检测n的值
		}
		System.out.println("检测到x.strs[0]变动"); // 可以保证

		TimeUnit.SECONDS.sleep(10);
	}

	/**
	 * volatile 修饰list 可见性验证
	 */
	@Test
	public void test4() throws InterruptedException {
		XData x = new XData();
		new Thread(() -> {
			System.out.println("线程A：");
			try {
				// 暂停一会
				TimeUnit.SECONDS.sleep(2);
				x.list.add("123");
				System.out.println("值已更新");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		while (x.list.isEmpty()) {
			// 检测n的值
		}
		System.out.println("检测到x.list变动"); // 可以保证

		TimeUnit.SECONDS.sleep(10);
	}

	/**
	 * volatile 修饰Object 可见性验证
	 */
	@Test
	public void test5() throws InterruptedException {
		XData x = new XData();
		new Thread(() -> {
			System.out.println("线程A：");
			try {
				// 暂停一会
				TimeUnit.SECONDS.sleep(2);
				x.v.check = false;
				System.out.println("值已更新");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		while (x.v.check) {
			// 检测n的值
		}
		System.out.println("检测到x.v.check变动"); // 可以保证

		TimeUnit.SECONDS.sleep(10);
	}

	/**
	 * 添加 volatile 不保证原子性
	 */
	@Test
	public void test11() throws InterruptedException {
		CountDownLatch cdl = new CountDownLatch(1);
		XData x = new XData();
		for (int i = 0; i < 20; i++) {
			new Thread(() -> {
				try {
					System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + " 启动");
					cdl.await();
					for (int j = 0; j < 1000; j++) {
						x.plus();
						x.volatilePlus(); // volatile不保证原子性
						x.sycnPlus(); // 使用synchronized可以保证
						x.atomPlus(); // 使用AtomicInteger保证原子性
					}
					System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + " 执行结束");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}, " 线程" + i).start();
		}
		TimeUnit.SECONDS.sleep(1);
		cdl.countDown();

//		while (Thread.activeCount() > 2) {
//			Thread.yield();
//		}
		TimeUnit.SECONDS.sleep(10);

		Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
		Set<Thread> keySet = allStackTraces.keySet();
		for (Thread thread : keySet) {
			System.out.println(thread.getName());
		}
		System.out.println("x. m=" + x.m);// 值不一定为20000
		System.out.println("x. vm=" + x.vm);// 值不一定为20000
		System.out.println("x. sm=" + x.sm);// 保证20000
		System.out.println("x. ai=" + x.ai.intValue());// 保证20000

	}
}

class XData {
	// int n = 0; //不加volatile时 主线程无法感知n变为0
	volatile int n = 0; // 加入volatile后主线程可以感知n值变动

	int m = 0;

	volatile int vm = 0; // 加入volatile后也不能保证原子性

	int sm = 0;

	AtomicInteger ai = new AtomicInteger();// 默认为0 CAS自旋锁保证原子性

	volatile String[] strs = { "A", "B", "C" };

	volatile List<String> list = new ArrayList<String>();

	volatile VO v = new VO();

	public void change10() {
		n = 10;
	}

	public void plus() {
		m++;
	}

	public void volatilePlus() {
		vm++;
	}

	public synchronized void sycnPlus() {
		sm++;
	}

	public void atomPlus() {
		ai.getAndIncrement();
		// ai.getAndAdd(1);
	}
}

class VO {
	boolean check = true;
}
