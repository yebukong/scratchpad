package pers.mine.scratchpad.reflect;

import cn.hutool.core.util.ClassUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 交换两个Integer数值,反射，int pool，自动拆箱，装箱
 * -XX:AutoBoxCacheMax=size
 */
public class IntegerSwapTest {
    private static final Logger LOG = LoggerFactory.getLogger(IntegerSwapTest.class);

    @Test
    public void start() throws IllegalAccessException {
        logIntCache();
        Integer a = 1;
        Integer b = 2;
        LOG.info("start val: a={},b={}", a, b);
        logHashCode("origin", a, b);
        logHashCode("valueOf", Integer.valueOf(1), Integer.valueOf(2));
        logHashCode("newInteger", new Integer(1), new Integer(2));
        LOG.info("-----------------------start swap -----------------------");
        // swap(a, b);
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

    public static void logIntCache() throws IllegalAccessException {
        Class<?> integerCacheClass = Integer.class.getDeclaredClasses()[0];
        Field cacheField = ClassUtil.getDeclaredField(integerCacheClass, "cache");
        cacheField.setAccessible(true);
        //类静态字段的值获取
        Object cache = cacheField.get(null);
        LOG.info("integerCache.cache - {}", cache);
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

    /**
     * cache内值交换
     */
    private void test3(Integer a, Integer b) throws IllegalAccessException {
        Class<?> integerCacheClass = Integer.class.getDeclaredClasses()[0];
        Field cacheField = ClassUtil.getDeclaredField(integerCacheClass, "cache");
        cacheField.setAccessible(true);
        Field lowField = ClassUtil.getDeclaredField(integerCacheClass, "low");
        lowField.setAccessible(true);
        //类静态字段的值获取
        Integer[] cache = (Integer[]) cacheField.get(null);
        int low = (Integer) lowField.get(null);
        int ai = a.intValue();
        int bi = b.intValue();
        cache[a.intValue() + (-low)] = b;
        cache[b.intValue() + (-low)] = a;
    }

}
