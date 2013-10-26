/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午7:52:35 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.test;

import java.lang.reflect.InvocationTargetException;

class Person
{
	public Person()
	{
		
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void print()
	{
		System.out.println(name);
	}
	
	
	public Person(String name)
	{
		this.name = name;
	}
	
	private String name;
}

/**
 * @author jeff
 *
 */
public class ReflectTest 
{
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException
	{
		Class<?> cls = Class.forName("org.footoo.jeffwebframe.test.Person");
		
		Person person = (Person) cls.newInstance();
		person.setName("jeff");
		person.print();
		
		Person person2 = (Person)cls.getConstructors()[1].newInstance("yuri");
		person2.print();
		
		Person person3 = (Person) cls.getConstructor(String.class).newInstance("yao");
		person3.print();
	}
}
