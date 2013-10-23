/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-20 下午4:49:20 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

import org.footoo.jeffwebframe.JWFBase;

/**
 * @author jeff
 *
 */
public class JWFNoImplementException extends Exception 
{
	/**
	 * 
	 * @param method 可以指定为JWFBase.GET, JWFBASE.POST...
	 */
	public JWFNoImplementException(int method) 
	{
		this.method = method;
	}
	
	public String toString()
	{
		String methodName = null;
		switch(method)
		{
		case JWFBase.GET:
			methodName = "GET";
			break;
		case JWFBase.POST:
			methodName = "POST";
			break;
		default:
			methodName = "Unknown";
			break;
		}
		return "Cannot support " + methodName + " method";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int method;
	

}
