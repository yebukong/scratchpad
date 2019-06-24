package pers.mine.scratchpad.guava;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * 双向map
 * @author Mine
 * @date 2019/06/24 20:17:41
 */
public class BiMapTest {
	@Test
	public void test() {
		BiMap<String, String> bm = HashBiMap.create();
		bm.put("1", "一");
		bm.put("2", "二");
		bm.put("3", "三");
		bm.put("4", "四");
		bm.put("5", "五");
		
		
		System.out.println(bm.get("1"));//一
		System.out.println(bm.get("一"));//null
		System.out.println(bm.inverse().get("一"));//1
		
		System.out.println(bm.containsKey("2"));//true
		System.out.println(bm.containsKey("二"));//false
		System.out.println(bm.containsValue("2"));//false
		System.out.println(bm.containsValue("二"));//true
		
		System.out.println(bm.inverse().remove("一"));//1
		System.out.println(bm.get("1"));//null
	}
}
