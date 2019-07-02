package pers.mine.scratchpad.concurrent.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.Test;

import lombok.Data;

/**
 * CAS相关测试 比较并交换 <br>
 * 缺点： 1.自旋频繁，可能加大cpu开销;2.仅仅可以保证一个共享变量的原子操作；3.存在ABA问题
 * 
 * @author Mine
 */
public class CasTest {
	@Test
	public void compareAndSetTest() {
		AtomicInteger ai = new AtomicInteger(5);
		System.out.println(ai.compareAndSet(5, 2019) + "|" + ai.get());
		System.out.println(ai.compareAndSet(5, 1024) + "|" + ai.get());
		System.out.println(ai.getAndIncrement());

	}
	// 这里的cas【weakCompareAndSetInt】是并发原语 依赖于硬件实现，属于原子指令
	// offset 表示内存地址
	// 当获取值后到修改值过程中，值被改过，cas将不通过，需要重新从主内存获取最新值，进行下一次修改尝试
	//
//	public final int getAndAddInt(Object o, long offset, int delta) {
//		int v;
//		do {
//			v = getIntVolatile(o, offset);
//		} while (!weakCompareAndSetInt(o, offset, v, v + delta));
//		return v;
//	}

	/**
	 * 原子引用
	 */
	@Test
	public void abaatomicReferenceTest() {
		Vo v1 = new Vo("A", 11);
		Vo v2 = new Vo("B", 12);
		AtomicReference<Vo> ar = new AtomicReference<Vo>();
		ar.set(v1);
		System.out.println(ar.compareAndSet(v1, v2) + "|" + ar.get());
		System.out.println(ar.compareAndSet(v1, v2) + "|" + ar.get());
	}

	/**
	 * ABA问题解决: AtomicStampedReference 带版本号的CAS
	 */
	@Test
	public void abaTest() {
		AtomicStampedReference<Integer> asr = new AtomicStampedReference<Integer>(100, 1);
		AtomicReference<Integer> ar = new AtomicReference<Integer>(100);

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "| " + ar.compareAndSet(100, -100) + "|" + ar.get()
					+ " | " + asr.getStamp());
			System.out.println(Thread.currentThread().getName() + "| " + ar.compareAndSet(-100, 100) + "|" + ar.get()
					+ " | " + asr.getStamp());
		}, "T1").start();
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// T2| true|-50 存在ABA问题
			System.out.println(Thread.currentThread().getName() + "| " + ar.compareAndSet(100, -50) + "|" + ar.get());
		}, "T2").start();

		new Thread(() -> {
			int stamp = asr.getStamp();
			System.out.println(Thread.currentThread().getName() + " | v:" + stamp);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "| " + asr.compareAndSet(100, -100, stamp, ++stamp)
					+ "|" + asr.getReference());
			System.out.println(Thread.currentThread().getName() + "| " + asr.compareAndSet(-100, 100, stamp, ++stamp)
					+ "|" + asr.getReference());

		}, "T3").start();

		new Thread(() -> {
			int stamp = asr.getStamp();
			System.out.println(Thread.currentThread().getName() + " | v:" + stamp);
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//T4| false|100 | 3  通过版本号解决ABA问题
			System.out.println(Thread.currentThread().getName() + "| " + asr.compareAndSet(100, 50, stamp, ++stamp)
					+ "|" + asr.getReference() + " | " + asr.getStamp());

		}, "T4").start();

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@Data
class Vo {
	public String name;
	public int age;

	public Vo(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

}
