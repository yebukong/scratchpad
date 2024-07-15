package pers.mine.scratchpad.base.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    private static final Logger LOG = LoggerFactory.getLogger(CompletableFutureTest.class);

    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf = new CompletableFuture<>();

        new Thread(() -> {
            try {
                LOG.info("sleep");
                Thread.sleep(10 * 1000);
                LOG.info("complete start");
                cf.complete(100);
                LOG.info("complete end");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        cf.whenComplete((r, t) -> {
            LOG.info("whenComplete - {}", r, t);
        }).whenCompleteAsync((r, t) -> {
            LOG.info("whenCompleteAsync- {}", r, t);
        }).thenAcceptAsync((a) -> {
            LOG.info("thenApply- {} {}", a, a);
        });
        LOG.info("cf.get()");
        LOG.info("{}", cf.get());
    }
}
