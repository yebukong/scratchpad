package pers.mine.scratchpad.other.json;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import pers.mine.scratchpad.util.HttpUtils;

/**
 * fastjson-jsonPath测试
 * 
 * @author Mine
 * @data 2019年10月23日 下午2:02:51
 */
public class JsonPathTest {
	JSONObject testJson = null;

	@Before
	public void init() throws Exception {
		String url = "https://sj.qq.com/myapp/cate/appList.htm?orgame=1&categoryId=105&pageSize=20&pageContext=97";
		testJson = HttpUtils.getForJson(url).getJsonBody();
	}

	@Test
	public void test1() {
		Object eval = JSONPath.eval(testJson, "$.obj[*].appName");
		System.out.println(eval.getClass().getSimpleName());
		System.out.println(eval);
	}

}
