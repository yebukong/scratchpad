package pers.mine.scratchpad.util;

import org.openjdk.jol.info.ClassLayout;

public class ClassSzieTest {
    byte b1;
    byte b2;
    byte b3;
    long l1;
    char c1;

    public static void main1(String[] args) {

        ClassSzieTest classSzieTest = new ClassSzieTest();
        System.out.println(ClassLayout.parseInstance(classSzieTest).toPrintable());

    }

    public static void main(String[] args) {
        System.out.println(int.class);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE+Long.MIN_VALUE);
        System.out.println(Long.MAX_VALUE + 1);
    }
}
