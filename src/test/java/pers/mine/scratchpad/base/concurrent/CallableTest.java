package pers.mine.scratchpad.base.concurrent;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * Callable 相关
 * @author Mine
 * @date 2019/07/08 18:22:14
 */
public class CallableTest {
	@Test
	public void test() throws InterruptedException, ExecutionException {
		FutureTask<String> futureTask = new FutureTask<String>(()->{
			System.out.println("开始执行...");
			TimeUnit.SECONDS.sleep(2);
			return UUID.randomUUID().toString().replace("-", "");
		});
		Thread t = new Thread(futureTask);
		t.start();
		System.out.println("等待返回值");
		String result = futureTask.get();
		System.out.println(result);
	}
	
	
	@Test
	public void test1() throws InterruptedException, ExecutionException {
		FutureTask<String> futureTask = new FutureTask<String>(new CallOne());
		Thread t = new Thread(futureTask);
		t.start();
		System.out.println("等待返回值");
		while (!futureTask.isDone()) {
		}
		String result = futureTask.get();
		System.out.println(result);
	}
	
	
	@Test
	public void test2() throws InterruptedException, ExecutionException {
		FutureTask<String> futureTask = new FutureTask<String>(new CallOne());
		new Thread(futureTask).start();
		new Thread(futureTask).start();//无法同时放入两个线程
		System.out.println("等待返回值");
		while (!futureTask.isDone()) {
		}
		String result = futureTask.get();
		System.out.println(result);
	}
	
	class CallOne implements Callable<String>{
		public String call() throws Exception {
			TimeUnit.SECONDS.sleep(2);
			System.out.println(Thread.currentThread().getName());
			return UUID.randomUUID().toString().toUpperCase().replace("-", "");
		}
	}
}
