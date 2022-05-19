package pers.mine.scratchpad.other;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.FieldLayout;

/**
 * @author wangxiaoqiang
 * @description TODO
 * @create 2022-02-21 11:21
 */
public class NumberTest {

    @Test
    public void printInt() {
        //Integer
        System.out.println(ClassLayout.parseInstance(2).toPrintable());
        for (FieldLayout field : ClassLayout.parseInstance(2).fields()) {
            System.out.println(field);
        }
    }
}
