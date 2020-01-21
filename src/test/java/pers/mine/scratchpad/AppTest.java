package pers.mine.scratchpad;

import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void one() throws Exception {
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
}
