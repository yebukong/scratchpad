package pers.mine.scratchpad.base.lambda;

/**
 * 验证实例方法
 */
public class Demo {
    String name;

    public Demo(String name) {
        this.name = name;
    }

    public void objectMethodA() {
        System.out.println(name);
        System.out.println(String.format("%s:验证实例方法引用", System.nanoTime()));
    }

    public void classMethodF() {
        System.out.println(name);
        System.out.println(String.format("%s:验证类调用实例方法引用", System.nanoTime()));
    }
}
