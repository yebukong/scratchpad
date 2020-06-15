package pers.mine.scratchpad;

import sun.misc.Launcher;

import java.io.File;
import java.net.URL;

/**
 * @create 2020-06-15 17:57
 */
public class ClassLoadTest {
    public static void main(String[] args) throws ClassNotFoundException {
        //类加载器的父类加载器应该是指的当前类加载器对象的parent成员变量指向的那个类加载器对象，
        // 这两个对象既不是继承关系，也没有谁加载谁的关系（因为ExtClassLoader以及AppClassLoader都是BoostrapLoader加载的）
        System.out.println("---Launcher.getBootstrapClassPath()----");
        URL[] urLs = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url.toExternalForm());
        }
        System.out.println("---Launcher.class.getClassLoader()----");

        ClassLoader classLoader = Launcher.class.getClassLoader();
        System.out.println(classLoader);
        String exts = System.getProperty("java.ext.dirs");
        System.out.println("---java.ext.dirs----");
        for (String s : exts.split(File.pathSeparator)) {
            System.out.println(s);
        }
        System.out.println("---SunEC.class----");
        ClassLoader sunEcClassLoader = sun.security.ec.SunEC.class.getClassLoader();
        System.out.println(sunEcClassLoader);
        System.out.println(sunEcClassLoader.getParent());
//        Class.forName("");
        ClassLoader classLoader1 = ClassLoadTest.class.getClassLoader();
        System.out.println(classLoader1);
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
    }
}
