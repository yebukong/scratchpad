package pers.mine.scratchpad.base.classload;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * 类加载主动引用被动引用
 */
public class ClassLoadRefTypeTest {
    static {
        System.out.println("ClassLoadRefTypeTest静态代码块执行.");
    }

    public ClassLoadRefTypeTest() {
        System.out.println("构造:" + this.getClass().getClassLoader().getClass());
    }

    public static void main(String[] args) throws Throwable {
        //主动引用
        activeRefTest();
        //被动引用
        //passiveRefTest();
    }

    /**
     * 主动使用
     */
    static void activeRefTest() throws Throwable {
        //1.new
        //new Inner();
        //2.访问静类态变量，非“常量”
        //String s = Inner.FINAL_HELLOWORLD;
        //String s = Inner.HELLOWORLD;
        //3.静态方法调用,方法上声明的自定义异常类也会加载，但不会初始化
        //Inner.test(false);
        //4.Class.forName及反射调用，方法上声明的自定义异常类也会加载
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        //Class.forName可以使用initialize控制是否初始化
        Class<?> aClass = Class.forName("pers.mine.scratchpad.base.classload.ClassLoadRefTypeTest$Inner", true, cl);
        //loadClass本身不触发初始化，常量字段反射访问会触发初始化
        //Class<?> aClass = cl.loadClass("pers.mine.scratchpad.base.classload.ClassLoadRefTypeTest$Inner");
        //Field finalHelloworld = aClass.getField("FINAL_HELLOWORLD");
        //System.out.println(finalHelloworld.get(null));
        //5.方法句柄
        //MethodHandles.Lookup lookup = MethodHandles.lookup();
        //MethodType methodType = MethodType.methodType(void.class, boolean.class);
        //MethodHandle test = lookup.findStatic(Inner.class, "test", methodType);
        //test.invokeExact(false);
        //MethodHandle final_helloworld = lookup.findStaticGetter(Inner.class, "FINAL_HELLOWORLD", String.class);
        //System.out.println(final_helloworld.invoke());
        //6.初始化子类时的父类初始化
        //7.Java虚拟机启动时被标明为启动类的类
    }

    /**
     * 被动引用
     */
    static void passiveRefTest() {
        //通过子类引用父类的静态字段，不会导致子类初始化,但是InnerSubClass是加载的
        //System.out.println(InnerSubClass.HELLOWORLD);
        //通过数组定义的引用类，不会触发此类的初始化,但是会触发加载
        //InnerSubClass[] arr = new InnerSubClass[1];
        //常量引用,类加载行为也不会有，编译阶段已放入常量池
        //System.out.println(InnerSubClass.FINAL_HELLOWORLD);
        //无法在编译期间确认的常量引用,依然会有类初始化，但只初始化真正持有常量引用的类(父类)，子类只加载，不初始化
        //System.out.println(InnerSubClass.FINAL_NEW_HELLOWORLD);
    }

    static class Inner {
        static {
            System.out.println("Inner静态代码块执行.");
        }

        public static String HELLOWORLD = "hello world";

        public static final String FINAL_HELLOWORLD = "final hello world";

        public static final String FINAL_NEW_HELLOWORLD = new String("final hello world");

        public Inner() {
            System.out.println("Inner构造方法执行.");
        }

        {
            System.out.println("Inner代码块执行.");
        }

        static void test(boolean b) throws InnerException {
            System.out.println("InnerTest静态方法。");
            if (b) {
                throw new InnerException();
            }
        }
    }

    static class InnerException extends Exception {
        static {
            System.out.println("InnerException静态代码块执行.");
        }
    }

    static class InnerSubClass extends Inner {
        static {
            System.out.println("InnerSubClass静态代码块执行.");
        }
    }

}
