package pers.mine.scratchpad.other;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

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
        int i = 1;
        System.out.println(i++);
        System.out.println(i);
        Map<String, Long> map = new HashMap<String, Long>();
        System.out.println(map.get("123"));
        System.out.println(String.format("BLNoSource_{}_{}_{}", 1, 2, 3));
        System.out.println(StrUtil.format("BLNoSource_{}_{}_{}", 1, 2, 3));
    }

    @Test
    public void mapNullTest() {
        Map<String, String> map = new HashMap<>();
        map.put(null, null);// 均可以为null
        map = new Hashtable<>();
        //map.put("valTest", null);// value不可以为null，主动校验抛出，确保并发情况下操作一致性
        //map.put(null, "keyTest");// key不可以为null,hashcode时未对key做null兼容，会抛出空指针
        map = new ConcurrentHashMap<>(); //和Hashtable表现一致，互相兼容
        // map.put("valTest", null);// value不可以为null
        //map.put(null, "keyTest");// key不可以为null
        map = new TreeMap<>();
        map.put("valTest", null);// value可以为null
        map.put(null, "keyTest");// key不可以为null，主动校验抛出，需要调用 k.compareTo(t.key)方法，所以提前校验
    }
}