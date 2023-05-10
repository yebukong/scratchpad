package pers.mine.scratchpad;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import sun.reflect.Reflection;

import java.lang.invoke.MethodHandles;

/**
 * 回调验证
 */
public class CallTest {

    @Test
    public void stackCallTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            System.out.println(StrUtil.format("{}.{}:{}",element.getClassName(),stackTrace[0].getMethodName(),stackTrace[0].getLineNumber()));
        }
    }

    @Test
    public void lookupCallTest() {
        Class<?> aClass = MethodHandles.lookup().lookupClass();
        System.out.println(aClass);
        System.out.println(MethodHandles.lookup().toString());
        //System.out.println(Reflection.getCallerClass());
    }
}
