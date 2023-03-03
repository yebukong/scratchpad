package pers.mine.scratchpad;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import org.junit.Test;

public class JavapoetTest {
    @Test
    public void test() {
        CodeBlock.Builder add = CodeBlock.builder().add("I ate $L $L", 3, "tacos");

        add.beginControlFlow("");
        add.add("//test");
        add.endControlFlow();
        CodeBlock build = add.build();
        System.out.println(build.toString());
    }

    @Test
    public void x() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addStatement("long now = $T.currentTimeMillis()", System.class)
                .beginControlFlow("if ($T.currentTimeMillis() < now)", System.class)
                .addStatement("$T.out.println($S)", System.class, "Time travelling, woo hoo!")
                .nextControlFlow("else if ($T.currentTimeMillis() == now)", System.class)
                .addStatement("$T.out.println($S)", System.class, "Time stood still!")
                .nextControlFlow("else")
                .addStatement("$T.out.println($S)", System.class, "Ok, time still moving forward")
                .endControlFlow()
                .build();
        System.out.println(main);
    }
}
