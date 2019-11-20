package pers.mine.scratchpad.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * SimpleDateFormat线程安全性测试
 * 
 * @author Mine
 */
public class SimpleDateFormatTest {
	ThreadLocal<SimpleDateFormat> tlSdl = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			System.out.println("========="+Thread.currentThread().getName());
			return new SimpleDateFormat("yyyyMMddHHmmssSSS");
		};
	};

	@Test
	public void testName() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String[] arr = new String[] { "20191119193832246", "20181119193832246", "20171119193832246",
				"20161119193832246" };
		ExecutorService es = Executors.newFixedThreadPool(4);
		CountDownLatch cdl = new CountDownLatch(4);
		for (int i = 0; i < 4; i++) {
			int ji = i;
			es.execute(() -> {
				cdl.countDown();
				System.out.println(ji);
				for (int j = 0; j < 100; j++) {
					try {
						// parse存在线程安全问题
						Date parse = tlSdl.get().parse(arr[ji]);
						System.out.println(arr[ji] + " - " + parse);
					} catch (ParseException e) {
						System.out.flush();
						e.printStackTrace();
						System.out.flush();
					}
					// format 在多线程下结果可能和预期不一致
					Date date = new Date();
					String format = tlSdl.get().format(date);
					System.out.println(date + " - " + format);
				}
			});
		}
		cdl.await();
		Thread.sleep(30 * 1000);
	}

}
