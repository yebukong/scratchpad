package pers.mine.scratchpad.base.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 
 * @author Mine
 * @date 2019/08/02 23:01:27
 */
public class GenericsSupper<T> {
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public GenericsSupper() {
		final Type genType = getClass().getGenericSuperclass();
		clazz = (Class<T>) ((ParameterizedType) genType).getActualTypeArguments()[0]; // 获得子类的泛型类型
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public static void main(String[] args) {
		System.out.println(new Test<String>().getClazz());
	}
}

class Test<T> extends GenericsSupper<String> {
}
