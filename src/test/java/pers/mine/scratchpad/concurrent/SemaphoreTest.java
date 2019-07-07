package pers.mine.scratchpad.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 
 * Semaphore['sɛməfɔr] 
 * 信号量
 * @author Mine
 * @date 2019/07/06 07:41:02
 */
public class SemaphoreTest {
	@Test
	public  void test() {
		//并发线程数控制
		Semaphore smp = new Semaphore(3, false);//是否使用公平锁
		//模拟6个人抢3个车位
		for (int i = 0; i < 6; i++) {
			new Thread(()->{
				try {
					// [ə'kwaɪr]
					smp.acquire();
					System.out.println(Thread.currentThread().getName()+"\t抢占");
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (Exception e) {
					}
					System.out.println(Thread.currentThread().getName()+"\t释放");
				}catch (Exception e) {
					// TODO: handle exception
				} finally {
					smp.release();
				}
			}).start();
		}
		
		
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (Exception e) {
		}
	}
}
