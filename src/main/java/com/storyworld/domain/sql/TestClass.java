package com.storyworld.domain.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestClass {

	public void testMethod() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Class<?> class1 = Class.forName("com.storyworld.domain.sql.Story");
		System.out.println(class1.isInstance(new Story()));
		Method[] m = class1.getDeclaredMethods();
		System.out.println(Arrays.toString(m));
		Object c = Class.forName("com.storyworld.domain.sql.Story").newInstance();
		if (c instanceof Story) {
			Story new_name = (Story) c;
			Method m1 = class1.getMethod("setTitle", String.class);
			m1.invoke(new_name, "1");
			System.out.println(new_name.toString());
		}

	}
}
