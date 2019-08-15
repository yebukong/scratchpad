package pers.mine.scratchpad.leetcode;

import java.util.Arrays;
import org.apache.poi.hssf.record.MergeCellsRecord;

/**
 *  合并有序数组使得合并后依然有序
 * 
 * @author Mine
 * @date 2019/08/16 00:33:45
 */
public class SolutionX {
	public static  int[] merge(int[] nums1, int m, int[] nums2, int n) {
		// 暂且认为 m为num1的长度 n为num2的长度
		int[] resultArr = new int[m + n];

		int index1 = 0;
		int index2 = 0;
		int tmp = 0;
		for (int i = 0; i < resultArr.length; i++) {
			if (index1 < m && index2 < n) {
				if (nums1[index1] < nums2[index2]) {
					tmp = nums1[index1];
					index1++;
				} else {
					tmp = nums2[index2];
					index2++;
				}
			} else if (index1 < m) {
				tmp = nums1[index1];
				index1++;
			} else if (index2 < n) {
				tmp = nums2[index2];
				index2++;
			} else {
				break;
			}
			resultArr[i] = tmp;
		}
		return resultArr;
	}

	public static void main(String[] args) {
		int[] a = { 1, 3, 4, 5, 6, 7, 8, 9 };
		int[] b = { 3, 4, 5, 111 };
		System.out.println(Arrays.toString(merge(a, a.length, b, b.length)));
	}
}
