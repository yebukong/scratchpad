package pers.mine.scratchpad.base.classload;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

public class ClassLoadUnloadTest1 {
    static {
        System.out.println("static exec.");
    }

    public ClassLoadUnloadTest1() {
        System.out.println("constructor exec:" + this.getClass().getClassLoader().getClass());
    }

    public static void main(String[] args) throws Exception {
        /*****被动引用样演示******/
        System.out.println(SubClass.value);
        SubClass[] ss = new SubClass[1];
        System.out.println(SubClass.HELLOWORLD); //
        System.out.println("------------------被动引用演示结束------------------");
        /*****加载卸载演示******/
        ClassLoadUnloadTest1 a = new ClassLoadUnloadTest1();
        URL resource = ClassLoadUnloadTest1.class.getResource("/");
        System.out.println(resource);
        //parent为空，表示父加载器是启动类加载器
        //resolve表示是否链接类，不包含初始化
        URLClassLoader cl = new URLClassLoader(new URL[]{resource}, null) {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return loadClass(name, true);
            }
        };
        Class<?> aClass = Class.forName("pers.mine.scratchpad.base.classload.ClassLoadUnloadTest1", true, cl);
        //Class<?> aClass = cl.loadClass("pers.mine.scratchpad.base.classload.ClassLoadUnloadTest");
        System.out.println("Class.forName end.");
        Object b = aClass.newInstance();
        System.out.println("instanceof test a1: " + (a instanceof ClassLoadUnloadTest1));
        System.out.println("isInstance test a2: " + (aClass.isInstance(a)));
        System.out.println("instanceof test b1: " + (b instanceof ClassLoadUnloadTest1));
        System.out.println("isInstance test b2: " + (aClass.isInstance(b)));
        System.out.println("isAssignableFrom:" + aClass.isAssignableFrom(ClassLoadUnloadTest1.class));
        b = null;
        aClass = null;
        cl = null;
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(300);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("end");
    }

    static class SuperClass {
        static {
            System.out.println("SuperClass init!");
        }

        public static int value = 123;
    }

    static class SubClass extends SuperClass {
        static {
            System.out.println("SubClass init!");
        }

        public static final String HELLOWORLD = "hello world";
    }
}
