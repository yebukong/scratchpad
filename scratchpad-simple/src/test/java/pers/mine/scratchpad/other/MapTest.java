package pers.mine.scratchpad.other;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import cn.hutool.core.util.StrUtil;

public class MapTest {
	@Test
	public void hashMap() {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(null, null);// key value 均支持为空
	}

	@Test
	public void concurrentHashMap() {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("", null);// 存在 NPE
		map.put(null, "");// 存在 NPE
	}

	@Test
	public void TreeMap() {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("", null);// value支持为空
		//map.put(null, "");// 存在 NPE
	}
	

	@Test
	public void Hashtable() {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("", null);// 存在 NPE
		map.put(null, "");// 存在 NPE
	}
	
	@Test
	public void other() {
		int i =1;
		System.out.println(i++);
		System.out.println(i);
		Map<String, Long> map = new HashMap<String, Long>();
		System.out.println(map.get("123"));
		System.out.println(String.format("BLNoSource_{}_{}_{}", 1, 2, 3));
		System.out.println(StrUtil.format("BLNoSource_{}_{}_{}", 1, 2, 3));
	}
}