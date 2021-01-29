package pers.mine.scratchpad.leetcode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FibonacciTest {
	private Fibonacci fi;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		fi = new Fibonacci();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testGetValue1() {
		for (int i = 1; i < 10; i++) {
			System.out.print(fi.getValue1(i)+"、");
		}
	}

	@Test
	public void testGetValue2() {
		for (int i = 1; i < 10; i++) {
			System.out.print(fi.getValue2(i)+"、");
		}
	}

	@Test
	public void testCaclSum1() {
		for (int i = 1; i < 10; i++) {
			System.out.print(fi.caclSum1(i)+"、");
		}
	}

	@Test
	public void testCaclSum2() {
		for (int i = 1; i < 10; i++) {
			System.out.print(fi.caclSum2(i)+"、");
		}
	}

	@Test
	public void testCaclSum3() {
		for (int i = 1; i < 10; i++) {
			System.out.print(fi.caclSum3(i)+"、");
		}
	}

}
