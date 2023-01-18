package pers.mine.scratchpad.jmh;

import org.apache.hadoop.io.Text;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.charset.StandardCharsets;

@BenchmarkMode(Mode.Throughput)
public class String2BytesTest {
    private static final String s = "hello world!";

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(String2BytesTest.class.getSimpleName())
                .output("./Benchmark.log")
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public void testString() {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        print(bytes);
    }

    @Benchmark
    public void testText() {
        Text text = new Text(s);
        byte[] bytes = text.getBytes();
        print(bytes);
    }

    private void print(Object a) {
    }
}
