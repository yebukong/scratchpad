package pers.mine.scratchpad.base.concurrent;

/**
 * wait,notify和notifyAll要与synchronized一起使用
 * Object.wait(),Object.notify(),Object.notifyAll()都是Object的方法，换句话说，就是每个类里面都有这些方法。
 * Object.wait()：释放当前对象锁，并进入阻塞队列
 * Object.notify()：唤醒当前对象阻塞队列里的任一线程（并不保证唤醒哪一个）
 * Object.notifyAll()：唤醒当前对象阻塞队列里的所有线程
 * 为什么这三个方法要与synchronized一起使用呢？解释这个问题之前，我们先要了解几个知识点
 * 每一个对象都有一个与之对应的监视器
 * 每一个监视器里面都有一个该对象的锁和一个等待队列和一个同步队列
 * wait()方法的语义有两个，一是释放当前对象锁，另一个是进入阻塞队列，可以看到，这些操作都是与监视器相关的，当然要指定一个监视器才能完成这个操作了
 * notify()方法也是一样的，用来唤醒一个线程，你要去唤醒，首先你得知道他在哪儿，所以必须先找到该对象，也就是获取该对象的锁，当获取到该对象的锁之后，才能去该对象的对应的等待队列去唤醒一个线程。值得注意的是，只有当执行唤醒工作的线程离开同步块，即释放锁之后，被唤醒线程才能去竞争锁。
 * notifyAll()方法和notify()一样，只不过是唤醒等待队列中的所有线程
 * 因wait()而导致阻塞的线程是放在阻塞队列中的，因竞争失败导致的阻塞是放在同步队列中的，notify()/notifyAll()实质上是把阻塞队列中的线程放到同步队列中去
 * @author xqwang
 * @since 2019年6月25日
 */
public class WaitTest {
    public static class WaitRunnable implements Runnable {
        Object syncObj = null;
        String name = "";
        public  WaitRunnable(String name,Object syncObj){
            this.name = name;
            this.syncObj = syncObj;
        }
        public void run() {
            synchronized (syncObj) {//竞争失败导致的阻塞是放在同步队列
                try {
                    System.out.println(name+":我需要等syncObj的一些东西");
                    syncObj.wait();//释放 syncObj 锁，且线程进入阻塞队列
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println(name);
            }
           
        }
    }
    
    public static class NotifyRunnable implements Runnable {
        Object syncObj = null;
        String name = "";
        public  NotifyRunnable(String name,Object syncObj){
            this.name = name;
            this.syncObj = syncObj;
        }
        public void run() {
            synchronized (syncObj) {
                System.out.println(name+":等我三秒");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                syncObj.notifyAll();//notify()/notifyAll()实质上是把阻塞队列中的线程放到同步队列中去
                System.out.println(name+":OK");
            }
        }
    }
    
    public static void main(String[] args) {
        Object ob = new Object();
        Thread threadA = new Thread(new WaitRunnable("A", ob));
        Thread threadB = new Thread(new NotifyRunnable("B", ob));
        threadA.start();
        threadB.start();
    }
}
