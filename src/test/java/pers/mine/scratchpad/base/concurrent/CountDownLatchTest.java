package pers.mine.scratchpad.base.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch简单使用
 * 
 * @author Mine
 * @date 2019/06/26 19:24:05
 */
public class CountDownLatchTest {
	public static void main(String[] args) {
		final CountDownLatch cl = new CountDownLatch(3);

		// 需要等待线程
		new Thread(() -> {
			try {
				long start = System.currentTimeMillis();
				System.out.println(start + ":" + Thread.currentThread().getName() + " 启动，等待其他线程");
				cl.await();
				long end = System.currentTimeMillis();
				System.out.println(end + ":" + Thread.currentThread().getName() + " 执行结束,等待时间:"+(end-start)+"ms");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				long start = System.currentTimeMillis();
				System.out.println(start + ":" + Thread.currentThread().getName() + " 启动，等待其他线程");
				cl.await();
				long end = System.currentTimeMillis();
				System.out.println(end + ":" + Thread.currentThread().getName() + " 执行结束,等待时间:"+(end-start)+"ms");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		// 被等待线程
		new Thread(() -> {
			try {
				int tmp = (int) (Math.random() * 3000);
				System.out.println(
						System.currentTimeMillis() + ":" + Thread.currentThread().getName() + " 启动，预计执行" + tmp + "ms");
				Thread.sleep(tmp);
				System.out.println(System.currentTimeMillis() + ":" + Thread.currentThread().getName() + "结束");
				cl.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				int tmp = (int) (Math.random() * 3000);
				System.out.println(
						System.currentTimeMillis() + ":" + Thread.currentThread().getName() + " 启动，预计执行" + tmp + "ms");
				Thread.sleep(tmp);
				System.out.println(System.currentTimeMillis() + ":" + Thread.currentThread().getName() + "结束");
				cl.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				int tmp = (int) (Math.random() * 3000);
				System.out.println(
						System.currentTimeMillis() + ":" + Thread.currentThread().getName() + " 启动，预计执行" + tmp + "ms");
				Thread.sleep(tmp);
				System.out.println(System.currentTimeMillis() + ":" + Thread.currentThread().getName() + "结束");
				cl.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
