package pers.mine.scratchpad.base.trycatch;

import java.util.Arrays;

public class TryCacheTest2 {

    public void test3() {
        int[] a = {111};
        a[0] = 5;
    }

    public static void main(String[] args) {

//        a[(a[0]=1)] = 2;
//        System.out.println(a[0]);
//        System.out.println(a[1]);
//        System.out.println(test1());
        int[] a = {0, 0};
        int i = 1;
        a[i] = (++i) + 1;
        System.out.println(a[0]);
        System.out.println(a[1]);

        System.out.println(Arrays.toString(a));
    }

    public static String test() {
        String x = "";
        try {
            x = "try";
            return x;
        } catch (Exception e) {
            x = "cache";
            return x;
        } finally {
            x = "finally";
        }
    }

    public static int test1() {
        int x = 0;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
            return x;
        }
    }

}
