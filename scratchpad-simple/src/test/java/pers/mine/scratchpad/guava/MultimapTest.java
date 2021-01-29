package pers.mine.scratchpad.guava;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Guava的Multimap提供了一个方便地把一个键对应到多个值的数据结构。 
 * Multimap有丰富的实现，所以你可以用它来替代程序里的Map<K, Collection<V>>
 * 
 * @author Mine
 * @date 2019/06/24 00:08:09
 */
//	实现            				  Keys 的行为类似       　		　Values的行为类似
//	ArrayListMultimap         HashMap                   　ArrayList
//	HashMultimap              HashMap                  　 HashSet
//	LinkedListMultimap        LinkedHashMap*              LinkedList*
//	LinkedHashMultimap        LinkedHashMap               LinkedHashSet
//	TreeMultimap              TreeMap                     TreeSet
//	ImmutableListMultimap  	  ImmutableMap                ImmutableList
//	ImmutableSetMultimap  	  ImmutableMap                ImmutableSet
public class MultimapTest {

	@Test
	public void arrayListMultimapTest() {
		Multimap<String, String> mMap = ArrayListMultimap.create();
		mMap.put("A", "1");
		mMap.put("A", "1");
		mMap.put("B", "1");
		mMap.put("B", "2");
		mMap.put("B", "3");
		mMap.put("C", "1");
		mMap.put("C", "1");
		mMap.put("C", "2");
		System.out.println(String.format("size:%s", mMap.size()));
		Set<String> keySet = mMap.keySet();
		for (String key : keySet) {
			Collection<String> coll = mMap.get(key);
			System.out.println(String.format("%s:%s -  %s", key, coll.size(), coll.toString()));
		}
//		size:8
//		A:2 -  [1, 1]
//		B:3 -  [1, 2, 3]
//		C:3 -  [1, 1, 2]

	}

	
	
	@Test
	public void hashMultimapTest() {
		Multimap<String, String> mMap = HashMultimap.create();
		mMap.put("A", "1");
		mMap.put("A", "1");
		mMap.put("B", "1");
		mMap.put("B", "2");
		mMap.put("B", "3");
		mMap.put("C", "1");
		mMap.put("C", "1");
		mMap.put("C", "2");
		System.out.println(String.format("size:%s", mMap.size()));
		Set<String> keySet = mMap.keySet();
		for (String key : keySet) {
			Collection<String> coll = mMap.get(key);
			System.out.println(String.format("%s:%s -  %s", key, coll.size(), coll.toString()));
		}
//		size:6		
//		A:1 -  [1]
//		B:3 -  [1, 2, 3]
//		C:2 -  [1, 2]
	}

	@Test
	public void multimapTest() {
		Multimap<String, String> mMap = ArrayListMultimap.create();
		mMap.put("A", "1");
		mMap.put("A", "1");
		mMap.put("B", "1");
		mMap.put("B", "2");
		mMap.put("B", "3");
		mMap.put("C", "11");
		mMap.put("C", "22");
		mMap.put("C", "33");
		System.out.println(mMap.removeAll("A"));//[1, 1]
		System.out.println(mMap.remove("B", "2"));//true
		System.out.println(mMap.removeAll("D"));//[]
		
		System.out.println(mMap.containsEntry("C", "22"));//true
		
		System.out.println(String.format("size:%s", mMap.size()));//5
		
		Map<String, Collection<String>> asMap = mMap.asMap();
		
		System.out.println(String.format("size:%s", asMap.size()));//2
		
		Set<String> keySet = asMap.keySet();
		for (String key : keySet) {
			Collection<String> coll = asMap.get(key);
			System.out.println(String.format("%s:%s -  %s", key, coll.size(), coll.toString()));
		}
//		B:2 -  [1, 3]
//		C:3 -  [11, 22, 33]
		
		asMap.remove("B");//会影响到原Multimap
		System.out.println(String.format("size:%s", mMap.size()));//3

	}
}
