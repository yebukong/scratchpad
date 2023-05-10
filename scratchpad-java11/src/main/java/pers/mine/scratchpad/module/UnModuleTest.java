package pers.mine.scratchpad.module;

import cn.hutool.core.util.ReflectUtil;

import javax.xml.xpath.XPathException;
import java.lang.reflect.Field;

public class UnModuleTest {
    public static void main(String[] args) {
        // 如果当前项目未使用模块化，则使用反射时仅出现警告 --illegal-access=warn
        Class<?> aClass = XPathException.class;
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            ReflectUtil.getStaticFieldValue(field);
        }
    }
}
