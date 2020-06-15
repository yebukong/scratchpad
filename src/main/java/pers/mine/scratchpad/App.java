package pers.mine.scratchpad;

import java.io.Serializable;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        var x = """
//                 asdasd
//                 啊实打实的ss
//                asdasd""";
//        System.out.println(x);
        System.out.println("Hello World!");

        List<String> l1 = Arrays.asList("1", "2");
        List<String> l2 = Arrays.asList("1", "2");
        List<String> l3 = new ArrayList<String>();
        l3.add("1");
        l3.add("2");
        Map<List<String>, Long> map = new HashMap<List<String>, Long>();
        map.put(l1, 2L);
        System.out.println(map.containsKey(l2));
        System.out.println(map.containsKey(l3));
        long uaId = 6040003420030001L;
        long x1 = (uaId / 1000);
        long x2 = (long) (x1 / (1000)) * 1000;
        long x3 = x1 - x2;
        System.out.println(x1);
        System.out.println(x2);
        System.out.println(x3);
        long y1 = uaId - (long) (uaId / (10000 * 10000L)) * (10000 * 10000L);
        long y2 = y1 / 10000;
        System.out.println(y1);
        System.out.println(y2);
    }
}
