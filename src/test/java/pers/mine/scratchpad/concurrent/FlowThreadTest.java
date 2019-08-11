package pers.mine.scratchpad.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * 确保线程顺序执行
 * 
 * @author Mine
 * @date 2019/06/26 07:31:53
 */
public class FlowThreadTest {

	public void flowTestOne() throws InterruptedException {
		TRunnable one = new TRunnable("A");
		TRunnable two = new TRunnable("B");
		TRunnable three = new TRunnable("C");

		Thread thread1 = new Thread(one);
		Thread thread2 = new Thread(two);
		Thread thread3 = new Thread(three);

		thread1.start();
		thread1.join();
		thread2.start();
		thread2.join();
		thread3.start();
		thread3.join();
	}

	/**
	 * 使用 join();
	 */
	@Test
	public void test1() throws InterruptedException {
		FlowThreadTest ftt = new FlowThreadTest();
		for (int i = 0; i < 100; i++) {
			ftt.flowTestOne();
		}

		Thread.sleep(3000);
	}

	/**
	 * 使用单线程池 Executors.newSingleThreadExecutor();
	 */
	@Test
	public void test2() throws InterruptedException {
		FlowThreadTest ftt = new FlowThreadTest();
		ExecutorService es = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 100; i++) {
			ftt.flowTestTwo(es, i + "");
		}
		es.shutdown();

		Thread.sleep(3000);
	}

	public void flowTestTwo(ExecutorService es, String preFix) throws InterruptedException {
		TRunnable one = new TRunnable(preFix + " A");
		TRunnable two = new TRunnable(preFix + " B");
		TRunnable three = new TRunnable(preFix + " C");

		es.submit(one);
		es.submit(two);
		es.submit(three);
	}

	class TRunnable implements Runnable {
		private String name;

		public TRunnable(String name) {
			this.name = name;
		}

		public void run() {
			System.out.println(name);
			try {
				Thread.sleep((int) (Math.random() * 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用wait()和notify() 类似 lock 和Condition搭配
	 */
	@Test
	public void test31() throws InterruptedException {
		ShareData sd = new ShareData();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				sd.sayA();
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				sd.sayB();
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				sd.sayC();
			}
		}).start();
		TimeUnit.SECONDS.sleep(3);
		Thread.sleep(3000);
	}

	/**
	 * 
	 * Condition线程间细粒度通信
	 */
	class ShareData {
		private int data = 1;
		private ReentrantLock lock = new ReentrantLock();
		private Condition conditionA = lock.newCondition();
		private Condition conditionB = lock.newCondition();
		private Condition conditionC = lock.newCondition();

		public void sayA() {
			lock.lock();
			try {
				while (data != 1) {
					conditionA.await();
				}
				data = 2;
				System.out.println("A说话");
				conditionB.signalAll();// 通知
			} catch (Exception e) {
			} finally {
				lock.unlock();
			}
		}

		public void sayB() {
			lock.lock();
			try {
				while (data != 2) {
					conditionB.await();
				}
				data = 3;
				System.out.println("B说话");
				conditionC.signalAll();// 通知
			} catch (Exception e) {
			} finally {
				lock.unlock();
			}
		}

		public void sayC() {
			lock.lock();
			try {
				while (data != 3) {
					conditionC.await();
				}
				data = 1;
				System.out.println("C说话");
				conditionA.signalAll();// 通知
			} catch (Exception e) {
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 使用CountDownLatch
	 */
	@Test
	public void test4() throws InterruptedException {
		// TODO
	}
}
