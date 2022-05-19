package pers.mine.scratchpad.base.concurrent;

import org.junit.Test;

public class ConcurrentHashMapTest {
    static final int MAXIMUM_CAPACITY = 1 << 30;

    @Test
    public void test() {
        int a = (Integer) null;
        System.out.println(a);
    }

    @Test
    public void test2() {
        System.out.println(tableSizeFor(10));
        System.out.println(roundUpToPowerOf2(10));
        System.out.println(roundUpToPowerOf2(10));

        System.out.println(tableSizeFor(16));
        System.out.println(roundUpToPowerOf2(16));
        System.out.println(roundUpToPowerOf2(16));

        System.out.println(Integer.highestOneBit(10));
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        int rounded = number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (rounded = Integer.highestOneBit(number)) != 0
                ? (Integer.bitCount(number) > 1) ? rounded << 1 : rounded
                : 1;

        return rounded;
    }

    static int roundUpToPowerOf2x(int number) {
        // assert number >= 0 : "number must be non-negative";
        return number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : number > 1 ? Integer.highestOneBit((number - 1) << 1)
                : 1;
    }

    @Test
    public void testBit() {
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(100));
        System.out.println(Integer.toBinaryString(-1 >>> 16));
        System.out.println(Integer.toBinaryString(-1 >> 31));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE >>> 4));
    }
}
