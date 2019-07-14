package pers.mine.scratchpad.other.zhihu;

/**
 * 请写出运行输出
 * 
 * @author Mine
 * @date 2019/07/14 17:58:38
 */
public class Q_50111592A {
	public void test(Object o) {
		System.out.println("Object");
	}

	public void test(String s) {
		System.out.println("String");
	}

	public static void main(String[] args) {
		Q_50111592A that = new Q_50111592A();
		//这里的that.test(null)可以匹配上两个重载版本的test()方法，但是String版本比Object版本更具体，所以匹配上String版。
		that.test(null);
		
		//强转来指定重载方法
		that.test((Object)null);
	}
}
