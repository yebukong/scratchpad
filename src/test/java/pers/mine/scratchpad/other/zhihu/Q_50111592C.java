package pers.mine.scratchpad.other.zhihu;

import java.util.Date;

/**
 * 下列程序的输出:
 *考察字符串常量池
 * 
 * @author Mine
 * @date 2019/07/14 18:27:25
 */
public class Q_50111592C {
	public static void main(String args[]) {
		String a = "abc";
		String b = "ab" + "c";
		System.out.println(a == b);
	}
}
