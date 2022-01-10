package pers.mine.scratchpad.base.lambda;

import java.util.UUID;

public class LambdaMan {
    Demo d = new Demo("LambdaMan demo");

    public void lambdaCaller(FunactionDemoA fd) {
        System.out.println("void FunactionDemoA.test()");
        fd.test();
//		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//		for (StackTraceElement ste : stackTrace) {
//			System.out.println(ste);
//		}
        return;
    }

    public void lambdaCaller(FunactionDemoB fd) {
        System.out.println("void FunactionDemoB.test(String)");
        fd.test(UUID.randomUUID().toString());
        return;
    }

    public void lambdaCaller(FunactionDemoC fd) {
        System.out.println("boolean FunactionDemoC.test()");
        System.out.println("return:" + fd.test());
        return;
    }

    public void lambdaCaller(FunactionDemoD fd) {
        System.out.println("String FunactionDemoD.test()");
        System.out.println("return:" + fd.test());
        return;
    }

    public void lambdaCaller(FunactionDemoE fd) {
        System.out.println("String FunactionDemoD.test(String)");
        System.out.println("return:" + fd.test("我是参数"));
        return;
    }

    public void lambdaCallerF(FunactionDemoF fd) {
        System.out.println("String FunactionDemoD.test(String)");
        fd.test(d);
        return;
    }

    public static void methodA() {
        System.out.println(String.format("%s:验证方法引用", System.nanoTime()));
    }

    public static void main(String[] args) {
        LambdaMan man = new LambdaMan();
//        man.lambdaCaller(() -> {
//            System.out.println(String.format("%s:输出A", System.nanoTime()));
//        });
//
//        man.lambdaCaller(s -> {
//            System.out.println(String.format("%s:输出B", System.nanoTime(), s));
//        });
//        man.lambdaCaller(() -> true);// 忽略return写法
//        man.lambdaCaller(() -> "叫我返回值");
//        man.lambdaCaller(s -> "直接返回 ->" + s);
        Demo e = new Demo("123");
        man.lambdaCaller(LambdaMan::methodA); //静态方法引用
        man.lambdaCaller(e::objectMethodA); //实例方法引用
        man.lambdaCallerF(Demo::classMethodF); //类调用实例方法引用
        man.lambdaCaller(new FunactionDemoA() {
            @Override
            public void test() {
                System.out.println("123");
            }
        });

        man.lambdaCaller(()->System.out.println("123"));
    }

}
