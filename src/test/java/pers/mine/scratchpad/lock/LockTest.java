package pers.mine.scratchpad.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Test;

/**
 * 锁 公平锁/不公平锁/可重入锁/自旋锁/独占锁/共享锁
 * 
 * @author Mine
 * @date 2019/07/04 00:43:13
 */
public class LockTest {
	@Test
	public void test1() {
		// synchronized 也属于非公平锁
		// 获取锁顺序和申请顺序无关，首先直接尝试获取锁，获取失败后采用类似公平锁的队列方式
		// 优点是吞吐量打，但可能导致某些线程一直等待
		ReentrantLock nonFairLock = new ReentrantLock();// 默认非公平
		// 根据申请锁顺序获取锁，先到先得 【依据当前锁维护的等待队列来保证】
		ReentrantLock fairLock = new ReentrantLock(true);// 公平
	}

	/**
	 * 可重入锁(递归锁)：最大作用是避免死锁
	 */
	@Test
	public void test2() {
		// 拥有当前锁的线程可以重复当前锁所同步的代码块
		ReentrantLock nonFairLock = new ReentrantLock();
	}

	/**
	 * synchronized 可重入验证
	 */
	@Test
	public void test3() throws InterruptedException {
		Phone phone = new Phone();
		new Thread(() -> {
			phone.call();
		}).start();

		new Thread(() -> {
			phone.call();
		}).start();

		TimeUnit.SECONDS.sleep(3);
	}

	/**
	 * ReentrantLock 可重入验证
	 */
	@Test
	public void test4() throws InterruptedException {
		PhoneA phoneA = new PhoneA();
		new Thread(phoneA).start();
		new Thread(phoneA).start();
		TimeUnit.SECONDS.sleep(3);
	}

	/**
	 * 自己实现自旋锁
	 * 自旋锁：指的是尝试获取锁的线程不会立即阻塞，而是采用循环方式获取锁，优点：减少线程上下文切换的性能损耗；缺点：可能导致cpu长时间空转
	 * @throws InterruptedException 
	 */
	@Test
	public void test5() throws InterruptedException {
		SpinLockDemo sld = new SpinLockDemo();
		new Thread(() -> {
			sld.lock();
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sld.unLock();
		}).start();

		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sld.lock();
			sld.unLock();
		}).start();
		TimeUnit.SECONDS.sleep(5);
	}
	
	/**
	 * 独占锁：ReentrantLock synchronized都是独占锁
	 *共享锁：读和读
	 *互斥锁:读和写  写和写
	 */
	@Test
	public void test6() {
		ReadWirteDemo rwd = new ReadWirteDemo();
		for (int i = 0; i < 5; i++) {
			final int tmp = i;
			new Thread(()->{
				rwd.read(tmp+"");
			},i+"").start();
		}
		for (int i = 0; i < 5; i++) {
			final int tmp = i;
			new Thread(()->{
				rwd.wirte(tmp+"",tmp+"");
			},i+"").start();
		}
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


/**
 * 读写锁实现
 *  读读 共存
 *  读写 互斥
 *  写写 互斥
 * @author Mine
 * @date 2019/07/04 20:14:35
 */
class ReadWirteDemo {
	private volatile Map<String, Object> cache = new HashMap<String, Object>(); 
	private ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
	
	public Object read(String key) {
		rrwl.readLock().lock();
		Object object = null;
		try {
			Thread ct = Thread.currentThread();
			System.out.println(ct.getName()+"\t 正在读取 "+ key);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			object = cache.get(key);
			System.out.println(ct.getName()+"\t 读取完成 "+ key);
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			rrwl.readLock().unlock();
		}
		return object;
	}
	
	public void wirte(String key,Object val) {
		rrwl.writeLock().lock();
		try {
			Thread ct = Thread.currentThread();
			System.out.println(ct.getName()+"\t 正在写入 "+ key);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cache.put(key, val);
			System.out.println(ct.getName()+"\t 写入完成 "+ key);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rrwl.writeLock().unlock();
		}
	}
}

class SpinLockDemo {
	AtomicReference<Thread> arth = new AtomicReference<Thread>();

	public void lock() {
		while (!arth.compareAndSet(null, Thread.currentThread())) {
		}
		System.out.println(System.nanoTime() + " | " + Thread.currentThread().getName() + "Lock");
		
	}

	public void unLock() {
		boolean result = arth.compareAndSet(Thread.currentThread(), null);
		System.out.println(System.nanoTime() + " | " + Thread.currentThread().getName() + "unLock" + " | " + result);
	}
}

class Phone {
	public synchronized void sendSMS() {
		System.out.println(System.nanoTime() + " | " + Thread.currentThread().getName() + "sendSMS");
	}

	public synchronized void call() {
		sendSMS();
		System.out.println(System.nanoTime() + " | " + Thread.currentThread().getName() + "call");
	}
}

class PhoneA implements Runnable {

	Lock lock = new ReentrantLock();

	@Override
	public void run() {
		A();
	}

	public void A() {
		lock.tryLock();
//		lock.tryLock(); 可以重复加锁，但解锁次数也要相匹配
		try {
			System.out.println(System.nanoTime() + " | " + Thread.currentThread().getName() + "A");
			B();
		} finally {
			lock.unlock();
		}

	}

	public void B() {
		lock.tryLock();
		try {
			System.out.println(System.nanoTime() + " | " + Thread.currentThread().getName() + "B");
		} finally {
			lock.unlock();
		}

	}
}