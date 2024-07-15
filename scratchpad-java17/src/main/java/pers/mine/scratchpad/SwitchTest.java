package pers.mine.scratchpad;

import java.time.Month;
import java.util.*;

public class SwitchTest {
    public static void main(String[] args) {

    }

    private void oldSwitch(Month m) {
        String s;
        switch (m) {
            case JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER:
                s = "ok";
                break;
            default:
                s = "error";
                break;
        }
        System.out.println(s);
    }

    private void newSwitch(Month m) {
        // 使用 -> 返回
        var s = switch (m) {
            case JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER ->
                    "ok";
            default -> "error";
        };
        System.out.println(s);
    }

    private void newSwitch1(Month m) {
        // 使用 yield 返回
        var s = switch (m) {
            case JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER:
                // 如果使用了新的Switch表达式语法， continue ; break 将不能在Switch表达式中使用;
                yield "ok";
            default:
                System.out.println("yield");
                yield "error";
        };
        System.out.println(s);
    }

    private void newSwitchInstanceof(Collection<String> c) {
        // 将Instanceof模式匹配扩展到Switch，17中属于预览特性
        var s = switch (c) {
            case List<String> l -> l.listIterator();
            case Queue<String> q -> q.iterator();
            default -> null;
        };
    }
}
