package pers.mine.scratchpad.base.thread;

import java.util.Map;

import org.junit.Test;

/**
 * 线程相关
 * @author Mine
 * @date 2019/07/01 20:00:12
 */
public class ThreadTest {
	/**
	 * 获取当前所有线程
	 */
	@Test
	public void showThreads(){
		Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
		int activeCount = Thread.activeCount();//当前活动线程数
		System.out.println(activeCount);
		
		for (Thread t : allStackTraces.keySet()) {
			System.out.println(t.getName());
		}
	}
	
	@Test
	public void showThreadsInfo(){
		Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();                                                                            
		for (Thread t : allStackTraces.keySet()) {
			System.out.println(t.getName());
			StackTraceElement[] stackTraceElements = allStackTraces.get(t);
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				System.out.println(stackTraceElement.getClassName());
			}
		}
	}
	
	/**
	 * 另一种获取方式：通过线程组
	 *  线程组相关:https://www.cnblogs.com/noteless/p/10354721.html
	 */
	@Test
	public void showThreadsX(){
		ThreadGroup currTg = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = currTg;  
		// 遍历线程组树，获取根线程组  
		while (currTg != null) {
		    topGroup = currTg;
		    currTg = currTg.getParent();
		} 
		// 激活的线程数加倍
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		// 枚举根线程组的所有线程 
		int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		System.out.println("Thread list size == " + list.length);
		for (Thread thread : list) {
		    System.out.println(thread.getName());
		}
	}
}
