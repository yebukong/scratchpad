package pers.mine.scratchpad.base.features;

import java.util.Arrays;

/**
 * @create 2020-03-25 16:21
 */
public class TextBlockFea {
    public static void main(String[] args) {
//        var x = """
//                  asdasd
//                  啊实打实的ss
//                asdasd""";
        String splitStr1 = ",1,2,,3,";
        String splitStr2 = ",1,2,,3,4";
        System.out.println(Arrays.toString(splitStr1.split(",", -1)));
        System.out.println(Arrays.toString(splitStr1.split(",")));
        System.out.println(Arrays.toString(splitStr2.split(",", -1)));
        System.out.println(Arrays.toString(splitStr2.split(",")));
        System.out.println(Test.A.getX());
    }

    static enum Test {
        A(1), B(2);
        int i;
        String x;

        Test(int i) {
            this.i = i;
            x = name() + "_" + i;
        }

        public String getX() {
            return x;
        }
    }
}
