package pers.mine.scratchpad.base.classload;

import sun.launcher.LauncherHelper;
import sun.misc.Launcher;

/**
 * @author wangxiaoqiang
 * @description TODO
 * @create 2022-06-13 11:59
 */
public class Test {
    static {
        a = 2;
    }

    static int a = 1;

    public static void main(String[] args) {
        System.out.println(Launcher.getLauncher());
        System.out.println(LauncherHelper.class.getClassLoader());
    }

    void test() {
        test1();
    }

    void test1() {

    }

    void test2() {

    }
}
