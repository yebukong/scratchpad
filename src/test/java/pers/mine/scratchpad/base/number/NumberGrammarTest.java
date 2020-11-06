package pers.mine.scratchpad.base.number;

import org.junit.Test;

public class NumberGrammarTest {
    @Test
    public void test1() {
        int a1 = 1_0___________0__0_0; //数字字面量,数字中间可以加入不限个数的下划线
        int a2 = 0b100; //二进制表示 0b 0B
        int a3 = 0B100;
        int a4 = 0100; //八进制表示 0b 0B
        int a5 = 100; //十制表示
        int a6 = +0100; //数字前可以添加正负,不限制进制
        int a7 = -0b100;
        int a8 = 0x100; //十六进制表示 0x 0X
        int a9 = 0X100;
        double a10 = 1e3; // 表示1 * 10 *10 *10
        double a11 = 1E-3;// 表示1 * （1/（10 *10 *10））
        double a12 = 8E-3;
        double a13 = 10E2;
        double a14 = 10.0123D;
        double a15 = 10.0123d;
        double a16 = .0111d;
        float a17 = 10.0123f;
        float a18 = 10.0123f;
        float a19 = .0123f;

        System.out.println("a1  - " + a1);
        System.out.println("a2  - " + a2);
        System.out.println("a3  - " + a3);
        System.out.println("a4  - " + a4);
        System.out.println("a5  - " + a5);
        System.out.println("a6  - " + a6);
        System.out.println("a7  - " + a7);
        System.out.println("a8  - " + a8);
        System.out.println("a9  - " + a9);
        System.out.println("a10 - " + a10);
        System.out.println("a11 - " + a11);
        System.out.println("a12 - " + a12);
        System.out.println("a13 - " + a13);

        System.out.println("a14 - " + a14);
        System.out.println("a15 - " + a15);
        System.out.println("a16 - " + a16);
        System.out.println("a17 - " + a17);
        System.out.println("a18 - " + a18);
        System.out.println("a19 - " + a19);
    }
}
