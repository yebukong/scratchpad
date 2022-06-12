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
    RefJmhTest t = null;
    Method method = null;
    MethodHandle methodMH = null;
    Object[] args = {1};

    @Setup
    public void setup() throws NoSuchMethodException, IllegalAccessException {
        t = new RefJmhTest();
        method = RefJmhTest.class.getDeclaredMethod("target", Integer.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(void.class, Integer.class);
        methodMH = lookup.findVirtual(RefJmhTest.class, "target", mt).bindTo(t);
    }

    @Benchmark
    public void test1() {
        t.target(1);
    }

    @Benchmark
    public void test2() throws InvocationTargetException, IllegalAccessException {
        method.invoke(t, args);
    }

    @Benchmark
    public void test3() throws Throwable {
        methodMH.invoke(1);
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
