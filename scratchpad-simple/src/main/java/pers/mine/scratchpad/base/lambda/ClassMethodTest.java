package pers.mine.scratchpad.base.lambda;

public class ClassMethodTest {

    public void a(Integer param1, int param2) {
        System.out.println(param1 + "----------" + param2);
    }

    public static void main(String[] args) {
        // j.a(k, l) 这种方式使用场景是什么呢?
        MyInter m = (j, k, l) -> j.a(k, l);
        //第一个参数为方法目标，其余参数为参数
        MyInter m1 = ClassMethodTest::a;

    }
}

@FunctionalInterface
interface MyInter {
    public void d(ClassMethodTest d, int param1, int param2);
}
