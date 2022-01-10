package pers.mine.scratchpad.util;

import cn.hutool.core.math.MathUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

public class BitMapTest {
    private static final Logger LOG = LoggerFactory.getLogger(BitMapTest.class);

    private Object test;

    @Test
    public void testBitMap() {
        System.out.println(test);
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
        System.out.println(63 / 32);
    }

    @Test
    public void testGet() {
        fun(5);
        fun(-5);
        System.out.println(5 << 2);
        fun(5 << 2);
        System.out.println(-5 << 2);
        fun(-5 << 2);

        fun(5);
        System.out.println(5 >>> 2);
        fun(5 >>> 2);
        System.out.println(5 >> 2);
        fun(5 >> 2);

        fun(-5);
        fun(-5 >>> 2);
        System.out.println(-5 >>> 2);
        fun(-5 >> 2);
        System.out.println(-5 >> 2);

    }

    void fun(int i) {
        String tmp = Integer.toBinaryString(i);
        String tmp2 = "";
        if (tmp.length() < 32) {
            tmp2 = StringX.dup("0", 32 - tmp.length());
        }
        System.out.println(tmp2 + tmp);
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

    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            LOG.info("{},{} -> {}", 2, i, Math.pow(2, i));
        }
    }


}
