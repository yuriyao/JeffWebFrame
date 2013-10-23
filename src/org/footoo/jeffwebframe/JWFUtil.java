/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-22 下午5:20:55 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

/**
 * @author jeff
 *
 */
public class JWFUtil 
{
	/**
	 * 退出程序
	 * @param status
	 */
	public static void exit(int status)
	{
		JWFLog.getLog().close();
		System.exit(status);
	}
	
	/**
	 * 系统初始化
	 */
	public static void init(String args[])
	{
		
	}
}
