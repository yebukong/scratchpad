package pers.mine.scratchpad.base.generics;

/**
 * @author mine
 * @description TODO
 * @create 2020-06-23 17:40
 */
public class CheckConstructor {
    public static void main1(String[] args) {
        String empty = "";
        char[] chars = empty.toCharArray();
        System.out.println(chars);
        String[] a = {};
        String[] b = new String[0];
        System.out.println(a.length);
        System.out.println(b.length);

        System.out.println("test()--------");
        test();
        System.out.println("test(null)--------");
        test(null);
        System.out.println("test((Object[]) null)--------");
        test((Object[]) null);
        System.out.println("test((Object[][]) null)----------");
        test((Object[][]) null);
    }

    public static void test(Object[]... args) {
        System.out.println(args);
        try {
            System.out.println(args.getClass());
            System.out.println(args.length);
            for (Object arg : args) {
                System.out.println(arg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> void test(T... s) {
        System.out.println(s.length);
        for (T t : s) {
            System.out.println(t);
        }
    }

//    public static <T> void test(T s1) {
//        System.out.println(s1);
//    }
//
//    public static <T> void test(T s1, T s2) {
//        System.out.println(s1);
//        System.out.println(s2);
//
//    }

    public static void main(String[] args) {
        int[] x = {1};
        test(x,x);
    }

    static class C extends B {

        @Override
        public void look() {
            System.out.println(name);
        }

        C() {
            super();
        }
    }

    static abstract class B implements A {
        String name;

        B() {
            System.out.println("B()");
        }
    }

    interface A {
        void look();
    }

}
