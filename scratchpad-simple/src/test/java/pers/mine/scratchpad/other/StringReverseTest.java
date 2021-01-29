package pers.mine.scratchpad.other;

import org.junit.Test;

/**
 * java 反转字符串的几种写法
 * @author Mine
 * @date 2019/06/24 01:03:06
 */
public class StringReverseTest {
	String s = "ABCDE";
	/**
	 * StringBuffer
	 */
	@Test
	public void testA() {
		System.out.println(new StringBuffer(s).reverse());
	}
	
	/**
	 * StringBuilder
	 */
	@Test
	public void testB() {
		System.out.println(new StringBuilder(s).reverse());
	}
	
	/**
	 * toCharArray()
	 */
	@Test
	public void testC() {
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			System.out.print(chars[chars.length-1-i]);
		}
		System.out.println();
	}
	/**
	 * charAt()
	 */
	@Test
	public void testD() {
		int length = s.length();
		for (int i = 0; i < length; i++) {
			System.out.print(s.charAt(length-1-i));
		}
		System.out.println();
	}
	
	/**
	 */
	@Test
	public void showChars() {
		char cs[] = {'a','b','c'};
		String[] ss = {"a","b"};
		System.out.println(cs);
		System.out.print(cs);
		System.out.println();
		System.out.println(ss);
		System.out.print(ss);


	}
}
