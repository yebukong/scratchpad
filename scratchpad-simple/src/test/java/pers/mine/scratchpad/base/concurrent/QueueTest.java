package pers.mine.scratchpad.base.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * 队列
 *
 * @author Mine
 * @date 2019/07/06 08:11:27
 */
public class QueueTest {
    private static final Logger LOG = LoggerFactory.getLogger(QueueTest.class);

    /**
     * 数组结构组成的有界阻塞队列
     *
     * @throws InterruptedException
     */
    @Test
    public void arrayBlockungQueueList() throws InterruptedException {
        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<String>(5);
        abq.add("A");
        for (int i = 0; i < 10; i++) {
            // add方法在添加元素的时候，若超出了度列的长度会直接抛出异常：java.lang.IllegalStateException: Queue full
            // System.out.println(abq.add("A"));
            // offer方法在添加元素时，如果发现队列已满无法添加的话，会直接返回false。
            // System.out.println(abq.offer("A"));
            // put 无返回值，若向队尾添加元素的时候发现队列已经满了会发生阻塞一直等待空间，以加入元素。
            // abq.put("A");
        }
        // remove:若队列为空，抛出NoSuchElementException异常。
        System.out.println(abq.remove());
        // poll: 若队列为空，返回null。
        System.out.println(abq.poll());
        // take:若队列为空，发生阻塞，等待有元素。
        System.out.println(abq.take());
        // peek 队首，不出队
        System.out.println(abq.peek());
        // element 队首，不出队，没有则抛异常
        System.out.println(abq.element());

    }

    /**
     * 链表结构组成的有界（但默认大小为Integer.MaxVlaue）阻塞队列
     *
     * @throws InterruptedException
     */
    @Test
    public void linkedBlockingQueuetest() throws InterruptedException {
        LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<String>(5);
        lbq.add("A");
        lbq.remove();

        lbq.offer("A");
        lbq.poll();

        lbq.put("A");
        lbq.take();

    }

    /**
     * 链表结构组成的有界（但默认大小为Integer.MaxVlaue）阻塞队列
     *
     * @throws InterruptedException
     */
    @Test
    public void linkedBlockingDequetest() throws InterruptedException {
        LinkedBlockingDeque<String> lbd = new LinkedBlockingDeque<String>(5);
        lbd.add("A"); // addLast
        lbd.addFirst("A");
        lbd.addLast("A");
        lbd.remove();// removeFirst
        lbd.removeFirst();
        lbd.removeLast();

        lbd.offer("A"); // offerLast
        lbd.offerFirst("A");
        lbd.offerLast("A");
        lbd.poll(); // pollFirst
        lbd.pollFirst();
        lbd.pollLast();
//		
        lbd.put("A"); // putLast
        lbd.putFirst("A");
        lbd.putLast("A");
        lbd.take(); // takeFirst
        lbd.takeFirst();
        lbd.takeLast();

    }

    /**
     * 一个支持优先级的无界阻塞队列。默认情况下元素采用自然顺序升序排序，当然我们也可以通过构造函数来指定Comparator来对元素进行排序。
     *
     * @throws InterruptedException
     */
    @Test
    public void priorityBlockingQueue() throws InterruptedException {
        // 构造方法表示初始大小
        PriorityBlockingQueue<String> q = new PriorityBlockingQueue<String>(5, (x, y) -> {
            // 元素比较方法
            return x.compareTo(y);
        });
        q.offer("b");
        q.offer("D");
        q.offer("a");
        q.offer("1");
        String data;
        while (null != (data = q.poll())) {
            LOG.info(data);
        }
    }

    /**
     * DelayQueue 优先级排序的
     * 无界阻塞队列，其中的对象只能在其到期时才能从队列中取走。这种队列是有序的，即队头对象的延迟到期时间最长。注意：不能将null元素放置到这种队列中。
     *
     * @throws InterruptedException
     */
    @Test
    public void delayQueueTest() throws InterruptedException {
        DelayQueue<Delayed> q = new DelayQueue<Delayed>();

        q.offer(MyDelayed.of("b", 2));
        q.offer(MyDelayed.of("D", 5));
        q.offer(MyDelayed.of("a", 3));
        q.offer(MyDelayed.of("1", 1));
        Delayed data;
        LOG.info("start");
        while (null != (data = q.poll(15, TimeUnit.SECONDS))) {
            // compareTo 影响poll 顺序，getDelay影响等待时间
            LOG.info("{}", data);
        }
    }

    static class MyDelayed implements Delayed {
        public long time;
        public String name;
        public long createTime;

        private MyDelayed(long time, String name) {
            this.time = time;
            this.name = name;
            this.createTime = System.currentTimeMillis();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return createTime + TimeUnit.SECONDS.toMillis(time) - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            MyDelayed item = (MyDelayed) o;
            return name.compareTo(item.name);
        }

        public static MyDelayed of(String name, long time) {
            return new MyDelayed(time, name);
        }

        @Override
        public String toString() {
            return "MyDelayed{" +
                    "time=" + time +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void synchronousQueue() throws InterruptedException {
        BlockingQueue<String> bq = new SynchronousQueue<String>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " | put 1");
                bq.put("1");
                System.out.println(Thread.currentThread().getName() + " | put 2");
                bq.put("2");
                System.out.println(Thread.currentThread().getName() + " | put 3");
                bq.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " | take " + bq.take());
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " | take " + bq.take());
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " | take " + bq.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        TimeUnit.SECONDS.sleep(20);

    }

    /**
     * 简单生产者消费者模型
     */
    @Test
    public void queueTest() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
        Thread main = Thread.currentThread();
        //生产者
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    TimeUnit.SECONDS.sleep(2);
                    LOG.info("offer {} -> {}", i, queue.offer(String.valueOf(i), 1, TimeUnit.SECONDS));
                }
                LOG.info("生产者退出");
            } catch (InterruptedException e) {
                LOG.error("err", e);
            }
        }, "生产者").start();
        new Thread(() -> {
            try {
                String data = null;
                while (null != (data = queue.poll(5, TimeUnit.SECONDS))) {
                    LOG.info("poll {}", data);
                }
                LOG.info("消费者退出，唤醒主线程");
                LockSupport.unpark(main);
            } catch (InterruptedException e) {
                LOG.error("err", e);
            }
        }, "消费者").start();
        LOG.info("阻塞主线程");
        LockSupport.park(this);
        TimeUnit.SECONDS.sleep(1);
        LOG.info("主线程退出");
    }
}
