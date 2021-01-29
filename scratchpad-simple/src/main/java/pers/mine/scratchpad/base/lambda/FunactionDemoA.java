package pers.mine.scratchpad.base.lambda;

/**
 * 函数式接口：无参无返回值 FunctionalInterface 注解只能标记在"有且仅有一个抽象方法"的接口上，表示函数式接口。
 * 
 * @author Mine
 * @date 2019/07/11 14:59:37
 */
@FunctionalInterface
public interface FunactionDemoA {
	/**
	 * 目标抽象方法
	 */
	public abstract void test();

	/**
	 * java.lang.Object中的方法不是抽象方法
	 */
	public  String toString();

	/**
	 * default不是抽象方法
	 */
	default public String defaultMethod() {
		return "我是默认方法";
	}

	/**
	 * static 不是抽象方法
	 */
	public static String staticMethod() {
		return "我是静态方法";
	}
}
