package pers.mine.scratchpad.other;

import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 */
public class MetaSpaceTest {
//	private static final long[] ls1 = new long[999999999];
//	private static final long[] ls2 = new long[999999999];
//	private static final long[] ls3 = new long[999999999];
//	private static final long[] ls4 = new long[999999999];
//	private static final long[] ls5 = new long[999999999];
//	private static final long[] ls6 = new long[999999999];

	@Test
	public void test() {
		// 元空间验证，
		{
			new MetaSpaceTest();
//Java HotSpot(TM) 64-Bit Server VM 18.3 [堆溢出，是因为数组的内存分配在堆中]
//		Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//		at pers.mine.scratchpad.other.MetaSpaceTest.<clinit>(MetaSpaceTest.java:10)
		}
		// 获取当前程序一些环境参数 bytes
		{
			// 返回JVM占用内存总量,此方法返回的值可能随时间的推移而变化，这取决于主机环境,如果设置了-Xms，则初始值为-Xms。
			long totalMemory = Runtime.getRuntime().totalMemory();
			// JVM试图使用的最大内存量。如果设置了-Xmx，为-Xmx。
			long maxMemory = Runtime.getRuntime().maxMemory();
			// JVM 空闲内存
			long freeMemory = Runtime.getRuntime().freeMemory();
			System.out.println(String.format("totalMemory(JVM内存占用量)：%s MB ", totalMemory / 1024 / 1024));
			System.out.println(String.format("maxMemory(JVM内存最大量)：%s MB ", maxMemory / 1024 / 1024));
			System.out.println(String.format("freeMemory(JVM内存空闲量)：%s MB ", freeMemory / 1024 / 1024));
		}
	}

	/**
	 * 启动前设置较小的元空间： -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=18m  -XX:+PrintGCDetails
	 */
	public static void main(String[] args) {
		int i = 0;
		try {
			while (true) {
				i++;
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(MetaSpaceTest.class);
				enhancer.setUseCache(false);
				enhancer.setCallback(new MethodInterceptor() {
					@Override
					public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3)
							throws Throwable {
						// TODO Auto-generated method stub
						return arg3.invoke(arg0, args);
					}
				});
				enhancer.create();
			}

		} catch (Throwable e) {
			System.out.println("i=" + i);
			e.printStackTrace();
		}
	}

}
