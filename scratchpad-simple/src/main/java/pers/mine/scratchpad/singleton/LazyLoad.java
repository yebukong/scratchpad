package pers.mine.scratchpad.singleton;

/**
 * jvm 延迟加载类
 */
public class LazyLoad {
    static {
        System.out.println("*********** loading TestLazyLoading class ***********");
    }

    public static void main(String[] args) {
        new TestA();
        System.out.println("*************** loading test ************");
        TestB testB = null;
    }

}

class TestA {

    static {
        System.out.println("********* load TestA **********");
    }

    public TestA() {
        System.out.println("*********** initial A **************");
    }

}

class TestB {

    static {
        System.out.println("********* load TestB **********");
    }

    public TestB() {
        System.out.println("*********** initial B **************");
    }

}
