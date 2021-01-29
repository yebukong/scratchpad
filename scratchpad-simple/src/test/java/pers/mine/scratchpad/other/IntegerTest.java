package pers.mine.scratchpad.other;

import org.junit.Test;

import pers.mine.scratchpad.util.BitMap;

public class IntegerTest {
	@Test
	public void moveBitTest() {
		Integer x = 6;
		System.out.println(Integer.toBinaryString(x));
		System.out.println((x >>> 0) & 1);
		System.out.println(1 & 1);
		System.out.println(1 & 0);
		System.out.println(0 & 0);
		System.out.println(1 | 1);
		System.out.println(1 | 0);
		System.out.println(0 | 0);
		System.out.println(6 & 3);
		System.out.println(Integer.toBinaryString(-1 - 4));
		System.out.println(1 << 2);
		System.out.println(Integer.toBinaryString(6 | 1));
		System.out.println(Integer.toBinaryString(5 | 2));

	}

	@Test
	public void bitMapTest() {
		System.out.println(Integer.toBinaryString(10086));
		System.out.println(BitMap.getIntegerBitValue(10086, 0));
		int newValue = BitMap.setIntegerBitValue(10086, 31, 1);
		System.out.println(Integer.toBinaryString(newValue));
	}

	@Test
	public void test1() {
		int a = 0;
		Integer aa = 0;
		int b = 0;
		int c = 0;
		for (int i = 0; i < 99; i++) {
			a = a++;
			aa = aa++;
			b = c++;
		}
		System.out.println(a); // a++ 准确表达应该是 先对a自增，再返回旧值
		System.out.println(aa);// 自动拆箱和自动装箱不影响结果
		System.out.println(b);
		System.out.println(c);
	}

	protected IntegerTest clone() throws CloneNotSupportedException {
		return (IntegerTest) super.clone();
	}

	@Test
	public void test2() {
		int i = 0;
		i = i++;
		int j = 0;
		j = i++;
		// int k = i + ++i * i++;
		System.out.println(i);
		System.out.println(j);

	}

	@Test
	public void test3() {
		System.out.println((1 + 5 / 2 + 2) > 5);
		System.out.println(5d / 2d);
		System.out.println(5 / 2);//两个int相除，取整除部分，忽略余数；
	}
}
