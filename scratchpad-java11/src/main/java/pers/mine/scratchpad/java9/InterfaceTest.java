package pers.mine.scratchpad.java9;

/**
 * 支持在接口中定义私有方法
 */
public interface InterfaceTest {

    default void say() {
        say0();
    }
    private void say0() {
        System.out.println("say");
    }
}
