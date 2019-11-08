package pers.mine.scratchpad.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class OneProxy implements InvocationHandler {
	public OneProxy(Object obj) {

	}

	public static Object newInstance(Object obj) {
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
				new OneProxy(obj));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method.getName());
		System.out.println(123);
		
		return method.invoke(proxy,args);
	}
	
	public static void main(String[] args) {
		Test test = (Test)Proxy.newProxyInstance(OneProxy.class.getClassLoader(), new Class[] {Test.class}, new OneProxy(null));
		test.test();
		test.toString();
		test.hashCode();
		test.equals("");
		//Proxy.isProxyClass(calss) //判断当前类是否是jdk代理类
	}
}

interface Test{
	void test();
}
