package pers.mine.scratchpad.base.thread;

/**
 * 守护线程验证,守护线程会在用户线程结束后立马销毁,不会有中断信号和kill -9类似吧，所有最好不要在守护线程逻辑中存放影响数据完整性的操作
 */
public class DaemoThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("start");
                Thread.sleep(60 * 1000);
            } catch (Exception e) {
                System.out.println("Exception" + e.getClass());
            } finally {
                System.out.println("finally");
            }
            System.out.println("w");
            System.out.flush();
        });
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName() + " - " + e.getMessage());
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("main");
        Thread.sleep(1000);
    }
}
