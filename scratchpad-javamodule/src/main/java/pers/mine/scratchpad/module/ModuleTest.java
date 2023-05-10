package pers.mine.scratchpad.module;

import javax.xml.XMLConstants;
import javax.xml.xpath.XPathException;
import java.lang.reflect.Field;

public class ModuleTest {
    public static void main(String[] args) throws ClassNotFoundException {
        // 非java.base 模块的包需要在module-info.java声明才能使用
        // 在classpath下，就算反射也无法访问
        System.out.println(XMLConstants.XML_NS_PREFIX);
        // 未进行opens声明的类无法深层反射
        // 可以使用 --add-opens java.xml/javax.xml.xpath=scratchpad.javamodule 修改可见性
        // ALL-UNNAMED表示所有未命名模块
        // --illegal-access=warn 选项仅适用于未命名模块
        Class<?> aClass = XPathException.class;
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            printFieldValue(field);
        }
    }

    public static void printFieldValue(Field field) {
        String fieldName = field.getName();
        try {
            // Make the field accessible, in case it is not accessible
            // based on its declaration such as a private field
            field.setAccessible(true);
            // Print the field's value
            System.out.println(fieldName + " = " + field.get(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
