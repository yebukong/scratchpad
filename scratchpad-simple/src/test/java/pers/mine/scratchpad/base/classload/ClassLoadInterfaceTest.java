package pers.mine.scratchpad.base.classload;

/**
 * 接口的初始化
 * 通过查看字节码，SuperInterface存在clinit方法，但是ClassLoadInterfaceTest没有
 */
public class ClassLoadInterfaceTest {
    public static void main(String[] args) {
        // 接口中不能使用静态语句块，但仍然有变量初始化的赋值操作，因此接口与类一样都会生成<clinit>()方法。
        // 但接口与类不同的是，执行接口的<clinit>()方法不需要先执行父接口的<clinit>()方法，
        // 因为只有当父接口中定义的变量被使用时，父接口才会被初始化。
        // 此外，接口的实现类在初始化时也一样不会执行接口的<clinit>()方法。
        SubInterface sbi = new SubInterface() {
        };
        System.out.println("sbi.superO调用前...");
        System.out.println(sbi.superO);
        System.out.println("sbi.subO调用前...");
        System.out.println(sbi.subO);

        //当一个接口中定义了JDK 8新加入的默认方法（被default关键字修饰的接口方法）时，
        //如果有这个接口的实现类发生了初始化，那该接口要在其之前被初始化。
        DefaultSubInterface dsbi = new DefaultSubInterface() {
        };
        System.out.println("dsbi.superO调用前...");
        System.out.println(dsbi.superO);
        System.out.println("dsbi.subO调用前...");
        System.out.println(dsbi.subO);
    }

    static interface SuperInterface {
        static Object superO = new Object() {
            {
                System.out.println("super.superO");
            }
        };
    }

    static interface SubInterface extends SuperInterface {
        static Object subO = new Object() {
            {
                System.out.println("sub.subO");
            }
        };
    }

    static interface DefaultSuperInterface {
        static Object superO = new Object() {
            {
                System.out.println("default super.superO");
            }
        };

        public default void test() {
            System.out.println("test");
        }
    }

    static interface DefaultSubInterface extends DefaultSuperInterface {
        static Object subO = new Object() {
            {
                System.out.println("default sub.subO");
            }
        };
    }
}
