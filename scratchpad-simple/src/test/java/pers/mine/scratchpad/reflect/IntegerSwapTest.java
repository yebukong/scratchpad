package pers.mine.scratchpad.reflect;

import cn.hutool.core.util.ClassUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 交换两个Integer数值,反射，int pool，自动拆箱，装箱
 */
public class IntegerSwapTest {
    private static final Logger LOG = LoggerFactory.getLogger(IntegerSwapTest.class);

    @Test
    public void start() throws IllegalAccessException {
        Integer a = 1;
        Integer b = 2;
        LOG.info("start val: a={},b={}", a, b);
        logHashCode("origin", a, b);
        logHashCode("valueOf", Integer.valueOf(1), Integer.valueOf(2));
        logHashCode("newInteger", new Integer(1), new Integer(2));
        LOG.info("-----------------------start swap -----------------------");
        swap2(a, b);
        LOG.info("end   val: a={},b={}", a, b);
        logHashCode("swap", a, b);
        logHashCode("valueOf", Integer.valueOf(1), Integer.valueOf(2));
        //交换过后缓存池出现混乱,导致拆箱装箱错误
        LOG.info("pool val check: a={},b={}", Integer.valueOf(1), Integer.valueOf(2));
        LOG.info("pool val check: a={},b={}", 1, 2);
    }

    public static void logHashCode(String tag, Integer a, Integer b) {
        LOG.info("HashCode {}: a={},b={}", tag, System.identityHashCode(a), System.identityHashCode(b));
    }

    private void swap(Integer a, Integer b) throws IllegalAccessException {
        logHashCode("swap", a, b);
        Field value = ClassUtil.getDeclaredField(Integer.class, "value");
        value.setAccessible(true);
        //Integer tmp = a;
        //int tmp=a.intValue();
        Integer tmp = new Integer(a);

        value.set(a, b);
        value.set(b, tmp);
    }

    private void swap2(Integer a, Integer b) throws IllegalAccessException {
        logHashCode("swap", a, b);
        Field value = ClassUtil.getDeclaredField(Integer.class, "value");
        value.setAccessible(true);
        int tmp = a.intValue();
        value.setInt(a, b);
        value.setInt(b, tmp);
    }
}
