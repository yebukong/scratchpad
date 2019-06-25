package pers.mine.scratchpad.concurrent;

/**
 * Join测试
 * 
 * @author Mine
 * @date 2019/06/26 07:26:06
 */
public class JoinTest {

    public static class JoinRunnable implements Runnable {
        Thread previousThread = null;
        String name = "";
        public  JoinRunnable(String name,Thread previousThread){
            this.name = name;
            this.previousThread = previousThread;
        }
        public void run() {
            try {
                if(previousThread!=null){
                    previousThread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        Thread previousThread = null;
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new JoinRunnable(i+"", previousThread));
            thread.start();
            thread.join();
            //previousThread = thread;
        }
        System.out.println("主线程执行完了");
    }
}