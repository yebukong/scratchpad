package pers.mine.scratchpad.other;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Lombok简单使用
 */
@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class LombokDemo {

	@Getter(lazy = true) // DCL（Double Check Lock） 可用于单例模式懒汉式
	private final static LombokDemo instance = new LombokDemo("小移动", new Date(), 10086);

// 生成代码【AtomicReference:原子引用,太高端，看不懂】
//	private static final AtomicReference<Object> instance = new AtomicReference();
//	public static Person getInstance() {
//		Object value = instance.get();
//		if (value == null)
//			synchronized (instance) {
//				value = instance.get();
//				if (value == null) {
//					Person actualValue = new Person("\u5C0F\u79FB\u52A8", new Date(), 10086);
//					value = actualValue == null ? instance : actualValue;
//					instance.set(value);
//				}
//			}
//		return (Person) (value == instance ? null : value);
//	}

	private String name;
	private Date birthDay;
	private int age;

	public String getName() {
		val list = new ArrayList<>();
		list.add("list");
		System.out.println("list:" + list);
		System.out.println(list instanceof ArrayList);
		System.out.println(list instanceof List);
		System.out.println(list instanceof Object);
		return name + "^_^";
	}
}
