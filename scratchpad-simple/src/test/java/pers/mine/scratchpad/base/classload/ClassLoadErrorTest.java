package pers.mine.scratchpad.base.classload;

import cn.hutool.core.text.StrBuilder;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 类加载一些异常验证
 */
public class ClassLoadErrorTest {

    /**
     * ClassNotFoundException和NoClassDefFoundError
     */
    @Test
    public void test1() throws Exception {
        URL resource = ClassLoadErrorTest.class.getResource("/");

        File f = new File(resource.getPath());
        StrBuilder sbu = new StrBuilder(f.getParentFile().getParentFile().getAbsolutePath());
        sbu.append(File.separator)
                .append("src").append(File.separator)
                .append("test").append(File.separator)
                .append("javax").append(File.separator);
        System.out.println(sbu.toString());
        URLClassLoader cl = new URLClassLoader(new URL[]{new File(sbu.toString()).toURI().toURL()}, ClassLoadErrorTest.class.getClassLoader()) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                System.out.println("name" + name);
                return super.loadClass(name, true);
            }
        };
        Class<?> aClass = cl.loadClass("ClassA");
        Method main = aClass.getDeclaredMethod("main", String[].class);
        main.invoke(null, (Object) null);
//        Class<?> bClass = cl.loadClass("ClassA$Inner");
    }

    /**
     * 静态块报错，ExceptionInInitializerError
     */
    @Test
    public void test2() throws Exception {
        Class.forName("ClassB", true, ClassLoadErrorTest.class.getClassLoader());
    }
}
