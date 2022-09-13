package pers.mine.scratchpad.base.string;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <a href="https://zh.m.wikipedia.org/zh-hans/%E6%9C%80%E9%95%BF%E5%9B%9E%E6%96%87%E5%AD%90%E4%B8%B2">最大回文子串算法</a>
 */
public class Manacher {
    /**
     * 朴素算法-中心扩展
     */
    public static void bruteForce(char[] source) {
        char[] cs = init(source);
        int[] p = new int[cs.length];
        int maxI = 0;
        int maxStep = 0;
        // 遍历跳过索引0和len-1，默认为0
        //p[i] 表示以 i 为中心的最长回文的半径，不包含当前字符本身
        for (int i = 1; i < cs.length - 1; i++) {
            //步长
            int nextStep = 1;
            // 不需边界判断，因为左有 $，右有 ^
            while (cs[i - nextStep] == cs[i + nextStep]) {
                nextStep++;
            }
            p[i] = nextStep - 1;

            //记录最新最长回文串信息
            if (p[i] > maxStep) {
                maxStep = p[i];
                maxI = i;
            }
        }
        System.out.println("i   : " + Arrays.toString(IntStream.range(0, cs.length).toArray()));
        System.out.println("s[i]: " + Arrays.toString(cs));
        System.out.println("p[i]: " + Arrays.toString(p));
        System.out.println(maxI + "(" + maxStep + ")");
    }

    /**
     * 优化后分支，不是完全等价的
     */
    public static void manacher(char[] source) {
        char[] cs = init(source);
        int[] p = new int[cs.length];
        int maxI = 0;
        int maxStep = 0;
        //维护向右延伸最远的回文串的信息 r,c共同确定
        // 其最右的位置 r
        int r = 0;
        // 其中心位置 c
        int c = 0;
        for (int i = 1; i < cs.length - 1; i++) {
            // 找出 i 为中心的回文串半径 p[i]
            if (i < r) {
                // 目标回文串的中心在 r 左边时
                // 最大化吸收已有信息
                // j 是 i 关于 c 对称的位置
                int j = c - (i - c);
                // p[i] 至少为下面二者最小值
                p[i] = Math.min(p[j], r - i);
            } else {
                // 否则，初始化 p[i]
                p[i] = 0;
            }

            // 左右进行扩展，探测剩余长度
            int nextStep = p[i] == 0 ? 1 : p[i];
            while (cs[i - nextStep] == cs[i + nextStep]) {
                nextStep++;
            }
            p[i] = nextStep - 1;

            // 维护更新 c 和 r
            if (i + p[i] > r) {
                r = i + p[i];
                c = i;
            }

            //记录最新最长回文串信息
            if (p[i] > maxStep) {
                maxStep = p[i];
                maxI = i;
            }
        }
        System.out.println("i   : " + Arrays.toString(IntStream.range(0, cs.length).toArray()));
        System.out.println("s[i]: " + Arrays.toString(cs));
        System.out.println("p[i]: " + Arrays.toString(p));
        System.out.println(maxI + "(" + maxStep + ")");
    }

    /**
     * 原始分支
     */
    public static void manacher0(char[] source) {
        char[] cs = init(source);
        int[] p = new int[cs.length];
        int maxI = 0;
        int maxStep = 0;
        //维护向右延伸最远的回文串的信息 r,c共同确定
        // 其最右的位置 r
        int r = 0;
        // 其中心位置 c
        int c = 0;
        for (int i = 1; i < cs.length - 1; i++) {
            // 找出 i 为中心的回文串半径 p[i]
            if (i < r) {
                // i < r ,目标回文串的中心在 r 左边时
                // j 是 i 关于 c 对称的位置
                int j = c - (i - c);
                if (i + p[j] < r) {
                    // 不需要扩展
                    p[i] = p[j];
                } else if (i + p[j] > r) {
                    // 不需要扩展
                    p[i] = r - i;
                } else {
                    //i + p[j] == r ，从p[j]步即r位置开始扩展
                    int nextStep = p[j] + 1;
                    while (cs[i - nextStep] == cs[i + nextStep]) {
                        nextStep++;
                    }
                    p[i] = nextStep - 1;
                }
            } else {
                // i >= r ,直接扩展i
                int nextStep = 1;
                while (cs[i - nextStep] == cs[i + nextStep]) {
                    nextStep++;
                }
                p[i] = nextStep - 1;
            }

            // 维护更新 c 和 r
            if (i + p[i] > r) {
                r = i + p[i];
                c = i;
            }

            //记录最新最长回文串信息
            if (p[i] > maxStep) {
                maxStep = p[i];
                maxI = i;
            }
        }
        System.out.println("i   : " + Arrays.toString(IntStream.range(0, cs.length).toArray()));
        System.out.println("s[i]: " + Arrays.toString(cs));
        System.out.println("p[i]: " + Arrays.toString(p));
        System.out.println(maxI + "(" + maxStep + ")");
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
        bruteForce("daba".toCharArray());
        bruteForce("aa".toCharArray());
        bruteForce("aaabcbaba".toCharArray());
        manacher("aaabcbaba".toCharArray());
        manacher0("aaabcbaba".toCharArray());
    }
}
