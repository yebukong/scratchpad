package pers.mine.scratchpad.singleton;

/**
 * 几种单例模式的实现
 * 
 * @author Mine
 */
public class Singleton {

	private Singleton() {
		System.out.println("Singleton初始化!");
	}

	private static Singleton instanceA;

	/**
	 * 方法用synchronized修饰 并发效率低，可以保证单例 √
	 */
	public synchronized static Singleton getInstanceA() {
		if (instanceA == null) {
			instanceA = new Singleton();
		}
		return instanceA;
	}

	// 使用volatile修饰原因：不用volatile的化极端情况下还是无法保证判空是否有效
	// Singleton的初始化sInstance =new Singleton();不是一条指令,是三条，指令可能重排
	// memory = allocate(); // 1.分配对象的内存空间
	// ctorInstance(memory); // 2.初始化对象
	// sInstance = memory; // 3.设置sInstance指向刚分配的内存地址
	private volatile static Singleton instanceB;

	/**
	 * https://www.cnblogs.com/a154627/p/10046147.html DCL双检锁: 可以保证单例，除非反射，
	 */
	public synchronized static Singleton getInstanceDCL() {
		if (instanceB == null) {// 可以去掉，但去掉后并发效率下降
			synchronized (Singleton.class) {
				if (instanceB == null) {// 不可以去掉，可能出现多个线程同时通过外部if，从而初始化多次
					instanceB = new Singleton();
				}
			}
		}
		return instanceB;
	}

	/**
	 * 深入原理：https://blog.csdn.net/mnb65482/article/details/80458571
	 * 静态内部类方式：没有if，DCL方式效率更高一掉吧，但依然会有反射的问题
	 * 1.静态内部类不会随着外部类的初始化而初始化，他是要单独去加载和初始化的，当第一次执行getInstance方法时，Inner类会被初始化。 
	 * <br>2.静态对象SINGLETION的初始化在Inner类初始化阶段进行，类初始化阶段即虚拟机执行类构造器<clinit>()方法的过程。
	 * 虚拟机会保证一个类的<clinit>()方法在多线程环境下被正确的加锁和同步，如果多个线程同时初始化一个类，只会有一个线程执行这个类的<clinit>()方法，其它线程都会阻塞等待。
	 */
	public static Singleton getInstanceC() {
		return Inner.SINGLETION;
	}

	private static class Inner {
		private static Singleton SINGLETION = new Singleton();
	}
	
	public static void test() {
		System.out.println("test");
	}
}
