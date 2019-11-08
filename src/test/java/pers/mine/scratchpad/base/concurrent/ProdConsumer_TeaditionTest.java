package pers.mine.scratchpad.base.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

import pers.mine.scratchpad.util.StringX;

/**
 * 生产者消费者
 * 
 * @author Mine
 * @date 2019/07/08 01:09:28
 */
public class ProdConsumer_TeaditionTest {
	/**
	 * lock版：一个初始为0的变量，两个变量交替修改，一个加1，一个减1 5次
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void test1() throws InterruptedException {
		ShareData sd = new ShareData();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				sd.increment();
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				sd.decrement();
			}
		}).start();

		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void test2() throws InterruptedException {
		ShareDataB sd = new ShareDataB();
		sd.setBq(new ArrayBlockingQueue<String>(1));

		new Thread(() -> {
			System.out.println("生产线程启动");
			try {
				sd.prod();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			System.out.println("消费线程启动");
			try {
				sd.cons();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

		TimeUnit.SECONDS.sleep(5);
		sd.setFlag(false);
		TimeUnit.SECONDS.sleep(5);
	}
}

class ShareData {
	private int data = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void increment() {
		lock.lock();
		try {
			// 判断
			while (data != 0) {
				condition.await();
			}
			// 生产
			data++;
			System.out.println(Thread.currentThread().getName() + "\t" + data);
			// 通知
			condition.signalAll();// 通知
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}

	public void decrement() {
		lock.lock();
		try {
			// 判断
			// while防止 signalAll虚假唤醒
			while (data == 0) {
				condition.await();
			}
			// 消费
			data--;
			System.out.println(Thread.currentThread().getName() + "\t" + data);
			// 通知
			condition.signalAll();// 通知
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}
}

class ShareDataB {
	private volatile boolean flag = true;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private AtomicInteger ai = new AtomicInteger();

	BlockingQueue<String> bq = null;

	public void setBq(BlockingQueue<String> bq) {
		this.bq = bq;
		System.out.println(bq.getClass().getName());
	}

	public void prod() throws InterruptedException {
		String data = "";
		boolean result = false;
		while (flag) {
			data = ai.incrementAndGet() +"|"+ System.nanoTime();
			result = bq.offer(data, 2, TimeUnit.SECONDS);
			if (result) {
				System.out.println(String.format("%s 插入%s 到队列成功", Thread.currentThread().getName(), data));
			} else {
				System.out.println(String.format("%s 插入%s 到队列失败", Thread.currentThread().getName(), data));
			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println("生产结束");

	}

	public void cons() throws InterruptedException {
		String result = "";
		while (flag) {
			System.out.println(System.nanoTime());
			result = bq.poll(2, TimeUnit.SECONDS);
			if (StringX.isEmpty(result)) {
				flag = false;
				System.out.println(String.format("%s从队列取出失败", Thread.currentThread().getName()));
			} else {
				System.out.println(String.format("%s 取出%s 从队列成功", Thread.currentThread().getName(), result));

			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println("消费 结束");

	}
}