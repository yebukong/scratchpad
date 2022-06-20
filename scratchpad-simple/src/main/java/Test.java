import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        System.out.println("start");
        System.out.println("AaAa".hashCode());
        System.out.println("BBBB".hashCode());
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.out.println("hook end");
                }
            });
            TimeUnit.SECONDS.sleep(60);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
