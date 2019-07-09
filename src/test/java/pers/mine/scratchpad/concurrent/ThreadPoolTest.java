package pers.mine.scratchpad.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 线程池相关
 * 
 * @author Mine
 * @date 2019/07/08 19:35:06
 */
public class ThreadPoolTest {
	@Test
	public void test() {
		// 可获取处理器次数
		System.out.println(Runtime.getRuntime().availableProcessors());
		Properties properties = System.getProperties();
		Set<Object> keySet = properties.keySet();
		for (Object object : keySet) {
			System.out.println(object + "\t" + properties.getProperty(object.toString()));
		}
	}

	@Test
	public void test1() throws InterruptedException {
		// ThreadPoolExecutor
		//ExecutorService tp = Executors.newFixedThreadPool(4);//请求队列最大数Integer.MAX_VALUE,可能导致OOM
		//ExecutorService tp = Executors.newSingleThreadExecutor();//请求队列最大数Integer.MAX_VALUE,可能导致OOM
		ExecutorService tp = Executors.newCachedThreadPool();//最大线程数Integer.MAX_VALUE,可能导致OOM

		// 模拟10个用户办理业务
		try {
			for (int i = 0; i < 10; i++) {
				int tmp = i;
				// submit和execute都是 ExecutorService 的方法，都是添加线程到线程池中。
				// 接收的参数不一样 submit有返回值，而execute没有
				tp.execute(() -> {
					System.out.println(Thread.currentThread().getName()+" 只执行， " +tmp+" 没有返回值 "+System.nanoTime());
				});
			}
		} finally {
			System.out.println(System.nanoTime());
			tp.shutdown();// 在终止前允许执行以前提交的任务
			// tp.shutdownNow();//shutdownNow()方法调用后，线程池会通过调用worker线程的interrupt方法尽最大努力(best-effort)去"终止"已经运行的任务。而对于那些在堵塞队列中等待执行的任务，线程池并不会再去执行这些任务，而是直接返回这些等待执行的任务，也就是该方法的返回值
		}
		TimeUnit.SECONDS.sleep(3);
	}
	
	@Test
	public void test2() throws InterruptedException, ExecutionException {
		ExecutorService tp = Executors.newSingleThreadExecutor();

		try {
				// submit和execute都是 ExecutorService 的方法，都是添加线程到线程池中。
				// 接收的参数不一样 submit有返回值，而execute没有
				Future<String> submit = tp.submit(() -> {
					System.out.println(Thread.currentThread().getName()+" | "+System.nanoTime());
					TimeUnit.SECONDS.sleep(2);
					return "123";
				});
				String string = submit.get();
				System.out.println("get "+System.nanoTime() +" "+ string);
				
				//lambda自动推断实现的接口
				Future<?> submit2 = tp.submit(() -> {
					System.out.println(Thread.currentThread().getName()+" | "+System.nanoTime());
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				Object o = submit2.get();
				System.out.println("get2 "+System.nanoTime() +" "+ o);
				//特殊方式传递Runnable 返回值
				List<String> result = new CopyOnWriteArrayList<String>();
				Future<List<String>> submit3 = tp.submit(new RunnableOne(result),result);
				List<String> rs = submit3.get();
				System.out.println("get2 "+System.nanoTime() +" "+ rs);
				
		} finally {
			System.out.println(System.nanoTime());
			tp.shutdown();
		}
		TimeUnit.SECONDS.sleep(10);
	}
}

class RunnableOne implements  Runnable{
	List<String> result = null;
	public RunnableOne(List<String> result) {
		this.result = result;
	} 
	public void run() {
		System.out.println(Thread.currentThread().getName()+" | "+System.nanoTime());
		try {
			result.add("返回值");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
