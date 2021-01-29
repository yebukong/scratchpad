package pers.mine.scratchpad.leetcode;

/**
 * 无重复字符的最长子串
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * 
 * @author Mine
 * @date 2019/07/05 19:08:49
 */
public class Solution3 {
	public int lengthOfLongestSubstring1(String s) {
		StringBuffer sbu = new StringBuffer();
		int max = 0;
		char tmp;
		for (int i = 0; i < s.length(); i++) {
			tmp = s.charAt(i);
			int index = sbu.lastIndexOf(Character.toString(tmp));
			if (index > -1) {
				int length = sbu.length();
				max = max < length ? length : max;
				sbu = new StringBuffer(sbu.substring(index + 1, length));
			}
			sbu.append(tmp);
		}
		max = max < sbu.length() ? sbu.length() : max;
		return max;
	}

	public int lengthOfLongestSubstring(String s) {
		//TODO hashMap版
		return 0;
	}

	public static void main(String[] args) {
		int x = new Solution3().lengthOfLongestSubstring1("aab");
		System.out.println(x);
//		int index = "ab".lastIndexOf("a");
//		System.out.println(index);
//		System.out.println( "ab".substring(index+1,2));
	}
}
