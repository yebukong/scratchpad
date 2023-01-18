package pers.mine.scratchpad;

import java.nio.charset.Charset;

public class SysTest {

    public static void main(String[] args) {
//        System.setProperty("file.encoding","GBK");
        String property = System.getProperty("file.encoding","GBK");
        System.out.println(property);

        Charset.defaultCharset();
    }
}
