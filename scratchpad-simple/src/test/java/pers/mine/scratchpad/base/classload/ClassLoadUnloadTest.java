package pers.mine.scratchpad.base.classload;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

/**
 * 卸载验证
 * 该类所有的实例已经被回收
 * 加载该类的ClassLoder已经被回收
 * 该类对应的java.lang.Class对象没有任何对方被引用
 */
public class ClassLoadUnloadTest {
    static {
        System.out.println("ClassLoadUnloadTest 初始化");
    }
    public static void main(String[] args) throws Throwable {
        ClassLoadUnloadTest a = new ClassLoadUnloadTest();
        URL resource = ClassLoadUnloadTest1.class.getResource("/");
        System.out.println(resource);
        //parent为空，表示父加载器是启动类加载器
        //loadClass resolve表示是否链接类，并不是是否初始化的开关
        URLClassLoader cl = new URLClassLoader(new URL[]{resource}, null) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                return super.loadClass(name, true);
            }
        };
        //Class<?> aClass = Class.forName("pers.mine.scratchpad.base.classload.ClassLoadUnloadTest", true, cl);
        Class<?> aClass = cl.loadClass("pers.mine.scratchpad.base.classload.ClassLoadUnloadTest");
        System.out.println("Class.forName end.");
        Object b = aClass.newInstance();
        //类唯一性验证
        System.out.println("instanceof test a1: " + (a instanceof ClassLoadUnloadTest));
        System.out.println("isInstance test a2: " + (aClass.isInstance(a)));
        System.out.println("instanceof test b1: " + (b instanceof ClassLoadUnloadTest));
        System.out.println("isInstance test b2: " + (aClass.isInstance(b)));
        System.out.println("isAssignableFrom:" + aClass.isAssignableFrom(ClassLoadUnloadTest.class));
        b = null;
        aClass = null;
        cl = null;
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    public static void testResolve() throws InnerException {
        throw new InnerException();
    }

    static class InnerException extends Exception {
        static {
            System.out.println("InnerException");
        }
    }
}
