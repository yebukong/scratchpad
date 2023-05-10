package pers.mine.scratchpad.other;

public class ClassLoaderTest {

    public static void main(String[] args) {

        ClassLoader appClassLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println("appClassLoader:" + appClassLoader);
        ClassLoader platformClassLoader = appClassLoader.getParent();
        System.out.println("platformClassLoader:" + platformClassLoader);
        ClassLoader bootClassLoader = platformClassLoader.getParent();
        System.out.println("bootClassLoader:" + bootClassLoader);
    }
}
