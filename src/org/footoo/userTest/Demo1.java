/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-26 下午2:56:04 by jeff 
 *  This is open source by GPL
 */
package org.footoo.userTest;

import org.footoo.jeffwebframe.JWFBase;
import org.footoo.jeffwebframe.JWFResult;

/**
 * @author jeff
 *
 */
public class Demo1 extends JWFBase 
{
	public JWFResult get()
	{
		System.out.println("Hello world");
		JWFResult result = new JWFResult();
		String name = getGetValue("name");
		name = name == null ? "jeff" : name;
		String sex = getGetValue("sex");
		sex = sex == null ? "Male" : sex;
		result.setContent(("Hello " + name + " " + sex).getBytes());
		
		return result;
	}
}
