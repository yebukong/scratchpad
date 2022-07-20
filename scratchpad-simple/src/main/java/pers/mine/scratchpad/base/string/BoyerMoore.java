package pers.mine.scratchpad.base.string;

import java.util.Arrays;

public class BoyerMoore {

    public static void main(String[] args) {
        char[] target = "abcde".toCharArray();
        char[] pattern = "cde".toCharArray();
        System.out.println(boyerMoore(target, pattern));
        target = "aaaaaaaa".toCharArray();
        pattern = "baaa".toCharArray();
        System.out.println(boyerMoore(target, pattern));
    }

    public static int boyerMoore(char[] target, char[] pattern) {
        if (target.length < pattern.length) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        // 预处理，记录模式串中每个字符最后出现的位置
        int[] bc = badCharRule(pattern);
        // 预处理，好后缀
        int[] suffix = new int[pattern.length];
        boolean[] prefix = new boolean[pattern.length];
        goodSuffixRule(pattern, suffix, prefix);

        int i = 0; // i表示主串与模式串对齐的第一个字符
        while (i <= target.length - pattern.length) {
            int j;
            // 模式串从后往前匹配
            for (j = pattern.length - 1; j >= 0; --j) {
                // 坏字符对应模式串中的下标是j，默认值为pattern.length - 1
                if (target[i + j] != pattern[j])
                    break;
            }
            // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            if (j < 0) {
                return i;
            }
            //坏字符位移,包含两种情况
            //1.坏字符不在匹配串中，则将匹配串右移到坏字符后面，最终offset为坏字符对应在模式串中位置(j) + 1
            //2.坏字符在匹配串中，则位移模式串至与坏字符匹配的最靠后的那个字符处，最终offset为坏字符对应在模式串中位置(j)-bc['c']
            int badOffset = (j - bc[(int) target[i + j]]);
            int goodOffset = 0;
            // 如果有好后缀
            if (j < pattern.length - 1) {
                goodOffset = moveByGS(j, pattern.length, suffix, prefix);
            }
            //跳过指定offset执行下一次匹配
            i = i + Math.max(badOffset, goodOffset);
        }
        return -1;
    }

    /**
     * 根据预处理数组快速计算好后缀位移
     *
     * @param j      坏字符在模式串中对应索引
     * @param m      模式串长度
     * @param suffix
     * @param prefix
     * @return
     */
    public static int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j; // 好后缀长度
        if (suffix[k] != -1)
            return j - suffix[k] + 1;
        for (int r = j + 2; r <= m - 1; ++r) {
            if (prefix[m - r]) {
                return r;
            }
        }
        return m;
    }

    /**
     * 生成坏字符查找数组
     */
    public static int[] badCharRule(char[] pattern) {
        int[] badChar = new int[256];
        Arrays.fill(badChar, -1);
        for (int i = 0; i < pattern.length; i++) {
            badChar[pattern[i]] = i;
        }
        return badChar;
    }

    /**
     * 预处理好后缀数组
     *
     * @param pattern 模式串
     * @param suffix  模式串后缀子串在模式串中重复出现的位置（不包括原本的位置，即如果除了后缀没有出现过，那就是没重复，为-1）
     * @param prefix  它表示的是模式串的后缀子串是否有可匹配的前缀子串，如果有，则值为 true。
     */
    public static void goodSuffixRule(char[] pattern, int[] suffix, boolean[] prefix) {
        // 初始化suffix 和 prefix
        Arrays.fill(suffix, -1);
        Arrays.fill(prefix, false);

        //上图 b[0, i]
        for (int i = 0; i < pattern.length - 1; i++) {
            int j = i;
            int k = 0; // 公共后缀子串长度

            // 与b[0, m-1]求公共后缀子串
            while (j >= 0 && pattern[j] == pattern[pattern.length - k - 1]) {
                --j;
                ++k;
                //j+1表示公共后缀子串在b[0, i]中的起始下标
                suffix[k] = j + 1;
            }
            if (j == -1) {
                //如果公共后缀子串也是模式串的前缀子串
                prefix[k] = true;
            }
        }
    }

}
