package pers.mine.scratchpad.base.optional;

import org.junit.Test;

import java.util.Optional;

/**
 * @create 2021-02-03 17:56
 */
public class OptionalTest {
    @Test
    public void test1() {
        Optional<Object> o1 = Optional.empty();
        Optional<Object> o2 = Optional.ofNullable(null);
        System.out.println(o1.isPresent());
        System.out.println(o2.isPresent());
        System.out.println(o2.equals(o1));
        o2.orElseThrow(RuntimeException::new);
    }
}
