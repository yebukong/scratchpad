package pers.mine.scratchpad.other;

/**
 * 
 * @author Mine
 * @date 2019/07/10 20:41:02
 */
public class MetaSpaceTest {
	private static final long[] ls1 = new long[999999999];
	private static final long[] ls2 = new long[999999999];
	private static final long[] ls3 = new long[999999999];
	private static final long[] ls4 = new long[999999999];
	private static final long[] ls5 = new long[999999999];
	private static final long[] ls6 = new long[999999999];

	public static void main(String[] args) {
		// 元空间验证
		{
			new MetaSpaceTest();
//Java HotSpot(TM) 64-Bit Server VM 18.3 [堆溢出，说明当前实现中元空间在堆区]
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

}
