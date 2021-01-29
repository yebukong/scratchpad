package pers.mine.scratchpad.other;

/**
 * 类加载顺序验证      
 * @author Mine
 * @date 2019/09/03 16:33:37
 */
public class ClassLoadTest {
	public static int k = 0;
	public static ClassLoadTest t1 = new ClassLoadTest("t1构造方法");

	public static int i = print("i");
	public static int n = 99;
	public int j = print("j");

	static {
		print("***静态块***");
	}
	public static ClassLoadTest t2 = new ClassLoadTest("t2构造方法");
	{
		print("***构造块***");
	}

	public ClassLoadTest(String str) {
		System.out.println((++k) + ":" + str + " i=" + i + " n=" + n);
		++n;
		++i;
	}

	public static int print(String str) {
		System.out.println((++k) + ":" + str + " i=" + i + " n=" + n);
		++i;
		return ++n;
	}

	public static void main(String[] args) throws ClassNotFoundException {
		// 特殊情况下同一个类的构造块/构造方法会比静态块先执行...
		// 侧面说明其实类的静态方法，静态变量和类的实例方法，成员变量并没有放在一起，否则感觉会陷入死循环

//        Class.forName("pers.mine.LoadClass");//或者main为空
//运行结果:
//        1:j i=0 n=0
//        2:***构造块*** i=1 n=1
//        3:t1构造方法 i=2 n=2
//        4:i i=3 n=3
//        5:***静态块*** i=4 n=99
//        6:j i=5 n=100
//        7:***构造块*** i=6 n=101
//        8:t2构造方法 i=7 n=102

		ClassLoadTest t = new ClassLoadTest("init");
//运行结果:
//         1:j i=0 n=0
//         2:***构造块*** i=1 n=1
//         3:t1构造方法 i=2 n=2
//         4:i i=3 n=3
//         5:***静态块*** i=4 n=99
//         6:j i=5 n=100
//         7:***构造块*** i=6 n=101
//         8:t2构造方法 i=7 n=102
//         9:j i=8 n=103
//         10:***构造块*** i=9 n=104
//         11:init i=10 n=105

//        当一个类被主动使用时，Java虚拟就会对其初始化，如下六种情况为主动使用：
//
//        当创建某个类的新实例时（如通过new或者反射，克隆，反序列化等）
//        当调用某个类的静态方法时
//        当使用某个类或接口的静态字段时
//        当调用Java API中的某些反射方法时，比如类Class中的方法，或者java.lang.reflect中的类的方法时
//        当初始化某个子类时
//        当虚拟机启动某个被标明为启动类的类（即包含main方法的那个类）
//        Java编译器会收集所有的类变量初始化语句和类型的静态初始化器，将这些放到一个特殊的方法中：clinit。 
	}
}
