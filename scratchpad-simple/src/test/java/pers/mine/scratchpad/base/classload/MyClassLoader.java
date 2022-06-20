package pers.mine.scratchpad.base.classload;

import cn.hutool.core.text.StrBuilder;
import sun.misc.URLClassPath;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;

public class MyClassLoader extends URLClassLoader {
    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.acc = AccessController.getContext();
        ucp = new URLClassPath(urls, acc);
    }

    private URLClassPath ucp;
    private AccessControlContext acc;

    @Override
    public Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        //只对MyClassLoader和String使用自定义的加载，其他的还是走双亲委派
        if (name.contains("MyClassLoader")
                || name.equals("java.lang.String")
                || name.equals("java.lang.Object")) {
            return super.findClass(name);
        } else {
            return getParent().loadClass(name);
        }
    }

    public static void main(String[] args) throws Exception {
        URL resource = MyClassLoader.class.getResource("/");

        File f = new File(resource.getPath());
        StrBuilder sbu = new StrBuilder(f.getParentFile().getParentFile().getAbsolutePath());
        sbu.append(File.separator)
                .append("src").append(File.separator)
                .append("test").append(File.separator)
                .append("javax").append(File.separator);
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        MyClassLoader cl = new MyClassLoader(new URL[]{new File(sbu.toString()).toURI().toURL()}, ccl);
        //使用自定义类加载器加载Object
        Class<?> strClass = cl.loadClass("java.lang.Object", false);

        System.out.println();
        //使用自定义类加载器加载
        //Class<?> strClass = cl.loadClass("java.lang.String", true);

    }

    static class Inner {
        public Inner() {
            System.out.println("test");
        }
    }
}

    
