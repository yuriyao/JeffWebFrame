/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午8:52:11 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

/**
 * @author jeff
 * HTTP请求头过长的异常
 */
public class JWFHTTPHeaderTooLong extends JWFHTTPException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8167915916895524286L;
	
	public JWFHTTPHeaderTooLong()
	{
		super("HTTP的请求头过长");
	}
	public JWFHTTPHeaderTooLong(String msg)
	{
		super(msg);
	}
}
