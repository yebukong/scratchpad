package pers.mine.scratchpad.jmh;

import org.apache.hadoop.io.Text;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
//@Measurement(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
//@Fork(3)
@OutputTimeUnit(TimeUnit.SECONDS)
public class String2BytesTest {
    private static final String s = "hello world!";

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(String2BytesTest.class.getSimpleName())
                .output("./Benchmark.log")
                .build();
        new Runner(options).run();

    }

    //    @Benchmark
    public void testString() {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        print(bytes);
    }

    //    @Benchmark
    public void testText() {
        Text text = new Text(s);
        byte[] bytes = text.getBytes();
        print(bytes);
    }

    private void print(Object a) {
    }

    private List<Object> arr1 = null;
    private List<Object> arr2 = null;

    private Object[] a1 = null;
    private Object[] a2 = null;

    private String str1 = null;
    private String str2 = null;

    private Object[] a0 = null;

    @Setup
    public void setup() {
        Object[] arr = new Object[15];
        StringBuilder s0 = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            String randomString = "Arrays.asList(ArandomString";
            arr[i] = randomString;
            s0.append(randomString);
        }
        a0 = arr;
        a1 = Arrays.copyOf(arr, 15);
        a2 = Arrays.copyOf(arr, 15);
        arr1 = Arrays.asList(Arrays.copyOf(arr, 15));
        arr2 = Arrays.asList(Arrays.copyOf(arr, 15));
        str1 = new String(s0.toString().toCharArray());
        str2 = new String(s0.toString().toCharArray());
        System.out.println("setup");
    }

    @Benchmark
    public boolean arrsyEqualsTest() {
        Object[] a1 = Arrays.copyOf(a0, 15);
        return Arrays.equals(a1, a0);
    }

    @Benchmark
    public boolean listEqualsTest() {
        List<Object> arr1 = this.arr1;
        List<Object> arr2 = Arrays.asList(Arrays.copyOf(a0, 15));
        return arr1.equals(arr2);
    }

    @Benchmark
    public boolean strEqualsTest() {
        String str1 = this.str1;
        StringBuilder s1 = new StringBuilder();
        for (Object o : a0) {
            s1.append(o.toString());
        }
        String str2 = s1.toString();
        return str1.equals(str2);
    }

}
