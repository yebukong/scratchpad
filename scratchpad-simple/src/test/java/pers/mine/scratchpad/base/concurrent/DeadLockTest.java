package pers.mine.scratchpad.base.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 死锁相关:循环等待
 * 
 * @author Mine
 * @date 2019/07/09 19:19:49
 */
public class DeadLockTest {
	/**
	 * 重现死锁
	 */
	public static void main(String[] args) {
		String a = "AAA";
		String b = "BBB";
		new Thread(new HoldLocker(a, b)).start();
		new Thread(new HoldLocker(b, a)).start();

	}
}

class HoldLocker implements Runnable {
	private String a;
	private String b;

	public HoldLocker(String a, String b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		synchronized (a) {
			System.out.println(Thread.currentThread().getName() + "\n 持有" + a );
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
			}
			System.out.println(Thread.currentThread().getName() + "\n 尝试获取" + b );
			synchronized (b) {
				System.out.println(Thread.currentThread().getName() + "\n 持有" + b );
			}
		}
	}
}
