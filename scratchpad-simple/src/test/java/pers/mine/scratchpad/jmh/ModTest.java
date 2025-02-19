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
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
//@Measurement(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
//@Fork(3)
@OutputTimeUnit(TimeUnit.SECONDS)
public class ModTest {
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

    long s = 0;

    @Setup
    public void setup() {
        s = RandomUtil.randomLong(100000, 2000000);
        System.out.println("s=" + s);
    }

    @Benchmark
    public long mod() {
        return s % 1000;
    }

    @Benchmark
    public long modBin() {
        return s % 1024;
    }
}
