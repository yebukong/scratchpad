import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        System.out.println("start");
        System.out.println(IdUtil.fastSimpleUUID());
        String x1 = "123";
        System.out.println(StrUtil.isEmpty(x1) ? a1() : a2());
        System.out.println("AaAa".hashCode());
        System.out.println("BBBB".hashCode());
        int x = 3;
        try {
            assert x < 0 : x + " must < 0";
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.out.println("hook end");
                }
            });
            TimeUnit.SECONDS.sleep(60);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    public static String a1() {
        System.out.println("a1");
        return "a1";
    }

    public static String a2() {
        System.out.println("a2");
        return "a2";
    }
}
