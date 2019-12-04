package pers.mine.scratchpad.base.generics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class AnnotationValueEditTest {

	@Test
	public void test() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//测试动态修改注解值
		AnnotationValueEdit ave = new AnnotationValueEdit();
		Annotation[] declaredAnnotations = ave.getClass().getDeclaredAnnotationsByType(TestAnn.class);
		for (Annotation annotation : declaredAnnotations) {
			System.out.println(annotation.toString());
			InvocationHandler h = Proxy.getInvocationHandler(annotation);
			Field hField = h.getClass().getDeclaredField("memberValues");
	        hField.setAccessible(true);
	        Map memberValues = (Map) hField.get(h);
	        Set<Map.Entry> entrySet = memberValues.entrySet();
	        for (Map.Entry entry : entrySet) {
				System.out.println(entry);
			}
	        memberValues.put("value", "testPro");
		}
		for (Annotation annotation : declaredAnnotations) {
			System.out.println(annotation.toString());
		}
	}

}
