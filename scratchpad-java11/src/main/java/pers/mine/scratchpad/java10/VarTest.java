package pers.mine.scratchpad.java10;

import cn.hutool.core.annotation.Alias;

import java.util.ArrayList;

/**
 * var类型推断
 */
public class VarTest {
    public static void main(String[] args) {
        var list = new ArrayList<String>(); // ArrayList<String>
        var stream = list.stream(); // Stream<String>

        // java 11 开始支持Lambda 表达式入参定义var,且可以定义注解
        stream.map((@Alias("asd") var e) -> e + "123");
    }
}
