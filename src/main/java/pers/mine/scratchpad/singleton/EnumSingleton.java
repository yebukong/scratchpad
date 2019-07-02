package pers.mine.scratchpad.singleton;

/**
 * 枚举方式实现单例 ： 防止了反射，不过是饿汉式
 */
public enum EnumSingleton {
	instance;
	
	/**
     * JVM保证这个方法绝对只调用一次,但这种方式是饿汉式
     */
	EnumSingleton() {
		System.out.println(123);
    }
	public static void test() {
		System.out.println("test");
	}
	
	public static void main(String[] args) {
		EnumSingleton.test();
//		System.out.println();
//		System.out.println(EnumSingleton.instance);
//
//		System.out.println(EnumSingleton.instance);

	}
}

