package test;

import java.lang.reflect.Field;

public class My {

	public static void setAllComponentsName(Object f)

	{

		Field[] fields = f.getClass().getDeclaredFields();

		for (int i = 0, len = fields.length; i < len; i++) {

			try {

				String varName = fields[i].getName();

				boolean accessFlag = fields[i].isAccessible();

				fields[i].setAccessible(true);

				Object o = fields[i].get(f);

				System.out.println("传入的对象中包含一个如下的属性：" + varName + "=" + o);

				fields[i].setAccessible(accessFlag);

			} catch (SecurityException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			} catch (IllegalArgumentException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			} catch (IllegalAccessException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

	}

	public static void main(String[] args) {

		setAllComponentsName(new Test());

	}

}
