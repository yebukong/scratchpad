package pers.mine.scratchpad.jmh;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
//@Measurement(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
//@Fork(3)
@OutputTimeUnit(TimeUnit.SECONDS)
public class ArrayCopyTest {
    public static final String[] KEY_SCHEMA = {
            "dat:STRING"
            , "explore_id:INT"
            , "vendor_id:INT"
            , "order_id:INT"
            , "adx_slot_id:STRING"
            , "ftx_slot_id:STRING"
            , "os_version:LONG"
            , "brand:LONG"
            , "package_name:STRING"
    };
    Object[] baseArray = new Object[]{"2023-11-11", 0, 1024, 120, "slot_1", "ftx_slot_2", 1000L, 200L, "com.cn.baidu"};

    public static void main(String[] args) throws RunnerException {
        Class<?> clazz = MethodHandles.lookup().lookupClass();
        String fileName = StrUtil.format("jmh-{}-{}.log", clazz.getSimpleName(), DateUtil.format(new Date(), "yyyyMMddHHmm"));
        String path = StrUtil.join(File.separator, System.getProperty("user.dir"), "jmh", fileName);
        Options options = new OptionsBuilder()
                .include(clazz.getSimpleName())
                .output(path)
                .build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        System.out.println("1 - " + Arrays.toString(baseArray.clone()));
        Object[] cloneKeys = new Object[KEY_SCHEMA.length];
        System.arraycopy(baseArray, 0, cloneKeys, 0, KEY_SCHEMA.length);
        System.out.println("2 - " + Arrays.toString(cloneKeys));
    }

    @Benchmark
    public Object[] objectClone() {
        return baseArray.clone();
    }

    @Benchmark
    public Object[] arrayClone() {
        Object[] cloneKeys = new Object[KEY_SCHEMA.length];
        System.arraycopy(baseArray, 0, cloneKeys, 0, KEY_SCHEMA.length);
        return cloneKeys;
    }
}
