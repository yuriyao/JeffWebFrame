/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午3:45:06 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.test;

/**
 * @author jeff
 *
 */
public class StringTest {
	public static void main(String args[])
	{
		String s1 = "hello";
		String s2 = "hello";
		System.out.println((s1 == s2) + " "  + (s1.compareTo(s2) == 0));
	}
}
