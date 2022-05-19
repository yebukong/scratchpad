package pers.mine.scratchpad.other;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.mine.scratchpad.StringX;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MineTest {
    private static final Logger LOG = LoggerFactory.getLogger(MineTest.class);

    @Test
    public void test() {
        int a = (Integer) null;
        System.out.println(a);
    }

    @Test
    public void concurrentHashMapTest() {
        Map<String, String> map = new ConcurrentHashMap<>(10, 0.75f, 16);
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

    @Test
    public void test1() {
        System.out.println(toBinaryString(10));
        System.out.println(toBinaryString(10 >>> 1));
        System.out.println(toBinaryString(10 | (10 >>> 1)));


    }

    private String toBinaryString(int i) {
        String s = Integer.toBinaryString(i);
        return StringX.makeSameWidthPrefix(s, 32, "0");
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
}
