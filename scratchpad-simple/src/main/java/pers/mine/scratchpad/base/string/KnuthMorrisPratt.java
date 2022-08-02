package pers.mine.scratchpad.base.string;

import java.util.Arrays;

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
        while (i < target.length && j < pattern.length) {
            if (target[i] == pattern[j]) {
                i++;
                j++;
            } else {
                int nextIndex = next[j];
                if (nextIndex == -1) { //-1表示第一个字符位未匹配，直接都+1
                    i++;
                    j = 0; //等同于j++，可以和外部if代码合并
                } else { //找到 next 情况下,i位置保持不变，j跳转到next所在位置开始比较
                    j = nextIndex;
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
        int[] next = new int[pattern.length];
        next[0] = -1; // 0 默认为-1
        int j = 0;
        int k = -1;
        while (j < pattern.length - 1) {
            // k 为 -1，表示不匹配，没有共有元素
            // p[k]表示前缀，p[j]表示后缀
            if (k == -1 || pattern[j] == pattern[k]) {
                j++;
                k++;
                //j进行了+1操作，所以这里的k值表示next值而非pmt值
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public static int[] getNext0(char[] pattern) {
        int[] next = new int[pattern.length];
        next[0] = -1; // next[0] 默认为-1
        int j = 0;
        int k = -1;
        while (j < pattern.length - 1) {
            // k 为 -1,表示不匹配，没有共有元素
            if (k == -1) {
                j++;
                k = 0;
                next[j] = 0;
            } else {
                //p[k]表示前缀，p[j]表示后缀
                if (pattern[j] == pattern[k]) {
                    j++;
                    k++;
                    //j进行了+1操作，所以这里的k值表示next值而非pmt值
                    next[j] = k;
                } else {
                    k = next[k];
                }
            }
        }
        return next;
    }

    public static int[] getNextX(char[] pattern) {
        int[] next = new int[pattern.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < pattern.length - 1) {
            // k 为 -1，表示不匹配，没有共有元素
            if (k == -1 || pattern[j] == pattern[k]) {
                j++;
                k++;
                if (pattern[j] == pattern[k]) {
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        char[] target = "ababababca".toCharArray();
        char[] pattern = "abababcd".toCharArray();
        System.out.println(Arrays.toString(getNext(pattern)));
        System.out.println(Arrays.toString(getNext0(pattern)));
        System.out.println(Arrays.toString(getNextX(pattern)));

        //System.out.println(Arrays.toString(getNext0(pattern)));
        System.out.println(knuthMorrisPratt(target, pattern));
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
