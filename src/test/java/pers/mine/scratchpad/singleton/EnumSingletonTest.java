package pers.mine.scratchpad.singleton;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnumSingletonTest {

	/**
	 *  验证枚举单例模式并不是懒加载
	 */
	@Test
	public void test() {
		EnumSingleton.test();
		System.out.println(EnumSingleton.instance);
	}

}
