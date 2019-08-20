package pers.mine.scratchpad.leetcode;

/**
  *  斐波那契数列相关
 * 1、1、2、3、5、8、13、21、34、...
 * 1、2、4、7、12、20、33、54、88、...
 * @author Mine
 */
public class Fibonacci {
	/**
	 * 递推:求第n项值
	 */
	public int getValue1(int n) {
		int beforeLast = 0; // 上两个
		int last = 0; // 上一个
		int current = 0;

		for (int i = 1; i <= n; i++) {
			if (i == 1) {
				current = 1;
			} else {
				current = last + beforeLast;
			}
			beforeLast = last;
			last = current;
		}
		return current;
	}

	/**
	 * 递归:求第n项值
	 */
	public int getValue2(int n) {
		if (n == 1 || n == 2) {
			return 1;
		} else {
			return getValue2(n - 2) + getValue2(n - 1);
		}
	}

	/**
	 * 递推：求和前n项
	 */
	public int caclSum1(int n) {
		int sum = 0;
		int beforeLast = 0; // 上两个
		int last = 0; // 上一个
		int current = 0;
		for (int i = 1; i <= n; i++) {
			if (i == 1) {
				current = 1;
			} else {
				current = last + beforeLast;
			}
			beforeLast = last;
			last = current;
			sum += current;
		}
		return sum;
	}

	/**
	 * 递归：求和前n项
	 */
	public int caclSum2(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += getValue2(i);
		}
		return sum;
	}

	/**
	 * 求和：https://www.zhihu.com/question/38235621/answer/121389348
	 */
	public int caclSum3(int n) {
		return getValue2(n + 2) - 1;
	}
}
