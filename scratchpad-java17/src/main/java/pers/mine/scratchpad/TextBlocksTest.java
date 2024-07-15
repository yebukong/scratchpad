package pers.mine.scratchpad;

public class TextBlocksTest {
    public static void main(String[] args) {
        // 相当于 "line 1\nline 2\nline 3\n"
        var s = """
                line 1
                line 2
                line 3
                """;
        // 相当于 "line 1\nline 2\nline 3",末尾不换行
        var s1 = """
                line 1
                line 2
                line 3""";
        // ”“ 空串表示
        String empty = """
                """;
        System.out.println(empty);
        // 行终止符被标准化为LF字符。这样可以避免不同平台（例如Windows和Unix）之间的兼容性问题。
        // 附带的前置空格和所有尾随空格均被删除。偶然的前导空格是通过找到所有行的前导空格的公共数量来确定的。
        // 不能在文本块内部出现连续三个双引号，必须转义其中一个
        String code =
                """
                            String text = \"""
                            A text block inside a text block
                            \""";
                        """;
        System.out.println(code);
        // 可以使用 \将单行文本拆分为多行
        String text = """
                1
                2 \
                3 \
                4
                5
                """;
        System.out.println(text);
        // 如果我们明确需要前置空格，则可以使用indent()方法：
        String indenttext = """
                {
                    "name": "FunTester",
                    "age": "30"
                }
                """.indent(4);
        System.out.println(indenttext);
    }
}
