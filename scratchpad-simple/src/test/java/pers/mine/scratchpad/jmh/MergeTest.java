package pers.mine.scratchpad.jmh;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.FastDateFormat;
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
public class MergeTest {
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

    long[] longValue;
    long[] longValue2;

    List<Long> listValue;
    List<Long> listValue2;

    @Setup
    public void setup() {
        long[] arr = new long[15];
        StringBuilder s0 = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            arr[i] = RandomUtil.randomLong(1000);
        }
        longValue = arr;
        longValue2 = Arrays.copyOf(arr, 15);
        listValue = Arrays.asList(Arrays.stream(longValue).boxed().toArray(Long[]::new));
        listValue2 = Arrays.asList(Arrays.stream(longValue).boxed().toArray(Long[]::new));
    }

    @Benchmark
    public List<Long> listMerge() {
        int size = listValue.size();
        for (int i = 0; i < size; i++) {
            listValue.set(i, listValue.get(i) + listValue2.get(i));
        }
        return listValue;
    }

    @Benchmark
    public long[] longMerge() {
        int size = longValue.length;
        for (int i = 0; i < size; i++) {
            longValue[i] = longValue[i] + longValue2[i];
        }
        return longValue;
    }
}
