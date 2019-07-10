package pers.mine.scratchpad.other;

/**
 * 元空间
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
		new MetaSpaceTest();
//Java HotSpot(TM) 64-Bit Server VM 18.3 [堆溢出，说明当前实现中元空间在堆区]
//		Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//		at pers.mine.scratchpad.other.MetaSpaceTest.<clinit>(MetaSpaceTest.java:10)


	}
}
