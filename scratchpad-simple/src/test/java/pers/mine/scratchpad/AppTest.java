package pers.mine.scratchpad;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.FastDateFormat;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void one() throws Exception {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		System.out.println(systemClassLoader);
		for (int i = 1; i < 98; i++) {
			System.out.print(i + ",");
		}
	}

	@Test
	public void stream() throws Exception {
		Stream.iterate(0, t -> ++t).limit(70).forEach(t -> {
			System.out.print(t + ",");
		});
	}

	public static final int[] ints = {1, 2};

	@Test
	public void test() {
		ints[1] = 100;
		System.out.println(Arrays.toString(ints));
	}
	@Test
	public void streamListtest() {
		List<String> strings = Arrays.asList("12 ", "  112");
		List<String> collect = strings.stream().map(String::trim).collect(Collectors.toList());
		List<Object> os = Arrays.asList(null, 2);
		List<String> headsTrim = os.stream().map(e -> e.toString().trim()).collect(Collectors.toList());
		System.out.println(headsTrim);
//        System.out.println(strings);
//        System.out.println(collect);
	}
	@Test
	public void testDate() throws ParseException {
		String start = "2020-07-06";
		String end = "2020-07-08";
		long starttime = DateUtil.parse(start).getTime();
		long endtime = DateUtil.parse(end).getTime();
		System.out.println(endtime - starttime);
		double span = (endtime - starttime) / (24 * 60 * 60 * 1000D);
		System.out.println((int) span);
		System.out.println(24 * 60 * 60 * 1000);

		String s = FastDateFormat.getInstance("yyyy-MM-dd").format(new Date(FastDateFormat.getInstance("yyyy-MM-dd").parse(start).getTime() + 2 * 1000L * 24 * 60 * 60));
		System.out.println(s);
	}
}
