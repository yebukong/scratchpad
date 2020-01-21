package pers.mine.scratchpad.other.json;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import pers.mine.scratchpad.util.HttpUtils;

/**
 * fastjson-jsonPath测试
 * 
 * @author Mine
 * @data 2019年10月23日 下午2:02:51
 */
public class JsonPathTest {
	JSONObject testJson = null;

	// @Before
	public void init() throws Exception {
		// String url =
		// "https://sj.qq.com/myapp/cate/appList.htm?orgame=1&categoryId=105&pageSize=20&pageContext=97";
		String url = "http://mi.talkingdata.com/app/profile/72.json?startTime=2018-11-01";
		testJson = HttpUtils.getForJson(url).getJsonBody();
	}

	@Test
	public void test1() {
		//
		Object eval = JSONPath.eval(testJson, "$.obj[*].appName");
		System.out.println(eval.getClass().getSimpleName());
		System.out.println(eval);
	}

	@Test
	public void test2() {
		//
		System.out.println(testJson);
		Object eval = JSONPath.eval(testJson, "$[?(@.profileName=='gender')]");
		System.out.println(eval.getClass().getSimpleName());
		System.out.println(eval);
	}

	@Test
	public void test3() {
		Map<String, String> x = new HashMap<String, String>();
		x.put("1", "a");
		x.forEach((a, b) -> {
			System.out.println(a + "|" + b);
		});
	}

	@Test
	public void test4() {
		System.out.println("{--asdas--}asda-}");

	}
	
	@Test
	public void test5() {
		System.out.println(null instanceof Boolean);
		System.out.println(Boolean.TRUE.equals(null));

	}
	
	@Test
	public void test6() {
		int[] arr = {1,1,1};
		arr[1]+=2;
		System.out.println(Arrays.toString(arr));
	}
	
	@Test
	public void test7() {
        String now = DateUtil.formatDateTime(new Date());
		System.out.println(now.substring(11, 13));
	}
}
