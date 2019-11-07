package pers.mine.scratchpad.util;

import java.util.Arrays;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;

/**
 * json工具类
 * 
 * @author Mine
 * @data 2019年9月27日 下午4:52:07
 */
public class JsonKit {
	/**
	 * 默认key路径分割符
	 */
	public static final Character DEFAULT_KEY_SEPARATOR = ',';

	/**
	 * 置jsonObject限定名key为指定值,key使用指定分隔符分隔<br>
	 * ps:注意不同深度值之间的覆盖问题
	 *
	 * @param jsonObject   待更新jsonObject
	 * @param qualifiedKey 限定名key
	 * @param baseValue    基础值
	 * @param keySeparator key分隔符
	 */
	public static void updateJson(JSONObject jsonObject, String qualifiedKey, Object baseValue,
			Character keySeparator) {
		int index = qualifiedKey.indexOf(keySeparator);
		if (index != -1) {
			String key = qualifiedKey.substring(0, index);
			JSONObject jo;
			if (jsonObject.containsKey(key)) {
				jo = jsonObject.getJSONObject(key);
			} else {
				jo = new JSONObject();
				jsonObject.put(key, jo);
			}
			String subQualifiedKey = qualifiedKey.substring(index + 1);
			updateJson(jo, subQualifiedKey, baseValue, keySeparator);
		} else {
			jsonObject.put(qualifiedKey, baseValue);
		}
	}

	/**
	 * 置jsonObject限定名key为指定值,key使用","分隔符分隔
	 *
	 * @see JsonUtils#updateJson(com.alibaba.fastjson.JSONObject, java.lang.String,
	 *      java.lang.Object, java.lang.Character)
	 */
	public static void updateJson(JSONObject jsonObject, String qualifiedKey, Object baseValue) {
		updateJson(jsonObject, qualifiedKey, baseValue, DEFAULT_KEY_SEPARATOR);
	}

	public static void main1(String[] args) {
		Scanner scan = new Scanner(System.in);
		int sum = 0;
		while (true) {
			System.out.println("请输入：");
			String next = scan.next();
			System.out.println("接收到了：" + next);
			JSONObject one = JSONObject.parseObject(next);
			for (String key : one.keySet()) {
				JSONObject two = one.getJSONObject(key);
				if (two.getJSONObject("order").containsKey("1")) {
					Long[] clks = two.getJSONObject("order").getJSONObject("1").getObject("clks", Long[].class);
					System.out.println(Arrays.deepToString(clks));
					sum += clks[0];
					System.out.println("sum:" + sum);
				} else {
					System.out.println("未找到order:1");
				}
			}

		}
	}
}
