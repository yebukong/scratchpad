package pers.mine.scratchpad.base.trycatch;

import org.junit.Test;

/**
 * @author mine
 * @description trycatch 处理顺序测试
 * @create 2020-07-29 11:27
 */
public class TryCatchTest {

    @Test
    public void tryTest() {
        System.out.println(0);
        System.out.flush();
        try {
            System.out.println(1);
            System.out.flush();
            System.out.println(1 / 0);
            System.out.println("x");
        } catch (Exception e) {
            System.out.println(2);
            System.out.flush();
            throw new RuntimeException("catch", e);
        } finally {
            System.out.println(3);
            System.out.flush();
            throw new RuntimeException("finally");
        }
    }
}
