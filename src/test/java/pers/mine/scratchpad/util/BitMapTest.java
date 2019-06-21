package pers.mine.scratchpad.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitMapTest {

	@Test
	public void testBitMap() {
		BitMap bm = new BitMap(1000);
		bm.put(63);
		bm.put(31);
		
		for (int i = 11; i < 30; i++) {
			bm.put(i);
		}
		for (int i = 11; i < 30; i++) {
			bm.clean(i);
		}
		System.out.println(bm.getBitLen());
		System.out.println(bm.get(1000));
		String tem = bm.toBinaryString();
		System.out.println(tem);
		System.out.println(tem.length());
	}

	@Test
	public void testPut() {
		System.out.println(63/32);
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetIntegerBitValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIntegerBitValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBitLen() {
		fail("Not yet implemented");
	}

}
