package pers.mine.scratchpad.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 测试雪花算法
 * 
 * @author Mine
 * @date 2019/04/09 23:04:78
 */
public class SnowFlakeTest {

	/**
	 * 唯一性校验
	 */
	@Test
	public void test() throws InterruptedException {
		int num = 100000;
		Map<String, String> idMap = new ConcurrentHashMap<String, String>();
		CountDownLatch cdl = new CountDownLatch(100);
		SnowFlake snowFlake = new SnowFlake(0, 0);
		for (int i = 0; i < 100; i++) {
			final int x = i;
			new Thread(() -> {
				try {
					cdl.await();
					System.out.println(x + " 开始");
					for (int j = 0; j < num; j++) {
						String id = snowFlake.nextID(16);
						idMap.put(id, "");
					}
					System.out.println(x + " 结束.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			cdl.countDown();
		}
		TimeUnit.SECONDS.sleep(30);
		int size = idMap.size();
		boolean result = size == 100 * num;
		System.out.println(size + " | " + result);
		assertTrue("ID存在重复", result);
	}

	/**
	 * 速度测试
	 * 某次记录: i7 6600U 生成2^20个id用时 497ms
	 */
	@Test()
	public void test1() {
		int num = 1 << 20;
		SnowFlake snowFlake = new SnowFlake(0, 0);
		List<String> ids = new ArrayList<String>(num);
		long start = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			ids.add(snowFlake.nextID(16));
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}

}
