package pers.mine.scratchpad.other;

import org.junit.Test;

import pers.mine.scratchpad.util.BitMap;

public class IntegerTest {
	@Test
	public void moveBitTest() {
		Integer x = 6;
		System.out.println(Integer.toBinaryString(x));
		System.out.println((x >>> 0) & 1);
		System.out.println(1&1);
		System.out.println(1&0);
		System.out.println(0&0);
		System.out.println(1|1);
		System.out.println(1|0);
		System.out.println(0|0);
		System.out.println(6&3);
		System.out.println(Integer.toBinaryString(-1-4));
		System.out.println(1<<2);
		System.out.println(Integer.toBinaryString(6|1));
		System.out.println(Integer.toBinaryString(5|2));

	}
	@Test
	public void  bitMapTest() {
		System.out.println(Integer.toBinaryString(10086));
		System.out.println(BitMap.getIntegerBitValue(10086, 0));
		int newValue = BitMap.setIntegerBitValue(10086, 31, 1);
		System.out.println(Integer.toBinaryString(newValue));
	}
}
