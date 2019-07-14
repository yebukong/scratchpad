package pers.mine.scratchpad.other;

import org.junit.Test;

/**
 * strictfp 关键字的使用 如果你想让你的浮点运算更加精确， 而且不会因为不同的硬件平台所执行的结果不一致的话，那就请用关键字strictfp。
 * 
 * 你可以将一个类、接口以及方法声明为strictfp，但是不允许对接口中的方法以及构造函数声明strictfp关键字
 * 
 * @author Mine
 * @date 2019/07/03 02:16:16
 */
public class StrictfpTest {
	public static void main(String[] args) {
	}

	@Test
	public void testMath() {
		// 四舍五入 Math.round =>(long)floor(a + 0.5d)
		System.out.println(String.format("Math.round(-15.51) =  %s", Math.round(-15.51)));
		System.out.println(String.format("Math.round(-15.50) =  %s", Math.round(-15.50)));
		System.out.println(String.format("Math.round(-15.49) =  %s", Math.round(-15.49)));

		System.out.println(String.format("Math.round(15.51) =  %s", Math.round(15.51)));
		System.out.println(String.format("Math.round(15.50) =  %s", Math.round(15.50)));
		System.out.println(String.format("Math.round(15.49) =  %s", Math.round(15.49)));

		// 向上取整
		System.out.println();
		System.out.println(String.format("Math.ceil(-15.01) =  %s", Math.ceil(-15.01)));
		System.out.println(String.format("Math.ceil(-15.00) =  %s", Math.ceil(-15.00)));
		System.out.println(String.format("Math.ceil(-14.99) =  %s", Math.ceil(-14.99)));

		System.out.println(String.format("Math.ceil(15.01) =  %s", Math.ceil(15.01)));
		System.out.println(String.format("Math.ceil(15.00) =  %s", Math.ceil(15.00)));
		System.out.println(String.format("Math.ceil(14.99) =  %s", Math.ceil(14.99)));

		// 向下取整
		System.out.println();
		System.out.println(String.format("Math.floor(-15.01) =  %s", Math.floor(-15.01)));
		System.out.println(String.format("Math.floor(-15.00) =  %s", Math.floor(-15.00)));
		System.out.println(String.format("Math.floor(-14.99) =  %s", Math.floor(-14.99)));

		System.out.println(String.format("Math.floor(15.01) =  %s", Math.floor(15.01)));
		System.out.println(String.format("Math.floor(15.00) =  %s", Math.floor(15.00)));
		System.out.println(String.format("Math.floor(14.99) =  %s", Math.floor(14.99)));
	}
}
