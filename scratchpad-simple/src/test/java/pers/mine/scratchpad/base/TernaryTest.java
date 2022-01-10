package pers.mine.scratchpad.base;

import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 三元表达式 test
 * @create 2021-03-30 19:25
 */
public class TernaryTest {
    @Test(timeout = 100)
    @Before
    @Ignore
    public void test() {
        Map<String, Integer> map = new HashMap<>();
        Integer i = map != null ? map.get("a") : Integer.MAX_VALUE;
        System.out.println(i);
    }
}
