package pers.mine.scratchpad.leetcode;

/**
 * 只出现一次的数字 II
 * https://leetcode-cn.com/problems/single-number-ii/
 * @author Mine
 * @date 2019/07/05 19:09:26
 */
public class Solution137 {
	public int singleNumber(int[] nums) {
		int result = 0;
		//根据所有数字特定位出现1的次数是否为3的次数判断
		for (int i = 0; i < 32; i++) {
			int count = 0;
			for (int num : nums) {
				if(((num >>> i) & 1)==1) {
					count++;
				}
			}
			if(count%3 != 0) {
				result = result | (1 << i);//当前位  置1
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int [] nums = {0,1,0,1,0,1,99};
		int singleNumber = new Solution137().singleNumber(nums);
		System.out.println(singleNumber);
	}
}
