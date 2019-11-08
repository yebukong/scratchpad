package pers.mine.scratchpad.lambda;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LambdaTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	public static void main(String[] args) {
		List<String> list = Arrays.asList("1", "2", "3");
		Map<String, String> map = new HashMap<>();
		list.forEach(System.out::println);
		list.forEach((x) -> {
			x = x + "^_^";
			System.out.println(x);
		});
		list.forEach(n -> map.put(n, n+"123"));
		map.forEach((k,v) -> {System.out.println(k+":"+v);});
		String X = ('\u3000'+" "+"\n\t\r");
		System.out.println(X.length());
		System.out.println(X);
		for (int  x : X.toCharArray()) {
			System.out.println(":"+x);
		}
		System.out.println(X.trim());
		System.out.println(X.trim().length());
		System.out.println(X.replaceAll("\\s*", ""));
		System.out.println(X.replaceAll("\\s*", "").length());
		System.out.println(Character.isWhitespace('\u3000'));
		System.out.println(Character.isWhitespace('\t'));
		System.out.println(Character.isWhitespace('\r'));
		System.out.println(Character.isWhitespace('\n'));
		System.out.println(Character.isWhitespace(' '));
		for (int i = 0; i <= 32; i++) {
			System.out.println(i+":" + Character.isWhitespace(i) + "|"+(char)i);
		}
		//Person.builder().age(1).build();
//		String.join(delimiter, elements)
		/*
		Person p = Person.getInstance();
		Person pC =Person.getInstance();
		
		System.out.println(p);
		System.out.println(pC);
		System.out.println(p.hashCode());
		System.out.println(pC.hashCode());
		System.out.println(p.equals(pC));
		System.out.println(p.getName());
		*/
	}
}
