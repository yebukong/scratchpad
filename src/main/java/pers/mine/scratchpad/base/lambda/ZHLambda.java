package pers.mine.scratchpad.base.lambda;

import java.util.Comparator;
import java.util.function.BooleanSupplier;

/**
 * 知乎：https://www.zhihu.com/question/50801791 在如下的百度面试题中，如何填写 if 条件才能输出 "ab"？
 * 
 * <pre>
 * public void print() { 
 *  if (    ) {
 *      System.out.print("a");
 *  } else {
 *      System.out.print("b");
 *  }
 * }
 * </pre>
 * 
 * ps:很久以前看过，回味一下，还是受益颇多啊
 * 
 * @author Mine
 */
public class ZHLambda {

	public static void main(String[] args) {
		ZHLambda zl = new ZHLambda();
		zl.printA();
		System.out.println();
		zl.printB();
		System.out.println();
		zl.printC();
		System.out.println();
		zl.printD();
		System.out.println();
		zl.printE();
		System.out.println();
		zl.printF();
		System.out.println();
	}

	/**
	 * 利用 PrintStream java.io.PrintStream.printf(String, Object...)
	 * 方法既可以输出[输出流自身，必然不为空]，又有返回值的特点
	 */
	public void printA() {
		if (System.out.printf("a") == null) {
			System.out.print("a");
		} else {
			System.out.print("b");
		}
	}

	/**
	 * 创建一个方法
	 */
	public void printB() {
		if (func()) {
			System.out.print("a");
		} else {
			System.out.print("b");
		}
	}

	private boolean func() {
		System.out.print("a");
		return false;
	}

	/**
	 * 利用函数式接口BooleanSupplier方法返回布尔值的特点
	 */
	public void printC() {
		if (((BooleanSupplier) (() -> {
			System.out.print("a");
			return false;
		})).getAsBoolean()) {
			System.out.print("a");
		} else {
			System.out.print("b");
		}
	}

	/**
	 * 推广到任意有返回值的函数式接口
	 */
	public void printD() {
		if (((Comparator<String>) ((x, y) -> {
			System.out.print("a");
			return 0;
		})).compare("A", "B") != 0) {
			System.out.print("a");
		} else {
			System.out.print("b");
		}
	}

	/**
	 * 重写Object方法
	 */
	public void printE() {
		if (new Object() {
			public boolean equals(Object o) {
				System.out.print("a");
				return false;
			}
		}.equals(null)) {
			System.out.print("a");
		} else {
			System.out.print("b");
		}
	}

	/**
	 * 匿名类：新方法<br>
	 * 一个匿名内部类的实例，在刚刚new完的时候编译器（javac级别）还知道它的实际类型，所以即便是它所继承的基类 /
	 * 所实现的接口没有的方法也照样可以调用。java.lang.Object上有foo()方法么？没有。但是我们的匿名内部类上有，刚new出来的地方就可以调用。
	 */
	public void printF() {
		if (new Object() {
			public boolean newFunc() {
				System.out.print("a");
				return false;
			}
		}.newFunc()) {
			System.out.print("a");
		} else {
			System.out.print("b");
		}
	}
}
