package pers.mine.scratchpad.leetcode;

/**
 * 买卖股票的最佳时机 https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 * 
 * @author Mine
 * @date 2019/09/01 23:29:22
 */
public class Solution121 {

	/**
	 * 遍历法
	 */
	public int maxProfit1(int[] prices) {
		int max = 0;
		int tmp = 0;
		for (int i = 0; i < prices.length; i++) {
			for (int j = i + 1; j < prices.length; j++) {
				tmp = prices[j] - prices[i];
				if (tmp > max) {
					max = tmp;
				}
			}
		}
		return max;
	}

	/**
	 * TODO
	 */
	public int maxProfit2(int[] prices) {
		return 0;
	}
	public static void main(String[] args) {
		int[] nums = { 7,1,5,3,6,4 };
		int maxProfit = new Solution121().maxProfit1(nums);
		System.out.println(maxProfit);
	}
}
