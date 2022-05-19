package pers.mine.scratchpad.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
public class JmhTest {
    BlockingQueue<String> arrayQ = null;
    BlockingQueue<String> linkQ = null;

    @Setup
    public void setup() {
        arrayQ = new ArrayBlockingQueue<>(1000 * 1000);
        linkQ = new LinkedBlockingQueue<>(1000 * 1000);
    }

    @Benchmark
    @Measurement(iterations = 5)
    public void arrayBlockingQueueTest() throws InterruptedException {
        arrayQ.put("A");
        arrayQ.take();
    }

    @Benchmark
    @Measurement(iterations = 5)
    public void linkedBlockingQueueTest() throws InterruptedException {
        linkQ.put("A");
        linkQ.take();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
