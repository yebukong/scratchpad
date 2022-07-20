package pers.mine.scratchpad.base.string;

/**
 *
 */
public class StringMatchingAlgorithms {

    public static int bruteForce(char[] target, char[] pattern) {
        if (target.length < pattern.length) {
            return -1;
        }
        if (pattern.length == 0) {
            return 0;
        }
        for (int i = 0; i <= (target.length - pattern.length); i++) {
            if (target[i] == pattern[0]) {
                int j;
                for (j = 1; j < pattern.length; j++) {
                    if (target[i + j] != pattern[j]) {
                        break;
                    }
                }
                if (j == pattern.length) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int rabinKarpSimpleHash(char[] target, char[] pattern) {
        int patternHash = 0;
        for (int i = 0; i < pattern.length; i++) {
            patternHash = simpleHash(patternHash, true, pattern[i]);
        }
        int subStrHash = 0;
        for (int i = 0; i <= (target.length - pattern.length); i++) {
            //计算子串hash
            if (i == 0) {
                for (int j = 0; j < pattern.length; j++) {
                    subStrHash = simpleHash(subStrHash, true, target[j]);
                }
            } else {
                subStrHash = simpleHash(subStrHash, false, target[i - 1]);
                subStrHash = simpleHash(subStrHash, true, target[i + pattern.length - 1]);
            }

            // hash相等
            if (subStrHash == patternHash) {
                //子串判等
                int j;
                for (j = 1; j < pattern.length; j++) {
                    if (target[i + j] != pattern[j]) {
                        break;
                    }
                }
                if (j == pattern.length) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 简单的hash算法:一种便捷但并不优秀的旋转哈希函数采用的计算方法为直接减去串首字符的值并加上串尾字符的值，类似一个滑动窗口操作：
     */
    public static int simpleHash(int baseHash, boolean isAdd, char c) {
        if (isAdd) {
            baseHash += (int) c;
        } else {
            baseHash -= (int) c;
        }
        return baseHash;
    }

    public static int rabinKarpPolynomialRollingHash(char[] target, char[] pattern) {
        int patternHash = 0;
        for (int i = 0; i < pattern.length; i++) {
            patternHash = polynomialRollingHashHash(patternHash, pattern.length - i - 1, true, pattern[i]);
        }
        int subStrHash = 0;
        for (int i = 0; i <= (target.length - pattern.length); i++) {
            //计算子串hash
            if (i == 0) {
                for (int j = 0; j < pattern.length; j++) {
                    subStrHash = polynomialRollingHashHash(subStrHash, pattern.length - j - 1, true, target[j]);
                }
            } else {
                //先减去 target(i-1) * 256 ^ (pattern.length - 1)
                subStrHash = polynomialRollingHashHash(subStrHash, pattern.length - 1, false, target[i - 1]);
                subStrHash = (subStrHash * 256) % 101;
                //加上
                subStrHash = polynomialRollingHashHash(subStrHash, 0, true, target[i + pattern.length - 1]);
            }

            // hash相等
            if (subStrHash == patternHash) {
                //子串判等
                int j;
                for (j = 1; j < pattern.length; j++) {
                    if (target[i + j] != pattern[j]) {
                        break;
                    }
                }
                if (j == pattern.length) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 多项式滚动hash,字符集选ASCII，进制为256,取模n选择101
     * k表示c对应的进制位,从右到左，从0开始
     */
    public static int polynomialRollingHashHash(int baseHash, int k, boolean isAdd, char c) {
        int mod = (c % 101);
        if (isAdd) {
            // Math.pow可提前计算优化
            for (int i = 0; i < k; i++) {
                mod = (mod * (256 % 101)) % 101;
            }
            baseHash += mod;
        } else {
            for (int i = 0; i < k; i++) {
                mod = (mod * (256 % 101)) % 101;
            }
            baseHash -= mod;
        }
        //向下取整，避免出现负数
        return Math.floorMod(baseHash, 101);
    }

    public static void main(String[] args) {
        System.out.println((int) '0');
        System.out.println((int) '1');
        System.out.println((int) '2');
        System.out.println((int) '3');
        System.out.println("-----");
        System.out.println((int) 'a');
        System.out.println((int) 'b');
        System.out.println((int) 'c');
        System.out.println((int) 'd');
        System.out.println((int) 'e');
        System.out.println((int) 'h');
        System.out.println((int) 'i');
        System.out.println((int) Character.MAX_VALUE);
        System.out.println("----");
        System.out.println(polynomialRollingHashHashArr("ab".toCharArray()));
        System.out.println(polynomialRollingHashHashArr("bc".toCharArray()));
        System.out.println(polynomialRollingHashHashArr("cd".toCharArray()));
        System.out.println(polynomialRollingHashHashArr("de".toCharArray()));

        char[] target = "abcde".toCharArray();
        char[] pattern = "de".toCharArray();
        System.out.println(bruteForce(target, pattern));
        System.out.println(rabinKarpSimpleHash(target, pattern));
        System.out.println(rabinKarpPolynomialRollingHash(target, pattern));
//        int patternHash = 0;
//        for (int i = 0; i < pattern.length; i++) {
//            patternHash = polynomialRollingHashHash(patternHash, pattern.length - i, true, pattern[i]);
//        }
        int cd = polynomialRollingHashHashArr("bc".toCharArray());
        int de = polynomialRollingHashHashArr("cd".toCharArray());
        System.out.println(cd);
        System.out.println(de);
        System.out.println("sssssss");
        int x = cd;
        x = polynomialRollingHashHash(x, 1, false, 'b');
        x = (x * 256) % 101;
        //加上
        x = polynomialRollingHashHash(x, 0, true, 'd');
        System.out.println(x);
        System.out.println(-54 % 101);
        System.out.println(47 % 101);
        System.out.println(Math.floorMod(-54, 101));
        System.out.println(Math.floorMod(47, 101));
    }

    public static int polynomialRollingHashHashArr(char... pattern) {
        int patternHash = 0;
        for (int i = 0; i < pattern.length; i++) {
            patternHash = polynomialRollingHashHash(patternHash, pattern.length - i - 1, true, pattern[i]);

        }
        return patternHash;
    }
}
