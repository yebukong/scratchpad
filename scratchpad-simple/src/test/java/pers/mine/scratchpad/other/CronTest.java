package pers.mine.scratchpad.other;

import java.util.Date;

import org.junit.Test;
import org.quartz.CronExpression;

import cn.hutool.core.date.DateUtil;

public class CronTest {
	/**
	   *  获取下一个执行时间
	 */
	@Test
	public void nextTest() throws Exception {
		CronExpression ce = new CronExpression("0 30 1-23 * * ? *");
		Date d = new Date();
		for (int i = 0; i < 30; i++) {
			d = ce.getNextValidTimeAfter(d);
			System.out.println(DateUtil.format(d, "yyyy-MM-dd HH:mm:ss"));
		}
	}
}
