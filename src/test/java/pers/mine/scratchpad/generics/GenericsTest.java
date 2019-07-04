package pers.mine.scratchpad.generics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 *   泛型通配符 ?
 * @author Mine
 * @date 2019/07/04 16:21:22
 */
public class GenericsTest {

	/**
	 * ? extends B 表示确定后的泛型类型是继承于B的，即上限可以保证，下限无法保证
	 * add 不可以调用，是因为不能保证add的参数类型的下限，因此无法保证安全的向上转型
	 *  但get方法可以保证类型的上限为B，可以安全的调用B的实例方法
	 */
	@Test
	public void test1() {
		List<? extends B> list = new ArrayList<C>();
		// 难以理解的设计：
		//list.add(new A());//报错 ,add参数无法适用
		//list.add(new B());//报错 ,add参数无法适用
		//list.add(new C());//报错 ,add参数无法适用
		
		// 这里的编码场景应该是这样 '? extends A' 改为' A'会编译报错
		List<? extends B> list1 = null;
		String type = "XXX";
		if ("C".equals(type)) {
			list1 = new ArrayList<C>();
		} else {
			list1 = new ArrayList<B>();
		}

		// 通用处理
		for (B b : list1) {
			b.b();
			System.out.println(b.toString());
		}
		System.out.println(list);
	}
	
	/**
	 * ? super B 表示确定后的泛型类型是B的超类，即上限无法保证，但下限可以保证
	 * add 可以调用，是因为可以确定参数类型的下限为B，因此B及B的子类可以保证安全的向上转型
	 * 但是get方法无法保证参数类型，只能是Object
	 */
	@Test
	public void test2() {
		List<? super B> list = new ArrayList<A>();
		list.add(new B());
		list.add(new C());
		//list.add(new A());//报错 ,add参数无法适用
		for (Object object : list) {
			//无法确定类型上限
		}
	}
	
	
}

class A {
	public void a() {
	}

	public String toString() {
		return "A";
	}
}

class B extends A {
	public void b() {
	}

	public String toString() {
		return "B";
	}
}

class C extends B {
	public void c() {
	}

	public String toString() {
		return "C";
	}
}
