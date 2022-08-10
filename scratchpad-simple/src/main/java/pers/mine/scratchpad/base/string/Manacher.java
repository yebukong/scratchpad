package pers.mine.scratchpad.base.string;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <a href="https://zh.m.wikipedia.org/zh-hans/%E6%9C%80%E9%95%BF%E5%9B%9E%E6%96%87%E5%AD%90%E4%B8%B2">最大回文子串算法</a>
 */
public class Manacher {
    /**
     * 暴力算法
     */
    public static void bruteForce(char[] source) {
        char[] cs = init(source);
        int[] p = new int[cs.length];
        int maxI = 0;
        int maxR = 0;
        // 遍历跳过索引0和len-1，默认为0
        //p[i] 表示以 i 为中心的最长回文的半径，不包含当前字符本身
        for (int i = 1; i < cs.length - 1; i++) {
            int r = 0;
            // 不需边界判断，因为左有 $，右有 ^
            while (cs[i - r] == cs[i + r]) {
                r++;
            }
            p[i] = r - 1;
            if (r > maxR) {
                maxR = r;
                maxI = i;
            }
        }
        System.out.println(Arrays.toString(IntStream.range(0, cs.length).toArray()));
        System.out.println(Arrays.toString(cs));
        System.out.println(Arrays.toString(p));
        System.out.println(maxI + "(" + maxR + ")");
    }

    /**
     * 预处理
     */
    public static char[] init(char[] source) {
        StringBuilder sbu = new StringBuilder();
        sbu.append('$').append('#');
        for (char c : source) {
            sbu.append(c).append('#');
        }
        sbu.append("^");
        return sbu.toString().toCharArray();
    }

    public static void main(String[] args) {
        bruteForce("aba".toCharArray());
        bruteForce("aa".toCharArray());
    }
}
