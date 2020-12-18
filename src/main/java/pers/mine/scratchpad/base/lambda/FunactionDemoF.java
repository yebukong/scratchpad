package pers.mine.scratchpad.base.lambda;

/**
 * 引用类的实例方法 验证，这个接口多了一个指定类实例的形参
 * https://blog.csdn.net/weixin_41126303/article/details/81187002
 */
@FunctionalInterface
public interface FunactionDemoF {
    /**
     *
     */
    public abstract void test(Demo e);
}
