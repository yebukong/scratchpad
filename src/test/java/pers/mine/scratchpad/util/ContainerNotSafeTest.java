package pers.mine.scratchpad.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 集合类不安全验证
 * 
 * @author Mine
 * @date 2019/07/03 19:13:24
 */
public class ContainerNotSafeTest {
	@Test
	public void test1() {
		// 初始化 空Object数组,初始数组大小为10(仅仅当add时才真正初始化数组大小)
		List<Integer> list = new ArrayList<Integer>();
		// 新增的时候发现容量不够用了，就去扩容，扩容原则：int newCapacity = oldCapacity + (oldCapacity >> 1);
		list.add(1);// 在首次add时会真正给定初始大小数组
		// 初始容量定义：默认为1 << 4（16）。最大容量为1<< 30。
		Map<String, String> map = new HashMap<String, String>();
		// 扩容加载因子为(0.75)，第一个临界点在当HashMap中元素的数量等于table数组长度*加载因子（16*0.75=12），
		// 如果超出则按oldThr << 1（原长度*2）扩容。
		map.put("A", "A");
	}

	@Test
	public void test2() throws InterruptedException {
		List<String> list = new ArrayList<String>(Arrays.asList("A", "B", "C"));
		list.forEach(System.out::println);// lambda表达式
		list.clear();

		// java.util.ConcurrentModificationException 并发修改异常
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				list.add(tmp + "");
				System.out.println(list);
			}).start();
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(list);

	}
	
	@Test
	public void test211() throws InterruptedException {
		List<String> list = new ArrayList<String>(Arrays.asList("A", "B", "C"));
		//java List的remove方法也会导致的异常java.util.ConcurrentModificationException
		for (int i = 0; i<list.size(); i++) {
			list.remove(i+1);
		}

	}

	/**
	 * Vector
	 */
	@Test
	public void test3() throws InterruptedException {
		// 可以解决并发异常，但性能不好
		List<String> list = new Vector<String>();
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				list.add(tmp + "");
				System.out.println(list);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(list);
	}

	// CopyOnWriteArrayList的写操作性能较差，而多线程的读操作性能较好。
	// 而Collections.synchronizedList的写操作性能比的Cop​​yOnWriteArrayList在多线程操作的情况下要好很多，而读操作因为是采用了同步关键字的方式，其读操作性能并不如的CopyOnWriteArrayList。
	/**
	 * Collections.synchronizedList包装
	 */
	@Test
	public void test4() throws InterruptedException {
		// 使用Collections.synchronizedList包装一下 包装类内部使用synchronized方式调用真实容器
		// ，无法保证迭代iterator的并发异常
		List<String> list = Collections.synchronizedList(new ArrayList<String>());
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				list.add(tmp + "");
				System.out.println(list);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(list);
	}

	/**
	 * CopyOnWriteArrayList 写时复制 读写分离思想
	 */
	@Test
	public void test5() throws InterruptedException {
		List<String> list = new CopyOnWriteArrayList<String>();
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				list.add(tmp + "");//由于在写时会复制一份数据，因此写性能很差,但读性能不受影响(除了volatile带来的损失)
				System.out.println(list);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(list);
	}
	
	/**
	 * HashSet 线程不安全
	 * 基于HashMap实现，value为一个 new Object();
	 */
	@Test
	public void test11() throws InterruptedException {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				set.add(tmp + "");
				System.out.println(set);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(set);
	}
	
	/**
	 * Collections.synchronizedSet 包装
	 * @throws InterruptedException
	 */
	@Test
	public void test12() throws InterruptedException {
		Set<String> set = Collections.synchronizedSet(new HashSet<String>());
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				set.add(tmp + "");
				System.out.println(set);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(set);
	}
	
	/**
	 * CopyOnWriteArraySet 写时复制
	 * 底层基于 CopyOnWriteArrayList,add方法保证唯一
	 * @throws InterruptedException
	 */
	@Test
	public void test13() throws InterruptedException {
		Set<String> set = new CopyOnWriteArraySet<String>();
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				set.add(tmp + "");
				System.out.println(set);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(set);
	}
	
	
	/**
	 * HashMap 线程不安全
	 */
	@Test
	public void test21() throws InterruptedException {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				map.put(tmp + "",tmp+"");
				System.out.println(map);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(map);
	}
	
	/**
	 * Collections.synchronizedMap 
	 */
	@Test
	public void test22() throws InterruptedException {
		Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				map.put(tmp + "",tmp+"");
				System.out.println(map);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(map);
	}
	
	/**
	 * ConcurrentHashMap 冲突小于8 时是链表，大于则是红黑树
	 *  1.7 使用分段锁+红黑树
	 *  1.8 Node数组+链表+红黑树
	 */
	@Test
	public void test24() throws InterruptedException {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		for (int i = 0; i < 30; i++) {
			int tmp = i;
			new Thread(() -> {
				map.put(tmp + "",tmp+"");
				System.out.println(map);
			}).start();
			;
		}
		TimeUnit.SECONDS.sleep(3);
		System.out.println(map);
	}
}
