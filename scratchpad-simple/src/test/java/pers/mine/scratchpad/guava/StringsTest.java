package pers.mine.scratchpad.guava;

import org.junit.Test;

import com.google.common.base.Strings;

/**
 * guava Strings简单使用
 * 
 * @author Mine
 * @date 2019/06/24 00:46:03
 */
public class StringsTest {
	/**
	 * 判空相关
	 */
	@Test
	public void nullTest() {
		System.out.println(Strings.isNullOrEmpty("A"));//false
		System.out.println(Strings.isNullOrEmpty(""));//true
		System.out.println(Strings.isNullOrEmpty(" "));//false
		System.out.println(Strings.isNullOrEmpty(null));//true
		
		System.out.println(Strings.nullToEmpty(null));// ""
		System.out.println(Strings.nullToEmpty(""));// ""
		System.out.println(Strings.nullToEmpty("A"));// A
		
		System.out.println(Strings.emptyToNull(null));// null
		System.out.println(Strings.emptyToNull(""));// null
		System.out.println(Strings.emptyToNull("A"));// A

	}
	
	/**
	 * 获取相同前后缀
	 */
	@Test
	public void commonFixTest() {
		System.out.println(Strings.commonPrefix("AA123BB", "AA234BB"));//AA
		System.out.println(Strings.commonSuffix("AA123BB", "AA234BB"));//BB
	}
	
	/**
	 * 填充
	 */
	@Test
	public void padTest() {
		System.out.println(Strings.padStart("123", 5, '0'));//00123
		System.out.println(Strings.padEnd("123", 5, '0'));//12300

	}
	
	/**
	 * 重复
	 */
	@Test
	public void repeat() {
		System.out.println(Strings.repeat("ABCD", 3));

	}
}
