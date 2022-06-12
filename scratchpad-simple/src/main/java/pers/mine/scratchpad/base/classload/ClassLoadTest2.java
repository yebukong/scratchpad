package pers.mine.scratchpad.base.classload;

import java.sql.DriverManager;

public class ClassLoadTest2 {
    /**
     * JDBC SPI 破坏双亲委派
     */
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(DriverManager.class.getClassLoader());
    }

}
