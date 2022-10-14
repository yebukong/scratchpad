package pers.mine.scratchpad.base.classload;

import sun.launcher.LauncherHelper;

import java.lang.reflect.Field;
import java.sql.DriverManager;

/**
 * java程序启动涉及的类
 */
public class ClassMain {
    public static void main(String[] args) throws Exception {
        //checkAndLoadMain可以直接调用
        //Class<?> aClass = LauncherHelper.checkAndLoadMain(false, 1, "pers.mine.scratchpad.base.classload.ClassLoaderPrepareTest");
        //System.out.println(aClass);
        //ClassLoader.getSystemClassLoader() -> initSystemClassLoader()
        // -> sun.misc.Launcher.getLauncher()
        //System.out.println(LauncherHelper.getApplicationClass());
        //类加载器内classes值
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Field classes = ClassLoader.class.getDeclaredField("classes");
        classes.setAccessible(true);
        Object o = classes.get(contextClassLoader);
        System.out.println(o);
        //JDBC SPI 破坏双亲委派
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(DriverManager.class.getClassLoader());
    }
}
