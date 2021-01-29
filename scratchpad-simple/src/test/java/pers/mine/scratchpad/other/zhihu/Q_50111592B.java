package pers.mine.scratchpad.other.zhihu;

/**
 * 以下程序运行时是否会抛出异常, 以及程序输出. 
 * <br>考察：静态变量的默认初始化，以及String的连接（+）
 * <br>静态变量会在类加载过程中的linking阶段得到默认初始化。引用类型的静态变量会被默认初始化为null。
 * 
 * @author Mine
 */
public class Q_50111592B {
	static String s0, s1;

	public static void main(String args[]) {
		// s0 = s0 + s1;
		// 在java 1.5-1.8 相当于
		// s0 = new StringBuilder().append(s0).append(s1).toString();
		// java9之后 是直接使用字节数组来操作，并且字节数组长度预先计算好，可以减少字符串复制操作
		// https://blog.csdn.net/wangyangzhizhou/article/details/81059282
		System.out.println(s0);
	}
}
