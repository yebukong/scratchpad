package pers.mine.scratchpad.base;

import java.util.stream.IntStream;

/**
 * avoid getfield opcode
 *
 * @see String#trim()
 */
public class AvoidGetfieldOpcodeTest {
    private final long[] val = {0};

    public void reset() {
        val[0] = 0;
    }

    public void changeVal() {
        for (int i = 0; i < 1000 * 1000 * 1000; i++) {
            val[0] += i;
        }
    }

    public void changeValLocal() {
        long[] val = this.val;
        for (int i = 0; i < 1000 * 1000 * 1000; i++) {
            val[0] += i;
        }
    }

    /**
     * 测试下来速度提升很轻微
     */
    public static void main(String[] args) {
        AvoidGetfieldOpcodeTest test = new AvoidGetfieldOpcodeTest();
        long start = System.nanoTime();
        IntStream.range(0, 100).forEach(i -> {
            test.reset();
            test.changeValLocal();
        });
        System.out.println(System.nanoTime() - start);
        start = System.nanoTime();
        IntStream.range(0, 100).forEach(i -> {
            test.reset();
            test.changeVal();
        });
        System.out.println(System.nanoTime() - start);
    }
}
