package pers.mine.scratchpad.other;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.mine.scratchpad.StringX;

public class MineTest {
    private static final Logger LOG = LoggerFactory.getLogger(MineTest.class);

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

    private int hash(Object k) {
        int h = 0;

        if ((0 != h) && (k instanceof String)) {
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();

        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h << 15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h << 3);
        h ^= (h >>> 6);
        h += (h << 2) + (h << 14);
        return h ^ (h >>> 16);
    }

    private String toBinaryString(int i) {
        String s = Integer.toBinaryString(i);
        return StringX.makeSameWidthPrefix(s, 32, "0");
    }

    @Test
    public void test11() {
        int segmentShift = 28;
        int segmentMask = 16 - 1;
        String str = "testMask";
        for (int i = 0; i < 100; i++) {
            String s = str + i;
            int hash = hash(s);
            LOG.info("s : {}", s);
            LOG.info("hash : {}", hash);
            LOG.info("hash bit: {}", toBinaryString(hash));

            LOG.info("seg index: {}", (hash >>> segmentShift) & segmentMask);
            LOG.info("seg index bit: {}", toBinaryString((hash >>> segmentShift) & segmentMask));

            LOG.info("seg 2 index: {}", (hash >>> segmentShift));
            LOG.info("seg 2 index bit: {}", toBinaryString((hash >>> segmentShift)));

            System.out.println();
        }
    }
}
