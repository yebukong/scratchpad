package pers.mine.scratchpad.other.zhihu;

import org.junit.Test;

public class P_27431652 {
	@Test
	public void test1() {
		int i = 1;
		int j = i++;
		if ((i == (++j)) && ((i++) == j)) {
			i += j;
		}
		System.out.println("i = " + i);// 5
	}

	@Test
	public void test5() {
		int x = 10;
		double y = 20.2;
		long z = 10L;
		String str = "" + x + y * z;
		System.out.println(str);// 拼接会时优先算出+的每一块
		String str1 = y + x + y * z + "";
		System.out.println(str1); // 只有在碰到字符串才开始使用字符串拼接
	}

	@Test
	public void test9() {
		int sum = 0;
		for (int x = 1; x < 10; x++) {
			sum += x;
			if (x % 3 == 0) {
				continue;
			}
		}
		System.out.println(sum);// 45
	}

	@Test
	public void test10() {
		int sum = 0;
		for (int x = 0; x < 10; x++) {
			sum += x;
			if (x % 3 == 0) {// 0就跳出了
				break;
			}
		}
		System.out.println(sum);// 0
	}
}
