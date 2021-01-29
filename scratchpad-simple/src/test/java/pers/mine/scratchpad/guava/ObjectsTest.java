package pers.mine.scratchpad.guava;

import org.junit.Test;

import com.google.common.base.Objects;

/**
 * 
 * @author Mine
 * @date 2019/06/24 18:53:49
 */
public class ObjectsTest {
	/**
	 * 安全的equal
	 */
	@Test
	public void equalTest() {
		String a = null;
		String b = null;
		System.out.println(a == b);//true
		
		System.out.println(Objects.equal(null, null));//true
		System.out.println(Objects.equal(null, ""));//false
		System.out.println(Objects.equal("", ""));//true
		System.out.println(Objects.equal("A", "A"));//true
		System.out.println(Objects.equal(1, 1));//true
	}
	/**
	 * 对多个对象hash
	 * 同 java.util.Objects.hash(values) Arrays.hashCode(objects)
	 */
	@Test
	public void hashCodeTest() {
		System.out.println(Objects.hashCode("A","B"));
		System.out.println(Objects.hashCode("A","B","ABC"));
	}
	
	
}
