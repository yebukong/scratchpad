package pers.mine.scratchpad.util;

/**
 * @author wangxiaoqiang
 * @description TODO
 * @create 2021-07-07 15:35
 */
public interface Test1 {
    String hello = "Hello";

    void sayHello();
}



class X implements Test1{

     @Override
     public void sayHello() {
          System.out.println(hello);
     }

     public static void main(String[] args) {
          System.out.println(Test1.hello);

     }
}
