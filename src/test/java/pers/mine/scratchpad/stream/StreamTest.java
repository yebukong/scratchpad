package pers.mine.scratchpad.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Mine
 * @date 2019/07/05 11:57:50
 */
public class StreamTest {
    @Test
    public void test() {
        List<Integer> list1 = Stream.iterate(1, i -> i + 1).limit(100).collect(Collectors.toList());
        List<Integer> list2 = Stream.iterate(1, i -> i + 2).limit(100).collect(Collectors.toList());
        System.out.println(list1);
        System.out.println(list2);
        boolean addAll = list1.addAll(list2);
        System.out.println(list1);

        List<String> qList = Arrays.asList("1", "2", "3", "4");
        // filter 用于筛选指定元素
        List<String> filterList = qList.stream().filter(x -> Integer.parseInt(x) < 3).collect(Collectors.toList());
        System.out.println(filterList);
    }

    @Test
    public void forEachTest1() {
        Stream.iterate(0, i -> i + 1).limit(100).forEach(System.out::println);
    }

    @Test
    public void forEachTest2() {
//        IntStream.range(0, 100).forEach(System.out::println);

        ArrayList<Integer> collect = IntStream.range(0, 100).collect(ArrayList<Integer>::new, ArrayList::add, ArrayList::addAll);
        for (Integer integer : collect) {
            System.out.println(integer);
        }
    }
}
