package pers.mine.scratchpad.base.classload;

public class ClassInterfaceTest {

    public static void main(String[] args) {
        SubInterface sbi = new SubInterface() {};
        System.out.println("sbi.x调用前...");
        System.out.println(sbi.x);
        System.out.println("sbi.o调用前...");
        System.out.println(sbi.o);

    }

    static interface SuperInterface {
        static Object o = new Object() {
            {
                System.out.println("super.o");
            }
        };
    }

    static interface SubInterface extends SuperInterface {
        static Object x = new Object() {
            {
                System.out.println("sub.x");
            }
        };
    }

}
