package pers.mine.scratchpad.base.classload;

/**
 * 准备阶段值验证
 */
public class ClassLoaderPrepareTest {
    public static ClassLoaderPrepareTest one = new ClassLoaderPrepareTest();
    public static boolean flag = true;
    public static byte b = 1;
    public static char c = 1;
    public static short s = 1;
    public static int i = 1;
    public static long l = 1L;
    public static float f = 1f;
    public static double d = 1d;
    public static String str = "str";
    //对于常量值，准备阶段就直接赋值了
    public static final String final_str = "final_str";

    static {
        //赋值正常，访问会编译报错 Illegal forward reference
        s_edit_1 = 1;
        //System.out.println(edit_3);
        s_edit_4 = 1;
    }

    //准备的初始化和赋值是两个阶段完成的
    public static int s_edit_1;
    public static int s_edit_2 = 0;
    public static int s_edit_3;
    public static int s_edit_4 = 0;

    static {
        //访问正常
        //System.out.println(s_edit_3);
    }

    public ClassLoaderPrepareTest() {
        System.out.println("flag :" + flag);
        System.out.println("b :" + b);
        System.out.println("c :" + c);
        System.out.println("s :" + s);
        System.out.println("i :" + i);
        System.out.println("l :" + l);
        System.out.println("f :" + f);
        System.out.println("d :" + d);
        System.out.println("str :" + str);
        System.out.println("final_str :" + final_str);

        s_edit_1 = 2;
        s_edit_2 = 2;
        s_edit_3 = 2;
        s_edit_4 = 2;

        edit_1 = 2;
        edit_2 = 2;
        edit_3 = 2;
        edit_4 = 2;
    }

    public int edit_1;
    public int edit_2 = 0;
    public int edit_3;
    public int edit_4 = 0;

    public static void main(String[] args) {
        System.out.println("s_edit_1最终值:" + s_edit_1);
        System.out.println("s_edit_2最终值:" + s_edit_2);
        System.out.println("s_edit_3最终值:" + s_edit_3);
        System.out.println("s_edit_4最终值:" + s_edit_4);

        System.out.println("one.edit_1最终值:" + one.edit_1);
        System.out.println("one.edit_2最终值:" + one.edit_2);
        System.out.println("one.edit_3最终值:" + one.edit_3);
        System.out.println("one.edit_4最终值:" + one.edit_4);
    }
}
