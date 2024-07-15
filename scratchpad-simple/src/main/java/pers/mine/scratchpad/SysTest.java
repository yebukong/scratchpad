package pers.mine.scratchpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;

public class SysTest {
    final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
//        System.setProperty("file.encoding","GBK");
        String property = System.getProperty("file.encoding","GBK");
        System.out.println(property);

        Charset.defaultCharset();
    }
}
