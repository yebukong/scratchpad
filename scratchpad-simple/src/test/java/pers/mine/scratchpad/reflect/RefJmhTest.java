package pers.mine.scratchpad.reflect;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射性能优化
 */
@BenchmarkMode({Mode.Throughput})
@State(Scope.Thread)
public class RefJmhTest {
    static final RefJmhTest t = new RefJmhTest();
    Method method = null;
    MethodHandle methodMH = null;
    Object[] args = {1};
    static final Integer i = 1;

    final static MethodHandle methodMH1;

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(void.class, Integer.class);
        try {
            methodMH1 = lookup.findVirtual(RefJmhTest.class, "target", mt).bindTo(t);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Setup
    public void setup() throws NoSuchMethodException, IllegalAccessException {
        method = RefJmhTest.class.getDeclaredMethod("target", Integer.class);
        method.setAccessible(true);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(void.class, Integer.class);
        methodMH = lookup.findVirtual(RefJmhTest.class, "target", mt).bindTo(t);
    }

    @Benchmark
    public void nativeCallTest() {
        t.target(i);
    }

    @Benchmark
    public void refInvoke() throws InvocationTargetException, IllegalAccessException {
        method.invoke(t, args);
    }

    @Benchmark
    public void methodHandleTest() throws Throwable {
        methodMH.invokeExact(i);
    }

    @Benchmark
    public void methodHandleTest1() throws Throwable {
        methodMH1.invokeExact(i);
    }

    public void target(Integer a) {
        a.intValue();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RefJmhTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();

    }
}
