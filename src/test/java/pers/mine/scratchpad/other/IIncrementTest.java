package pers.mine.scratchpad.other;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证<code>i++;</code>不是原子操作及几种解决方法
 * 
 * @author Mine
 * @date 2019/04/09 19:04:41
 */
public class IIncrementTest {
//  volatile 只可以保证可见性，无法保证原子操作
	int i = 0;
	AtomicInteger ai = new AtomicInteger(0);
	Lock lock = new ReentrantLock();

	public void incr() {
		i++;
	}

	/**
	 * synchronized 关键字解决
	 */
	public synchronized void incr1() {
		i++;
	}

	/**
	 * 使用原子类解决
	 */
	public void incr2() {
		ai.incrementAndGet(); // 原子类自增
	}

	/**
	 * 使用Lock类解决
	 */
	public void incr3() {
		try {
			lock.lock();
			i++;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		IIncrementTest it = new IIncrementTest();
		CountDownLatch cdl = new CountDownLatch(4);
		for (int i = 0; i < 4; i++) {
			new Thread(() -> {
				try {
					cdl.await();
					for (int j = 0; j < 10000; j++) {
//						it.incr();
//						it.incr1();
//						it.incr2();
						it.incr3();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}).start();
			cdl.countDown();
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(it.i);
//		System.out.println(lt.ai.get());
	}
}
