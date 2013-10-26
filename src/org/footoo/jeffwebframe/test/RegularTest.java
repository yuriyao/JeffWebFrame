/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午5:27:58 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.test;

import java.util.regex.Pattern;


/**
 * @author jeff
 *
 */
public class RegularTest 
{
	public static void main(String args[])
	{
		Pattern pattern = Pattern.compile("^Java.");
		System.out.println(pattern.matcher("Javas").matches());
	}
}
