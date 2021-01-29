package pers.mine.scratchpad.other;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

/**
 * https://learnku.com/articles/30625
 * 
 * @author Mine
 * @date 2019/07/19 17:02:59
 */
public class LearnkuTest {
	@Test
	public void scannerTest() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入：");
		String next = scan.next();
		System.out.println("接收到了：" + next);
	}

	@Test
	public void randomTest() {
		for (int i = 0; i < 10; i++) {
			// 默认32 ，随机范围：[0,bound)
			System.out.println(new Random().nextInt(10));
		}
	}

	@Test
	public void arraysTest() {
		List<String> asList = Arrays.asList("a", "b", "c");
		// asList.add("123"); //不支持修改
		System.out.println(asList);

		int[] ins = new int[100];
		Arrays.fill(ins, -1);// 填充指定值
		System.out.println(Arrays.toString(ins));

		String[] strs = { "A", "C", "D", "B" };
		//串行排序 https://segmentfault.com/a/1190000009140981?utm_source=tag-newest
		// Arrays.sort(strs);
		//指定区间排序 [start,end)
		//Arrays.sort(strs, 2, 4);
		//自定义比较规则
//		Arrays.sort(strs, 1, 4, (x,y)->{
//			return - x.compareTo(y);
//		});
		//并行排序
		Arrays.parallelSort(strs);
		System.out.println(Arrays.toString(strs));
		
		//二分法查找指定key，前提是有序数组
		int binarySearch = Arrays.binarySearch(strs, "D");
		System.out.println(binarySearch);
		
		//拷贝数组，其内部调用了 System.arraycopy () 方法，从下标 0 开始，如果超过原数组长度，会用 null 进行填充
		String[] strs2 = Arrays.copyOf(strs,4);
		System.out.println(Arrays.toString(strs2));
		//数组hash值  deepHashCode 多维数组
		System.out.println(Arrays.hashCode (strs));
		System.out.println(Arrays.hashCode (strs2));

		System.out.println(Arrays.equals(strs, strs2));
		//通过指定规则设置值
		Arrays.setAll(strs, i -> strs[i] + strs[i]);
		System.out.println(Arrays.toString(strs));
		//parallelPrefix x上一级执行结果，y当前元素
		Arrays.parallelPrefix(strs, (x,y)->{
			return x+y;
		});
		System.out.println(Arrays.toString(strs));


	}
}
