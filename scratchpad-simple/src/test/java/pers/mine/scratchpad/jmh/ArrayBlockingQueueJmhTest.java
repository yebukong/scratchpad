package pers.mine.scratchpad.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@BenchmarkMode(Mode.Throughput)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayBlockingQueueJmhTest {
    private BlockingQueue<String> arrayQ;

    @Setup
    public void up() {
        arrayQ = new ArrayBlockingQueue<>(1000 * 1000);
    }

    @Benchmark
    @Group("Producer")
    @GroupThreads(1)
    public void producer() throws InterruptedException {
        arrayQ.offer("Event",100,TimeUnit.MILLISECONDS);
    }

    @Benchmark
    @Group("Consumer")
    @GroupThreads(1)
    public String consumer() throws InterruptedException {
        return arrayQ.poll(100,TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ArrayBlockingQueueJmhTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
