package pers.mine.scratchpad.base.classload;

/**
 * 类代码块初始化顺序
 */
public class ClassLoadInitOrderTest {

    static {
        System.out.println("ClassLoadInitOrderTest静态代码块");
    }

    public static void main(String[] args) {
        new SubClass();
    }

    static class SuperClass {
        static {
            System.out.println("父类静态代码块");
        }

        public SuperClass() {
            System.out.println("父类构造方法.");
        }

        {
            System.out.println("父类代码块");
        }

    }

    static class SubClass extends SuperClass {

        static {
            System.out.println("子类静态代码块");
        }

        public SubClass() {
            System.out.println("子类构造方法.");
        }

        {
            System.out.println("子类代码块");
        }
    }
}
