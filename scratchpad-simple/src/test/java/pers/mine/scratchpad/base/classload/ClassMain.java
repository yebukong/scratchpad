package pers.mine.scratchpad.base.classload;

import sun.launcher.LauncherHelper;

import java.sql.DriverManager;

/**
 *
 */
public class ClassMain {
    public static void main(String[] args) {
        //checkAndLoadMain可以直接调用
        //Class<?> aClass = LauncherHelper.checkAndLoadMain(false, 1, "pers.mine.scratchpad.base.classload.ClassLoaderPrepareTest");
        //System.out.println(aClass);
        //ClassLoader.getSystemClassLoader() -> initSystemClassLoader()
        // -> sun.misc.Launcher.getLauncher()
        //System.out.println(LauncherHelper.getApplicationClass());

        //JDBC SPI 破坏双亲委派
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(DriverManager.class.getClassLoader());
    }
}
