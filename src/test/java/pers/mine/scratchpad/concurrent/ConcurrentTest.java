package pers.mine.scratchpad.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

/**
 * 简单使用 CountDownLatch 和 CyclicBarrier
 * @author xqwang
 * @since 2019年3月27日
 */
public class ConcurrentTest {
	static int pool = 2;
	@Test
	public void countDownLatchTest() throws InterruptedException {
		CountDownLatch cdl = new CountDownLatch(1);
		for (int i = 0; i < 100; i++) {
			Thread th = new Thread(new CDLRunnable(cdl));
			th.start();
		}
		System.out.println(System.currentTimeMillis());
		cdl.countDown();
		Thread.sleep(2000);
		System.out.println(pool);
		
	}
	class CDLRunnable implements Runnable{
		CountDownLatch cdl = null;
		public CDLRunnable(CountDownLatch cdl){
			this.cdl=cdl;
		}
		public void run() {
			try {
				System.out.println(System.currentTimeMillis() + ":"
						+ Thread.currentThread().getName() + " 开始执行");
				cdl.await();
				if (pool > 0) {
					pool = pool - 1;
				}
				System.out.println(System.currentTimeMillis() + ":"
						+ Thread.currentThread().getName()+ " 结束执行");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void cyclicBarrierTest() throws InterruptedException {
		//所有 cb.await(); 的线程的数量到了10 就开始唤醒其他等待线程继续执行
		CyclicBarrier cb = new CyclicBarrier(10,new Runnable() {
			@Override
			public void run() {
				System.out.println(System.currentTimeMillis() + ":执行一点什么");
			}
		});
		for (int i = 0; i < 9; i++) {
			Thread th = new Thread(new CBRunnable(cb));
			th.start();
		}
		Thread.sleep(2000);
		Thread th = new Thread(new CBRunnable(cb));
		th.start();
		Thread.sleep(2000);
		for (int i = 0; i < 10; i++) {
			new Thread(new CBRunnable(cb)).start();
		}
		Thread.sleep(2000);
		System.out.println(pool);
	}
	class CBRunnable implements Runnable{
		CyclicBarrier cb = null;
		public CBRunnable(CyclicBarrier cb){
			this.cb=cb;
		}
		
		public void run() {
			try {
				System.out.println(System.currentTimeMillis() + ":"
						+ Thread.currentThread().getName() + " 开始执行");
				cb.await();
				if (pool > 0) {
					pool = pool - 1;
				}
				System.out.println(System.currentTimeMillis() + ":"
						+ Thread.currentThread().getName()+ " 结束执行");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
