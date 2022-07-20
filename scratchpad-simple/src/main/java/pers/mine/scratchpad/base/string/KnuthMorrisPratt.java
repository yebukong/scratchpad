package pers.mine.scratchpad.base.string;

/**
 * KMP算法
 */
public class KnuthMorrisPratt {
    public static int knuthMorrisPratt(char[] target, char[] pattern) {
        if (target.length < pattern.length) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        int[] next = getNext(pattern);
        int i = 0; //主串索引比较的起始位置
        int j = 0; //匹配串字符比较索引
        while ((i + j) < target.length && j < pattern.length) {
            if (target[i] == pattern[j]) {
                i++;
                j++;
            } else {
                int nextIndex = next[j];
                if (nextIndex == -1) { //-1表示第一个字符位未匹配，直接都+1
                    i++;
                    j++;
                } else { //找到 next 情况下,i位置不变(j已经在)，j跳转到next所在位置开始比较
                    j = next[j];
                }
            }
        }
        if (j == pattern.length) {
            return i - j;
        }
        return -1;
    }

    public static int knuthMorrisPratt0(char[] target, char[] pattern) {
        if (target.length < pattern.length) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        int[] next = getNext(pattern);
        int i = 0; //主串索引比较的起始位置
        int j = 0; //匹配串字符比较索引
        while (i < target.length && j < pattern.length) {
            if (j == -1 || target[i] == pattern[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == pattern.length) {
            return i - j;
        }
        return -1;
    }

    public static int[] getNext(char[] pattern) {
        return null;
    }

    /**
     * 暴力匹配，回溯写法
     */
    public static int bruteForce1(char[] target, char[] pattern) {
        if (target.length < pattern.length) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        int i = 0; //主串索引比较的起始位置
        int j = 0; //匹配串字符比较索引
        while ((i + j) < target.length && j < pattern.length) {
            if (target[i + j] == pattern[j]) {
                j++;
            } else {
                i++;
                j = 0;
            }
        }
        if (j == pattern.length) {
            return i;
        }
        return -1;
    }

    /**
     * 暴力匹配，回溯写法
     */
    public static int bruteForce2(char[] target, char[] pattern) {
        if (target.length < pattern.length) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        int i = 0; //主串索引比较的起始位置
        int j = 0; //匹配串字符比较索引
        while (i < target.length && j < pattern.length) {
            if (target[i] == pattern[j]) {
                i++;
                j++;
            } else {
                i = (i - j) + 1;
                j = 0;
            }
        }
        if (j == pattern.length) {
            return i - j;
        }
        return -1;
    }
}
