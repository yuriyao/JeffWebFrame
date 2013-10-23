/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-22 下午4:55:14 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * @author jeff
 *
 */
public class PrintStreamTest {
	public static void main(String args[])
	{
		PrintStream streams[] = new PrintStream[2];
		streams[0] = System.out;
		try {
			streams[1] = new PrintStream("test.log");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < 20; i ++)
		{
			streams[i % 2].print("hello" + i);
			streams[i % 2].flush();
		}
		streams[0].close();
		streams[1].close();
		streams[1].close();
		streams[0].println("fuck");
	}
}
