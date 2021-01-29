package pers.mine.scratchpad.other.hotdeploy;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 热加载demo
 * 
 * @author Mine
 * @date 2019/09/03 16:44:18
 */
public class LoaderDemo {
	public static void main(String[] args) throws ClassNotFoundException, IOException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InterruptedException {
		String path = "D:\\iWork\\Eclipse\\MineTest\\target\\classes\\pers\\mine\\hotdeploy\\TargetClass.class";
		ManageClassLoader mc = new ManageClassLoader();
		while (true) {
			Class c = mc.loadClass(path);
			Object o = c.newInstance();
			Method m = c.getMethod("say");
			m.invoke(o);
			System.out.println(c.getClassLoader());
			Thread.sleep(5000);
		}
	}
}
